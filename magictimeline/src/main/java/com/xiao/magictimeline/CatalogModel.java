package com.xiao.magictimeline;

import android.graphics.Color;

/**
 * Created by xiao on 2017/4/22.
 */

public class CatalogModel {
    public  static final int TYPE_GROUP = 1;
    public  static final int TYPE_CHILD = 2;

    protected int type;
    protected String gruopName;

    protected String videoID;
    protected String childName;
    protected String videoTime;
    protected int fontColor = Color.BLACK;
    protected int videoSize ;
    protected String URL_M;
    protected String URL_H;

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGruopName() {
        return gruopName;
    }

    public void setGruopName(String gruopName) {
        this.gruopName = gruopName;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime;
    }

    public int getFontColor() {
        return fontColor;
    }

    public void setFontColor(int fontColor) {
        this.fontColor = fontColor;
    }

    public int getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(int videoSize) {
        this.videoSize = videoSize;
    }

    public String getURL_M() {
        return URL_M;
    }

    public void setURL_M(String URL_M) {
        this.URL_M = URL_M;
    }

    public String getURL_H() {
        return URL_H;
    }

    public void setURL_H(String URL_H) {
        this.URL_H = URL_H;
    }
}
