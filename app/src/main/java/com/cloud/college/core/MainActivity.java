package com.cloud.college.core;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.cloud.college.R;
import com.cloud.college.uitl.MyApplication;
import com.cloud.college.uitl.SpUitl;
import com.cloud.college.widgets.MyViewPager;
import com.yinglan.alphatabs.AlphaTabsIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

/**
 * Author: xiao(xhw219@163.com)
 * Date: 2017-02-21 23:22
 * DESC:
 */

public class MainActivity extends FragmentActivity {

    private Unbinder unbinder;
    @BindView(R.id.ViewPager) MyViewPager viewPager;
    @BindView(R.id.alphaIndicator) AlphaTabsIndicator alphaTabsIndicator;

    IndexFragment homepageFragment ;
    StudyCenterFragment studyCenterFragment;
    MineFragment mineFragment ;
    static boolean isCollectionEmpty = false;

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

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.checkUpdate(this,true);
    }

    private void initView() {
        homepageFragment = new IndexFragment();
        studyCenterFragment = new StudyCenterFragment();
        mineFragment = new MineFragment();
        //DefaultFragment defaultFragment3 = new DefaultFragment();

        final ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(homepageFragment);
        fragmentList.add(studyCenterFragment);
        fragmentList.add(mineFragment);
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
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        alphaTabsIndicator.setViewPager(viewPager);

        //未登录，课程中心，默认可以滑动
        if(SpUitl.isLogin(this))
            isCollectionEmpty = false;
        else
            isCollectionEmpty = true;
    }

    private void initEvent() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==1&&!isCollectionEmpty)
                    viewPager.setPagingEnabled(false);
                else
                    viewPager.setPagingEnabled(true);

                if(position ==1&& MyApplication.refreshCollection){
                    studyCenterFragment.init();
                }

                if(position ==2&& MyApplication.refreshMine){
                    mineFragment.init();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //-----------------两次back退出--------------------
    int back=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back++;
            switch (back) {
                case 1:
                    Toasty.info(this, "再按一次退出！").show();
                    break;
                case 2:
                    back = 0;
                    finish();// 关闭程序
                    android.os.Process.killProcess(android.os.Process.myPid());// 关闭进程
                    break;
            }
            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
