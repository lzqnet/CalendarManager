package com.zql.lzqcalendar

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.provider.CalendarContract
import android.util.Log

class EventWrapper {

    fun addEvent(event: Event, resolver: ContentResolver): Long {
        val events = queryEventByCalendarId(event.CALENDAR_ID, resolver)
        if (events != null && events.size > 0) {
            for (data in events) {
                if (data.TITLE?.equals(event.TITLE) == true
                        && data.DTSTART == event.DTSTART
                        && data.DTEND == event.DTEND) {
                    Log.d("lzqtest", "EventWrapper.addEvent: alread exist event $events ")
                    return CalendarStatus.ALREADY_EXIST.value()
                }
            }
        }
        if (event.CALENDAR_ID == null || event.CALENDAR_ID!! < 0) {
            return CalendarStatus.PARAMETER_ERROR.value()
        }
        val contentValues = ContentValues()
        contentValues.put(CalendarContract.Events.CALENDAR_ID, event.CALENDAR_ID)
        if (!event.TITLE.isNullOrEmpty())
            contentValues.put(CalendarContract.Events.TITLE, event.TITLE)
        if (!event.EVENT_LOCATION.isNullOrEmpty())
            contentValues.put(CalendarContract.Events.EVENT_LOCATION, event.EVENT_LOCATION)
        if (!event.DESCRIPTION.isNullOrEmpty()) {
            contentValues.put(CalendarContract.Events.DESCRIPTION, event.DESCRIPTION)
        }
        if (event.EVENT_COLOR != null) {
            contentValues.put(CalendarContract.Events.EVENT_COLOR, event.EVENT_COLOR)
        }
        if (event.STATUS != null) {
            contentValues.put(CalendarContract.Events.STATUS, event.STATUS)
        }
        if (event.SELF_ATTENDEE_STATUS != null) {
            contentValues.put(CalendarContract.Events.SELF_ATTENDEE_STATUS, event.SELF_ATTENDEE_STATUS)
        }
        if (event.DTSTART != null) {
            contentValues.put(CalendarContract.Events.DTSTART, event.DTSTART)
        }
        if (!event.EVENT_TIMEZONE.isNullOrEmpty()) {
            contentValues.put(CalendarContract.Events.EVENT_TIMEZONE, event.EVENT_TIMEZONE)
        }
        if (event.ALL_DAY != null) {
            contentValues.put(CalendarContract.Events.ALL_DAY, event.ALL_DAY)
        }
        if (event.ACCESS_LEVEL != null) {
            contentValues.put(CalendarContract.Events.ACCESS_LEVEL, event.ACCESS_LEVEL)
        }
        if (event.AVAILABILITY != null) {
            contentValues.put(CalendarContract.Events.AVAILABILITY, event.AVAILABILITY)
        }
        if (event.HAS_ALARM != null) {
            contentValues.put(CalendarContract.Events.HAS_ALARM, event.HAS_ALARM)
        }
        if (event.HAS_EXTENDED_PROPERTIES != null) {
            contentValues.put(CalendarContract.Events.HAS_EXTENDED_PROPERTIES, event.HAS_EXTENDED_PROPERTIES)
        }
        if (!event.RRULE.isNullOrEmpty()) {
            contentValues.put(CalendarContract.Events.RRULE, event.RRULE)
        }
        if (!event.RDATE.isNullOrEmpty()) {
            contentValues.put(CalendarContract.Events.RDATE, event.RDATE)
        }
        if (!event.EXRULE.isNullOrEmpty()) {
            contentValues.put(CalendarContract.Events.EXRULE, event.EXRULE)
        }
        if (!event.EXDATE.isNullOrEmpty()) {
            contentValues.put(CalendarContract.Events.EXDATE, event.EXDATE)
        }
        if (event.ORIGINAL_ID != null) {
            contentValues.put(CalendarContract.Events.ORIGINAL_ID, event.ORIGINAL_ID)
        }
        if (event.LAST_DATE != null) {
            contentValues.put(CalendarContract.Events.LAST_DATE, event.LAST_DATE)
        }
        if (event.HAS_ATTENDEE_DATA != null) {
            contentValues.put(CalendarContract.Events.HAS_ATTENDEE_DATA, event.HAS_ATTENDEE_DATA)
        }
        if (event.GUESTS_CAN_MODIFY != null) {
            contentValues.put(CalendarContract.Events.GUESTS_CAN_MODIFY, event.GUESTS_CAN_MODIFY)
        }
        if (event.GUESTS_CAN_INVITE_OTHERS != null) {
            contentValues.put(CalendarContract.Events.GUESTS_CAN_INVITE_OTHERS, event.GUESTS_CAN_INVITE_OTHERS)
        }
        if (event.GUESTS_CAN_SEE_GUESTS != null) {
            contentValues.put(CalendarContract.Events.GUESTS_CAN_SEE_GUESTS, event.GUESTS_CAN_SEE_GUESTS)
        }
        if (!event.ORGANIZER.isNullOrEmpty()) {
            contentValues.put(CalendarContract.Events.ORGANIZER, event.ORGANIZER)
        }
        if (event.DELETED != null) {
            contentValues.put(CalendarContract.Events.DELETED, event.DELETED)
        }
        if (!event.EVENT_END_TIMEZONE.isNullOrEmpty()) {
            contentValues.put(CalendarContract.Events.EVENT_END_TIMEZONE, event.EVENT_END_TIMEZONE)
        }

        if (event.DTEND != null) {
            contentValues.put(CalendarContract.Events.DTEND, event.DTEND)
        }
        Log.d("lzqtest", "EventWrapper.addEvent: insert event now ")
        val ret = resolver.insert(CalendarContract.Events.CONTENT_URI, contentValues)
        Log.d("lzqtest", "EventWrapper.addEvent: insert result ret=$ret ")
        return if (ret != null) ContentUris.parseId(ret) else CalendarStatus.FAIL.value()
    }

