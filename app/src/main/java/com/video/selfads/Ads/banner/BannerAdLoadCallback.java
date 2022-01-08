package com.video.selfads.Ads.banner;

public interface BannerAdLoadCallback {
    void onAdLoaded();
    void onAdClose();
    void onAdFailedToLoad(String text);
}
