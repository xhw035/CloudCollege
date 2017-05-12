package com.cloud.college.network;

import java.util.List;

/**
 * Created by xiao on 2017/5/12.
 */

public class CollectInfoData {

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
         * courseImg : http://www.maiziedu.com/uploads/course/2016/05/12.jpg
         * courseName : Java 语言基础
         * score : 3.4
         * collectTime : 2015.09.23
         */

        private String courseID;
        private String courseImg;
        private String courseName;
        private double score;
        private String collectTime;

        public String getCourseID() {
            return courseID;
        }

        public void setCourseID(String courseID) {
            this.courseID = courseID;
        }

        public String getCourseImg() {
            return courseImg;
        }

        public void setCourseImg(String courseImg) {
            this.courseImg = courseImg;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getCollectTime() {
            return collectTime;
        }

        public void setCollectTime(String collectTime) {
            this.collectTime = collectTime;
        }
    }
}
