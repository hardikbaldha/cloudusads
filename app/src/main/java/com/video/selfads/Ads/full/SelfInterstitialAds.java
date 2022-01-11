package com.video.selfads.Ads.full;

import android.app.Activity;
import static com.video.selfads.Ads.SelfeAds.full_fail;
import static com.video.selfads.Ads.SelfeAds.isSelfInterstitialLoaded;

public class SelfInterstitialAds {
    Activity activity;
    public static InterstitialAdLoadCallback interstitialAdLoadCallback;
    private FullScreenContentCallback fullScreenContentCallback;

    public SelfInterstitialAds(Activity activity) {
        this.activity = activity;
    }

    public void load(Activity activity, InterstitialAdLoadCallback interstitialAdLoadCallback) {
        this.activity = activity;
        this.interstitialAdLoadCallback = interstitialAdLoadCallback;
        if (isSelfInterstitialLoaded){
            SelfInterstitialAds.interstitialAdLoadCallback.onAdLoaded();
        }else {
            SelfInterstitialAds.interstitialAdLoadCallback.onAdFailedToLoad("fail : " + full_fail);
        }
    }

    public FullScreenContentCallback getFullScreenContentCallback() {
        return fullScreenContentCallback;
    }

    public void setFullScreenContentCallback(FullScreenContentCallback fullScreenContentCallback) {
        this.fullScreenContentCallback = fullScreenContentCallback;
    }


}
