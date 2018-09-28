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

/**
 * 孩子设置弹出框
 */
public class boySettingDialog extends Dialog implements View.OnClickListener {
//    private static final String TAG = "boySettingDialog:";
    private Context mContext;
    private Button mBtnClose;
    private Button mBtnChildNameSetting;//名字设置
    private ImageView mImgIconChild;//当前孩子头像
    private TextView mTxtChildName;//当前孩子名字
    private ImageButton mBtnChildOne, mBtnChildTwo, mBtnChildThree, mBtnChildFour;//设置孩子头像按钮
    private ImageView mImgSoliderOne, mImgSoliderTwo, mImgSoliderThree, mImgSoliderFour;//头像选中
    private Button mBtnNoAlert, mBtnConfirm, mBtnCancel;//不再提醒， 确认 ，取消
    private ImageView mImgRemove;//减号图片
    private TextView mTxtNoAlert;// “不再帮助孩子提醒”
    private NameSettingDialog nameSettingDialog;//设置名字的dialog

    private User child;

    public boySettingDialog(@NonNull Context context, User user) {
        super(context, R.style.TodayBottomDialog);
        mContext = context;
        this.nameSettingDialog = new NameSettingDialog(mContext);
        child = user;
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        Display d = window.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        d.getSize(size);
        attributes.height = (int) (size.y*0.6);
//        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_me_boy_setting);
        initView();
        initData();
    }

    private void initView() {
        mBtnClose = findViewById(R.id.btn_select_close);
        mBtnChildNameSetting = findViewById(R.id.btn_child_name_setting);
        mImgIconChild = findViewById(R.id.img_icon_child);
        mTxtChildName = findViewById(R.id.txt_child_name);
        mBtnChildOne = findViewById(R.id.btn_child_one);
        mBtnChildTwo = findViewById(R.id.btn_child_two);
        mBtnChildThree = findViewById(R.id.btn_child_three);
        mBtnChildFour = findViewById(R.id.btn_child_four);
        mImgSoliderOne = findViewById(R.id.img_solider_one);
        mImgSoliderTwo = findViewById(R.id.img_solider_two);
        mImgSoliderThree = findViewById(R.id.img_solider_three);
        mImgSoliderFour = findViewById(R.id.img_solider_four);
        mBtnNoAlert = findViewById(R.id.btn_no_alert);
        mBtnConfirm = findViewById(R.id.btn_alert_confirm);
        mBtnCancel = findViewById(R.id.btn_alert_cancel);
        mImgRemove = findViewById(R.id.img_alert_remove);
        mTxtNoAlert = findViewById(R.id.txt_no_alert);

        mBtnClose.setOnClickListener(this);
        mBtnChildNameSetting.setOnClickListener(this);
        mBtnChildOne.setOnClickListener(this);
        mBtnChildTwo.setOnClickListener(this);
        mBtnChildThree.setOnClickListener(this);
        mBtnChildFour.setOnClickListener(this);
        mBtnNoAlert.setOnClickListener(this);
        mBtnConfirm.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
    }

    private void initData() {
        if (child != null) {
            mTxtChildName.setText(child.getName());
            mImgIconChild.setImageResource(DrinkAlertDefaultConstant.IMGS_PERSON[child.getIcon()]);
            hideSoliders();
            switch (child.getIcon()) {
                case 1:
                    mImgSoliderOne.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    mImgSoliderTwo.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    mImgSoliderThree.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    mImgSoliderFour.setVisibility(View.VISIBLE);
                    break;
            }
        } else {
            if (UserManager.getUsers().size() == 3) {
                child = UserManager.getInstance().getChildUser().get(1);
            } else if (UserManager.getUsers().size() == 2) {
                child = UserManager.getInstance().getChildUser().get(0);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select_close:
                boySettingDialog.this.dismiss();
                break;
            case R.id.btn_child_one:
                hideSoliders();
                mImgSoliderOne.setVisibility(View.VISIBLE);
                mImgIconChild.setImageResource(DrinkAlertDefaultConstant.IMGS_PERSON[1]);
                /**
                 * 修改user头像
                 */
                child.setIcon(1);
                initNoAlert();
                break;
            case R.id.btn_child_two:
                hideSoliders();
                mImgSoliderTwo.setVisibility(View.VISIBLE);
                mImgIconChild.setImageResource(DrinkAlertDefaultConstant.IMGS_PERSON[2]);
                /**
                 * 修改user头像
                 */
                child.setIcon(2);
                initNoAlert();
                break;
            case R.id.btn_child_three:
                hideSoliders();
                mImgSoliderThree.setVisibility(View.VISIBLE);
                mImgIconChild.setImageResource(DrinkAlertDefaultConstant.IMGS_PERSON[3]);
                /**
                 * 修改user头像
                 */
                child.setIcon(3);
                initNoAlert();
                break;
            case R.id.btn_child_four:
                hideSoliders();
                mImgSoliderFour.setVisibility(View.VISIBLE);
                mImgIconChild.setImageResource(DrinkAlertDefaultConstant.IMGS_PERSON[4]);
                /**
                 * 修改user头像
                 */
                child.setIcon(4);
                initNoAlert();
                break;
            case R.id.btn_child_name_setting:
                nameSettingDialog.show();
                nameSettingDialog.setNameisChangedListener(new NameSettingDialog.OnNameisChangedListener() {
                    @Override
                    public void onNameisChanged(String name) {
                        mTxtChildName.setText(name);
                        /**
                         * 修改user 的 name属性
                         */
                        child.setName(name);
                    }
                });
                initNoAlert();
                break;
            case R.id.btn_no_alert:
                mBtnNoAlert.setVisibility(View.GONE);
                mTxtNoAlert.setTextColor(getContext().getColor(R.color.colorInit));
                mImgRemove.setImageResource(R.drawable.img_remove_g);
                mBtnConfirm.setVisibility(View.VISIBLE);
                mBtnCancel.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_alert_confirm:
                /**
                 * 删除user(孩子)
                 */
                if (UserManager.getInstance().getCurrentUser().getId().equals(child.getId())) {
                    UserManager.getInstance().setCurrentUser(UserManager.getInstance().getManUser());
                }
                UserManager.getInstance().delete(child.getId()).initUsers();
                dismiss();
                break;
            case R.id.btn_alert_cancel:
                initNoAlert();
                break;
        }
    }


    /* 隐藏所有选择框 */
    private void hideSoliders() {
        mImgSoliderOne.setVisibility(View.GONE);
        mImgSoliderTwo.setVisibility(View.GONE);
        mImgSoliderThree.setVisibility(View.GONE);
        mImgSoliderFour.setVisibility(View.GONE);
    }
    /* 初始化不再提醒 */
    private void initNoAlert() {
        mBtnNoAlert.setVisibility(View.VISIBLE);
        mTxtNoAlert.setTextColor(getContext().getColor(R.color.colorActionBar));
        mImgRemove.setImageResource(R.drawable.img_remove_w);
        mBtnConfirm.setVisibility(View.GONE);
        mBtnCancel.setVisibility(View.GONE);
    }

}
