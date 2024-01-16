package com.treat.customer.utils

import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DateTimePickerManager {
    private fun getDate(
        pattern: String,
        selectedYear: Int,
        selectedMonth: Int,
        selectedDay: Int
    ): String {
        val calender = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat(pattern)
        calender.set(Calendar.YEAR, selectedYear)
        calender.set(Calendar.MONTH, selectedMonth)
        calender.set(Calendar.DATE, selectedDay)
        return simpleDateFormat.format(calender.time)

    }


    private fun getTime(
        pattern: String,
        selectedHour: Int,
        selectedMinute: Int,
        selectedSecond: Int
    ): String {
        val calender = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat(pattern)
        calender.set(Calendar.HOUR, selectedHour)
        calender.set(Calendar.MINUTE, selectedMinute)
        calender.set(Calendar.SECOND, selectedSecond)
        return simpleDateFormat.format(calender.time)


    }


    fun getFormattedDate(pattern: String, originalPattern: String, date: String?): String {
//        val originalDateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy",Locale.US)
        val originalDateFormat = SimpleDateFormat(originalPattern, Locale.ENGLISH)
        val parsedDate = originalDateFormat.parse(date)
        val simpleDateFormat = SimpleDateFormat(pattern,Locale.ENGLISH)
        return simpleDateFormat.format(parsedDate)
    }



    fun getCurrentDate(pattern: String): String {
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat(pattern,Locale.ENGLISH)
        return simpleDateFormat.format(calendar.time)
    }


}