package com.example.sonliu.drinkingalert.ui.adapter;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.sonliu.drinkingalert.DrinkAlertApp;
import com.example.sonliu.drinkingalert.R;
import com.example.sonliu.drinkingalert.User.User;
import com.example.sonliu.drinkingalert.User.UserManager;
import com.example.sonliu.drinkingalert.datebase.data.Alarm;

import java.util.Calendar;
import java.util.List;


public class PlanFragmentRecAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Alarm> alarms;
    private User currentUser;
    private AlertDialog alertDialog;

    private final static int TYPE_ADD = 1;
    private final static int TYPE_VIEW = 0;

    public PlanFragmentRecAdapter(Context context, List<Alarm> alarms) {
        this.alarms = alarms;
        mContext = context;
        currentUser = UserManager.getInstance().getCurrentUser();
    }

    @Override
    public int getItemViewType(int position) {
        int contentSize = alarms.size();
        if (position == contentSize) {
            return TYPE_ADD;
        } else {
            return TYPE_VIEW;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        if (type == TYPE_ADD) {
            AddHolder addHolder = new AddHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_rec_plan_alarm_add, viewGroup, false));
            return addHolder;
        } else {
            ContentHolder viewHolder = new ContentHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_rec_plan_alarm, viewGroup, false));
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof ContentHolder) {
           final ContentHolder contentHolder = (ContentHolder) viewHolder;
           //设置时间
           contentHolder.mTxtAlarm.setText(minutesToString(alarms.get(i).getTime()));

           //点击闹钟
            contentHolder.mCardItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            contentHolder.mTxtAlarm
                                    .setText(timeFormat(hourOfDay + "",minute + ""));
                            currentUser.modifyAlarm(hourOfDay, minute, alarms.get(i).getId());
                            //重新设置提醒
                            if (UserManager.getInstance().getManUser().getIsAlert() == 1) {
                                UserManager.getInstance().getManUser().setIsAlert(1);
                            }
                        }
                    },(alarms.get(i).getTime()) / 60, (alarms.get(i).getTime()) %60,
                            true).show();
                }
            });
            //点击删除
            contentHolder.mBtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog = new AlertDialog.Builder(mContext)
                            .setMessage(DrinkAlertApp.getContext().getString(R.string.alert_dialog_cancel_alarm))
                            .setNegativeButton(DrinkAlertApp.getContext()
                                            .getString(R.string.alert_dialog_cancel_cancel_button),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) { }
                                    })
                            .setPositiveButton(DrinkAlertApp.getContext()
                                            .getString(R.string.alert_dialog_cancel_confirm_button),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Log.d("Adapter", "onClick: 点击了" + currentUser.getId());
                                            currentUser.deleteAlarm(alarms.get(i).getId());
                                            alarms.remove(i);
                                            //重新设置提醒
                                            if (UserManager.getInstance().getManUser().getIsAlert() == 1) {
                                                UserManager.getInstance().getManUser().setIsAlert(1);
                                            }
                                            notifyItemRemoved(i);
                                            notifyDataSetChanged();
                                        }
                                    })
                            .create();
                    alertDialog.show();
                }
            });
        } else if (viewHolder instanceof AddHolder) {
            //尾部添加
            final AddHolder addHolder = (AddHolder) viewHolder;
            addHolder.mCardAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar now = Calendar.getInstance();
                    new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            currentUser.addAlarm(hourOfDay, minute);
                            notifyItemInserted(calPosition(hourOfDay, minute));
                            notifyDataSetChanged();
                            //重新设置提醒
                            if (UserManager.getInstance().getManUser().getIsAlert() == 1) {
                                UserManager.getInstance().getManUser().setIsAlert(1);
                            }
                        }
                    }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true)
                            .show();
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return alarms.size() + 1;
    }

    class ContentHolder extends RecyclerView.ViewHolder {
        private ImageButton mBtnCancel;
        private TextView mTxtAlarm;
        private CardView mCardItem;

        public ContentHolder(@NonNull View itemView) {
            super(itemView);
            mBtnCancel = itemView.findViewById(R.id.btn_alarm_cancel);
            mTxtAlarm = itemView.findViewById(R.id.txt_plan_alarm);
            mCardItem = itemView.findViewById(R.id.item_plan_rec_card);
        }
    }

    class AddHolder extends RecyclerView.ViewHolder{
        private CardView mCardAdd;

        public AddHolder(@NonNull View itemView) {
            super(itemView);
            mCardAdd = itemView.findViewById(R.id.item_plan_rec_card_add);
        }
    }

    private String minutesToString(Integer minutes) {
        String hour = (minutes / 60) + "";
        String minute = (minutes % 60) + "";
        return timeFormat(hour, minute);
    }

    /**
     * 格式化时间
     * @param hour   小时
     * @param minute 分钟
     * @return 格式为00:00的字符串
     */
    private String timeFormat(String hour, String minute) {
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        if (minute.length() == 1) {
            minute = "0" + minute;
        }
        return hour + ":" + minute;
    }

    /**
     * 计算添加的位置
     * @return
     */
    private int calPosition(int hour, int minute) {
        for (int i = 0;i < alarms.size(); i++) {
            if (alarms.get(i).getTime() < (hour * 60 + minute)) {
                return i-1;
            }
        }
        return alarms.size();
    }

}
