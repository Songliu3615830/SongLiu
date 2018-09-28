package com.example.sonliu.drinkingalert.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
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
import com.example.sonliu.drinkingalert.ui.adapter.UserSettingAdapter;
import com.example.sonliu.drinkingalert.util.ToastUtils;

import java.util.List;

/**
 * 我的设置弹出框
 */
public class UserSettingBottomDialog extends Dialog implements View.OnClickListener {
//    private static final String TAG = "UserSettingBottomDialog:";
    private Context mContext;
    private Button mBtnClose;
    private RecyclerView mClyListSetting;
    private NameSettingDialog nameSettingDialog;
    private boySettingDialog boySettingDialog;
    private UserSettingAdapter clyAdapter;
    private User mainUser;
    private List<User> children;

    public UserSettingBottomDialog(@NonNull Context context) {
        super(context, R.style.TodayBottomDialog);
        mContext = context;
        if (this.nameSettingDialog == null) {
            this.nameSettingDialog = new NameSettingDialog(mContext);
        }
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        Display d = window.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        d.getSize(size);
        attributes.height = (int) (size.y*0.45);
//        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_me_user_setting);
        mBtnClose = findViewById(R.id.btn_select_close);
        mClyListSetting = findViewById(R.id.list_setting);
        initData();
        //关闭按钮
        mBtnClose.setOnClickListener(this);
        //设置recycle
        final GridLayoutManager layoutManager = new GridLayoutManager(mContext, 1);
        mClyListSetting.setLayoutManager(layoutManager);
        setClyAdapter();
        mClyListSetting.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select_close:
                UserSettingBottomDialog.this.dismiss();
                break;
        }
    }

    private void initData() {
        mainUser = UserManager.getInstance().getManUser();
        children = UserManager.getInstance().getChildUser();
    }

    private void setClyAdapter() {
        UserSettingAdapter.OnItemClickedListener itemClickedListener =
                new UserSettingAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int item) {
                children = UserManager.getInstance().getChildUser();
                switch (item) {
                    case 0:
                        nameSettingDialog.setNameisChangedListener(new NameSettingDialog.OnNameisChangedListener() {
                            @Override
                            public void onNameisChanged(String name) {
                                mainUser.setName(name);
                                mClyListSetting.setAdapter(clyAdapter);
                                mClyListSetting.invalidate();
                            }
                        });
                        nameSettingDialog.show();
                        break;
                    case 1:
                        User user = null;
                        if (children.size() >= 1) {
                            //点击的是孩子1
                            user = children.get(0);
                        }
                        if (children.size() == 0) {
                            //点击的是添加孩子
                            UserManager.getInstance().addUser(1).initUsers();
                        }
                        boySettingDialog = new boySettingDialog(mContext, user);
                        boySettingDialog.setOnDismissListener(new OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                UserSettingBottomDialog.this.dismiss();
                            }
                        });
                        if (boySettingDialog != null) {
                            boySettingDialog.show();
                        } else {
                            ToastUtils.shortToast(mContext, "并没有孩子1");
                        }
                        break;
                    case 2:
                        User user1 = null;
                        if (children.size() == 2) {
                            //点击的是孩子2
                            user1 = children.get(1);

                        }
                        if (children.size() == 1) {
                            //点击的是添加孩子
                            UserManager.getInstance().addUser(1).initUsers();
                        }
                        boySettingDialog = new boySettingDialog(mContext, user1);
                        boySettingDialog.setOnDismissListener(new OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                UserSettingBottomDialog.this.dismiss();
                            }
                        });
                        if (boySettingDialog != null) {
                            boySettingDialog.show();
                        } else {
                            ToastUtils.shortToast(mContext, "并没有孩子2");
                        }
                        break;
                }
            }
        };
        if (children == null || children.size() == 0) {
            //没有孩子
            clyAdapter = new UserSettingAdapter(mContext,
                    mainUser.getName() == null ? "" : mainUser.getName(), itemClickedListener);
        }
        if (children != null && children.size() == 1) {
            //有一个孩子
            clyAdapter = new UserSettingAdapter(mContext,
                    mainUser.getName() == null ? "" : mainUser.getName(),
                    children.get(0).getName() == null ? "" : children.get(0).getName(),
                    children.get(0).getIcon(), itemClickedListener);
        }
        if (children != null && children.size() == 2) {
            //有两个孩子
            clyAdapter = new UserSettingAdapter(mContext,
                    mainUser.getName() == null ? "" : mainUser.getName(),
                    children.get(0).getName() == null ? "" : children.get(0).getName(),
                    children.get(1).getName() == null ? "" : children.get(1).getName(),
                    children.get(0).getIcon(), children.get(1).getIcon(), itemClickedListener);
        }
        mClyListSetting.setAdapter(clyAdapter);
    }
}
