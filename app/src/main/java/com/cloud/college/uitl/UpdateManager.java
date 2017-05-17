package com.cloud.college.uitl;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class UpdateManager {

    protected static String updateUrl="http://123.207.237.185:8080/edu/home/android/updatejson/download";

	Context mContext;
	String  downApkUrl;
	String updateDesc; //有更新时的，更新的内容描述
	int  oldVersionCode; //当前软件版本号
	String	dialogTitle="";//更新对话框显示的标题
	Boolean isAutoCheck=false; //标记是自动检测更新，还是点击检测更新
	Boolean notPrompt=false;//标记是否不再提示自动更新，默认提示
	Boolean isForced =false;//标记更新的重要程度，是否是必须更新
	SharedPreferences sp;
	
	//点击检测更新所用构造方法
	public UpdateManager(Context context)
	{
		this.mContext = context;
	}
	
	//自动检测更新所用构造方法
	public UpdateManager(Context context,Boolean auto)
	{
		this.mContext = context;
		isAutoCheck=auto;
	}
	
	// 获取当前软件版本名称，显示给用户看
	public static  String getVersionName(Context context) {
		String versionName ="";
		try {
			String packageName = context.getPackageName();
			versionName = context.getPackageManager().getPackageInfo(packageName, 0).versionName;
			//System.out.println("当前应用包名为："+packageName);
			System.out.println("当前应用的版本名称为："+versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}
	
	//获取当前软件版本号，用于比较
    public static int getVersionCode(Context context) {
		int versionCode =1;
		try {
			String packageName = context.getPackageName();
			versionCode = context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
			//System.out.println("包名为："+packageName);
			System.out.println("当前软件的版本号为："+versionCode);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}
	
	//判断是要更新
	public boolean isUpdate() {
		// 获取当前软件版本
		oldVersionCode = getVersionCode(mContext);
		
		// 开启线程异步获取最新版本
		GetVersionInfoThread thread = new GetVersionInfoThread(updateUrl);
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (thread.newVersionCode!=0) {
				downApkUrl=thread.downloadUrl;
				updateDesc=thread.updateDesc;
				String newVersionName=thread.newVersionName;
				int newVersionCode=thread.newVersionCode;
				
				//1.新版本号比旧版版本号大10，强制更新
				if (newVersionCode-oldVersionCode>10) {
					dialogTitle="新版本:"+newVersionName+"有特别重大更新!";
					isForced =true;
					return true;
				}else
				//2.新版本号比旧版版本号大5，建议更新
				if (newVersionCode-oldVersionCode>5) {
					dialogTitle="新版本:"+newVersionName+"有重大更新!";
					return true;
				 }else
				 //3.新版本号比旧版版本号大，但小于5
				 if (newVersionCode-oldVersionCode>0) {
					 dialogTitle="发现新版本:"+newVersionName;
						return true;
				 }else
				 //4.其他都是无需更新
				 {
					return false;
				 }
		}
		//没有获取到版本信息，也不给提示
		else {
			return false;
		}
	}

	 //根据选择了不再提示，来返回是否是否显示红点提示
     public boolean isShowRedDot() {
         sp=mContext.getSharedPreferences("config", mContext.MODE_PRIVATE);
         notPrompt=sp.getBoolean("notPrompt", false);
         if(notPrompt&&isUpdate())
             return true;
         else
             return false;
     }

	 //检测软件更新的核心方法
	public void checkUpdate(boolean auto)
	{
        isAutoCheck=auto;
		sp=mContext.getSharedPreferences("config", mContext.MODE_PRIVATE);
		notPrompt=sp.getBoolean("notPrompt", false);
		//如果是自动更新并且之前点击过"不再提示"，则什么都不做。
		if (isAutoCheck&&notPrompt) {
			return;
		}
		else //否则，不管点击更新还是自动检测更新，都需要判断是否需要更新
			if (isUpdate())//判断是要更新
			{
				// 需要跟新时，显示提示对话框
				showNoticeDialog();
			} else {	//当前是最新版本不需要更新，且又是自动检测，则不给任何提示
				if (!isAutoCheck) {
				    Toast.makeText(mContext, "当前已是最新版本："+getVersionName(mContext)+"，无需更新！", Toast.LENGTH_LONG).show();
				 }
			}
	}
	

	//提示更新对话框
	private void showNoticeDialog() {
			if (isForced) {
				//特别重大更新，显示必须更新对话框
				forceUpdatedialog();
			}else
			if (isAutoCheck) {
				//如果是自动更新，显示对应对话框
				autoUpdatedialog();
			}
			else
			{	
				//如果是点击更新，显示对应对话框
				clickUpdatedialog();
			}
	}
	
	//必须更新对话框
	private void forceUpdatedialog() {
		// 构造对话框
	AlertDialog dialog = new Builder(mContext,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
	.setTitle(dialogTitle)
	.setMessage(updateDesc)
	//必须更新
	.setPositiveButton("立即更新", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{	
				Toast.makeText(mContext, "请注意您的流量，建议您在WiFi情况下更新！", Toast.LENGTH_SHORT).show();
				//调用浏览器下载
				Uri uri = Uri.parse(downApkUrl);
				Intent intent = new Intent(Intent.ACTION_VIEW,uri);
				mContext.startActivity(intent);
				dialog.dismiss();
                android.os.Process.killProcess(android.os.Process.myPid());// 关闭进程
			}
		}).setNegativeButton("退出应用", new OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                    android.os.Process.killProcess(android.os.Process.myPid());// 关闭进程
                }
            }).create();
	 dialog.setCancelable(false);
     // dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
	 dialog.show();
		
	}

	//自动更新的对话框
	private void autoUpdatedialog() {

		// 构造对话框
		new Builder(mContext,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
		.setTitle(dialogTitle)
		.setMessage(updateDesc)
		// 更新
		.setPositiveButton("立即更新", new OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{	
					Toast.makeText(mContext, "请注意您的流量，建议您在WiFi情况下更新！", Toast.LENGTH_SHORT).show();
					//调用浏览器下载
					Uri uri = Uri.parse(downApkUrl);
					Intent intent = new Intent(Intent.ACTION_VIEW,uri);
					mContext.startActivity(intent);
					dialog.dismiss();
				}
			})
			//不再提示
			.setNeutralButton("不再提示", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					sp.edit().putBoolean("notPrompt", true).commit();
					dialog.dismiss();
				}
			})
			//暂不更新
		  .setNegativeButton("暂不更新", new OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
				}
			}).create().show();
	
		
	}

	//点击更新的对话框
	private void clickUpdatedialog() {
		// 构造对话框
		new Builder(mContext,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
		.setTitle(dialogTitle)
		.setMessage(updateDesc)
		// 更新
		.setPositiveButton("立即更新", new OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{	
					Toast.makeText(mContext, "请注意您的流量，建议您在WiFi情况下更新！", Toast.LENGTH_SHORT).show();
					//调用浏览器下载
					Uri uri = Uri.parse(downApkUrl);
					Intent intent = new Intent(Intent.ACTION_VIEW,uri);
					mContext.startActivity(intent);
					dialog.dismiss();
				}
			})
			// 稍后更新
		  .setNegativeButton("稍后更新", new OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
				}
			}).create().show();
	}
	
}

