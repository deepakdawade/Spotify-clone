package com.devdd.framework.spotify.data.local.objectbox.database

import com.devdd.framework.spotify.data.local.entities.Song
import com.devdd.framework.spotify.data.local.objectbox.ObjectBox.boxStore
import com.devdd.framework.spotify.data.local.objectbox.SpotifyBox
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import javax.inject.Inject

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 10:25 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
class BoxSong @Inject constructor():SpotifyBox<Song>() {
    override val box: Box<Song> = boxStore.boxFor()
}