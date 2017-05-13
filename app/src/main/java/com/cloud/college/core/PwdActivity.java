package com.cloud.college.core;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloud.college.R;
import com.cloud.college.network.universalResponseData;
import com.cloud.college.uitl.MD5;
import com.cloud.college.uitl.MyApplication;
import com.cloud.college.uitl.NetworkUtil;
import com.kaopiz.kprogresshud.KProgressHUD;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PwdActivity extends Activity {

    private final Activity mContext = PwdActivity.this;
    private ImageView back;
	private EditText editText1;
	private EditText editText2;
	private ImageButton deleteBtn1;
	private ImageButton deleteBtn2;
	private Button submitBtn;

	protected String phone="";
	//标记不同操作，0注册，1修改密码，2忘记密码
	int type=0;

	private TextView title;
	protected String password1;
	protected String password2;
	protected String md5Password;
    private KProgressHUD kProgressHUD;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pwd);
		
		Intent intent = getIntent();
        phone=intent.getExtras().getString("phone");
        type=intent.getExtras().getInt("type");

		initView();
		initEvent();
	}

	private void initView() {
		back=	(ImageView) findViewById(R.id.pwdBack);
		title=	(TextView) findViewById(R.id.pwdTitle);
		
		editText1=	(EditText) findViewById(R.id.pwd1);
		editText2=	(EditText) findViewById(R.id.pwd2);
		deleteBtn1=	(ImageButton) findViewById(R.id.pwd_delete_button1);
		deleteBtn2=	(ImageButton) findViewById(R.id.pwd_delete_button2);
		submitBtn=	(Button) findViewById(R.id.pwdButton);
		
		if(type==0){
			submitBtn.setText("注     册");
			title.setText("确认密码");
		}else if(type==1){
            submitBtn.setText("修改密码");
            title.setText("修改密码");
		}else if(type==2){
            submitBtn.setText("重置密码");
            title.setText("找回密码");

		}
		
	}

	private void initEvent() {
		//密码框1焦点改变，显示清除按钮1
		editText1.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!editText1.getText().toString().equals("")) {
					deleteBtn1.setVisibility(View.VISIBLE);
				}
			}
		});
		
		//清除按钮1,点击可清除密码框1的密码
		deleteBtn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editText1.setText("");
				deleteBtn1.setVisibility(View.GONE);
			}
		});
		
		//密码框2焦点改变，显示清除按钮2
		editText2.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!editText2.getText().toString().equals("")) {
					deleteBtn2.setVisibility(View.VISIBLE);
				}
			}
		});
		
		//清除按钮1,点击可清除密码框1的密码
		deleteBtn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editText2.setText("");
				deleteBtn2.setVisibility(View.GONE);
			}
		});
		
		
		//最下的按钮
		submitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				password1 = editText1.getText().toString();
				password2 = editText2.getText().toString();
				
				if (password1.trim().equals("")) {
                    Toasty.error(mContext, "您还没输入密码呢!").show();
                }else {
                    if (password2.trim().equals("")) {
                        Toasty.error(mContext, "您还没确认密码呢!").show();
                    } else if (!password2.trim().equals(password1.trim())) {
                        Toasty.error(mContext, "您两次输入的密码不一样!").show();
                    } else if (NetworkUtil.isNetworkAvailable(mContext)) {
                        //注册,找回密码,修改密码
                        md5Password = MD5.getMD5String(password1.trim().getBytes());
                        submitData(mContext,phone, md5Password,type);
                    } else {
                        Toasty.error(mContext, "网络异常，请稍候重试!").show();
                    }
                }

			}
			
		});
		
		//返回监听
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		
	}

	protected void submitData(final Activity context , String phone, String md5Password, final int type) {

        kProgressHUD = KProgressHUD.create(context)
        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        .setLabel("正在加载...")
        .setWindowColor(Color.parseColor("#992BC17A"))
        .setAnimationSpeed(2)
        .setDimAmount(0.5f)
        .show();

        if(!NetworkUtil.isNetworkAvailable(mContext)){
            kProgressHUD.dismiss();
            Toasty.error(mContext, "网络异常，请稍候重试！").show();
            return;
        }

        Call<universalResponseData> pwdCall = MyApplication.getMyService().handlePassword(phone, md5Password, type);
        pwdCall.enqueue(new Callback<universalResponseData>() {
            @Override
            public void onResponse(Call<universalResponseData> call, Response<universalResponseData> response) {
                if(response.body()==null){
                    kProgressHUD.dismiss();
                    Toasty.error(context,"服务器异常，请稍候重试").show();
                    context.finish();
                    return;
                }

                //1.注册
                if(type==0&&response.body().getState() == 0) {
                    kProgressHUD.dismiss();
                    Toasty.success(context,"注册成功，可前往登录").show();
                    context.finish();
                    return;
                } else
                if(type==0&&response.body().getState() == 1) {
                    kProgressHUD.dismiss();
                    Toasty.error(context,"注册失败，该手机号已被注册！").show();
                    context.finish();
                    return;
                }

                //2.修改密码
                if(type==1&&response.body().getState() == 0) {
                    kProgressHUD.dismiss();
                    Toasty.success(context,"修改密码成功").show();
                    context.finish();
                    return;
                } else
                if(type==1&&response.body().getState() == 1) {
                    kProgressHUD.dismiss();
                    Toasty.error(context,"修改失败，该手机号还未注册！").show();
                    context.finish();
                    return;
                }

                //2.忘记密码
                if(type==2&&response.body().getState() == 0) {
                    kProgressHUD.dismiss();
                    Toasty.success(context,"重置密码成功，可前往登录").show();
                    context.finish();
                    return;
                } else
                if(type==2&&response.body().getState() == 1) {
                    kProgressHUD.dismiss();
                    Toasty.error(context,"重置密码失败，该手机号还未注册！").show();
                    context.finish();
                    return;
                }

                //返回State为其他值
                kProgressHUD.dismiss();
                Toasty.error(context,"服务器异常，请稍候重试！").show();
                context.finish();
                return;

            }

            @Override
            public void onFailure(Call<universalResponseData> call, Throwable t) {
                kProgressHUD.dismiss();
                Toasty.error(mContext, "网络异常，操作失败!").show();
            }
        });

	}


}
