package com.video.selfads.model;

import com.google.gson.annotations.SerializedName;

public class ImpressionAdsResponse {
    @SerializedName("flag")
    private Boolean flag;
    @SerializedName("message")
    private String meAge;
    @SerializedName("data")
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
