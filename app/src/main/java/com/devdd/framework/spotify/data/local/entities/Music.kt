package com.devdd.framework.spotify.data.local.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 10:07 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
@Entity
data class Music(
    @Id(assignable = true)
    var obId:Long = 0,
    var mediaId: String = "",
    val mediaUrl: String = "",
    val title: String = "",
    val subtitle: String = "",
    val mediaThumbnail: String = "",
    val imageUrl: String,
    val songUrl: String
)