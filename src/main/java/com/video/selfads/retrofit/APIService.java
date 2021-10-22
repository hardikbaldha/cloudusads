package com.video.selfads.retrofit;

import com.video.selfads.model.GetAdsResponse;
import com.video.selfads.model.ImpressionAdsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface APIService {

    @POST
    @FormUrlEncoded
    Call<GetAdsResponse> APIGetAdsBy(@Url String str,
                                     @Field("p_name") String p_name);

    @POST
    @FormUrlEncoded
    Call<ImpressionAdsResponse> ImpressionAdsBy(@Url String str,
                                                @Field("id") int id,
                                                @Field("tag") int tag,
                                                @Field("p_name") String p_name);


}
