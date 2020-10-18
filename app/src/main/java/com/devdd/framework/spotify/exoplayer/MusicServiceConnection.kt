package com.devdd.framework.spotify.exoplayer

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devdd.framework.spotify.exoplayer.MusicContants.NETWORK_ERROR
import com.devdd.framework.spotify.utils.result.Event
import com.devdd.framework.spotify.utils.result.Resource

/**
 * Created by @author Deepak Dawade on 10/10/2020 at 11:26 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
class MusicServiceConnection(
    private val context: Context
) {
    private val _networkConnected:MutableLiveData<Event<Resource<Boolean>>> = MutableLiveData()
    val networkConnected: LiveData<Event<Resource<Boolean>>> get() = _networkConnected

    private val _networkError:MutableLiveData<Event<Resource<Boolean>>> = MutableLiveData()
    val networkError: LiveData<Event<Resource<Boolean>>> get() = _networkError

    private val _playbackState:MutableLiveData<PlaybackStateCompat?> = MutableLiveData()
    val playbackState: LiveData<PlaybackStateCompat?> get() = _playbackState

    private val _currentPlayingSong:MutableLiveData<MediaMetadataCompat?> = MutableLiveData()
    val currentPlayingSong: LiveData<MediaMetadataCompat?> get() = _currentPlayingSong

    lateinit var mediaControllerCompat: MediaControllerCompat

    private val mediaBrowserConnectionCallback = MediaBrowserConnectedCallBack(context)

    private val mediabrowser = MediaBrowserCompat(
        context,
        ComponentName(
            context,
            MusicService::class.java
        ),
        mediaBrowserConnectionCallback,
        null
    ).apply {
        connect()
    }
    val transportController: MediaControllerCompat.TransportControls
        get() = mediaControllerCompat.transportControls

    fun subscribe(parentId:String,callback:MediaBrowserCompat.SubscriptionCallback){
        mediabrowser.subscribe(parentId,callback)
    }

    fun unSubscribe(parentId:String,callback:MediaBrowserCompat.SubscriptionCallback){
        mediabrowser.unsubscribe(parentId,callback)
    }

    private inner class MediaBrowserConnectedCallBack(
        private val context:Context
    ):MediaBrowserCompat.ConnectionCallback(){
        override fun onConnected() {
            mediaControllerCompat = MediaControllerCompat(context,mediabrowser.sessionToken).apply {
                registerCallback(MediaControllerCallBack())
            }
            _networkConnected.postValue(Event(Resource.success(true)))
        }

        override fun onConnectionSuspended() {
            _networkConnected.postValue(Event(Resource.error("The network was suspended",false)))
        }

        override fun onConnectionFailed() {
            _networkConnected.postValue(Event(Resource.error("couldn't connect to the media browser",false)))
        }
    }

    inner class MediaControllerCallBack:MediaControllerCompat.Callback(){
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            _playbackState.postValue(state)
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            _currentPlayingSong.postValue(metadata)
        }

        override fun onSessionEvent(event: String?, extras: Bundle?) {
            super.onSessionEvent(event, extras)
            when(event){
                NETWORK_ERROR->{
                    _networkError.postValue(
                        Event(Resource.error("Couldn't connect to the server,please check your internet connection",null))
                    )
                }
            }
        }

        override fun onSessionDestroyed() {
            mediaBrowserConnectionCallback.onConnectionSuspended()
        }
    }

}