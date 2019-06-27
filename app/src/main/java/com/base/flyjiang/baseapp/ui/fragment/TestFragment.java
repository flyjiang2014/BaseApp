package com.base.flyjiang.baseapp.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.adapter.ProductAdapter;
import com.base.flyjiang.baseapp.bean.CommonReturnData;
import com.base.flyjiang.baseapp.bean.ProductBean;
import com.base.flyjiang.baseapp.bean.SignalReturnData;
import com.base.flyjiang.baseapp.callback.JsonCallback;
import com.carking.quotationlibrary.base.BaseFragmentV4;
import com.carking.quotationlibrary.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.Request;
import com.weavey.loading.lib.LoadingLayout;

import net.anumbrella.pullrefresh.PullRefreshBase.PullRefreshBase;
import net.anumbrella.pullrefresh.Widget.PullRefreshListView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by ${flyjiang} on 2016/10/18.
 * 文件说明：
 */
public class TestFragment extends BaseFragmentV4 implements PullRefreshBase.OnRefreshListener<ListView> {

    private static TestFragment fragment;
    private PullRefreshListView pullRefreshListView;
    private int currentPage = 1;
    ProductAdapter productAdapter;
    private ListView mListView;
    List<ProductBean> list = new ArrayList<>();
    int more;
    int state;
    private LoadingLayout loadingLayout;
    @Override
    protected View initView(LayoutInflater inflater) {
        state = getArguments().getInt("state");
        View view = inflater.inflate(R.layout.layout_test_fragment, null);
        initView(view);
        return view;
    }

    @Override
    public void initData() {
        ToastUtil.showToast("我是第" + state + "个");
        Log.e("jiang", "我是第" + state + "个");
       //  getData();
    }

    public  static  TestFragment getInstance(int state) {
        fragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initView(View view) {
        EventBus.getDefault().register(this);
        pullRefreshListView = (PullRefreshListView) view.findViewById(R.id.pullRefreshListView);
        loadingLayout= (LoadingLayout) view.findViewById(R.id.loading);
        pullRefreshListView.setPullLoadEnabled(true);
        pullRefreshListView.setScrollLoadEnabled(false);//滑动底部自动加载
        pullRefreshListView.setOnRefreshListener(this);
        //  pullRefreshListView.doAutoPullRefreshing(true, 500);//设置开始时自动刷新
        mListView = pullRefreshListView.getRefreshableView();
        productAdapter = new ProductAdapter(mContext, list);
        pullRefreshListView.setAdapter(productAdapter);
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                getData();
            }
        });
    }

    /**
     * 获取数据
     */
    public void getData() {


        OkGo.<CommonReturnData<SignalReturnData>>get("http://218.90.187.218:8888/shop_m/viewspot/listTicketsByState.do")
              /*  .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .cacheKey("ceshi")*/
                .params("token", "0425f4ec-f6ef-4073-853d-1b027b6a01a2")
                .params("viewSpotId", "24")
                .params("currentPage", currentPage + "")
                .params("pageSize", "10")
                .execute(
                        new JsonCallback<CommonReturnData<SignalReturnData>>(getActivity()) {

                            @Override
                            public void onStart(Request<CommonReturnData<SignalReturnData>, ? extends Request> request) {
                                super.onStart(request);
                            }

                            @Override
                            public void onSuccess(CommonReturnData<SignalReturnData> returnData) {

                            }

                            @Override
                            public void onSuccess(com.lzy.okgo.model.Response<CommonReturnData<SignalReturnData>> response) {
                                CommonReturnData<SignalReturnData> responseData = response.body();
                                if (responseData.getSuccess() == 1) {
                                    list.clear();
                                    list.addAll(responseData.getData().getItems());
                                    more = responseData.getData().getMore();
                                    productAdapter.notifyDataSetChanged();
                                    loadingLayout.setStatus(LoadingLayout.Success);
                                } else {
                                    ToastUtil.showToast(responseData.getMessage());
                                    loadingLayout.setStatus(LoadingLayout.Error);
                                }

                            }

                            @Override
                            public void onError(Response<CommonReturnData<SignalReturnData>> response) {
                                super.onError(response);
                                loadingLayout.setStatus(LoadingLayout.Error);
                            }

                            /*       @Override
                            public void onBefore(BaseRequest request) {
                                super.onBefore(request);
                                loadingLayout.setStatus(LoadingLayout.Loading);
                            }

                            @Override
                            public void onSuccess(final CommonReturnData<SignalReturnData> responseData, Call call, Response response) {
                                if (responseData.getSuccess() == 1) {
                                    list.clear();
                                    list.addAll(responseData.getData().getItems());
                                    more = responseData.getData().getMore();
                                    productAdapter.notifyDataSetChanged();
                                    loadingLayout.setStatus(LoadingLayout.Success);
                                } else {
                                    ToastUtil.showToast(responseData.getMessage());
                                    loadingLayout.setStatus(LoadingLayout.Error);
                                }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                loadingLayout.setStatus(LoadingLayout.Error);
                            }*/
                        }
                );
    }

    @Override
    public void onPullDownRefresh(PullRefreshBase<ListView> refreshView) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPage = 1;
                Log.e("jiang", "下拉刷新");
                productAdapter.notifyDataSetChanged();
                pullRefreshListView.onPullDownRefreshComplete();
            }
        }, 2000);

    }

    @Override
    public void onPullUpToRefresh(PullRefreshBase<ListView> refreshView) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("jiang", "上拉加载");
                if (more == 0) {
                    pullRefreshListView.onPullUpRefreshComplete();
                    pullRefreshListView.setHasMoreData(false);
                    return;
                }
                currentPage++;
                getData();
                pullRefreshListView.onPullUpRefreshComplete();
                pullRefreshListView.setHasMoreData(true);
            }
        }, 2000);

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 刷新列表通知
     *
     * @param event
     * @创建时间 2015年5月7日 下午4:16:51
     */
    public void onEventMainThread(String event) {
        if (event.equals("event")) {
            ToastUtil.showToast("刷新数据");
        }
    }
}
