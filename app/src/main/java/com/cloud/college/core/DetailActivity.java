package com.cloud.college.core;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cloud.college.R;
import com.cloud.college.network.CourseCatalogData;
import com.cloud.college.network.universalResponseData;
import com.cloud.college.uitl.MyApplication;
import com.cloud.college.uitl.SpUitl;
import com.cloud.college.uitl.networkUtil;
import com.cloud.college.widgets.MarqueeTextView;
import com.cloud.college.widgets.MyViewPager;
import com.cloud.college.widgets.ScaleTransitionPagerTitleView;
import com.dl7.player.media.IjkPlayerView;
import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.sdsmdg.tastytoast.TastyToast;
import com.xiao.magictimeline.CatalogFragment;
import com.xiao.magictimeline.CatalogModel;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.xiao.magictimeline.CatalogAdapter.OnItemClickListener;

/**
 * Created by xiao on 2017/4/9.
 * 课程详情页
 */

public class DetailActivity extends AppCompatActivity {

    private Unbinder unbinder;
    @BindView(R.id.detailToolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_title) MarqueeTextView toolbarTitle;
    @BindView(R.id.playerView) IjkPlayerView playerView;
    @BindView(R.id.magic_indicator) MagicIndicator magicIndicator ;
    @BindView(R.id.detailViewpager) MyViewPager mViewPager;
    @BindView(R.id.expandableBtn) AllAngleExpandableButton plusButton;
    @BindView(R.id.detailContent) LinearLayout detailContent;
    @BindView(R.id.detailLoading) LinearLayout detailLoading;
    @BindView(R.id.detailException) LinearLayout detailException;
    @BindView(R.id.detailExceptionTip) TextView detailExceptionTip;

    private final DetailActivity mContext = DetailActivity.this;
    private Call<CourseCatalogData> catalogCall;
    private Call<universalResponseData> collectCall;
    private CatalogFragment catalogFragment;
    private IntroductionFragment introductionFragment;
    private CommentFragment commentFragment;

    public String courseID="";
    //当前播放视频的ID
    public String videoID="";
    public String courseName="";
    public String thumbnail="";
    public boolean isCollected;
    public ArrayList<CourseCatalogData.DataBean> catalog;
    public boolean isPlaying;
    private KProgressHUD kProgressHUD;

    private List<CatalogModel> catalogList = new ArrayList<CatalogModel>();
    //目录的视频标题和左滑下载按钮监听
    OnItemClickListener listener=  new OnItemClickListener(){
        @Override
        public void onTitleClick(int position) {
            for (int i = 0; i < catalogList.size(); i++) {
                catalogList.get(i).setFontColor(Color.BLACK);
                if(i==position)
                    catalogList.get(i).setFontColor(Color.parseColor("#2BC17A"));
            }
            catalogFragment.adpter.notifyDataSetChanged();

            playerView.stop()
            .setTitle(catalogList.get(position).getChildName())
            .setVideoSource(null, catalogList.get(position).getURL_M(), catalogList.get(position).getURL_H(), null, null)
            .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH)
            .start();
            videoID = catalogList.get(position).getVideoID();
        }

        @Override
        public void onDownloadClick(View view, int position) {
            Toasty.success(getApplicationContext(),"download").show();
            ((ImageView)view).setBackground(getResources().getDrawable(R.drawable.bg_download_gray));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        unbinder = ButterKnife.bind(this);
        courseID = getIntent().getStringExtra("courseID");
        initView();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        playerView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerView.onDestroy();
        unbinder.unbind();
        catalogCall.cancel();
        //collectCall.cancel();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        playerView.configurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (playerView.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if(plusButton.isExpanded()){
            plusButton.collapse();
            return;
        }
        if (playerView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }


    private void initView() {
        toolbar.inflateMenu(R.menu.detail_toolbar_menu);
        toolbar.findViewById(R.id.action_item2).setVisibility(View.GONE);

        detailLoading.setVisibility(View.VISIBLE);
        detailException.setVisibility(View.GONE);
        detailContent.setVisibility(View.GONE);
        initVedioPlayer();
        initMagicIndicator();
        initViewPager();
        initPlusButton();
    }

