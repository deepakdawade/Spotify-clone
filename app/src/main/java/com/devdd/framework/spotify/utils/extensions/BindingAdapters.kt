package com.devdd.framework.spotify.utils.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Handler
import android.text.InputFilter
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.ProgressBar
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputLayout.END_ICON_NONE
import com.google.android.material.textview.MaterialTextView
import com.medcords.rurallite.utils.extension.DecimalDigitInputFilter

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 7:53 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/

@BindingAdapter("textValue", "textDefault")
fun setTextElseDefault(view: MaterialTextView, value: String?, default: String) {
    view.text = if (value.isNullOrBlank()) default else value
}

@BindingAdapter("setDrawBehindNavigation")
fun setDrawBehindNavigation(view: View, shouldDrawBehindNavigation: Boolean) {
    if (shouldDrawBehindNavigation) view.drawBehindNavigationBar()
}
@BindingAdapter("textColorTint")
fun textColor(view: MaterialTextView, color: Int) {
    view.apply { setTextColor(ContextCompat.getColor(context, color)) }
}

@BindingAdapter("backgroundColorTint")
fun backgroundTintColor(view: View, color: Int) {
    view.apply { backgroundTintList = ContextCompat.getColorStateList(context, color) }
}

@BindingAdapter("imageTint")
fun tintColorName(view: AppCompatImageView, color: Int) {
    view.apply {
        imageTintList = ColorStateList.valueOf(color)
    }
}

@BindingAdapter("goneIfNull")
fun goneIfNullOrBlank(view: View, string: String?) {
    view.visibility = if (string.isNullOrBlank()) View.GONE else View.VISIBLE
}

@BindingAdapter("stringId", "stringValue")
fun formatString(view: MaterialTextView, stringId: Int?, stringValue: String) {
    view.visibility = if (stringId == null) {
        view.text = ""
        View.GONE
    } else {
        view.context.apply {
            view.text = String.format(stringValue, getString(stringId))
        }
        View.VISIBLE
    }
}

@BindingAdapter("showOnData", "shouldShow", requireAll = false)
fun showOnData(view: View, response: String?, shouldShow: Boolean = true) {
    view.visibility = if (response.isNullOrBlank() && shouldShow) View.GONE else View.VISIBLE
}

@BindingAdapter("tintOnIncomplete", "tintOnComplete", "determinateProgress")
fun changeWhenComplete(view: ProgressBar, tintOnIncomplete: Int, tintOnComplete: Int, determinateProgress: Int?) {
    view.animateProgress(determinateProgress ?: 0)
    if (determinateProgress ?: 0 >= 100) view.progressTintList = ColorStateList.valueOf(tintOnComplete)
    else view.progressTintList = ColorStateList.valueOf(tintOnIncomplete)
}

@BindingAdapter("hideGroupWhenLoading")
fun hideGroupWhenLoading(view: Group, isLoading: Boolean?) {
    val visibility: Int = view.visibility
    view.visibility = when (isLoading) {
        false, null -> visibility
        true -> View.GONE
    }
}

@BindingAdapter("goneUnless")
fun goneWhenDataLoaded(view: ProgressBar, response: Boolean?) {
    view.visibility = if (response == true) View.VISIBLE else View.GONE
}

@BindingAdapter("showWhileFetching")
fun showWhileFetchData(view: ProgressBar, response: List<*>?) {
    view.visibility = if (response == null) View.VISIBLE else View.GONE
}

@BindingAdapter("hideOnEmptyList")
fun showOnData(view: View, response: List<*>?) {
    view.visibility = if (response.isNullOrEmpty()) View.GONE else View.VISIBLE
}

@BindingAdapter("hideEmptyScreen", "showEmptyScreen", "disableEmptyScreen", requireAll = false)
fun showEmptyScreen(view: View, isLoading: Boolean, data: List<*>?, disableEmptyScreen: Boolean = false) {
    if (disableEmptyScreen) {
        view.visibility = View.GONE
        return
    }
    val visibility = if (isLoading || data.isNullOrEmpty().not()) View.GONE else View.VISIBLE
    if (view.visibility != visibility) view.post { view.visibility = visibility }
}

