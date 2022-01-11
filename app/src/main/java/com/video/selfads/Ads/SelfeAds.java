package com.video.selfads.Ads;

import static com.video.selfads.Ads.full.SelfInterstitialAds.interstitialAdLoadCallback;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.video.selfads.Ads.full.FullScreenContentCallback;
import com.video.selfads.Ads.full.InterstitialAdLoadCallback;
import com.video.selfads.Ads.full.SelfInterstitialAds;
import com.video.selfads.OnInitializedSelfCompleteListener;
import com.video.selfads.R;
import com.video.selfads.model.GetAdsResponse;
import com.video.selfads.model.ImpressionAdsResponse;
import com.video.selfads.model.InterTitialArray;
import com.video.selfads.model.NativeArray;
import com.video.selfads.retrofit.APIContent;
import com.video.selfads.retrofit.ApiUtils;
import com.video.selfads.reverseprogressbar.ReverseProgressBar;
import com.vincan.medialoader.DefaultConfigFactory;
import com.vincan.medialoader.MediaLoader;
import com.vincan.medialoader.MediaLoaderConfig;
import com.vincan.medialoader.data.file.naming.HashCodeFileNameCreator;
import com.vincan.medialoader.download.DownloadListener;
import com.yqritc.scalablevideoview.ScalableVideoView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelfeAds {
    public static SelfeAds instance;
    public static Activity activity;
    public static String InfoLink = "";
    public static int skip_sec;

    private static final String TAG = "Selfe_Ads";


    // Native
    public static SelfNativeAds selfNativeAds;
    public static ArrayList<NativeArray> nativeArrays = new ArrayList<>();
    public static int native_pos;
    public static String Native = "";
    public static boolean isSelfNativeLoaded;

    public static String nativefail = "";

    // Full
    public static SelfInterstitialAds selfInterstitialAds;
    public static String PackageName;
    public static String Full = "";
    public static ArrayList<InterTitialArray> interTitialArrays = new ArrayList<>();
    public static int full_pos;
    public static String fullfail = "";
    static MediaPlayer mediaPlayer;
    static MediaPlayer mediaPlayer_native;
    public static MediaLoader mMediaLoader;
    public static boolean isSelfInterstitialLoaded;
    public static boolean ismute = true;
    public static boolean ismute_native = true;

    public static boolean NativeShow;

    public SelfeAds(Activity activity1) {
        activity = activity1;
    }

    public static SelfeAds getInstance(Activity activity1) {
        activity = activity1;
        if (instance == null) {
            instance = new SelfeAds(activity1);
        }
        return instance;
    }

    public static void initialize(Activity activity, String PackageID, OnInitializedSelfCompleteListener onInitializCompleteListener) {

        PackageName = PackageID;
        ApiUtils.getAPIService(APIContent.MainUrl).APIGetAdsBy(APIContent.GetAds, PackageID).enqueue(new Callback<GetAdsResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetAdsResponse> call, @NonNull Response<GetAdsResponse> response) {
                if (response.body() == null) {
                    onInitializCompleteListener.oninitializselfcomplete(false, "null");
                    return;
                }

                if (response.isSuccessful()) {
                    if (response.body().getInterTitialArray().size() == 00) {
                        onInitializCompleteListener.oninitializselfcomplete(false, response.body().getMeAge());
                        Log.e(TAG, "null: " + response.body().getInterTitialArray().size());
                    } else {
                        onInitializCompleteListener.oninitializselfcomplete(true, response.body().getMeAge());
                        SelfeAds.InfoLink = response.body().getInfoLink();
                        SelfeAds.skip_sec = response.body().getSkip_sec();
                        full_pos = randomItemFull(response.body().getInterTitialArray());
                        SelfeAds.Full = response.body().getInterTitialArray().get(full_pos).getVideo();
                        SelfeAds.getInstance(activity).preloadSelfAds(response.body().getNativeArray(), response.body().getInterTitialArray());
                    }
                } else {
                    onInitializCompleteListener.oninitializselfcomplete(false, "");
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetAdsResponse> call, @NonNull Throwable t) {
                SelfeAds.nativefail = t.getMessage();
                SelfeAds.fullfail = t.getMessage();
                Log.e(TAG, "onFailure: " + t.getMessage());
                onInitializCompleteListener.oninitializselfcomplete(false, t.getMessage());
            }
        });
    }


    public void preloadSelfAds(ArrayList<NativeArray> nativeArrays, ArrayList<InterTitialArray> interTitialArrays) {
        SelfeAds.nativeArrays = nativeArrays;
        SelfeAds.interTitialArrays = interTitialArrays;
        preloadSelfNativeAd();
        preloadSelfInterstitialAd();
    }

    public static void showSelfInterstitial(Activity activity, FullScreenContentCallback fullScreenContentCallback) {
        if (interTitialArrays.size() == 00) {
            Log.e(TAG, "NotSelfFull: ");
        } else {
            if (isSelfInterstitialLoaded) {
                SelfeAds.activity = activity;
                showDialog(SelfeAds.activity, fullScreenContentCallback);
            } else {
                fullScreenContentCallback.onclose();
            }
        }

//        com.google.android.gms.ads.interstitial.InterstitialAd googleInterstitial = googleInterstitialAd;
//
//        if (googleInterstitial != null) {
//            googleInterstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
//                @Override
//                public void onAdDismissedFullScreenContent() {
//                    BaseClass.interstitialCallBack();
//                    googleInterstitialAd = null;
//                    preloadGoogleInterstitialAd();
//                }
//
//                @Override
//                public void onAdShowedFullScreenContent() {
//                }
//
//                @Override
//                public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
//                    super.onAdFailedToShowFullScreenContent(adError);
//                }
//            });
//
//            googleInterstitial.show(activity);
//
//            BaseClass.showFullAd = false;
//            isGoogleInterstitialLoaded = false;
//            Log.d("TEMP", "google onAdLoaded: " + isGoogleInterstitialLoaded);
//            BaseClass.startAdHandler();
    }

    //
