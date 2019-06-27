package com.carking.quotationlibrary.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.widget.RemoteViews;

import com.carking.quotationlibrary.R;
import com.carking.quotationlibrary.downloader.MultiThreadDownloadUtil;
import com.carking.quotationlibrary.utils.FilepathUtil;
import com.carking.quotationlibrary.utils.NetworkUtil;
import com.carking.quotationlibrary.utils.ToastUtil;

import java.io.File;

import in.srain.cube.util.CLog;

/**
 * 作者：flyjiang
 * 时间: 2015/06月/日 09:53
 * 邮箱：caiyoufei@looip.cn
 * 说明: APP升级服务
 */
public class UpdateService extends Service implements Handler.Callback {
    private static final String TAG = "UpdateService";
    /**
     * 是否正在下载
     */
    public static boolean isRuning = false;
    /**
     * 文件下载路径
     */
    private String mFilePath;
    /**
     * 上下文
     */
    private static Context mContext;
    /**
     * 结果返回到主线程
     */
    private Handler mHandler;
    /**
     * 下载回调类
     */
    private MultiThreadDownloadUtil.DownloadResponseHandler mResponseHandler;
    /**
     * 下载的百分比
     */
    private float mPercent = 0;
    /**
     * 通知管理
     */
    private NotificationManager mNotificationManager;
    /**
     * 通知
     */
    private Notification mNotification;
    /**
     * 通知创建类
     */
    private Notification.Builder mBuilder;
    /**
     * IOC
     */
    private int mICON;
    /**
     * APP名称
     */
    private String mAppName;
    /**
     * 更新的通知ID
     */
    private final int NOTIFICATION_ID = 8888;
    /**
     * 上次的下载量(方便计算下载速度)
     */
    private long mLastBytes = 0;
    /**
     * 上次的时间
     */
    private long mLastTime = 0;
    /**
     * 下载速度
     */
    private long mSpeed = 0;
    /**
     * 是下载APP还是更新APP(0为更新，1为下载)
     */
    private int mUpdateDown = 0;

