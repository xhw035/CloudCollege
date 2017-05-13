package com.cloud.college.core;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cloud.college.R;
import com.cloud.college.network.universalResponseData;
import com.cloud.college.uitl.MD5;
import com.cloud.college.uitl.MyApplication;
import com.cloud.college.uitl.NetworkUtil;
import com.cloud.college.uitl.SpUitl;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hss01248.dialog.StyledDialog.context;

public class LoginActivity extends Activity {
	
    @BindView(R.id.loginBack) ImageView back;
    @BindView(R.id.loginAccount) EditText account;
    @BindView(R.id.loginPwd) EditText password;
    @BindView(R.id.loginButton) Button loginBtn;

    @BindView(R.id.loginIndicator) ImageView indicator;
    @BindView(R.id.loginAccount_delete_button) ImageButton deleteAccount;
    @BindView(R.id.loginListview) ListView accountListview;

    @BindView(R.id.forgetPwd) TextView forgetPwd;
    @BindView(R.id.loginRegistAccount) TextView register;

    @BindView(R.id.loginQQ) ImageView qq;
    @BindView(R.id.loginWeixin) ImageView weixin;
    @BindView(R.id.loginWeibo) ImageView weibo;

    private Unbinder mUnbinder;
    private String phone;
    private String pwd;
    private String md5Password;

    private Intent intent;
    //标记是否需要返回给调用的activity,只有点击头像的时候需要。
    private Boolean isBack;
    private KProgressHUD kProgressHUD;
    private LoginActivity mContext;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
        mUnbinder = ButterKnife.bind(this);
        intent=getIntent();
		isBack=intent.getExtras().getBoolean("isBack");

		initView();
		initEvent();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
        mUnbinder.unbind();
	}

	private void initView() {

	}

	private void initEvent() {

        //返回监听
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

		//当账号输入框焦点改变，值不为空，显示清除账号按钮
        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!account.getText().toString().equals("")) {
                    deleteAccount.setVisibility(View.VISIBLE);
                }
            }
        });


		//清除账号按钮可见时，可以一键删除输入的账号
		deleteAccount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				account.setText("");
                password.setText("");
				deleteAccount.setVisibility(View.GONE);
			}
		});

		//点击登录按钮
        mContext = LoginActivity.this;
        loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				phone =account.getText().toString();
				pwd=password.getText().toString();
				md5Password= MD5.getMD5String(pwd.getBytes());

				if(NetworkUtil.isNetworkAvailable(mContext))
					if(phone.trim().equals(""))
                        Toasty.info(mContext, "您当还没输入用户名呢！").show();
					else if(pwd.trim().equals(""))
                        Toasty.info(mContext, "您当还没输入密码呢！").show();
					else
					    login(mContext,phone,md5Password);
				else
                    Toasty.error(mContext,"您当前的网络不可用！").show();
			}

		});

		//点击“注册账号”
		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                verifyPhone(mContext,0);
			}
		});

        //点击“忘记密码”
        forgetPwd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPhone(mContext,2);
            }
        });



	}



    private void login(final Activity context, final String phone, String md5Password) {
        kProgressHUD = KProgressHUD.create(context)
        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        .setLabel("正在登录...")
        .setWindowColor(Color.parseColor("#992BC17A"))
        .setAnimationSpeed(2)
        .setDimAmount(0.5f)
        .show();

        if(!NetworkUtil.isNetworkAvailable(mContext)){
            kProgressHUD.dismiss();
            Toasty.error(mContext, "网络异常，请稍候重试！").show();
            return;
        }

        Call<universalResponseData> loginCall = MyApplication.getMyService().handleLogin(phone, md5Password);
        loginCall.enqueue(new Callback<universalResponseData>() {
            @Override
            public void onResponse(Call<universalResponseData> call, Response<universalResponseData> response) {
                if (response.body() == null) {
                    kProgressHUD.dismiss();
                    Toasty.error(context, "服务器异常，请稍候重试！").show();
                    context.finish();
                    return;
                }

                //登录成功，存入userID,用户名phone,根据isBack返回
                if (response.body().getState() == 0) {
                    kProgressHUD.dismiss();
                    SpUitl.setUserID(mContext, response.body().getDesc().trim());
                    SpUitl.setPhone(mContext, phone);
                    //"我的"点击头像区，需要返回并刷新
                    if (isBack)
                        setResult(0);
                    context.finish();
                    return;
                }

                if (response.body().getState() == 1) {
                    kProgressHUD.dismiss();
                    Toasty.error(context, "登录失败，手机号还未注册，请先注册").show();
                    return;
                }

                if (response.body().getState() == 2) {
                    kProgressHUD.dismiss();
                    Toasty.error(context, "登录失败，密码不正确").show();
                    return;
                }

                //返回其他值
                kProgressHUD.dismiss();
                Toasty.error(context, "登录失败，密码不正确").show();
                return;
            }

            @Override
            public void onFailure(Call<universalResponseData> call, Throwable t) {
                kProgressHUD.dismiss();
                Toasty.error(mContext, "服务器异常，请稍候重试！").show();
            }
        });


    }

    public static void verifyPhone(final Activity mContext, final int type) {
        //打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");

                    //提交验证码成功，此时已经验证成功了
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                        Intent intent = new Intent(mContext,PwdActivity.class);
                        intent.putExtra("phone", phone);
                        intent.putExtra("type", type);
                        mContext.startActivity(intent);
                    }
                }
            }
        });
        registerPage.show(context);

    }


}
