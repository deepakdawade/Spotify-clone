package com.devdd.framework.spotify.utils.result

/**
 * Created by @author Deepak Dawade on 10/5/2020 at 12:59 AM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {

        fun <T> success(data: T?) = Resource(Status.SUCCESS, data, null)

        fun <T> error(message: String?, data: T?) = Resource(Status.ERROR, data, message)

        fun <T> loading(data: T?) = Resource(Status.LOADING, data, null)

    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}
