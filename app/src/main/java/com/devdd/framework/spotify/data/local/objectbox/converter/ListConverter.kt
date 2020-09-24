package com.devdd.framework.spotify.data.local.objectbox.converter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.objectbox.converter.PropertyConverter

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 4:51 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
class ListConverter<T> : PropertyConverter<List<T>?, String> {
    override fun convertToEntityProperty(databaseValue: String?): List<T>? {
        if (databaseValue == null) return null
        return Gson().fromJson(databaseValue, object : TypeToken<List<T>>() {}.type)
    }

    override fun convertToDatabaseValue(entityProperty: List<T>?): String? {
        if (entityProperty.isNullOrEmpty()) return null
        return Gson().toJson(entityProperty)
    }
}