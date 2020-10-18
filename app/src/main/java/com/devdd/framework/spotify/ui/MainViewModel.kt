package com.devdd.framework.spotify.ui

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_MEDIA_ID
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devdd.framework.spotify.data.entities.Song
import com.devdd.framework.spotify.exoplayer.MusicContants.MEDIA_ROOT_ID
import com.devdd.framework.spotify.exoplayer.MusicServiceConnection
import com.devdd.framework.spotify.exoplayer.isPlaying
import com.devdd.framework.spotify.exoplayer.isPlayingEnabled
import com.devdd.framework.spotify.exoplayer.isPrepared
import com.devdd.framework.spotify.utils.result.Resource


/**
 * Created by @author Deepak Dawade on 10/18/2020 at 2:13 PM.
 * Copyright (c) 2020 deepak.dawade.dd@gmail.com All rights reserved.
 *
 */
class MainViewModel @ViewModelInject constructor(
    private val musicServiceConnection: MusicServiceConnection,
) : ViewModel() {

    private val _mediaItems = MutableLiveData<Resource<List<Song>>>()
    val mediaItems: LiveData<Resource<List<Song>>> get() = _mediaItems

    private val isNetworkConnected = musicServiceConnection.networkConnected
    private val networkError = musicServiceConnection.networkError
    private val currPlayingSong = musicServiceConnection.currentPlayingSong
    private val playBackState = musicServiceConnection.playbackState

    init {
        _mediaItems.postValue(Resource.loading(null))
        musicServiceConnection.subscribe(MEDIA_ROOT_ID,
            object : MediaBrowserCompat.SubscriptionCallback() {
                override fun onChildrenLoaded(
                    parentId: String,
                    children: MutableList<MediaBrowserCompat.MediaItem>,
                ) {
                    super.onChildrenLoaded(parentId, children)
                    val items = children.map {
                        with(it.description) {
                            Song(
                                mediaId = it.mediaId.toString(),
                                title = title.toString(),
                                subtitle = subtitle.toString(),
                                songUrl = mediaUri.toString(),
                                imageUrl = iconUri.toString()
                            )
                        }
                    }
                    _mediaItems.postValue(Resource.success(items))
                }
            })
    }

    fun skipToNext() {
        musicServiceConnection.transportController.skipToNext()
    }

    fun skipToPrevious() {
        musicServiceConnection.transportController.skipToPrevious()
    }

    fun seekTo(position: Long) {
        musicServiceConnection.transportController.seekTo(position)
    }

    fun playOrToggleSong(media: Song, toggle: Boolean = false) {
        val isPrepared = playBackState.value?.isPrepared ?: false
        if (isPrepared && currPlayingSong.value?.getString(METADATA_KEY_MEDIA_ID) == media.mediaId) playBackState.value?.let {
            when {
                it.isPlaying -> if (toggle) musicServiceConnection.transportController.pause()
                it.isPlayingEnabled -> musicServiceConnection.transportController.play()
                else -> Unit
            }
        } else
            musicServiceConnection.transportController.playFromMediaId(media.mediaId, null)
    }

    override fun onCleared() {
        super.onCleared()
        musicServiceConnection.unSubscribe(MEDIA_ROOT_ID,
            object : MediaBrowserCompat.SubscriptionCallback() {
                //EMPTY BODY
            })
    }
}