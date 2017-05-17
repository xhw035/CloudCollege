package com.cloud.college.core;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cloud.college.R;
import com.cloud.college.network.CollectInfoData;
import com.cloud.college.network.universalResponseData;
import com.cloud.college.uitl.MyApplication;
import com.cloud.college.uitl.SpUitl;
import com.cloud.college.uitl.NetworkUtil;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sdsmdg.tastytoast.TastyToast;
import com.xiao.magictimeline.SwipeDragLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import info.hoang8f.android.segmented.SegmentedGroup;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: xiao(xhw219@163.com)
 * Date: 2017-02-21 23:22
 * DESC:学习中心对应的Fragment
 */

public class StudyCenterFragment extends Fragment  {

    @BindView(R.id.segmentedGroup) SegmentedGroup segmentedGroup;
    @BindView(R.id.downloadBtn) RadioButton downloadBtn;
    @BindView(R.id.collectBtn) RadioButton collectBtn;
    @BindView(R.id.downloadListview) ListView downloadListview;
    @BindView(R.id.collectListview) ListView collectListview;

    @BindView(R.id.centerLoading) View centerLoading;
    @BindView(R.id.centerException) View centerException;
    @BindView(R.id.centerfresh) ImageView centerfresh;
    @BindView(R.id.centerEmpty) View centerEmpty;

    @BindView(R.id.centerEmptyTip) TextView centerEmptyTip;

    private View view;
    private Context mContext;
    private collectAdapter collectAdapter;
    private Call<CollectInfoData> collectCall;
    public List<CollectInfoData.DataBean> dataList;
    private KProgressHUD kProgressHUD;
    private Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_studycenter,container,false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    public void init() {
        //collectBtn.setChecked(true);
        OnSwipeListener listener = new OnSwipeListener(getActivity());
        collectAdapter = new collectAdapter(getActivity(),listener);
        collectListview = (ListView)view.findViewById(R.id.collectListview);
        collectListview.setAdapter(collectAdapter);

        downloadListview.setVisibility(View.GONE);
        collectListview.setVisibility(View.GONE);
        centerLoading.setVisibility(View.VISIBLE);
        centerException.setVisibility(View.GONE);
        centerEmpty.setVisibility(View.GONE);

        handleCollectData();
        MyApplication.refreshCollection = false;
    }

    @OnClick(R.id.centerfresh)
    public void refresh(View view){
        init();
    }

    //从网络加载收藏列表数据
    private void handleCollectData() {
        if(!SpUitl.isLogin(mContext)){
            downloadListview.setVisibility(View.GONE);
            collectListview.setVisibility(View.GONE);
            centerLoading.setVisibility(View.GONE);
            centerException.setVisibility(View.GONE);
            centerEmpty.setVisibility(View.VISIBLE);
            Toasty.info(mContext,"您还未登录呢，无法获取收藏数据").show();
            return;
        }

       //collectCall = MyApplication.getMyService().getCollectInfo();
       collectCall = MyApplication.getMyService().getCollectInfo(SpUitl.getUserID(mContext));
        if(!NetworkUtil.isNetworkAvailable(getActivity())){
            downloadListview.setVisibility(View.GONE);
            collectListview.setVisibility(View.GONE);
            centerLoading.setVisibility(View.GONE);
            centerException.setVisibility(View.VISIBLE);
            centerEmpty.setVisibility(View.GONE);
            Toasty.error(mContext,"网络异常，无法连接服务器！").show();
            return ;
        }

        collectCall.enqueue(new retrofit2.Callback<CollectInfoData>() {
            @Override
            public void onResponse(Call<CollectInfoData> call, Response<CollectInfoData> response) {
                if(response.body()==null||response.body().getData()==null||response.body().getData().size()==0){
                    downloadListview.setVisibility(View.GONE);
                    collectListview.setVisibility(View.GONE);
                    centerLoading.setVisibility(View.GONE);
                    centerException.setVisibility(View.GONE);
                    centerEmpty.setVisibility(View.VISIBLE);
                    centerEmptyTip.setText("空空如是也...");
                    Toasty.info(getActivity(),"您还没收藏任何课程").show();
                    MainActivity.isCollectionEmpty = true;
                    return;
                }

                dataList = response.body().getData();
                collectAdapter.addList(dataList);
                collectAdapter.notifyDataSetChanged();

                downloadListview.setVisibility(View.GONE);
                collectListview.setVisibility(View.VISIBLE);
                centerLoading.setVisibility(View.GONE);
                centerException.setVisibility(View.GONE);
                centerEmpty.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CollectInfoData> call, Throwable t) {
                downloadListview.setVisibility(View.GONE);
                collectListview.setVisibility(View.GONE);
                centerLoading.setVisibility(View.GONE);
                centerException.setVisibility(View.VISIBLE);
                centerEmpty.setVisibility(View.GONE);
                Toasty.error(mContext,"加载数据失败，请稍候重试！").show();
                return ;
            }

        });

    }

