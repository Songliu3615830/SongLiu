package com.example.sonliu.drinkingalert.datebase.Operator;

import com.example.sonliu.drinkingalert.datebase.data.Alarm;
import com.example.sonliu.drinkingalert.datebase.data.Cup;
import com.example.sonliu.drinkingalert.datebase.data.DrinkRecord;
import com.example.sonliu.drinkingalert.datebase.data.UserPlan;

import java.util.List;


/**
 * 数据库接口 四张表
 * 分别为user_plan, drink_record, alarm, cup
 * ps: 其中有些功能暂时用不到
 */
public interface DBOperator {
    /**
     * 添加一个闹钟
     * @param alarm
     */
    void insertAlarm(Alarm alarm);

    /**
     * 根据id修改闹钟
     * @param alarm
     */
    void updateAlarmById(Alarm alarm);

    /**
     * 根据id删除闹钟
     * @param id
     */
    void deleteAlarmById(Integer id);

    /**
     * 删除该用户下所有闹钟
     * @param uid
     */
    void deleteAlarmByUid(Integer uid);

    /**
     * 查找该用户下所有闹钟
     * @param uid
     * @return
     */
    List<Alarm> selectAlarmByUid(Integer uid);

    /**
     * 根据id查找闹钟
     * @param id
     * @return
     */
    Alarm selectAlarmById(Integer id);

    /**
     * 查找所有闹钟
     * @return
     */
    List<Alarm> selectAllAlarm();

    /**
     * 添加一个杯子
     * @param cup
     */
    void insertCup(Cup cup);

    /**
     * 更新杯子的属性(图片，文字)
     * @param cup
     */
    void updateCupById(Cup cup);

    /**
     * 删除某个杯子
     * @param id 数据库字段
     */
    void deleteCupById(Integer id);

    /**
     * 删除该用户下所有杯子
     * @param uid
     */
    void deleteCupByUid(Integer uid);

    /**
     * 查找该用户的所有杯子
     * @param uid 用户ID
     * @return
     */
    List<Cup> selectAllCupByUid(Integer uid);

    /**
     * 增加一条喝水记录
     * @param drinkRecord
     */
    void insertDrinkRecord(DrinkRecord drinkRecord);

    /**
     * 根据id删除喝水记录
     * @param id
     */
    void deleteDrinkRecordById(Integer id);

    /**
     * 删除用户所有喝水记录
     * @param uid 用户id
     */
    void deleteDrinkRecordByUid(Integer uid);

    /**
     * 找到该用户下所有喝水记录
     * @param uid 用户id
     * @return 喝水记录集合
     */
    List<DrinkRecord> selectAllRecordByUid(Integer uid);

    /**
     * 找到该用户当日喝水所有记录
     * @param uid 用户id
     * @param todayMilliseconds 当日00:00的毫秒数
     * @return
     */
    List<DrinkRecord> selectRecordTodayByUid(Integer uid, Long todayMilliseconds);

    /**
     * 增加一个用户
     * @param userPlan
     */
    void insertUserPlan(UserPlan userPlan);

    /**
     * 根据id修改用户信息
     * @param userPlan
     */
    void updateUserPlanById(UserPlan userPlan);

    /**
     * 根据id删除用户
     * @param id
     */
    void deleteUserPlanById(Integer id);

    /**
     * 根据id查找用户
     * @param id
     * @return UserPlan
     */
    UserPlan selectUserPlanById(Integer id);

    /**
     * 根据用户类型查找用户
     * @param type 0 成人 1 孩子
     * @return
     */
    List<UserPlan> selectUserPlanByType(Integer type);

    /**
     * 查找所有的用户
     * @return
     */
    List<UserPlan> selectAllUserPlan();
}
