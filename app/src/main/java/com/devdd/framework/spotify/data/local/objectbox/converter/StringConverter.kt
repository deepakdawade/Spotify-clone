package com.devdd.framework.spotify.data.local.objectbox.converter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.objectbox.converter.PropertyConverter

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 4:52 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
class StringConverter : PropertyConverter<List<String>?, String> {
    override fun convertToEntityProperty(databaseValue: String?): List<String>? {
        if (databaseValue == null) return null
        return Gson().fromJson(databaseValue, object : TypeToken<List<String>>() {}.type)
    }

    override fun convertToDatabaseValue(entityProperty: List<String>?): String? {
        if (entityProperty.isNullOrEmpty()) return null
        return Gson().toJson(entityProperty)
    }
}