    //==================SwipeDragLayout监听器内部类=============================
    class OnSwipeListener implements SwipeDragLayout.SwipeListener{

        private Context context;
        private Call<universalResponseData> cancleCall;

        public OnSwipeListener(Context context) {
            this.context = context;
        }

        @Override
        public void onUpdate(SwipeDragLayout layout, float offset) {
        }

        @Override
        public void onOpened(SwipeDragLayout layout) {
        }

        @Override
        public void onClosed(SwipeDragLayout layout) {
        }

        @Override
        //点击进行视频详情页
        public void onClick(SwipeDragLayout layout) {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("courseID", dataList.get((int) layout.getTag()).getCourseID());
            context.startActivity(intent);
        }

        @Override
        //长按取消收藏课程
        public void onLongClick(final SwipeDragLayout layout) {

            StyledDialog.buildIosAlert("", "您确定要取消收藏该课程吗？", new MyDialogListener() {
                @Override
                public void onFirst() {
                    cancelCollect((int)layout.getTag());
                }

                @Override
                public void onSecond() {
                }

            }).setBtnText("确定","取消").show();

        }

        public  void cancelCollect(final int position){
            kProgressHUD = KProgressHUD.create(context)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("正在删除...")
            .setWindowColor(Color.parseColor("#992BC17A"))
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
            .show();

            if(!NetworkUtil.isNetworkAvailable(mContext)){
                kProgressHUD.dismiss();
                Toasty.error(mContext,"网络异常，无法连接服务器！").show();
                return ;
            }

//          cancleCall = MyApplication.getMyService().collect();
            cancleCall = MyApplication.getMyService().collect(SpUitl.getUserID(context),dataList.get(position).getCourseID());
            cancleCall.enqueue(new retrofit2.Callback<universalResponseData>() {
                @Override
                public void onResponse(Call<universalResponseData> call, Response<universalResponseData> response) {
                    if(response.body()==null){
                        kProgressHUD.dismiss();
                        Toasty.error(context,"服务器异常，提交数据出错！").show();
                        return;
                    }

                    if(response.body().getState() == 1) {
                        kProgressHUD.dismiss();
                        dataList.remove(position);
                        collectAdapter.notifyDataSetChanged();

                        //收藏课程删完了，则显示空提示。
                        if(dataList.size() == 0){
                            downloadListview.setVisibility(View.GONE);
                            collectListview.setVisibility(View.GONE);
                            centerLoading.setVisibility(View.GONE);
                            centerException.setVisibility(View.GONE);
                            centerEmpty.setVisibility(View.VISIBLE);
                            centerEmptyTip.setText("空空如是也...");
                            MainActivity.isCollectionEmpty = true;
                        }
                        TastyToast.makeText(context, "取消收藏成功", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    }else {
                        kProgressHUD.dismiss();
                        TastyToast.makeText(context, "服务器异常，取消收藏失败", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }
                }

                @Override
                public void onFailure(Call<universalResponseData> call, Throwable t) {
                    kProgressHUD.dismiss();
                    Toasty.error(context,"取消收藏失败，请稍候重试！").show();
                }
            });

        }


    }


}

class collectAdapter extends BaseAdapter {

    private Context mContext;
    private List<CollectInfoData.DataBean> mList;
    private StudyCenterFragment.OnSwipeListener listener;

    public collectAdapter(Context context,StudyCenterFragment.OnSwipeListener listener) {
        mList = new ArrayList<CollectInfoData.DataBean>();
        mContext = context ;
        this.listener = listener;
    }

    public void  addList(List<CollectInfoData.DataBean> list){
        mList = list;
    }

    public void  addOnSwipeListener(StudyCenterFragment.OnSwipeListener listener){
        this.listener = this.listener;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CollectInfoData.DataBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(mContext,R.layout.view_listview_collect,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(mList.get(position).getCourseImg()).fitCenter().into(holder.collectCourseImg);
        holder.collectCourseName.setText(mList.get(position).getCourseName());
        holder.collectRatingBar.setRating((float) mList.get(position).getScore());
        holder.collectTime.setText(mList.get(position).getCollectTime());

        holder.centerDragLayout.setTag(position);
        holder.centerDragLayout.addListener(listener);
        holder.collectDragBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.centerDragLayout.smoothClose(true);
                listener.cancelCollect(position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @BindView(R.id.centerDragLayout) SwipeDragLayout centerDragLayout;
        @BindView(R.id.collectCourseImg) RoundedImageView collectCourseImg;
        @BindView(R.id.collectCourseName) TextView collectCourseName;
        @BindView(R.id.collectRatingBar) SimpleRatingBar collectRatingBar;
        @BindView(R.id.collectTime) TextView collectTime;
        @BindView(R.id.collectDragBtn) ImageView collectDragBtn;

    }


}