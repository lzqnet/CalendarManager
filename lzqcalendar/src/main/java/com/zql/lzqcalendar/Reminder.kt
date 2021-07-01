package com.zql.lzqcalendar

data class Reminder(
        var _ID: Long? = null,
        var EVENT_ID: Long? = null,
        var MINUTES: Long? = null,
        var METHOD: Int? = null
){
    class Builder(var EVENT_ID: Long? = null,
            var MINUTES: Long? = null,
            var METHOD: Int? = null){

        fun build(): Reminder{
            return Reminder(EVENT_ID=EVENT_ID,
                    MINUTES=MINUTES,METHOD=METHOD
            )
        }

    }
}
