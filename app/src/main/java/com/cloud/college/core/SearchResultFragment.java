package com.cloud.college.core;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cloud.college.R;
import com.cloud.college.network.SearchResultData;
import com.cloud.college.uitl.MyApplication;
import com.cloud.college.uitl.SpUitl;
import com.cloud.college.uitl.networkUtil;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Author: xiao(xhw219@163.com)
 * Date: 2017-02-21 23:22
 * DESC:搜索结果页对应的Fragment
 */

public class SearchResultFragment extends Fragment implements AdapterView.OnItemClickListener {

    private Unbinder mUnbinder;
    @BindView(R.id.searchLoading) LinearLayout loading;
    @BindView(R.id.resultListview) ListView listview;
    @BindView(R.id.searchException) LinearLayout fail;
    @BindView(R.id.searchExceptionTip) TextView tip;
    private String typeID, keywords;
    private SearchResultAdapter adapter;
    private Call<SearchResultData> searchCall;
    private List<SearchResultData.DataBean> dataList;

    public SearchResultFragment() {
    }

    public SearchResultFragment(String typeID, String keywords) {
        this.typeID = typeID;
        this.keywords = keywords;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new SearchResultAdapter(getActivity());
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
        handleData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        searchCall.cancel();
    }

    private void handleData() {

        searchCall = MyApplication.getMyService().getSearchResult(typeID,keywords);
//        searchCall = MyApplication.getMyService().getSearchResult();
        if(!networkUtil.isNetworkAvailable(getActivity())){
            loading.setVisibility(View.GONE);
            listview.setVisibility(View.GONE);
            tip.setText("网络异常，无法连接服务器...");
            fail.setVisibility(View.VISIBLE);
            Toasty.error(getActivity(),"网络异常，无法连接服务器！").show();
            return ;
        }

        searchCall.enqueue(new Callback<SearchResultData>() {
            @Override
            public void onResponse(Call<SearchResultData> call, Response<SearchResultData> response) {
                if(response.body()==null||response.body().getData()==null||response.body().getData().size()==0){
                    loading.setVisibility(View.GONE);
                    listview.setVisibility(View.GONE);
                    tip.setText("空空如是也...");
                    fail.setVisibility(View.VISIBLE);
                    Toasty.info(getActivity(),"没有搜索到任何课程").show();
                    return;
                }

                dataList = response.body().getData();
                adapter.addList(dataList);
                adapter.notifyDataSetChanged();
                loading.setVisibility(View.GONE);
                fail.setVisibility(View.GONE);
                listview.setVisibility(View.VISIBLE);
                //如果是关键字搜索且成功则加入历史纪录
                if(!keywords.equals(""))
                  SpUitl.addHistoryData(getActivity(),keywords);
            }

            @Override
            public void onFailure(Call<SearchResultData> call, Throwable t) {
                loading.setVisibility(View.GONE);
                listview.setVisibility(View.GONE);
                tip.setText("加载数据失败...");
                fail.setVisibility(View.VISIBLE);
                Toasty.error(getActivity(),"加载数据失败，请稍候重试！").show();
                return ;
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(),DetailActivity.class);
        intent.putExtra("courseID",dataList.get(position).getCourseID());
        startActivity(intent);
    }

}

class SearchResultAdapter extends BaseAdapter {

    private Context mContext;
    private List<SearchResultData.DataBean> mList;

    public SearchResultAdapter(Context context) {
        mList = new ArrayList<SearchResultData.DataBean>();
        mContext = context ;
    }

    public void  addList(List<SearchResultData.DataBean> list){
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public SearchResultData.DataBean getItem(int position) {
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

        @BindView(R.id.resultCourseImg) RoundedImageView resultCourseImg;
        @BindView(R.id.resultCourseName) TextView resultCourseName;
        @BindView(R.id.resultRatingBar) SimpleRatingBar resultRatingBar;
        @BindView(R.id.resultCourseScore) TextView resultCourseScore;
        @BindView(R.id.resultStudyNumber) TextView resultStudyNumber;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(mContext,R.layout.view_listview_search,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(mList.get(position).getCourseImg()).fitCenter().into(holder.resultCourseImg);
        holder.resultCourseName.setText(mList.get(position).getCourseName());
        holder.resultRatingBar.setRating((float) mList.get(position).getScore());
        holder.resultCourseScore.setText(mList.get(position).getScore()+"");
        holder.resultStudyNumber.setText(mList.get(position).getStudyNumber()+"人学过");
        return convertView;
    }

}