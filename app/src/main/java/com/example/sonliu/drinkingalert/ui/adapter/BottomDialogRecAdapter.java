package com.example.sonliu.drinkingalert.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.sonliu.drinkingalert.R;
import com.example.sonliu.drinkingalert.DrinkAlertDefaultConstant;
import com.example.sonliu.drinkingalert.datebase.data.Cup;

/**
 * 底部弹出栏 水杯设置表 适配器
 */
public class BottomDialogRecAdapter extends RecyclerView.Adapter<BottomDialogRecAdapter.ViewHolder> {
    private Context mContext;
    private int[] imgsId = DrinkAlertDefaultConstant.IMGS_CUP;
    private Cup clickCup;
    private OnItemSelectedListener listener;

    public BottomDialogRecAdapter(Context mContext, Cup clickCup, OnItemSelectedListener listener) {
        this.clickCup = clickCup;
        this.mContext = mContext;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_bottom_dialog,viewGroup,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.Bind(i);
        viewHolder.mImgIsSelected.setVisibility(View.GONE);
        viewHolder.mBtnItemCup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemSelected(i);
            }
        });
        if (i == clickCup.getImg()) {
            viewHolder.mImgIsSelected.setVisibility(View.VISIBLE);
        }

        if (i == 3) {
            viewHolder.mImgIsSelected.setVisibility(View.GONE);
            viewHolder.mBtnItemCup.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return imgsId.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgIsSelected;//默认选中框
        private ImageButton mBtnItemCup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgIsSelected = itemView.findViewById(R.id.img_is_selected);
            mBtnItemCup = itemView.findViewById(R.id.btn_item_cup);
        }

        void Bind(int id) {
            mBtnItemCup.setImageResource(imgsId[id]);
        }
    }

    public interface OnItemSelectedListener{
        void onItemSelected(int i);
    }
}
