package com.cloud.college.uitl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

/**
 * Created by xiao on 2017/5/9.
 */

public class networkUtil {

    public static boolean isNetworkAvailable(final Context context){
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        /*final android.net.NetworkInfo wifi =cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile =cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(wifi.isAvailable()||mobile.isAvailable())
            return true;
        else
            return false;*/

        if (cm == null) {
            return false;
        } else {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        //在网络连接的情况下测试能否ping通
                        Runtime runtime = Runtime.getRuntime();
                        try {
                            //Process iProcess = runtime.exec("/system/bin/ping -c 1 "+MyApplication.serverHost);
                            Process iProcess = runtime.exec("/system/bin/ping -c 1 www.baidu.com");
                            int  exitValue = iProcess.waitFor();
                            return (exitValue == 0);
                        } catch (IOException e){ e.printStackTrace(); }
                        catch (InterruptedException e) { e.printStackTrace(); }
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) {
            return false;
        } else {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
            return false;
        }
    }

    public static boolean isWifiConnected(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

}
