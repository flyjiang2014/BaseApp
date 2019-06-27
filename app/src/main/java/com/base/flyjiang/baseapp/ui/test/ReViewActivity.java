package com.base.flyjiang.baseapp.ui.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.base.flyjiang.baseapp.R;

import de.greenrobot.event.EventBus;

public class ReViewActivity extends AppCompatActivity {
    private Button btn_shuaxin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_view);
        btn_shuaxin = (Button) findViewById(R.id.btn_shuaxin);
        btn_shuaxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post("event");
            }
        });
    }
}
