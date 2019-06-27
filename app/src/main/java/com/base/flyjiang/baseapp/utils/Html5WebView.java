package com.base.flyjiang.baseapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Html5WebView extends WebView {

    private Context mContext;

    public Html5WebView(Context context) {
        super(context);
        mContext = context;
        init();
        Log.d("webs", "Html5WebView:33333333333333 ");
    }

    public Html5WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
        Log.d("webs", "Html5WebView:11111111111111 ");
    }

    public Html5WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d("webs", "Html5WebView:22222222222222 ");
    }

    private void init() {
        WebSettings mWebSettings = getSettings();
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setLoadsImagesAutomatically(true);

        //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportMultipleWindows(true);
        //缓存数据
        saveData(mWebSettings);
        newWin(mWebSettings);
        setWebChromeClient(webChromeClient);
        setWebViewClient(webViewClient);
    }

    /**
     * 多窗口的问题
     */
    private void newWin(WebSettings mWebSettings) {
        //html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
        //然后 复写 WebChromeClient的onCreateWindow方法
        mWebSettings.setSupportMultipleWindows(false);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    /**
     * web view 有缓存啊
     //设置 缓存模式
     webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
     // 开启 DOM storage API 功能
     webView.getSettings().setDomStorageEnabled(true);
     这样你就可以在返回前一个页面的时候不刷新了

     作者：漫漫逝去
     链接：https://www.zhihu.com/question/40943177/answer/88907556
     来源：知乎
     著作权归作者所有，转载请联系作者获得授权。
     * HTML5数据存储
     * 首先来了解一下webview加载网页的几个模式，即websetting中设置的加载模式。websetting.setCacheMode( int  mode)。
     * <p/>
     * LOAD_CACHE_ELSE_NETWORK：只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
     * <p/>
     * LOAD_CACHE_ONLY：只加载缓存数据，如果没有缓存数据，就出现加载失败；
     * <p/>
     * LOAD_DEFAULT：默认加载方式， 根据cache-control决定是否从网络上取数据；
     * <p/>
     * LOAD_NO_CACHE：不使用缓存，只从网络获取数据；
     * <p/>
     * LOAD_CACHE_NORMAL: API level 17中已经废弃, 从API level 11开始作用同LOAD_DEFAULT模式
     */
    private void saveData(WebSettings mWebSettings) {
        //有时候网页需要自己保存一些关键数据,Android WebView 需要自己设置
        Log.d("webs:", "saveData----------------HTML5数据存储");
        if (NetStatusUtil.isConnected(mContext)) {
            Log.d("webs:", "saveData----------------/根据cache-control决定是否从网络上取数据。");
            mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
        } else {
            Log.d("webs:", "saveData----------------//没网，则从本地获取，即离线加载");
            mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }

        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setAppCacheEnabled(true);
        String appCachePath = mContext.getCacheDir().getAbsolutePath();
        Log.d("webs", "saveData: 缓存地址" + appCachePath);
        mWebSettings.setAppCachePath(appCachePath);
    }

    WebViewClient webViewClient = new WebViewClient() {
        /**
         * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
            Log.d("webs:", "shouldOverrideUrlLoading----------------" + url);
            return false;
        }
    };

    WebChromeClient webChromeClient = new WebChromeClient() {

        //=========HTML5定位==========================================================
        //需要先加入权限
        //<uses-permission android:name="android.permission.INTERNET"/>
        //<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
        //<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }

        @Override
        public void onGeolocationPermissionsHidePrompt() {
            super.onGeolocationPermissionsHidePrompt();
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);//注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
        //=========HTML5定位==========================================================


        //=========多窗口的问题==========================================================
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            Log.d("webs", "onCreateWindow:多窗口的问题-------------- ");
            WebViewTransport transport = (WebViewTransport) resultMsg.obj;
            transport.setWebView(view);
            resultMsg.sendToTarget();
            return true;
        }
        //=========多窗口的问题==========================================================
    };
}
