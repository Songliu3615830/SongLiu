package com.example.sonliu.drinkingalert.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.example.sonliu.drinkingalert.DrinkAlertApp;


/**
 * 版本名和版本号
 */

public class PackageUtils {

    public static long packageCode() {
        PackageManager manager = DrinkAlertApp.getContext().getPackageManager();
        long code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(DrinkAlertApp.getContext().getPackageName(), 0);
            code = info.getLongVersionCode();//版本号 过时 尚未解决
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static String packageName() {
        PackageManager manager = DrinkAlertApp.getContext().getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(DrinkAlertApp.getContext().getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }
}
