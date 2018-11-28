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
        //setContentView(R.layout.activity_main);

        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.darker_gray)
                .fragment(R.layout.intro_1)
                .build());

        addSlide(new FragmentSlide.Builder()
								.background(android.R.color.darker_gray)
                .fragment(R.layout.intro_2)
                .build());

        /*addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_3)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_4)
                .build());
*/
        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_cadastro)
                .canGoForward(false)
                .build());
    }

    public void btn_entrar (View view){
        startActivity(new Intent(this, LoginActivity.class));

    }

    public void btn_cadastrar (View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }


    public void goToHome(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }
}
