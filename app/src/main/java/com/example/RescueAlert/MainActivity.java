package com.example.RescueAlert;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 5000;


    //Variables
    Animation topAnim;
    Animation botAnim;
    ImageView image;
    TextView logo;
    TextView slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        botAnim = AnimationUtils.loadAnimation(this, R.anim.bot_animation);

        //Hooks
        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView3);
        slogan = findViewById(R.id.textView4);

        image.setAnimation(topAnim);
        logo.setAnimation(botAnim);
        slogan.setAnimation(botAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

             /*   if() {

                }*/

                Intent i = (new Intent(MainActivity.this, Signup.class));
                startActivity(i);
                finish();

            }

        }, SPLASH_SCREEN);

    }
}