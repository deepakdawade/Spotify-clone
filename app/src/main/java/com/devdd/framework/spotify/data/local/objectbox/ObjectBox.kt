package com.devdd.framework.spotify.data.local.objectbox

import android.content.Context
import com.devdd.framework.spotify.data.local.entities.MyObjectBox
import io.objectbox.BoxStore

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 4:40 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
object ObjectBox {

    lateinit var boxStore: BoxStore

    fun init(applicationContext: Context) {
        boxStore = MyObjectBox.builder().androidContext(applicationContext).build()
    }

    fun panicState(applicationContext: Context): Boolean {
        boxStore.close()
        val result = BoxStore.deleteAllFiles(applicationContext, null)
        init(applicationContext)
        return result
    }
}
