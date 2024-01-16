package com.treat.customer.utils

import android.util.Log
import com.treat.customer.domain.entities.Day
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object SingleLineCalender {
    private var year: Int = 0
    private var month: Int = 0

    fun generateDaysForAllYears(): List<String> {
        val daysList = mutableListOf<String>()
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("d", Locale.getDefault())

        val currentYear = calendar.get(Calendar.YEAR)
        val lastYear =
            currentYear + 5 // Change this value to generate days for a different number of years

        for (year in currentYear..lastYear) {
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, Calendar.JANUARY)
            calendar.set(Calendar.DAY_OF_MONTH, 1)

            while (calendar.get(Calendar.YEAR) == year) {
                daysList.add(sdf.format(calendar.time))
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        return daysList
    }

    fun generateDaysForCurrentMonth(monthYear: String): List<Day> {
        extractDate(monthYear);
        val daysList = mutableListOf<Day>()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val sdf = SimpleDateFormat("d", Locale.getDefault())
        val sdf1 = SimpleDateFormat("EEE", Locale.getDefault())
        val currentMonth = calendar.get(Calendar.MONTH)
        while (calendar.get(Calendar.MONTH) == currentMonth ) {
            daysList.add(Day(sdf1.format(calendar.time), sdf.format(calendar.time)))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return daysList
    }

    fun getCurrentMonth(): String {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        return sdf.format(calendar.time)
    }

    fun previousMonth(monthYear: String): String {
        extractDate(monthYear);
        val calendar = Calendar.getInstance()
        val calendar1 = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)
        return if (calendar.after(calendar1)) {
            calendar.add(Calendar.MONTH, -1)
            val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
            dateFormat.format(calendar.time)
        } else {
            monthYear

        }
    }

    fun nextMonth(monthYear: String): String {
        extractDate(monthYear)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)
        calendar.add(Calendar.MONTH, 1)
        val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun extractDate(monthYear: String) {
        val dateFormatSymbols = DateFormatSymbols.getInstance()
        val monthNames = dateFormatSymbols.months.map { it.lowercase() }
        val monthName = monthYear.split(" ")[0].trim().lowercase()
        month = monthNames.indexOf(monthName.lowercase(Locale.getDefault()))
        year = monthYear.split(" ")[1].trim().toInt()
    }
    fun isCurrentDay(day: String, monthYear: String):Boolean{
        val calendar = Calendar.getInstance()
       return (monthYear== getCurrentMonth() && day.toInt() == calendar.get(Calendar.DATE))
    }
    fun getCurrentDayPosition(monthYear: String):Int{
        val dayList = generateDaysForCurrentMonth(monthYear)
        val calendar = Calendar.getInstance()
        val day = dayList.find { it.dayNo.toInt() == calendar.get(Calendar.DATE) }
        return dayList.indexOf(day)

    }
    fun isDayBefore(day: String, monthYear: String):Boolean{
        extractDate(monthYear)
        val calendar = Calendar.getInstance()
        val calendar1 = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DATE, day.toInt())
        return calendar.time.before(calendar1.time)
    }
}