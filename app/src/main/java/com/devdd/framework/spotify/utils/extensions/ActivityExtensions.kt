package com.devdd.framework.spotify.utils.extensions

import android.app.Activity
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.devdd.framework.spotify.utils.result.Event
import com.devdd.framework.spotify.utils.result.observeEvent

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 7:53 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
inline fun <reified T : AppCompatActivity> AppCompatActivity.finishAndLaunch(
    noinline with: (Intent.() -> Intent) = { Intent() },
) {
    with(Intent()).setClass(this, T::class.java).also {
        startActivity(it)
    }
    finish()
}

fun <V : ViewDataBinding> AppCompatActivity.bindingWithLifecycleOwner(@LayoutRes layoutId: Int): V {
    val binding: V = DataBindingUtil.setContentView(this, layoutId)
    binding.lifecycleOwner = this
    return binding
}

fun Activity.hideSoftInput() {
    val imm: InputMethodManager? = getSystemService()
    val currentFocus = currentFocus
    if (currentFocus != null && imm != null) {
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}

fun <T> FragmentActivity.observe(liveData: LiveData<T>, perform: T.() -> Unit) {
    liveData.observe(this, Observer {
        perform(it)
    })
}

fun <T> FragmentActivity.observeEvent(liveData: LiveData<Event<T>>, perform: T.() -> Unit) {
    liveData.observeEvent(this) {
        perform(it)
    }
}