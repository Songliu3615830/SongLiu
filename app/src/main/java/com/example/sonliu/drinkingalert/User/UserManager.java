package com.example.sonliu.drinkingalert.User;

import android.content.Intent;
import android.content.IntentFilter;

import com.example.sonliu.drinkingalert.Alarm.AlarmReceiver;
import com.example.sonliu.drinkingalert.DrinkAlertApp;
import com.example.sonliu.drinkingalert.R;
import com.example.sonliu.drinkingalert.datebase.data.Alarm;
import com.example.sonliu.drinkingalert.datebase.data.Cup;
import com.example.sonliu.drinkingalert.datebase.data.UserPlan;
import com.example.sonliu.drinkingalert.datebase.DataBaseExecutor;
import com.example.sonliu.drinkingalert.datebase.Modify.AlarmManagerListener;
import com.example.sonliu.drinkingalert.util.AlarmUtil;
import com.example.sonliu.drinkingalert.util.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.sonliu.drinkingalert.DrinkAlertDefaultConstant.*;

/**
 * 用户管理类 单例
 */
public class UserManager {
    private static UserManager instance;
    private static User currentUser;//当前用户
    private static ArrayList<User> users;//所有用户
    private static DataBaseExecutor executor;//数据库
    private static AlarmManagerListener listener;//闹钟管理
    private AlarmReceiver receiver;
    private UserManager() { }

    public static UserManager getInstance() {
        instance = new UserManager();
        if (users == null) {
            users = new ArrayList<>();
        }
        if (executor == null) {
            executor = DrinkAlertApp.mDBExecutor;
        }
        return instance;
    }

    /**
     * 初始化用户
     * @return 流式设计
     */
    public UserManager initUsers() {
        List<UserPlan> userPlans = executor.selectAllUserPlan();
        if (userPlans.size() > MAX_USER) {
            ToastUtils.shortToast(DrinkAlertApp.getContext(), "用户不能超过" + MAX_USER + "个");
            return instance;
        }
        if (userPlans.size() == 0) {
            return instance;
        }
        if (users != null) {
            users.clear();
        }
        for (UserPlan userPlan : userPlans) {
            User user = new User(userPlan.getId(), userPlan.getType(), userPlan.getIcon(), userPlan.getName(),
                    userPlan.getIsAlert(),userPlan.getTypeAlert() , userPlan.getDayDrink(),
                    executor.selectAllRecordByUid(userPlan.getId()),
                    executor.selectAllCupByUid(userPlan.getId()),
                    executor.selectAlarmByUid(userPlan.getId()));
            //初始化用户水杯和闹钟
            if (user.getCups() == null || user.getCups().size() == 0) {
                for (int i = 0; i < DEFALUT_CUP_IMG.length; i++) {
                    executor.insertCup(new Cup(userPlan.getId(), DEFALUT_CUP_IMG[i], DEFALUT_CUP_NUM[i]));
                }
                user.setCups(executor.selectAllCupByUid(user.getId()));
            }
            if (user.getAlarms() == null || user.getAlarms().size() == 0) {
                for (int i = 0; i < DEFALUT_ALARM.length; i++) {
                    executor.insertAlarm(new Alarm(userPlan.getId(), DEFALUT_ALARM[i]));
                }
                user.setAlarms(executor.selectAlarmByUid(user.getId()));
            }
            users.add(user);
        }

        //监听闹钟开启和关闭
        listener = new AlarmManagerListener() {
            @Override
            public void onStartAlarm() {
                setAlarm();
            }

            @Override
            public void onCloseAlarm() {
                closeAlarm();
            }

            @Override
            public void onTypeChanged(Integer alertType) {
                //重新开启闹钟会刷新之前设置的闹钟
                setAlarm();
            }
        };
        getManUser().setAlarmManagerListener(listener);
        return instance;
    }

