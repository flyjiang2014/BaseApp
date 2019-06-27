package com.base.flyjiang.baseapp.ui.test;

import android.os.Bundle;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.base.BaseDemoActivity;

public class CoordinatorLayoutActivity  extends BaseDemoActivity {
    @Override
    public void onCreateBefore() {
        super.onCreateBefore();
      //  setIsShowTitle(false);
        setTetileMiddleText("测试1");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public int setBaseContentView() {
        return R.layout.activity_coordinator_layout;
    }

    @Override
    public void init() {

    }
}
