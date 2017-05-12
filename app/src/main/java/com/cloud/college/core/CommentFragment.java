package com.cloud.college.core;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cloud.college.R;
import com.cloud.college.network.CommentData;
import com.cloud.college.uitl.MyApplication;
import com.cloud.college.uitl.SpUitl;
import com.cloud.college.uitl.networkUtil;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static es.dmoral.toasty.Toasty.info;

/**
 * Author: xiao(xhw219@163.com)
 * Date: 2017-02-21 23:22
 * DESC:视频详情页下面评论对应的Fragment
 */

public class CommentFragment extends Fragment {

    @BindView(R.id.commentListView) ListView commentListView;
    @BindView(R.id.noCommm) LinearLayout noCommm;
    @BindView(R.id.commException) LinearLayout commException;
    @BindView(R.id.commLoading) LinearLayout commLoading;

    private Unbinder mUnbinder;
    private View view;
    private SimpleRatingBar simpleRatingBar;
    private CommentAdapter adapter;
    private Call<CommentData> commCall;

    private float myScore;
    List<CommentData.DataBean> commentData;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail_comment,container,false);
        mUnbinder = ButterKnife.bind(this, view);
        View headerView = View.inflate(getActivity(), R.layout.view_header_comment, null);
        commentListView.addHeaderView(headerView);
        simpleRatingBar = (SimpleRatingBar )headerView.findViewById(R.id.simpleRatingBar);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initEvent();

       /* List<CommentModel> mlist = new ArrayList<CommentModel>();
        for (int i = 0; i < 20; i++) {
            CommentModel model = new CommentModel();
            model.setHeadUrl("http://avatar.csdn.net/7/F/F/2_xhw035.jpg");
            model.setNickName("我就是太嚣张我就是太嚣张我就是太嚣张我就是太嚣张我就是太嚣张我就是太嚣张我就是太嚣张我就是太嚣张");
            model.setCommentTime("2017.05.02");
            model.setCommentStr("我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅");
            mlist.add(model);
        }
        adapter = new CommentAdapter(getActivity(),mlist);
        commentListView.setAdapter(adapter);*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    public void initData(){
        commentListView.setVisibility(View.GONE);
        noCommm.setVisibility(View.GONE);
        commException.setVisibility(View.GONE);
        commLoading.setVisibility(View.VISIBLE);

//        commCall = MyApplication.getMyService().getComment();
        commCall = MyApplication.getMyService().getComment(SpUitl.getUserID(getActivity()),((DetailActivity)getActivity()).courseID);
        if(!networkUtil.isNetworkAvailable(getActivity())){
            commentListView.setVisibility(View.GONE);
            noCommm.setVisibility(View.GONE);
            commException.setVisibility(View.VISIBLE);
            commLoading.setVisibility(View.GONE);
            Toasty.error(getActivity(),"网络异常，无法连接服务器！").show();
            return ;
        }

        commCall.enqueue(new Callback<CommentData>() {
            @Override
            public void onResponse(Call<CommentData> call, Response<CommentData> response) {
                if(response.body()==null){
                    commentListView.setVisibility(View.GONE);
                    noCommm.setVisibility(View.GONE);
                    commException.setVisibility(View.VISIBLE);
                    commLoading.setVisibility(View.GONE);
                    Toasty.error(getActivity(),"服务器异常，加载数据出错！").show();
                    return;
                }

                myScore =(float) response.body().getMyScore();
                commentData = response.body().getData();
                initView();
            }

            @Override
            public void onFailure(Call<CommentData> call, Throwable t) {
                commentListView.setVisibility(View.GONE);
                noCommm.setVisibility(View.GONE);
                commException.setVisibility(View.VISIBLE);
                commLoading.setVisibility(View.GONE);
                Toasty.error(getActivity(),"加载数据失败，请稍候重试！").show();
                return;
            }
        });

    }

    public void initView(){
        simpleRatingBar.setRating(myScore);
        List<CommentModel> list = new ArrayList<CommentModel>();
        for (CommentData.DataBean comm : commentData) {
            CommentModel model = new CommentModel();
            model.setHeadUrl(comm.getUserImg());
            model.setNickName(comm.getNickname());
            model.setCommentTime(comm.getTime());
            model.setCommentStr(comm.getDesc());
            list.add(model);
        }
        if(list.size()==0){
            commentListView.setVisibility(View.GONE);
            noCommm.setVisibility(View.VISIBLE);
            commException.setVisibility(View.GONE);
            commLoading.setVisibility(View.GONE);
        }else {
            adapter = new CommentAdapter(getActivity(), list);
            commentListView.setAdapter(adapter);

            commentListView.setVisibility(View.VISIBLE);
            noCommm.setVisibility(View.GONE);
            commException.setVisibility(View.GONE);
            commLoading.setVisibility(View.GONE);
        }
    }

    public void initEvent() {
        simpleRatingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            info(getActivity(), "您的评分为：" + simpleRatingBar.getRating()).show();

            }
        });

    }

    @OnClick(R.id.commRefresh)
    public void refresh(View view){
        initData();
    }
}

class CommentModel {
   private String headUrl;
   private String nickName;
   private String commentTime;
   private String commentStr;

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentStr() {
        return commentStr;
    }

    public void setCommentStr(String commentStr) {
        this.commentStr = commentStr;
    }
}

class  CommentAdapter extends BaseAdapter{

    private Context mContext;
    private List<CommentModel> mList;

    public CommentAdapter(Context context,List<CommentModel> list ) {
        mList = list ;
        mContext = context ;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CommentModel getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
        @BindView(R.id.commHeadImg) RoundedImageView commHeadImg;
        @BindView(R.id.commNickName) TextView commNickName;
        @BindView(R.id.commTime) TextView commTime;
        @BindView(R.id.commStr) TextView commStr;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(mContext,R.layout.view_listview_comment,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(mList.get(position).getHeadUrl()).fitCenter().into(holder.commHeadImg);
        holder.commNickName.setText(mList.get(position).getNickName());
        holder.commTime.setText(mList.get(position).getCommentTime());
        holder.commStr.setText(mList.get(position).getCommentStr());
        return convertView;
    }

}