package com.example.sonliu.drinkingalert.User;

import com.example.sonliu.drinkingalert.datebase.data.Alarm;
import com.example.sonliu.drinkingalert.datebase.data.Cup;
import com.example.sonliu.drinkingalert.datebase.data.DrinkRecord;
import com.example.sonliu.drinkingalert.datebase.data.UserPlan;
import com.example.sonliu.drinkingalert.datebase.Modify.AlarmManagerListener;
import com.example.sonliu.drinkingalert.datebase.Modify.onCupChangedListener;
import com.example.sonliu.drinkingalert.datebase.Modify.onDrinkRecordChangedListener;
import com.example.sonliu.drinkingalert.datebase.Modify.onUserPlanChangedListener;
import com.example.sonliu.drinkingalert.util.SPHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *  数据封装类
 */
public class User implements Serializable {
    private Integer id;
    private Integer type;// 0 成人， 1 孩子
    private Integer icon;//头像id
    private String name;
    private Integer isAlert;// 0 否 1 是
    private Integer alertType;//提醒方式
    private Integer dayPlan;//每日计划量

    private int todayNum;//当日喝水量
    private int tolNum; //总喝水量
    private int tolDay; //记录总天数
    private int successDay;//达到目标天数
    private int avgDayNum;//平均每日喝水
    private int maxDayNum;//记录最多喝水量

    private List<DrinkRecord> records = new ArrayList<>();//喝水记录
    private List<Cup> cups = new ArrayList<>();//杯子
    private List<Alarm> alarms = new ArrayList<>();//闹钟

    private onUserPlanChangedListener onUserPlanChangedListener;//监听User中 name,isAlert,alertType,dayPlan变化
    private onDrinkRecordChangedListener onDrinkRecordChangedListener;//监听records变化
    private onCupChangedListener onCupChangedListener;//监听cups变化
    private AlarmManagerListener alarmManagerListener;//监听开启和关闭闹钟

    protected User() { }

    protected User(Integer id, Integer type, Integer icon, String name, Integer isAlert, Integer alertType, Integer dayPlan, List<DrinkRecord> records, List<Cup> cups, List<Alarm> alarms) {
        this.id = id;
        this.type = type;
        this.icon = icon;
        this.name = name;
        this.isAlert = isAlert;
        this.alertType = alertType;
        this.dayPlan = dayPlan;
        this.records = records;
        this.cups = cups;
        this.alarms = alarms;
    }

    public void setOnUserPlanChangedListener(onUserPlanChangedListener onUserPlanChangedListener) {
        this.onUserPlanChangedListener = onUserPlanChangedListener;
    }

    public void setOnDrinkRecordChangedListener(onDrinkRecordChangedListener onDrinkRecordChangedListener) {
        this.onDrinkRecordChangedListener = onDrinkRecordChangedListener;
    }

    public void setOnCupChangedListener(onCupChangedListener onCupChangedListener) {
        this.onCupChangedListener = onCupChangedListener;
    }


    public void setAlarmManagerListener(AlarmManagerListener alarmManagerListener) {
        this.alarmManagerListener = alarmManagerListener;
    }

    public Integer getId() {
        return id;
    }

    public Integer getType() {
        return type;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
        UserManager.getmOperator().updateUserPlanById(new UserPlan(id, type, icon, name,
                dayPlan, isAlert, alertType));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if (this.name != null) {
            if (onUserPlanChangedListener != null) {
                onUserPlanChangedListener.onNameChanged(new UserPlan(id, type, icon, name,
                        dayPlan, isAlert, alertType));
            }
            UserManager.getmOperator().updateUserPlanById(new UserPlan(id, type, icon, name,
                    dayPlan, isAlert, alertType));
        }
    }

    public Integer getIsAlert() {
        return isAlert;
    }