    fun deleteEvent(id: Int, resolver: ContentResolver): Int {
        val selection: String = "(${CalendarContract.Events._ID} = ?)"
        val selectionArgs: Array<String> = arrayOf(
                id.toString()
        )
        return resolver.delete(
                CalendarContract.Events.CONTENT_URI, selection, selectionArgs)
    }

    fun deleteEventByCalendarId(calendarId: Int, resolver: ContentResolver): Int {
        val selection: String = "(${CalendarContract.Calendars._ID} = ?)"
        val selectionArgs: Array<String> = arrayOf(
                calendarId.toString()
        )
        return resolver.delete(
                CalendarContract.Events.CONTENT_URI, selection, selectionArgs)
    }


    fun queryEventBId(id: Int?, resolver: ContentResolver): ArrayList<Event> {
        val events = ArrayList<Event>()
        if (id == null) {
            return events
        }
        val selection: String = "(${CalendarContract.Events._ID} = ?)"
        val selectionArgs: Array<String> = arrayOf(
                id.toString()
        )

        resolver.query(
                CalendarContract.Events.CONTENT_URI,
                null, selection, selectionArgs, null
        ).use { cursor ->
            val ret = readEvents(cursor)
            events.addAll(ret)
            cursor?.close()
            return events
        }
    }

    fun queryEvent(event: Event,resolver: ContentResolver):ArrayList<Event>{
        val events = ArrayList<Event>()
//        if (event.CALENDAR_ID==null || event.DTEND==null||event.DTSTART==null||event.DESCRIPTION.isNullOrEmpty()||event.TITLE.isNullOrEmpty()){
//            return events
//        }
        val selection: String = "((${CalendarContract.Events.CALENDAR_ID} = ?) AND (" +
                "${CalendarContract.Events.TITLE} = ?) AND (" +
                "${CalendarContract.Events.DTEND} = ?) AND (" +
                "${CalendarContract.Events.DESCRIPTION} = ?) AND (" +
                "${CalendarContract.Events.DTSTART} = ?))"
        val selectionArgs: Array<String?> = arrayOf(
                event.CALENDAR_ID.toString(),
                event.TITLE,
                event.DTEND.toString(),
                event.DESCRIPTION,
                event.DTSTART.toString()
        )
        resolver.query(
                CalendarContract.Events.CONTENT_URI,
                null, selection, selectionArgs, null
        ).use { cursor ->
            val ret = readEvents(cursor)
            events.addAll(ret)
            cursor?.close()
            return events
        }

    }

