package com.example.sonliu.drinkingalert.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sonliu.drinkingalert.R;
import com.example.sonliu.drinkingalert.User.User;
import com.example.sonliu.drinkingalert.User.UserManager;

import java.util.List;

import static com.example.sonliu.drinkingalert.DrinkAlertDefaultConstant.IMGS_PERSON;

public class ReportFragment extends Fragment implements View.OnClickListener{
    private User currentUser;
    private TextView mTxtTolNum, mTxtTolDayNum, mTxtSuccessDayNum, mTxtAvgNum, mTxtMaxNum, mTxtBalance;
    private ConstraintLayout mClyBar;
    private ImageButton mBtnCurrent, mBtnOne, mBtnTwo;
    private TextView mTxTCurrentName;

    public static ReportFragment newInstance() {
        return new ReportFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_main, container, false);
        currentUser = UserManager.getInstance().getCurrentUser();
        bindView(view);
        initBar();
        return view;
    }

    private void bindView(View view) {
        mTxtTolNum = view.findViewById(R.id.txt_tol_num);
        mTxtTolDayNum = view.findViewById(R.id.txt_tol_day_num);
        mTxtSuccessDayNum = view.findViewById(R.id.txt_success_day_num);
        mTxtAvgNum = view.findViewById(R.id.txt_avg_day_num);
        mTxtMaxNum = view.findViewById(R.id.txt_max_day_num);
        mTxtBalance = view.findViewById(R.id.txt_water_balance);
        //bar
        mClyBar = view.findViewById(R.id.cly_bar);
        mTxTCurrentName = view.findViewById(R.id.txt_main_name);
        mBtnCurrent = view.findViewById(R.id.img_main);
        mBtnOne = view.findViewById(R.id.img_child_one);
        mBtnTwo = view.findViewById(R.id.img_child_two);
    }

    /**
     * 初始化头部CurrentUserSelect
     */
    private void initBar() {
        List<User> users = UserManager.getUsers();
        UserManager userManager = UserManager.getInstance();
        currentUser = userManager.getCurrentUser();
        if (currentUser == null) {
            userManager.setCurrentUser(userManager.getManUser());
        }
        switch (users.size()) {
            case 1:
                mClyBar.setVisibility(View.GONE);
                break;
            case 2:
                mClyBar.setVisibility(View.VISIBLE);
                mBtnOne.setVisibility(View.VISIBLE);
                mBtnTwo.setVisibility(View.GONE);
                User one = userManager.getChildUser().get(0);
                mTxTCurrentName.setText(currentUser.getName()==null? "" : currentUser.getName());
                if (currentUser.getId().equals(one.getId())) {
                    //当前用户为孩子1
                    mBtnCurrent.setImageResource(IMGS_PERSON[one.getIcon()]);
                    mBtnOne.setImageResource(IMGS_PERSON[0]);
                } else {
                    mBtnCurrent.setImageResource(IMGS_PERSON[0]);
                    mBtnOne.setImageResource(IMGS_PERSON[one.getIcon()]);
                }
                break;
            case 3:
                mClyBar.setVisibility(View.VISIBLE);
                mBtnOne.setVisibility(View.VISIBLE);
                mBtnTwo.setVisibility(View.VISIBLE);
                User boy1 = userManager.getChildUser().get(0);
                User boy2 = userManager.getChildUser().get(1);
                mTxTCurrentName.setText(currentUser.getName()==null? "" : currentUser.getName());
                if (currentUser.getId().equals(boy1.getId())) {
                    //当前用户孩子1
                    mBtnCurrent.setImageResource(IMGS_PERSON[boy1.getIcon()]);
                    mBtnOne.setImageResource(IMGS_PERSON[0]);
                    mBtnTwo.setImageResource(IMGS_PERSON[boy2.getIcon()]);
                } else if (currentUser.getId().equals(boy2.getId())) {
                    //当前用户孩子2
                    mBtnCurrent.setImageResource(IMGS_PERSON[boy2.getIcon()]);
                    mBtnTwo.setImageResource(IMGS_PERSON[0]);
                    mBtnOne.setImageResource(IMGS_PERSON[boy1.getIcon()]);
                } else {
                    mBtnCurrent.setImageResource(IMGS_PERSON[0]);
                    mBtnOne.setImageResource(IMGS_PERSON[boy1.getIcon()]);
                    mBtnTwo.setImageResource(IMGS_PERSON[boy2.getIcon()]);
                }
                break;
        }
        mBtnOne.setOnClickListener(this);
        mBtnTwo.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mTxtTolNum != null) {
                initBar();
                initData();
            }
        }
    }

    /**
     * 初始化数据刷新
     */
    private void initData() {
        //刷新数据
        currentUser.setTolNum().setTolDay().setSuccessDay().setAvgDayNum().setMaxDayNum();
        //获取数据
        mTxtTolNum.setText(currentUser.getTolNum() + "");
        mTxtTolDayNum.setText(currentUser.getTolDay() + "");
        mTxtSuccessDayNum.setText(currentUser.getSuccessDay() + "");
        mTxtAvgNum.setText(currentUser.getAvgDayNum() + "");
        mTxtMaxNum.setText(currentUser.getMaxDayNum() + "");
        //判断水平衡指数
        if (currentUser.getAvgDayNum() > 1000 && currentUser.getAvgDayNum() < 2000) {
            mTxtBalance.setText(getContext().getString(R.string.report_water_balance_good));
            mTxtBalance.setBackground(getContext().getDrawable(R.drawable.shape_water_balance_good));
        } else if (currentUser.getAvgDayNum() >= 2000) {
            mTxtBalance.setText(getContext().getString(R.string.report_water_balance_super));
            mTxtBalance.setBackground(getContext().getDrawable(R.drawable.shape_water_balance_super));
        } else if (currentUser.getAvgDayNum() <= 1000) {
            mTxtBalance.setText(getContext().getString(R.string.report_water_balance_bad));
            mTxtBalance.setBackground(getContext().getDrawable(R.drawable.shape_water_balance_bad));
        }

    }

    @Override
    public void onClick(View v) {
        UserManager userManager = UserManager.getInstance();
        switch (v.getId()) {
            case R.id.img_child_one:
                User childOne = userManager.getChildUser().get(0);
                Integer mainId = userManager.getManUser().getId();
                Integer childOneId = childOne.getId();
                if (currentUser.getId().equals(mainId)) {
                    //设置孩子1为当前用户
                    userManager.setCurrentUser(childOne);
                    mBtnCurrent.setImageResource(IMGS_PERSON[childOne.getIcon()]);
                    mBtnOne.setImageResource(IMGS_PERSON[0]);
                    currentUser = userManager.getCurrentUser();
                } else if (currentUser.getId().equals(childOneId)){
                    //设置成人为当前用户
                    userManager.setCurrentUser(userManager.getManUser());
                    mBtnCurrent.setImageResource(IMGS_PERSON[0]);
                    mBtnOne.setImageResource(IMGS_PERSON[childOne.getIcon()]);
                }
                currentUser = userManager.getCurrentUser();
                mTxTCurrentName.setText(currentUser.getName()==null? "" : currentUser.getName());
                initData();
                initBar();
                break;
            case R.id.img_child_two:
                User child2 = userManager.getChildUser().get(1);
                User child1 = userManager.getChildUser().get(0);
                User man = userManager.getManUser();
                if (currentUser.getId().equals(man.getId())) {
                    //主用户是成人
                    userManager.setCurrentUser(child2);
                    mBtnTwo.setImageResource(man.getIcon());
                    mBtnCurrent.setImageResource(IMGS_PERSON[child2.getIcon()]);
                    mBtnTwo.setImageResource(IMGS_PERSON[0]);
                } else if (currentUser.getId().equals(child1.getId())) {
                    //主用户是孩子1
                    userManager.setCurrentUser(child2);
                    mBtnCurrent.setImageResource(IMGS_PERSON[child2.getIcon()]);
                    mBtnTwo.setImageResource(IMGS_PERSON[child1.getIcon()]);
                } else if (currentUser.getId().equals(child2.getId())) {
                    userManager.setCurrentUser(man);
                    mBtnCurrent.setImageResource(IMGS_PERSON[0]);
                    mBtnTwo.setImageResource(IMGS_PERSON[child2.getIcon()]);
                }
                currentUser = userManager.getCurrentUser();
                mTxTCurrentName.setText(currentUser.getName()==null? "" : currentUser.getName());
                initData();
                initBar();
                break;
        }
    }
}
