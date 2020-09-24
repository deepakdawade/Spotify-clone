package com.devdd.framework.spotify.utils.extensions

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.devdd.framework.spotify.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.random.Random

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 7:53 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/

fun Context.toDimensions(value: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, resources.displayMetrics)
}
fun Int.dp(context: Context): Float {
    return this / context.resources.displayMetrics.density
}

fun Int.px(context: Context): Float {
    return this * context.resources.displayMetrics.density
}

@ColorInt
fun randomColor(): Int {
    return Color.argb(128, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
}

fun Context.stringByLocale(id: Int, locale: Locale = Locale.ENGLISH): String {
    val configuration = Configuration(resources.configuration)
    configuration.setLocale(locale)
    return createConfigurationContext(configuration).resources.getString(id)
}

fun Context.resourcesByLocale(locale: Locale = Locale.ENGLISH): Resources {
    val configuration = Configuration(resources.configuration)
    configuration.setLocale(locale)
    return createConfigurationContext(configuration).resources
}

fun Context.getResourceId(name: String, type: String): Int {
    return resources.getIdentifier(name, type, packageName)
}

fun Context.adjustFontScale(configuration: Configuration, scale: Float): DisplayMetrics {
    configuration.fontScale = scale
    val metrics: DisplayMetrics = resources.displayMetrics
    val wm = getSystemService(WINDOW_SERVICE) as WindowManager
    wm.defaultDisplay.getMetrics(metrics)
    metrics.scaledDensity = configuration.fontScale * metrics.density
    return metrics
}

fun Context.showMaterialAlertDialog(
        @StringRes title: Int = R.string.app_name,
        @StringRes message: Int = R.string.app_name,
        @StringRes negativeActionMsg: Int = R.string.app_name,
        @StringRes positiveActionMsg: Int = R.string.app_name,
        positiveAction: () -> Unit = {},
        negativeAction: () -> Unit = {},
        @LayoutRes layoutId: Int? = null,
        @DrawableRes iconId: Int? = null,
        cancelable: Boolean = true
): AlertDialog {
    val dialogBuilder = MaterialAlertDialogBuilder(this).setCancelable(cancelable)

    if (title != R.string.app_name) dialogBuilder.setTitle(title)

    if (message != R.string.app_name) dialogBuilder.setMessage(message)

    if (negativeActionMsg != R.string.app_name) {
        dialogBuilder.setNegativeButton(negativeActionMsg) { dialog, _ ->
            negativeAction()
            dialog.cancel()
        }
    }

    if (positiveActionMsg != R.string.app_name) {
        dialogBuilder.setPositiveButton(positiveActionMsg) { dialog, _ ->
            positiveAction()
            dialog.cancel()
        }
    }

    if (layoutId != null) dialogBuilder.setView(layoutId)

    if (iconId != null) dialogBuilder.setIcon(iconId)

    return dialogBuilder.create()
}