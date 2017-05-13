package com.cloud.college.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cloud.college.R;
import com.cloud.college.network.CourseInfoData;
import com.cloud.college.uitl.MyApplication;
import com.cloud.college.uitl.NetworkUtil;
import com.hedgehog.ratingbar.RatingBar;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Author: xiao(xhw219@163.com)
 * Date: 2017-02-21 23:22
 * DESC:课程介绍对应的Fragment
 */

public class IntroductionFragment extends Fragment {

    private Unbinder mUnbinder;
    Call<CourseInfoData> infoCall;
    @BindView(R.id.courseName)     TextView courseName;
    @BindView(R.id.courseRatingBar) RatingBar courseRatingBar;
    @BindView(R.id.courseScore)     TextView courseScore;
    @BindView(R.id.studyNumber)  TextView studyNumber;
    @BindView(R.id.courseDesc) TextView courseDesc;
    
    @BindView(R.id.providerImg) RoundedImageView providerImg;
    @BindView(R.id.providerName) TextView providerName;
    @BindView(R.id.providerDesc) TextView providerDesc;
    
    @BindView(R.id.teacherImg) RoundedImageView teacherImg;
    @BindView(R.id.teacherName) TextView teacherName;
    @BindView(R.id.teacherDesc) TextView teacherDesc;

    @BindView(R.id.introContent) ScrollView introContent;
    @BindView(R.id.introException) LinearLayout introException;
    @BindView(R.id.introlLoading) LinearLayout introlLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_introduction,container,false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        infoCall.cancel();
    }

    public void initData(){
        introContent.setVisibility(View.GONE);
        introlLoading.setVisibility(View.VISIBLE);
        introException.setVisibility(View.GONE);
        
//        infoCall = MyApplication.getMyService().getCourseInfo();
        infoCall = MyApplication.getMyService().getCourseInfo(((DetailActivity)getActivity()).courseID);
        if(!NetworkUtil.isNetworkAvailable(getActivity())){
            introContent.setVisibility(View.GONE);
            introlLoading.setVisibility(View.GONE);
            introException.setVisibility(View.VISIBLE);
            Toasty.error(getActivity(),"网络异常，无法连接服务器！").show();
            return ;
        }

        infoCall.enqueue(new Callback<CourseInfoData>() {
            @Override
            public void onResponse(Call<CourseInfoData> call, Response<CourseInfoData> response) {
                if(response.body()==null){
                    introContent.setVisibility(View.GONE);
                    introlLoading.setVisibility(View.GONE);
                    introException.setVisibility(View.VISIBLE);
                    Toasty.error(getActivity(),"服务器异常，加载数据出错！").show();
                    return;
                }
                initView(response);

            }

            @Override
            public void onFailure(Call<CourseInfoData> call, Throwable t) {
                introContent.setVisibility(View.GONE);
                introlLoading.setVisibility(View.GONE);
                introException.setVisibility(View.VISIBLE);
                Toasty.error(getActivity(),"加载数据失败，请稍候重试！").show();
                return;
            }
        });

    }

    private void initView(Response<CourseInfoData> response) {
        CourseInfoData infoData = response.body();
        courseName.setText(infoData.getCourseName());
        courseRatingBar.setStar((float)infoData.getScore());
        courseRatingBar.setmClickable(false);
        courseScore.setText(infoData.getScore()+"");
        studyNumber.setText(infoData.getStudyNumber()+"人学过");
        courseDesc.setText(infoData.getCourseDesc());
        providerName.setText(infoData.getProviderName());
        providerDesc.setText(infoData.getProviderDesc());
        teacherName.setText(infoData.getTeacherName());
        teacherDesc.setText(infoData.getTeacherDesc());
        Glide.with(getActivity()).load(infoData.getProviderImg()).fitCenter().into(providerImg);
        Glide.with(getActivity()).load(infoData.getTeacherImg()).fitCenter().into(teacherImg);

        introContent.setVisibility(View.VISIBLE);
        introlLoading.setVisibility(View.GONE);
        introException.setVisibility(View.GONE);
    }

    @OnClick(R.id.introRefresh)
    public void refresh(View view){
        initData();
    }

}