package com.base.flyjiang.baseapp.ui.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.base.DemoApplication;
import com.base.flyjiang.baseapp.ui.chat.ChatConversationListActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import hyphenate.chatui.DemoHelper;

public class TestHuanxinChatActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_regist, btn_login, btn_out, btn_conversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_huanxin_chat);
        initHuanxin();
        btn_regist = (Button) findViewById(R.id.btn_regist);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_out = (Button) findViewById(R.id.btn_out);
        btn_conversation = (Button) findViewById(R.id.btn_conversation);
        btn_regist.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_out.setOnClickListener(this);
        btn_conversation.setOnClickListener(this);
    }

    public void initHuanxin() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_regist:
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            // call method in SDK
                            EMClient.getInstance().createAccount("ceshi123","ceshi123");
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    // save current user
                                    DemoHelper.getInstance().setCurrentUserName("ceshi123");
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registered_successfully), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        } catch (final HyphenateException e) {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    int errorCode=e.getErrorCode();
                                    if(errorCode== EMError.NETWORK_ERROR){
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
                                    }else if(errorCode == EMError.USER_ALREADY_EXIST){
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
                                    }else if(errorCode == EMError.USER_AUTHENTICATION_FAILED){
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.registration_failed_without_permission), Toast.LENGTH_SHORT).show();
                                    }else if(errorCode == EMError.USER_ILLEGAL_ARGUMENT){
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.illegal_user_name),Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }).start();
                break;
            case R.id.btn_login:
                EMLoginAction("ceshi123","ceshi123");
                break;
            case R.id.btn_out:
                EMClient.getInstance().logout(true);
                break;
            case R.id.btn_conversation:
                Intent intent = new Intent(TestHuanxinChatActivity.this, ChatConversationListActivity.class);
                startActivity(intent);
                break;
        }
    }
    public void EMLoginAction(final String username, final String password) {
        EMClient.getInstance().login(username, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().updateCurrentUserNick(
                        DemoApplication.currentUserNick.trim());
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }
                // get user's info (this should be get from App's server or 3rd party service)
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                Log.e("jiang", "登录成功");
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d("jiang", "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
