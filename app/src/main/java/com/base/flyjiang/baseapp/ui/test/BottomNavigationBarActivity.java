package com.base.flyjiang.baseapp.ui.test;

import android.support.v4.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.base.BaseDemoActivity;
import com.base.flyjiang.baseapp.ui.fragment.TestFragment;
import com.base.flyjiang.baseapp.ui.fragment.TestFragment01;
import com.base.flyjiang.baseapp.ui.fragment.TestFragment02;
import com.base.flyjiang.baseapp.ui.fragment.TestFragment03;
import com.carking.quotationlibrary.base.BaseFragmentV4;

public class BottomNavigationBarActivity extends BaseDemoActivity {
    private BottomNavigationBar bottomNavigationBar;
    private TestFragment mTestFragment ;
    private TestFragment01 mTestFragment01 ;
    private TestFragment02 mTestFragment02 ;
    private TestFragment03 mTestFragment03 ;
    private BaseFragmentV4 mBaseFragment;
    FragmentTransaction mTransaction;

    @Override
    public int setBaseContentView() {
        return R.layout.activity_bottom_navigation_bar;
    }

    @Override
    public void init() {
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
      //  bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
//        http://www.jianshu.com/p/134d7847a01e  模式设置说明
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.tabbar_home_n,"测试").setInactiveIconResource(R.drawable.tabbar_home_o))
                .addItem(new BottomNavigationItem(R.drawable.tabbar_home_n,"测试").setInactiveIconResource(R.drawable.tabbar_home_o))
                .addItem(new BottomNavigationItem(R.drawable.tabbar_home_n,"测试").setInactiveIconResource(R.drawable.tabbar_home_o))
                .addItem(new BottomNavigationItem(R.drawable.tabbar_home_n,"测试").setInactiveIconResource(R.drawable.tabbar_home_o))
                .setFirstSelectedPosition(0) //默认第一个选中
               // .setMode(BottomNavigationBar.MODE_FIXED)
              //  .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .setBarBackgroundColor(R.color.white)
                .initialise();

        mTestFragment = TestFragment.getInstance(0);
        mTestFragment01 = TestFragment01.getInstance(1);
        mTestFragment02 = TestFragment02.getInstance(2);
        mTestFragment03 = TestFragment03.getInstance(3);
        mBaseFragment = mTestFragment;
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.fragment_container, mBaseFragment).commit();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0 :
                      switchContent(mBaseFragment,mTestFragment);
                        break;
                    case 1:
                        switchContent(mBaseFragment,mTestFragment01);
                        break;
                    case 2:
                        switchContent(mBaseFragment,mTestFragment02);
                        break;
                    case 3:
                        switchContent(mBaseFragment,mTestFragment03);
                        break;

                }
            }
               @Override
                public void onTabUnselected ( int position){
                }
                @Override
                public void onTabReselected ( int position){
                }
        });
    }

    /**
     * 切换Fragment
     *
     * @param from 当前显示的Fragment
     * @param to   需要显示的Fragment
     */
    public void switchContent(BaseFragmentV4 from, BaseFragmentV4 to) {
        if (mBaseFragment != to) {
            mBaseFragment = to;
            mTransaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                mTransaction.hide(from).add(R.id.fragment_container, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                mTransaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }
}
