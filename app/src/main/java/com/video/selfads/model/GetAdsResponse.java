package com.video.selfads.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetAdsResponse {

    @SerializedName("flag")
    Boolean flag;
    @SerializedName("message")
    String meAge;
    @SerializedName("info_link")
    String infoLink;
    @SerializedName("skip_sec")
    private Integer skip_sec;
    @SerializedName("NativeArray")
    ArrayList<NativeArray> nativeArray = null;
    @SerializedName("InterstitialArray")
    ArrayList<InterTitialArray> interTitialArray = null;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getMeAge() {
        return meAge;
    }

    public void setMeAge(String meAge) {
        this.meAge = meAge;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    public Integer getSkip_sec() {
        return skip_sec;
    }

    public void setSkip_sec(Integer skip_sec) {
        this.skip_sec = skip_sec;
    }

    public ArrayList<NativeArray> getNativeArray() {
        return nativeArray;
    }

    public void setNativeArray(ArrayList<NativeArray> nativeArray) {
        this.nativeArray = nativeArray;
    }

    public ArrayList<InterTitialArray> getInterTitialArray() {
        return interTitialArray;
    }

    public void setInterTitialArray(ArrayList<InterTitialArray> interTitialArray) {
        this.interTitialArray = interTitialArray;
    }
}
