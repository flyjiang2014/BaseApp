package com.base.flyjiang.baseapp.ui.test;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.adapter.ProductAdapter;
import com.base.flyjiang.baseapp.base.BaseDemoActivity;
import com.base.flyjiang.baseapp.bean.CommonReturnData;
import com.base.flyjiang.baseapp.bean.ProductBean;
import com.base.flyjiang.baseapp.bean.SignalReturnData;
import com.base.flyjiang.baseapp.callback.DialogCallback;
import com.carking.quotationlibrary.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import net.anumbrella.pullrefresh.PullRefreshBase.PullRefreshBase;
import net.anumbrella.pullrefresh.Widget.PullRefreshListView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class TestLvActivity extends BaseDemoActivity implements PullRefreshBase.OnRefreshListener<ListView> {
    private PullRefreshListView pullRefreshListView;
    private Context mContext;
    private int currentPage = 1;
    ProductAdapter productAdapter;
    private LinearLayout ll_above;
    private ListView mListView;
    List<ProductBean> list = new ArrayList<>();
    int more;


    @Override
    public int setBaseContentView() {
        return R.layout.activity_test_lv;
    }

    @Override
    public void init() {
        initView();
        getData();
    }

    public void initView() {
        mContext = this;
        ll_above = (LinearLayout) findViewById(R.id.ll_above);
        pullRefreshListView = (PullRefreshListView) findViewById(R.id.PullRefreshListView);
        pullRefreshListView.setPullLoadEnabled(true); //滑到底部手动上拉加载
        pullRefreshListView.setScrollLoadEnabled(false);//滑动底部自动加载
        //pullRefreshListView.doAutoPullRefreshing(true, 500);//设置开始时自动刷新
        productAdapter = new ProductAdapter(mContext, list);
        mListView = pullRefreshListView.getRefreshableView();

        View headView = LayoutInflater.from(this).inflate(R.layout.activity_test_share, null);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        // pullRefreshListView.addView(headView,1,layoutParams);
        mListView.addHeaderView(headView);
        mListView.setAdapter(productAdapter);
        // pullRefreshListView.setAdapterWithProgress(productAdapter);
        pullRefreshListView.setAdapterWithProgress(productAdapter);
        setEmptyView(mListView);
        pullRefreshListView.setAdapterWithProgress(productAdapter);
        //  pullRefreshListView.setAdapterWithProgress(productAdapter);
        //pullRefreshListView.setAdapter(productAdapter);
        pullRefreshListView.setErrorView(R.layout.activity_login);
        pullRefreshListView.setOnRefreshListener(this);
        //    onPullDownRefresh(pullRefreshListView);
    }

    /**
     * 获取数据
     */
    public void getData() {
        OkGo.<CommonReturnData<SignalReturnData>>get("http://218.90.187.218:8888/shop_m/viewspot/listTicketsByState.do")
              //  .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
               /* .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                .cacheKey("ceshi")*/
                         .params("token", "c485483d-7fa6-49b5-8c86-32ed84f9d209")
                .params("viewSpotId", "264")
                .params("currentPage", currentPage + "")
                .params("pageSize", "10")
                .execute(
                        new DialogCallback<CommonReturnData<SignalReturnData>>(this) {

                            @Override
                            public void onSuccess(CommonReturnData<SignalReturnData> returnData) {

                            }

                            @Override
                            public void onSuccess(com.lzy.okgo.model.Response<CommonReturnData<SignalReturnData>> response) {
                                CommonReturnData<SignalReturnData> responseData = response.body();
                                if (responseData.getSuccess() == 1) {
                                    if(responseData.getData().getItems()!=null&&responseData.getData().getItems().size()>0) {
                                        SignalReturnData signalReturnData = responseData.getData();
                                        if(signalReturnData!=null){
                                            signalReturnData.save();
                                        }
                                        list.clear();
                                        list.addAll(responseData.getData().getItems());
                                        Log.e("jiang", "这里来了吗");
                                        productAdapter.notifyDataSetChanged();
                                    }else{ //list无数据
                                        setEmptyViewText("无更多数据");
                                    }
                                } else {
                                    ToastUtil.showToast(responseData.getMessage());
                                    //  pullRefreshListView.showErrorView();
                                    setEmptyViewWithImageText("出错了", true);
                                }
                            }

                            @Override
                            public void onError(Response<CommonReturnData<SignalReturnData>> response) {
                                super.onError(response);
                                SignalReturnData signalReturnData = DataSupport.findFirst(SignalReturnData.class);
                                list.clear();
                                list.addAll(signalReturnData.getItems());
                                Log.e("jiang", "这里来了吗");
                                productAdapter.notifyDataSetChanged();
                            }

                            /*      @Override
                            public void onSuccess(final CommonReturnData<SignalReturnData> responseData, Call call, Response response) {
                                if (responseData.getSuccess() == 1) {
                                    if(responseData.getData().getItems()!=null&&responseData.getData().getItems().size()>0) {
                                        SignalReturnData signalReturnData = responseData.getData();
                                        if(signalReturnData!=null){
                                            signalReturnData.save();
                                        }
                                        list.clear();
                                        list.addAll(responseData.getData().getItems());
                                        Log.e("jiang", "这里来了吗");
                                        productAdapter.notifyDataSetChanged();
                                    }else{ //list无数据
                                        setEmptyViewText("无更多数据");
                                    }
                                } else {
                                    ToastUtil.showToast(responseData.getMessage());
                                    //  pullRefreshListView.showErrorView();
                                    setEmptyViewWithImageText("出错了", true);
                                }
                            }*/

                     /*       @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                setEmptyViewText("出错了");

                                SignalReturnData signalReturnData = DataSupport.findFirst(SignalReturnData.class);
                                list.clear();
                                list.addAll(signalReturnData.getItems());
                                Log.e("jiang", "这里来了吗");
                                productAdapter.notifyDataSetChanged();

                            }*/
                        }


                     /*   new JsonCallback<CommonReturnData<SignalReturnData>>() {

                            @Override
                            public void onSuccess(CommonReturnData<SignalReturnData> responseData, Call call, Response response) {

                                list.clear();
                                list.addAll(responseData.getData().getItems());
                                productAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                Log.e("jiang", "出错了" + "");
                            }
                        }*/

                       /* new JsonCallback01<ReturnData>() {
                            @Override
                            public void onSuccess(ReturnData returnData, Call call, Response response) {
                                Log.e("jiang", "成功了" + returnData);

                                if(returnData.getSuccess().equals("1")){
                                    list.clear();
                                    list.addAll(returnData.getData().getItems());
                                    productAdapter.notifyDataSetChanged();
                                }else{
                                    Log.e("jiang", returnData.getMessage());
                                }

                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                Log.e("jiang", "出错了" + "");
                            }

                            @Override
                            public ReturnData convertSuccess(Response response) throws Exception {
                                Type genType = getClass().getGenericSuperclass();
                                //从上述的类中取出真实的泛型参数，有些类可能有多个泛型，所以是数值
                                Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                                //我们的示例代码中，只有一个泛型，所以取出第一个，得到如下结果
                                //com.lzy.demo.model.Login
                                Type type = params[0];

                                //这里我们既然都已经拿到了泛型的真实类型，即对应的 class ，那么当然可以开始解析数据了，我们采用 Gson 解析
                                //以下代码是根据泛型解析数据，返回对象，返回的对象自动以参数的形式传递到 onSuccess 中，可以直接使用
                                JsonReader jsonReader = new JsonReader(response.body().charStream());
                                //有数据类型，表示有data
                                ReturnData data = Convert.fromJson(jsonReader, type);
                                response.close();
                                return data;
                            }
                        }*/



                     /*   new StringDialogCallback(this) {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                Log.e("jiang", "成功了" + s);
                                Type type = new TypeToken<CommList<ProductBean>>() {
                                }.getType();
                                CommList<ProductBean> dataPage = new Gson().fromJson( s.toString(), type);
                                more = dataPage.getData().getMore();
                                currentPage = dataPage.getData().getPage();
                                List<ProductBean> lists = dataPage.getData().getItems();
                                list.addAll(lists);
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                Log.e("jiang", "出错了" + "");
                            }
                        }*/

                );
    }

    @Override
    public void onPullDownRefresh(PullRefreshBase<ListView> refreshView) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPage = 1;
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

}
