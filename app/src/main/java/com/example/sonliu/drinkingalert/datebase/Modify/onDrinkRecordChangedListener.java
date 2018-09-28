package com.example.sonliu.drinkingalert.datebase.Modify;

import com.example.sonliu.drinkingalert.datebase.data.DrinkRecord;

public interface onDrinkRecordChangedListener {

    void onAddDrinkRecord(DrinkRecord drinkRecord);

    /**
     * 删除喝水记录，现功能不允许，以后也许需要
     * @param uid
     */
    void onDeleteDrinkRecord(Integer uid);

}
