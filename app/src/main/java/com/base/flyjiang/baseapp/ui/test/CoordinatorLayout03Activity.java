package com.base.flyjiang.baseapp.ui.test;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.widget.ImageView;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.base.BaseDemoActivity;
import com.base.flyjiang.baseapp.bean.AppBarStateChangeListener;
import com.base.flyjiang.baseapp.bean.State;

import in.srain.cube.util.CLog;

public class CoordinatorLayout03Activity extends BaseDemoActivity {
    private AppBarLayout appBarLayout;
    private ImageView img;
    @Override
    public void onCreateBefore() {
        super.onCreateBefore();
      //  setIsShowTitle(false);
        setTetileMiddleText("测试2");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public int setBaseContentView() {
        setIsShowTitle(false);
        return R.layout.activity_coordinator_layout03;
    }

    @Override
    public void init() {
        appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        img = (ImageView) findViewById(R.id.img_ee4);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                CLog.d("STATE", state.name());
                if( state == State.EXPANDED ) {
                    //展开状态
                    img.setVisibility(View.GONE);
                }else if(state == State.COLLAPSED){
                    img.setVisibility(View.VISIBLE);
                    //折叠状态

                }else {

                    //中间状态

                }
            }
        });
    }
}
