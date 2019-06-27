package com.carking.quotationlibrary.base;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.carking.quotationlibrary.config.ConfigFresco;
import com.carking.quotationlibrary.utils.CrashUtil;
import com.carking.quotationlibrary.utils.FileUtil;
import com.carking.quotationlibrary.utils.SharepreferenceUtil;
import com.carking.quotationlibrary.utils.ToastUtil;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.xutils.x;

/**
 * 作者：flyjiang
 * 时间: 2015/5/26 16:30
 * 邮箱：caiyoufei@looip.cn
 * 说明: 
 */
public class BaseApplication extends MultiDexApplication {
    private static Context mApplicationContext;

    @Override
    public void onCreate() {
        MultiDex.install(getApplicationContext());
        mApplicationContext = this;
        super.onCreate();


        //基础信息初始化
        initBase();
        x.Ext.init(this);
        //图片下载初始化
        initFresco();
    }

    public static Context getmApplicationContext() {
        return mApplicationContext;
    }

    /**
     * 各种初始化的信息
     */
    private void initBase() {
        //初始化打印日志
/*        if (BuildConfig.DEBUG) {
            // 开发模式
//            CLog.setLogLevel(CLog.LEVEL_VERBOSE);
        } else {
            CLog.setLogLevel(CLog.LEVEL_ERROR);
        }*/
        //初始化文件夹
        FileUtil.init(this);
        //初始化闪退日志
        CrashUtil.getInstance().init(this);
        //初始化SharedPreferences
        SharepreferenceUtil.init(this);
        //初始化Toast
        ToastUtil.init(this);
    }

    /**
     * Fresco的初始化
     */
    private void initFresco() {
        //如果不需要用OKTHHP下载，则执行
        Fresco.initialize(this, ConfigFresco.getImagePipelineConfig(this));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
