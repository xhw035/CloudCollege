package com.cloud.college.network;

import java.util.List;

/**
 * Created by xiao on 2017/5/10.
 */

public class SearchResultData {

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
         * studyNumber : 12345
         */

        private String courseID;
        private String courseImg;
        private String courseName;
        private double score;
        private int studyNumber;

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

        public int getStudyNumber() {
            return studyNumber;
        }

        public void setStudyNumber(int studyNumber) {
            this.studyNumber = studyNumber;
        }
    }
}
