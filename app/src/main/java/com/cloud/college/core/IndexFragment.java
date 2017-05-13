package com.cloud.college.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.ToxicBakery.viewpager.transforms.BackgroundToForegroundTransformer;
import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.ToxicBakery.viewpager.transforms.FlipHorizontalTransformer;
import com.ToxicBakery.viewpager.transforms.FlipVerticalTransformer;
import com.ToxicBakery.viewpager.transforms.ForegroundToBackgroundTransformer;
import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.ToxicBakery.viewpager.transforms.StackTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomInTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomOutTranformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.cloud.college.R;
import com.cloud.college.network.BannerData;
import com.cloud.college.network.MyService;
import com.cloud.college.network.PopularCourseData;
import com.cloud.college.uitl.MyApplication;
import com.cloud.college.uitl.NetworkImageHolderView;
import com.cloud.college.uitl.SpUitl;
import com.cloud.college.uitl.NetworkUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.carbs.android.avatarimageview.library.AvatarImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author: xiao(xhw219@163.com)
 * Date: 2017-02-21 23:22
 * DESC:首页对应的Fragment
 */

public class IndexFragment extends Fragment implements ViewPager.OnPageChangeListener, OnItemClickListener  {

    @BindView(R.id.convenientBanner) ConvenientBanner convenientBanner;

    @BindView(R.id.typeView1) LinearLayout typeView1;
    @BindView(R.id.typeView2) LinearLayout typeView2;
    @BindView(R.id.typeView3) LinearLayout typeView3;
    @BindView(R.id.typeView4) LinearLayout typeView4;
    @BindView(R.id.typeView5) LinearLayout typeView5;
    @BindView(R.id.typeView6) LinearLayout typeView6;
    @BindView(R.id.typeView7) LinearLayout typeView7;
    @BindView(R.id.typeView8) LinearLayout typeView8;

    @BindView(R.id.avatar1) AvatarImageView avatar1;
    @BindView(R.id.avatar2) AvatarImageView avatar2;
    @BindView(R.id.avatar3) AvatarImageView avatar3;
    @BindView(R.id.avatar4) AvatarImageView avatar4;
    @BindView(R.id.avatar5) AvatarImageView avatar5;
    @BindView(R.id.avatar6) AvatarImageView avatar6;
    @BindView(R.id.avatar7) AvatarImageView avatar7;
    @BindView(R.id.avatar8) AvatarImageView avatar8;

    @BindView(R.id.chartImg1) ImageView chartImg1;
    @BindView(R.id.chartImg2) ImageView chartImg2;
    @BindView(R.id.chartImg3) ImageView chartImg3;
    @BindView(R.id.chartImg4) ImageView chartImg4;
    @BindView(R.id.chartImg5) ImageView chartImg5;
    @BindView(R.id.chartImg6) ImageView chartImg6;
    @BindView(R.id.chartImg7) ImageView chartImg7;
    @BindView(R.id.chartImg8) ImageView chartImg8;
    @BindView(R.id.chartImg9) ImageView chartImg9;
    @BindView(R.id.chartImg10) ImageView chartImg10;

    @BindView(R.id.chartTitle1) TextView chartTitle1;
    @BindView(R.id.chartTitle2) TextView chartTitle2;
    @BindView(R.id.chartTitle3) TextView chartTitle3;
    @BindView(R.id.chartTitle4) TextView chartTitle4;
    @BindView(R.id.chartTitle5) TextView chartTitle5;
    @BindView(R.id.chartTitle6) TextView chartTitle6;
    @BindView(R.id.chartTitle7) TextView chartTitle7;
    @BindView(R.id.chartTitle8) TextView chartTitle8;
    @BindView(R.id.chartTitle9) TextView chartTitle9;
    @BindView(R.id.chartTitle10) TextView chartTitle10;

    private Unbinder mUnbinder;
    ArrayList<String> transformerList;
    private MyService service;
    private Call<BannerData> bannerCall;
    List<BannerData.DataBean> bannerList;
    private Call<PopularCourseData> popularCourseCall;
    List<PopularCourseData.DataBean> popularCourseList;
    private boolean bannerLoadState = false;
    private boolean courseLoadState = false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index,container,false);
        mUnbinder = ButterKnife.bind(this, view);
        service = MyApplication.getMyService();
        return view;
    }
