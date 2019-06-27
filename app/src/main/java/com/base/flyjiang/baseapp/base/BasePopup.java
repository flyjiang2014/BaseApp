package com.base.flyjiang.baseapp.base;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by ${flyjiang} on 2017/7/26.
 * 文件说明：
 */

public abstract class BasePopup {
    protected Activity mActivity;
    protected PopupWindow mPopupWindow;
    protected View mContentView;
    protected View mLocationView;
    protected boolean showShadow = true;

    public BasePopup(Activity mActivity) {
        this.mActivity = mActivity;
        mContentView = onCreatePopupView();
        mLocationView = onCreateLocationView();
       // initPopupWindow();
        mPopupWindow.setOnDismissListener(new poponDismissListener());
    }

    public void show() {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            if(showShadow){
                backgroundAlpha(0.7f); //背景变暗
            }
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1.0f);
                }
            });
        }
    }

    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            backgroundAlpha(1.0f);
            mPopupWindow.dismiss();
        }
    }


    protected abstract View onCreatePopupView();

    protected abstract View onCreateLocationView();

    protected abstract void initPopupWindow();


    // 背景变色的方法
    public void backgroundAlpha(float bgAlpha) {
        if (bgAlpha == 1) {
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        mActivity.getWindow().setAttributes(lp);
    }

    public void setShowShadow(boolean showShadow) {
        this.showShadow = showShadow;
    }


    /**
     * 添加弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }
}
