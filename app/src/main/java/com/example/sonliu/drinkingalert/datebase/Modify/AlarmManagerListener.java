package com.example.sonliu.drinkingalert.datebase.Modify;

/**
 * 闹钟关闭
 */
public interface AlarmManagerListener {
    void onStartAlarm();
    void onCloseAlarm();
    void onTypeChanged(Integer alertType);
}
