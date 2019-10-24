package com.base.flyjiang.baseapp.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.base.BaseDemoActivity;
import com.base.flyjiang.baseapp.runtimepermissions.PermissionsManager;
import com.base.flyjiang.baseapp.runtimepermissions.PermissionsResultAction;
import com.base.flyjiang.baseapp.ui.test.ALPayActivity;
import com.base.flyjiang.baseapp.ui.test.AgentWebActivity;
import com.base.flyjiang.baseapp.ui.test.BottomNavigationBarActivity;
import com.base.flyjiang.baseapp.ui.test.BottomSheetActivity;
import com.base.flyjiang.baseapp.ui.test.CoordinatorLayout02Activity;
import com.base.flyjiang.baseapp.ui.test.CoordinatorLayout03Activity;
import com.base.flyjiang.baseapp.ui.test.CoordinatorLayoutActivity;
import com.base.flyjiang.baseapp.ui.test.FragmentIndexActivity;
import com.base.flyjiang.baseapp.ui.test.LoginActivity;
import com.base.flyjiang.baseapp.ui.test.PagerBottomTabStripActivity;
import com.base.flyjiang.baseapp.ui.test.PopupTestActivity;
import com.base.flyjiang.baseapp.ui.test.RecycleTestActivity;
import com.base.flyjiang.baseapp.ui.test.ScrollViewActivity;
import com.base.flyjiang.baseapp.ui.test.SecondDemoActivity;
import com.base.flyjiang.baseapp.ui.test.SpecialActivity;
import com.base.flyjiang.baseapp.ui.test.TabLayoutActivity;
import com.base.flyjiang.baseapp.ui.test.TestAnimActivity;
import com.base.flyjiang.baseapp.ui.test.TestFragmengActivity;
import com.base.flyjiang.baseapp.ui.test.TestHuanxinChatActivity;
import com.base.flyjiang.baseapp.ui.test.TestLvActivity;
import com.base.flyjiang.baseapp.ui.test.TestShareActivity;
import com.base.flyjiang.baseapp.ui.test.TestTwoCodeActivity;
import com.base.flyjiang.baseapp.ui.test.WebActivity;
import com.carking.quotationlibrary.utils.ClickUtil;
import com.carking.quotationlibrary.utils.TextStyleUtil;
import com.carking.quotationlibrary.utils.ToastUtil;
import com.carking.quotationlibrary.view.CustomGridview;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.EMLog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.List;

import hyphenate.chatui.Constant;
import hyphenate.chatui.DemoHelper;

public class MainActivity extends BaseDemoActivity implements View.OnClickListener {

    private Button btn_login;
    private Button btn_baidu;
    private Button btn_lv_shuaxin;
    private Button btn_share;
    private Button btn_chat;
    private Button btn_frg;
    private Button btn_frg_index;
    private Button btn_webView;
    public final static String baseURL = "http://192.168.0.199:8888/";
    public final static String baseURL_m = baseURL + "guide_m/";

    private BroadcastReceiver internalDebugReceiver;
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;
    public static MainActivity instance ;
    private CustomGridview gv_item;
    private ArrayAdapter itemArrayAdapter;
    private String aa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
      //  showingProgressDialog("加载中"); //加载动画展示
       // PermissionUtil.checkAllPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
     //   PermissionUtil.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

