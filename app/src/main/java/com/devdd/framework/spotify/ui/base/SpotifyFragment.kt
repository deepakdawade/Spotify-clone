package com.devdd.framework.spotify.ui.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 3:50 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
abstract class SpotifyFragment <V : ViewDataBinding>(@LayoutRes layoutId: Int) :
    Fragment(layoutId) {

//    val binding: V by dataBinding()
    protected lateinit var binding: V

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createBinding(inflater, container)
            .also { binding = it }
            .root
    }
    protected abstract fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): V

    abstract fun onViewCreated(binding: V, savedInstanceState: Bundle?)

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated(binding, savedInstanceState)
    }
}
