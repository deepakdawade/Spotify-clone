package com.devdd.framework.spotify.utils.extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 7:53 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/

inline fun <T : ViewDataBinding> bindWithLayout(
        @LayoutRes layoutId: Int,
        parent: ViewGroup,
        bind: (T.() -> Unit) = {}): T {
    val inflater: LayoutInflater = LayoutInflater.from(parent.context)
    val binding: T = DataBindingUtil.inflate(inflater, layoutId, parent, false)
    binding.bind()
    return binding
}

inline fun <T : ViewDataBinding> bindWithLayout(
        @LayoutRes layoutId: Int,
        inflater: LayoutInflater,
        bind: (T.() -> Unit) = {}): T {
    val binding: T = DataBindingUtil.inflate(inflater, layoutId, null, false)
    binding.bind()
    return binding
}