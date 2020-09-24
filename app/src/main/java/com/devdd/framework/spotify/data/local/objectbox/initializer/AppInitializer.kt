package com.devdd.framework.spotify.data.local.objectbox.initializer

import android.app.Application

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 4:44 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
interface AppInitializer {
    fun init(application: Application)
}