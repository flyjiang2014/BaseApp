package com.base.flyjiang.baseapp.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.bean.ProductBean;
import com.carking.quotationlibrary.view.ViewHolder;

import java.util.List;

/**
 * 商品适配器
 *
 * @author zcxiao
 */
public class ProductAdapter extends BaseAdapter {
    Context mContext = null;
    private List<ProductBean> mList;
    public final int RESULT_ADD_SUCCESS = 1;

    public ProductAdapter(Context mContext, List<ProductBean> tempList) {
        this.mContext = mContext;
        this.mList = tempList;
    }

    @Override
    public View getView(int poisition, View conventView, ViewGroup parent) {
        if (conventView == null) {
            conventView = LayoutInflater.from(mContext)
                    .inflate(R.layout.product_item_layout, null);
        }

        TextView tvName =  ViewHolder.get(conventView, R.id.tvName);
        TextView tvNowPrice =  ViewHolder.get(conventView, R.id.tvNowPrice);
        TextView tvOriginalPrice =  ViewHolder.get(conventView, R.id.tvOriginalPrice);
        TextView tvSaleTime = ViewHolder.get(conventView, R.id.tvSaleTime);
        TextView tvSaleNum = ViewHolder.get(conventView, R.id.tvSaleNum);
        TextView tvState = ViewHolder.get(conventView, R.id.tvState);

        TextView tvOffline = ViewHolder.get(conventView, R.id.tvOffline);
        TextView tvUpdate =  ViewHolder.get(conventView, R.id.tvUpdate);
        TextView tvCancelOnline = ViewHolder.get(conventView, R.id.tvCancelOnline);
        TextView tvOnlineAgain =  ViewHolder.get(conventView, R.id.tvOnlineAgain);

        final ProductBean mProductBean = mList.get(poisition);
        tvName.setText(mProductBean.getName());
        tvNowPrice.setText("￥ " + mProductBean.getPresentPrice());

        tvOriginalPrice.setText("￥ " + mProductBean.getOriginalPrice());
        tvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        tvSaleTime.setText(mProductBean.getStartDate() + "至" + mProductBean.getEndDate());

        tvSaleNum.setText("已售：" + mProductBean.getSaleNum() + "      剩余：" + (mProductBean.getStockNum() - mProductBean.getSaleNum()));

        switch (mProductBean.getState()) {//状态  0：新建，1:上线,2:下线,9：删除
            case 0:
                tvState.setText("●待上线");
                tvOffline.setVisibility(View.GONE);
                tvUpdate.setVisibility(View.VISIBLE);
                tvCancelOnline.setVisibility(View.VISIBLE);
                tvOnlineAgain.setVisibility(View.GONE);
                break;
            case 1:
                tvState.setText("●上线中");

                tvOffline.setVisibility(View.VISIBLE);
                tvUpdate.setVisibility(View.GONE);
                tvCancelOnline.setVisibility(View.GONE);
                tvOnlineAgain.setVisibility(View.GONE);
                break;
            case 2:
                tvState.setText("●已下线");

                tvOffline.setVisibility(View.GONE);
                tvUpdate.setVisibility(View.GONE);
                tvCancelOnline.setVisibility(View.GONE);
                tvOnlineAgain.setVisibility(View.VISIBLE);
                break;
            case 9:
                tvState.setText("●已删除");
                tvOffline.setVisibility(View.GONE);
                tvUpdate.setVisibility(View.GONE);
                tvCancelOnline.setVisibility(View.GONE);
                tvOnlineAgain.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        return conventView;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

}
