package com.base.flyjiang.baseapp.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by ${flyjiang} on 2018/3/22.
 * 文件说明：
 */

    public class StickScrollView extends ScrollView {
    private OnScrollListener onScrollListener;

    public StickScrollView(Context context) {
        this(context, null);
    }

    public StickScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /**
     * 设置滚动接口
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(onScrollListener != null){
            onScrollListener.onScroll(t);
        }
    }

    /**
     *
     * 滚动的回调接口
     *
     * @author xiaanming
     *
     */
    public interface OnScrollListener{
        /**
         * 回调方法， 返回MyScrollView滑动的Y方向距离
         * @param scrollY
         */
         void onScroll(int scrollY);
    }
}
