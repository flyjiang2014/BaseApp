package com.base.flyjiang.baseapp.ui.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.Widget.DividerGridItemDecoration;
import com.base.flyjiang.baseapp.adapter.ProductDemoAdapter;
import com.base.flyjiang.baseapp.bean.CommonReturnData;
import com.base.flyjiang.baseapp.bean.ProductBean;
import com.base.flyjiang.baseapp.bean.SignalReturnData;
import com.base.flyjiang.baseapp.callback.DialogCallback;
import com.carking.quotationlibrary.utils.ToastUtil;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import net.anumbrella.pullrefresh.Adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecycleTestActivity extends AppCompatActivity {

    RecyclerView rec_pro;
    ProductDemoAdapter productDemoAdapter;
    List<ProductBean> list = new ArrayList<>();
    private Context mContext;
    private Button ceshi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_recycle_test);
        rec_pro = (RecyclerView) findViewById(R.id.rec_pro);
        ceshi = (Button) findViewById(R.id.ceshi);
        ceshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StyledDialog.buildMdAlert("title", "测试加载中...", new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        //showToast("onFirst");
                    }

                    @Override
                    public void onSecond() {
                        //showToast("onSecond");
                    }

                    @Override
                    public void onThird() {

                        //showToast("onThird");
                    }

                })
                        .setBtnSize(20)
                        .setBtnText("i", "b", "3")
                        .show();
            }
        });
        productDemoAdapter = new ProductDemoAdapter(mContext, list);

       /* rec_pro.setLayoutManager(new LinearLayoutManager(this));
        rec_pro.setLayoutManager(new LinearLayoutManager(this,DividerItemDecoration.HORIZONTAL_LIST,false));*/

        rec_pro.setLayoutManager(new GridLayoutManager(this,2));

     /*  rec_pro.getRefreshableView().addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL_LIST, 40, (R.color.red)));*/
  /* rec_pro.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST, 10, R.color.red));*/

        /*rec_pro.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL_LIST, R.drawable.divider_drawable));*/

      /* rec_pro.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_drawable01));*/

       rec_pro.addItemDecoration(new DividerGridItemDecoration(this,
                 R.drawable.divider_drawable01));


       /* rec_pro.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_drawable01));*/

 /*  rec_pro.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));*/
     /*   rec_pro.getRefreshableView().addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL_LIST));*/

        rec_pro.setAdapter(productDemoAdapter);
        productDemoAdapter.setmOnItemClickLitener(new RecyclerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.showToast(list.get(position).getStartDate());
            }

            @Override
            public boolean onItemLongClick(View view, int position) {
                return false;
            }
        });

        getData();
    }

    public void getData() {

    //  OkGo.<CommonReturnData<SignalReturnData>>get("https://shopapi.kdding.com/shop_m/viewspot/listTicketsByState.do")
        OkGo.<CommonReturnData<SignalReturnData>>get("http://218.90.187.218:8888/shop_m/viewspot/listTicketsByState.do")
//            .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
//                .cacheKey("ceshi")
//                .cacheTime(3000)
                .params("token", "11e83617c2a570903497f68bd9c3bf27")
                .params("shopId", "88")
                .params("currentPage", "1")
                .params("pageSize", "10")
                .execute( new DialogCallback<CommonReturnData<SignalReturnData>>(this) {
//                              @Override
//                              public void onSuccess(Response<CommonReturnData<SignalReturnData>> response) {
//                                  CommonReturnData<SignalReturnData> returnData = response.body();
//                                  list.clear();
//                                        list.addAll(returnData.getData().getItems());
//                                        productDemoAdapter.notifyDataSetChanged();
//                              }

                              @Override
                              public void onSuccess(CommonReturnData<SignalReturnData> returnData) {
                                  list.clear();
                                  list.addAll(returnData.getData().getItems());
                                  productDemoAdapter.notifyDataSetChanged();
                              }

                              @Override
                              public void onError(Response<CommonReturnData<SignalReturnData>> response) {
                                  super.onError(response);
                              }
                          }
//                        new DialogCallback<CommonReturnData<SignalReturnData>>(this) {
//                            @Override
//                            public void onBefore(BaseRequest request) {
//                                super.onBefore(request);
//                            }
//
//                            @Override
//                            public void onSuccess(final CommonReturnData<SignalReturnData> responseData, Call call, Response response) {
//                                if (responseData.getSuccess() == 1) {
//                                    if (responseData.getData().getItems() != null && responseData.getData().getItems().size() > 0) {
//
//                                        SignalReturnData signalReturnData = responseData.getData();
//
//                                        List<String> aa = new ArrayList<String>();
//                                        aa.add("1");
//                                        aa.add("2");
//                                        aa.add("3");
//
//                                        signalReturnData.setList(aa);
//                                        List<ProductBean>  productBeens= new ArrayList<ProductBean>();
//                                        productBeens.addAll(responseData.getData().getItems());
//                                        Log.e("jiang09",responseData.getData().getItems().size()+"");
//                                        signalReturnData.setItems(productBeens);
//
//*//*
//                                        for (int i = 0; i <responseData.getData().getItems().size() ; i++) {
//                                           ProductBean p= new ProductBean("12",i+"");
//                                            items.add(p);
//                                        }
//                                        signalReturnData.setItems(items);
//
//                                        for (int i = 0; i < responseData.getData().getItems().size(); i++) {
//                                            ProductBean p = new ProductBean("12", i + "");
//                                            p .save();
//                                            items.add(p);
//                                        }
//                                        signalReturnData.setItems(items);*//*
//                                        signalReturnData.save();
//                                        DataSupport.saveAll(productBeens);
//
//                                        list.clear();
//                                        list.addAll(responseData.getData().getItems());
//                                        productDemoAdapter.notifyDataSetChanged();
//
//                                    } else { //list无数据
//                                        //    setEmptyViewText("无更多数据");
//                                    }
//                                } else {
//                                    ToastUtil.showToast(responseData.getMessage());
//                                    //  pullRefreshListView.showErrorView();
//                                    //  setEmptyViewWithImageText("出错了", true);
//                                }
//                            }
//
//                         @Override
//                            public void onCacheSuccess(CommonReturnData<SignalReturnData> responseData, Call call) {
//                                super.onCacheSuccess(responseData, call);
//                                Log.e("jiang", "这里来了onCacheSuccess");
//                                list.clear();
//                                list.addAll(responseData.getData().getItems());
//                                productDemoAdapter.notifyDataSetChanged();
//                            }
//
//                            @Override
//                            public void onError(Call call, Response response, Exception e) {
//                                super.onError(call, response, e);
//                            //    ToastUtil.showToast("这里");
//                                SignalReturnData signalReturnData = DataSupport.findFirst(SignalReturnData.class,true);
//                                Log.e("jiang", signalReturnData.toString() + "size=" + signalReturnData.getItems().size());
//                               // Log.e("jiang",signalReturnData.getItems().get(0).getEndDate()+"");
//                                list.clear();
//                                list.addAll(signalReturnData.getItems());
//                                productDemoAdapter.notifyDataSetChanged();
//                            }
//                        }
                );
    }

}
