package com.devdd.framework.spotify.utils

import android.util.Patterns

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 5:12 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/

internal val mobileNumberRegex = Regex("^[6-9]\\d{9}$")

internal val otpRegex = Regex("\\d{4}")

internal val digitsRegex = Regex("\\d+")

//refer https://www.regular-expressions.info/unicode.html#prop
/**
 * p[L] verifies that if a character belongs to letter category of any language.
 * p[M] specifies the letter that is used in combination without any extra space.
 * empty space in square brackets specifies that space is allowed in between
 */
internal val personNameRegex = Regex("(?:[\\p{L}\\p{M}] ?)+$")


internal val emailRegex = Patterns.EMAIL_ADDRESS.toRegex()

