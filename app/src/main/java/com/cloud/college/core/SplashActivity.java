package com.cloud.college.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.cloud.college.R;
import com.cloud.college.uitl.DimensionUtil;
import com.cloud.college.uitl.SpUitl;
import com.cloud.college.uitl.UpdateManager;

import java.util.ArrayList;
import java.util.List;


public class SplashActivity extends Activity implements View.OnClickListener{
	private ViewPager mViewPager;
    private Context mContext;
    private LayoutInflater mInflater;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);
        mContext = SplashActivity.this;
        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        mInflater = LayoutInflater.from(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        //第一登录显示引导页
        if (SpUitl.isFirstTime(mContext)
                ||UpdateManager.getVersionCode(mContext)>SpUitl.getLastVersionCode(mContext)) {

            ViewPager.LayoutParams params = new ViewPager.LayoutParams();
            params.height = ViewPager.LayoutParams.MATCH_PARENT;
            params.width = ViewPager.LayoutParams.MATCH_PARENT;

            View view1 = new View(mContext);
            view1.setBackgroundResource(R.drawable.guide_page1);
            view1.setLayoutParams(params);
            View view2 = new View(mContext);
            view2.setBackgroundResource(R.drawable.guide_page2);
            view2.setLayoutParams(params);
            View view3 = new View(mContext);
            view3.setBackgroundResource(R.drawable.guide_page3);
            view3.setLayoutParams(params);

            Button button = new Button(mContext);
            button.setPadding(DimensionUtil.dp2px(mContext,60),0,DimensionUtil.dp2px(mContext,60),0);
            button.setBackgroundResource(R.drawable.guide_button_shape);
            button.setTextColor(getResources().getColor(R.color.white));
            button.setText("立即体验");
            button.setOnClickListener(this);

            RelativeLayout.LayoutParams btnParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            btnParams.bottomMargin = DimensionUtil.dp2px(mContext,70);
            btnParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            btnParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

            RelativeLayout view4 = new RelativeLayout(mContext);
            view4.setBackgroundResource(R.drawable.guide_page4);
            view4.setLayoutParams(params);
            view4.addView(button,btnParams);


    		final ArrayList<View> views = new ArrayList<View>();
    		views.add(view1);
    		views.add(view2);
    		views.add(view3);
    		views.add(view4);

    		MyPagerAdapter mPagerAdapter = new MyPagerAdapter(views);
    		mViewPager.setAdapter(mPagerAdapter);
            SpUitl.setLastVersionCode(mContext, UpdateManager.getVersionCode(mContext));

		}
		//以后都显示闪屏
        else {
            ViewPager.LayoutParams params = new ViewPager.LayoutParams();
            params.height = ViewPager.LayoutParams.MATCH_PARENT;
            params.width = ViewPager.LayoutParams.MATCH_PARENT;

            View view = new View(mContext);
            view.setBackgroundResource(R.drawable.splash_bg);
            view.setLayoutParams(params);
            final ArrayList<View> views = new ArrayList<View>();
            views.add(view);

            MyPagerAdapter mPagerAdapter = new MyPagerAdapter(views);
            mViewPager.setAdapter(mPagerAdapter);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }
    }

    //点击欢迎页最后一页的按钮
    @Override
    public void onClick(View v) {
		Intent intent = new Intent();
		intent.setClass(this,MainActivity.class);
		startActivity(intent);
        SpUitl.setFirstTime(false);
		this.finish();
    }


}

//填充ViewPager的数据适配器内部类
class MyPagerAdapter extends PagerAdapter
{
    List<View> views;

    public MyPagerAdapter(List<View> views){
        this.views=views;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
    @Override
    public int getCount() {
        return views.size();
    }
    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager)container).removeView(views.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager)container).addView(views.get(position));
        return views.get(position);
    }
}


