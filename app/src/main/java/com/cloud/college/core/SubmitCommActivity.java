package com.cloud.college.core;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.cloud.college.R;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by xiao on 2017/5/3.
 */

public class SubmitCommActivity extends Activity {

    private Unbinder mUnbinder;
    @BindView(R.id.cancelComm) ImageView cancelComm;
    @BindView(R.id.submitComm) ImageView submitComm;

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

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();

    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            StyledDialog.buildIosAlert( "title", "content",  new MyDialogListener() {
                @Override
                public void onFirst() {
                    finish();
                    //SubmitCommActivity.this.overridePendingTransition(R.anim.activity_close,0);
                }

                @Override
                public void onSecond() {
                }

            }).setBtnText("sure","cancle").show();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @OnClick(R.id.cancelComm)
    public void cancel(View view){
        finish();
    }
}
