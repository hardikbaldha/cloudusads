package com.video.selfads.Ads.full;

public interface InterstitialAdLoadCallback {
    void onAdLoaded();
    void onAdClose();
    void onAdFailedToLoad(String text);
}