    public void setIsAlert(Integer isAlert) {
        this.isAlert = isAlert;
        if (isAlert == 1) {
            alarmManagerListener.onStartAlarm();
        } else {
            alarmManagerListener.onCloseAlarm();
        }
        UserManager.getmOperator().updateUserPlanById(new UserPlan(id, type, icon, name,
                dayPlan, isAlert, alertType));
    }

    public Integer getAlertType() {
        return alertType;
    }

    public void setAlertType(Integer alertType) {
        this.alertType = alertType;
        alarmManagerListener.onTypeChanged(alertType);
        UserManager.getmOperator().updateUserPlanById(new UserPlan(id, type, icon, name,
                dayPlan, isAlert, alertType));
    }

    public Integer getDayPlan() {
        return dayPlan;
    }

    public void setDayPlan(Integer dayPlan) {
        this.dayPlan = dayPlan;
        onUserPlanChangedListener.onDayDrinkChanged(new UserPlan(id, type, icon, name,
                dayPlan, isAlert, alertType));
        UserManager.getmOperator().updateUserPlanById(new UserPlan(id, type, icon, name,
                dayPlan, isAlert, alertType));
    }

    public List<DrinkRecord> getRecords() {
        return records;
    }

    public void addRecord(DrinkRecord record) {
        if (record != null) {
            this.records.add(record);
            onDrinkRecordChangedListener.onAddDrinkRecord(record);
            UserManager.getmOperator().insertDrinkRecord(record);
        }
    }

    public void deleteRecords() {
        if (records != null) {
            this.records.clear();
//            onDrinkRecordChangedListener.onDeleteDrinkRecord(id);
            UserManager.getmOperator().deleteDrinkRecordByUid(id);
        }
    }

    public List<Cup> getCups() {
        return cups;
    }

    /**
     * 修改杯子
     */
    public void modifyCup(Cup cup) {
        if (cups != null) {
            for (int i = 0; i < cups.size(); i++) {
                if (cups.get(i).getId().equals(cup.getId())) {
                    cups.set(i, cup);
                }
            }
            onCupChangedListener.onCupChanged(cup);
            UserManager.getmOperator().updateCupById(cup);
        }
    }


    public void clearCups() {
        if (cups != null) {
            this.cups.clear();
            UserManager.getmOperator().deleteCupByUid(id);
        }
    }

    public List<Alarm> getAlarms() {
        alarms = UserManager.getmOperator().selectAlarmByUid(id);
        return alarms;
    }

    public void addAlarm(Integer hour, Integer minute) {
        Integer timeInput = hour * 60 + minute;
        Alarm newAlarm = null;
        boolean isRepeated = false;
        if (alarms != null) {
            if (alarms.size() > 0) {
                for (int i = 0; i < alarms.size(); i++) {
                    if (alarms.get(i).getTime().equals(timeInput)) {
                        isRepeated = true;
                    }
                }
            }
            //如果不重复则添加 否则不添加
            if (!isRepeated) {
                newAlarm = new Alarm(id, timeInput);
                alarms.add(newAlarm);
                UserManager.getmOperator().insertAlarm(newAlarm);
            }
        }
    }

    public void modifyAlarm(Integer hour, Integer minute, Integer id) {
        int time = hour * 60 + minute;
        for (Alarm alarm : alarms) {
            if (alarm.getTime() == time) {
                time++;
            }
        }
        Alarm modifyAlarm = new Alarm(id, this.id, time);
        UserManager.getmOperator().updateAlarmById(modifyAlarm);
    }

    public void deleteAlarm(Integer id) {
        UserManager.getmOperator().deleteAlarmById(id);
    }

    public void clearAlarm() {
        if (alarms != null && alarms.size() > 0) {
            alarms.clear();
            UserManager.getmOperator().deleteCupByUid(id);
        }
    }

    /* 以下是操作report */

    public int getTolNum() {
        return tolNum;
    }

    /**
     * 设置总喝水量
     */
    public User setTolNum() {
        this.tolNum = calTolNum(records);
        return this;
    }

