package com.digitalhouse.whatchapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.digitalhouse.whatchapp.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    ImageView whatchapp, oculos, camera, chewbacca;
    Animation frombottom, fromright, fromtop, fromleft;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        whatchapp = findViewById(R.id.whatchapp);
        oculos = findViewById(R.id.oculos);
        camera = findViewById(R.id.camera);
        chewbacca = findViewById(R.id.chewbacca);

        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromright = AnimationUtils.loadAnimation(this, R.anim.fromright);
        fromleft = AnimationUtils.loadAnimation(this, R.anim.fromleft);
        fromtop = AnimationUtils.loadAnimation(this, R.anim.fromtop);

        whatchapp.setAnimation(frombottom);
        oculos.setAnimation(fromright);
        camera.setAnimation(fromleft);
        chewbacca.setAnimation(fromtop);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                jump(null);
            }
        },5000);


    }


    public void  jump (View view){
        startActivity(new Intent(SplashActivity.this,MainActivity.class));
        finish();
    }
}
