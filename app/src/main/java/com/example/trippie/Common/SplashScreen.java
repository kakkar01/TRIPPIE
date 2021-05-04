package com.example.trippie.Common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.trippie.R;
import com.example.trippie.User.UserDashboard;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
     private  static  int SPLASH_TIMER=4000;
    ImageView backgroundImage;
    Animation sideAnim, bottomAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
//hooks
        backgroundImage = findViewById(R.id.background_image);
        //animation
        sideAnim= AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        // set anim on element

        backgroundImage.setAnimation(sideAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,UserDashboard.class);
                startActivity(intent);
                finish();


            }
        }, SPLASH_TIMER);
    }
}
