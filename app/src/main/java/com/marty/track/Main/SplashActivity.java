package com.marty.track.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.marty.track.R;
import com.marty.track.School.SchoolActivity;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT=2000;
    ImageView logo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.splash_image);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPrefs.getLoggedStatus(getApplicationContext())){
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, SchoolActivity.class));
                }
            }
        }, SPLASH_SCREEN_TIME_OUT);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        logo.startAnimation(animation);

    }
}
