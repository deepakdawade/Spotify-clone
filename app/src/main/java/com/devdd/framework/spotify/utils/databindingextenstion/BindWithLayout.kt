package com.devdd.framework.spotify.utils.databindingextenstion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


/**
 * Created by @author Deepak Dawade on 10/23/2020 at 12:09 AM.
 * Copyright (c) 2020 deepak.dawade.dd@gmail.com All rights reserved.
 *
 */

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