package com.base.flyjiang.baseapp.ui.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.basepopup.DemoPopup;
import com.carking.quotationlibrary.utils.ToastUtil;

import razerdp.basepopup.BasePopupWindow;

public class PopupTestActivity extends AppCompatActivity {
    DemoPopup demoPopup;
    TextView tv_pop4;
    TextView tv_pop1;
    TextView tv_pop2;
    TextView tv_pop3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_test);
        tv_pop4 = (TextView) findViewById(R.id.tv_pop4);
        tv_pop1 = (TextView) findViewById(R.id.tv_pop1);
        tv_pop2 = (TextView) findViewById(R.id.tv_pop2);
        tv_pop3 = (TextView) findViewById(R.id.tv_pop3);
        demoPopup=new DemoPopup(PopupTestActivity.this,600,800);
        demoPopup.setBackPressEnable(false);
        demoPopup.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ToastUtil.showToast("onDismiss");
            }
        });
    }

    public void popup01(View view){
        ToastUtil.showToast("popup01");
        demoPopup.showPopupWindow(tv_pop1);
    }
    public void popup02(View view){
        ToastUtil.showToast("popup02");

    }
    public void popup03(View view){
        ToastUtil.showToast("popup03");
    }
}
