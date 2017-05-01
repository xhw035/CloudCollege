package com.cloud.college.core;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cloud.college.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static es.dmoral.toasty.Toasty.info;

/**
 * Author: xiao(xhw219@163.com)
 * Date: 2017-02-21 23:22
 * DESC:首页对应的Fragment
 */

public class CommentFragment extends Fragment {

    private Unbinder mUnbinder;
    private ListView commentListView;
    @BindView(R.id.simpleRatingBar)SimpleRatingBar simpleRatingBar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_comment,container,false);
        commentListView = (ListView )view.findViewById(R.id.commentListView);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.view_header_comment, null);
        mUnbinder = ButterKnife.bind(this, view);
        commentListView.addHeaderView(view);

        List<CommentModel> mlist = new ArrayList<CommentModel>();
        for (int i = 0; i < 20; i++) {
            CommentModel model = new CommentModel();
            model.setHeadUrl("http://avatar.csdn.net/7/F/F/2_xhw035.jpg");
            model.setNickName("我就是太嚣张我就是太嚣张我就是太嚣张我就是太嚣张我就是太嚣张我就是太嚣张我就是太嚣张我就是太嚣张");
            model.setCommentTime("2017.05.02");
            model.setCommentStr("我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅我好帅");
            mlist.add(model);
        }
        CommentAdapter adapter = new CommentAdapter(getActivity(),mlist);
        commentListView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick(R.id.simpleRatingBar)
    public void onRatingBarClick(View v) {
        info(getActivity(), "您的评分为：" + simpleRatingBar.getRating()).show();
    }
}

class CommentModel{
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
        //holder.commHeadImg.setImageResource(R.drawable.chart_img4);
        holder.commNickName.setText(mList.get(position).getNickName());
        holder.commTime.setText(mList.get(position).getCommentTime());
        holder.commStr.setText(mList.get(position).getCommentStr());
        return convertView;
    }

}