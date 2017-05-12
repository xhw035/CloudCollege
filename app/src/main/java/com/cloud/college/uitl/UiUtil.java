package com.cloud.college.uitl;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;


public final class UiUtil {

    public static int dip2px(Context context, double dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5);
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    //收起软键盘
    public static void hideSoftInput(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    //弹起软键盘
    public static void showSoftInput(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); //开关软件键盘
        /* 下面的都不生效
        inputManager.showSoftInput(editTextComm, 0);
        inputManager.showSoftInputFromInputMethod(getWindow().getDecorView().getWindowToken(),0);*/
    }


}