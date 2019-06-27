package com.carking.quotationlibrary.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.carking.quotationlibrary.R;
import com.carking.quotationlibrary.utils.ActivityUtil;
import com.carking.quotationlibrary.utils.KeyboardUtil;
import com.carking.quotationlibrary.utils.PhoneUtil;
import com.carking.quotationlibrary.view.CustomProgressDialog;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * 作者：flyjiang
 * 时间: 2015/5/26 17:49
 * 邮箱：caiyoufei@looip.cn
 * 说明: 基类
 */
public abstract class BaseActivity extends FragmentActivity {
    /**
     * TAG
     */
    protected final String TAG = this.getClass().getSimpleName();
    /**
     * 布局根View
     */
    private RelativeLayout mBaseContainer;
    /**
     * 状态栏颜色
     */
    private int statusColor = -1;
    /**
     * 是否不填充状态栏
     */
    private boolean isNotFillSysState = false;
    /**
     * 是否显示标题
     */
    private boolean isShowTitle = true;
    /**
     * 屏幕宽度
     */
    protected int mScreentWidth;
    /**
     * 屏幕高度
     */
    protected int mScreentHeight;
    /**
     * 状态栏高度
     */
    protected int mStatusHeight;
    /**
     * 标题布局
     */
    private RelativeLayout mTitleLayout;
    /**
     * 标题左边布局
     */
    protected RelativeLayout mTitleLeftRelativeLayout;
    /**
     * 标题右边布局
     */
    protected RelativeLayout mTitleRightRelativeLayout;
    /**
     * 标题中间布局
     */
    protected RelativeLayout mTitleMiddleRelativeLayout;
    /**
     * 标题左边图片
     */
    private ImageView mTitleLeftImageView;
    /**
     * 标题中间文字
     */
    private TextView mTitleMiddleTextView;
    /**
     * 标题右边图片
     */
    private ImageView mTitleRightImageView;
    /**
     * 标题右边文字
     */
    private TextView mTitleRightTextView;
    /**
     * 四种相对布局布局参数
     */
    protected RelativeLayout.LayoutParams mParamsRelativeMM;
    protected RelativeLayout.LayoutParams mParamsRelativeMW;
    protected RelativeLayout.LayoutParams mParamsRelativeWM;
    protected RelativeLayout.LayoutParams mParamsRelativeWW;