    public int getTolDay() {
        return tolDay;
    }

    /**
     * 设置总天数
     */
    public User setTolDay() {
        int count = calTolDay(records);
        if (this.tolDay != count && this.tolDay != 0) {
            SPHelper.setIsReadSuccessDayByWriteSP(this.id, true);
        }
        this.tolDay = count;
        return this;
    }

    public int getSuccessDay() {
        return successDay;
    }

    /**
     * 设置达到目标天数
     */
    public User setSuccessDay() {
        this.successDay = SPHelper.getSuccessDayByReadSP(this.id);
        if (SPHelper.getIsReadSuccessDayByReadSP(this.id) && calTodayNum(records) >= this.dayPlan) {
            this.successDay++;
            SPHelper.setSuccessDayByWriteSP(this.id, this.successDay);
            SPHelper.setIsReadSuccessDayByWriteSP(this.id, false);
        }
        return this;
    }

    public int getAvgDayNum() {
        return avgDayNum;
    }

    /**
     * 设置平均喝水量
     */
    public User setAvgDayNum() {
        if (tolDay == 0) {
            return this;
        }
        this.avgDayNum = this.tolNum / this.tolDay;
        return this;
    }

    public int getMaxDayNum() {
        return maxDayNum;
    }


    public int getTodayNum() {
        return todayNum;
    }

    public User setTodayNum() {
        this.todayNum = calTodayNum(records);
        return this;
    }

    /**
     * 计算最大量
     */
    public void setMaxDayNum() {
        if (this.tolDay == 1) {
            this.maxDayNum = this.tolNum;
        } else {
            Integer max = calTodayNum(records);
            if (max > maxDayNum) {
                this.maxDayNum = max;
            }
        }
    }

    /**
     * 计算总喝水量
     * @return 总喝水量
     */
    private Integer calTolNum(List<DrinkRecord> records) {
        Integer tolNum = 0;
        if (records == null || records.size() <= 0) {
            return tolNum;
        }
        for (DrinkRecord record : records) {
            tolNum += record.getNum();
        }
        return tolNum;
    }

    /**
     * 计算总天数
     * @return 总天数
     */
    private Integer calTolDay(List<DrinkRecord> records) {
        Integer tolDay = 0;
        List<Integer> days = new ArrayList<>();
        if (records == null || records.size() <= 0) {
            return tolDay;
        }
        for (DrinkRecord record : records) {
            Long timeMilliseconds = record.getTime();
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(timeMilliseconds);
            if (!(days.contains(c.get(Calendar.DAY_OF_YEAR)))) {
                days.add(c.get(Calendar.DAY_OF_YEAR));
            }
        }
        return days.size();
    }

    /**
     * 计算今日喝水
     * @return 今日喝水量
     */
    private Integer calTodayNum(List<DrinkRecord> records) {
        Integer todayNum = 0;
        if (records == null || records.size() <= 0) {
            return todayNum;
        }
        for (DrinkRecord record : records) {
            Long timeMilliseconds = record.getTime();
            Calendar c = Calendar.getInstance();
            int today = c.get(Calendar.DAY_OF_YEAR);
            c.setTimeInMillis(timeMilliseconds);
            if (today == c.get(Calendar.DAY_OF_YEAR)) {
                todayNum += record.getNum();
            }
        }
        return todayNum;
    }

    public void setCups(List<Cup> cups) {
        this.cups = cups;
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", type=" + type +
                ", icon=" + icon +
                ", name='" + name + '\'' +
                ", isAlert=" + isAlert +
                ", alertType=" + alertType +
                ", dayPlan=" + dayPlan +
                ", tolNum=" + tolNum +
                ", tolDay=" + tolDay +
                ", successDay=" + successDay +
                ", avgDayNum=" + avgDayNum +
                ", maxDayNum=" + maxDayNum +
                ", records=" + records +
                ", cups=" + cups +
                ", alarms=" + alarms +
                '}';
    }
}
