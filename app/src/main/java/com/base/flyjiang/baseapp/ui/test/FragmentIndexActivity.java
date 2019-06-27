package com.base.flyjiang.baseapp.ui.test;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.base.BaseDemoActivity;
import com.base.flyjiang.baseapp.ui.fragment.TestFragment;
import com.base.flyjiang.baseapp.ui.fragment.TestFragment01;
import com.base.flyjiang.baseapp.ui.fragment.TestFragment02;
import com.base.flyjiang.baseapp.ui.fragment.TestFragment03;
import com.carking.quotationlibrary.base.BaseFragmentV4;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class FragmentIndexActivity extends BaseDemoActivity implements View.OnClickListener {
    private TestFragment mTestFragment ;
    private TestFragment01 mTestFragment01 ;
    private TestFragment02 mTestFragment02 ;
    private TestFragment03 mTestFragment03 ;
    private BaseFragmentV4 mBaseFragment;
    FragmentTransaction mTransaction;
    private RadioGroup radioGroup;
    private RadioButton radioButton0;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    Badge badge;
    @Override
    public int setBaseContentView() {
        return R.layout.activity_fragment_index;
    }

    @Override
    public void init() {
      setTetileMiddleText("测试Fragment");
        initView();
        mTestFragment = TestFragment.getInstance(0);
        mTestFragment01 = TestFragment01.getInstance(1);
        mTestFragment02 = TestFragment02.getInstance(2);
        mTestFragment03 = TestFragment03.getInstance(3);
        mBaseFragment = mTestFragment;
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.fragment_container, mBaseFragment).commit();
    }

    public void initView(){
        radioGroup = (RadioGroup) findViewById(R.id.tabs_rg);
        radioButton0 = (RadioButton) findViewById(R.id.radioButton0);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton0.setOnClickListener(this);
        radioButton1.setOnClickListener(this);
        radioButton2.setOnClickListener(this);
        radioButton3.setOnClickListener(this);
         badge = new QBadgeView(this).bindTarget(radioButton2).setBadgeNumber(5).setGravityOffset(0,0,true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.radioButton0:
                changeTabState(0);
                switchContent(mBaseFragment, mTestFragment);
                mTestFragment.initData();
                badge.setBadgeNumber(99);
                break;
            case R.id.radioButton1:
                changeTabState(1);
                switchContent(mBaseFragment, mTestFragment01);
                mTestFragment01.initData();
                break;
            case R.id.radioButton2:
                changeTabState(2);
                switchContent(mBaseFragment,mTestFragment02);
                mTestFragment02.initData();
                break;
            case R.id.radioButton3:
                changeTabState(3);
                switchContent(mBaseFragment,mTestFragment03);
                mTestFragment03.initData();
                break;

        }
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

    /**
     * 改变底部栏的选中状态
     *
     * @return
     */
    public void changeTabState(int inx) {
        if (inx == 0) {
            radioButton2.setChecked(false);// badge绑定后设置一下
            radioButton0.setChecked(true);
        } else if (inx == 1) {
            radioButton2.setChecked(false); // badge绑定后设置一下
            radioButton1.setChecked(true);
        } else if (inx == 2) {
            radioButton0.setChecked(false);
            radioButton1.setChecked(false);
            radioButton3.setChecked(false);
            radioButton2.setChecked(true);
        } else if (inx == 3) {
            radioButton2.setChecked(false);
            radioButton3.setChecked(true);
        }
    }
}