@BindingAdapter("onDataValid", "includeNetworkState", requireAll = false)
fun onDataValid(view: MaterialButton, onDataValid: Boolean?, includeNetworkState: Boolean?) {
    view.isEnabled = false
    if (includeNetworkState == true) return
    onDataValid?.let { if (view.isEnabled != it) view.isEnabled = it }
}

@BindingAdapter("onCountDownFinish")
fun onCountDownFinish(view: MaterialButton, dataToCheck: Long?) {
    view.isEnabled = false
    dataToCheck?.let {
        val shouldEnable: Boolean = it == 0L
        if (view.isEnabled != shouldEnable) view.isEnabled = shouldEnable
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setCountDownTimer")
fun setCountDownTimer(view: MaterialTextView, remainingTime: Long?) {
    remainingTime?.let {
        val secs: Int = (it / 1000).toInt()
        val min: Int = secs / 60
        val secsText: String = (if (secs < 10) "0" else "") + secs
        val minText: String = (if (min < 10) "0" else "") + min
        view.text = "${minText}:${secsText}"
    }
}

@BindingAdapter("editable", "setEndIconMode")
fun disableEditText(view: TextInputLayout, enable: Boolean?, @TextInputLayout.EndIconMode endIconMode: Int) {
    enable?.let {
        view.endIconMode = if (it) endIconMode else END_ICON_NONE
        view.editText?.isEnabled = it
        view.editText?.isFocusable = it
    }
}

@BindingAdapter("compareErrorMsg", "compareWith")
fun isMatching(view: TextInputLayout, errorMessage: String, compareWith: String?) {
    view.isErrorEnabled = true
    compareWith.let {
        if (view.editText?.text?.toString() != it ?: "") {
            view.error = errorMessage
        } else {
            view.error = null
        }
    }
    view.editText?.doOnTextChanged { text, _, _, _ ->
        if (text.toString() != compareWith) {
            view.error = errorMessage
        } else {
            view.error = null
        }
    }
}

@BindingAdapter("app:loadImage")
fun loadImage(view: ImageView, url: String?) {
    url?.let { Glide.with(view.context).load(it).into(view) }
}


@BindingAdapter("app:onCloseIconClick")
fun onCloseIconClick(view: Chip, body: () -> Unit) {
    view.setOnCloseIconClickListener {
        body()
    }
}

@BindingAdapter("app:handleMultipleClicks")
fun handleMultipleClicksListener(view: View, body: () -> Unit) {
    view.setOnClickListener {
        view.isEnabled = false
        body()
        Handler().postDelayed({
            if (view.parent != null) {
                view.isEnabled = true
            }
        }, 700)
    }
}

//@BindingAdapter("animateVisibility")
//fun animateVisibility(view: View, visibility: Int) {
//    val constraintLayout = view.parent as? ConstraintLayout
//    constraintLayout?.let { TransitionManager.beginDelayedTransition(it) }
//    view.visibility = visibility
//}

@IntDef(View.VISIBLE, View.INVISIBLE, View.GONE)
@Retention(AnnotationRetention.SOURCE)
annotation class Visibility

@BindingAdapter("app:animateVisibility")
fun animateVisibility(view: View, @Visibility visibility: Int) {
    view.apply {
        val shortAnimationDuration =
            context.resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
        when (visibility) {
            View.VISIBLE -> {
                // Set the content view to 0% opacity but visible, so that it is visible
                // (but fully transparent) during the animation.
                alpha = 0f
                view.visibility = View.VISIBLE

                // Animate the content view to 100% opacity, and clear any animation
                // listener set on the view.
                animate()
                    .alpha(1f)
                    .setDuration(shortAnimationDuration)
                    .setListener(null)
            }
            View.INVISIBLE, View.GONE -> {
                // Animate the loading view to 0% opacity. After the animation ends,
                // set its visibility to GONE as an optimization step (it won't
                // participate in layout passes, etc.)
                animate()
                    .alpha(0f)
                    .setDuration(shortAnimationDuration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            view.visibility = visibility
                        }
                    })
            }
        }
    }
}

