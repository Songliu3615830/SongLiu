package com.example.sonliu.drinkingalert.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sonliu.drinkingalert.DrinkAlertDefaultConstant;
import com.example.sonliu.drinkingalert.R;
import com.example.sonliu.drinkingalert.User.User;
import com.example.sonliu.drinkingalert.User.UserManager;
import com.example.sonliu.drinkingalert.datebase.data.UserPlan;
import com.example.sonliu.drinkingalert.datebase.Modify.UserPlanChangedListenerAdapter;
import com.example.sonliu.drinkingalert.ui.dialog.AlertTypeDialog;
import com.example.sonliu.drinkingalert.ui.dialog.UserSettingBottomDialog;
import com.example.sonliu.drinkingalert.util.PackageUtils;

import java.util.List;

public class MeFragment extends Fragment implements View.OnClickListener {
//    public static final String TAG = "MeFragment:";

    private String name = DrinkAlertDefaultConstant.DEAFLUT_MAIN_NAME;//名字
    private boolean isAlert = DrinkAlertDefaultConstant.DEFALUT_IS_ALERT;//是否提醒
    private int alertType = DrinkAlertDefaultConstant.DEFALUT_ALERT_TYPE;//提醒方式
    private String version = PackageUtils.packageName();//版本号

    private Button mBtnAlert, mBtnBar, mBtnAlertType;//设置按钮
    private ImageView mSwitch, mImgChildOne, mImgChildTwo;//提醒开关图片 孩子1孩子2头像
    private TextView mTxtAlertType, mTxtMainName, mTxtVersion;//提醒方式  用户名 和 版本号
    private UserSettingBottomDialog mUserSettingBottomDialog;//用户设置弹出框
    private AlertTypeDialog mAlertTypeDialog;//提醒方式设置弹出框
    private View view;//填充fragment的view
    private User mainUser;//成人用户
    private List<User> children;//孩子用户们

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me_main, container, false);
        bindView(view);
        initData();
        initView(view);
        return view;
    }
    /* 绑定组件 */
    private void bindView(View view) {
        mSwitch = view.findViewById(R.id.switch_me_alert);
        mBtnAlert = view.findViewById(R.id.btn_me_alert);
        mBtnAlertType = view.findViewById(R.id.btn_me_alertType);
        mBtnBar = view.findViewById(R.id.btn_me_bar);
        mImgChildOne = view.findViewById(R.id.img_me_child_one);
        mImgChildTwo = view.findViewById(R.id.img_me_child_two);
        mTxtMainName = view.findViewById(R.id.txt_me_main_name);
        mTxtAlertType = view.findViewById(R.id.txt_me_set_alert);
        mTxtVersion = view.findViewById(R.id.txt_me_set_version);
    }
    /* 数据初始化 */
    private void initData() {
        mainUser = UserManager.getInstance().getManUser();
        children = UserManager.getInstance().getChildUser();
        //提醒条件默认为成人用户设置
        isAlert = mainUser.getIsAlert() == 1;
        alertType = mainUser.getAlertType();
        if (mainUser == null || "".equals(mainUser.getName())) {
            name = "";
        } else {
            name = mainUser.getName() == null ? "" : mainUser.getName();
        }

    }

    /* 初始化View */
    private void initView(View view) {
        //设置名字
        mTxtMainName.setText(name);
        //设置孩子
        mImgChildOne.setVisibility(View.GONE);
        mImgChildTwo.setVisibility(View.GONE);
        if (children != null && children.size() > 0) {
            if (children.size() >= 1) {
                mImgChildOne.setImageResource(DrinkAlertDefaultConstant.IMGS_PERSON[children.get(0).getIcon()]);
                mImgChildOne.setVisibility(View.VISIBLE);
            }
            if (children.size() >= 2) {
                mImgChildTwo.setImageResource(DrinkAlertDefaultConstant.IMGS_PERSON[children.get(1).getIcon()]);
                mImgChildTwo.setVisibility(View.VISIBLE);
            }
        }
        //设置提醒开关
        mSwitch.setImageResource(R.drawable.slib_img_on);
        if (!isAlert) {
            mSwitch.setImageResource(R.drawable.slib_img_off);
        }
        //提醒方式
        mTxtAlertType.setText(setAlertType(alertType));
        //版本号
        mTxtVersion.setText(version);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnBar.setOnClickListener(this);
        mBtnAlert.setOnClickListener(this);
        mBtnAlertType.setOnClickListener(this);

        mainUser.setOnUserPlanChangedListener(new UserPlanChangedListenerAdapter() {
            @Override
            public void onNameChanged(UserPlan userPlan) {
                super.onNameChanged(userPlan);
                if (userPlan.getType() == 0) {
                    mTxtMainName.setText(userPlan.getName());
                }
            }
        });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (view != null) {
                initData();
                initView(view);
            }
        }
    }

    /**
     * 获得提醒方式 （int -> String）
     * @param alertType 提醒方式编码
     * @return alert 提醒方式字符串
     */
    private String setAlertType(int alertType) {
        String alert = "振动加提示音";
        switch (alertType) {
            case 0:
                alert = "无声";
                break;
            case 1:
                alert = "仅振动";
                break;
            case 2:
                alert = "仅提示音";
                break;
            case 3:
                break;
        }
        return alert;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_me_bar:
                //用户设置底部弹出框
                mUserSettingBottomDialog = new UserSettingBottomDialog(getActivity());
                mUserSettingBottomDialog.show();
                mUserSettingBottomDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        initData();
                        initView(view);
                    }
                });
                break;
            case R.id.btn_me_alert:
                if (isAlert) {
                    //设置不提醒
                    mSwitch.setImageResource(R.drawable.slib_img_off);
                    isAlert = false;
                } else {
                    //设置提醒
                    mSwitch.setImageResource(R.drawable.slib_img_on);
                    isAlert = true;
                }
                mainUser.setIsAlert(isAlert ? 1 : 0);
                break;
            case R.id.btn_me_alertType:
                if (mAlertTypeDialog == null) {
                    mAlertTypeDialog = new AlertTypeDialog(getActivity(),
                            mTxtAlertType.getText().toString(),
                            new AlertTypeDialog.AlertTypeWheelListener() {
                                @Override
                                public void selectedItem(String item) {
                                    mTxtAlertType.setText(item);
                                }
                            });
                }
                mAlertTypeDialog.show();
                break;
        }
    }
}
