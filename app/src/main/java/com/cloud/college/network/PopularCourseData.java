package com.cloud.college.network;

import java.util.List;

/**
 * Created by xiao on 2017/5/9.
 */

public class PopularCourseData {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * courseName : 11
         * courseImg : http://192.168.191.1:8080/course/image?imgname=1494324247553.jpg
         * courseID : 40283f815beca962015becaa7c4f0000
         */

        private String courseName;
        private String courseImg;
        private String courseID;

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseImg() {
            return courseImg;
        }

        public void setCourseImg(String courseImg) {
            this.courseImg = courseImg;
        }

        public String getCourseID() {
            return courseID;
        }

        public void setCourseID(String courseID) {
            this.courseID = courseID;
        }
    }

}
