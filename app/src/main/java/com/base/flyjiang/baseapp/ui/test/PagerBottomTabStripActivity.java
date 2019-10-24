package com.base.flyjiang.baseapp.ui.test;

import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.base.BaseDemoActivity;
import com.base.flyjiang.baseapp.ui.fragment.TestFragment;
import com.base.flyjiang.baseapp.ui.fragment.TestFragment01;
import com.base.flyjiang.baseapp.ui.fragment.TestFragment02;
import com.base.flyjiang.baseapp.ui.fragment.TestFragment03;
import com.carking.quotationlibrary.base.BaseFragmentV4;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class PagerBottomTabStripActivity extends BaseDemoActivity {

    private TestFragment mTestFragment ;
    private TestFragment01 mTestFragment01 ;
    private TestFragment02 mTestFragment02 ;
    private TestFragment03 mTestFragment03 ;
    private BaseFragmentV4 mBaseFragment;
    private FragmentTransaction mTransaction;
    private PageNavigationView tab;

    @Override
    public int setBaseContentView() {
        return R.layout.activity_pager_bottom_tab_strip;
    }

    @Override
    public void init() {
         tab = (PageNavigationView) findViewById(R.id.tab);
        //注意这里调用了custom()方法
        NavigationController navigationController = tab.custom()
                .addItem(newItem(R.drawable.tabbar_home_n, R.drawable.tabbar_home_o, "Recents"))
                .addItem(newItem(R.drawable.tabbar_home_n,R.drawable.tabbar_home_o,"Favorites"))
                .addItem(newItem(R.drawable.tabbar_home_n,R.drawable.tabbar_home_o,"Nearby"))
                .addItem(newItem(R.drawable.tabbar_home_n, R.drawable.tabbar_home_o, "fly"))
                .build();

        mTestFragment = TestFragment.getInstance(0);
        mTestFragment01 = TestFragment01.getInstance(1);
        mTestFragment02 = TestFragment02.getInstance(2);
        mTestFragment03 = TestFragment03.getInstance(3);
        mBaseFragment = mTestFragment;
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.fragment_container, mBaseFragment).commit();
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                //选中时触发
                switch (index) {
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
            public void onRepeat(int index) {
                //重复选中时触发
            }
        });



    }

    //创建一个Item
    private BaseTabItem newItem(int drawable,int checkedDrawable,String text) {
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(0xFF009688);
        return normalItemView;
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
