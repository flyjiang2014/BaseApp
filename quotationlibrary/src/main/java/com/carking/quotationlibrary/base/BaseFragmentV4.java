package com.carking.quotationlibrary.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.carking.quotationlibrary.R;
import com.carking.quotationlibrary.utils.PhoneUtil;


/**
 * 作者：flyjiang
 * 时间: 2015/5/28 12:25
 * 邮箱：caiyoufei@looip.cn
 * 说明: 基于V4包的Fragment，为了使用FragmentPagerAdapter
 */
public abstract class BaseFragmentV4 extends Fragment {
    protected final String TAG = this.getClass().getSimpleName();
    protected View rootView;
    protected Context mContext;
    protected Activity mActivity;
    private boolean hasInitData;
    private View mEmptyViewLayout;
    private TextView mEmptyViewTV;
    private ImageView mEmptyViewIV;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        mContext = getActivity();
        mActivity = (Activity) mContext;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = initView(inflater);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!hasInitData) {
            initData();
            hasInitData = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (rootView != null && rootView.getParent() != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
    }

    /**
     * 子类实现初始化View操作
     */
    protected abstract View initView(LayoutInflater inflater);

    /**
     * 子类实现初始化数据操作(子类自己调用)
     */
    public abstract void initData();

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
            mEmptyViewLayout = getActivity().getLayoutInflater().inflate(R.layout.empty_image_layout01, null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mEmptyViewLayout.setLayoutParams(params);
            mEmptyViewTV = (TextView) mEmptyViewLayout.findViewById(R.id.empty_tv);
            mEmptyViewIV = (ImageView) mEmptyViewLayout.findViewById(R.id.empty_image);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(PhoneUtil.getScreenWidth(getActivity()) / 4,  PhoneUtil.getScreenHeight(getActivity())/4);
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
}
