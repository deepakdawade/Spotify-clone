package com.devdd.framework.spotify.data.local.objectbox.converter

import io.objectbox.converter.PropertyConverter
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 4:50 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
class DateConverter : PropertyConverter<String?, Date> {
    override fun convertToEntityProperty(databaseValue: Date?): String? {
        if (databaseValue == null) return null
        val inputSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return inputSimpleDateFormat.format(databaseValue)
    }

    override fun convertToDatabaseValue(entityProperty: String?): Date? {
        if (entityProperty.isNullOrEmpty()) return null
        val inputSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return inputSimpleDateFormat.parse(entityProperty)

    }
}
