package com.example.sonliu.drinkingalert.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sonliu.drinkingalert.DrinkAlertApp;
import com.example.sonliu.drinkingalert.DrinkAlertDefaultConstant;
import com.example.sonliu.drinkingalert.R;
import com.example.sonliu.drinkingalert.User.User;
import com.example.sonliu.drinkingalert.User.UserManager;
import com.example.sonliu.drinkingalert.datebase.data.Cup;
import com.example.sonliu.drinkingalert.datebase.data.DrinkRecord;
import com.example.sonliu.drinkingalert.datebase.Modify.onCupChangedListener;
import com.example.sonliu.drinkingalert.datebase.Modify.onDrinkRecordChangedListener;
import com.example.sonliu.drinkingalert.ui.dialog.CupSettingDialog;
import com.example.sonliu.drinkingalert.ui.dialog.DrinkWaterDialog;

import java.util.List;

import static com.example.sonliu.drinkingalert.DrinkAlertDefaultConstant.IMGS_PERSON;

public class TodayFragment extends Fragment implements View.OnClickListener {
//    private static final String TAG = "TodayFragment:";
    private User currentUser;
    private Context mContext;
    //bar
    private ConstraintLayout mClyBar;
    private ImageButton mBtnCurrent, mBtnOne, mBtnTwo;
    private TextView mTxTCurrentName;
    //content
    private ImageView mImgIcon;//是否完成喝水目标标志
    private TextView mTxtCard, mTxtCardNum;//今日喝水和喝水量
    private ImageButton mBtnTodayOne, mBtnTodayTwo, mBtnTodayThree, mBtnTodayFour;//水杯
    private TextView mTxtTodayOne, mTxtTodayTwo, mTxtTodayThree, mTxtTodayFour;//水杯容量
    private DrinkWaterDialog drinkWaterDialog;//点击喝水弹窗

