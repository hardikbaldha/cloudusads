package com.video.selfads.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NativeArray {
    @SerializedName("id")
    private Integer id;
    @SerializedName("icon")
    private String icon;
    @SerializedName("app_name")
    private String appName;
    @SerializedName("desc")
    private String deC;
    @SerializedName("image")
    private String image;
    @SerializedName("video")
    private String video;
    @SerializedName("p_name")
    private String pName;
    @SerializedName("rate")
    private Double rate;
    @SerializedName("type")
    private String type;
    @SerializedName("status")
    private Integer tatu;
    @SerializedName("monetisation_event_tbls")
    private ArrayList<String> monetiAtionEventTbl = null;
    @SerializedName("color")
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDeC() {
        return deC;
    }

    public void setDeC(String deC) {
        this.deC = deC;
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

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTatu() {
        return tatu;
    }

    public void setTatu(Integer tatu) {
        this.tatu = tatu;
    }

    public ArrayList<String> getMonetiAtionEventTbl() {
        return monetiAtionEventTbl;
    }

    public void setMonetiAtionEventTbl(ArrayList<String> monetiAtionEventTbl) {
        this.monetiAtionEventTbl = monetiAtionEventTbl;
    }
}
