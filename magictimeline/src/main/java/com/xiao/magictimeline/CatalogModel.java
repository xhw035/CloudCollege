package com.xiao.magictimeline;

/**
 * Created by xiao on 2017/4/22.
 */

public class CatalogModel {
    public  static final int TYPE_GROUP = 1;
    public  static final int TYPE_CHILD = 2;

    protected int type;
    protected String gruopName;
    protected String childName;

    public String getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime;
    }

    protected String videoTime;

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



}
