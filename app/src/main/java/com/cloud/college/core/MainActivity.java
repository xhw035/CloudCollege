package com.cloud.college.core;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.cloud.college.R;
import com.cloud.college.uitl.MyApplication;
import com.cloud.college.widgets.MyViewPager;
import com.yinglan.alphatabs.AlphaTabsIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author: xiao(xhw219@163.com)
 * Date: 2017-02-21 23:22
 * DESC:
 */

public class MainActivity extends FragmentActivity {

    private Unbinder unbinder;
    @BindView(R.id.ViewPager) MyViewPager viewPager;
    @BindView(R.id.alphaIndicator) AlphaTabsIndicator alphaTabsIndicator;

    indexFragment homepageFragment ;
    StudyCenterFragment studyCenter;
    DefaultFragment defaultFragment2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        initView();
        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        homepageFragment = new indexFragment();
        studyCenter = new StudyCenterFragment();
        defaultFragment2 = new DefaultFragment();
        //DefaultFragment defaultFragment3 = new DefaultFragment();

        final ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(homepageFragment);
        fragmentList.add(studyCenter);
        fragmentList.add(defaultFragment2);
        //fragmentList.add(defaultFragment3);

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };

        viewPager.setAdapter(adapter);
        alphaTabsIndicator.setViewPager(viewPager);
    }

    private void initEvent() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==1)
                    viewPager.setPagingEnabled(false);
                else
                    viewPager.setPagingEnabled(true);

                if(position ==1&& MyApplication.refreshCollection){
                    studyCenter.init();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
