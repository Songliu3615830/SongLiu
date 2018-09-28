package com.example.sonliu.drinkingalert.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


import com.example.sonliu.drinkingalert.R;
import com.example.sonliu.drinkingalert.User.User;
import com.example.sonliu.drinkingalert.User.UserManager;
import com.example.sonliu.drinkingalert.datebase.data.Cup;
import com.example.sonliu.drinkingalert.ui.adapter.BottomDialogRecAdapter;

public class CupSettingDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private Button mBtnClose;
    private RecyclerView recTodaySelect;
    private Cup clickCup;
    private User currentUser;

    public CupSettingDialog(@NonNull Context context, Cup clickCup) {
        super(context, R.style.TodayBottomDialog);
        mContext = context;
        Window window = getWindow();
        this.clickCup = clickCup;
        currentUser = UserManager.getInstance().getCurrentUser();
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
        setContentView(R.layout.dialog_today_empty);
        recTodaySelect = findViewById(R.id.rec_today_select);
        final GridLayoutManager layoutManager = new GridLayoutManager(mContext, 4);
        recTodaySelect.setLayoutManager(layoutManager);
        mBtnClose = findViewById(R.id.btn_select_close);
        recTodaySelect.setAdapter(new BottomDialogRecAdapter(mContext, clickCup, new BottomDialogRecAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                dismiss();
                clickCup.setImg(i);
                currentUser.modifyCup(clickCup);
                //选择杯子大小
                CupNumSettingDialog bottomCupNumSettingDialog =
                        new CupNumSettingDialog(mContext, clickCup);
                bottomCupNumSettingDialog.show();
            }
        }));
        mBtnClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select_close:
                CupSettingDialog.this.dismiss();
                break;
        }
    }
}