    private void initVedioPlayer() {

        playerView.init()
        .setToolbar(toolbar)
        .setTitle("")
        .setMscreenShotDir("CloudCollege/screenshot")
        .setVideoSource(null, "", "", null, null)
        .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH);
    }

    private void initMagicIndicator() {
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        final String[] pagerTitle = new String[]{"目录", "介绍", "评论"};
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return pagerTitle.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(pagerTitle[index]);
                simplePagerTitleView.setTextSize(20);
                simplePagerTitleView.setNormalColor(Color.BLACK);
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.main));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                BezierPagerIndicator indicator = new BezierPagerIndicator(context);
                indicator.setColors(getResources().getColor(R.color.main));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
    }

    private void initViewPager() {
        //==============初始化时间轴目录数据===================
//        catalogCall = MyApplication.getMyService().getCourseCatalog();
        catalogCall = MyApplication.getMyService().getCourseCatalog(courseID,SpUitl.getUserID(this));
        if(!networkUtil.isNetworkAvailable(this)){
            detailLoading.setVisibility(View.GONE);
            detailContent.setVisibility(View.GONE);
            detailExceptionTip.setText("网络异常，加载视频失败...");
            detailException.setVisibility(View.VISIBLE);
            Toasty.error(this,"网络异常，无法连接服务器！").show();
            return ;
        }

        catalogCall.enqueue(new Callback<CourseCatalogData>() {
            @Override
            public void onResponse(Call<CourseCatalogData> call, Response<CourseCatalogData> response) {
                if(response.body()==null||response.body().getData()==null||response.body().getData().size()==0){
                    detailLoading.setVisibility(View.GONE);
                    detailContent.setVisibility(View.GONE);
                    detailExceptionTip.setText("加载视频出错...");
                    detailException.setVisibility(View.VISIBLE);
                    Toasty.error(mContext,"服务器异常，加载视频出错！").show();
                    return;
                }

                CourseCatalogData courseData = response.body();
                courseID = courseData.getCourseID();
                courseName = courseData.getCourseName();
                thumbnail = courseData.getThumbnail();
                isCollected = courseData.getIsCollected();
                catalog = courseData.getData();

                if (catalog == null || catalog.size() == 0) {
                    boolean a = catalog == null;
                    detailLoading.setVisibility(View.GONE);
                    detailContent.setVisibility(View.GONE);
                    detailExceptionTip.setText("加载视频出错~~~");
                    detailException.setVisibility(View.VISIBLE);
                    Toasty.error(mContext,"服务器异常，加载视频出错~").show();
                    return;
                }

                for (CourseCatalogData.DataBean bean : catalog) {
                    CatalogModel chapterModel = new CatalogModel();
                    chapterModel.setType(CatalogModel.TYPE_GROUP);
                    chapterModel.setGruopName( bean.getChapter().getName());
                    catalogList.add(chapterModel);
                    for (CourseCatalogData.DataBean.VideoBean videoBean : bean.getVideo()) {
                        CatalogModel videoModel = new CatalogModel();
                        videoModel.setType(CatalogModel.TYPE_CHILD);
                        videoModel.setVideoID(videoBean.getVideoID());
                        videoModel.setChildName(videoBean.getName());
                        videoModel.setVideoTime(videoBean.getDuration());
                        videoModel.setVideoSize(videoBean.getSize());
                        videoModel.setURL_M(videoBean.getUrl_m());
                        videoModel.setURL_H(videoBean.getUrl_h());
                        catalogList.add(videoModel);
                    }
                }

                //默认第一个着色选中
                catalogList.get(1).setFontColor(Color.parseColor("#2BC17A"));
                catalogFragment = new CatalogFragment(catalogList,listener);
                introductionFragment = new IntroductionFragment(); //简介Fragment
                commentFragment = new CommentFragment(); //评论Fragment

                final ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
                fragmentList.add(catalogFragment);
                fragmentList.add(introductionFragment);
                fragmentList.add(commentFragment);

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

                mViewPager.setPagingEnabled(false);
                mViewPager.setAdapter(adapter);
                setFirstTimePlayerData();

            }

            @Override
            public void onFailure(Call<CourseCatalogData> call, Throwable t) {
                detailLoading.setVisibility(View.GONE);
                detailContent.setVisibility(View.GONE);
                detailExceptionTip.setText("加载视频失败...");
                detailException.setVisibility(View.VISIBLE);
                Toasty.error(mContext,"加载视频失败，请稍候重试！").show();
                return ;
            }
        });

    }

    /**
    底部数据都加载成功时，设置详情页面，并将播放器视频设置为第一个视频
     */
    private void setFirstTimePlayerData() {
        ViewPagerHelper.bind(magicIndicator, mViewPager);
        toolbarTitle.setText(courseName+"                          ");
        //toolbar.findViewById(R.id.action_item2).setVisibility(View.VISIBLE);

        Glide.with(this).load(thumbnail).fitCenter().into(playerView.mPlayerThumb);
        playerView.stop().setTitle(catalogList.get(1).getChildName())
        .setVideoSource(null, catalogList.get(1).getURL_M(), catalogList.get(1).getURL_H(), null, null)
        .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH);
        videoID = catalogList.get(1).getVideoID();
        detailLoading.setVisibility(View.GONE);
        detailException.setVisibility(View.GONE);
        detailContent.setVisibility(View.VISIBLE);
        if(!SpUitl.isAutoPlay(this)) {
            playerView.stop();
        }
    }

    private void initPlusButton() {
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.img_plus, R.drawable.img_download, R.drawable.img_comment, R.drawable.img_heart};
        int[] color = {R.color.main, R.color.transparent, R.color.transparent, R.color.red};
        for (int i = 0; i < 4; i++) {
            ButtonData buttonData;
            if (i == 0) {
                buttonData = ButtonData.buildIconButton(this, drawable[i], 15);
            } else {
                buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
            }
            buttonData.setBackgroundColorId(this, color[i]);
            buttonDatas.add(buttonData);
        }

        plusButton.setButtonDatas(buttonDatas);
    }

    private void initEvent() {
        //---------------------------toolbar-------------------------------
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
               /* if (menuItemId == R.id.action_item1) {
                    TastyToast.makeText(DetailActivity.this, "收藏成功", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                }*/
                if (menuItemId == R.id.action_item2) {
                    TastyToast.makeText(mContext, "分享成功", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                }
                return true;
            }
        });

        //---------------------------视频下方的ViewPager-------------------------------
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if(position==0)
                    mViewPager.setPagingEnabled(false);
                else
                    mViewPager.setPagingEnabled(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        //---------------------------加号按钮-------------------------------
        plusButton.setButtonEventListener(new ButtonEventListener() {

            @Override
            public void onButtonClicked(int index) {
                kProgressHUD = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                //.setLabel("正在提交...")
                //.setWindowColor(Color.parseColor("#992BC17A"))
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

                if(!networkUtil.isNetworkAvailable(mContext)){
                    kProgressHUD.dismiss();
                    Toasty.error(mContext,"网络异常，无法连接服务器！").show();
                    return ;
                }

                if(!SpUitl.isLogin(mContext)){
                    Toasty.info(mContext,"您还没有登录呢，请先登录").show();
                    //=====================这里进行调起登录页的操作==================
                    return;
                }

                if(index == 1) {
                    //下载
                    kProgressHUD.dismiss();
                    Toasty.info(mContext,"本版本暂不支持该功能").show();
                }
                if(index == 2) {
                    //评论
                    kProgressHUD.dismiss();
                    Intent intent = new Intent(mContext,SubmitCommActivity.class);
                    intent.putExtra("videoID",videoID);
                    startActivityForResult(intent,0);
                    overridePendingTransition(R.anim.activity_open,0);//底部弹起动画
                }
                if(index == 3) {
                    //收藏
//                    collectCall = MyApplication.getMyService().collect();
                    collectCall = MyApplication.getMyService().collect(SpUitl.getUserID(mContext),courseID);
                    collectCall.enqueue(new Callback<universalResponseData>() {
                        @Override
                        public void onResponse(Call<universalResponseData> call, Response<universalResponseData> response) {
                            if(response.body()==null){
                                kProgressHUD.dismiss();
                                Toasty.error(mContext,"服务器异常，提交数据出错！").show();
                                return;
                            }

                            if(response.body().getState() == 0){
                                kProgressHUD.dismiss();
                                MyApplication.refreshCollection = true;
                                TastyToast.makeText(mContext, "收藏成功", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                            }else if(response.body().getState() == 1) {
                                kProgressHUD.dismiss();
                                MyApplication.refreshCollection = true;
                                TastyToast.makeText(mContext, "取消收藏成功", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                            }else {
                                kProgressHUD.dismiss();
                                TastyToast.makeText(mContext, "服务器异常，收藏失败", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                             }
                        }

                        @Override
                        public void onFailure(Call<universalResponseData> call, Throwable t) {
                            kProgressHUD.dismiss();
                            Toasty.error(mContext,"收藏失败，请稍候重试！").show();
                        }
                    });



                }
            }

            @Override
            public void onExpand() {
                isPlaying = playerView.isPlaying();
                playerView.editVideo();
                playerView.setRotate(false);
                //playerView.setBackground(getDrawable(R.drawable.playerview_fg));
            }

            @Override
            public void onCollapse() {
                playerView.recoverFromEditVideo();
                playerView.setRotate(true);
                //playerView.setBackground(null);
            }
        });
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            commentFragment.refresh(null);
            if(isPlaying)
                playerView.start();
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                mViewPager.setCurrentItem(2);
                handler.sendEmptyMessageDelayed(0,300);
                break;
            default:
                break;
        }
    }

}
