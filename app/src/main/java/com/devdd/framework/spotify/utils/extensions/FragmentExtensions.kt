package com.devdd.framework.spotify.utils.extensions

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 7:53 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
inline fun <reified T : AppCompatActivity> Fragment.launch(
        noinline with: (Intent.() -> Intent) = { Intent() }) {
    with(Intent()).setClass(requireContext(), T::class.java).also {
        startActivity(it)
    }
}

fun <T : ViewDataBinding> Fragment.bindingWithLifecycleOwner(
        inflater: LayoutInflater,
        @LayoutRes layoutId: Int,
        container: ViewGroup?): T {

    val binding: T = DataBindingUtil.inflate(inflater, layoutId, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    return binding
}

inline fun <reified T> Fragment.callerFragment(callerFragmentType: CallerFragmentType, position: Int): T? {
    return when (callerFragmentType) {
        CallerFragmentType.PARENT_ACTIVITY -> activity as? T
        CallerFragmentType.PARENT_FRAGMENT -> parentFragmentManager.primaryFragment() as? T
        CallerFragmentType.VIEW_PAGER_FRAGMENT -> parentFragmentManager.primaryFragment()?.childFragmentManager?.primaryFragment(position) as? T
        CallerFragmentType.BOTTOM_NAVIGATION -> parentFragmentManager.primaryFragment() // get parent fragment which contains bottom navigation (i.e DASHBOARD FRAGMENT)
                ?.childFragmentManager?.primaryFragment() // get parent fragment navHostFragment
                ?.childFragmentManager?.primaryFragment() as? T // get navHostFragment current fragment
    }
}

fun Fragment.requireNavHostParentFragment(): Fragment = requireParentFragment().requireParentFragment()

fun FragmentManager.primaryFragment(position: Int = 0): Fragment? = fragments.getOrNull(position)

fun Fragment.hideSoftInput(): Unit = requireActivity().hideSoftInput()

enum class CallerFragmentType {
    BOTTOM_NAVIGATION,
    PARENT_FRAGMENT,
    VIEW_PAGER_FRAGMENT,
    PARENT_ACTIVITY
}
