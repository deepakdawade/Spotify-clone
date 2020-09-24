package com.devdd.framework.spotify.utils.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.devdd.framework.spotify.utils.result.Result

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 7:53 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/

fun <T> MutableLiveData<T>.postValueIfNew(newValue: T) {
    if (value != newValue)
        postValue(newValue)
}

var <T> MutableLiveData<T>.valueIfNew: T?
    get() = value
    set(newValue) {
        if (value != newValue)
            value = newValue
    }

fun <T> MutableLiveData<Result<T>>.updateNetworkState(): MutableLiveData<Result<T>> {
    postValue(Result.Loading)
    return this
}

/** Uses `Transformations.map` on a LiveData */
fun <X, Y> LiveData<X>.map(body: (X) -> Y): LiveData<Y> {
    return Transformations.map(this, body)
}
