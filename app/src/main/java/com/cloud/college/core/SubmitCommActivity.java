package com.cloud.college.core;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.cloud.college.R;
import com.cloud.college.network.universalResponseData;
import com.cloud.college.uitl.MyApplication;
import com.cloud.college.uitl.SpUitl;
import com.cloud.college.uitl.NetworkUtil;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.sdsmdg.tastytoast.TastyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Response;

import static com.cloud.college.uitl.UiUtil.hideSoftInput;
import static com.cloud.college.uitl.UiUtil.showSoftInput;

/**
 * Created by xiao on 2017/5/3.
 */

public class SubmitCommActivity extends Activity {

    private Unbinder mUnbinder;
    @BindView(R.id.cancelComm) ImageView cancelComm;
    @BindView(R.id.submitComm) ImageView submitComm;
    @BindView(R.id.editTextComm) EditText editTextComm;

    private String commStr = "";
    private String videoID = "";
    private Context mContext = this;
    private Call<universalResponseData> commentCall;
    private KProgressHUD kProgressHUD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_comm);
        mUnbinder = ButterKnife.bind(this);
        videoID = getIntent().getStringExtra("videoID");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        //commentCall.cancel();
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
                hideSoftInput(SubmitCommActivity.this);
                finish();
            }else {
                StyledDialog.buildIosAlert( "", "您的评论还没有提交，确定要离开吗？",  new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        hideSoftInput(SubmitCommActivity.this);
                        finish();
                    }

                    @Override
                    public void onSecond() {
                        showSoftInput(SubmitCommActivity.this);
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
        hideSoftInput(SubmitCommActivity.this);
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
             hideSoftInput(SubmitCommActivity.this);
            handler.sendEmptyMessageDelayed(0,200);
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            kProgressHUD = KProgressHUD.create(mContext)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("正在提交...")
            .setWindowColor(Color.parseColor("#992BC17A"))
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
            .show();

            if(!NetworkUtil.isNetworkAvailable(mContext)){
                kProgressHUD.dismiss();
                Toasty.error(mContext,"网络异常，无法连接服务器！").show();
                return ;
            }


//            commentCall = MyApplication.getMyService().addComment();
          commentCall = MyApplication.getMyService().addComment(SpUitl.getUserID(mContext),videoID,commStr);

            commentCall.enqueue(new retrofit2.Callback<universalResponseData>() {

                @Override
                public void onResponse(Call<universalResponseData> call, Response<universalResponseData> response) {
                    if(response.body()==null){
                        kProgressHUD.dismiss();
                        Toasty.error(mContext,"服务器异常，提交数据出错！").show();
                        return;
                    }

                    if(response.body().getState() == 0){
                        kProgressHUD.dismiss();
                        TastyToast.makeText(mContext, "评论成功", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                        setResult(0);
                        finish();
                    }else {
                        kProgressHUD.dismiss();
                        TastyToast.makeText(mContext, "服务器异常，评论失败", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }

                }

                @Override
                public void onFailure(Call<universalResponseData> call, Throwable t) {
                    kProgressHUD.dismiss();
                    Toasty.error(mContext,"评论失败，请稍候重试！").show();
                }
            });

        }
    };




}
