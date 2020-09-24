package com.devdd.framework.spotify.utils.result

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CancellationException

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 11:08 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
/**
 * [Success.data] if [Result] is of type [Success]
 */
fun <T> Result<T>.successOr(fallback: T): T {
    return (this as? Result.Success<T>)?.data ?: fallback
}

inline fun <T> Result<T>.onServerError(task: ServerException.() -> Unit): Result<T> {
    if (this is Result.Error && exception is ServerException) {
        this.exception.task()
    }
    return this
}

inline fun <reified T> Result<T>.onSuccess(task: T.() -> Unit): Result<T> {
    if (this is Result.Success) {
        task(data)
    }
    return this
}

fun <T> Result<T>.updateLoadingState(liveData: MutableLiveData<Boolean>): Result<T> {
    liveData.postValue(false)
    return this
}

inline fun <T> Result<T>.onError(task: Result.Error.() -> Unit): Result<T> {
    if (this is Result.Error && (exception is ServerException).not()) {
        task()
    }
    return this
}

inline fun <T> Result<T>.onErrorWithoutCancellation(task: Result.Error.() -> Unit): Result<T> {
    if (this is Result.Error && (exception is ServerException).not() && (exception is CancellationException).not()) {
        task()
    }
    return this
}


val <T> Result<T>.data: T?
    get() = (this as? Result.Success)?.data

/**
 * Updates value of [liveData] if [Result] is of type [Success]
 */
inline fun <reified T> Result<T>.updateOnSuccess(liveData: MutableLiveData<T>) {
    if (this is Result.Success) {
        liveData.value = data
    }
}

inline fun <reified T> Result<T>.onResult(task: () -> Unit): Result<T> {
    task()
    return this
}

inline fun <reified T> Result<T>.updateOnError(liveData: MutableLiveData<Result<T>>) {
    if (this is Result.Error) {
        liveData.postValue(this)
    }
}

inline fun <reified T> Result<T>.updateNetworkState(liveData: MutableLiveData<Boolean>): Result<T> {
    liveData.postValue(data is Result.Loading)
    return this
}
