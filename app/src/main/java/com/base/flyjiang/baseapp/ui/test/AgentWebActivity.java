package com.base.flyjiang.baseapp.ui.test;

import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.base.BaseDemoActivity;
import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;

public class AgentWebActivity extends BaseDemoActivity {
    protected AgentWeb mAgentWeb;
    private LinearLayout mLinearLayout;

    @Override
    public int setBaseContentView() {
        return R.layout.activity_agent_web;
    }

    @Override
    public void init() {
        mLinearLayout = (LinearLayout) findViewById(R.id.container);

        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(mLinearLayout,new LinearLayout.LayoutParams(-1,-1) )//
                .useDefaultIndicator()//
                .defaultProgressBarColor()
                .setReceivedTitleCallback(mCallback)
                .setSecutityType(AgentWeb.SecurityType.strict)
                .createAgentWeb()//
                .ready()
                .go("http://www.baidu.com");
    }


    private ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            setTetileMiddleText(title);
        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.destroy();
    }
}
