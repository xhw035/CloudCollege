package com.cloud.college.network;

import java.util.List;

/**
 * Created by xiao on 2017/5/9.
 */

public class BannerData {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * courseID : 0
         * bannerURL : http://edu-image.nosdn.127.net/0B79A2CB919106942DF25D4A73BB97B1.jpg?imageView&thumbnail=960y440&quality=100
         */

        private String courseID;
        private String bannerURL;

        public String getCourseID() {
            return courseID;
        }

        public void setCourseID(String courseID) {
            this.courseID = courseID;
        }

        public String getBannerURL() {
            return bannerURL;
        }

        public void setBannerURL(String bannerURL) {
            this.bannerURL = bannerURL;
        }
    }
}
