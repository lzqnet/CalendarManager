package com.zql.lzqcalendar

import android.content.ContentResolver
import android.content.Context
import android.provider.CalendarContract
import android.util.Log

object CalendarManager {
    fun addDefaultCalendarEventAndReminder(event: Event, context: Context): Boolean {
        val contentResolver = context.contentResolver
        val calendarWrapper = CalendarWrapper()
        val calendar = Calendar.DefaultBuilder(context).build()
        val accountList = queryCalendar(calendar, context)
        var calendarId: Long?

        if (accountList == null || accountList.size <= 0) {
            Log.d("lzqtest", "CalendarManager.addDefaultCalendarEventAndReminder: has no calender,add it=$calendar ")
            calendarId = calendarWrapper
                .addCalendar(calendar, contentResolver)
        } else {
            Log.d("lzqtest", "CalendarManager.addDefaultCalendarEventAndReminder: already exist calendar=$accountList ")
            calendarId = accountList[0].ACCOUNT_ID
        }
        //add event
        if (calendarId != null && calendarId >= 0) {
            event.CALENDAR_ID = calendarId
            val eventWrapper = EventWrapper()
            var eventList = eventWrapper.queryEvent(event, contentResolver)
            var eventId: Long?
            if (eventList != null && eventList.size > 0) {
                Log.d("lzqtest", "CalendarManager.addDefaultCalendarEventAndReminder: already exist event ${eventList} ")
                eventId = eventList[0].ID
            } else {
                Log.d("lzqtest", "CalendarManager.addDefaultCalendarEventAndReminder: has no event ,add it  ")
                eventId = eventWrapper.addEvent(event, contentResolver)
            }
            //add reminder
            if (eventId != null && eventId >= 0) {
                val reminder = Reminder.Builder(eventId, 0, CalendarContract.Reminders.METHOD_ALERT)
                val remindId = CalendarManager.addReminder(reminder.build(), context)
                Log.d("lzqtest", "CalendarManager.addDefaultCalendarEventAndReminder: remindId=$remindId ")
            } else {
                Log.w("lzqtest", "CalendarManager.addDefaultCalendarEventAndReminder: eventId is invalid eventId=$eventId ")
                return false
            }

        } else {
            Log.w("lzqtest", "CalendarManager.addDefaultCalendarEventAndReminder: calendarId is invalid calendarId=$calendarId ")
            return false
        }

        return true

    }

    fun addCalendar(calendar: Calendar, context: Context): Long? {
        val contentResolver = context.contentResolver
        val calendarWrapper = CalendarWrapper()
        val id = calendarWrapper
            .addCalendar(calendar, contentResolver)
        return id
    }

    fun queryCalendar(calendar: Calendar, context: Context): ArrayList<Calendar> {
        val contentResolver = context.contentResolver
        val calendarWrapper = CalendarWrapper()
        val list = calendarWrapper
            .queryCalendar(calendar.ACCOUNT_NAME, calendar.ACCOUNT_TYPE, calendar.OWNER_ACCOUNT, contentResolver)
        Log.d("lzqtest", "CalendarManager.queryCalendar: id=$list ")
        return list
    }

    fun addEvent(event: Event, context: Context): Long {
        val contentResolver = context.contentResolver
        val eventWrapper = EventWrapper()
        val id = eventWrapper.addEvent(event, contentResolver)
        return id

    }

    fun deleteEvent(id: Int, context: Context): Int {
        val contentResolver = context.contentResolver
        val eventWrapper = EventWrapper()
        val ret = eventWrapper.deleteEvent(id, contentResolver)
        return ret

    }

    fun addReminder(reminder: Reminder, context: Context): Long? {
        val contentResolver = context.contentResolver
        val reminderWrapper = ReminderWrapper()
        val ret = reminderWrapper.addReminder(reminder, contentResolver)
        return ret

    }
    fun deleteCalendar(id: Int, context: Context): Int {
        val contentResolver = context.contentResolver
        val calendarWrapper = CalendarWrapper()
        return calendarWrapper.deleteCalendar(id, contentResolver)

    }


    fun deleteCalendarByAccount(accountName: String, accountType: String, context: Context): Int {
        val contentResolver = context.contentResolver
        val calendarWrapper = CalendarWrapper()
        return calendarWrapper.deleteCalendarByAccount(accountName, accountType, contentResolver)

    }

    fun addCalendarEventAndReminders() {

    }

    fun deleteReminder() {

    }

    fun deleteEvent() {

    }

}