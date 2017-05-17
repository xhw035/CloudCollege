package com.cloud.college.uitl;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.cloud.college.network.UserInfoData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.hss01248.dialog.StyledDialog.context;

/**
 * Created by xiao on 2017/5/8.
 * SharedPreferences工具类
 */

public class SpUitl {

    public  static String[] getHistoryData(Context context){
        SharedPreferences sp = context.getSharedPreferences("history", Context.MODE_PRIVATE);
        int size = sp.getInt("size", 0);
        if(size == 0) {
            return null;
        }
        String[] arr = new String[size];
        for (int i = size-1; i >=0; i--) {
            arr[size-1-i] = sp.getString(i+"","");
        }
        return arr;
    }

    public  static  void  addHistoryData(Context context,String str){
        SharedPreferences sp = context.getSharedPreferences("history", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String[] arr = getHistoryData(context);

        ArrayList<String> list ;
        if (arr!=null)
            list =new ArrayList(Arrays.asList(arr));
        else
            list = new ArrayList();
        Iterator<String> it = list.iterator();
        while(it.hasNext()){
            String s = it.next();
            if(s.equals(str)){
                it.remove();
            }
        }
        for (int i = 0; i < list.size(); i++) {
            editor.putString(i+"",list.get(i));
        }

        editor.putString(list.size()+"",str);
        editor.putInt("size",list.size()+1);
        editor.commit();
    }

    public static  void clearHistoryData(Context context){
        SharedPreferences sp = context.getSharedPreferences("history", Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }

    public  static  void addBannerDataCache(Context context, List<String> list){
        SharedPreferences sp = context.getSharedPreferences("bannerDataCache", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
        for (int i = 0; i < list.size(); i++) {
            editor.putString(i+"",list.get(i));
        }
        editor.putInt("size",list.size());
        editor.commit();
    }

    public  static  List<String> getBannerDataCache(Context context){
        SharedPreferences sp = context.getSharedPreferences("bannerDataCache", Context.MODE_PRIVATE);
        List<String> list = new ArrayList<String>();
        int size = sp.getInt("size",0);
        if(size == 0){
            return null;
        }else {
            for (int i = 0; i < size; i++) {
                list.add(sp.getString(i + "", ""));
            }
            return list;
        }
    }

    public  static  void addPopularCourseCache(Context context, List<String> list){
        SharedPreferences sp = context.getSharedPreferences("popularCourseCache", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
        for (int i = 0; i < list.size(); i++) {
            editor.putString(i+"",list.get(i));
        }
        editor.putInt("size",list.size());
        editor.commit();
    }

    public  static  List<String> getPopularCourseCache(Context context){
        SharedPreferences sp = context.getSharedPreferences("popularCourseCache", Context.MODE_PRIVATE);
        List<String> list = new ArrayList<String>();
        int size = sp.getInt("size",0);
        if(size == 0){
            return null;
        }else {
            for (int i = 0; i < size; i++) {
                list.add(sp.getString(i + "", ""));
            }
            return list;
        }
    }

    public static boolean isAutoPlay(Context context){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getBoolean("autoPlay", false);
    }

   public static void setAutoPlay(Context context,boolean arg){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("autoPlay", arg).commit();
    }

    public static String getUserID(Context context){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getString("userID", "");
    }

    public static void setUserID(Context context,String userID){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("userID", userID).commit();
    }

    public static boolean isLogin(Context context){
        return !TextUtils.isEmpty(getUserID(context));
    }

    public static String getPhone(Context context){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getString("phone", "");
    }

    public static void setPhone(Context context,String phone){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("phone", phone).commit();
    }

    public  static  void addUserInfoCache(Context context, UserInfoData data){
        SharedPreferences sp = context.getSharedPreferences("userInfoCache", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
        editor.putString("nickname",data.getNickname());
        editor.putString("avatar",data.getAvatar());
        editor.commit();

    }

    public  static  UserInfoData getUserInfoCache(Context context){
        SharedPreferences sp = context.getSharedPreferences("userInfoCache", Context.MODE_PRIVATE);
        UserInfoData data = new UserInfoData();
        data.setNickname(sp.getString("nickname",""));
        data.setAvatar(sp.getString("avatar",""));
        return data;
    }

    public  static  boolean isFirstTime(Context context){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getBoolean("isFirstTime", true);
    }

    public  static  void setFirstTime(boolean notFirst){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isFirstTime", notFirst).commit();
    }

    public static int getLastVersionCode(Context context){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getInt("versionCode", 0);
    }

    public static void setLastVersionCode(Context context,int code){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("versionCode", code).commit();
    }

}