    /**
     * 动画加载中
     */
    public CustomProgressDialog customProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreateBefore();
        super.onCreate(savedInstanceState);
        // 添加当前Activity到所有管理列表
        ActivityUtil.addActivityALL(this);
        container();
        init();
        init(savedInstanceState);
    }

    /**
     * 在setContentView之前的一些操作
     */
    public void onCreateBefore() {
        mScreentWidth = PhoneUtil.getScreenWidth(this);
        mScreentHeight = PhoneUtil.getScreenHeight(this);
        mStatusHeight = PhoneUtil.getStatusHeight(this);
        mParamsRelativeMM = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mParamsRelativeMW = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mParamsRelativeWM = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mParamsRelativeWW = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置Activity的布局
     */
    private void container() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mBaseContainer = (RelativeLayout) inflater.inflate(R.layout.base_container, null);
        View view = inflater.inflate(setBaseContentView(), null);
        //如果版本高于安卓4.4则透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//安卓4.4
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
             tintManager.setStatusBarTintColor(statusColor == -1 ? getResources().getColor(R.color.transparent) : statusColor);
          //  tintManager.setStatusBarTintColor(statusColor);
        }
        if (!isNotFillSysState) {
            mBaseContainer.setClipToPadding(false);
            mBaseContainer.setFitsSystemWindows(false);
        }
        //标题布局控件
        mTitleLayout = (RelativeLayout) inflater.inflate(R.layout.title_layout, null);
        mTitleLeftRelativeLayout = (RelativeLayout) mTitleLayout.findViewById(R.id.title_layout_left);
        mTitleRightRelativeLayout = (RelativeLayout) mTitleLayout.findViewById(R.id.title_layout_right);
        mTitleMiddleRelativeLayout = (RelativeLayout) mTitleLayout.findViewById(R.id.title_layout_middle);
        mTitleLeftImageView = (ImageView) mTitleLayout.findViewById(R.id.title_layout_left_image);
        mTitleMiddleTextView = (TextView) mTitleLayout.findViewById(R.id.title_layout_middle_tv);
        mTitleRightImageView = (ImageView) mTitleLayout.findViewById(R.id.title_layout_right_image);
        mTitleRightTextView = (TextView) mTitleLayout.findViewById(R.id.title_layout_right_tv);
        //除标题外的布局参数
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //是否显示标题
        if (isShowTitle) {
            //如果需要填充标题栏,则标题栏顶部空出状态栏
            if (!isNotFillSysState && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mTitleLayout.setPadding(0, mStatusHeight, 0, 0);
            }
            //添加标题栏
            mBaseContainer.addView(mTitleLayout, mParamsRelativeMW);
            //添加左边点击事件
            mTitleLeftRelativeLayout.setOnClickListener(leftClickListener);
            //添加右边点击事件
            mTitleRightRelativeLayout.setOnClickListener(rightClickListener);
            //需要使用mTitleLayout.getId(),先要在XML中给mTitleLayout控件添加ID
            params.addRule(RelativeLayout.BELOW, mTitleLayout.getId());
        }
        mBaseContainer.addView(view, params);
        setContentView(mBaseContainer);
    }

    /**
     * 设置活动的布局ID
     *
     * @return
     */
    public abstract int setBaseContentView();

    /**
     * 初始化
     */
    public abstract void init();

    /**
     * 含传参的初始化
     *
     * @param savedInstanceState
     */
    public void init(Bundle savedInstanceState) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        // 添加当前Activity到可见管理列表
        ActivityUtil.addActivityVISIBLE(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 从可见管理列表移除当前Activity
        ActivityUtil.removeActivityInVISIBLE(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 添加当前Activity到不可见管理列表
        ActivityUtil.addActivityINVISIBLE(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 从不可见管理列表移除当前Activity
        ActivityUtil.removeActivityInINSIVIBLE(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 从所有管理列表移除当前Activity
        ActivityUtil.removeActivityInALL(this);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        //执行Activity切换动画
    }

    @Override
    public void finish() {
        super.finish();
        KeyboardUtil.closeKeyboard(this);
        //执行Activity切换动画
    }

    /**
     * 设置状态栏背景色(需要在onCreateBefore中才有效)
     *
     * @param statusColor 需要设置的状态栏颜色
     */
    public void setStatusColor(int statusColor) {
        this.statusColor = statusColor;
    }

    /**
     * 设置是否需要填充状态栏
     *
     * @param isNotFillSysState 需要View填充到状态栏设为false
     */
    public void setIsNotFillSysState(boolean isNotFillSysState) {
        this.isNotFillSysState = isNotFillSysState;
    }

    /**
     * 设置是否显示标题
     *
     * @param isShowTitle
     */
    public void setIsShowTitle(boolean isShowTitle) {
        this.isShowTitle = isShowTitle;
    }

    /**
     * 左边点击监听
     */
    private View.OnClickListener leftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onClickLeft(view);
        }
    };

    /**
     * 右边点击监听
     */
    private View.OnClickListener rightClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onClickRight(view);
        }
    };

    /**
     * 标题左边点击事件
     *
     * @param view 点击的View
     */
    public void onClickLeft(View view) {
        onBackPressed();
    }

    /**
     * 标题右边点击事件
     *
     * @param view 点击的View
     */
    public void onClickRight(View view) {
    }

    /**
     * 设置标题左边图片
     *
     * @param resId 图片资源
     */
    public void setTitleLeftImage(int resId) {
        if (mTitleLeftImageView != null) {
            mTitleLeftImageView.setImageResource(resId);
        }
    }

    /**
     * 设置中间标题
     *
     * @param title
     */
    public void setTetileMiddleText(String title) {
        if (mTitleMiddleTextView != null && !TextUtils.isEmpty(title)) {
            mTitleMiddleTextView.setText(title);
        }
    }

    /**
     * 设置标题右边图片
     *
     * @param resId 图片资源
     */
    public void setTitleRightImage(int resId) {
        if (mTitleRightImageView != null) {
            mTitleRightImageView.setImageResource(resId);
            mTitleRightRelativeLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置右边文字
     *
     * @param title
     */
    public void setTitleRightText(String title) {
        if (mTitleRightTextView != null && !TextUtils.isEmpty(title)) {
            mTitleRightTextView.setText(title);
            mTitleRightRelativeLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取标题左边ImageView
     *
     * @return 左边ImageView
     */
    public ImageView getmTitleLeftImageView() {
        return mTitleLeftImageView;
    }

    /**
     * 获取中间TextView
     *
     * @return 左边TextView
     */
    public TextView getmTitleMiddleTextView() {
        return mTitleMiddleTextView;
    }

    /**
     * 获取标题右边ImageView
     *
     * @return 右边ImageView
     */
    public ImageView getmTitleRightImageView() {
        return mTitleRightImageView;
    }

    /**
     * 获取右边TextView
     *
     * @return 右边TextView
     */
    public TextView getmTitleRightTextView() {
        return mTitleRightTextView;
    }

    /**
     * 获取标题栏
     *
     * @return 标题栏布局
     */
    public RelativeLayout getmTitleLayout() {
        return mTitleLayout;
    }

    /**
     * 获取标题右边布局
     *
     * @return 标题右边布局
     */
    public RelativeLayout getmTitleRightRelativeLayout() {
        return mTitleRightRelativeLayout;
    }

    /**
     *打开ProgressDialog
     */
    public void showingProgressDialog(String strHint) {
        try {
            if (customProgressDialog == null) {
                customProgressDialog = CustomProgressDialog.createLoadingDialog(this,strHint);
            }
            if (customProgressDialog.isShowing())
                return;
                customProgressDialog.show();

        } catch (Exception e) {

        }
    }
    /**
     * 关闭ProgressDialog
     */
    public void dismissProgressDialog() {
        try {
            if (customProgressDialog != null) customProgressDialog.dismiss();
        } catch (Exception e) {
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 1.0f全透明 0.5f半透明
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
        if (bgAlpha == 1) {
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        this.getWindow().setAttributes(lp);
    }
}
