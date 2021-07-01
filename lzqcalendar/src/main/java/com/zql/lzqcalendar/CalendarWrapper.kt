package com.zql.lzqcalendar

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.provider.CalendarContract
import android.provider.CalendarContract.Calendars
import android.util.Log


class CalendarWrapper {

    fun addCalendar(calendar: Calendar, resolver: ContentResolver): Long {
        val calendars = queryCalendar(calendar.ACCOUNT_NAME, calendar.ACCOUNT_TYPE, calendar.OWNER_ACCOUNT, resolver)
        if (calendars != null && calendars.size > 0) {
            Log.d("lzqtest", "CalendarWrapper.addCalendar: already has a same calendar "+calendars)
            return CalendarStatus.ALREADY_EXIST.value()
        }
        val contentValues = ContentValues()
        if (!calendar.ACCOUNT_TYPE.isNullOrEmpty()) {
            contentValues.put(Calendars.ACCOUNT_TYPE, calendar.ACCOUNT_TYPE)
        }
        if (!calendar.ACCOUNT_NAME.isNullOrEmpty()) {
            contentValues.put(Calendars.ACCOUNT_NAME, calendar.ACCOUNT_NAME)
        }
        if (!calendar.NAME.isNullOrEmpty()) {
            contentValues.put(Calendars.NAME, calendar.NAME)
        }
        if (calendar.DIRTY != null) {
            contentValues.put(Calendars.DIRTY, calendar.DIRTY)
        }
        if (!calendar.MUTATORS.isNullOrEmpty()) {
            contentValues.put(Calendars.MUTATORS, calendar.MUTATORS)
        }
        if (!calendar.CALENDAR_DISPLAY_NAME.isNullOrEmpty()) {
            contentValues.put(Calendars.CALENDAR_DISPLAY_NAME, calendar.CALENDAR_DISPLAY_NAME)
        }
        if (calendar.CALENDAR_COLOR != null) {
            contentValues.put(Calendars.CALENDAR_COLOR, calendar.CALENDAR_COLOR)
        }
        if (calendar.CALENDAR_ACCESS_LEVEL != null) {
            contentValues.put(Calendars.CALENDAR_ACCESS_LEVEL, calendar.CALENDAR_ACCESS_LEVEL)
        }
        if (!calendar.CALENDAR_LOCATION.isNullOrEmpty()) {
            contentValues.put(Calendars.CALENDAR_LOCATION, calendar.CALENDAR_LOCATION)
        }
        if (!calendar.CALENDAR_TIME_ZONE.isNullOrEmpty()) {
            contentValues.put(Calendars.CALENDAR_TIME_ZONE, calendar.CALENDAR_TIME_ZONE)
        }
        if (!calendar.OWNER_ACCOUNT.isNullOrEmpty()) {
            contentValues.put(Calendars.OWNER_ACCOUNT, calendar.OWNER_ACCOUNT)
        }
        if (calendar.MAX_REMINDERS != null) {
            contentValues.put(Calendars.MAX_REMINDERS, calendar.MAX_REMINDERS)
        }
        if (!calendar.ALLOWED_REMINDERS.isNullOrEmpty()) {
            contentValues.put(Calendars.ALLOWED_REMINDERS, calendar.ALLOWED_REMINDERS)
        }
        if (!calendar.ALLOWED_AVAILABILITY.isNullOrEmpty()) {
            contentValues.put(Calendars.ALLOWED_AVAILABILITY, calendar.ALLOWED_AVAILABILITY)
        }
        if (!calendar.ALLOWED_ATTENDEE_TYPES.isNullOrEmpty()) {
            contentValues.put(Calendars.ALLOWED_ATTENDEE_TYPES, calendar.ALLOWED_ATTENDEE_TYPES)
        }
        var uri = Calendars.CONTENT_URI
        uri= uri.buildUpon()
            .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
            .appendQueryParameter(Calendars.ACCOUNT_NAME, calendar.ACCOUNT_NAME)
            .appendQueryParameter(Calendars.ACCOUNT_TYPE,
                    calendar.ACCOUNT_TYPE)
            .build()
        Log.d("lzqtest", "CalendarWrapper.addCalendar: insert calendar now ")
         val ret=resolver.insert(uri, contentValues)
        Log.d("lzqtest", "CalendarWrapper.addCalendar: insert result ret=$ret ")
        return if (ret!=null) ContentUris.parseId(ret) else CalendarStatus.FAIL.value()

    }

