package com.devdd.framework.spotify.utils.extensions

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 7:53 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
internal fun String.isInRange(low: Int, high: Int): Boolean {
    if (isNullOrBlank()) {
        return false
    } else {
        toIntOrNull()?.let { value ->
            if (value in low..high) {
                return true
            }
        }
    }
    return false
}

private fun String.removeISDCode(): String {
    var number: String = this

    // removes space if any
    if (number.contains(" ")) number = replace(" ", "")

    //removes
    if (number.startsWith("+")) number = replace("+91", "")

    return number
}
