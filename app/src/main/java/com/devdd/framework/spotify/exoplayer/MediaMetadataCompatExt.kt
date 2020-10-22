package com.devdd.framework.spotify.exoplayer

import android.support.v4.media.MediaMetadataCompat
import com.devdd.framework.spotify.data.entities.Song

fun MediaMetadataCompat.toSong(): Song? {
    return description?.let {
        Song(
            it.mediaId ?: "",
            it.title.toString(),
            it.subtitle.toString(),
            it.mediaUri.toString(),
            it.iconUri.toString()
        )
    }
}