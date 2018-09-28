package com.example.sonliu.drinkingalert.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.example.sonliu.drinkingalert.DrinkAlertApp;


/**
 * Toast 工具类
 */

public class ToastUtils {
    public static void shortToast(Context context, String msg) {
        Toast toast = Toast.makeText(context,msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 40);
        toast.show();
    }

    public static void longToast(Context context,String msg) {
        Toast toast = Toast.makeText(context,msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 40);
        toast.show();
    }
}
