package com.base.flyjiang.baseapp.bean;

import android.support.design.widget.AppBarLayout;
import android.util.Log;

/**
 * Created by ${flyjiang} on 2019/10/16.
 * 文件说明：
 */
public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    private State mCurrentState = State.IDLE;

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        Log.e("jiang",i+"");
        Log.e("jiang01",appBarLayout.getTotalScrollRange()+"");
        if (i == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED);
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED);
            }
            mCurrentState = State.COLLAPSED;
        } else {
            if (mCurrentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE);
            }
            mCurrentState = State.IDLE;
        }
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, State state);
}