package com.example.sonliu.drinkingalert.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.sonliu.drinkingalert.R;
import com.example.sonliu.drinkingalert.User.User;
import com.example.sonliu.drinkingalert.User.UserManager;
import com.example.sonliu.drinkingalert.util.wheelviewUtil.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;


public class DrinkPlanSettingDialog extends Dialog  {
    private Context mContext;
    private List<String> items;
    private User currentUser;

    public DrinkPlanSettingDialog(@NonNull Context context) {
        super(context, R.style.TodayBottomDialog);
        mContext = context;
        items = new ArrayList<>();
        addItem();
        currentUser = UserManager.getInstance().getCurrentUser();
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
        setContentView(R.layout.dialog_plan_drink_plan_setting);
        WheelView wheelView = findViewById(R.id.wheel_dialog);
        wheelView.setItems(items, (currentUser.getDayPlan() - 1000) / 100);
        wheelView.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedIndex, String item) {
                currentUser.setDayPlan(selectedIndex * 100 + 1000);
                Log.d("PlanSetting", "选中" + selectedIndex +"DayPlan:"
                        + currentUser.getDayPlan());
            }
        });
    }
    private void addItem() {
        for (int i = 1000; i <= 9000; i += 100) {
            items.add(i + "毫升");
        }
    }

}
