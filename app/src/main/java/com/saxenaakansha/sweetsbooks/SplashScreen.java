package com.saxenaakansha.sweetsbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {
    private static final int SPLASH_DELAY = 2000; // Delay in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // Add a delay using a Handler

        // Set your splash screen layout
        setContentView(R.layout.activity_splash_screen);

        // Check if the view pager has already been shown
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        boolean isViewPagerShown = sharedPreferences.getBoolean("isViewPagerShown", false);

        if (!isViewPagerShown) {
            // If the view pager has not been shown, start the view pager activity
            startActivity(new Intent(SplashScreen.this, MainActivity.class));

            // Set the flag indicating that the view pager has been shown
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isViewPagerShown", true);
            editor.apply();

            // Finish the splash screen activity
            finish();
        } else {
            // Delay the start of the recipe main activity
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Start the recipe main activity
                    Intent intent = new Intent(SplashScreen.this, MainScreen.class);
                    startActivity(intent);

                    // Finish the splash screen activity
                    finish();
                }
            }, SPLASH_DELAY);
        }
    }
}