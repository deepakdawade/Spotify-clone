package com.devdd.framework.spotify.utils.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.devdd.framework.spotify.R
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import timber.log.Timber

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 7:53 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/

@BindingAdapter("shimmerAutoStart")
fun setShimmerDrawable(view: AppCompatImageView, enable: Boolean) {
    val shimmer = Shimmer.ColorHighlightBuilder()
        .setBaseColor(ContextCompat.getColor(view.context, R.color.colorAccent))
        .setHighlightColor(ContextCompat.getColor(view.context, R.color.colorAccent))
        .setHighlightAlpha(0.85f)
        .setBaseAlpha(0.95f)
        .setDuration(300)
        .setRepeatDelay(500)
        .setIntensity(0.1f)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(enable)
        .build()
    val shimmerDrawable = ShimmerDrawable()
    shimmerDrawable.setShimmer(shimmer)
    view.setImageDrawable(shimmerDrawable)
}

internal fun <T> ImageView.setImage(url: T) {
    try {
        Glide.with(this.context)
            .load(url)
            .fitCenter()
            .into(this)
    } catch (e: IllegalArgumentException) {
        Timber.e(e)
    }
}

@BindingAdapter("app:setRoundImage", "app:placeholderForRoundImage")
fun setRoundImage(view: AppCompatImageView, url: String?, placeholder: Drawable) {
    Glide.with(view.context)
        .load(url)
        .placeholder(placeholder)
        .apply(RequestOptions().circleCrop())
        .into(view)
}

@BindingAdapter("glide:loadImage", "glide:errorPlaceholder", requireAll = false)
fun glideLoadImage(
    imageView: AppCompatImageView,
    imagePath: String?,
    errorPlaceHolder: Drawable? = null
) {
    val glideRequest = Glide.with(imageView)
    if (imagePath != null) {
        val loader =
            glideRequest.load(imagePath).transition(DrawableTransitionOptions.withCrossFade(100))
        errorPlaceHolder?.let { drawable ->
            loader.placeholder(drawable)
        }
        loader.into(imageView)
    } else {
        if (errorPlaceHolder != null) {
            val loader = glideRequest.load(errorPlaceHolder)
                .transition(DrawableTransitionOptions.withCrossFade(100))
            loader.into(imageView)
        }
    }
}
