package com.devdd.framework.spotify.data.local.objectbox.converter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.objectbox.converter.PropertyConverter

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 4:51 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
class IntConverter  : PropertyConverter<List<Int>, String> {
    override fun convertToEntityProperty(databaseValue: String?): List<Int>? {
        if (databaseValue == null) return null
        return Gson().fromJson(databaseValue, object : TypeToken<List<Int>>() {}.type)
    }

    override fun convertToDatabaseValue(entityProperty: List<Int>): String {
        return Gson().toJson(entityProperty)
    }
}
