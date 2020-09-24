package com.devdd.framework.spotify.utils.extensions

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 7:53 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/

fun <T> Collection<T>.elementsOrNull(): Collection<T>? {
    return if (isNullOrEmpty()) null else this
}

fun <T> Collection<T>.listOrNull(): List<T>? {
    return if (isNullOrEmpty()) null else this.toList()
}
internal fun String.isInRange(low: Long, high: Long): Boolean {
    if (isNullOrBlank()) {
        return false
    } else {
        toLongOrNull()?.let { value ->
            if (value in low..high) {
                return true
            }
        }
    }
    return false
}
