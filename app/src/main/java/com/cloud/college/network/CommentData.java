package com.cloud.college.network;

import java.util.List;

/**
 * Created by xiao on 2017/5/11.
 */

public class CommentData {

    /**
     * courseID : 0
     * myScore : 3.5
     * data : [{"userImg":"http://www.maiziedu.com/uploads/avatar/2016/04/104_vM1exiP.png","nickname":"徐叶枫","time":"2017.05.02","desc":"帅啊"},{"userImg":"http://www.maiziedu.com/uploads/avatar/2016/04/104_vM1exiP.png","nickname":"尔代夫","time":"1017.05.02","desc":"斯蒂芬森发"},{"userImg":"http://www.maiziedu.com/uploads/avatar/2016/04/104_vM1exiP.png","nickname":"到底","time":"2017.07.02","desc":"是的是的打算的"},{"userImg":"http://www.maiziedu.com/uploads/avatar/2016/04/104_vM1exiP.png","nickname":"徐叶的速度枫","time":"2017.05.12","desc":"爱啥啥"},{"userImg":"http://www.maiziedu.com/uploads/avatar/2016/04/104_vM1exiP.png","nickname":"啊啥啥大事","time":"2037.05.02","desc":"大大的速度"},{"userImg":"http://www.maiziedu.com/uploads/avatar/2016/04/104_vM1exiP.png","nickname":"1的速度","time":"2017.08.02","desc":"大说的都是多少分发不发"},{"userImg":"http://www.maiziedu.com/uploads/avatar/2016/04/104_vM1exiP.png","nickname":"千万千万","time":"2014.05.02","desc":"修辞手法的"}]
     */

    private String courseID;
    private double myScore;
    private List<DataBean> data;

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public double getMyScore() {
        return myScore;
    }

    public void setMyScore(double myScore) {
        this.myScore = myScore;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userImg : http://www.maiziedu.com/uploads/avatar/2016/04/104_vM1exiP.png
         * nickname : 徐叶枫
         * time : 2017.05.02
         * desc : 帅啊
         */

        private String userImg;
        private String nickname;
        private String time;
        private String desc;

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
