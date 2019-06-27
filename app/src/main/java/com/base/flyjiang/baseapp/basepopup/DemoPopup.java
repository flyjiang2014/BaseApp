package com.base.flyjiang.baseapp.basepopup;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;

import com.base.flyjiang.baseapp.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by ${flyjiang} on 2017/7/24.
 * 文件说明：DemoPopup 例子
 */

public class DemoPopup extends BasePopupWindow {
    private View popupView;
    public DemoPopup(Activity context) {
        super(context);
        setAutoLocatePopup(true);
    }

    public DemoPopup(Activity context, int w, int h) {
        super(context, w, h);
    }

    @Override
    public void showPopupWindow(View v) {
      //  setOffsetY(v.getHeight());
        Log.e("jiang",getHeight()+"");
        setOffsetY(1);
        super.showPopupWindow(v);
    }

    @Override
    protected Animation initShowAnimation() {
        return getDefaultScaleAnimation();
    }
    @Override
    public View getClickToDismissView() {
       // return popupView.findViewById(R.id.rl_whole);
        return null;
    }
    @Override
    public View onCreatePopupView() {
        popupView = LayoutInflater.from(getContext()).inflate(R.layout.popupwindow_changephototheme, null);
        return  popupView;
    }
    @Override
    public View initAnimaView() {
        return popupView.findViewById(R.id.ll_test);
    }
}
