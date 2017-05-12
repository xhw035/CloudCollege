package com.cloud.college.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cloud.college.R;
import com.cloud.college.uitl.SpUitl;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

/**
 * Author: xiao(xhw219@163.com)
 * Date: 2017-05-08 23:22
 * DESC: 搜索历史记录页
 */

public class SearchHistoryFragment extends Fragment {

    private Unbinder mUnbinder;
    private EditText mEditText;
    private TagAdapter<String> mAdapter;
    private String[] dataArray;
    private View view;
    @BindView(R.id.flowlayout) TagFlowLayout mFlowLayout;

    public SearchHistoryFragment() {
    }

    public SearchHistoryFragment(EditText editText) {
        mEditText = editText;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_history, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //从本地SP文件中获取历史搜索数据
        dataArray = SpUitl.getHistoryData(getActivity().getApplicationContext());
        if (dataArray != null && dataArray.length != 0) {
            System.out.println(dataArray.length);
            for (int i = 0; i < dataArray.length; i++) {
                System.out.println(i+":"+dataArray[i]);
            }

            mAdapter = new TagAdapter<String>(dataArray) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.view_textview_history, parent, false);
                    tv.setText(s);
                    return tv;
                }

            };
            mFlowLayout.setAdapter(mAdapter);
            view.setVisibility(View.VISIBLE);

            mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    if (mEditText != null) {
                        mEditText.setText(dataArray[position].trim());
                        mEditText.setSelection(dataArray[position].trim().length());
                    }
                    return true;
                }
            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick(R.id.clearHistory)
    public void clearHistory(View v) {
        //清除SP文件中的历史搜索数据
        SpUitl.clearHistoryData(getActivity().getApplicationContext());
        Toasty.success(getActivity(),"清除历史搜索记录成功").show();
        view.setVisibility(View.GONE);
    }
}