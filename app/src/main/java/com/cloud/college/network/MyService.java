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
    @GET("/home/android/banner/data")
    Call<BannerData> getBannerData();

    /**
     * 获取最受欢迎的十门课
     */
    @GET("/home/android/popularCourse/data")
    Call<PopularCourseData> getPopularCourse();

    /**
     * 获取搜索结果
     */
    @GET("/home/android/SearchResult/data")
    Call<SearchResultData> getSearchResult(@Query("typeID") String typeID, @Query("keyWords") String keyWords);

    /**
     * 获取课程目录
     */
    @GET("/home/android/courseList/data")
    Call<CourseCatalogData> getCourseCatalog(@Query("courseID") String courseID,@Query("userID") String userID);

    /**
     * 获取课程信息
     */
    @GET("/home/android/courseInfo/data")
    Call<CourseInfoData> getCourseInfo(@Query("courseID") String courseID);

    /**
     * 获取评论信息
     */
    @GET("/home/android/getComment/data")
    Call<CommentData> getComment(@Query("userID") String userID,@Query("courseID") String courseID);

    /**
     * 提交收藏数据
     */
    @GET("/home/android/collectCourse/data")
    Call<universalResponseData> collect(@Query("userID") String userID,@Query("courseID") String courseID);

    /**
     * 提交评论数据
     */
    @GET("/home/android/addComment/data")
    Call<universalResponseData> addComment(@Query("userID") String userID, @Query("videoID") String videoID, @Query("content") String content);

    /**
     * 提交评论数据
     */
    @GET("/home/android/getCollection/data")
    Call<CollectInfoData> getCollectInfo(@Query("userID") String userID);


}