;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initEvent();
    }

    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        bannerCall.cancel();
        popularCourseCall.cancel();
    }

    public void initView() {
        initBanner();
        initCourseType();
        initPopularCourse();
    }

    private void initBanner() {
        //添加各种翻页效果
        transformerList = new ArrayList<String>();
        transformerList.add(DefaultTransformer.class.getSimpleName());
        transformerList.add(AccordionTransformer.class.getSimpleName());
        transformerList.add(BackgroundToForegroundTransformer.class.getSimpleName());
        transformerList.add(CubeInTransformer.class.getSimpleName());
        transformerList.add(CubeOutTransformer.class.getSimpleName());
        transformerList.add(DepthPageTransformer.class.getSimpleName());
        transformerList.add(FlipHorizontalTransformer.class.getSimpleName());
        transformerList.add(FlipVerticalTransformer.class.getSimpleName());
        transformerList.add(ForegroundToBackgroundTransformer.class.getSimpleName());
        transformerList.add(RotateDownTransformer.class.getSimpleName());
        transformerList.add(RotateUpTransformer.class.getSimpleName());
        transformerList.add(StackTransformer.class.getSimpleName());
        transformerList.add(ZoomInTransformer.class.getSimpleName());
        transformerList.add(ZoomOutTranformer.class.getSimpleName());

        bannerCall = service.getBannerData();
        if(!NetworkUtil.isNetworkConnected(getActivity())){
            configBannerCache();
            bannerLoadState = false;
            Toasty.error(getActivity(),"网络异常，无法连接服务器！").show();
            return ;
        }

        //异步进行网络请求，获取轮播图数据
        bannerCall.enqueue(new Callback<BannerData>() {
            @Override
            public void onResponse(Call<BannerData> call, Response<BannerData> response) {
                if(response.body()==null){
                    configBannerCache();
                    bannerLoadState = false;
                    Toasty.error(getActivity(),"加载数据出错，请稍候重试！").show();
                    return;
                }
                bannerList = response.body().getData();
                List<String> cacheList = new ArrayList<String>();
                for (BannerData.DataBean dataBean : bannerList) {
                    cacheList.add(dataBean.getBannerURL());
                }
                //将数据缓存到sp文件
                SpUitl.addBannerDataCache(getActivity(),cacheList);

                //将数据绑定到banner，并进行配置
                convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                },cacheList)
                //设置指示器的方向
                .setPageIndicator(new int[]{R.drawable.img_page_indicator,R.drawable.img_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                //循环播放
                .startTurning(5000);
                bannerLoadState = true;


            }

            @Override
            public void onFailure(Call<BannerData> call, Throwable t) {
                configBannerCache();
                bannerLoadState = false;
                Toasty.error(getActivity(),"加载数据失败，请稍候重试！").show();
            }

        });


    }

    //获取数据失败展示banner缓存数据
    private void configBannerCache() {
        List<String> cacheList = SpUitl.getBannerDataCache(getActivity());
        if (cacheList == null) {
            return;
        }
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, cacheList)
        //设置指示器的方向
        .setPageIndicator(new int[]{R.drawable.img_page_indicator,R.drawable.img_page_indicator_focused})
        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
        //循环播放
        .startTurning(5000);
    }

    private void initCourseType() {
        avatar1.setTextAndColor("IT", 0xFFFED65C);
        avatar2.setTextAndColor("工", 0xFFFA7556);
        avatar3.setTextAndColor("经", 0xFFA2D669);
        avatar4.setTextAndColor("人", 0xFF62B2FA);

        avatar5.setTextAndColor("哲", 0xFF49D1AD);
        avatar6.setTextAndColor("思", 0xFFC5A9F6);
        avatar7.setTextAndColor("理", 0xFF67E0F3);
        avatar8.setTextAndColor("其", 0xFF9CABC2);
    }

    private void initPopularCourse() {
        popularCourseCall = service.getPopularCourse();
        if(!NetworkUtil.isNetworkConnected(getActivity())){
            configPopularCourseCache();
            courseLoadState = false;
            Toasty.error(getActivity(),"网络异常，无法连接服务器！").show();
            return ;
        }

        popularCourseCall.enqueue(new Callback<PopularCourseData>() {
            @Override
            public void onResponse(Call<PopularCourseData> call, Response<PopularCourseData> response) {
                if(response.body()==null){
                    configPopularCourseCache();
                    courseLoadState = false;
                    Toasty.error(getActivity(),"加载数出错，请稍候重试！").show();
                    return;
                }
                popularCourseList = response.body().getData();
                List<String> cacheList = new ArrayList<String>();
                for (PopularCourseData.DataBean bean : popularCourseList) {
                    cacheList.add(bean.getCourseName()+"#"+bean.getCourseImg());
                }
                SpUitl.addPopularCourseCache(getActivity(),cacheList);

                //绑定数据
                chartTitle1.setText(popularCourseList.get(0).getCourseName());
                chartTitle2.setText(popularCourseList.get(1).getCourseName());
                chartTitle3.setText(popularCourseList.get(2).getCourseName());
                chartTitle4.setText(popularCourseList.get(3).getCourseName());
                chartTitle5.setText(popularCourseList.get(4).getCourseName());
                chartTitle6.setText(popularCourseList.get(5).getCourseName());
                chartTitle7.setText(popularCourseList.get(6).getCourseName());
                chartTitle8.setText(popularCourseList.get(7).getCourseName());
                chartTitle9.setText(popularCourseList.get(8).getCourseName());
                chartTitle10.setText(popularCourseList.get(9).getCourseName());

                ImageLoader.getInstance().displayImage(popularCourseList.get(0).getCourseImg(),chartImg1);
                ImageLoader.getInstance().displayImage(popularCourseList.get(1).getCourseImg(),chartImg2);
                ImageLoader.getInstance().displayImage(popularCourseList.get(2).getCourseImg(),chartImg3);
                ImageLoader.getInstance().displayImage(popularCourseList.get(3).getCourseImg(),chartImg4);
                ImageLoader.getInstance().displayImage(popularCourseList.get(4).getCourseImg(),chartImg5);
                ImageLoader.getInstance().displayImage(popularCourseList.get(5).getCourseImg(),chartImg6);
                ImageLoader.getInstance().displayImage(popularCourseList.get(6).getCourseImg(),chartImg7);
                ImageLoader.getInstance().displayImage(popularCourseList.get(7).getCourseImg(),chartImg8);
                ImageLoader.getInstance().displayImage(popularCourseList.get(8).getCourseImg(),chartImg9);
                ImageLoader.getInstance().displayImage(popularCourseList.get(9).getCourseImg(),chartImg10);
                courseLoadState = true;

            }

            @Override
            public void onFailure(Call<PopularCourseData> call, Throwable t) {
                configPopularCourseCache();
                courseLoadState = false;
                Toasty.error(getActivity(),"加载数据失败，请稍候重试！").show();
            }
        });

    }

    //获取数据失败展示最受欢迎课程的缓存数据
    private void configPopularCourseCache() {
        List<String> cacheList = SpUitl.getPopularCourseCache(getActivity());
        if(cacheList==null||cacheList.size()!=10)
            return;
        chartTitle1.setText(cacheList.get(0).split("#")[0]);
        chartTitle2.setText(cacheList.get(1).split("#")[0]);
        chartTitle3.setText(cacheList.get(2).split("#")[0]);
        chartTitle4.setText(cacheList.get(3).split("#")[0]);
        chartTitle5.setText(cacheList.get(4).split("#")[0]);
        chartTitle6.setText(cacheList.get(5).split("#")[0]);
        chartTitle7.setText(cacheList.get(6).split("#")[0]);
        chartTitle8.setText(cacheList.get(7).split("#")[0]);
        chartTitle9.setText(cacheList.get(8).split("#")[0]);
        chartTitle10.setText(cacheList.get(9).split("#")[0]);

        ImageLoader.getInstance().displayImage(cacheList.get(0).split("#")[1],chartImg1);
        ImageLoader.getInstance().displayImage(cacheList.get(1).split("#")[1],chartImg2);
        ImageLoader.getInstance().displayImage(cacheList.get(2).split("#")[1],chartImg3);
        ImageLoader.getInstance().displayImage(cacheList.get(3).split("#")[1],chartImg4);
        ImageLoader.getInstance().displayImage(cacheList.get(4).split("#")[1],chartImg5);
        ImageLoader.getInstance().displayImage(cacheList.get(5).split("#")[1],chartImg6);
        ImageLoader.getInstance().displayImage(cacheList.get(6).split("#")[1],chartImg7);
        ImageLoader.getInstance().displayImage(cacheList.get(7).split("#")[1],chartImg8);
        ImageLoader.getInstance().displayImage(cacheList.get(8).split("#")[1],chartImg9);
        ImageLoader.getInstance().displayImage(cacheList.get(9).split("#")[1],chartImg10);
    }

    private void initEvent() {
        convenientBanner.setOnPageChangeListener(this).setOnItemClickListener(this);
    }

    /**
     * 处理顶部banner监听事件
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageSelected(int position) {
        //convenientBanner.setCanLoop(false);
        String transforemerName = transformerList.get((int)(Math.random()*14));
        System.out.println(transforemerName);
        try {
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + transforemerName);
            ABaseTransformer transforemer= (ABaseTransformer)cls.newInstance();
            convenientBanner.getViewPager().setPageTransformer(true,transforemer);

            //部分3D特效需要调整滑动速度
            if(transforemerName.equals("StackTransformer")){
                convenientBanner.setScrollDuration(1200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //convenientBanner.setCanLoop(!convenientBanner.isCanLoop());
    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /**
     *轮播图点击监听
     */
    @Override
    public void onItemClick(int position) {
        if(!NetworkUtil.isNetworkConnected(getActivity())){
            Toasty.error(getActivity(),"网络异常，无法连接服务器！").show();
            return ;
        }
        if(!bannerLoadState){
            Toasty.error(getActivity(),"网络异常，未加载到数据!").show();
            return ;
        }
        Intent intent = new Intent(getActivity(),DetailActivity.class);
        intent.putExtra("courseID",bannerList.get(position).getCourseID());
        startActivity(intent);
    }

    /**
     * 处理搜索框监听事件
     */
    @OnClick(R.id.serach)
    public void handleKeywordSerach(View view){
        if(!NetworkUtil.isNetworkAvailable(getActivity())){
            Toasty.error(getActivity(),"网络异常，无法连接服务器！").show();
            return ;
        }
        Intent intent = new Intent(getActivity(), SearchPageActivity.class);
        intent.putExtra("flag",0);
        startActivity(intent);
    }

    /**
     * 类别搜索监听
     */
    @OnClick({R.id.typeView1,R.id.typeView2,R.id.typeView3,R.id.typeView4,
              R.id.typeView5,R.id.typeView6,R.id.typeView7,R.id.typeView8})
    public void handleTypeSearch(View v) {
        if(!NetworkUtil.isNetworkAvailable(getActivity())){
            Toasty.error(getActivity(),"网络异常，无法连接服务器！").show();
            return ;
        }
        Intent intent = new Intent(getActivity(), SearchPageActivity.class);
        String[] arr = ((String) v.getTag()).split("#");
        intent.putExtra("flag",1);
        intent.putExtra("typeID",arr[0]);
        intent.putExtra("typeName",arr[1]);
        startActivity(intent);
    }

    /**
     * 模拟点击历史最受欢迎课程
     */
    @OnClick({R.id.chartCard1,R.id.chartCard2,R.id.chartCard3,R.id.chartCard4,R.id.chartCard5,
            R.id.chartCard6,R.id.chartCard7,R.id.chartCard8,R.id.chartCard9,R.id.chartCard10})
    public void handleChartCard(View v) {
        if(!NetworkUtil.isNetworkConnected(getActivity())){
            Toasty.error(getActivity(),"网络异常，无法连接服务器！").show();
            return ;
        }
        if(!courseLoadState){
            Toasty.error(getActivity(),"网络异常，未加到数据!").show();
            return ;
        }
        Intent intent = new Intent(getActivity(),DetailActivity.class);
        intent.putExtra("courseID",popularCourseList.get(Integer.parseInt((String)v.getTag())).getCourseID());
        startActivity(intent);
    }


}