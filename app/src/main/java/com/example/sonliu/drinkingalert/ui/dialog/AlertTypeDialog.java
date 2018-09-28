package com.example.sonliu.drinkingalert.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;

import android.support.annotation.NonNull;

import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.sonliu.drinkingalert.R;

import com.example.sonliu.drinkingalert.User.UserManager;
import com.example.sonliu.drinkingalert.util.wheelviewUtil.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;


public class AlertTypeDialog extends Dialog  {
    private Context mContext;
    private List<String> items;
    private int alertType = 3;
    private AlertTypeWheelListener listener;

    public AlertTypeDialog(@NonNull Context context, String alertType, AlertTypeWheelListener listener) {
        super(context, R.style.TodayBottomDialog);
        mContext = context;
        this.alertType = getIdByAlertType(alertType);
        this.listener = listener;
        items = new ArrayList<>();
        items.add("无声");
        items.add("仅振动");
        items.add("仅提示音");
        items.add("振动加提示音");
        Window window = getWindow();
        WindowManager manager = window.getWindowManager();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        Display d = manager.getDefaultDisplay();
        Point size = new Point();
        d.getSize(size);
        attributes.height = (int) (size.y*0.4);
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_me_alert_type);
        WheelView wheelView = findViewById(R.id.wheel_dialog);
        wheelView.setItems(items, alertType);
        wheelView.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedIndex, String item) {
                listener.selectedItem(item);
                UserManager.getInstance().getManUser().setAlertType(selectedIndex);
            }
        });
    }

    public interface AlertTypeWheelListener{
        void selectedItem(String item);
    }

    private int getIdByAlertType(String alertType) {
        int alertTypeId = 3;
        switch (alertType) {
            case "无声":
                alertTypeId = 0;
                break;
            case "仅振动":
                alertTypeId = 1;
                break;
            case "仅提示音":
                alertTypeId = 2;
                break;
            case "振动加提示音":
                alertTypeId = 3;
                break;
        }
        return alertTypeId;
    }
}
