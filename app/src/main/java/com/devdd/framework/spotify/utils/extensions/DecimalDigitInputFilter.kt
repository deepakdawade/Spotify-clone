package com.medcords.rurallite.utils.extension

import android.text.Html
import android.text.InputFilter
import android.text.Spanned
import java.lang.Exception
import java.util.regex.Pattern

/**
 * Created by @author Deepak Dawade on 6/29/20.
 * Copyright (c) 2020 deepak.dawade@medcords.com All rights reserved.
 */
//"[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[1-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?"
val decimalReg = Regex("^\\d+(\\.[0-9]?)?$")

class DecimalDigitInputFilter(private val regexToMatch: Regex = decimalReg) : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val hasString = dest.toString()
        val enteredString = source.toString()
        val input: String =
            hasString.substring(0, dstart) + enteredString + hasString.substring(
                dend,
                hasString.length
            )

        if (input.isBlank()) return ""

        return if (input.length <= 5 && input.matches(regexToMatch))
            null
        else
            ""
    }

}