# CalenderManager
Android插入日历提醒

> 本文用于说明Android日历功能

Android系统提供了插入日功能。第三方应用可以自行插入日历事件，系统日历会根据日历事件在设定的时间弹出通知提醒用户。

# Android系统日历提醒原理

Android提供了一个provider:`com.android.providers.calendar`  该provider提供一套接口供开发者使用。

### 日历事件存储

日历事件存储在数据库里，数据库的路径是：

```
/data/data/com.android.providers.calendar/databases/calendar.db
```

### 日历事件数据格式

日历事件主要涉及三个数据结构，也是上面说的数据库里的三个表：Calendars、Events、Reminders

#### Calendars

存储的是日历帐号信息。每个app都可以往日历数据库里注册多个帐号，也可以使用数据库里已有的帐号。一般来讲，每个app注册一个帐号即可。当不需要使用这个日的时候，删除这个日历。与这个日历绑定的事件和提醒都会被删除。

表结构为：

![img](https://github.com/lzqnet/CalendarManager/blob/main/image/calendar.png)



#### Events

日历事件，比如今天下午三点要去读书。这就是一个事件。在Events表里，每一行数据都是一个事件

表结构为：

![img](https://github.com/lzqnet/CalendarManager/blob/main/image/event.png)

表中有一个字段是calendar_id。用于标识该事件属于哪个帐号，该值对应着Calendars表中的_id字段

#### Reminders

日历提醒，有了日历事件。并不代表就会产生提醒。如果某个日历事件需要提醒用户。需要对这个日历事件创建一个提醒，告诉系统，该事件需要以什么方式提醒用户

表结构：

![img](https://github.com/lzqnet/CalendarManager/blob/main/image/reminder.png)

表中event_id用于标识该提醒属于哪个日历事件。该值对应着Events表中的_id字段



以上三个表的字段含义这里就不一一解释了。具体可以看https://developer.android.com/reference/android/provider/CalendarContract.Calendars

https://developer.android.com/reference/android/provider/CalendarContract.Events

https://developer.android.com/reference/android/provider/CalendarContract.Reminders

### 日历开发API

1. #### 存储日历帐号

- 存储帐号信息的URI:android.provider.CalendarContract.Calendars#CONTENT_URI

- DEMO CODE

```
//Calendar 为自定义日历数据格式，用于存储用户创建的帐号信息

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

    //uri需要通过appendQueryParameter 追加参数，否则会报插入失败的错误

    uri= uri.buildUpon()

        .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")

        .appendQueryParameter(Calendars.ACCOUNT_NAME, calendar.ACCOUNT_NAME)

        .appendQueryParameter(Calendars.ACCOUNT_TYPE,

                calendar.ACCOUNT_TYPE)

        .build()

//插入日历帐号信息

     val ret=resolver.insert(uri, contentValues)



    return if (ret!=null) ContentUris.parseId(ret) else CalendarStatus.FAIL.value()



}
```

1. ### 存储日历事件

- 存储日历事件URI:android.provider.CalendarContract.Events#CONTENT_URI
- DEMO CODE

```
// EVENT 为自定义对象，用于存储用户日历事件信息

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
```

1. ### 存储日历提醒

- 存储日历提醒URI:android.provider.CalendarContract.Reminders#CONTENT_URI
- DEMO CODE

```
fun addReminder(reminder: Reminder, resolver: ContentResolver): Long? {

    val reminders = queryMinderByEventId(reminder.EVENT_ID, resolver)

    if (reminders != null && reminders.size > 0) {

        for (data in reminders) {

            if (data.MINUTES == (reminder.MINUTES)

                && data.METHOD == reminder.METHOD

            ) {

                Log.d(

                    "lzqtest",

                    "ReminderWrapper.addReminder: alread exist reminder=$reminders "

                )

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

    val ret = resolver.insert(CalendarContract.Reminders.CONTENT_URI, contentValues)

    Log.d("lzqtest", "ReminderWrapper.addReminder: result ret=$ret ")

    return if (ret == null) CalendarStatus.FAIL.value() else ContentUris.parseId(ret)

}
```

按以上方式插入日历数据到provider后，系统日历app即可显示插入的日历提醒。第三方日历app不一定会显示。这取决于第三方日历app会不会去读取系统日历数据库