    /**
     * 外部跳转到下载
     *
     * @param context      上下文
     * @param path         下载地址
     * @param ioc          ICON
     * @param appName      APP名称
     * @param updateOrDown 0为更新，1为下载
     */
    public static void startIntent(final Context context, final String path, int ioc, String appName, int updateOrDown) {
        if (context == null) {
            ToastUtil.showToast("Context不能为空!");
            return;
        } else if (TextUtils.isEmpty(path)) {
            ToastUtil.showToast("文件下载路径不能为空!");
            return;
        } else if (isRuning) {
            ToastUtil.showToast("文件正在下载!");
            return;
        }
        mContext = context;
        Intent intent = new Intent(context, UpdateService.class);
        intent.putExtra("download_path", path);
        intent.putExtra("app_ioc", ioc);
        intent.putExtra("app_name", appName);
        intent.putExtra("update_Down", updateOrDown);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            mFilePath = intent.getStringExtra("download_path");
            mICON = intent.getIntExtra("app_ioc", android.R.drawable.stat_sys_download);
            mAppName = intent.getStringExtra("app_name");
            mUpdateDown = intent.getIntExtra("update_Down", 0);
        }
        //初始化通知
        initNotification(mICON, TextUtils.isEmpty(mAppName) ? getPackageName() : mAppName);
        if (TextUtils.isEmpty(mFilePath)) {
            stopSelf();
        } else {
            startDownFile(mContext, mFilePath);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 开始下载文件
     *
     * @param context  上下文
     * @param filePath 下载路径
     */
    private void startDownFile(final Context context, final String filePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                isRuning = true;
                mNotificationManager.notify(NOTIFICATION_ID, mNotification);
                MultiThreadDownloadUtil.downloadFile(context, filePath, mResponseHandler);
                reInit();
            }
        }).start();
    }

    /**
     * 初始化
     */
    private void init() {
        //设置为单线程下载
        MultiThreadDownloadUtil.setThreadCount(mContext, 1);
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper(), this);
        }
        if (mResponseHandler == null) {
            mResponseHandler = new MultiThreadDownloadUtil.DownloadResponseHandler() {
                @Override
                public void onSuccess(String result, String fileName) {
                    Message message = Message.obtain();
                    message.what = 0xAA1111;
                    Bundle bundle = new Bundle();
                    bundle.putString("result", result);
                    bundle.putString("fileName", fileName);
                    message.setData(bundle);
                    mHandler.sendMessage(message);
                }

                @Override
                public void onFailed(String result) {
                    Message message = Message.obtain();
                    message.what = 0xAA0000;
                    message.obj = result;
                    mHandler.sendMessage(message);
                }

                @Override
                public void onProgress(int threadId, long totalBytes, long currentBytes) {
                    if (totalBytes != 0 && (float) (currentBytes * 100.0 / totalBytes) >= mPercent + 0.1) {
                        Message message = Message.obtain();
                        message.what = 0xAA1100;
                        Bundle bundle = new Bundle();
                        bundle.putLong("totalBytes", totalBytes);
                        bundle.putLong("currentBytes", currentBytes);
                        message.setData(bundle);
                        mHandler.sendMessage(message);
                    }
                }
            };
        }
    }

    /**
     * 初始化通知栏
     *
     * @param icon 下载的ICON
     * @param name 下载的APP名称
     */
    private void initNotification(int icon, String name) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.update_notification);
        remoteViews.setImageViewResource(R.id.image, icon);
        remoteViews.setTextViewText(R.id.title, name + (mUpdateDown == 0 ? "版本更新" : "下载"));
        remoteViews.setTextViewText(R.id.text, name + (mUpdateDown == 0 ? "版本更新正在下载" : "正在下载"));
        remoteViews.setProgressBar(R.id.progress, 100, 0, false);
        remoteViews.setTextViewText(R.id.percent, "0%");
        //获取通知管理
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new Notification.Builder(mContext)
                .setSmallIcon(icon)
                .setContentTitle(name + (mUpdateDown == 0 ? "更新完成" : "下载完成"))
                .setContentText("点击进行安装")
                        //以上三个必须设置
                .setWhen(System.currentTimeMillis())
                        //不被通知栏清除功能清除
                .setDefaults(Notification.FLAG_NO_CLEAR)
                .setOngoing(true)
                .setAutoCancel(false)
                .setTicker("新APP开始下载")
                .setContent(remoteViews);
        if (Build.VERSION.SDK_INT >= 16) {
            mNotification = mBuilder.build();
        } else {
            mNotification = mBuilder.getNotification();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            //成功
            case 0xAA1111:
                Bundle bundle1 = message.getData();
                String result = bundle1.getString("result");
                String fileName = bundle1.getString("fileName");
                CLog.d(TAG, "下载结果:" + result + ";下载的文件名:" + fileName);
                mHandler.removeMessages(0xAA1111);
                mNotification.contentView.setTextViewText(R.id.title, mAppName + (mUpdateDown == 0 ? "版本更新完成" : "下载完成"));
                mNotification.contentView.setTextViewText(R.id.text, "点击进行安装");
                mNotification.contentView.setProgressBar(R.id.progress, 100, 100, false);
                mNotification.contentView.setTextViewText(R.id.percent, "100%");
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(FilepathUtil.getCacheRootPath(mContext) + File.separator + FilepathUtil.getAPKS() + File.separator + fileName)),
                        "application/vnd.android.package-archive");
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                mBuilder.setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                       // .setSmallIcon(R.drawable.carking_icon)
                        .setOngoing(false)
                       // .setContent(null)//设置为null使用系统默认布局
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.FLAG_AUTO_CANCEL);
                if (Build.VERSION.SDK_INT >= 16) {
                    mNotification = mBuilder.build();
                } else {
                    mNotification = mBuilder.getNotification();
                }
                mNotificationManager.notify(NOTIFICATION_ID, mNotification);
                break;
            //下载中
            case 0xAA1100:
                Bundle bundle2 = message.getData();
                long totalBytes = bundle2.getLong("totalBytes", 1);
                long currentBytes = bundle2.getLong("currentBytes", 0);
                mPercent = (float) (currentBytes * 100.0 / totalBytes);
                CLog.d(TAG, "文件总大小:" + (Formatter.formatFileSize(mContext, totalBytes))
                        + ";已下载文件大小:" + (Formatter.formatFileSize(mContext, currentBytes))
                        + ";已下载百分比:" + String.format("%.1f", mPercent) + "%");
                if (mLastTime == 0 || mLastBytes == 0) {
                    mSpeed = currentBytes / 1000;
                    mLastTime = System.currentTimeMillis();
                    mLastBytes = currentBytes;
                } else if (System.currentTimeMillis() - mLastTime >= 1000) {
                    mSpeed = (currentBytes - mLastBytes) / (System.currentTimeMillis() - mLastTime) * 1000;
                    mLastTime = System.currentTimeMillis();
                    mLastBytes = currentBytes;
                }
                mNotification.contentView.setProgressBar(R.id.progress, 100, (int) mPercent, false);
                mNotification.contentView.setTextViewText(R.id.percent, String.format("%.1f", mPercent) + "%");
                mNotification.contentView.setTextViewText(R.id.text, "共:"
                        + Formatter.formatFileSize(mContext, totalBytes) + ",已下载"
                        + Formatter.formatFileSize(mContext, currentBytes) + ",下载速度:"
                        + Formatter.formatFileSize(mContext, mSpeed) + "/s");
                mNotificationManager.notify(NOTIFICATION_ID, mNotification);
                mHandler.removeMessages(0xAA1100);
                break;
            //失败
            case 0xAA0000:
                CLog.d(TAG, "下载失败:" + message.obj.toString());
                String msg = "";
                if (NetworkUtil.isAvailable(this)) {
                    msg = "请联系客服确认下载地址";
                } else {
                    msg = "请检查网络和SD卡容量";
                }
                mBuilder.setContentTitle(mAppName + (mUpdateDown == 0 ? "版本更新失败" : "下载失败"))
                        .setContentText(msg)
                       // .setSmallIcon(R.drawable.carking_icon)
                        .setAutoCancel(true)
                        .setOngoing(false)
                       // .setContent(null)//设置为null使用系统默认布局
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.FLAG_AUTO_CANCEL);
                if (Build.VERSION.SDK_INT >= 16) {
                    mNotification = mBuilder.build();
                } else {
                    mNotification = mBuilder.getNotification();
                }
                mNotificationManager.notify(NOTIFICATION_ID, mNotification);
                mHandler.removeMessages(0xAA0000);
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 重置信息
     */
    private void reInit() {
        mPercent = 0;
        mResponseHandler = null;
        mFilePath = null;
        isRuning = false;
        mLastBytes = 0;
        mLastTime = 0;
        mSpeed = 0;
        stopSelf();
    }
}