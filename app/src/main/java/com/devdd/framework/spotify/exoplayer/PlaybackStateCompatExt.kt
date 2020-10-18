package com.devdd.framework.spotify.exoplayer

import android.support.v4.media.session.PlaybackStateCompat


/**
 * Created by @author Deepak Dawade on 10/18/2020 at 2:38 PM.
 * Copyright (c) 2020 deepak.dawade.dd@gmail.com All rights reserved.
 *
 */
inline val PlaybackStateCompat.isPrepared
    get() = state == PlaybackStateCompat.STATE_BUFFERING || state == PlaybackStateCompat.STATE_PLAYING || state == PlaybackStateCompat.STATE_PAUSED

inline val PlaybackStateCompat.isPlaying
    get() = state == PlaybackStateCompat.STATE_BUFFERING || state == PlaybackStateCompat.STATE_PLAYING

inline val PlaybackStateCompat.isPlayingEnabled
    get() = actions and PlaybackStateCompat.ACTION_PLAY != 0L || (actions and PlaybackStateCompat.ACTION_PLAY_PAUSE != 0L && state == PlaybackStateCompat.STATE_PAUSED)