class GetVersionInfoThread extends Thread {

	private String result=null;
	private String address=null;

	public  int newVersionCode=0;
	public  String newVersionName=null;
	public  String downloadUrl =null;
	public  String updateDesc=null;

	public GetVersionInfoThread(String url) {
		address=url;
	}

	@Override
	public void run()
	{

		try {
			URL url=new URL(address);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(3000);
			conn.setRequestMethod("GET");
			conn.connect();

			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader bfr=new BufferedReader(isr);

			String s;
			StringBuffer sb = new StringBuffer();
			while ((s=bfr.readLine())!=null) {
				sb.append(s);
			}
			result=sb.toString();
			bfr.close();
			isr.close();
			is.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (result!=null) {
			//读完了开始解析
			try {
				JSONObject json =new JSONObject(result);
				newVersionCode=json.getInt("newVersionCode");
				newVersionName=json.getString("newVersionName");
                downloadUrl =json.getString("downloadUrl");
				updateDesc=json.getString("updateDesc");

				System.out.println("最新版本号为："+newVersionCode);
				System.out.println("最新版本名称为："+newVersionName);
				System.out.println("下载链接为："+downloadUrl);
				System.out.println("更新描述信息为："+updateDesc);
			} catch (JSONException e) {
				System.out.println("获取更新信息，json解析失败！");
				e.printStackTrace();
			}
		}else{
			return;
		}

	}


}

