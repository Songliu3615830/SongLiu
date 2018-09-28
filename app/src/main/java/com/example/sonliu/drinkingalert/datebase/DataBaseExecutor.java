package com.example.sonliu.drinkingalert.datebase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.sonliu.drinkingalert.datebase.data.Alarm;
import com.example.sonliu.drinkingalert.datebase.data.Cup;
import com.example.sonliu.drinkingalert.datebase.data.DrinkRecord;
import com.example.sonliu.drinkingalert.datebase.data.UserPlan;
import com.example.sonliu.drinkingalert.datebase.Operator.DBOperator;
import java.util.ArrayList;
import java.util.List;

public class DataBaseExecutor implements DBOperator {
    private MyDBOpenHelper helper;
    private SQLiteDatabase db;

    public DataBaseExecutor(Context context) {
        helper = new MyDBOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    /* 闹钟数据操作 */
    @Override
    public void insertAlarm(Alarm alarm) {
        db.execSQL("insert into alarm values(null, ?, ?)",
                new Object[]{alarm.getUid(), alarm.getTime()});
    }

    @Override
    public void updateAlarmById(Alarm alarm) {
        db.execSQL("update alarm set time = ? where id = ?", new Object[]{alarm.getTime(),
                alarm.getId()});
    }

    @Override
    public void deleteAlarmById(Integer id) {
        db.execSQL("delete from alarm where id = ?", new Object[]{id});
    }

    @Override
    public void deleteAlarmByUid(Integer uid) {
        db.execSQL("delete from alarm where uid = ?", new Object[]{uid});
    }

    @Override
    public List<Alarm> selectAlarmByUid(Integer uid) {
        List<Alarm> alarms = new ArrayList<>();
        Cursor cursor = db.rawQuery(
                "select id, uid, time from alarm where uid = ? order by time asc",
                new String[]{uid.toString()});
        while (cursor.moveToNext()) {
            alarms.add(new Alarm(cursor.getInt(0), cursor.getInt(1),
                    cursor.getInt(2)));
        }
        return alarms;
    }

    @Override
    public Alarm selectAlarmById(Integer id) {
        Alarm alarm = null;
        Cursor cursor = db.rawQuery("select uid, time from alarm where id = ?",
                new String[]{id.toString()});
        if (cursor.moveToNext()) {
            alarm = new Alarm(id, cursor.getInt(0), cursor.getInt(1));
        }
        return alarm;
    }

    public List<Alarm> selectAllAlarm() {
        List<Alarm> alarms = new ArrayList<>();
        Cursor cursor = db.rawQuery("select id from alarm", null);
        while (cursor.moveToNext()) {
            alarms.add(new Alarm(cursor.getInt(0), null, null));
        }
        return alarms;
    }

    /* 杯子相关数据操作 */
    @Override
    public void insertCup(Cup cup) {
        db.execSQL("insert into cup values(null,?,?,?)",
                new Object[]{cup.getUid(), cup.getImg(), cup.getNum()});
    }

    @Override
    public void updateCupById(Cup cup) {
        db.execSQL("update cup set img = ?, num = ? where id = ?",
                new Object[]{cup.getImg(), cup.getNum(), cup.getId()});
    }

    @Override
    public void deleteCupById(Integer id) {
        db.execSQL("delete from cup where id = ?", new Object[]{id});
    }

    @Override
    public void deleteCupByUid(Integer uid) {
        db.execSQL("delete from cup where uid = ?", new Object[]{uid});
    }

    @Override
    public List<Cup> selectAllCupByUid(Integer uid) {
        List<Cup> cups = new ArrayList<>();
        Cursor cursor = db.rawQuery("select id,uid,img,num from cup where uid = ?",
                new String[]{uid.toString()});
        while (cursor.moveToNext()) {
            cups.add(new Cup(cursor.getInt(0), cursor.getInt(1),
                    cursor.getInt(2), cursor.getInt(3)));
        }
        return cups;
    }

    /* 喝水记录数据操作 */
    @Override
    public void insertDrinkRecord(DrinkRecord drinkRecord) {
        db.execSQL("insert into drink_record values(null, ?, ?, ?)",
                new Object[]{drinkRecord.getUid(), drinkRecord.getNum(), drinkRecord.getTime()});
    }

    @Override
    public void deleteDrinkRecordById(Integer id) {
        db.execSQL("delete from drink_record where id = ?", new Object[]{id});
    }

    @Override
    public void deleteDrinkRecordByUid(Integer uid) {
        db.execSQL("delete from drink_record where uid = ?", new Object[]{uid});
    }

    @Override
    public List<DrinkRecord> selectAllRecordByUid(Integer uid) {
        List<DrinkRecord> drinkRecords = new ArrayList<>();
        Cursor cursor = db.rawQuery("select id,uid,num,time from drink_record where uid = ?",
                new String[]{uid.toString()});
        while (cursor.moveToNext()) {
            drinkRecords.add(new DrinkRecord(cursor.getInt(0), cursor.getInt(1),
                    cursor.getInt(2), cursor.getLong(3)));
        }
        return drinkRecords;
    }

    @Override
    public List<DrinkRecord> selectRecordTodayByUid(Integer uid, Long todayMilliseconds) {
        List<DrinkRecord> drinkRecords = new ArrayList<>();
        Cursor cursor = db.rawQuery("select id,uid,num,time from drink_record where " +
                "uid = ? and time = ?", new String[]{uid.toString(), todayMilliseconds.toString()});
        while (cursor.moveToNext()) {
            drinkRecords.add(new DrinkRecord(cursor.getInt(0), cursor.getInt(1),
                    cursor.getInt(2), cursor.getLong(3)));
        }
        return drinkRecords;
    }

    /* 用户计划数据操作 */
    @Override
    public void insertUserPlan(UserPlan userPlan) {
        db.execSQL("insert into user_plan values(null, ?, ?, ?, ?, ?, ?)",
                new Object[]{userPlan.getType(), userPlan.getIcon(), userPlan.getName(),
                        userPlan.getDayDrink(), userPlan.getIsAlert(), userPlan.getTypeAlert()});
    }

    @Override
    public void updateUserPlanById(UserPlan userPlan) {
        db.execSQL("update user_plan set type = ?, icon = ?, name = ?, day_drink = ?," +
                "is_alert = ?, type_alert = ? where id = ?", new Object[]{
                userPlan.getType(), userPlan.getIcon(), userPlan.getName(), userPlan.getDayDrink(),
                userPlan.getIsAlert(), userPlan.getTypeAlert(), userPlan.getId()
        });
    }

    @Override
    public void deleteUserPlanById(Integer id) {
        db.execSQL("delete from user_plan where id = ?", new Object[]{ id });
    }

    @Override
    public UserPlan selectUserPlanById(Integer id) {
        UserPlan userPlan = null;
        Cursor cursor = db.rawQuery("select id,type,icon,name,day_drink,is_alert,type_alert from " +
                "user_plan where id = ?", new String[]{ id.toString() });
        while (cursor.moveToNext()) {
            userPlan = new UserPlan(cursor.getInt(0), cursor.getInt(1),
                    cursor.getInt(2), cursor.getString(3),
                    cursor.getInt(4), cursor.getInt(5),
                    cursor.getInt(6));
        }
        return userPlan;
    }

    @Override
    public List<UserPlan> selectUserPlanByType(Integer type) {
        List<UserPlan> userPlans = new ArrayList<>();
        Cursor cursor = db.rawQuery("select id,type,icon,name,day_drink,is_alert,type_alert from " +
                "user_plan where type = ?", new String[]{ type.toString() });
        while (cursor.moveToNext()) {
            userPlans.add(new UserPlan(cursor.getInt(0), cursor.getInt(1),
                    cursor.getInt(2), cursor.getString(3),
                    cursor.getInt(4), cursor.getInt(5),
                    cursor.getInt(6)));
        }
        return userPlans;
    }

    @Override
    public List<UserPlan> selectAllUserPlan() {
        List<UserPlan> userPlans = new ArrayList<>();
        Cursor cursor = db.rawQuery("select id,type,icon,name,day_drink,is_alert,type_alert from " +
                "user_plan", null);
        while (cursor.moveToNext()) {
            userPlans.add(new UserPlan(cursor.getInt(0), cursor.getInt(1),
                    cursor.getInt(2), cursor.getString(3),
                    cursor.getInt(4), cursor.getInt(5),
                    cursor.getInt(6)));
        }
        return userPlans;
    }
}

