package com.video.selfads.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiEventClient {

    private static Retrofit retrofit;
    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(600, TimeUnit.SECONDS)
            .writeTimeout(650, TimeUnit.SECONDS)
            .build();
    public static Retrofit getClient(String str) {

        return new Builder().client(okHttpClient).baseUrl(str).addConverterFactory(GsonConverterFactory.create()).build();
    }
}
