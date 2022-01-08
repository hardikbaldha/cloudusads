package com.video.selfads.Ads.full;

import android.app.Activity;
import static com.video.selfads.Ads.SelfeAds.fullfail;
import static com.video.selfads.Ads.SelfeAds.isSelfNativeLoaded;

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
        if (isSelfNativeLoaded){
            SelfInterstitialAds.interstitialAdLoadCallback.onAdLoaded();
        }else {
            SelfInterstitialAds.interstitialAdLoadCallback.onAdFailedToLoad("fail : " + fullfail);
        }
    }

    public FullScreenContentCallback getFullScreenContentCallback() {
        return fullScreenContentCallback;
    }

    public void setFullScreenContentCallback(FullScreenContentCallback fullScreenContentCallback) {
        this.fullScreenContentCallback = fullScreenContentCallback;
    }


}
