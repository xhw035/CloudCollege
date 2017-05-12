package com.cloud.college.network;

/**
 * Created by xiao on 2017/5/11.
 */

public class universalResponseData {

    /**
     * state : 0
     * desc : success
     */

    private int state;
    private String desc;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
