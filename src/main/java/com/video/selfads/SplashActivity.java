package com.video.selfads;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.video.selfads.Ads.SelfeAds;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            load();
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 6000);
    }

    private void load() {
        SelfeAds.initialize(this, getPackageName(), new OnInitializCompleteListener() {
            @Override
            public void onInitializComplete(Boolean var1,String message) {
                Log.i("SELFADS", "" + var1);
            }
        });
    }
}