    /**
     * 开启闹钟
     */
    private void setAlarm() {
        //动态注册广播
        register();
        Calendar now = Calendar.getInstance();
        int i = 1;
        for (User user : users) {
            Intent intent = new Intent();
            intent.setAction(DrinkAlertApp.getContext().getString(R.string.receiver_action));
            if (user.getType() == 1) {
                //孩子用户名字为空时  添加孩子1 和 孩子2
                intent.putExtra("user_name", (user.getName() == null ||
                        user.getName().equals("")) ? "孩子" + ((++i)%2 + 1) : user.getName());
            }
            intent.putExtra("user_type", user.getType().toString());
            intent.putExtra("alert_type", getManUser().getAlertType());
            List<Alarm> alarms = user.getAlarms();
            for (Alarm alarm : alarms) {
                intent.putExtra("alarm_time", alarm.getTime());
                intent.putExtra("alarm_id", alarm.getId().toString());
                if (alarm.getTime() >=
                        (now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE))) {
                    //开启今天的闹钟
                    AlarmUtil.startAlarm(DrinkAlertApp.getContext(), alarm.getTime() / 60,
                            alarm.getTime() % 60, alarm.getId(), intent);
                    //开启明天的闹钟
                    AlarmUtil.startPreAlarm(DrinkAlertApp.getContext(), alarm.getTime() / 60,
                            alarm.getTime() % 60, alarm.getId(), intent);
                }
            }
        }
    }

    /**
     * 关闭闹钟
     */
    private void closeAlarm() {
        for (User user : users) {
            Intent intent = new Intent();
            intent.setAction(DrinkAlertApp.getContext().getString(R.string.receiver_action));
            List<Alarm> alarms = user.getAlarms();
            for (Alarm alarm : alarms) {
                AlarmUtil.closeAlarm(DrinkAlertApp.getContext(), intent, alarm.getId());
                AlarmUtil.closePreAlarm(DrinkAlertApp.getContext(), intent, alarm.getId());
            }
        }
    }

    /**
     * 添加用户
     * @param type 0 成人 1 孩子
     * @return 流式设计
     */
    public UserManager addUser(Integer type) {
        if (users.size() >= MAX_USER) {
            ToastUtils.shortToast(DrinkAlertApp.getContext(), "人数超出上线");
            return instance;
        }
        for (User user : users) {
            if (user.getType() == 0 && type == 0) {
                ToastUtils.shortToast(DrinkAlertApp.getContext(), "成人用户已存在");
                return instance;
            }
        }
        UserPlan userPlan = new UserPlan(type, 1, "",
                type == 0 ? DEFAULT_MAN_DRINK : DEFAULT_BOY_DRINK, 1, 3);
        executor.insertUserPlan(userPlan);
        return instance;
    }

    /**
     * 查找成人用户
     * @return 成人用户
     */
    public User getManUser() {
        for (User user : users) {
            if (user.getType().equals(0)) {
                return user;
            }
        }
        return null;
    }

    /**
     * 查找孩子用户
     * @return list.size()==0 则 没有孩子，否则 即有
     */
    public List<User> getChildUser() {
        List<User> children = new ArrayList<>();
        for (User user : users) {
            if (user.getType().equals(1)) {
                children.add(user);
            }
        }
        return children;
    }

    /**
     * 删除用户
     * @param id uid
     * @return 流式设计
     */
    public UserManager delete(Integer id) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (id.equals(user.getId())) {
                closeAlarm();
                user.clearAlarm();
                //更新闹钟
                if (user.getIsAlert() == 1) {
                    getManUser().setIsAlert(1);
                }
                user.clearCups();
                user.deleteRecords();
                executor.deleteUserPlanById(id);
                users.remove(i);
            }
        }
        return instance;
    }

    public UserManager setCurrentUser(User user) {
        currentUser = user;
        return instance;
    }

    private void register() {
        //动态注册
        receiver = new AlarmReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DrinkAlertApp.getContext().getString(R.string.receiver_action));
        DrinkAlertApp.getContext().registerReceiver(receiver, filter);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    protected static DataBaseExecutor getmOperator() {
        return executor;
    }
}
