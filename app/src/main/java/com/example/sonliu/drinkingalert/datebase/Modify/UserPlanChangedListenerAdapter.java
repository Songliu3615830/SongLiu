package com.example.sonliu.drinkingalert.datebase.Modify;

import com.example.sonliu.drinkingalert.datebase.data.UserPlan;

/**
 * 适配onUserPlanChangeListener
 */
public abstract class UserPlanChangedListenerAdapter implements onUserPlanChangedListener {
    @Override
    public void onNameChanged(UserPlan userPlan) { }

    @Override
    public void onDayDrinkChanged(UserPlan userPlan) { }
}
