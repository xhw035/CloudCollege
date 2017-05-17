package com.cloud.college.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xiao on 2017/5/9.
 *
 * retrofit服务类
 */

public interface MyService {

    /**************测试环境****************/
   /* *//**
     * 获取轮播图数据
     *//*
    @GET("/cloudCollege/bannerData.json")
    Call<BannerData> getBannerData();

    *//**
     * 获取最受欢迎的十门课
     *//*
    @GET("/cloudCollege/popularCourse.json")
    Call<PopularCourseData> getPopularCourse();

    *//**
     * 获取搜索结果
     *//*
    @GET("/cloudCollege/searchResult.json")
    Call<SearchResultData> getSearchResult();

    *//**
     * 获取课程目录
     *//*
    @GET("/cloudCollege/courseList.json")
    Call<CourseCatalogData> getCourseCatalog();

    *//**
     * 获取课程信息
     *//*
    @GET("/cloudCollege/courseInfo.json")
    Call<CourseInfoData> getCourseInfo();

    *//**
     * 获取评论信息
     *//*
    @GET("/cloudCollege/courseComm.json")
    Call<CommentData> getComment();

    *//**
     * 提交收藏数据
     *//*
    @GET("/cloudCollege/universalResponse.json")
    Call<universalResponseData> collect();

    *//**
     * 提交评论数据
     *//*
    @GET("/cloudCollege/universalResponse.json")
    Call<universalResponseData> addComment();

    *//**
     * 提交评论数据
     *//*
    @GET("/cloudCollege/collectionInfo.json")
    Call<CollectInfoData> getCollectInfo();*/



    /**************生产环境****************/

    /**
     * 获取轮播图数据
     */
    @GET("/edu/home/android/banner/data")
    Call<BannerData> getBannerData();

    /**
     * 获取最受欢迎的十门课
     */
    @GET("/edu/home/android/popularCourse/data")
    Call<PopularCourseData> getPopularCourse();

    /**
     * 获取搜索结果
     */
    @GET("/edu/home/android/SearchResult/data")
    Call<SearchResultData> getSearchResult(@Query("typeID") String typeID, @Query("keyWords") String keyWords);

    /**
     * 获取课程目录
     */
    @GET("/edu/home/android/courseList/data")
    Call<CourseCatalogData> getCourseCatalog(@Query("courseID") String courseID,@Query("userID") String userID);

    /**
     * 获取课程信息
     */
    @GET("/edu/home/android/courseInfo/data")
    Call<CourseInfoData> getCourseInfo(@Query("courseID") String courseID);

    /**
     * 获取评论信息
     */
    @GET("/edu/home/android/getComment/data")
    Call<CommentData> getComment(@Query("userID") String userID,@Query("courseID") String courseID);

    /**
     * 提交评论数据
     */
    @GET("/edu/home/android/addComment/data")
    Call<universalResponseData> addComment(@Query("userID") String userID, @Query("videoID") String videoID, @Query("content") String content);

    /**
     * 提交收藏数据
     */
    @GET("/edu/home/android/collectCourse/data")
    Call<universalResponseData> collect(@Query("userID") String userID,@Query("courseID") String courseID);

    /**
     * 获取收藏课程
     */
    @GET("/edu/home/android/getCollection/data")
    Call<CollectInfoData> getCollectInfo(@Query("userID") String userID);

    /**
     * 提交评分
     */
    @GET("/edu/home/android/score/add")
    Call<universalResponseData> submitScore(@Query("userID") String userID, @Query("videoID") String videoID, @Query("score") float score);



    /**
     * 注册、找回、修改密码
     */
    @GET("/edu/home/android/reg/data")
    Call<universalResponseData> handlePassword(@Query("phone") String phone,@Query("pwd") String password,@Query("type") int type);

    /**
     * 登录
     */
    @GET("/edu/home/android/login/data")
    Call<universalResponseData> handleLogin(@Query("phone") String phone,@Query("pwd") String password);


    /**
     * 获取用户昵称头像
     */
    @GET("/edu/home/android/getnickNameImg/data")
    Call<UserInfoData> getUserInfo(@Query("userID") String userID);


}
