package com.digitalhouse.whatchapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.digitalhouse.whatchapp.R;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class MainActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()
                .background(R.color.secondary_text)
                .backgroundDark(R.color.accent)
                .fragment(R.layout.intro_1)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.secondary_text)
                .backgroundDark(R.color.accent)
                .fragment(R.layout.intro_2)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.secondary_text)
                .backgroundDark(R.color.accent)
                .fragment(R.layout.intro_3)
                .build());

    }

    public void showCinema(View view) { startActivity(new Intent(MainActivity.this, HomeActivity.class));}

    public void showTv(View view) { startActivity(new Intent(MainActivity.this, HomeActivity.class));}

}
