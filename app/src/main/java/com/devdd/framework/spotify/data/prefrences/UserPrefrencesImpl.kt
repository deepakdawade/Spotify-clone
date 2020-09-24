package com.devdd.framework.spotify.data.prefrences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 4:58 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
@Singleton
class UserPreferencesImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    val prefs: SharedPreferences
) : UserPreferences {

    companion object {
        // STATES FOR PREF
        const val STATE_LOADING = "LOADING"
        const val STATE_SUCCESS = "SUCCESS"
        const val STATE_FAILURE = "FAILURE"

        const val IS_LOGGED_IN = "isLoggedIn"
        const val IS_LOGGED_OUT = "isLoggedOut"
    }


    override fun putBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    override fun getBoolean(key: String): Boolean {
        return prefs.getBoolean(key, false)
    }

    override fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    @SuppressLint("ApplySharedPref")
    override fun putStringCommit(key: String, value: String) {
        prefs.edit().putString(key, value).commit()
    }

    override fun getString(key: String, value: String): String {
        return prefs.getString(key, value)!!
    }

    override fun isLoggedIn(): Boolean {
        return prefs.getBoolean(IS_LOGGED_IN, false)
    }

    @SuppressLint("ApplySharedPref")
    override fun setLoggedIn(isLoggedIn: Boolean) {
        prefs.edit().putBoolean(IS_LOGGED_IN, isLoggedIn).commit()
    }

    @SuppressLint("ApplySharedPref")
    override fun setLoggedOut(isLoggedOut: Boolean) {
        prefs.edit().putBoolean(IS_LOGGED_OUT, isLoggedOut).commit()
    }

    override fun isLoggedOut(): Boolean = prefs.getBoolean(IS_LOGGED_OUT, true)

    override fun clearPreference() {
        prefs.edit().clear().apply()
    }

    override fun removeKey(key: String) {
        return prefs.edit().remove(key).apply()
    }
}