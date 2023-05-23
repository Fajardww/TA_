package com.example.posdiasthadjayakediri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.posdiasthadjayakediri.Api.NetworkClient;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    SharedPreferences preferences = getSharedPreferences(NetworkClient.PREFERENCE_NAME, MODE_PRIVATE);
                    boolean isloggin = preferences.getBoolean(NetworkClient.KEY_ISE_LOGGED_IN, false);
                    String level = preferences.getString(NetworkClient.KEY_LEVEL, "");

                    if (isloggin) {
                        if (level.equals("1")) {
                            startActivity(new Intent(getApplicationContext(), DashboardActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();
                        } else {
                            startActivity(new Intent(getApplicationContext(), KasirActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();
                        }
                    }
                    else {
                        startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
                    }
                    finish();

//                    startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
//                    finish();
                }
            }
        };
        thread.start();
    }
}