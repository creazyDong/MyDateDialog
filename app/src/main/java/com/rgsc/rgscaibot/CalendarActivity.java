package com.rgsc.rgscaibot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rgsc.rgscaibot.ui.MyCalendar;
import com.rgsc.rgscaibot.ui.MyDateDialog;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Button button = findViewById(R.id.btn_show);
        Button btn_simple = findViewById(R.id.btn_simple);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDateDialog myDateDialog = new MyDateDialog(CalendarActivity.this, MyCalendar.RANGE_DATE);
                myDateDialog.setDialogDateValueLister(new MyDateDialog.DialogDateValueLister() {
                    @Override
                    public void onSubmit(String startDate, String endDate) {
                        myDateDialog.dismiss();
                    }
                });
                myDateDialog.init();
                myDateDialog.show();
            }
        });
        btn_simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDateDialog myDateDialog = new MyDateDialog(CalendarActivity.this, MyCalendar.SIMPLE_DATE);
                myDateDialog.setDialogDateValueLister(new MyDateDialog.DialogDateValueLister() {
                    @Override
                    public void onSubmit(String startDate, String endDate) {
                        myDateDialog.dismiss();
                    }
                });
                myDateDialog.init();
                myDateDialog.show();
            }
        });
    }
}
