package com.devdd.framework.spotify.ui.base

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 3:50 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
abstract class SpotifyBottomSheetDialogFragment<V : ViewDataBinding> : BottomSheetDialogFragment() {

   protected lateinit var binding: V

    protected fun requireBinding(): V = requireNotNull(binding)

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createBinding(inflater, container)
            .also { binding = it }
            .root
    }

    protected abstract fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): V

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated(requireBinding(), savedInstanceState)
        view.clipToOutline = true
    }

    abstract fun onViewCreated(binding: V, savedInstanceState: Bundle?)
}
