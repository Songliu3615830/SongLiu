package com.example.sonliu.drinkingalert.datebase.Modify;

import com.example.sonliu.drinkingalert.datebase.data.UserPlan;

public interface onUserPlanChangedListener {

    void onNameChanged(UserPlan userPlan);

    void onDayDrinkChanged(UserPlan userPlan);

}
