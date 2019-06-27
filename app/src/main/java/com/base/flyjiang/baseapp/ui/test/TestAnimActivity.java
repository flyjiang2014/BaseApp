package com.base.flyjiang.baseapp.ui.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.base.flyjiang.baseapp.R;
import com.carking.quotationlibrary.utils.AnimUtil;

public class TestAnimActivity extends AppCompatActivity {
    private ImageView img_anim;
    private Button btn_change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_anim);
        img_anim = (ImageView) findViewById(R.id.img_anim);
        btn_change= (Button) findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimUtil.startShow(img_anim, 90, 3000, 1500);
            }
        });
    }
}
