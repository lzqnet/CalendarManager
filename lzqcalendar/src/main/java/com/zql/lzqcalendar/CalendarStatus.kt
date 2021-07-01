package com.zql.lzqcalendar

enum class CalendarStatus(private val value: Long) {
        ALREADY_EXIST(-100L),
         SUCCESS(0),
         PERMISSION_DENY(-101L),
         FAIL(-102L),
         PARAMETER_ERROR(-103L);

    fun value():Long{
        return value
    }


}