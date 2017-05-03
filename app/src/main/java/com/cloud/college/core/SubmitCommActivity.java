package com.cloud.college.core;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.cloud.college.R;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

/**
 * Created by xiao on 2017/5/3.
 */

public class SubmitCommActivity extends Activity {

    private Unbinder mUnbinder;
    @BindView(R.id.cancelComm) ImageView cancelComm;
    @BindView(R.id.submitComm) ImageView submitComm;
    @BindView(R.id.editTextComm) EditText editTextComm;

    String commStr="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_comm);
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void finish() {
        //关闭窗体动画显示
        super.finish();
        //this.overridePendingTransition(R.anim.activity_close,0);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            commStr = editTextComm.getText().toString();
            if (TextUtils.isEmpty(commStr)){
                hideSoftInput();
                finish();
            }else {
                StyledDialog.buildIosAlert( "", "您的评论还没有提交，确定要离开吗？",  new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        hideSoftInput();
                        finish();
                    }

                    @Override
                    public void onSecond() {
                        showSoftInput();
                    }

                }).setBtnText("确定","取消").show();
            }

            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @OnClick(R.id.cancelComm)
    public void cancel(View view){
        hideSoftInput();
        finish();
        //SubmitCommActivity.this.overridePendingTransition(R.anim.activity_close,0);
    }

    @OnClick(R.id.submitComm)
    public void submit(View view){
         commStr = editTextComm.getText().toString();
        if (TextUtils.isEmpty(commStr)){
            Toasty.info(this,"请输入内容后再提交！").show();
            return;
        }else {
            hideSoftInput();
            //============将内容提交到服务器并关闭当前页==========

            Toasty.info(this,"评论提交成功").show();
            setResult(RESULT_OK);
            finish();
        }
    }

    //收起软键盘
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    //弹起软键盘
    public void showSoftInput() {
        Toasty.info(this,"弹起软键盘").show();
        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); //开关软件键盘
        /* 下面的都不生效
        inputManager.showSoftInput(editTextComm, 0);
        inputManager.showSoftInputFromInputMethod(getWindow().getDecorView().getWindowToken(),0);*/
    }





}
