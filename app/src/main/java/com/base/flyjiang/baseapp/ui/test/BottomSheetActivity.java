package com.base.flyjiang.baseapp.ui.test;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.base.flyjiang.baseapp.R;

public class BottomSheetActivity extends AppCompatActivity {

    View view;
    BottomSheetDialog mBottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);
        view = getLayoutInflater().inflate(R.layout.test01_layout, null);
        findViewById(R.id.tv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBottomSheetDialog==null){
                    mBottomSheetDialog = new BottomSheetDialog(BottomSheetActivity.this);
                    mBottomSheetDialog.setContentView(view);
                    mBottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);//设置背景透明
                    final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
                    mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }
                    });
                }
                mBottomSheetDialog.show();
            }
        });
    }
}