@BindingAdapter("app:value")
fun setValue(view: NumberPicker, newValue: Int) {
    if (view.value != newValue) {
        view.value = newValue
    }
}

@InverseBindingAdapter(attribute = "app:value")
fun getValue(view: NumberPicker): Int {
    return view.value
}


@BindingAdapter(value = ["valueAttrChanged"])
fun setListener(view: NumberPicker, listener: InverseBindingListener?) {
    if (listener != null) {
        view.setOnValueChangedListener { _, _, _ ->
            listener.onChange()
        }
    }
}

@BindingAdapter("srcVector")
fun setSrcVector(view: AppCompatImageView, @DrawableRes drawable: Int) {
    view.setImageResource(drawable)
}

/**
 * Correct binding section
 */
@BindingAdapter("toggleVisibility")
fun toggleVisibility(view: View, condition: Boolean) {
    view.visibility = if (condition) View.VISIBLE else View.GONE
}

@BindingAdapter("stringVisibility")
fun stringVisibility(view: View, string: String?) {
    view.visibility = if (string.isNullOrBlank()) View.GONE else View.VISIBLE
}

@BindingAdapter("setShimmer")
fun setShimmer(view: ShimmerFrameLayout, condition: Boolean) {
    if (condition) {
        view.visibility = View.VISIBLE
        view.startShimmer()
    } else {
        view.visibility = View.GONE
        view.stopShimmer()
    }
}

/**
 * End binding adapter
 */

@BindingAdapter("onEndIconClicked", "endIconCustomDrawable", requireAll = false)
fun setOnEndIconClicked(
    view: TextInputLayout,
    action: () -> Unit,
    endIconCustomDrawable: Drawable?
) {
    val listener = View.OnClickListener {
        action()
    }
    view.addOnEndIconChangedListener { textInputLayout, previousIcon ->
        if (textInputLayout.endIconMode == TextInputLayout.END_ICON_CUSTOM) {
            view.endIconDrawable = endIconCustomDrawable
            view.setEndIconOnClickListener(listener)

        }
    }
}


@BindingAdapter("imageSpan", "imageSpanTint", "spanString", requireAll = false)
fun setImageSpanString(
    view: MaterialTextView,
    imageSpan: Drawable? = null,
    imageSpanTint: Int? = null,
    spanString: String?
) {
    imageSpanTint?.let {
        imageSpan?.setTint(it)
    }
    imageSpan?.setBounds(
        0,
        0,
        imageSpan.intrinsicWidth,
        imageSpan.intrinsicHeight
    )
    val spannedString = SpannableStringBuilder(spanString!!)
    spannedString.setSpan(
        ImageSpan(imageSpan!!, ImageSpan.ALIGN_BASELINE),
        spannedString.toString().indexOf("|"),
        spannedString.toString().indexOf("|") + 1,
        Spanned.SPAN_INCLUSIVE_EXCLUSIVE
    )

    view.text = spannedString
}

@BindingAdapter("filter")
fun bindAmountInputFilter(
    view: EditText,
    filter: String
) {
    view.filters = when (filter) {
        "onePointDecimal" -> arrayOf<InputFilter>(
            DecimalDigitInputFilter()
        )

        else -> arrayOf<InputFilter>(
            DecimalDigitInputFilter(Regex(filter))
        )
    }
}

private const val PERSON_NAME = "personName"
private const val DIGIT_ONLY = "digitOnly"
private const val MOBILE_NUMBER = "mobileNumber"
private const val EMAIL_ID = "email"