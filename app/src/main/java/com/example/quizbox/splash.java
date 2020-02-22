package com.example.quizbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class splash extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                Intent i;
                if(FirebaseAuth.getInstance().getCurrentUser()==null)
                {
                    i = new Intent(splash.this, MainActivity.class);
                }
                else
                {
                    i = new Intent(splash.this, Home.class);
                }
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
