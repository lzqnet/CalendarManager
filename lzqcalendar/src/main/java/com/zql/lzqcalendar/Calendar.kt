package com.zql.lzqcalendar

import android.content.Context
import android.provider.CalendarContract

data class Calendar(
        var ACCOUNT_ID: Long? = null,
        var ACCOUNT_NAME: String? = null,
        var ACCOUNT_TYPE: String? = null,
        var NAME: String? = null,
        var DIRTY: Int? = null,
        var MUTATORS: String? = null,
        var CALENDAR_DISPLAY_NAME: String? = null,
        var CALENDAR_COLOR: Int? = null,
        var CALENDAR_ACCESS_LEVEL: Int? = null,
        var CALENDAR_LOCATION: String? = null,
        var CALENDAR_TIME_ZONE: String? = null,
        var OWNER_ACCOUNT: String? = null,
        var MAX_REMINDERS: Int? = null,
        var ALLOWED_REMINDERS: String? = null,
        var ALLOWED_AVAILABILITY: String? = null,
        var ALLOWED_ATTENDEE_TYPES: String? = null
) {
    class Builder(accountName: String, accountType: String, name: String, ownerAccount: String, displayName: String) {
        var ACCOUNT_NAME: String? = null
        var ACCOUNT_TYPE: String? = null
        var NAME: String? = null
        var DIRTY: Int? = null
        var MUTATORS: String? = null
        var CALENDAR_DISPLAY_NAME: String? = null
        var CALENDAR_COLOR: Int? = null
        var CALENDAR_ACCESS_LEVEL: Int? = null
        var CALENDAR_LOCATION: String? = null
        var CALENDAR_TIME_ZONE: String? = null
        var OWNER_ACCOUNT: String? = null
        var MAX_REMINDERS: Int? = null
        var ALLOWED_REMINDERS: String? = null
        var ALLOWED_AVAILABILITY: String? = null
        var ALLOWED_ATTENDEE_TYPES: String? = null

        init {
            ACCOUNT_NAME = accountName
            ACCOUNT_TYPE = accountType
            NAME = name
            CALENDAR_DISPLAY_NAME = displayName
            OWNER_ACCOUNT = ownerAccount
        }

        fun build(): Calendar {
            return Calendar(ACCOUNT_NAME = ACCOUNT_NAME, ACCOUNT_TYPE = ACCOUNT_TYPE,
                    NAME = NAME, DIRTY = DIRTY, MUTATORS = MUTATORS, CALENDAR_DISPLAY_NAME = CALENDAR_DISPLAY_NAME,
                    CALENDAR_COLOR = CALENDAR_COLOR, CALENDAR_ACCESS_LEVEL = CALENDAR_ACCESS_LEVEL, CALENDAR_LOCATION = CALENDAR_LOCATION,
                    CALENDAR_TIME_ZONE = CALENDAR_TIME_ZONE, OWNER_ACCOUNT = OWNER_ACCOUNT, MAX_REMINDERS = MAX_REMINDERS,
                    ALLOWED_REMINDERS = ALLOWED_REMINDERS, ALLOWED_AVAILABILITY = ALLOWED_AVAILABILITY, ALLOWED_ATTENDEE_TYPES = ALLOWED_ATTENDEE_TYPES)
        }
    }

    class DefaultBuilder(context: Context) {
        var ACCOUNT_NAME: String? = null
        var ACCOUNT_TYPE: String? = null
        var NAME: String? = null
        var DIRTY: Int? = null
        var MUTATORS: String? = null
        var CALENDAR_DISPLAY_NAME: String? = null
        var CALENDAR_COLOR: Int? = null
        var CALENDAR_ACCESS_LEVEL: Int? = null
        var CALENDAR_LOCATION: String? = null
        var CALENDAR_TIME_ZONE: String? = null
        var OWNER_ACCOUNT: String? = null
        var MAX_REMINDERS: Int? = null
        var ALLOWED_REMINDERS: String? = null
        var ALLOWED_AVAILABILITY: String? = null
        var ALLOWED_ATTENDEE_TYPES: String? = null

        init {
            ACCOUNT_NAME = context.getResources().getString(context.applicationInfo.labelRes)
            ACCOUNT_TYPE = CalendarContract.ACCOUNT_TYPE_LOCAL
            NAME = context.packageName
            CALENDAR_DISPLAY_NAME = context.getResources().getString(context.applicationInfo.labelRes)
            OWNER_ACCOUNT = context.getResources().getString(context.applicationInfo.labelRes)
        }

        fun build(): Calendar {
            return Calendar(ACCOUNT_NAME = ACCOUNT_NAME, ACCOUNT_TYPE = ACCOUNT_TYPE,
                    NAME = NAME, DIRTY = DIRTY, MUTATORS = MUTATORS, CALENDAR_DISPLAY_NAME = CALENDAR_DISPLAY_NAME,
                    CALENDAR_COLOR = CALENDAR_COLOR, CALENDAR_ACCESS_LEVEL = CALENDAR_ACCESS_LEVEL, CALENDAR_LOCATION = CALENDAR_LOCATION,
                    CALENDAR_TIME_ZONE = CALENDAR_TIME_ZONE, OWNER_ACCOUNT = OWNER_ACCOUNT, MAX_REMINDERS = MAX_REMINDERS,
                    ALLOWED_REMINDERS = ALLOWED_REMINDERS, ALLOWED_AVAILABILITY = ALLOWED_AVAILABILITY, ALLOWED_ATTENDEE_TYPES = ALLOWED_ATTENDEE_TYPES)
        }
    }
}
