package com.carking.quotationlibrary.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：flyjiang
 * 时间: 2015/7/1 14:09
 * 邮箱：caiyoufei@looip.cn
 * 说明: 基于APP的Fragment,最低API为11
 */
public abstract class BaseFragmentApp extends Fragment {
    protected View rootView;
    protected Context mContext;
    protected Activity mActivity;
    private boolean hasInitData;

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
}
