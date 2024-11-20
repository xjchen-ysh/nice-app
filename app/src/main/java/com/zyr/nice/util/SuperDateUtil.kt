package com.zyr.nice.util

import java.util.Calendar

object SuperDateUtil {

    fun currentYear(): Int {
        return Calendar.getInstance().get(Calendar.YEAR)
    }

    fun currentMonth(): Int {
        return Calendar.getInstance().get(Calendar.MONTH) + 1
    }

    fun currentDay(): Int {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    }

    fun currentYYMMDD(): String {
        return "${currentYear()}-${currentMonth()}-${currentDay()}"
    }

}