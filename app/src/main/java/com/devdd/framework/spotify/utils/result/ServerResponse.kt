package com.medcords.rurallite.base.result

import com.devdd.framework.spotify.utils.result.ServerException
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ServerResponse<T>(
    @Expose
    @SerializedName("status")
    var status: Int,

    @Expose
    @SerializedName("errCode")
    var errorCode: Int,

    @Expose
    @SerializedName("msg")
    var message: String = "",

    @Expose
    @SerializedName("data")
    var data: T
)

fun <T> ServerResponse<T>.dataOrThrowException(executeOnSuccess: T.() -> Unit = {}): T {
    return if (status == 0) data.also(executeOnSuccess) else throw ServerException(
        errorCode,
        message
    )
}
