package com.base.flyjiang.baseapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.base.BaseDemoActivity;
import com.base.flyjiang.baseapp.bean.WelcomeBean;
import com.base.flyjiang.baseapp.callback.StringDialogCallback;
import com.carking.quotationlibrary.utils.FrescoUtil;
import com.carking.quotationlibrary.utils.JsonUtil;
import com.carking.quotationlibrary.utils.SharepreferenceUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.okgo.OkGo;

import java.io.File;

import in.srain.cube.util.CLog;

/**
 * 作者：flyjiang
 * 时间: 2015/8/18 10:29
 * 说明: 欢迎页
 */
public class WelcomeActivity extends BaseDemoActivity {
    /**
     * 欢迎页图片
     */
    private SimpleDraweeView mImageView;
    /**
     * 旧图保存地址KEY
     */
    private final String FILE_OLD_URL = "FILE_OLD_URL";
    /**
     * 新图保存地址KEY
     */
    private final String FILE_NEW_URL = "FILE_NEW_URL";
    /**
     * 是否开启欢迎页请求接口
     */
    private boolean isOpenWelcome = false;

    /**
     * 消息处理机制
     */
    protected Handler mHandler = new Handler(Looper.myLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            dealWithRequestResult(msg);
            return true;
        }
    });

    @Override
    public void onCreateBefore() {
        super.onCreateBefore();
        setStatusColor(getResources().getColor(R.color.transparent));
        setIsShowTitle(false);
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_welcome;
    }

    @Override
    public void init() {
        mImageView = (SimpleDraweeView) findViewById(R.id.welcome_image);
        /**如果需要采用接口,打开请求，修改地址和参数以及WelcomeBean即可*/
       if (isOpenWelcome) {
            requestWelcomeImage();
        }
        mHandler.sendEmptyMessageDelayed(0x170002, 2000);
    }

    /**
     * 请求欢迎页图片地址
     */
    private void requestWelcomeImage() {

        OkGo.<String>get("*****欢迎页的请求地址*****").execute(new StringDialogCallback(this) {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                mRequestResult = response.body();
                mHandler.sendEmptyMessage(0x172512);
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<String> response) {
                super.onError(response);
                mHandler.sendEmptyMessage(0x172513);
            }
        });

    }

    public void dealWithRequestResult(Message msg) {
        switch (msg.what) {
            //进入主页
            case 0x170002:
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            //第17个页面，第25号接口(具体等出接口再修改)，第一次接口请求消息，失败为1，成功为2，出错为3
            case 0x172511:
                CLog.e(TAG, "欢迎页请求失败");
                break;
            case 0x172512:
                if (!TextUtils.isEmpty(mRequestResult)) {
                    CLog.e(TAG, "欢迎页请求成功:" + mRequestResult);
                    dealWithWelcomeResult(mRequestResult);
                } else {
                    CLog.e(TAG, "欢迎页请求成功，但服务器返回数据为空");
                }
                break;
            case 0x172513:
                CLog.e(TAG, "欢迎页请求出错");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /**
     * 处理请求结果
     *
     * @param requestResult
     */
    private void dealWithWelcomeResult(String requestResult) {
        WelcomeBean welcomeBean = JsonUtil.json2Bean(requestResult, WelcomeBean.class);
        if (welcomeBean != null && welcomeBean.getData() != null && !TextUtils.isEmpty(welcomeBean.getData().getImageUrl())) {
            downloadImage(welcomeBean.getData().getImageUrl());
        } else {
            loadOldImage();
        }
    }

    /**
     * 加载图片
     */
    private void downloadImage(final String url) {
        //图片地址可用
        if (!TextUtils.isEmpty(url)) {
            //判断新图片是否存在
            File fileNew = FrescoUtil.getDefaultImageFile(this, url);
            //新图存在
            if (fileNew.exists()) {
                //将地址保存为新旧图地址
                SharepreferenceUtil.saveString(FILE_OLD_URL, url);
                SharepreferenceUtil.saveString(FILE_NEW_URL, url);
                mImageView.setImageURI(Uri.parse("file://" + fileNew.getAbsolutePath()));
                CLog.e(TAG, "加载新图");
            }
            //新图不存在
            else {
                //将地址保存为新图地址
                SharepreferenceUtil.saveString(FILE_NEW_URL, url);
                //下载新图
                downLoadNewImage(url);
                //加载旧图
                loadOldImage();
            }
        }
        //图片地址不可用
        else {
            loadOldImage();
        }
    }

    /**
     * 加载旧图
     */
    private void loadOldImage() {
        //获取旧图地址
        String oldUrl = SharepreferenceUtil.getString(FILE_OLD_URL);
        //旧图地址可用
        if (!TextUtils.isEmpty(oldUrl)) {
            //获取旧图文件
            File fileOld = FrescoUtil.getDefaultImageFile(mContext, oldUrl);
            //旧图文件存在
            if (fileOld.exists()) {
                mImageView.setImageURI(Uri.parse("file://" + fileOld.getAbsolutePath()));
                CLog.e(TAG, "加载旧图");
            }
            //旧图文件不存在
            else {
                //下载旧图
                downLoadNewImage(oldUrl);
                //加载默认图片
                loadDefaultImage();
            }
        }
        //旧图地址不可用
        else {
            loadDefaultImage();
        }
    }

    /**
     * 加载默认图片
     */
    private void loadDefaultImage() {
        //mImageView.setImageURI(Uri.parse("res://mipmap/" + R.mipmap.welcome_default));
        mImageView.setBackgroundResource(R.mipmap.guide_img1);
        CLog.e(TAG, "加载默认图");
    }

    /**
     * 下载新图
     *
     * @param url 图片地址
     */
    private void downLoadNewImage(final String url) {
        mImageView.setImageURI(Uri.parse(url));
    }
}
