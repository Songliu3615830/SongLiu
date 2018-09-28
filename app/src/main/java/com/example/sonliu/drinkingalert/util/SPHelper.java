package com.example.sonliu.drinkingalert.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.sonliu.drinkingalert.DrinkAlertApp;

/**
 * 状态保存
 */
public class SPHelper {
    private static final String TAG = "SPHelper:";

    public static Integer getSuccessDayByReadSP(int id) {
        SharedPreferences sp = DrinkAlertApp.getContext()
                .getSharedPreferences("success" + id, Context.MODE_PRIVATE);
        Integer successDay = null;
        successDay = sp.getInt("success_day", 0);
        Log.d(TAG, "getSuccessDayByReadSP: 读取成功" + successDay);
        return successDay;
    }

    public static void setSuccessDayByWriteSP(int id, int successDay) {
        SharedPreferences sp = DrinkAlertApp.getContext()
                .getSharedPreferences("success" + id, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("success_day", successDay);
        editor.commit();
        Log.d(TAG, "setSuccessDayByWriteSP: 保存成功:" + sp.getInt("success_day", 0));
    }

    public static void setIsReadSuccessDayByWriteSP(int id, boolean isRead) {
        SharedPreferences sp = DrinkAlertApp.getContext()
                .getSharedPreferences("success" + id, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("is_read", isRead);
        editor.commit();
    }

    public static boolean getIsReadSuccessDayByReadSP(int id) {
        SharedPreferences sp = DrinkAlertApp.getContext()
                .getSharedPreferences("success" + id, Context.MODE_PRIVATE);
        return sp.getBoolean("is_read", true);
    }

}
