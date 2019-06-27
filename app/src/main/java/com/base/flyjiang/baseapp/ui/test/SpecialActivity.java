package com.base.flyjiang.baseapp.ui.test;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.adapter.ViewFlowAdapter;
import com.base.flyjiang.baseapp.base.BaseDemoActivity;
import com.base.flyjiang.baseapp.utils.ColorUtil;
import com.carking.quotationlibrary.utils.DensityUtil;
import com.carking.quotationlibrary.view_flow.CircleFlowIndicator;
import com.carking.quotationlibrary.view_flow.ViewFlow;

import net.anumbrella.pullrefresh.PullRefreshBase.PullRefreshBase;
import net.anumbrella.pullrefresh.Widget.PullRefreshListView;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.util.CLog;

public class SpecialActivity extends BaseDemoActivity implements PullRefreshBase.OnRefreshListener<ListView> {

    /**
     * 底部轮播
     */
    private ViewFlow mViewFlowCircle;
    /**
     * 底部轮播图配置器
     */
    private ViewFlowAdapter mViewFlowAdapter;
    /**
     * 底部轮播图图片地址
     */
    private List<String> mDataViewFlow;

    /**
     * ViewFlw的高度
     */
    private int mHeightViewFlow = 200;

    /**
     * 轮播图对应圆点
     */
    private CircleFlowIndicator mCircleFlowIndicator;

    private boolean isScrollIdle = true; // ListView是否在滑动
    private float headViewTopMargin; // headView距离顶部的距离

    private float headViewViewHeight; //headView的高度

    private PullRefreshListView pullRefreshListView;
    private RelativeLayout rlBar;

    private ArrayAdapter itemArrayAdapter;
    private ListView mListView;
    View viewTitleBg;
    View viewActionMoreBg;

    @Override
    public int setBaseContentView() {
        return R.layout.activity_special;
    }

    @Override
    public void onCreateBefore() {
        super.onCreateBefore();
        setIsShowTitle(false);
    }

    @Override
    public void init() {

        pullRefreshListView = (PullRefreshListView) findViewById(R.id.PullRefreshListView);
        rlBar = (RelativeLayout) findViewById(R.id.rl_bar);
        viewTitleBg = findViewById(R.id.view_title_bg);
        viewActionMoreBg = findViewById(R.id.view_action_more_bg);
        pullRefreshListView.setPullLoadEnabled(true); //滑到底部手动上拉加载
        pullRefreshListView.setScrollLoadEnabled(false);//滑动底部自动加载
        mListView = pullRefreshListView.getRefreshableView();

        itemArrayAdapter = new ArrayAdapter(mContext, android.R.layout.simple_expandable_list_item_1);
        itemArrayAdapter.add("jiang");
        itemArrayAdapter.add("效果");
        for (int i = 0; i < 15; i++) {
            itemArrayAdapter.add("效果" + i);
        }

        mListView.setAdapter(itemArrayAdapter);
        final View headView = LayoutInflater.from(this).inflate(R.layout.viewflow_head, null);
        mListView.addHeaderView(headView);

        pullRefreshListView.setErrorView(R.layout.activity_login);
        pullRefreshListView.setOnRefreshListener(this);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                isScrollIdle = (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScrollIdle && headViewTopMargin < 0) {
                    return;
                }
                if (headView != null) {
                    headViewViewHeight = DensityUtil.px2dp(mContext, headView.getHeight());
                    handleTitleBarColorEvaluate(headView,firstVisibleItem);
                }
            }
        });

        mHeightViewFlow = (int) (mScreentWidth * 332.0f / 640);
        CLog.d(TAG, "ViewFlow的高度:" + mHeightViewFlow + ";标题高度:" + getResources().getDimension(R.dimen.title_height));
        mViewFlowCircle = (ViewFlow) headView.findViewById(R.id.viewflow_flow);
        mViewFlowCircle.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, mHeightViewFlow));
        /**************************************轮播图******************************************/
        mDataViewFlow = new ArrayList<>();
        mDataViewFlow.add("res://drawable/" + R.drawable.banner1);
        mDataViewFlow.add("res://drawable/" + R.drawable.banner2);
        mDataViewFlow.add("res://drawable/" + R.drawable.banner3);
        mDataViewFlow.add("res://drawable/" + R.drawable.banner4);
        mViewFlowAdapter = new ViewFlowAdapter(mContext, mDataViewFlow, mHeightViewFlow, false);

        /**************************************************************************************/
        mCircleFlowIndicator = (CircleFlowIndicator) headView.findViewById(R.id.viewflow_indic);
        fillViewFlowCircle();
    }

    // 处理标题栏颜色渐变
    private void handleTitleBarColorEvaluate(View headView,int firstVisibleItem ) {
        float fraction;
        headViewTopMargin = DensityUtil.px2dp(mContext, headView.getTop());
        if (headViewTopMargin > 0) {
            fraction = 1f - headViewTopMargin * 1f / 60;
            if (fraction < 0f) fraction = 0f;
            rlBar.setAlpha(fraction);
            return;
        }

        float space = Math.abs(headViewTopMargin) * 1f;
        fraction = space / (headViewViewHeight);
        if (fraction < 0f) fraction = 0f;
        if (fraction > 1f) fraction = 1f;
        rlBar.setAlpha(1f);

        if (fraction >= 1f||firstVisibleItem>0) {
            viewTitleBg.setAlpha(0f);
            viewActionMoreBg.setAlpha(0f);
            rlBar.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            viewTitleBg.setAlpha(1f - fraction);
            viewActionMoreBg.setAlpha(1f - fraction);
            rlBar.setBackgroundColor(ColorUtil.getNewColorByStartEndColor(mContext, fraction, R.color.transparent, R.color.colorPrimary));
        }
    }


    /**
     * 填充轮播圆点
     */
    private void fillViewFlowCircle() {
        mViewFlowCircle.setAdapter(mViewFlowAdapter);
        // 实际图片张数
        mViewFlowCircle.setmSideBuffer(mDataViewFlow.size());
        mViewFlowCircle.setFlowIndicator(mCircleFlowIndicator);
        mViewFlowCircle.setTimeSpan(2000);
        // 设置初始位置
        mViewFlowCircle.setSelection(3 * 10000);
        //设置圆点位置
        mCircleFlowIndicator.setGravity(Gravity.CENTER);//默认居中,可设置Gravity.RIGHT和Gravity.LEFT
        // 启动自动播放
        mViewFlowCircle.startAutoFlowTimer();
    }

    @Override
    public void onPullDownRefresh(PullRefreshBase<ListView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullRefreshBase<ListView> refreshView) {

    }
}
