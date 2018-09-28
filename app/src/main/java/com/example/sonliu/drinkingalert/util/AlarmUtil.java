package com.example.sonliu.drinkingalert.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class AlarmUtil {
    /**
     * 开启今天的闹钟
     */
    public static void startAlarm(Context context, int hour, int minute, int requestCode, Intent intent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 1);
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() - 300, pi);
    }

    /**
     * 关闭今天的闹钟
     */
    public static void closeAlarm(Context context, Intent intent, int requestCode) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(pi);
    }

    /**
     * 设置明天的闹钟
     */
    public static void startPreAlarm(Context context, int hour, int minute, int requestCode, Intent intent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
        calendar.set(Calendar.HOUR_OF_DAY , hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 1);
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode + 1024, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() - 300, pi);
    }

    /**
     * 关闭明天的闹钟
     */
    public static void closePreAlarm(Context context, Intent intent, int requestCode) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode +1024, intent, 0);
        alarmManager.cancel(pi);
    }

}
