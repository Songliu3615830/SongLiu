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
import com.example.sonliu.drinkingalert.User.User;
import com.example.sonliu.drinkingalert.User.UserManager;
import com.example.sonliu.drinkingalert.datebase.data.Cup;
import com.example.sonliu.drinkingalert.util.wheelviewUtil.wheelview.*;

import java.util.ArrayList;
import java.util.List;


public class CupNumSettingDialog extends Dialog  {
    private Context mContext;
    private List<String> items;
    private Cup clickCup;
    private User currentUser;

    public CupNumSettingDialog(@NonNull Context context, Cup clickCup) {
        super(context, R.style.TodayBottomDialog);
        mContext = context;
        items = new ArrayList<>();
        this.clickCup = clickCup;
        addItem();
        currentUser = UserManager.getInstance().getCurrentUser();
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        Display d = window.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        d.getSize(size);
        attributes.height = (int) (size.y*0.45);
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_today_split_empty);
        WheelView wheelView = findViewById(R.id.wheel_dialog);
        wheelView.setItems(items, (clickCup.getNum() - 100) / 50);
        wheelView.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedIndex, String item) {
                //选中每日目标
                clickCup.setNum(getDrinkNum(item));
                currentUser.modifyCup(clickCup);
            }
        });
    }

    private void addItem(){
        for (int i = 100; i <= 1200; i += 50) {
            items.add(i+"毫升");
        }
    }

    private Integer getDrinkNum(String strDrink) {
        if (strDrink.trim().equals("")) {
            return null;
        }
        String strNum = strDrink.substring(0,strDrink.length()-2);
        return Integer.valueOf(strNum);
    }
}
