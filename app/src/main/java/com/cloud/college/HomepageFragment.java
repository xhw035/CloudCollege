package com.cloud.college;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.carbs.android.avatarimageview.library.AvatarImageView;

/**
 * Author: xiao(xhw219@163.com)
 * Date: 2017-02-21 23:22
 * DESC:首页对应的Fragment
 */

public class HomepageFragment extends Fragment implements ViewPager.OnPageChangeListener, OnItemClickListener  {

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


    private String[] images = {
        "http://edu-image.nosdn.127.net/0B79A2CB919106942DF25D4A73BB97B1.jpg?imageView&thumbnail=960y440&quality=100",
        "http://edu-image.nosdn.127.net/AA006A73F0ED272BA3CD4394772DD40D.jpg?imageView&thumbnail=960y440&quality=100",
        "http://edu-image.nosdn.127.net/775B986E5E971490AEE45E83A7E53DF1.jpg?imageView&thumbnail=960y440&quality=100",
        "http://edu-image.nosdn.127.net/D8784A11D217E5056BA32D49A72FFFFC.jpg?imageView&thumbnail=960y440&quality=100",
        "http://edu-image.nosdn.127.net/08df3158-d1dc-4498-93e2-a80ed901f881.jpg?imageView&thumbnail=960y440&quality=100",
        "http://edu-image.nosdn.127.net/0e4fd8d1-5218-40c9-a40f-8a509d1e1ef2.jpg?imageView&thumbnail=960y440&quality=100",
        "http://edu-image.nosdn.127.net/fedb7c44-d91e-4501-a046-1538e140744e.jpg?imageView&thumbnail=960y440&quality=100"
    };
    private List<String> networkImages = Arrays.asList(images);;
    ArrayList<String> transformerList = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage,container,false);
        ButterKnife.bind(this,view);
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

    private void initView() {
        initBanner();
        initCourseType();
    }

    private void initBanner() {
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        },networkImages)
        .setPageIndicator(new int[]{R.drawable.img_page_indicator,
         R.drawable.img_page_indicator_focused})//设置指示器的方向
        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

        //循环播放
        convenientBanner.startTurning(5000);
        //添加各种翻页效果
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
    }

    private void initCourseType() {
        avatar1.setTextAndColor("IT", 0xFFFED65C);
        avatar2.setTextAndColor("经", 0xFFFA7556);
        avatar3.setTextAndColor("人", 0xFFA2D669);
        avatar4.setTextAndColor("社", 0xFF62B2FA);

        avatar5.setTextAndColor("理", 0xFF49D1AD);
        avatar6.setTextAndColor("工", 0xFFC5A9F6);
        avatar7.setTextAndColor("高", 0xFF67E0F3);
        avatar8.setTextAndColor("其", 0xFF9CABC2);
    }

    private void initEvent() {
        convenientBanner.setOnPageChangeListener(this).setOnItemClickListener(this);
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageSelected(int position) {

        //convenientBanner.setCanLoop(false);
        String transforemerName = transformerList.get((int)(Math.random()*14));
        Toast.makeText(getActivity(),"监听到翻到第"+position+":"+transforemerName,Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(),"点击了第"+position+"个",Toast.LENGTH_SHORT).show();
    }

}