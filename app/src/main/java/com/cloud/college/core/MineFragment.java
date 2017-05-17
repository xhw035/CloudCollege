package com.cloud.college.core;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloud.college.R;
import com.cloud.college.network.UserInfoData;
import com.cloud.college.uitl.MyApplication;
import com.cloud.college.uitl.NetworkUtil;
import com.cloud.college.uitl.SpUitl;
import com.cloud.college.uitl.UpdateManager;
import com.hss01248.dialog.StyledDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cloud.college.R.id.mineExitBtn;

/**
 * Author: xiao(xhw219@163.com)
 * Date: 2017-02-21 23:22
 * DESC:首页对应的Fragment
 */

public class MineFragment extends Fragment {

    private Unbinder mUnbinder;
    @BindView(R.id.headImgArea) LinearLayout headArea;
    @BindView(R.id.mineHeadImg) RoundedImageView headImg;
    @BindView(R.id.mineNickName) TextView nickName;

    @BindView(R.id.mineUserInfo) RelativeLayout userInfo;
    @BindView(R.id.mineSetting) RelativeLayout setting;
    @BindView(R.id.mineFeedback) RelativeLayout feedback;
    @BindView(R.id.mineShare) RelativeLayout share;
    @BindView(R.id.mineCheackUpdate) RelativeLayout cheackUpdate;
    @BindView(R.id.mineAbout) RelativeLayout about;
    @BindView(R.id.mineVersionName) TextView versionName;

    @BindView(mineExitBtn) Button exitBtn;

    private Context mContext ;
    private UpdateManager updateManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine,container,false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    public void init(){
        mContext = getActivity();
        updateManager = new UpdateManager(mContext);
        versionName.setText(UpdateManager.getVersionName(mContext));
        handleLoginInfo();
        showUpdatetip(mContext,updateManager,versionName);
        MyApplication.refreshMine = false;
    }

    private void showUpdatetip(Context context , UpdateManager updateManager, final TextView versionName) {
        if(updateManager.isShowRedDot()){
            versionName.setText("发现新版本");
            new QBadgeView(context)
            .bindTarget(versionName)
            .setBadgeGravity(Gravity.TOP|Gravity.END)
            .setGravityOffset(4,8,true)
            .setBadgeText("")
            .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                @Override
                public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                    versionName.setText(UpdateManager.getVersionName(mContext));
                }
            });
        }
    }

    /**
     * 维护登录状态以及展示效果，获取用户头像
     */
    private void handleLoginInfo() {

        if(SpUitl.isLogin(mContext)){
            //==============已登录，请求网络获取用户头像========
            if(!NetworkUtil.isNetworkAvailable(mContext)){
                configCache(mContext);
                return;
            }

            Call<UserInfoData> userInfoCall = MyApplication.getMyService().getUserInfo(SpUitl.getUserID(mContext));
            userInfoCall.enqueue(new Callback<UserInfoData>() {
                @Override
                public void onResponse(Call<UserInfoData> call, Response<UserInfoData> response) {
                    if(response.body()==null){
                        configCache(mContext);
                        return;
                    }

                    UserInfoData data = response.body();
                    nickName.setText(data.getNickname());
                    ImageLoader.getInstance().displayImage(data.getAvatar(),headImg);
                    exitBtn.setVisibility(View.VISIBLE);
                    return;
                }

                @Override
                public void onFailure(Call<UserInfoData> call, Throwable t) {
                    configCache(mContext);
                    return;
                }
            });

        }else {
            //未登录显示
            nickName.setText("注册/登录");
            headImg.setImageResource(R.drawable.img_head_deafult);
            exitBtn.setVisibility(View.GONE);
        }

    }

    private void configCache(Context context) {
        UserInfoData cache = SpUitl.getUserInfoCache(context);
        if(!TextUtils.isEmpty(cache.getNickname()))
            nickName.setText(cache.getNickname());
        if(!TextUtils.isEmpty(cache.getAvatar()))
            ImageLoader.getInstance().displayImage(cache.getAvatar(),headImg);
    }

    @OnClick(R.id.headImgArea)
    public void onClickHead(View v){
        //处理登录
        if(SpUitl.isLogin(mContext)){
            //================已登录，点击头像换头像=========================

        }else{
            //未登录，点击跳转去登录页
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            intent.putExtra("isBack", true);
            startActivityForResult(intent, 0);
            getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
        }
    }

    @OnClick(R.id.mineCheackUpdate)
    public void cheackUpdate(View v){
        MyApplication.checkUpdate(mContext,false);
    }

    @OnClick(R.id.mineAbout)
    public void about(View v){
        ViewGroup customView = (ViewGroup) View.inflate(getActivity(),R.layout.view_mine_about,null);
        StyledDialog.buildCustom(customView, Gravity.CENTER).setCancelable(true,true).show();
    }

    @OnClick(mineExitBtn)
    public void exit(View v){
        SpUitl.setUserID(mContext,"");
        nickName.setText("注册/登录");
        headImg.setImageResource(R.drawable.img_head_deafult);
        //刷新收藏夹
        MyApplication.refreshCollection =true;
        exitBtn.setVisibility(View.GONE);
    }

    //点击头像登陆成功后，再次初始化视图，获取用户头像和昵称,同时还有刷新学习中心。
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0&&resultCode==0){
            init();
            MyApplication.refreshCollection =true;
        }
    }

}