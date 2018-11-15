package com.digitalhouse.whatchapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.digitalhouse.whatchapp.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                jump(null);
            }
        }, 4000);
    }

    public void jump(View view) {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }


}