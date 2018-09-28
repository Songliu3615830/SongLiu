package com.example.sonliu.drinkingalert.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;


import com.example.sonliu.drinkingalert.R;

public class NameSettingDialog extends Dialog{
    private Context mContext;
    private String name;
    private Toolbar mToobar;
    private TextView mTxtNum;
    private EditText mETxtInput;
    private OnNameisChangedListener nameisChangedListener;


    public NameSettingDialog(@NonNull Context context) {
        super(context, R.style.TodayBottomDialog);
        mContext = context;
        this.name = name;
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
//        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_setting_dialog);
        mToobar = findViewById(R.id.toolbar_name_setting);
        mTxtNum = findViewById(R.id.txt_edit_num);
        mETxtInput = findViewById(R.id.etxt_edit);
        if (name == null) {
            name = "";
        }
        mETxtInput.setText(name);
        mToobar.inflateMenu(R.menu.name_setting_menu);

        mETxtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = mETxtInput.getText().toString().trim();
                int num = str.toCharArray().length;
                mTxtNum.setText("("+num+"/15)");
            }
        });

        mToobar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.name_setting_finish) {
                    //点击完成
                    nameisChangedListener.onNameisChanged(mETxtInput.getText().toString());
                    dismiss();
                }
                return true;
            }
        });

        mToobar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击左侧退出按钮
                if (nameisChangedListener != null) {
                    nameisChangedListener.onNameisChanged(mETxtInput.getText().toString());
                }
                dismiss();
            }
        });
    }

    interface OnNameisChangedListener {
        void onNameisChanged(String name);
    }

    public void setNameisChangedListener(OnNameisChangedListener nameisChangedListener) {
        this.nameisChangedListener = nameisChangedListener;
    }

    public void setName(String name) {
        this.name = name;
    }
}