        /**
       设备单点登录
         */
        if (savedInstanceState != null && savedInstanceState.getBoolean(Constant.ACCOUNT_REMOVED, false)) {
            DemoHelper.getInstance().logout(false,null);
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        } else if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false)) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {  //版本权限判断
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivity(intent);
            }
        }

        /**
         runtime permission for android 6.0, just require all permissions here for simple 权限管理6.0
          */
        //requestPermissions();

        registerBroadcastReceiver();

        if (getIntent().getBooleanExtra(Constant.ACCOUNT_CONFLICT, false) && !isConflictDialogShow) {
            showConflictDialog();
        } else if (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) && !isAccountRemovedDialogShow) {
            showAccountRemovedDialog();
        }
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_share = (Button) findViewById(R.id.btn_share);
        btn_lv_shuaxin = (Button) findViewById(R.id.btn_lv_shuaxin);
        btn_baidu = (Button) findViewById(R.id.btn_baidu);
        btn_chat = (Button) findViewById(R.id.btn_chat);
        btn_frg = (Button) findViewById(R.id.btn_frg);
        btn_webView = (Button) findViewById(R.id.btn_webView);
        gv_item = (CustomGridview) findViewById(R.id.gv_item);
        btn_login.setOnClickListener(this);
        btn_share.setOnClickListener(this);
        btn_lv_shuaxin.setOnClickListener(this);
        btn_baidu.setOnClickListener(this);
        btn_chat.setOnClickListener(this);
        btn_frg.setOnClickListener(this);
        btn_webView.setOnClickListener(this);
        btn_frg_index  = (Button) findViewById(R.id.btn_frg_index);
        btn_frg_index.setOnClickListener(this);
        btn_login.setText((new TextStyleUtil(btn_login.getText().toString()).setForegroundColor(R.color.blue, 0, 3).getSpannableString()));
        itemArrayAdapter = new ArrayAdapter(mContext,android.R.layout.simple_expandable_list_item_1);
        itemArrayAdapter.add("jiang");
        itemArrayAdapter.add("效果");
        itemArrayAdapter.add("滑动");
        itemArrayAdapter.add("RecycleView");
        itemArrayAdapter.add("Tablayout");
        itemArrayAdapter.add("AgentWeb");
        itemArrayAdapter.add("支付宝");
        itemArrayAdapter.add("BottomNavigationBar");
        itemArrayAdapter.add("PagerBottomTabStrip");
        itemArrayAdapter.add("popup");
        itemArrayAdapter.add("二维码");
        itemArrayAdapter.add("约束布局");
        itemArrayAdapter.add("约束布局02");
        itemArrayAdapter.add("约束布局03");
        itemArrayAdapter.add("BottomSheet");

        gv_item.setAdapter(itemArrayAdapter);
        gv_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        CrashReport.testJavaCrash();
                        startActivity(new Intent(mContext, TestAnimActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(mContext, SpecialActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(mContext, ScrollViewActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(mContext, RecycleTestActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(mContext, TabLayoutActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(mContext, AgentWebActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(mContext, ALPayActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(mContext, BottomNavigationBarActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(mContext, PagerBottomTabStripActivity.class));
                        break;
                    case 9:
                        startActivity(new Intent(mContext, PopupTestActivity.class));
                        break;
                    case 10:
                        startActivity(new Intent(mContext, TestTwoCodeActivity.class));
                        break;
                    case 11:
                        startActivity(new Intent(mContext, CoordinatorLayoutActivity.class));
                        break;
                    case 12:
                        startActivity(new Intent(mContext, CoordinatorLayout02Activity.class));
                        break;
                    case 13:
                        startActivity(new Intent(mContext, CoordinatorLayout03Activity.class));
                        break;
                    case 14:
                        startActivity(new Intent(mContext, BottomSheetActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_login:
                //startActivity(new Intent(mContext, TestLineActivity.class));
                Dexter.checkPermission(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if (response == null || TextUtils.isEmpty(response.getPermissionName())) {
                            return;
                        }
                        ToastUtil.showToast("当前权限已被允许");
                        //处理允许权限的逻辑
                        Log.e("jiang","onPermissionGranted");
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response == null || TextUtils.isEmpty(response.getPermissionName())) {
                            return;
                        }
                        String ok= response.isPermanentlyDenied()?"永久拒绝":"拒绝一下";
                        //处理被拒绝权限的逻辑
                       ToastUtil.showToast("当前权限已被"+ok);
                        Log.e("jiang", "onPermissionDenied");
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        //用于用户自定义弹窗是否请求权限，请求权限token.continuePermissionRequest();不请求权限token.cancelPermissionRequest();
                        token.continuePermissionRequest();
                        ToastUtil.showToast("当前权限之前已被" + "永久拒绝");
                        Log.e("jiang", "nPermissionRationaleShouldBeShown");
                    }
                }, Manifest.permission.ACCESS_FINE_LOCATION);
             //   login();
                break;
            case R.id.btn_baidu:
                intent = new Intent(mContext, SecondDemoActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_lv_shuaxin:
                if(ClickUtil.isFastDoubleClick()){
                    return;  //防止快速多次点击
                }
                 intent = new Intent(mContext, TestLvActivity.class);
                //intent = new Intent(mContext, SettingStyleActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_share:
                 intent= new Intent(mContext, TestShareActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_chat:
                intent= new Intent(mContext, TestHuanxinChatActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_frg:
                intent= new Intent(mContext, TestFragmengActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_frg_index:
                intent= new Intent(mContext, FragmentIndexActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_webView:
                WebActivity.runActivity(mContext, "测试webview", "http://192.168.0.126/new_h5_share/17.3.7/share_waiwang_test/qianggou/qianggou.html?viewSpotId=5");
                break;
            default:
                break;
        }
    }

    public void login() {
   /*     CustomHttpRequest.showLoading("ceshi", mContext);
        RequestParams requestParams = new RequestParams(baseURL_m + "user/login.do");
        requestParams.addBodyParameter("mobile", "13914038661");
        requestParams.addBodyParameter("password", MD5Util.MD5("12345678").toUpperCase());
        CustomHttpRequest.getRequest(this, requestParams, loginCallback);*/
    }

    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                DemoHelper.getInstance().getNotifier().onNewMsg(message);
            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //red packet code : 处理红包回执透传消息
            for (EMMessage message : messages) {
                EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                final String action = cmdMsgBody.action();//获取自定义action
               /* if (action.equals(RedPacketConstant.REFRESH_GROUP_RED_PACKET_ACTION)) {
                    RedPacketUtil.receiveRedPacketAckMessage(message);
                }*/
            }
            //end of red packet code
            refreshUIWithMessage();
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {}
    };

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                updateUnreadLabel();
           /*     if (currentTabIndex == 0) {
                    // refresh conversation list
                    if (conversationListFragment != null) {
                        conversationListFragment.refresh();
                    }
                }*/
            }
        });
    }

 /*   @Override
        public void back(View view) {
        super.back(view);
    }*/

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
      //  intentFilter.addAction(RedPacketConstant.REFRESH_GROUP_RED_PACKET_ACTION);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                updateUnreadLabel();
                updateUnreadAddressLable();
            /*    if (currentTabIndex == 0) {
                    // refresh conversation list
                    if (conversationListFragment != null) {
                        conversationListFragment.refresh();
                    }
                } else if (currentTabIndex == 1) {
                    if(contactListFragment != null) {
                        contactListFragment.refresh();
                    }
                }*/
                String action = intent.getAction();
          /*      if(action.equals(Constant.ACTION_GROUP_CHANAGED)){
                    if (EaseCommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName())) {
                        GroupsActivity.instance.onResume();
                    }
                }
                //red packet code : 处理红包回执透传消息
                if (action.equals(RedPacketConstant.REFRESH_GROUP_RED_PACKET_ACTION)){
                    if (conversationListFragment != null){
                        conversationListFragment.refresh();
                    }
                }*/
                //end of red packet code
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver(){
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isConflict && !isCurrentAccountRemoved) {
            updateUnreadLabel();
            updateUnreadAddressLable();
        }

        // unregister this event listener when this activity enters the
        // background
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.pushActivity(this);

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    @Override
    protected void onStop() {
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.popActivity(this);

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (conflictBuilder != null) {
            conflictBuilder.create().dismiss();
            conflictBuilder = null;
        }
        unregisterBroadcastReceiver();
        try {
            unregisterReceiver(internalDebugReceiver);
        } catch (Exception e) {
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isConflict", isConflict);
        outState.putBoolean(Constant.ACCOUNT_REMOVED, isCurrentAccountRemoved);
        super.onSaveInstanceState(outState);
    }

    /**
     * 6.0权限控制
     */
    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * get unread event notification count, including application, accepted, etc
     *
     * @return
     */
    public int getUnreadAddressCountTotal() {
        int unreadAddressCountTotal = 0;
    //    unreadAddressCountTotal = inviteMessgeDao.getUnreadMessagesCount(); //获取未读消息
        return unreadAddressCountTotal;
    }

    /**
     * update unread message count
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
          //  unreadLabel.setText(String.valueOf(count));
          //  unreadLabel.setVisibility(View.VISIBLE);
        } else {
          //  unreadLabel.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * get unread message count
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal = 0;
        int chatroomUnreadMsgCount = 0;
        unreadMsgCountTotal = EMClient.getInstance().chatManager().getUnreadMsgsCount();
        for(EMConversation conversation:EMClient.getInstance().chatManager().getAllConversations().values()){
            if(conversation.getType() == EMConversation.EMConversationType.ChatRoom)
                chatroomUnreadMsgCount=chatroomUnreadMsgCount+conversation.getUnreadMsgCount();
        }
        return unreadMsgCountTotal-chatroomUnreadMsgCount;
    }

    /**
     * update the total unread count
     */
    public void updateUnreadAddressLable() {
        runOnUiThread(new Runnable() {
            public void run() {
                int count = getUnreadAddressCountTotal();
                if (count > 0) {
                    // unreadAddressLable.setVisibility(View.VISIBLE);
                } else {
                    //  unreadAddressLable.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    private boolean isConflictDialogShow;
    private boolean isAccountRemovedDialogShow;
    private android.app.AlertDialog.Builder conflictBuilder;
    private android.app.AlertDialog.Builder accountRemovedBuilder;
    // user logged into another device
    public boolean isConflict = false;
    // user account was removed
    private boolean isCurrentAccountRemoved = false;
    /**
     * show the dialog when user logged into another device 另一设备登录dialog
     */
    private void showConflictDialog() {
        isConflictDialogShow = true;
        DemoHelper.getInstance().logout(false, null);
        String st = getResources().getString(R.string.Logoff_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (conflictBuilder == null)
                    conflictBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                conflictBuilder.setTitle(st);
                conflictBuilder.setMessage(R.string.connect_conflict);
                conflictBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        conflictBuilder = null;
                        finish();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                conflictBuilder.setCancelable(false);
                conflictBuilder.create().show();
                isConflict = true;
            } catch (Exception e) {
                EMLog.e(TAG, "---------color conflictBuilder error" + e.getMessage());
            }

        }

    }

    /**
     * show the dialog if user account is removed 账号被删除dialog
     */
    private void showAccountRemovedDialog() {
        isAccountRemovedDialogShow = true;
        DemoHelper.getInstance().logout(false,null);
        String st5 = getResources().getString(R.string.Remove_the_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (accountRemovedBuilder == null)
                    accountRemovedBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                accountRemovedBuilder.setTitle(st5);
               // accountRemovedBuilder.setMessage(R.string.em_user_remove);
                accountRemovedBuilder.setMessage("此用户已被移除");
                accountRemovedBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        accountRemovedBuilder = null;
                        finish();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                });
                accountRemovedBuilder.setCancelable(false);
                accountRemovedBuilder.create().show();
                isCurrentAccountRemoved = true;
            } catch (Exception e) {
                EMLog.e(TAG, "---------color userRemovedBuilder error" + e.getMessage());
            }

        }

    }
    /**
     * 检查当前用户是否被删除
     */
    public boolean getCurrentAccountRemoved() {
        return isCurrentAccountRemoved;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (getIntent().getBooleanExtra("conflict", false) && !isConflictDialogShow) {
            showConflictDialog();
        } else if (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) && !isAccountRemovedDialogShow) {
            showAccountRemovedDialog();
        }
    }

    /**
     * 登录接口
     */
   /* CustomHttpRequest.RequestCallback loginCallback = new CustomHttpRequest.RequestCallback() {
        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            CustomHttpRequest.dismissLoading();
        }

        @Override
        public void onSuccess(String result) {
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("success") == 1) {
                        LoginSuccessBean loginSuccess = JsonUtil.json2Bean(jsonObject.get("data").toString(),LoginSuccessBean.class);
                        ToastUtil.showToast("登录成功");
                    } else {
                        ToastUtil.showToast(jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            CustomHttpRequest.dismissLoading();
        }
    };*/
}
