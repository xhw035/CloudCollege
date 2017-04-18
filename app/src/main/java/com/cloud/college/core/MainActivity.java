package com.cloud.college.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cloud.college.R;
import com.yinglan.alphatabs.AlphaTabsIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: xiao(xhw219@163.com)
 * Date: 2017-02-21 23:22
 * DESC:
 */

public class MainActivity extends FragmentActivity {

    @BindView(R.id.ViewPager) ViewPager viewPager;
    @BindView(R.id.alphaIndicator) AlphaTabsIndicator alphaTabsIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initView() {
        indexFragment homepageFragment = new indexFragment();
        DefaultFragment defaultFragment1 = new DefaultFragment();
        DefaultFragment defaultFragment2 = new DefaultFragment();
        //DefaultFragment defaultFragment3 = new DefaultFragment();

        final ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(homepageFragment);
        fragmentList.add(defaultFragment1);
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

    }

    public void onTest(View view){
        Intent intent = new Intent(this,DetailActivity.class);
        startActivity(intent);
    }
}
