package com.dl7.player.media;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.OrientationEventListener;


/**
 * Created by xiaohuawen on 2017/3/20.
 */

class PlayerOrientoinListener extends OrientationEventListener {

    Activity mContext=null;

    public PlayerOrientoinListener(Activity context) {
        super(context);
        mContext=context;
    }


    @Override
    public void onOrientationChanged(int orientation) {
        int screenOrientation = mContext.getResources().getConfiguration().orientation;
        if (((orientation >= 0) && (orientation < 45)) || (orientation > 315)) {//设置竖屏
            if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    && orientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                    &&IjkPlayerView.originalOrientoin!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                //转了一次初始屏幕方向作废
                IjkPlayerView.originalOrientoin=-1;
            }
        } else if (orientation > 225 && orientation < 315) { //设置横屏
            if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    &&IjkPlayerView.originalOrientoin!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                //转了一次初始屏幕方向作废
                IjkPlayerView.originalOrientoin=-1;
            }
        } else if (orientation > 45 && orientation < 135) {// 设置反向横屏
            if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                    &&IjkPlayerView.originalOrientoin!= ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                //转了一次初始屏幕方向作废
                IjkPlayerView.originalOrientoin=-1;
            }
        } else if (orientation > 135 && orientation < 225) {
            if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                    &&IjkPlayerView.originalOrientoin!= ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
                mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                //转了一次初始屏幕方向作废
                IjkPlayerView.originalOrientoin=-1;
            }
        }
    }
}
