package com.example.sonliu.drinkingalert.util;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/**
 * 震动工具类
 */
public class VibratorUtil {
    public static boolean hasVibrator;//是否有振动器

    /* 震动milliseconds毫秒 */
    public static void vibrator(final Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    /* 以pattern[] 形式震动 */
    public static void vibrator(final Context context, long[] pattern, int repeat) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern,repeat);
    }

    /* 取消震动 */
    public static void virateCancle(final Context context) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.cancel();
    }
}
