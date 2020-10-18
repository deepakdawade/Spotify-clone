package com.devdd.framework.spotify.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.devdd.framework.spotify.utils.extensions.bindingWithLifecycleOwner

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 3:50 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
abstract class SpotifyFragment<V : ViewDataBinding>(@LayoutRes private val layoutId: Int) : Fragment(layoutId) {

    private var _binding: V? = null
    protected val binding: V get() = requireNotNull(_binding)
    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return bindingWithLifecycleOwner<V>(inflater, layoutId, container)
            .also { _binding = it }
            .root
    }

    abstract fun onViewCreated(binding: V, savedInstanceState: Bundle?)

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated(binding, savedInstanceState)
        observeLiveData()
    }

    abstract fun observeLiveData()
}
