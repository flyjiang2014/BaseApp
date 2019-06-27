package com.base.flyjiang.baseapp.ui.test;

import android.widget.TextView;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.base.BaseDemoActivity;

public class AboveAllActivity extends BaseDemoActivity {
    private TextView tv_now;

    @Override
    public int setBaseContentView() {
        return R.layout.activity_above_all;
    }

    @Override
    public void init() {
        tv_now = (TextView) findViewById(R.id.tv_now);
    }
}
