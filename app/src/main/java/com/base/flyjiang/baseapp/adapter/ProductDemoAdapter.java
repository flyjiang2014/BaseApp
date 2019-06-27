package com.base.flyjiang.baseapp.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.bean.ProductBean;

import net.anumbrella.pullrefresh.Adapter.BaseViewHolder;
import net.anumbrella.pullrefresh.Adapter.RecyclerAdapter;

import java.util.List;

/**
 * Created by ${flyjiang} on 2017/2/6.
 * 文件说明：
 */
public class ProductDemoAdapter extends RecyclerAdapter<ProductBean> {

    public ProductDemoAdapter(Context context) {
        super(context);
    }

    public ProductDemoAdapter(Context context, List<ProductBean> objects) {
        super(context, objects);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        ProductBean productBean = mContents.get(position);

       // ((ViewHolder) holder).tvName.

        if(position==3){
           // ((ViewHolder) holder).setVisibility(View.GONE);
        }

    }

    class ViewHolder extends BaseViewHolder<ProductBean> {
         private TextView tvName;
         private TextView tvNowPrice;
         private  TextView tvOriginalPrice;
         private  TextView tvSaleTime;
         private TextView tvSaleNum;
         private TextView tvState ;

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.product_item_layout);
            tvName = $(R.id.tvName);
            tvNowPrice =$(R.id.tvNowPrice);
            tvOriginalPrice =$(R.id.tvOriginalPrice);
            tvSaleTime =$(R.id.tvSaleTime);
            tvSaleNum =$(R.id.tvSaleNum);
            tvState =$(R.id.tvState);
        }

       @Override
        public void setData(ProductBean data) {
            super.setData(data);
            tvName.setText(data.getName());
            tvNowPrice.setText(data.getPresentPrice());
            tvOriginalPrice.setText(data.getOriginalPrice());
            tvSaleTime.setText(data.getEnterEndTime());
        }
    }
}
