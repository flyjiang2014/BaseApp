package com.base.flyjiang.baseapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.base.flyjiang.baseapp.R;
import com.carking.quotationlibrary.utils.PhoneUtil;
import com.carking.quotationlibrary.utils.ViewUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * 作者：蔡有飞
 * 时间: 2015/6/25 11:08
 * 邮箱：caiyoufei@looip.cn
 * 说明: Viewflow适配器
 */
public class ViewFlowAdapter extends CommonAdapter<String> {
    private RelativeLayout.LayoutParams layoutParams;
    private boolean isClick = false;

    public ViewFlowAdapter(Context context, List<String> data, int heightViewFlow, boolean isClick) {
        super(context, data);
        this.isClick = isClick;
        layoutParams = new RelativeLayout.LayoutParams(PhoneUtil.getScreenWidth(mContext), heightViewFlow > 0 ? heightViewFlow : (int) ViewUtil.dip2px(mContext, 200));
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;// 返回很大的值使得getView中的position不断增大来实现循环
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_view_flow, null);
            holder.mSimpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.item_view_flow_iv);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (mData != null && mData.size() > 0) {
            final int position2 = position % mData.size();
            holder.mSimpleDraweeView.setLayoutParams(layoutParams);
            Log.e("jiang",mData.get(position2));
            holder.mSimpleDraweeView.setImageURI(Uri.parse(mData.get(position2)));
        }
        return view;
    }
    class ViewHolder {
        SimpleDraweeView mSimpleDraweeView;
    }
}
