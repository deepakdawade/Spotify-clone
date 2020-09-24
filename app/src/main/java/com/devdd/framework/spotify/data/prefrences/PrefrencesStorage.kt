package com.devdd.framework.spotify.data.prefrences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.devdd.framework.spotify.utils.extensions.toDataClass
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 4:55 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
interface PreferenceStorage {
    var eventCount: Int
    fun isFeatureNew(featureName: String): Boolean
    fun clear()
}

@Singleton
class PreferenceStorageImpl @Inject constructor(
    @ApplicationContext context: Context,
) : PreferenceStorage {

    private val prefs: Lazy<SharedPreferences> = lazy {
        // Lazy to prevent IO access to main thread.
        context.applicationContext.getSharedPreferences(
            PREFS_NAME, Context.MODE_PRIVATE
        )
    }

    override var eventCount: Int
        get() = prefs.value.getInt(PREFS_EVENT_COUNT, 0)
        set(value) = prefs.value.edit {
            putInt(PREFS_EVENT_COUNT, value)
        }


    override fun isFeatureNew(featureName: String): Boolean {
        val features =
            prefs.value.getString(PREFS_NEW_FEATURES, null)?.toDataClass<List<String>>()
        return true
    }


    override fun clear() {
        prefs.value.edit(commit = true) {
            clear()
        }
    }

    companion object {
        const val PREFS_NAME = "aayu_preferences"
        const val PREFS_EVENT_COUNT = "eventCount"
        const val PREFS_NEW_FEATURES = "newFeatures"
    }
}
