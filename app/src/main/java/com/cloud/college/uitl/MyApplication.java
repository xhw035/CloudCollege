package com.cloud.college.uitl;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;

import com.cloud.college.R;
import com.cloud.college.network.MyService;
import com.hss01248.dialog.MyActyManager;
import com.hss01248.dialog.StyledDialog;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import cn.smssdk.SMSSDK;
import es.dmoral.toasty.Toasty;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: xiao(xhw219@163.com)
 * Date: 2017-02-26 01:54
 * DESC:应用一启动就执行，用于初始化。
 */

public class MyApplication extends Application{

    //public static String serverHost = "192.168.191.1";
    public static String serverHost = "123.207.237.185";
    //public static String serverHost = "xhw123.cn";
    public static boolean refreshCollection = true;
    public static boolean refreshMine = true;
    private static Retrofit mRetrofit;


    @Override
    public void onCreate() {
        super.onCreate();
        SMSSDK.initSDK(this, "1dd40f4a6d5ab", "a64396ea251971034f21444655ef9cad");
        initImageLoader();
        StyledDialog.init(this);
        registCallback();
        initRetrofit();
        //initTest();
    }



    //获取Retrofit服务
    public static MyService getMyService(){
        return mRetrofit.create(MyService.class);
    }

   ///初始化网络图片缓存库,详细的配置
   private void initImageLoader(){
       //设置显示的图片的各种格式
       DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
               .showImageOnLoading(R.drawable.img_loading)
               .showImageForEmptyUri(R.drawable.img_deafult)
               .showImageOnFail(R.drawable.img_load_fail)
               .resetViewBeforeLoading(false)  // default
               .delayBeforeLoading(1000)
               .cacheInMemory(true) // default
               .cacheOnDisk(true) // default
               //.preProcessor(...)
               //.postProcessor(...)
               //.extraForDownloader(...)
               .considerExifParams(false) // default
               .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//图片会缩放到目标大小
               .bitmapConfig(Bitmap.Config.ARGB_8888) // default
               //.decodingOptions(...)
               .displayer(new SimpleBitmapDisplayer()) // default
               .handler(new Handler()) // default
               .build();

       // 设置缓存的目录地址
       File cacheDir = StorageUtils.getOwnCacheDirectory(this,"CloudCollege/Cache");

       ImageLoaderConfiguration config = new ImageLoaderConfiguration
               .Builder(this)
               .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
               .diskCacheExtraOptions(480, 800, null)
               //.taskExecutor(...)
               //.taskExecutorForCachedImages(...)
               .threadPoolSize(3) // default
               .threadPriority(Thread.NORM_PRIORITY - 2) // default
               .tasksProcessingOrder(QueueProcessingType.FIFO) // default
               .denyCacheImageMultipleSizesInMemory()
               .memoryCache(new LruMemoryCache(2 * 1024 * 1024))// default
               .memoryCacheSize(2 * 1024 * 1024)
               .memoryCacheSizePercentage(13) // default
               .diskCache(new UnlimitedDiskCache(cacheDir)) // 自定义缓存路径
               .diskCacheSize(50 * 1024 * 1024)
               .diskCacheFileCount(100)
               .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
               //.imageDownloader(new BaseImageDownloader(this)) // default
               // connectTimeout (5 s), readTimeout (30 s)超时时间
               .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000))
               .imageDecoder(new BaseImageDecoder(true))
               .defaultDisplayImageOptions(defaultOptions)
               .writeDebugLogs()
               .build();

       ImageLoader.getInstance().init(config);
   }

   //在activity生命周期callback中拿到顶层activity引用
    private void registCallback() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                MyActyManager.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    //配置Retrofit
    private void initRetrofit() {
        //先配置OkHttpClient客户端
        OkHttpClient client = new OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .connectTimeout(15, TimeUnit.SECONDS)
        .build();

        //获取实例
        mRetrofit = new Retrofit.Builder()
        //设置OKHttpClient,如果不设置会提供一个默认的
        .client(client)
        //设置baseUrl
        .baseUrl("http://"+serverHost+":8080")
//        .baseUrl("http://"+serverHost)
        //添加Gson转换器
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    }



    private void initTest() {
        //SpUitl.setUserID(getApplicationContext(),"297e386a56410ddd0156411a94c50021");
        SpUitl.setFirstTime(true);
    }

    //检查更新
    public static void checkUpdate(Context context, boolean isAuto){

        if(NetworkUtil.isNetworkAvailable(context)){
            new UpdateManager(context).checkUpdate(isAuto);
        }else if (!isAuto){
            Toasty.error(context,"网络异常，无法获取更新信息！").show();
        }
    }
}