    fun queryEventByCalendarId(calendarId: Long?, resolver: ContentResolver): ArrayList<Event> {
        val events = ArrayList<Event>()
        if (calendarId == null) {
            return events
        }
        val selection: String = "(${CalendarContract.Events.CALENDAR_ID} = ?)"
        val selectionArgs: Array<String> = arrayOf(
                calendarId.toString()
        )

        resolver.query(
                CalendarContract.Events.CONTENT_URI,
                null, selection, selectionArgs, null
        ).use { cursor ->
            val ret = readEvents(cursor)
            events.addAll(ret)
            cursor?.close()
            return events
        }
    }

    fun readEvents(cursor: Cursor?): ArrayList<Event> {
        val events = ArrayList<Event>()

        // 不存在日历账户
        if (null == cursor) {
            return events
        }


        if (!cursor.moveToFirst()) {
            return events

        }
        do {
            var event: Event = Event()
            event.ID =
                cursor.getLong(cursor.getColumnIndex(CalendarContract.Events._ID))
            event.CALENDAR_ID =
                cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.CALENDAR_ID))
            event.TITLE =
                cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE))
            event.EVENT_LOCATION =
                cursor.getString(cursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION))
            event.DESCRIPTION =
                cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION))
            event.EVENT_TIMEZONE =
                cursor.getString(cursor.getColumnIndex(CalendarContract.Events.EVENT_TIMEZONE))
            event.RRULE =
                cursor.getString(cursor.getColumnIndex(CalendarContract.Events.RRULE))
            event.RDATE =
                cursor.getString(cursor.getColumnIndex(CalendarContract.Events.RDATE))
            event.EXRULE =
                cursor.getString(cursor.getColumnIndex(CalendarContract.Events.EXRULE))
            event.EXDATE =
                cursor.getString(cursor.getColumnIndex(CalendarContract.Events.EXDATE))
            event.ORGANIZER =
                cursor.getString(cursor.getColumnIndex(CalendarContract.Events.ORGANIZER))
            event.EVENT_END_TIMEZONE =
                cursor.getString(cursor.getColumnIndex(CalendarContract.Events.EVENT_END_TIMEZONE))
            event.EVENT_COLOR =
                cursor.getInt(cursor.getColumnIndex(CalendarContract.Events.EVENT_COLOR))
            event.STATUS =
                cursor.getInt(cursor.getColumnIndex(CalendarContract.Events.STATUS))
            event.SELF_ATTENDEE_STATUS =
                cursor.getInt(cursor.getColumnIndex(CalendarContract.Events.SELF_ATTENDEE_STATUS))
            event.DTSTART =
                cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART))
            event.DTEND =
                cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTEND))
            event.ALL_DAY =
                cursor.getInt(cursor.getColumnIndex(CalendarContract.Events.ALL_DAY))
            event.ACCESS_LEVEL =
                cursor.getInt(cursor.getColumnIndex(CalendarContract.Events.ACCESS_LEVEL))
            event.AVAILABILITY =
                cursor.getInt(cursor.getColumnIndex(CalendarContract.Events.AVAILABILITY))
            event.HAS_ALARM =
                cursor.getInt(cursor.getColumnIndex(CalendarContract.Events.HAS_ALARM))
            event.HAS_EXTENDED_PROPERTIES =
                cursor.getInt(cursor.getColumnIndex(CalendarContract.Events.HAS_EXTENDED_PROPERTIES))
            event.ORIGINAL_ID =
                cursor.getInt(cursor.getColumnIndex(CalendarContract.Events.ORIGINAL_ID))
            event.LAST_DATE =
                cursor.getInt(cursor.getColumnIndex(CalendarContract.Events.LAST_DATE))
            event.HAS_ATTENDEE_DATA =
                cursor.getInt(cursor.getColumnIndex(CalendarContract.Events.HAS_ATTENDEE_DATA))
            event.GUESTS_CAN_MODIFY =
                cursor.getInt(cursor.getColumnIndex(CalendarContract.Events.GUESTS_CAN_MODIFY))
            event.GUESTS_CAN_INVITE_OTHERS =
                cursor.getInt(cursor.getColumnIndex(CalendarContract.Events.GUESTS_CAN_INVITE_OTHERS))
            event.GUESTS_CAN_SEE_GUESTS =
                cursor.getInt(cursor.getColumnIndex(CalendarContract.Events.GUESTS_CAN_SEE_GUESTS))
            event.DELETED =
                cursor.getInt(cursor.getColumnIndex(CalendarContract.Events.DELETED))

            events.add(event)
        } while (cursor.moveToNext())
        return events
    }


}