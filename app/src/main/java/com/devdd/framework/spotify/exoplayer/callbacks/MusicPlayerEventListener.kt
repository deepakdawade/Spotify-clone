package com.devdd.framework.spotify.exoplayer.callbacks

import android.widget.Toast
import com.devdd.framework.spotify.exoplayer.MusicService
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player

/**
 * Created by @author Deepak Dawade on 9/30/2020 at 9:58 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
class MusicPlayerEventListener(
    private val musicService: MusicService
) : Player.EventListener{
    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        super.onPlayerStateChanged(playWhenReady, playbackState)
        if (playbackState == Player.STATE_READY && !playWhenReady){
            musicService.stopForeground(false)
        }
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        super.onPlayerError(error)
        Toast.makeText(musicService,"An error Occurred",Toast.LENGTH_LONG).show()
    }
}