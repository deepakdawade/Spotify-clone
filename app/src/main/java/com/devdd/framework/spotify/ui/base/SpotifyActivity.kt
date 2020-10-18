package com.devdd.framework.spotify.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.devdd.framework.spotify.utils.extensions.bindingWithLifecycleOwner

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 3:50 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
abstract class SpotifyActivity<V : ViewDataBinding>(@LayoutRes private val layoutId: Int) :
    AppCompatActivity(layoutId) {
    private var _binding: V? = null
    protected val binding: V get() = requireNotNull(_binding)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingWithLifecycleOwner<V>(layoutId)
        setupView(binding)
    }

    abstract fun setupView(binding: V)
    abstract fun observeLiveData()
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
