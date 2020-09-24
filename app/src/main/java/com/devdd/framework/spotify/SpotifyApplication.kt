package com.devdd.framework.spotify

import android.app.Application
import com.bumptech.glide.RequestManager
import com.devdd.framework.spotify.data.local.objectbox.ObjectBox
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 3:48 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
@HiltAndroidApp
class SpotifyApplication : Application() {
    @Inject lateinit var glide:RequestManager
    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}