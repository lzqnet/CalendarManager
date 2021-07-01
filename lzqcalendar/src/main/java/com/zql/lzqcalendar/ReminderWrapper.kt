package com.zql.lzqcalendar

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log

class ReminderWrapper {
    fun addReminder(reminder: Reminder, resolver: ContentResolver): Long? {
        val reminders = queryMinderByEventId(reminder.EVENT_ID, resolver)
        if (reminders != null && reminders.size > 0) {
            for (data in reminders) {
                if (data.MINUTES == (reminder.MINUTES)
                        && data.METHOD == reminder.METHOD) {
                            Log.d("lzqtest", "ReminderWrapper.addReminder: alread exist reminder=$reminders ")
                    return CalendarStatus.ALREADY_EXIST.value()
                }
            }
        }
        if (reminder.EVENT_ID == null) {
            Log.d("lzqtest", "ReminderWrapper.addReminder: EVENT_ID is null ")
            return CalendarStatus.PARAMETER_ERROR.value()
        }
        val contentValues = ContentValues()
        contentValues.put(CalendarContract.Reminders.EVENT_ID, reminder.EVENT_ID)

        if (reminder.METHOD != null) {
            contentValues.put(CalendarContract.Reminders.METHOD, reminder.METHOD)
        }
        if (reminder.MINUTES != null) {
            contentValues.put(CalendarContract.Reminders.MINUTES, reminder.MINUTES)
        }
Log.d("lzqtest", "ReminderWrapper.addReminder: add reminder now ")
        val ret= resolver.insert(CalendarContract.Reminders.CONTENT_URI, contentValues)
        Log.d("lzqtest", "ReminderWrapper.addReminder: result ret=$ret ")
        return if(ret==null) CalendarStatus.FAIL.value() else ContentUris.parseId(ret)
    }

    fun queryReminder(id: Int?, resolver: ContentResolver): ArrayList<Reminder> {
        val reminders = ArrayList<Reminder>()
        if (id == null) {
            return reminders
        }
        val selection: String = "(${CalendarContract.Reminders._ID} = ?)"
        val selectionArgs: Array<String> = arrayOf(
                id.toString()
        )

        resolver.query(
                CalendarContract.Reminders.CONTENT_URI,
                null, selection, selectionArgs, null
        ).use { cursor ->
            val ret = readReminder(cursor)
            reminders.addAll(ret)
            cursor?.close()
            return reminders
        }
    }


    fun queryMinderByEventId(eventId: Long?, resolver: ContentResolver): ArrayList<Reminder> {
        val reminders = ArrayList<Reminder>()
        if (eventId == null) {
            return reminders
        }
        val selection: String = "(${CalendarContract.Reminders.EVENT_ID} = ?)"
        val selectionArgs: Array<String> = arrayOf(
                eventId.toString()
        )

        resolver.query(
                CalendarContract.Reminders.CONTENT_URI,
                null, selection, selectionArgs, null
        ).use { cursor ->
            val ret = readReminder(cursor)
            reminders.addAll(ret)
            cursor?.close()
            return reminders
        }
    }

    fun readReminder(cursor: Cursor?): ArrayList<Reminder> {
        val reminders = ArrayList<Reminder>()

        if (null == cursor) {
            return reminders
        }


        if (!cursor.moveToFirst()) {
            return reminders

        }
        do {
            var reminder: Reminder = Reminder()
            reminder._ID =
                cursor.getLong(cursor.getColumnIndex(CalendarContract.Reminders._ID))
            reminder.EVENT_ID =
                cursor.getLong(cursor.getColumnIndex(CalendarContract.Reminders.EVENT_ID))
            reminder.MINUTES =
                cursor.getLong(cursor.getColumnIndex(CalendarContract.Reminders.MINUTES))
            reminder.METHOD =
                cursor.getInt(cursor.getColumnIndex(CalendarContract.Reminders.METHOD))
            reminders.add(reminder)
        } while (cursor.moveToNext())
        return reminders
    }

    fun deleteRemindById(id: Int?, resolver: ContentResolver): Int {
        if (id == null) {
            return -1
        }
        val selection: String = "(${CalendarContract.Reminders._ID} = ?)"
        val selectionArgs: Array<String> = arrayOf(
                id.toString()
        )
        return resolver.delete(
                CalendarContract.Reminders.CONTENT_URI, selection, selectionArgs)
    }

    fun deleteReminderByEventId(eventId: Int?, resolver: ContentResolver): Int {
        if (eventId == null) {
            return -1
        }
        val selection: String = "(${CalendarContract.Reminders.EVENT_ID} = ?)"
        val selectionArgs: Array<String> = arrayOf(
                eventId.toString()
        )
        return resolver.delete(
                CalendarContract.Reminders.CONTENT_URI, selection, selectionArgs)
    }


}