//    }
//
//    public void ManageLoadingDialog() {
//        try {
//            if (adLoadDialog != null) {
//                if (adLoadDialog.isShowing())
//                    adLoadDialog.dismiss();
//                adLoadDialog = null;
//            } else {
//                adLoadDialog = new Dialog(activity);
//                adLoadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                adLoadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                adLoadDialog.setContentView(R.layout.dialog_ad_loading);
//                adLoadDialog.setCancelable(false);
//                adLoadDialog.show();
//            }
//
//        } catch (Error | Exception e) {
//            Log.d(TAG, "ManageLoadingDialog: " + e);
//            e.printStackTrace();
//        }
//    }
//
//    public void preloadSelfAds(ArrayList<NativeArray> nativeArrays, ArrayList<InterTitialArray> interTitialArrays) {
//        this.nativeArrays = nativeArrays;
//        this.interTitialArrays = interTitialArrays;
//        preloadSelfNativeAd();
//        preloadGoogleInterstitialAd();
//    }

    public static void showDialog(Activity activity, FullScreenContentCallback fullScreenContentCallback) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_ads);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setAttributes(lp);
        LinearLayout li_main = dialog.findViewById(R.id.li_main);
        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        ImageView im_app_icon = dialog.findViewById(R.id.im_app_icon);
        ImageView imageView = dialog.findViewById(R.id.ImageView1);
        ImageView im_vol = dialog.findViewById(R.id.im_vol);
        TextView te_app_name = dialog.findViewById(R.id.te_app_name);
        TextView te_close = dialog.findViewById(R.id.te_close);
        TextView te_dec = dialog.findViewById(R.id.te_dec);
        RatingBar ratingBars = dialog.findViewById(R.id.ratingBars);
        ImageView im_open_link = dialog.findViewById(R.id.im_open_link);
        ReverseProgressBar reverseProgressBar = dialog.findViewById(R.id.reverseProgressBar);
        ScalableVideoView videoView = dialog.findViewById(R.id.videoView1);
        CardView ca_view = dialog.findViewById(R.id.ca_view);
        Glide.with(activity).load(interTitialArrays.get(full_pos).getIcon()).into(im_app_icon);
        Glide.with(activity).load(interTitialArrays.get(full_pos).getImage()).into(imageView);

        ApiCall(1, interTitialArrays.get(full_pos).getId());

        ca_view.setCardBackgroundColor(Color.parseColor(interTitialArrays.get(full_pos).getColor()));

        ca_view.setOnClickListener(v -> {
            ApiCall(2, interTitialArrays.get(full_pos).getId());
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + interTitialArrays.get(full_pos).getpName()));
            activity.startActivity(intent);
        });

        li_main.setOnClickListener(v -> {
            ApiCall(2, interTitialArrays.get(full_pos).getId());
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + interTitialArrays.get(full_pos).getpName()));
            activity.startActivity(intent);
        });

        int Ad_close_sec = SelfeAds.skip_sec * 1000;

        reverseProgressBar.setMax(Ad_close_sec);
        reverseProgressBar.setProgress(Ad_close_sec);
        reverseProgressBar.setStrokeWidth(5);
        reverseProgressBar.strockCap(true);
        new CountDownTimer(Ad_close_sec, 10) {

            public void onTick(long millisUntilFinished) {
                reverseProgressBar.setProgress((int) (millisUntilFinished));
                te_close.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                imgClose.setVisibility(View.VISIBLE);
                reverseProgressBar.setVisibility(View.GONE);
                te_close.setVisibility(View.GONE);
            }

        }.start();


        MediaLoaderConfig mediaLoaderConfig = new MediaLoaderConfig.Builder(activity)
                .cacheRootDir(DefaultConfigFactory.createCacheRootDir(activity, "your_cache_dir"))//directory for cached files
                .cacheFileNameGenerator(new HashCodeFileNameCreator())//names for cached files
                .maxCacheFilesCount(100)//max files count
                .maxCacheFilesSize(100 * 1024 * 1024)//max files size
                .maxCacheFileTimeLimit(5 * 24 * 60 * 60)//max file time
                .downloadThreadPoolSize(3)//download thread size
                .downloadThreadPriority(Thread.NORM_PRIORITY)//download thread priority
                .build();

        mMediaLoader = MediaLoader.getInstance(activity);
        mMediaLoader.init(mediaLoaderConfig);


        new Thread(new Runnable() {
            @Override
            public void run() {
                // Run whatever background code you want here.

                mMediaLoader.addDownloadListener(Full, new DownloadListener() {
                    @Override
                    public void onProgress(String url, File file, int progress) {
                        Log.d("CheckProgress_d", "" + progress);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("CheckProgress_d", "" + e.getMessage());
                    }
                });


                boolean isCached = mMediaLoader.isCached(Full);
                if (isCached) {

                }
//        videoView.setVideoPath(mMediaLoader.getProxyUrl(mUrl));
//        videoView.start();

                try {

                    videoView.setDataSource(mMediaLoader.getProxyUrl(Full));
                    imageView.setVisibility(View.VISIBLE);
                    videoView.prepareAsync(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            videoView.setLooping(false);
                            videoView.start();
                            mp.setLooping(false);
                            mediaPlayer = mp;
                            mediaPlayer.setVolume(0, 0);
                            imageView.setVisibility(View.GONE);
                            im_vol.setVisibility(View.VISIBLE);
                        }
                    });
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            imageView.setVisibility(View.VISIBLE);
                            videoView.setVisibility(View.GONE);
                            im_vol.setVisibility(View.GONE);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        im_open_link.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(SelfeAds.InfoLink));
            activity.startActivity(intent);
        });

        double d = interTitialArrays.get(full_pos).getRate();
        float f = (float) d;
        ratingBars.setRating((float) d);
        te_app_name.setText(interTitialArrays.get(full_pos).getAppName());
        te_dec.setText(interTitialArrays.get(full_pos).getDeC());


       /* if (full_aBoolean) {
            uri = Uri.parse(Full_Url);
        } else {
            uri = Uri.parse(interTitialArrays.get(full_pos).getVideo());
        }*/

        im_vol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ismute) {
                    mediaPlayer.setVolume(1, 1);
                    ismute = false;
                    im_vol.setImageResource(R.drawable.unmute);
                } else {
                    mediaPlayer.setVolume(0, 0);
                    ismute = true;
                    im_vol.setImageResource(R.drawable.mute);
                }
            }
        });


        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAdLoadCallback != null) {
                    dialog.dismiss();
                    interstitialAdLoadCallback.onAdClose();
                    fullScreenContentCallback.onclose();
                    try {
                        int pos = randomItemFull(interTitialArrays);
                        if (Full.equals(interTitialArrays.get(pos).getVideo())) {
                            Log.e("TAG", "Not");
                        } else {
                            full_pos = pos;
                            Full = "";
                            Full = interTitialArrays.get(pos).getVideo();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        dialog.show();
    }

    private static void ApiCall(int tag, int id) {
        ApiUtils.getAPIService(APIContent.MainUrl).ImpressionAdsBy(APIContent.ImpressionAds, id, tag, PackageName).enqueue(new Callback<ImpressionAdsResponse>() {
            @Override
            public void onResponse(@NonNull Call<ImpressionAdsResponse> call, @NonNull Response<ImpressionAdsResponse> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(@NonNull Call<ImpressionAdsResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    public static int randomItemFull(ArrayList<InterTitialArray> arr_data) {
        Random random = new Random();
        int index = random.nextInt(arr_data.size());
        return index;
    }

    public static int randomItemNative(ArrayList<NativeArray> arr_data) {
        Random random = new Random();
        int index = random.nextInt(arr_data.size());
        return index;
    }

    public static void preloadSelfInterstitialAd() {
        if (isSelfInterstitialLoaded) {
            return;
        }
        if (interTitialArrays.size() == 0) {
            Log.e(TAG, "preloadSelfNativeAd: ");
        } else {
            selfInterstitialAds = new SelfInterstitialAds(activity);
            isSelfInterstitialLoaded = true;
            selfInterstitialAds.load(activity, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded() {
                    isSelfInterstitialLoaded = true;
                    Log.e(TAG, "Native Load :- ");
                }

                @Override
                public void onAdClose() {
                }

                @Override
                public void onAdFailedToLoad(String text) {
                    Log.e(TAG, "Native Fail" + text);
                }
            });
        }


//        if (googleInterstitialAd == null) {
//            AdRequest adRequest = new AdRequest.Builder().build();
//            com.google.android.gms.ads.interstitial.InterstitialAd.load(activity, BaseClass.AD_MOB_INTERSTITIAL, adRequest, new InterstitialAdLoadCallback() {
//
//                @Override
//                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                    Log.d(TAG, "googleInterstitialPreLoad: " + loadAdError.getMessage());
//                }
//
//                @Override
//                public void onAdLoaded(@NonNull com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
//                    super.onAdLoaded(interstitialAd);
//                    Log.d(TAG, "googleInterstitialPreLoad onAdLoaded: " + interstitialAd);
//                    googleInterstitialAd = interstitialAd;
//                    isGoogleInterstitialLoaded = true;
//                    Log.d("TEMP", "google onAdLoaded: " + isGoogleInterstitialLoaded);
//                }
//            });
//        }
    }


    @SuppressLint("MissingPermission")
    public static void preloadSelfNativeAd() {
        if (isSelfNativeLoaded) {
            return;
        }
        if (nativeArrays.size() == 0) {
            Log.e(TAG, "preloadSelfNativeAd: ");
            return;
        } else {
            native_pos = randomItemNative(nativeArrays);
            Native = nativeArrays.get(native_pos).getVideo();
            selfNativeAds = new SelfNativeAds(activity);
            isSelfNativeLoaded = true;
            selfNativeAds.load(activity, new NativeAdLoadCallback() {
                @Override
                public void onAdLoaded() {
                    isSelfNativeLoaded = true;
                }

                @Override
                public void onAdClose() {
                }

                @Override
                public void onAdFailedToLoad(String text) {
                    Log.e(TAG, "Native Fail" + text);
                }
            });
        }

    }


    public static void showSelfNative(LinearLayout layout, Activity activity) {
        if (nativeArrays.size() == 00) {
            Log.e(TAG, "showSelfNative: ");
        } else {
            if (layout != null) {
                layout.removeAllViews();
                if (isSelfNativeLoaded) {
                    SelfeAds.activity = activity;
                    native_pos = randomItemNative(nativeArrays);
                    inflateSelfNative(layout, nativeArrays);
                }
                preloadSelfNativeAd();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public static void inflateSelfNative(LinearLayout ad_layout, ArrayList<NativeArray> nativeArrayArrayList) {
        try {
            ad_layout.setVisibility(View.VISIBLE);
            LayoutInflater inflater = LayoutInflater.from(activity);
            View view = inflater.inflate(R.layout.common_admob_native, null);

            ScalableVideoView ad_media = view.findViewById(R.id.ad_media);
            TextView ad_headline = view.findViewById(R.id.ad_headline);
            TextView ad_body = view.findViewById(R.id.ad_body);
            ImageView im_vol_native = view.findViewById(R.id.im_vol_native);
            TextView ad_call_to_action = view.findViewById(R.id.ad_call_to_action);
            ImageView ad_app_icon = view.findViewById(R.id.ad_app_icon);
            ImageView im_open_link = view.findViewById(R.id.im_open_link);
            ImageView main_image = view.findViewById(R.id.main_image);
            TextView ad_price = view.findViewById(R.id.ad_price);
            RatingBar ad_stars = view.findViewById(R.id.ad_stars);
            TextView ad_store = view.findViewById(R.id.ad_store);
            TextView ad_advertiser = view.findViewById(R.id.ad_advertiser);

            ApiCall(1, nativeArrayArrayList.get(native_pos).getId());


            im_open_link.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(SelfeAds.InfoLink));
                activity.startActivity(intent);
            });
            ad_headline.setText(nativeArrayArrayList.get(native_pos).getDeC());
            ad_body.setText(nativeArrayArrayList.get(native_pos).getAppName());
            ad_call_to_action.setText("Install");
            ad_store.setText(nativeArrayArrayList.get(native_pos).getAppName());
            ad_advertiser.setText(nativeArrayArrayList.get(native_pos).getAppName());
            ad_call_to_action.setBackgroundColor(Color.parseColor(nativeArrayArrayList.get(full_pos).getColor()));

            ad_call_to_action.setOnClickListener(v -> {
                ApiCall(2, nativeArrayArrayList.get(native_pos).getId());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + nativeArrayArrayList.get(full_pos).getpName()));
                activity.startActivity(intent);
            });


            main_image.setVisibility(View.VISIBLE);
            Glide.with(activity).load(nativeArrayArrayList.get(native_pos).getIcon()).into(ad_app_icon);
            if (Native == null) {
                Glide.with(activity).load(nativeArrayArrayList.get(native_pos).getImage()).into(main_image);
            } else {
                VideoLoad(main_image, ad_media, nativeArrayArrayList,im_vol_native);
            }
            ad_layout.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void VideoLoad(ImageView imageView, ScalableVideoView videoView, ArrayList<NativeArray> nativeArrayArrayList,ImageView im_vol_native) {
        Glide.with(activity).load(nativeArrayArrayList.get(native_pos).getImage()).into(imageView);
        MediaLoaderConfig mediaLoaderConfig = new MediaLoaderConfig.Builder(activity)
                .cacheRootDir(DefaultConfigFactory.createCacheRootDir(activity, "your_cache_dir"))//directory for cached files
                .cacheFileNameGenerator(new HashCodeFileNameCreator())//names for cached files
                .maxCacheFilesCount(100)//max files count
                .maxCacheFilesSize(100 * 1024 * 1024)//max files size
                .maxCacheFileTimeLimit(5 * 24 * 60 * 60)//max file time
                .downloadThreadPoolSize(3)//download thread size
                .downloadThreadPriority(Thread.NORM_PRIORITY)//download thread priority
                .build();

        mMediaLoader = MediaLoader.getInstance(activity);
        mMediaLoader.init(mediaLoaderConfig);


        new Thread(new Runnable() {
            @Override
            public void run() {
                // Run whatever background code you want here.

                mMediaLoader.addDownloadListener(Native, new DownloadListener() {
                    @Override
                    public void onProgress(String url, File file, int progress) {
                        Log.d("CheckProgress_d", "" + progress);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("CheckProgress_d", "" + e.getMessage());
                    }
                });


                boolean isCached = mMediaLoader.isCached(Native);
                if (isCached) {

                }
                try {

                    videoView.setDataSource(mMediaLoader.getProxyUrl(Native));
                    imageView.setVisibility(View.VISIBLE);
                    videoView.prepareAsync(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            videoView.setLooping(false);
                            videoView.start();
                            mp.setLooping(false);
                            mediaPlayer_native = mp;
                            mediaPlayer_native.setVolume(0, 0);
                            imageView.setVisibility(View.GONE);
                            im_vol_native.setVisibility(View.VISIBLE);
                        }
                    });
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            imageView.setVisibility(View.VISIBLE);
                            videoView.setVisibility(View.GONE);
                            im_vol_native.setVisibility(View.GONE);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }


                im_vol_native.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ismute_native) {
                            mediaPlayer_native.setVolume(1, 1);
                            ismute_native = false;
                            im_vol_native.setImageResource(R.drawable.unmute);
                        } else {
                            mediaPlayer_native.setVolume(0, 0);
                            ismute_native = true;
                            im_vol_native.setImageResource(R.drawable.mute);
                        }
                    }
                });


            }
        }).start();
    }
}
