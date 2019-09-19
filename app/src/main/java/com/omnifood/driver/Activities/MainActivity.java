package com.omnifood.driver.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.omnifood.driver.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private final int SPLASH_DISPLAY_LENGTH = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                MainActivity.this.startActivity(loginIntent);
                MainActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
