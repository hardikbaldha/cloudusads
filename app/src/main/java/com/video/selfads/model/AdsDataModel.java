package com.video.selfads.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AdsDataModel {
    @SerializedName("id")
    String id;
    @SerializedName("icon")
    String icon;
    @SerializedName("app_name")
    String app_name;
    @SerializedName("desc")
    String desc;
    @SerializedName("image")
    String image;
    @SerializedName("video")
    String video;
    @SerializedName("p_name")
    String p_name;
    @SerializedName("rate")
    String rate;
    @SerializedName("type")
    String type;
    @SerializedName("status")
    String status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
