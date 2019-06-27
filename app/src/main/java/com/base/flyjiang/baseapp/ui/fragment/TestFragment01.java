package com.base.flyjiang.baseapp.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.adapter.ProductAdapter;
import com.base.flyjiang.baseapp.bean.ProductBean;
import com.carking.quotationlibrary.base.BaseFragmentV4;
import com.carking.quotationlibrary.utils.ToastUtil;

import net.anumbrella.pullrefresh.PullRefreshBase.PullRefreshBase;
import net.anumbrella.pullrefresh.Widget.PullRefreshListView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by ${flyjiang} on 2016/10/18.
 * 文件说明：
 */
public class TestFragment01 extends BaseFragmentV4 implements PullRefreshBase.OnRefreshListener<ListView> {

    private static TestFragment01 fragment;
    private PullRefreshListView pullRefreshListView;
    private int currentPage=1;
    ProductAdapter productAdapter;
    private ListView mListView;
    List<ProductBean> list =  new ArrayList<>();
    int more;
    int  state ;

    @Override
    protected View initView(LayoutInflater inflater) {
        state =  getArguments().getInt("state");
        View view =inflater.inflate(R.layout.layout_test_fragment01, null);
        initView(view);
        return view;
    }

    @Override
    public void initData() {
        ToastUtil.showToast("我是第1个");
       // getData(true);
    }
    public static TestFragment01 getInstance( int  state ) {
           fragment = new TestFragment01();
            Bundle bundle = new Bundle();
            bundle.putInt("state", state);
            fragment.setArguments(bundle);
           //fragment.state = state;
            return fragment;
    }

    private void initView(View view){
        EventBus.getDefault().register(this);
        pullRefreshListView = (PullRefreshListView) view.findViewById(R.id.pullRefreshListView);
        pullRefreshListView.setPullLoadEnabled(true);
        pullRefreshListView.setScrollLoadEnabled(true);//滑动底部自动加载
        pullRefreshListView.setOnRefreshListener(this);
        //  pullRefreshListView.doAutoPullRefreshing(true, 500);//设置开始时自动刷险
        mListView = pullRefreshListView.getRefreshableView();
        productAdapter = new ProductAdapter(mContext, list);
        mListView.setAdapter(productAdapter);
    }

   /* *//**
     * 登录接口
     *//*
    CustomHttpRequest.RequestCallback dataCallback = new CustomHttpRequest.RequestCallback() {
        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            CustomHttpRequest.dismissLoading();
        }

        @Override
        public void onSuccess(String result) {
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("success") == 1) {
                        ToastUtil.showToast("测试成功");
                        List<ProductBean> mList = JSON.parseArray(jsonObject.getJSONObject("data").getString("items"), ProductBean.class);
                        more = jsonObject.getJSONObject("data").getInt("more");
                        if (currentPage == 1) {
                            list.clear();
                        }
                        list.addAll(mList);
                        productAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showToast(jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            CustomHttpRequest.dismissLoading();
        }
    };


    @Override
    public void onPullDownRefresh(PullRefreshBase<ListView> refreshView) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPage = 1;
                getData(false);
                pullRefreshListView.onPullDownRefreshComplete();
            }
        }, 2000);

    }

    @Override
    public void onPullUpToRefresh(PullRefreshBase<ListView> refreshView) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (more == 0) {
                    pullRefreshListView.onPullUpRefreshComplete();
                    pullRefreshListView.setHasMoreData(false);
                    return;
                }
                currentPage++;
                getData(false);
                pullRefreshListView.onPullUpRefreshComplete();
                pullRefreshListView.setHasMoreData(true);
            }
        }, 2000);

    }

    public void getData(Boolean isShowDiaLog){
        if(isShowDiaLog){
            CustomHttpRequest.showLoading("ceshi", mContext);
        }
        RequestParams requestParams = new RequestParams("http://192.168.0.199:8888/shop_m/viewspot/listTicketsByState.do");
        requestParams.addBodyParameter("token", "3361e95c-1ecc-433a-a3bc-22edad753fc7");
        requestParams.addBodyParameter("viewSpotId","118");
        requestParams.addBodyParameter("state",state+"");
        Log.e("jiang",state+"");
        requestParams.addBodyParameter("currentPage",currentPage+"");
        requestParams.addBodyParameter("pageSize","10");
        CustomHttpRequest.getRequest(mContext, requestParams, dataCallback);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }*/

    /**
     * 刷新列表通知
     *
     * @创建时间 2015年5月7日 下午4:16:51
     * @param event
     */
    public void onEventMainThread(String event) {

      if(event.equals("event")){
          ToastUtil.showToast("刷新数据");
          Log.e("jiang", state + "ceshi");
      }
    }

    @Override
    public void onPullDownRefresh(PullRefreshBase<ListView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullRefreshBase<ListView> refreshView) {

    }
}
