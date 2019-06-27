package com.base.flyjiang.baseapp.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.flyjiang.baseapp.R;
import com.carking.quotationlibrary.base.BaseActivity;
import com.weavey.loading.lib.LoadingLayout;

/**
 * 作者：flyjiang
 * 时间: 2015/7/13 19:42
 */
public abstract class BaseDemoActivity extends BaseActivity{
    /**
     * 上下文
     */
    protected Context mContext;

    /**
     * 当页面只有一个请求时，可以使用这个变量记录请求结果数据,如果有多个请求，请自行定义多个结果
     */
    protected String mRequestResult = "";
    /**
     * 带图片的空布局
     */
    private View mEmptyViewLayout;
    /**
     * 带图片的空布局文字
     */
    private TextView mEmptyViewTV;
    /**
     * 带图片的空布局图片
     */
    private ImageView mEmptyViewIV;
    /**
     * 上次按下时间
     */
    private static long lastClickTime ;

    private LoadingLayout loading;


    @Override
    public void onCreateBefore() {
        super.onCreateBefore();
        mContext = this;
    }

    /**
     * 设置空View(deprecated by setEmptyViewWithImage)
     *
     * @param listView 需要设置空View的ListView
     */
    @Deprecated
    public void setEmptyView(ListView listView) {
        setEmptyViewWithImage(listView);
    }



    /**
     * 设置空View文字(deprecated by setEmptyViewWithImageText)
     *
     * @param text 需要显示的文字
     */
    @Deprecated
    public void setEmptyViewText(String text) {
        setEmptyViewWithImageText(text, false);
    }



    public void setEmptyViewWithImage(ListView listView) {
        if (mEmptyViewLayout == null) {
            mEmptyViewLayout = getLayoutInflater().inflate(R.layout.empty_image_layout01, null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mEmptyViewLayout.setLayoutParams(params);
            mEmptyViewTV = (TextView) mEmptyViewLayout.findViewById(R.id.empty_tv);
            mEmptyViewIV = (ImageView) mEmptyViewLayout.findViewById(R.id.empty_image);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mScreentWidth / 4, mScreentWidth / 4);
            layoutParams.addRule(RelativeLayout.ABOVE, mEmptyViewTV.getId());
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            mEmptyViewIV.setLayoutParams(layoutParams);
            ((ViewGroup) listView.getParent()).addView(mEmptyViewLayout);
            listView.setEmptyView(mEmptyViewLayout);
        }
    }


    /**
     * 设置空View文字
     *
     * @param text 需要显示的文字
     */
    public void setEmptyViewWithImageText(String text, boolean isShowImage) {
        if (mEmptyViewLayout != null) {
            mEmptyViewTV.setText(text);
            if (isShowImage) {
                mEmptyViewIV.setVisibility(View.VISIBLE);
            } else {
                mEmptyViewIV.setVisibility(View.GONE);
            }
        }
    }

    public LoadingLayout getLoading() {
        return loading;
    }

}