    fun queryCalendar(accountName: String?, accountType: String?, ownerAccount: String?, resolver: ContentResolver): ArrayList<Calendar> {
        if (accountName.isNullOrEmpty() || accountType.isNullOrEmpty() || ownerAccount.isNullOrEmpty()) {
            Log.w("lzqtest", "CalendarWrapper.queryCalendar: accountName=$accountName " +
                    "accountType=$accountType ownerAccount=$ownerAccount is invalid")
            return ArrayList()
        }
        val selection: String = "((${CalendarContract.Calendars.ACCOUNT_NAME} = ?) AND (" +
                "${CalendarContract.Calendars.ACCOUNT_TYPE} = ?) AND (" +
                "${CalendarContract.Calendars.OWNER_ACCOUNT} = ?))"
        val selectionArgs: Array<String> = arrayOf(
                accountName,
                accountType,
                ownerAccount
        )

        resolver.query(
                CalendarContract.Calendars.CONTENT_URI,
                null, selection, selectionArgs, null
        ).use { cursor ->
            val calendars = ArrayList<Calendar>()

            // 不存在日历账户
            if (null == cursor) {
                return calendars
            }


            if (!cursor.moveToFirst()) {
                cursor.close()
                return calendars

            }
            do {
                var calendar: Calendar = Calendar()
                calendar.ACCOUNT_ID =
                    cursor.getLong(cursor.getColumnIndex(CalendarContract.Calendars._ID))
                calendar.OWNER_ACCOUNT =
                    cursor.getString(cursor.getColumnIndex(Calendars.OWNER_ACCOUNT))
                calendar.ACCOUNT_TYPE =
                    cursor.getString(cursor.getColumnIndex(Calendars.ACCOUNT_TYPE))
                calendar.MUTATORS = cursor.getString(cursor.getColumnIndex(Calendars.MUTATORS))
                calendar.CALENDAR_DISPLAY_NAME =
                    cursor.getString(cursor.getColumnIndex(Calendars.CALENDAR_DISPLAY_NAME))
                calendar.CALENDAR_LOCATION =
                    cursor.getString(cursor.getColumnIndex(Calendars.CALENDAR_LOCATION))
                calendar.CALENDAR_TIME_ZONE =
                    cursor.getString(cursor.getColumnIndex(Calendars.CALENDAR_TIME_ZONE))
                calendar.ACCOUNT_NAME =
                    cursor.getString(cursor.getColumnIndex(Calendars.ACCOUNT_NAME))
                calendar.ALLOWED_REMINDERS =
                    cursor.getString(cursor.getColumnIndex(Calendars.ALLOWED_REMINDERS))
                calendar.ALLOWED_AVAILABILITY =
                    cursor.getString(cursor.getColumnIndex(Calendars.ALLOWED_AVAILABILITY))
                calendar.ALLOWED_ATTENDEE_TYPES =
                    cursor.getString(cursor.getColumnIndex(Calendars.ALLOWED_ATTENDEE_TYPES))
                calendar.DIRTY =
                    cursor.getInt(cursor.getColumnIndex(CalendarContract.Calendars.DIRTY))
                calendar.CALENDAR_COLOR =
                    cursor.getInt(cursor.getColumnIndex(CalendarContract.Calendars.CALENDAR_COLOR))
                calendar.MAX_REMINDERS =
                    cursor.getInt(cursor.getColumnIndex(CalendarContract.Calendars.MAX_REMINDERS))

                calendars.add(calendar)
            } while (cursor.moveToNext())
            cursor.close()
            return calendars
        }
    }

    fun deleteCalendar(id: Int, resolver: ContentResolver): Int {
        val selection: String = "(${CalendarContract.Calendars._ID} = ?)"
        val selectionArgs: Array<String> = arrayOf(
                id.toString()
        )
        return resolver.delete(
                CalendarContract.Calendars.CONTENT_URI, selection, selectionArgs)
    }

    fun deleteCalendarByAccount(accountName: String, accountType: String, resolver: ContentResolver): Int {
        val selection: String = "((${CalendarContract.Calendars.ACCOUNT_NAME} = ?) AND (" +
                "${CalendarContract.Calendars.ACCOUNT_TYPE} = ?))"
        val selectionArgs: Array<String> = arrayOf(
                accountName,
                accountType,
        )

        return resolver.delete(
                CalendarContract.Calendars.CONTENT_URI, selection, selectionArgs)
    }
}