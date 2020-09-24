package com.devdd.framework.spotify.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 5:11 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
object DateFormatter {
    /*
    *  Date patterns constants
    */

    const val INDIAN_STANDARD_FULL_TIME_FORMAT: String = "dd MMM yyyy, hh:mm a"
    const val SERVER_YEAR_DATE_FORMAT: String = "yyyy-MM-dd"
    const val MONTH_YEAR_FORMAT: String = "MMM yyyy"
    const val MONTH_FORMAT: String = "MMM"
    const val SERVER_DAY_DATE_FORMAT: String = "dd-MM-yyyy"

    val timeZoneUTC: TimeZone = TimeZone.getTimeZone("UTC")

    fun dayOfTheYear(): Int = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)

    fun timeToUTCFormattedDate(pattern: String, time: Long): Date {
        val simpleDateFormat: SimpleDateFormat = inputSimpleDateFormat(pattern)
        simpleDateFormat.timeZone = timeZoneUTC
        val date: String = simpleDateFormat.format(time)
        return simpleDateFormat.parse(date)
    }

    fun inputSimpleDateFormat(pattern: String): SimpleDateFormat =
        SimpleDateFormat(pattern, Locale.getDefault())

    fun inputSimpleDateFormatUTC(pattern: String): SimpleDateFormat {
        val simpleDateFormat: SimpleDateFormat = inputSimpleDateFormat(pattern)
        simpleDateFormat.timeZone = timeZoneUTC
        return simpleDateFormat
    }

    /*
   * @param time in seconds not milliseconds
   * @return 3 Sep 2019, 6:30 PM
   */
    fun indianStandardFullTimeFormat(time: Long): String {
        return inputSimpleDateFormat(INDIAN_STANDARD_FULL_TIME_FORMAT).format(Date(time * 1000))
    }

    /*
   * @param time in seconds not milliseconds
   */
    fun userProvidedFormat(time: Long, format : String): String {
        return inputSimpleDateFormat(format).format(Date(time * 1000))
    }

    /*
   * @param time in milliseconds
   * @return 03-12-2019
   */
    fun serverDayDateFormat(time: Long): String {
        return inputSimpleDateFormat(INDIAN_STANDARD_FULL_TIME_FORMAT).format(Date(time))
    }

    /*
   *  @return tomorrow UTC date
   */
    fun tomorrowUTCDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis += 86400000
        return timeToUTCFormattedDate(SERVER_DAY_DATE_FORMAT, calendar.timeInMillis)
    }

    /*
  *  @return today UTC date
  */
    fun todayUTCDate(): Date {
        val timeZone: TimeZone = TimeZone.getTimeZone("UTC")
        val calendar = Calendar.getInstance(timeZone)
        return timeToUTCFormattedDate(SERVER_DAY_DATE_FORMAT, calendar.timeInMillis)
    }

    /*
  *  @return tomorrow UTC date
  */
    fun yesterdayUTCDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis -= 86400000
        return timeToUTCFormattedDate(SERVER_DAY_DATE_FORMAT, calendar.timeInMillis)
    }

    fun resetTimeInDate(date: String, pattern: String): Date {
        val simpleDateFormat = inputSimpleDateFormatUTC(pattern)
        return simpleDateFormat.parse(date)
    }


}