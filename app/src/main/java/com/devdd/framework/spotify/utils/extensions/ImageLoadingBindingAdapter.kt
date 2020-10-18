package com.devdd.framework.spotify.utils.extensions

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.RequestManager


/**
 * Created by @author Deepak Dawade on 10/18/2020 at 3:05 PM.
 * Copyright (c) 2020 deepak.dawade.dd@gmail.com All rights reserved.
 */
@BindingAdapter("requestManager", "imageUrl")
fun AppCompatImageView.loadImageWithGlide(requestManager: RequestManager, imageUrl: String) {
    requestManager.load(imageUrl).into(this)
}
