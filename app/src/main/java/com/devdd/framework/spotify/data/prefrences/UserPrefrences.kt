package com.devdd.framework.spotify.data.prefrences

import javax.inject.Singleton

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 4:56 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
@Singleton
interface UserPreferences {
    fun putString(key: String, value: String)
    fun getString(key: String, value: String): String
    fun putStringCommit(key: String, value: String)
    fun isLoggedIn(): Boolean
    fun setLoggedIn(isLoggedIn: Boolean)
    fun setLoggedOut(isLoggedOut: Boolean)
    fun isLoggedOut(): Boolean
    fun clearPreference()

    fun getBoolean(key: String): Boolean
    fun putBoolean(key: String, value: Boolean)
    fun removeKey(key: String)
}