package com.cloud.college.core;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cloud.college.R;
import com.cloud.college.widgets.MyViewPager;
import com.cloud.college.widgets.ScaleTransitionPagerTitleView;
import com.dl7.player.media.IjkPlayerView;
import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;
import com.sdsmdg.tastytoast.TastyToast;
import com.xiao.magictimeline.CatalogFragment;
import com.xiao.magictimeline.CatalogModel;
import com.xiao.magictimeline.ChildHolder;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

import static com.xiao.magictimeline.CatalogAdapter.OnItemClickListener;

/**
 * Created by xiao on 2017/4/9.
 */

public class DetailActivity extends AppCompatActivity {

    private Unbinder unbinder;
    @BindView(R.id.expandableBtn) AllAngleExpandableButton plusButton;
    @BindView(R.id.detailToolbar) Toolbar toolbar;
    @BindView(R.id.playerView) IjkPlayerView playerView;
    @BindView(R.id.detailViewpager) MyViewPager mViewPager;

    private static final String playerThumb = "http://www.maiziedu.com/uploads/course/2016/04/Activity.jpg";
    private static final String video_1 = "rtmpt://123.207.237.185:5080/oflaDemo/it/时光里的百度.flv";
    private static final String video_2 = "rtmpt://123.207.237.185:5080/oflaDemo/mv/SeeYouAgain.mp4";
    private static final String video_3 = "rtmpt://123.207.237.185:5080/oflaDemo/电影/速度与激情8.flv";
    private static final String video_4 = "rtmpt://123.207.237.185/oflaDemo/手机/小米MIX概念手机背后的故事.mp4";
    private static final String video_5 = "rtmpt://123.207.237.185:5080/oflaDemo/Android视频/0.Android集成开发环境搭建/1.在Mac平台搭建Android集成开发环境.mp4";

    private static final String[] pagerTitle = new String[]{"目录", "介绍", "评论"};
    private List<String> titleList = Arrays.asList(pagerTitle);
    List<CatalogModel> catalogList;
    CatalogFragment catalogFragment;
    IntroductionFragment introductionFragment;
    CommentFragment commentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        unbinder = ButterKnife.bind(this);
        //WindowManager.LayoutParams attributes = getWindow().getAttributes();
        //getWindow().setAttributes(attributes);
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
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        playerView.configurationChanged(newConfig);
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

        //============================处理视频播放器=============================
        Glide.with(this).load(playerThumb).fitCenter().into(playerView.mPlayerThumb);
        playerView.init()
        .setToolbar(toolbar)
        .setTitle("这是个跑马灯TextView，标题要足够长足够长足够长足够长足够长足够长足够长足够长才会跑")
        .setMscreenShotDir("CloudCollege/screenshot")
        .setVideoSource(video_1, video_2, video_3, video_4, video_5)
        .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_BD);

        //========================处理下方ViewPager,MagicIndicator,加号==========================
        mViewPager = (MyViewPager) findViewById(R.id.detailViewpager);
        initViewPager();
        initMagicIndicator();
        initPlusButton();
    }

    /*public class MyListener implements CatalogAdapter.OnItemClickListener{

        @Override
        public void onTitleClick(View view, int position) {
            Toasty.info(getApplicationContext(),((TextView)view).getText()).show();
        }

        @Override
        public void onDownloadClick(View view, int position) {
            Toasty.success(getApplicationContext(),"download").show();
        }
    }*/

    private void initViewPager() {
        //=======================初始化时间轴目录数据和监听器=========================
        catalogList = new ArrayList<CatalogModel>();
        for (int i = 0; i < 20; i++) {
            CatalogModel catalogModel = new CatalogModel();
            if(i==0||i==5||i==11)
                catalogModel.setType(CatalogModel.TYPE_GROUP);
            else
                catalogModel.setType(CatalogModel.TYPE_CHILD);
            if(catalogModel.getType()== CatalogModel.TYPE_GROUP)
                catalogModel.setGruopName("章节名称"+i);
            if(catalogModel.getType()== CatalogModel.TYPE_CHILD){
                catalogModel.setChildName("课程名称很长很长很长很长很长"+i);
                catalogModel.setVideoTime("12:"+i);
                catalogModel.setURL_M(video_1);
                catalogModel.setURL_H(video_2);
            }
            catalogList.add(catalogModel);
        }

        OnItemClickListener listener=  new OnItemClickListener(){
            @Override
            public void onTitleClick(ChildHolder holder, int position) {
                for (int i = 0; i < catalogList.size(); i++) {
                    catalogList.get(i).setFontColor(Color.BLACK);
                    if(i==position)
                        catalogList.get(i).setFontColor(Color.parseColor("#2BC17A"));
                }
                catalogFragment.adpter.notifyDataSetChanged();

                playerView.stop();
                playerView
                .setTitle(catalogList.get(position).getChildName())
                .setVideoSource(null, catalogList.get(position).getURL_M(), catalogList.get(position).getURL_H(), null, null)
                .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH)
                .start();
            }

            @Override
            public void onDownloadClick(View view, int position) {
                Toasty.success(getApplicationContext(),"download").show();
                ((ImageView)view).setBackground(getResources().getDrawable(R.drawable.bg_download_gray));
            }
        };
        catalogFragment = new CatalogFragment(catalogList,listener);

        //=========================简介和评论Fragment==============================
        introductionFragment = new IntroductionFragment();
        commentFragment = new CommentFragment();

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
    }

    private void initMagicIndicator() {
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titleList == null ? 0 : titleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(titleList.get(index));
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
        ViewPagerHelper.bind(magicIndicator, mViewPager);
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
                if (menuItemId == R.id.action_item1) {
                    TastyToast.makeText(DetailActivity.this, "收藏成功", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                }
                if (menuItemId == R.id.action_item2) {
                    TastyToast.makeText(DetailActivity.this, "分享成功", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
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
                Toasty.info(getApplicationContext(),"clicked index:" + index).show();
                if(index == 2) {
                    Intent intent = new Intent(DetailActivity.this,SubmitCommActivity.class);
                    startActivityForResult(intent,0);
                    //底部弹起动画
                    overridePendingTransition(R.anim.activity_open,0);
                }
            }

            @Override
            public void onExpand() {
                playerView.editVideo();
                //playerView.setBackground(getDrawable(R.drawable.playerview_fg));
            }

            @Override
            public void onCollapse() {
                playerView.recoverFromEditVideo();
                //playerView.setBackground(null);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                commentFragment.refresh();
                mViewPager.setCurrentItem(2);
                break;
            default:
                break;
        }
    }

}
