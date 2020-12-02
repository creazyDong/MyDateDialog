package com.rgsc.rgscaibot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.rgsc.rgscaibot.ui.MyScrollFrameLayout;

public class LoginActivity extends AppCompatActivity {
    private MyScrollFrameLayout mMyScrollFrameLayout;
    private Button btn_start;
    private Button btn_stop;
    private Button btn_change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_login);
        mMyScrollFrameLayout = findViewById(R.id.fl_main);
        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);
        btn_change = findViewById(R.id.btn_change);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMyScrollFrameLayout.startScroll();
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMyScrollFrameLayout.stopScroll();
            }
        });
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mMyScrollFrameLayout.changeScrollOrientation();
                Intent intent = new Intent(LoginActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
    }
}
