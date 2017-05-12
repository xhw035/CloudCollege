package com.cloud.college.network;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiao on 2017/5/10.
 */

public class CourseCatalogData {

    /**
     * courseID : 0
     * courseName : 安卓应用开发-Activity
     * thumbnail : http://www.maiziedu.com/uploads/course/2016/04/Activity.jpg
     * isCollected : false
     * data : [{"chapter":{"seq":0,"name":"安卓开发入门"},"video":[{"videoID":"0","seq":0,"name":"Activity概念和简单使用","duration":"18:39","size":50,"url_h":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/L.mp4","url_m":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/M.mp4"},{"videoID":"1","seq":1,"name":"Activity基本配置","duration":"11:04","size":34,"url_h":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/L.mp4","url_m":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/M.mp4"},{"videoID":"2","seq":2,"name":"Activity启动方法(上)","duration":"16:24","size":36,"url_h":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/L.mp4","url_m":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/M.mp4"},{"videoID":"3","seq":3,"name":"编辑Activity启动方法(下）","duration":"09:04","size":23,"url_h":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/L.mp4","url_m":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/M.mp4"}]},{"chapter":{"seq":1,"name":"安卓开发进阶"},"video":[{"videoID":"0","seq":0,"name":"Activity显式隐式启动","duration":"12:23","size":25,"url_h":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/L.mp4","url_m":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/M.mp4"},{"videoID":"1","seq":1,"name":"Activity生命周期","duration":"19:54","size":39,"url_h":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/L.mp4","url_m":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/M.mp4"},{"videoID":"2","seq":2,"name":"加载模式-上","duration":"14:46","size":29,"url_h":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/L.mp4","url_m":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/M.mp4"},{"videoID":"3","seq":3,"name":"加载模式-中","duration":"03:52","size":12,"url_h":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/L.mp4","url_m":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/M.mp4"},{"videoID":"4","seq":4,"name":"9.加载模式-下","duration":"13:15","size":37,"url_h":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/L.mp4","url_m":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/M.mp4"}]},{"chapter":{"seq":2,"name":"安卓开发精通"},"video":[{"videoID":"0","seq":1,"name":"IntentFlag-上","duration":"13:57","size":38,"url_h":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/L.mp4","url_m":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/M.mp4"},{"videoID":"1","seq":1,"name":"11.IntentFlag-下","duration":"12:32","size":34,"url_h":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/L.mp4","url_m":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/M.mp4"}]}]
     */

    private String courseID;
    private String courseName;
    private String thumbnail;
    private boolean isCollected;
    private ArrayList<DataBean> data;

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(boolean isCollected) {
        this.isCollected = isCollected;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * chapter : {"seq":0,"name":"安卓开发入门"}
         * video : [{"videoID":"0","seq":0,"name":"Activity概念和简单使用","duration":"18:39","size":50,"url_h":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/L.mp4","url_m":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/M.mp4"},{"videoID":"1","seq":1,"name":"Activity基本配置","duration":"11:04","size":34,"url_h":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/L.mp4","url_m":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/M.mp4"},{"videoID":"2","seq":2,"name":"Activity启动方法(上)","duration":"16:24","size":36,"url_h":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/L.mp4","url_m":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/M.mp4"},{"videoID":"3","seq":3,"name":"编辑Activity启动方法(下）","duration":"09:04","size":23,"url_h":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/L.mp4","url_m":"http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/M.mp4"}]
         */

        private ChapterBean chapter;
        private List<VideoBean> video;

        public ChapterBean getChapter() {
            return chapter;
        }

        public void setChapter(ChapterBean chapter) {
            this.chapter = chapter;
        }

        public List<VideoBean> getVideo() {
            return video;
        }

        public void setVideo(List<VideoBean> video) {
            this.video = video;
        }

        public static class ChapterBean {
            /**
             * seq : 0
             * name : 安卓开发入门
             */

            private int seq;
            private String name;

            public int getSeq() {
                return seq;
            }

            public void setSeq(int seq) {
                this.seq = seq;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class VideoBean {
            /**
             * videoID : 0
             * seq : 0
             * name : Activity概念和简单使用
             * duration : 18:39
             * size : 50
             * url_h : http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/L.mp4
             * url_m : http://v1.mukewang.com/d79a0b8f-144d-40f6-ac87-5ac2d1e5d6b7/M.mp4
             */

            private String videoID;
            private int seq;
            private String name;
            private String duration;
            private int size;
            private String url_h;
            private String url_m;

            public String getVideoID() {
                return videoID;
            }

            public void setVideoID(String videoID) {
                this.videoID = videoID;
            }

            public int getSeq() {
                return seq;
            }

            public void setSeq(int seq) {
                this.seq = seq;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public String getUrl_h() {
                return url_h;
            }

            public void setUrl_h(String url_h) {
                this.url_h = url_h;
            }

            public String getUrl_m() {
                return url_m;
            }

            public void setUrl_m(String url_m) {
                this.url_m = url_m;
            }
        }
    }
}
