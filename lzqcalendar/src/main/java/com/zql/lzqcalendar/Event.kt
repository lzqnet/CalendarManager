package com.zql.lzqcalendar

data class Event(
        var ID: Long? = null,
        var CALENDAR_ID: Long? = null,
        var TITLE: String? = null,
        var EVENT_LOCATION: String? = null,
        var DESCRIPTION: String? = null,
        var EVENT_COLOR: Int? = null,
        var STATUS: Int? = null,
        var SELF_ATTENDEE_STATUS: Int? = null, //+ " INTEGER NOT NULL DEFAULT 0," +
        // dtstart in millis since epoch
        // dtstart in millis since epoch
        var DTSTART: Long? = null,
        // dtend in millis since epoch
        // dtend in millis since epoch
        var DTEND: Long? = null,
        // timezone for event
        // timezone for event
        var EVENT_TIMEZONE: String? = null,
        //var DURATION : String,
        var ALL_DAY: Int? = null, //+ " INTEGER NOT NULL DEFAULT 0," +
        var ACCESS_LEVEL: Int? = null, //+ " INTEGER NOT NULL DEFAULT 0," +
        var AVAILABILITY: Int? = null, //+ " INTEGER NOT NULL DEFAULT 0," +
        var HAS_ALARM: Int? = null, //+ " INTEGER NOT NULL DEFAULT 0," +
        var HAS_EXTENDED_PROPERTIES: Int? = null, //+ " INTEGER NOT NULL DEFAULT 0," +
        var RRULE: String? = null,
        var RDATE: String? = null,
        var EXRULE: String? = null,
        var EXDATE: String? = null,
        var ORIGINAL_ID: Int? = null,
        // ORIGINAL_SYNC_ID is the _sync_id of recurring event
        // ORIGINAL_SYNC_ID is the _sync_id of recurring event
        //var ORIGINAL_SYNC_ID : String,
        // originalInstanceTime is in millis since epoch
        // originalInstanceTime is in millis since epoch
        //var ORIGINAL_INSTANCE_TIME : Int,
        //var ORIGINAL_ALL_DAY : Int,
        // lastDate is in millis since epoch
        // lastDate is in millis since epoch
        var LAST_DATE: Int? = null,
        var HAS_ATTENDEE_DATA: Int? = null, //+ " INTEGER NOT NULL DEFAULT 0," +
        var GUESTS_CAN_MODIFY: Int? = null, // + " INTEGER NOT NULL DEFAULT 0," +
        var GUESTS_CAN_INVITE_OTHERS: Int? = null, //+ " INTEGER NOT NULL DEFAULT 1," +
        var GUESTS_CAN_SEE_GUESTS: Int? = null, //+ " INTEGER NOT NULL DEFAULT 1," +
        var ORGANIZER: String? = null, //+ " STRING," +
        var DELETED: Int? = null, //+ " INTEGER NOT NULL DEFAULT 0," +
        // timezone for event with allDay events are in local timezone
        // timezone for event with allDay events are in local timezone
        var EVENT_END_TIMEZONE: String? = null
        //var CUSTOM_APP_PACKAGE : String
) {
    class Build(
            var TITLE: String,
            var DESCRIPTION: String,
            var DTSTART: Long,
            var DTEND: Long,
            var EVENT_TIMEZONE: String


    ) {
        var CALENDAR_ID: Long?=null
        var EVENT_COLOR: Int? = null
        var STATUS: Int? = null
        var SELF_ATTENDEE_STATUS: Int? = null //+ " INTEGER NOT NULL DEFAULT 0," +
        // dtstart in millis since epoch
        // dtstart in millis since epoch

        // dtend in millis since epoch
        // dtend in millis since epoch
        var EVENT_LOCATION: String? = null

        // timezone for event
        // timezone for event
        //var DURATION : String,
        var ALL_DAY: Int? = null //+ " INTEGER NOT NULL DEFAULT 0," +
        var ACCESS_LEVEL: Int? = null //+ " INTEGER NOT NULL DEFAULT 0," +
        var AVAILABILITY: Int? = null //+ " INTEGER NOT NULL DEFAULT 0," +
        var HAS_ALARM: Int? = null //+ " INTEGER NOT NULL DEFAULT 0," +
        var HAS_EXTENDED_PROPERTIES: Int? = null //+ " INTEGER NOT NULL DEFAULT 0," +
        var RRULE: String? = null
        var RDATE: String? = null
        var EXRULE: String? = null
        var EXDATE: String? = null
        var ORIGINAL_ID: Int? = null

        // ORIGINAL_SYNC_ID is the _sync_id of recurring event
        // ORIGINAL_SYNC_ID is the _sync_id of recurring event
        //var ORIGINAL_SYNC_ID : String,
        // originalInstanceTime is in millis since epoch
        // originalInstanceTime is in millis since epoch
        //var ORIGINAL_INSTANCE_TIME : Int,
        //var ORIGINAL_ALL_DAY : Int,
        // lastDate is in millis since epoch
        // lastDate is in millis since epoch
        var LAST_DATE: Int? = null
        var HAS_ATTENDEE_DATA: Int? = null //+ " INTEGER NOT NULL DEFAULT 0," +
        var GUESTS_CAN_MODIFY: Int? = null // + " INTEGER NOT NULL DEFAULT 0," +
        var GUESTS_CAN_INVITE_OTHERS: Int? = null //+ " INTEGER NOT NULL DEFAULT 1," +
        var GUESTS_CAN_SEE_GUESTS: Int? = null //+ " INTEGER NOT NULL DEFAULT 1," +
        var ORGANIZER: String? = null //+ " STRING," +
        var DELETED: Int? = null //+ " INTEGER NOT NULL DEFAULT 0," +

        // timezone for event with allDay events are in local timezone
        // timezone for event with allDay events are in local timezone
        var EVENT_END_TIMEZONE: String? = null


        fun build(): Event {
            return Event(CALENDAR_ID = CALENDAR_ID, TITLE = TITLE, EVENT_LOCATION = EVENT_LOCATION, DESCRIPTION = DESCRIPTION,
                    DTSTART = DTSTART, DTEND = DTEND, EVENT_COLOR = EVENT_COLOR, STATUS = STATUS, SELF_ATTENDEE_STATUS = SELF_ATTENDEE_STATUS,
                    EVENT_TIMEZONE = EVENT_TIMEZONE, ALL_DAY = ALL_DAY, ACCESS_LEVEL = ACCESS_LEVEL, AVAILABILITY = AVAILABILITY,
                    HAS_ALARM = HAS_ALARM, HAS_EXTENDED_PROPERTIES = HAS_EXTENDED_PROPERTIES, RRULE = RRULE, RDATE = RDATE, EXRULE = EXRULE,
                    EXDATE = EXDATE, ORIGINAL_ID = ORIGINAL_ID, LAST_DATE = LAST_DATE, HAS_ATTENDEE_DATA = HAS_ATTENDEE_DATA, GUESTS_CAN_MODIFY = GUESTS_CAN_MODIFY,
                    GUESTS_CAN_INVITE_OTHERS = GUESTS_CAN_INVITE_OTHERS, GUESTS_CAN_SEE_GUESTS = GUESTS_CAN_SEE_GUESTS, ORGANIZER = ORGANIZER,
                    DELETED = DELETED, EVENT_END_TIMEZONE = EVENT_END_TIMEZONE
            )
        }
    }
}


