package com.base.flyjiang.baseapp.ui.test;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.base.BaseDemoActivity;

public class SecondDemoActivity extends BaseDemoActivity implements View.OnClickListener{

    private Button btn_baidu;
    private TextView tv_button;

    @Override
    public int setBaseContentView() {
        return R.layout.activity_second_demo;
    }

    @Override
    public void init() {
        btn_baidu = (Button) findViewById(R.id.btn_baidu);
        btn_baidu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_baidu:
                Intent intent = new Intent(mContext,BaiduDemoActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
