package com.cloud.college.core;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.cloud.college.R;
import com.dl7.player.media.IjkPlayerView;
import com.sdsmdg.tastytoast.TastyToast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiao on 2017/4/9.
 */

public class DetailActivity extends AppCompatActivity {

    private static final String playerThumb = "http://www.maiziedu.com/uploads/course/2016/04/Activity.jpg";
    private static final String video_1 = "rtmpt://123.207.237.185:5080/oflaDemo/mv/小苹果.mp4";
    private static final String video_2 = "rtmp://123.207.237.185:5080/oflaDemo/mv/无尽的爱.mp4";
    private static final String video_3 = "rtmpt://123.207.237.185:5080/oflaDemo/mv/道士下山.mov";
    private static final String video_4 = "rtmp://123.207.237.185/oflaDemo/0.Android集成开发环境搭建/2.在Windows平台搭建Android集成开发环境.mp4";
    private static final String video_5 = "rtmpt://123.207.237.185:5080/oflaDemo/0.Android集成开发环境搭建/1.在Mac平台搭建Android集成开发环境.mp4";

    @BindView(R.id.detailToolbar) Toolbar toolbar;
    @BindView(R.id.playerView) IjkPlayerView playerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
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
        if (playerView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }


    private void initView() {
        toolbar.inflateMenu(R.menu.detail_toolbar_menu);
        Glide.with(this).load(playerThumb).fitCenter().into(playerView.mPlayerThumb);
        playerView.init()
        .setToolbar(toolbar)
        .setTitle("这是个跑马灯TextView，标题要足够长足够长足够长足够长足够长足够长足够长足够长才会跑")
        .setMscreenShotDir("CloudCollege/screenshot")
        .setVideoSource(video_1, video_2, video_3, video_4, video_5)
        .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_BD);
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
    }


}
