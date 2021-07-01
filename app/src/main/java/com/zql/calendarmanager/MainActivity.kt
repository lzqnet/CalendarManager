package com.zql.calendarmanager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.zql.lzqcalendar.*
import com.zql.lzqcalendar.Calendar
import java.util.*

class MainActivity : AppCompatActivity() {
    public val PERMISSION_REQUEST_ID = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_CALENDAR
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("lzqtest", "SettingActivity.onPreferenceTreeClick: request permission ")
            ActivityCompat.requestPermissions(
                    this, arrayOf(
                    Manifest.permission.WRITE_CALENDAR,
                    Manifest.permission.READ_CALENDAR
            ), PERMISSION_REQUEST_ID
            )
        }
    }

    fun addEvent(view: View) {
//        val calendar = Calendar.DefaultBuilder(this).build()
//        //        val calendarWrapper= CalendarWrapper()
//        var id = CalendarManager.addCalendar(calendar, this)
//        Log.d("lzqtest", "MainActivity.addEvent: calendar id=$id ")
//        //        if (id== Constants.ALREADY_EXIST){
//        //            Log.d("lzqtest", "MainActivity.addEvent: delete account and add again ")
//        //            val ret= CalendarManager.deleteCalendarByAccount(calendar.ACCOUNT_NAME?:"",calendar.ACCOUNT_TYPE?:"",this)
//        //            id = CalendarManager.addCalendar(calendar, this)
//        //        }
//        if (id == CalendarStatus.ALREADY_EXIST.value()) {
//            var list = CalendarManager.queryCalendar(calendar, this)
//            if (list.size > 0) {
//                id = list[0].ACCOUNT_ID
//            }
//        }
//        if (id != null && id >= 0L) {
//            val event = Event.Build( "测试一下" + System.currentTimeMillis(), "描述", System.currentTimeMillis().toInt(), (System.currentTimeMillis() + 60000).toInt(),
//                    TimeZone.getDefault().id)
//            event.CALENDAR_ID=id
//            val eventId = CalendarManager.addEvent(event.build(), this)
//            Log.d("lzqtest", "MainActivity.addEvent: eventUri=$eventId ")
//            if (eventId!=null && eventId>=0){
//                val reminder=Reminder.Builder(eventId,event.DTSTART,CalendarContract.Reminders.METHOD_ALERT)
//                val remindId=CalendarManager.addReminder(reminder.build(),this)
//                Log.d("lzqtest", "MainActivity.addEvent: remindId=$remindId ")
//            }
//
//        }


        val event = Event.Build( "测试一下" + System.currentTimeMillis(), "描述", System.currentTimeMillis().toLong()+1000*60, (System.currentTimeMillis() + 1000*60+60000).toLong(),
                TimeZone.getDefault().id)
        event.ACCESS_LEVEL=CalendarContract.Events.ACCESS_DEFAULT
        event.STATUS=CalendarContract.Events.STATUS_TENTATIVE
        event.HAS_ALARM=1
        event.AVAILABILITY=CalendarContract.Events.AVAILABILITY_BUSY
       val ret= CalendarManager.addDefaultCalendarEventAndReminder(event.build(),this)
        Log.d("lzqtest", "MainActivity.addEvent: ret=$ret ")
    }

    fun deleteCalendar(view: View) {
        val calendar = Calendar.DefaultBuilder(this).build()
        val ret = CalendarManager.deleteCalendarByAccount(calendar.ACCOUNT_NAME?:"",calendar.ACCOUNT_TYPE?:"",this)
        Log.d("lzqtest", "MainActivity.deleteCalendar: ret=$ret ")
    }


}