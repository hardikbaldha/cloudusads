package com.video.selfads.Ads.Native;

public interface NativeAdLoadCallback {
    void onAdLoaded();
    void onAdClose();
    void onAdFailedToLoad(String text);
}
