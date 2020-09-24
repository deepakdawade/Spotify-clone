package com.devdd.framework.spotify.utils.extensions

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.appbar.MaterialToolbar
/**
 * Created by @author Deepak Dawade on 9/20/2020 at 7:53 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
/** Convenience for callbacks/listeners whose return value indicates an event was consumed. */
inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}

/**
 * Allows calls like
 *
 * `supportFragmentManager.inTransaction { add(...) }`
 */
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

/**
 * Allows adding fragment to activity
 */
fun AppCompatActivity.addFrag(container: Int, fragment: Fragment, tag: String) {
    supportFragmentManager.inTransaction {
        add(container, fragment, tag)
    }
}

fun EditText.showSoftInput() {
    requestFocus()
    setSelection(text?.length ?: 0)
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}


fun Activity.showSoftInput() {
    val currentFocus = currentFocus
    if (currentFocus != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(currentFocus, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun View.hideSoftInput() {
    val imm: InputMethodManager? = context.getSystemService()
    if (imm != null && imm.isActive(this)) {
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}

val MaterialToolbar.navigationIconView: View?
    get() {
        //check if contentDescription previously was set
        val hadContentDescription = !TextUtils.isEmpty(navigationContentDescription)
        val contentDescription = if (hadContentDescription) navigationContentDescription else "navigationIcon"
        navigationContentDescription = contentDescription
        val potentialViews = arrayListOf<View>()
        //find the view based on it's content description, set programmatically or with android:contentDescription
        findViewsWithText(potentialViews, contentDescription, FIND_VIEWS_WITH_CONTENT_DESCRIPTION)
        //Clear content description if not previously present
        if (!hadContentDescription) {
            navigationContentDescription = null
        }
        //Nav icon is always instantiated at this point because calling setNavigationContentDescription ensures its existence
        return potentialViews.firstOrNull()
    }

fun ProgressBar.animateProgress(progress: Int) {
    if (progress == this.progress) return
    // will update the "progress" propriety of progressBar until it reaches providedProgress
    val animation = ObjectAnimator.ofInt(this, "progress", progress)
    animation.duration = 500 // 0.5 second
    animation.interpolator = DecelerateInterpolator()
    animation.start()
}

fun View.drawBehindNavigationBar() {
    systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_STABLE or SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
}

fun View.doOnApplyWindowInsets(
        f: (View, insets: WindowInsetsCompat, initialPadding: ViewDimensions, initialMargin: ViewDimensions) -> Unit
) {
    // Create a snapshot of the view's padding state
    val initialPadding = createStateForViewPadding(this)
    val initialMargin = createStateForViewMargin(this)
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        f(v, insets, initialPadding, initialMargin)
        insets
    }
    requestApplyInsetsWhenAttached()
}

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        // We're already attached, just request as normal
        requestApplyInsets()
    } else {
        // We're not attached to the hierarchy, add a listener to
        // request when we are
        addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

private fun createStateForViewPadding(view: View) = ViewDimensions(
        view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom, view.paddingStart,
        view.paddingEnd
)

private fun createStateForViewMargin(view: View): ViewDimensions {
    return (view.layoutParams as? ViewGroup.MarginLayoutParams)?.let {
        ViewDimensions(it.leftMargin, it.topMargin, it.rightMargin, it.bottomMargin,
                it.marginStart, it.marginEnd)
    } ?: ViewDimensions()
}

data class InitialPadding(val left: Int, val top: Int,
                          val right: Int, val bottom: Int)

data class ViewDimensions(
        val left: Int = 0, val top: Int = 0, val right: Int = 0, val bottom: Int = 0,
        val start: Int = 0, val end: Int = 0
)
