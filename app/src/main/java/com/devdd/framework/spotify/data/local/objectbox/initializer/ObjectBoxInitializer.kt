package com.devdd.framework.spotify.data.local.objectbox.initializer

import android.app.Application
import android.content.Context
import com.devdd.framework.spotify.data.local.objectbox.ObjectBox
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 4:49 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
class ObjectBoxInitializer @Inject constructor(
    @ApplicationContext private val context: Context
) : AppInitializer {

    init {
        ObjectBox.init(context)
    }

    override fun init(application: Application) {
        /*Timber.i("Timber is initializing")
        ObjectBox.init(application)*/
    }
}
