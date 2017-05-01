package com.cloud.college.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloud.college.R;
import com.hedgehog.ratingbar.RatingBar;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Author: xiao(xhw219@163.com)
 * Date: 2017-02-21 23:22
 * DESC:课程介绍对应的Fragment
 */

public class IntroductionFragment extends Fragment {

    private Unbinder mUnbinder;
    @BindView(R.id.courseName)     TextView courseName;
    @BindView(R.id.courseRatingBar) RatingBar courseRatingBar;
    @BindView(R.id.courseScore)     TextView courseScore;
    @BindView(R.id.watchingNumber)  TextView watchingNumber;

    @BindView(R.id.courseDesc) TextView courseDesc;
    @BindView(R.id.schoolImg) RoundedImageView schoolImg;
    @BindView(R.id.schoolName) TextView schoolName;
    @BindView(R.id.schoolDesc) TextView schoolDesc;
    @BindView(R.id.teacherImg) RoundedImageView teacherImg;
    @BindView(R.id.teacherName) TextView teacherName;

    @BindView(R.id.teacherDesc) TextView teacherDesc;
    private String  courseNameStr = "Java程序设计";
    private float  score = 3.7f;
    private String  watchingNumberStr = "116727人观看";

    private String  courseDescstr = "《Java程序设计》课程是使用Java语言进行应用程序设计的课程，针对各专业的大学本科生开设。课程的主要目标有三： 一、掌握Java语言的语法，能够较为深入理解Java语言机制，掌握Java语言面向对象的特点。 二、掌握JavaSE中基本的API，掌握在集合、线程、输入输出、图形用户界面、网络等方面的应用。三、能够编写有一定规模的应用程序，养成良好的编程习惯，会使用重构、设计模式、单元测试、日志、质量管理工具提高代码的质量。 对于学过“计算机基础、计算概论或C语言的学生”尤为适用。";
    private String  schoolNameStr ="北京大学";
    private String  schoolDescStr = "北京大学（Peking University）简称“北大”，诞生于1898年，初名京师大学堂，是中国近代第一所国立大学，也是第一个以“大学”之名创办的学校，其成立标志着中国近代高等教育的开端。北大是中国近代以来唯一以国家最高学府身份创立的学校";
    private String  teacherNameStr = " 唐大仕";
    private String  teacherDescStr = "唐大仕，博士，北京大学信息科学技术学院教师，在程序设计方面有多年的项目开发经验和教学经验，任教育部计算机教指委分委专家组成员。出版的教材包括《Java程序设计》（曾获第六届全国高校出版社优秀畅销书奖）《C#程序设计教程》《VB程序设计》《Visual C++.NET程序设计》等。在北京大学开设多门程序设计课程，课程内容以系统知识与实践应用相结合，注重培养对知识体系的深入理解，在与实际工作生活相结合的应用实践中分析问题、解决问题的能力。讲授过程以循序渐进为特色，善于启发。课堂风格轻快幽默。";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_introduction,container,false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        courseName.setText(courseNameStr);
        courseRatingBar.setStar(3.5f);
        courseRatingBar.setmClickable(false);
        courseScore.setText(score+"");
        watchingNumber.setText(watchingNumberStr);
        courseDesc.setText(courseDescstr);

        schoolImg.setImageResource(R.drawable.chart_img3);
        schoolName.setText(schoolNameStr);
        schoolDesc.setText(schoolDescStr);
        teacherImg.setImageResource(R.drawable.chart_img4);
        teacherName.setText(teacherNameStr);
        teacherDesc.setText(teacherDescStr);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}