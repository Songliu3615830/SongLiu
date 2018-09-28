package com.example.sonliu.drinkingalert.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sonliu.drinkingalert.DrinkAlertDefaultConstant;
import com.example.sonliu.drinkingalert.R;
import com.example.sonliu.drinkingalert.User.User;
import com.example.sonliu.drinkingalert.User.UserManager;
import com.example.sonliu.drinkingalert.datebase.data.Cup;
import com.example.sonliu.drinkingalert.datebase.data.DrinkRecord;

import java.util.Date;


public class DrinkWaterDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private ImageButton mBtnCup;
    private TextView mTxtCupNum, mTxtModify;
    private Button mBtnDrink;

    private Cup cup;
    private Integer mNumDrink;
    private User currentUser;



    public DrinkWaterDialog(@NonNull Context context, Cup cup, Integer numDrink) {
        super(context, R.style.TodayBottomDialog);
        mContext = context;
        this.cup = cup;
        mNumDrink = numDrink;
        currentUser = UserManager.getInstance().getCurrentUser();
        Window window = getWindow();
        WindowManager manager = getWindow().getWindowManager();
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获取屏幕宽高
        Display d = manager.getDefaultDisplay();
        Point size = new Point();
        d.getSize(size);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (size.x*0.8);
        attributes.height = (int) (size.y*0.4);
        attributes.gravity = Gravity.CENTER;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_today_right_empty);
        initUnSuccessView();
    }

    private void initUnSuccessView() {
        mBtnCup = findViewById(R.id.btn_right_dialog);
        mTxtCupNum = findViewById(R.id.txt_right_dialog);
        mBtnDrink = findViewById(R.id.btn_drink_right_dialog);
        mTxtModify = findViewById(R.id.txt_modify_right_dialog);

        mBtnCup.setImageResource(DrinkAlertDefaultConstant.IMGS_CUP[cup.getImg()]);
        mTxtCupNum.setText(mNumDrink+"毫升");
        mBtnDrink.setOnClickListener(this);
        mTxtModify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_drink_right_dialog:
                //点击喝水按钮
                currentUser.addRecord(new DrinkRecord(currentUser.getId(), mNumDrink, new Date().getTime()));
                setContentView(R.layout.dialog_today_right_click);
                ImageView imgIcon = findViewById(R.id.img_click_icon);
                TextView txt = findViewById(R.id.txt_click);
                if ((currentUser.setTodayNum().getTodayNum() - currentUser.getDayPlan()) < 0) {
                    //未达标
                    txt.setText("喝水：+" + mNumDrink + "毫升");
                } else {
                    imgIcon.setImageResource(R.drawable.img_goal);
                    txt.setTextSize(14);
                    txt.setText(getContext().getString(R.string.alert_click_success));
                }
                break;
            case R.id.txt_modify_right_dialog:
                //点击修改杯子
                dismiss();
                CupSettingDialog bottomCupSettingDialog =
                        new CupSettingDialog(mContext, cup);
                bottomCupSettingDialog.show();
        }
    }

    /**
     * 修改杯子形状
     */
    //未完成



}
