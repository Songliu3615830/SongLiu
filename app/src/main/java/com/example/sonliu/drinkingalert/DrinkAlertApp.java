package com.example.sonliu.drinkingalert;

import android.app.Application;
import android.content.IntentFilter;

import com.example.sonliu.drinkingalert.Alarm.AlarmReceiver;
import com.example.sonliu.drinkingalert.User.UserManager;
import com.example.sonliu.drinkingalert.datebase.DataBaseExecutor;


public class DrinkAlertApp extends Application {
    private static DrinkAlertApp context;
    public static DataBaseExecutor mDBExecutor;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mDBExecutor = new DataBaseExecutor(context);
        //如果没当前用户 设置成人用户为当前用户
        UserManager userManager = UserManager.getInstance().initUsers();
        if (userManager.getCurrentUser() == null) {
            userManager.setCurrentUser(userManager.getManUser());
        }
        //默认开启闹钟
        if (userManager.getManUser().getIsAlert() == 1) {
            userManager.getManUser().setIsAlert(1);
        }

    }

    public static DrinkAlertApp getContext() {
        return context;
    }

}
