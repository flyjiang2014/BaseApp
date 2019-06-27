package com.base.flyjiang.baseapp.ui.test;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.base.BaseDemoActivity;
import com.base.flyjiang.baseapp.ui.fragment.TestFragment;
import com.carking.quotationlibrary.utils.ToastUtil;
import com.carking.quotationlibrary.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

public class TestFragmengActivity extends BaseDemoActivity {
    private ViewPager mPager;
    /** PagerSlidingTabStrip的实例 */
    private PagerSlidingTabStrip mTabs;
    /** 获取当前屏幕的密度 */
    private DisplayMetrics dm;

    Button btn_reView ;

    private TestFragment fragment_01;
    private TestFragment fragment_02;
    private TestFragment fragment_03;

   private List<TestFragment> fragments = new ArrayList<TestFragment>();

    @Override
    public int setBaseContentView() {
        return R.layout.activity_test_fragmeng;
    }

    @Override
    public void init() {
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        fragment_01 =TestFragment.getInstance(0);
        fragment_02= TestFragment.getInstance(1);
        fragment_03 = TestFragment.getInstance(2);
        fragments.add(fragment_01);
        fragments.add(fragment_02);
        fragments.add(fragment_03);

        mTabs = (PagerSlidingTabStrip) findViewById(R.id.my_tabs);
        mPager  = (ViewPager) findViewById(R.id.my_pager);
        btn_reView = (Button) findViewById(R.id.btn_reView);
        btn_reView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReViewActivity.class);
                startActivity(intent);
            }
        });
        setTabsValue();
      //  mPager.setOffscreenPageLimit(3);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fragments.get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTabs.setViewPager(mPager);
    }

    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值
     */
    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
        mTabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        mTabs.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        mTabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab Indicator的高度
        mTabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, dm));
        // 设置Tab标题文字的大小
        mTabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, dm));
        // 设置Tab Indicator的颜色
        mTabs.setIndicatorColor(getResources().getColor(
                R.color.red));
        // 设置选中Tab文字的颜色
        mTabs.setSelectedTextColor(getResources().getColor(
                R.color.tab_text_select_color));
        // 设置文字最底部横线颜色
        mTabs.setUnderlineColor(getResources().getColor(
                R.color.gray_normal));
        // 设置横线高度
        mTabs.setIndicatorHeight(6);
    }
    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = { "我的收藏", "买车需求", "卖车需求" };

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mPager.getCurrentItem()==0){
            ToastUtil.showToast("ceshi");
        }
    }
}
