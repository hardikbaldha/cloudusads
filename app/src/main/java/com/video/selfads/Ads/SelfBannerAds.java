package com.video.selfads.Ads.banner;

import static com.video.selfads.Ads.SelfeAds.banner_fail;
import static com.video.selfads.Ads.SelfeAds.isSelfBannerLoaded;

import android.app.Activity;

public class SelfBannerAds {
    Activity activity;
    BannerAdLoadCallback bannerAdLoadCallback;
    boolean isLoaded;
    //    ArrayList<NativeArray> nativeArrays = new ArrayList<>();
    private BannerContentCallback bannerContentCallback;


    public SelfBannerAds(Activity activity) {
        this.activity = activity;
    }

    public void load(Activity activity, BannerAdLoadCallback bannerAdLoadCallback) {
        this.activity = activity;
        this.bannerAdLoadCallback = bannerAdLoadCallback;
        if (isSelfBannerLoaded) {
            this.bannerAdLoadCallback.onAdLoaded();
        } else {
            this.bannerAdLoadCallback.onAdFailedToLoad("fail : " + banner_fail);
        }
    }

    public boolean isLoaded() {
        return isLoaded;
    }


    public BannerContentCallback getBannerContentCallback() {
        return bannerContentCallback;
    }

    public void setNativeContentCallback(BannerContentCallback bannerContentCallback) {
        this.bannerContentCallback = bannerContentCallback;
    }
}
