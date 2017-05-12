package com.cloud.college.core;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloud.college.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

import static com.cloud.college.uitl.UiUtil.hideSoftInput;
import static com.cloud.college.uitl.UiUtil.showSoftInput;

/**
 * Created by xiao on 2017/5/7.
 */

public class SearchPageActivity extends FragmentActivity implements TextView.OnEditorActionListener
        ,TextWatcher{

    private Unbinder unbinder;
    @BindView(R.id.keywords) EditText keywordsET;
    @BindView(R.id.clearKeywords) ImageView clearKeywords;

    private Intent intent;
    private int flag;
    private String typeID;
    private SearchHistoryFragment histroyFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        unbinder = ButterKnife.bind(this);
        initView();
        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction mTransaction = fm.beginTransaction();

        intent = getIntent();
        flag = intent.getIntExtra("flag", -1);
        if(flag == 0) {
            histroyFragment = new SearchHistoryFragment(keywordsET);
            mTransaction.replace(R.id.searchContent,histroyFragment);
            mTransaction.commit();
            showSoftInput(SearchPageActivity.this);
        }

        if(flag == 1){
            //类别搜索时，编辑框不可编辑
            hideSoftInput(SearchPageActivity.this);
            keywordsET.setEnabled(false);
            keywordsET.setFocusable(false);
            keywordsET.setFocusableInTouchMode(false);

            typeID = intent.getStringExtra("typeID");
            String typeName = intent.getStringExtra("typeName");
            keywordsET.setText(typeName);
            keywordsET.setTextSize(18f);
            keywordsET.setTypeface(Typeface.DEFAULT_BOLD);
            keywordsET.setGravity(Gravity.CENTER);

            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)keywordsET.getLayoutParams();
            params.setMargins(0,0,0,0);
            keywordsET.setLayoutParams(params);

            SearchResultFragment fragment = new SearchResultFragment(typeID,"");
            mTransaction.replace(R.id.searchContent,fragment);
            mTransaction.commit();
        }


    }

    private void initEvent() {
        if (flag == 0) {
            keywordsET.addTextChangedListener(this);
        }
        keywordsET.setOnEditorActionListener(this);
    }

    @OnClick(R.id.backSearch)
    public void back(View view){
        finish();
    }

    @OnClick(R.id.clearKeywords)
    public void clear(View view){
        keywordsET.setText("");
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (flag == 0 && !s.toString().trim().isEmpty()) {
            clearKeywords.setVisibility(View.VISIBLE);
        }else {
            clearKeywords.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId== EditorInfo.IME_ACTION_SEARCH)
        {
            String keyword = keywordsET.getText().toString().trim();
            if(keyword.isEmpty()){
                Toasty.info(this,"请输入搜索内容后再提交").show();
                return true;
            }else {
                //=====================这里处理提交关键词搜索==============
                hideSoftInput(SearchPageActivity.this);
                SearchResultFragment fragment = new SearchResultFragment("",keyword);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction mTransaction = fm.beginTransaction();
                mTransaction.replace(R.id.searchContent,fragment);
                mTransaction.commit();


                return true;
            }
        }
        return false;
    }


}