    public static TodayFragment newInstance() {
        return new TodayFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_main, container, false);
//        Log.d(TAG, "onCreateView: ");
        mContext = getContext();
        BindView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Log.d(TAG, "onViewCreated: ");
        mBtnTodayOne.setOnClickListener(this);
        mBtnTodayTwo.setOnClickListener(this);
        mBtnTodayThree.setOnClickListener(this);
        mBtnTodayFour.setOnClickListener(this);
        initBar();
        initData();
        hideZeroNum(mTxtTodayOne);
        hideZeroNum(mTxtTodayTwo);
        hideZeroNum(mTxtTodayThree);
        hideZeroNum(mTxtTodayFour);
    }

    private void BindView(View view) {
        //card
        mImgIcon = view.findViewById(R.id.img_today_icon);
        mTxtCard = view.findViewById(R.id.txt_card_today);
        mTxtCardNum = view.findViewById(R.id.txt_num_card_today);
        //水杯
        mBtnTodayOne = view.findViewById(R.id.btn_today_one);
        mBtnTodayTwo = view.findViewById(R.id.btn_today_two);
        mBtnTodayThree = view.findViewById(R.id.btn_today_three);
        mBtnTodayFour = view.findViewById(R.id.btn_today_four);
        mTxtTodayOne = view.findViewById(R.id.txt_today_one);
        mTxtTodayTwo = view.findViewById(R.id.txt_today_two);
        mTxtTodayThree = view.findViewById(R.id.txt_today_three);
        mTxtTodayFour = view.findViewById(R.id.txt_today_four);
        //bar
        mClyBar = view.findViewById(R.id.cly_bar);
        mTxTCurrentName = view.findViewById(R.id.txt_main_name);
        mBtnCurrent = view.findViewById(R.id.img_main);
        mBtnOne = view.findViewById(R.id.img_child_one);
        mBtnTwo = view.findViewById(R.id.img_child_two);
    }

    private void initData() {
        currentUser = UserManager.getInstance().getCurrentUser();
        initCup();
        //判断是否达标
        isSuccessUISetting();
        //修改目标ui
        currentUser.setOnDrinkRecordChangedListener(new onDrinkRecordChangedListener() {
            @Override
            public void onAddDrinkRecord(DrinkRecord drinkRecord) {
                Integer num = currentUser.getDayPlan() - currentUser.setTodayNum().getTodayNum();
                if (num.intValue() > 0) {
                    //未达到目标
                    mImgIcon.setImageResource(R.drawable.img_water_drop);
                    mTxtCard.setText(getString(R.string.alert_click_unsuccess));
                    mTxtCardNum.setText(num.toString());
                } else {
                    //达到目标
                    modifySuccess();
                }
            }

            @Override
            public void onDeleteDrinkRecord(Integer uid) { }
        });

        //修改杯子ui
        currentUser.setOnCupChangedListener(new onCupChangedListener() {
            @Override
            public void onCupChanged(Cup cup) {
                initCup();
                hideZeroNum(mTxtTodayOne);
                hideZeroNum(mTxtTodayTwo);
                hideZeroNum(mTxtTodayThree);
                hideZeroNum(mTxtTodayFour);
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
        mTxTCurrentName.setText(currentUser.getName()==null ? "" : currentUser.getName());
        mBtnOne.setOnClickListener(this);
        mBtnTwo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        UserManager userManager = UserManager.getInstance();
        switch (v.getId()) {
            case R.id.btn_today_one:
                Integer numOne = getDrinkNum(mTxtTodayOne);
                if (numOne != null) {
                    drinkWaterDialog = new DrinkWaterDialog(mContext,
                            currentUser.getCups().get(0), numOne);
                    drinkWaterDialog.show();
                } else {
                    CupSettingDialog bottomCupSettingDialog =
                            new CupSettingDialog(mContext, currentUser.getCups().get(0));
                    bottomCupSettingDialog.show();
                }
                break;
            case R.id.btn_today_two:
                Integer numTwo = getDrinkNum(mTxtTodayTwo);
                if (numTwo != null) {
                    drinkWaterDialog = new DrinkWaterDialog(mContext,
                            currentUser.getCups().get(1), numTwo);
                    drinkWaterDialog.show();
                } else {
                    CupSettingDialog bottomCupSettingDialog =
                            new CupSettingDialog(mContext, currentUser.getCups().get(1));
                    bottomCupSettingDialog.show();
                }
                break;
            case R.id.btn_today_three:
                Integer numThree = getDrinkNum(mTxtTodayThree);
                if (numThree != null) {
                    drinkWaterDialog = new DrinkWaterDialog(mContext,
                            currentUser.getCups().get(2), numThree);
                    drinkWaterDialog.show();
                } else {
                    CupSettingDialog bottomCupSettingDialog =
                            new CupSettingDialog(mContext, currentUser.getCups().get(2));
                    bottomCupSettingDialog.show();
                }
                break;
            case R.id.btn_today_four:
                Integer numFour = getDrinkNum(mTxtTodayFour);
                if (numFour != null) {
                    drinkWaterDialog = new DrinkWaterDialog(mContext,
                            currentUser.getCups().get(3), numFour);
                    drinkWaterDialog.show();
                } else {
                    CupSettingDialog bottomCupSettingDialog =
                            new CupSettingDialog(mContext, currentUser.getCups().get(3));
                    bottomCupSettingDialog.show();
                }
                break;
            //以下是点击bar
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
                initBar();
                initData();
                hideZeroNum(mTxtTodayOne);
                hideZeroNum(mTxtTodayTwo);
                hideZeroNum(mTxtTodayThree);
                hideZeroNum(mTxtTodayFour);
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
                initBar();
                initData();
                hideZeroNum(mTxtTodayOne);
                hideZeroNum(mTxtTodayTwo);
                hideZeroNum(mTxtTodayThree);
                hideZeroNum(mTxtTodayFour);
                break;
        }
    }

    private Integer getDrinkNum(TextView txt) {
        String strDrink = txt.getText().toString();
        if (strDrink.trim().equals("")) {
            return null;
        }
        String strNum = strDrink.substring(0, strDrink.length() - 2);
        return Integer.valueOf(strNum);
    }

    /**
     * 修改水杯UI
     */
    private void modifyCup(ImageButton btnCup, TextView txtNum, Cup cup) {
        btnCup.setImageResource(DrinkAlertDefaultConstant.IMGS_CUP[cup.getImg()]);
        txtNum.setText(cup.getNum() + "毫升");
    }

    /**
     * 达标修改ui
     */
    private void modifySuccess() {
        mTxtCard.setText(DrinkAlertApp.getContext().getString(R.string.today_success_top));
        mImgIcon.setImageResource(R.drawable.img_goal);
        mTxtCardNum.setText(currentUser.setTodayNum().getTodayNum() + "");
    }

    /**
     * 隐藏为0的num
     *
     * @param txtNum
     */
    private void hideZeroNum(TextView txtNum) {
        Integer num = getDrinkNum(txtNum);
        if (num != null && num.equals(0)) {
            txtNum.setText("");
        }
    }

    /**
     * 达标ui设置
     */
    private void isSuccessUISetting() {
        currentUser = UserManager.getInstance().getCurrentUser();
        if ((currentUser.setTodayNum().getTodayNum() - currentUser.getDayPlan()) >= 0) {
            //达标 修改ui
            modifySuccess();
        } else {
            //未达标
            mImgIcon.setImageResource(R.drawable.img_water_drop);
            mTxtCard.setText(DrinkAlertApp.getContext().getString(R.string.alert_click_unsuccess));
            mTxtCardNum.setText((currentUser.getDayPlan() - currentUser.getTodayNum()) + "");
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mTxtCardNum != null) {
                isSuccessUISetting();
                initBar();
                initData();
                hideZeroNum(mTxtTodayOne);
                hideZeroNum(mTxtTodayTwo);
                hideZeroNum(mTxtTodayThree);
                hideZeroNum(mTxtTodayFour);
//                Log.d(TAG, "setUserVisibleHint: 不为空");
            } else {
//                Log.d(TAG, "setUserVisibleHint: 为空");
            } 
        }
    }

    private void initCup() {
        List<Cup> cups = currentUser.getCups();
        if (cups != null && cups.size() == 4) {
            modifyCup(mBtnTodayOne, mTxtTodayOne, cups.get(0));
            modifyCup(mBtnTodayTwo, mTxtTodayTwo, cups.get(1));
            modifyCup(mBtnTodayThree, mTxtTodayThree, cups.get(2));
            modifyCup(mBtnTodayFour, mTxtTodayFour, cups.get(3));
        }
    }
}













