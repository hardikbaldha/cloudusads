package com.video.selfads;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.VideoView;
import com.video.selfads.Ads.SelfeAds;
import com.video.selfads.Ads.full.FullScreenContentCallback;
import com.vincan.medialoader.MediaLoader;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button btnEvent;
    Button btnEvent_native;
/*    SelfInterstitialAds selfInterstitialAds;
    SelfNativeAds selfNativeAds;*/
    private MediaLoader mMediaLoader;
    VideoView videoView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnEvent = findViewById(R.id.btnEvent);
        videoView = findViewById(R.id.videoView);
        btnEvent_native = findViewById(R.id.btnEvent_native);

        btnEvent.setText("Loading");

        btnEvent_native.setText("Loading");

        btnEvent.setOnClickListener(v -> {
         SelfeAds.showSelfInterstitial(MainActivity.this, new FullScreenContentCallback() {
             @Override
             public void onclose() {
                 Log.e("ADDDDD","close");
             }
         });
        });

        btnEvent_native.setOnClickListener(v -> {
            SelfeAds.showSelfNative(findViewById(R.id.native_layout),MainActivity.this);
        });

    }

}
