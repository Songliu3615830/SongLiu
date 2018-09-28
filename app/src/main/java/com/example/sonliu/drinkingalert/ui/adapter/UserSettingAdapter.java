package com.example.sonliu.drinkingalert.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sonliu.drinkingalert.DrinkAlertDefaultConstant;
import com.example.sonliu.drinkingalert.R;
import com.example.sonliu.drinkingalert.User.UserManager;

import static com.example.sonliu.drinkingalert.DrinkAlertDefaultConstant.IMGS_DEFALUT_MAN;

/**
 * 用户设置弹出框 适配器
 */
public class UserSettingAdapter extends RecyclerView.Adapter<UserSettingAdapter.ViewHolder> {
    private Context context;
    private String mainName, childOneName, childTwoName;
    private int iconOne, iconTwo;
    private OnItemClickedListener clickedListener;

    public UserSettingAdapter(Context context, String mainName, String childOneName,
                              String childTwoName, int iconOne, int iconTwo,
                              OnItemClickedListener clickedListener) {
        this.context = context;
        this.mainName = mainName;
        this.childOneName = childOneName;
        this.childTwoName = childTwoName;
        this.iconOne = iconOne;
        this.iconTwo = iconTwo;
        this.clickedListener = clickedListener;
    }

    public UserSettingAdapter(Context context, String mainName, OnItemClickedListener clickedListener) {
        this.context = context;
        this.mainName = mainName;
        this.iconOne = 0;
        this.iconTwo = 0;
        this.clickedListener = clickedListener;
    }

    public UserSettingAdapter(Context context, String mainName, String childOneName, int iconOne, OnItemClickedListener clickedListener) {
        this.context = context;
        this.mainName = mainName;
        this.childOneName = childOneName;
        this.iconOne = iconOne;
        this.clickedListener = clickedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_setting_main, viewGroup, false));
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        //初始化数据
        switch (i) {
            case 0:
                //第一个选项
                viewHolder.Bind(IMGS_DEFALUT_MAN,
                        context.getDrawable(R.drawable.login_icon_enter),
                        "".equals(mainName) ? "还没填名字" : mainName
                );
                break;
            case 1:
                //第二个选项
                if (UserManager.getInstance().getChildUser().size() == 0) {
                    viewHolder.showAdd();
                } else {
                    viewHolder.showChildren();
                    viewHolder.Bind(iconOne, context.getDrawable(R.drawable.img_edit_g),
                            "".equals(childOneName) ? "编辑" : childOneName);
                }
                break;
            case 2:
                //第三个选项
                if (UserManager.getInstance().getChildUser().size() == 0) {
                    viewHolder.hideAll();
                } else if (UserManager.getInstance().getChildUser().size() == 1) {
                    viewHolder.showAdd();
                } else {
                    viewHolder.showChildren();
                    viewHolder.Bind(iconTwo, context.getDrawable(R.drawable.img_edit_g),
                            "".equals(childTwoName) ? "编辑" : childTwoName);
                }
                break;
        }
        //设置监听
        viewHolder.mClyItemSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedListener.onItemClicked(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout mClyItemSetting;
        private ImageView mImgIcon, mImgRight;
        private TextView mTxtContent;
        //隐藏的添加项
        private ImageView mImgAdd;
        private TextView mTxtAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mClyItemSetting = itemView.findViewById(R.id.sly_item_setting);
            mImgIcon = itemView.findViewById(R.id.img_item_icon);
            mImgRight = itemView.findViewById(R.id.img_item_right);
            mTxtContent = itemView.findViewById(R.id.txt_item_content);
            mImgAdd = itemView.findViewById(R.id.img_item_add_child);
            mTxtAdd = itemView.findViewById(R.id.txt_item_add_child);
            mTxtAdd.setVisibility(View.GONE);
            mImgAdd.setVisibility(View.GONE);
        }

        void Bind(int Icon, Drawable right, String content) {
            mImgIcon.setImageResource(DrinkAlertDefaultConstant.IMGS_PERSON[Icon]);
            mImgRight.setImageDrawable(right);
            mTxtContent.setText(content);
        }

        void showAdd() {
            mImgIcon.setVisibility(View.GONE);
            mImgRight.setVisibility(View.GONE);
            mTxtContent.setVisibility(View.GONE);
            mImgAdd.setVisibility(View.VISIBLE);
            mTxtAdd.setVisibility(View.VISIBLE);
        }

        void showChildren() {
            mImgIcon.setVisibility(View.VISIBLE);
            mImgRight.setVisibility(View.VISIBLE);
            mTxtContent.setVisibility(View.VISIBLE);
            mImgAdd.setVisibility(View.GONE);
            mTxtAdd.setVisibility(View.GONE);
        }
        void hideAll() {
            mClyItemSetting.setVisibility(View.GONE);
        }
    }

    public interface OnItemClickedListener {
        void onItemClicked(int item);
    }

}
