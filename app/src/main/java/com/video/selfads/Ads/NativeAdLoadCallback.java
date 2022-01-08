package com.video.selfads.Ads;

public interface NativeAdLoadCallback {
    void onAdLoaded();
    void onAdClose();
    void onAdFailedToLoad(String text);
}
