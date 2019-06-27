package com.base.flyjiang.baseapp.ui.test;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.base.flyjiang.baseapp.R;

import net.anumbrella.pullrefresh.PullRefreshBase.PullRefreshBase;
import net.anumbrella.pullrefresh.Widget.PullRefreshScrollView;

public class ScrollViewActivity extends AppCompatActivity implements PullRefreshBase.OnRefreshListener<ScrollView> {

    PullRefreshScrollView mScrollView;
    TextView tv_ceshi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);
        mScrollView = (PullRefreshScrollView) findViewById(R.id.PullRefreshScrollView);
//        ScrollView sv = mScrollView.getRefreshableView();
//     /*   switch_btn = (SwitchToggleView) findViewById(R.id.switch_btn);
//        switch_btn.setSwitchBackground(R.color.transparent);
//        switch_btn.setSwitchSlide(R.drawable.em_camera_switch_normal);*/
//        try {
//            View view = mScrollView.getChildAt(mScrollView.getChildCount()-1);
//            mScrollView.removeViewAt(mScrollView.getChildCount()-1);
//            ((ScrollView)((FrameLayout)mScrollView.getChildAt(1)).getChildAt(0)).addView(view);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        mScrollView.setScrollViewStatue();
        mScrollView.setPullRefreshEnabled(true);
        mScrollView.setOnRefreshListener(this);
        tv_ceshi = (TextView) findViewById(R.id.tv_ceshi);
        tv_ceshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("jiang","jiang02");
            }
        });
    }

    @Override
    public void onPullDownRefresh(PullRefreshBase<ScrollView> refreshView) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("jiang", "下拉刷新");
                mScrollView.onPullDownRefreshComplete();
            }
        }, 2000);

    }

    @Override
    public void onPullUpToRefresh(PullRefreshBase<ScrollView> refreshView) {

    }
}
