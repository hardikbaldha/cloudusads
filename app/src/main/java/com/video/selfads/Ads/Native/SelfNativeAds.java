package com.video.selfads.Ads.Native;

import static com.video.selfads.Ads.SelfeAds.isSelfNativeLoaded;
import static com.video.selfads.Ads.SelfeAds.native_fail;

import android.app.Activity;

import com.vincan.medialoader.MediaLoader;

public class SelfNativeAds {
    Activity activity;
    NativeAdLoadCallback nativeAdLoadCallback;
    boolean isLoaded;
    private MediaLoader mMediaLoader;
    //    ArrayList<NativeArray> nativeArrays = new ArrayList<>();
    private NativeContentCallback nativeContentCallback;


    public SelfNativeAds(Activity activity) {
        this.activity = activity;
    }

    public void load(Activity activity, NativeAdLoadCallback nativeAdLoadCallback) {
        this.activity = activity;
        this.nativeAdLoadCallback = nativeAdLoadCallback;
        if (isSelfNativeLoaded) {
            this.nativeAdLoadCallback.onAdLoaded();
        } else {
            this.nativeAdLoadCallback.onAdFailedToLoad("fail : " + native_fail);
        }
    }

    public boolean isLoaded() {
        return isLoaded;
    }


    public NativeContentCallback getNativeContentCallback() {
        return nativeContentCallback;
    }

    public void setNativeContentCallback(NativeContentCallback nativeContentCallback) {
        this.nativeContentCallback = nativeContentCallback;
    }
}
