package com.cloud.college.network;

/**
 * Created by xiao on 2017/5/11.
 */

public class CourseInfoData {

    /**
     * courseID : 0
     * courseName : 安卓应用开发-Activity
     * score : 3.4
     * studyNumber : 12345
     * courseDesc : Android智能手机的迅猛发展，巨大的市场份额激发很多传统IT公司转向APP应用开发，未来十年Android APP开发工程师仍然是炙手可热的职位。本课程将通过实践教会你Andriod应用开发最常用的元素-Activity，快速入门Android应用开发。
     * providerImg : http://www.maiziedu.com/images/index/logo_greenx2.png
     * providerName : 麦子学院
     * roviderDesc : 麦子学院是成都麦子信息技术有限公司旗下一个IT在线教育平台，目前已有30万注册用户，10万以上APP下载量，5000小时视频内容。我们从不说空话，专注于IT在线教育，脱离传统教育的束缚，让你走哪学哪，想学就学。逗比的老师，贴心的助教，在这儿你能感受到来自五湖四海伙伴们热情和踏实的学习态度！
     * teacherImg : http://www.maiziedu.com/uploads/avatar/2016/04/104_KMignJ2.png
     * teacherName : Mike Mei
     * teacherDesc : 多年移动端app主程开发经验，熟悉安卓应用开发常用技术。讲课注重理论与实践相结合，深入浅出。
     */

    private String courseID;
    private String courseName;
    private double score;
    private int studyNumber;
    private String courseDesc;
    private String providerImg;
    private String providerName;
    private String providerDesc;
    private String teacherImg;
    private String teacherName;
    private String teacherDesc;

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

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public String getProviderImg() {
        return providerImg;
    }

    public void setProviderImg(String providerImg) {
        this.providerImg = providerImg;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderDesc() {
        return providerDesc;
    }

    public void setProviderDesc(String providerDesc) {
        this.providerDesc = providerDesc;
    }

    public String getTeacherImg() {
        return teacherImg;
    }

    public void setTeacherImg(String teacherImg) {
        this.teacherImg = teacherImg;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherDesc() {
        return teacherDesc;
    }

    public void setTeacherDesc(String teacherDesc) {
        this.teacherDesc = teacherDesc;
    }
}
