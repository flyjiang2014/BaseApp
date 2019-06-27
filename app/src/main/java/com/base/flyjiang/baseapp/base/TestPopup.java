package com.base.flyjiang.baseapp.base;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by ${flyjiang} on 2017/7/26.
 * 文件说明：
 */

public class TestPopup extends BasePopup{

    public TestPopup(Activity mActivity) {
        super(mActivity);
        initPopupWindow();
    }

    @Override
    public void show() {   //用于设置PopupWindow展示的区域
        super.show();
        mPopupWindow.showAtLocation(mLocationView, Gravity.CENTER, 0, 0);
    }

    @Override
    protected void initPopupWindow() {   //用于设置PopupWindow各个属性
        mPopupWindow = new PopupWindow(mContentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
    }

    @Override
    protected View onCreatePopupView() {  //创建PopupWindow界面
        return null;
    }

    @Override
    protected View onCreateLocationView() { //创建创建PopupWindow展示在哪个界面，一般为当前Activity界面
        return null;
    }
}
