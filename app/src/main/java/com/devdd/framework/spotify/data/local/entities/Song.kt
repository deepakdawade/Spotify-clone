package com.devdd.framework.spotify.data.local.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 10:07 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
@Entity
data class Song(
    @Id
    var mediaId:Long = 0,
    val mediaUrl:String = "",
    val mediaTitle:String = "",
    val mediaSubtitle:String = "",
    val mediaThumbnail:String = ""
)