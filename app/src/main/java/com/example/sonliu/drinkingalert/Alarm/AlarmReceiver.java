package com.example.sonliu.drinkingalert.Alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;

import com.example.sonliu.drinkingalert.DrinkAlertApp;
import com.example.sonliu.drinkingalert.DrinkAlertDefaultConstant;
import com.example.sonliu.drinkingalert.R;
import com.example.sonliu.drinkingalert.ui.activity.MainActivity;
import com.example.sonliu.drinkingalert.util.MediaPlayerUtil;
import com.example.sonliu.drinkingalert.util.VibratorUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    private Context mContext;
    private Notification notification;
    private NotificationManager nManager;
    private String name, userType, alertType, alarmId;
    private int alarmTime;
    private MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mediaPlayer = new MediaPlayer();
        name = intent.getStringExtra("user_name");
        userType = intent.getStringExtra("user_type");
        alertType = intent.getStringExtra("alert_type");
        alarmTime = intent.getIntExtra("alarm_time", 0);
        alarmId = intent.getStringExtra("alarm_id");
        if (intent.getAction().equals(context.getString(R.string.receiver_action))) {
            startNotification();
            parseAlertType();
        }
    }

    private void startNotification() {
        Integer time = alarmTime;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, time / 60);
        c.set(Calendar.MINUTE, time % 60);
        PendingIntent pi = PendingIntent.getActivity(mContext, DrinkAlertDefaultConstant.SKIP_MAINACTIVITY_CODE,
                new Intent(mContext, MainActivity.class), 0);
        Bitmap bitmap = BitmapFactory.decodeResource(DrinkAlertApp.getContext().getResources(),
                R.drawable.ic_notification_large);
        Notification.Builder builder = new Notification.Builder(mContext);
        builder.setContentTitle(new SimpleDateFormat("HH:mm").format(c.getTime()) +
                mContext.getString(R.string.notification_content_bottom))
                .setContentText((name == null ? "" : name + ":") + mContext.getString(R.string.notification_content))
                .setSubText(mContext.getString(R.string.notification_title))
                .setLargeIcon(bitmap)
                .setWhen(c.getTimeInMillis())
                .setSmallIcon(R.drawable.ic_notification)
                .setTicker(mContext.getString(R.string.notification_ticker))
                .setAutoCancel(true)
                .setContentIntent(pi);
        notification = builder.build();
        nManager.notify(Integer.parseInt(alarmId), notification);
    }

    private void parseAlertType() {
        if (alertType != null) {
            switch (Integer.parseInt(alertType)) {
                case 0:
                    MediaPlayerUtil.stopRing(mediaPlayer);
                    VibratorUtil.virateCancle(mContext);
                    break;
                case 1:
                    MediaPlayerUtil.stopRing(mediaPlayer);
                    VibratorUtil.vibrator(mContext, 500);
                    break;
                case 2:
                    VibratorUtil.virateCancle(mContext);
                    MediaPlayerUtil.playRing(mContext, mediaPlayer);
                    break;
                case 3:
                    MediaPlayerUtil.playRing(mContext, mediaPlayer);
                    VibratorUtil.vibrator(mContext, 500);
                    break;
            }
        }
    }
}
