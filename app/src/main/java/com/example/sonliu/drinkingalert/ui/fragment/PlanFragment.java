package com.example.sonliu.drinkingalert.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sonliu.drinkingalert.R;
import com.example.sonliu.drinkingalert.User.User;
import com.example.sonliu.drinkingalert.User.UserManager;
import com.example.sonliu.drinkingalert.datebase.data.Alarm;
import com.example.sonliu.drinkingalert.datebase.data.UserPlan;
import com.example.sonliu.drinkingalert.datebase.Modify.UserPlanChangedListenerAdapter;
import com.example.sonliu.drinkingalert.ui.adapter.PlanFragmentRecAdapter;
import com.example.sonliu.drinkingalert.ui.dialog.DrinkPlanSettingDialog;

import java.util.List;

import static com.example.sonliu.drinkingalert.DrinkAlertDefaultConstant.IMGS_PERSON;

public class PlanFragment extends Fragment implements View.OnClickListener{
//    private static final String TAG = "PlanFragment";
    private Context mContext;
    private List<Alarm> alarms;//闹钟
    private RecyclerView mRecPlanAlarm;//闹钟表
    private CardView mCardDayDrink;
    private User currentUser;
    private TextView mTxtNum;
    private View view;
    //头部（选择用户）
    private ConstraintLayout mClyBar;
    private ImageButton mBtnCurrent, mBtnOne, mBtnTwo;
    private TextView mTxTCurrentName;

    public static PlanFragment newInstance() {
        return new PlanFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan_main, container, false);
        mContext = getActivity();
        bindView(view);
        //添加分割线
        mRecPlanAlarm.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        return view;
    }

    private void bindView(View view) {
        mCardDayDrink = view.findViewById(R.id.card_day_drink);
        mRecPlanAlarm = view.findViewById(R.id.rly_plan_alarm);
        mTxtNum = view.findViewById(R.id.txt_card_day_drink_num);
        mClyBar = view.findViewById(R.id.cly_bar);
        mTxTCurrentName = view.findViewById(R.id.txt_main_name);
        mBtnCurrent = view.findViewById(R.id.img_main);
        mBtnOne = view.findViewById(R.id.img_child_one);
        mBtnTwo = view.findViewById(R.id.img_child_two);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentUser = UserManager.getInstance().getCurrentUser();
        initBar();
        initData();
        initView(view);
    }

    private void initView(View view) {
        mCardDayDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改 计划喝水量
                DrinkPlanSettingDialog drinkPlanSettingBottomDialog =
                        new DrinkPlanSettingDialog(mContext);
                drinkPlanSettingBottomDialog.show();
            }
        });

        //闹钟recycleView
        final PlanFragmentRecAdapter adapter = new PlanFragmentRecAdapter(getActivity(), alarms);
        setRecycleView(adapter);

        //根据User属性修改ui
        currentUser.setOnUserPlanChangedListener(new UserPlanChangedListenerAdapter(){
            @Override
            public void onDayDrinkChanged(UserPlan userPlan) {
                super.onDayDrinkChanged(userPlan);
                mTxtNum.setText(currentUser.getDayPlan().toString());
            }
        });
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

    private void initData() {
        currentUser = UserManager.getInstance().getCurrentUser();
        mTxtNum.setText(currentUser.getDayPlan().toString());
        alarms = currentUser.getAlarms();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //再一次切回fragment 刷新页面
            if (view != null) {
                bindView(view);
                initBar();
                initData();
                initView(view);
            }
        }
    }
    /* 设置闹钟表adapter */
    private void setRecycleView(PlanFragmentRecAdapter adapter) {
        mRecPlanAlarm.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mRecPlanAlarm.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        //头部currentUser选择栏
        UserManager userManager = UserManager.getInstance();
        switch (v.getId()) {
            case R.id.img_child_one:
                //孩子一
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
                if (view != null) {
                    bindView(view);
                    initBar();
                    initData();
                    initView(view);
                }
                break;
            case R.id.img_child_two:
                //孩子二
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
                if (view != null) {
                    bindView(view);
                    initBar();
                    initData();
                    initView(view);
                }
                break;
        }
    }
}