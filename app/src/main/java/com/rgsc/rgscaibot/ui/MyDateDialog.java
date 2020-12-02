package com.rgsc.rgscaibot.ui;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;

import com.rgsc.rgscaibot.R;

public class MyDateDialog extends Dialog {
    private Context context;
    private MyCalendar mMyCalendar;
    private Button mButton;
    private DialogDateValueLister mDialogDateValueLister;
    private int model;

    public MyDateDialog(Context context, int model) {
        super(context, R.style.common_dialog);
        this.context = context;
        this.model = model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LinearLayout layoutView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.date_dialog, null);
        this.getWindow().setContentView(layoutView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        changeDialogStyle();
        showWithMoveAnim(layoutView);
        mMyCalendar = layoutView.findViewById(R.id.calendar);
        mButton = layoutView.findViewById(R.id.btn_submit);
        mButton.setOnClickListener(mDialogDateValueLister);
        mMyCalendar.setIMyCalendarLister(mDialogDateValueLister);
        mMyCalendar.setSelectMode(model);
    }

    public abstract static class DialogDateValueLister implements IMyCalendarLister, View.OnClickListener {
        private String startDate;
        private String endDate;

        @Override
        public void onDateValueChanged(String value) {
            startDate = value;
        }

        @Override
        public void onTitleClick(int year, int month) {

        }

        @Override
        public void onStartToEndDataValueChange(String startDate, String endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
            Log.e("选择的时间", startDate + " 至 " + endDate);
        }

        public abstract void onSubmit(String startDate, String endDate);

        @Override
        public void onClick(View view) {
            onSubmit(startDate, endDate);
        }
    }

    public void setDialogDateValueLister(DialogDateValueLister dialogDateValueLister) {
        mDialogDateValueLister = dialogDateValueLister;
    }

    /**
     * 动画方式显示，从底部向上显示出来。
     * 如果其他想要其他方式，则修改里面的animation实现即可。
     */
    private void showWithMoveAnim(View srcView) {
        Animation animation = new TranslateAnimation(0f, 0f, getWindowHeight(context) - srcView.getHeight(), 0f);
        animation.setFillAfter(true);
        animation.setDuration(300L);
        srcView.startAnimation(animation);
    }

    private int getWindowHeight(Context context) {
        Point point = new Point();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        if (Build.VERSION.SDK_INT > 16) {
            display.getRealSize(point);
        } else {
            display.getSize(point);
        }
        return point.y;
    }


    /**
     * 设置dialog居下占满屏幕
     */
    public void changeDialogStyle() {
        Window window = getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams params = window.getAttributes();
        if (params != null) {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        }
    }
}
