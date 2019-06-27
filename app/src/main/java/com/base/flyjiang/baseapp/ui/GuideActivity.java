package com.base.flyjiang.baseapp.ui;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.base.BaseDemoActivity;
import com.carking.quotationlibrary.utils.AppUtil;
import com.carking.quotationlibrary.utils.SharepreferenceUtil;
import com.carking.quotationlibrary.view_guide.ParallaxContainer;

/**
 * 作者：flyjiang
 * 时间: 2015/7/13 14:43
 * 说明: 引导页
 */
public class GuideActivity extends BaseDemoActivity {
    /**
     * 引导页控件
     */
    private ParallaxContainer mParallaxContainer;
    /**
     * 圆点显示
     */
  //  private LinearLayout mLinearLayoutPoint;
    /**
     * 是否显示引导页的保存数据KEY值
     */
    private final String GUIDE_OR_NOT = "GUIDE_OR_NOT";

    @Override
    public void onCreateBefore() {
        super.onCreateBefore();
        //如果保存的版本号和当前版本号相同，则直接进入欢迎页
        if (SharepreferenceUtil.getString(GUIDE_OR_NOT).equals(AppUtil.getAppVersionName(mContext))) {
            Intent intent = new Intent(mContext, WelcomeActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        //小于安卓4.4
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            // 不显示程序的标题栏
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            // 不显示系统的标题栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setStatusColor(getResources().getColor(R.color.transparent));
        //不显示标题栏
        setIsShowTitle(false);
    }


    @Override
    public int setBaseContentView() {
        return R.layout.activity_guide;
    }

    @Override
    public void init() {
        mParallaxContainer = (ParallaxContainer) findViewById(R.id.guide_parallax);
     //   mLinearLayoutPoint = (LinearLayout) findViewById(R.id.guide_point_layout);
        //设置圆点控件
    //    mParallaxContainer.setPointLayout(mLinearLayoutPoint);
        //设置引导页
        mParallaxContainer.setupChildren(getLayoutInflater(), R.layout.guide_1, R.layout.guide_2, R.layout.guide_3, R.layout.guide_4);

    }

    public void goMainActivity(View view) {
        switch (view.getId()) {
            case R.id.guide_go:
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();
                //保存当前版本号
                SharepreferenceUtil.saveString(GUIDE_OR_NOT, AppUtil.getAppVersionName(mContext));
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //禁止返回
    }
}
