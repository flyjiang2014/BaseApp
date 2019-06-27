package com.base.flyjiang.baseapp.ui.test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.alipay.PayResult;
import com.base.flyjiang.baseapp.bean.CommonReturnData;
import com.base.flyjiang.baseapp.bean.OrderPay;
import com.base.flyjiang.baseapp.bean.OrderWechatPayResponse;
import com.base.flyjiang.baseapp.callback.DialogCallback;
import com.base.flyjiang.baseapp.wxapi.Constants;
import com.carking.quotationlibrary.utils.ToastUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class ALPayActivity extends AppCompatActivity {
    private Button btn_oay,btn_pay_wechat;
    String order ="NJ301497239255797608";
    String token="0c9cca4b-3054-46df-b281-57cef316deb4";
    private static final int SDK_PAY_FLAG = 1;
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpay);
        api = WXAPIFactory.createWXAPI(this,Constants.APP_ID);
        btn_oay = (Button) findViewById(R.id.btn_pay);
        btn_pay_wechat = (Button) findViewById(R.id.btn_pay_wechat);
        btn_oay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
        btn_pay_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData01();
            }
        });
       /* Badge badge =  new QBadgeView(this).bindTarget(btn_oay).setBadgeText("新").setBadgeTextSize(13, true)
                .setBadgeBackgroundColor(0xffffeb3b).setBadgeTextColor(0xff000000)
                .stroke(0xff000000, 1, true);*/
        Badge badge = new QBadgeView(this).bindTarget(btn_oay).setBadgeNumber(5).setGravityOffset(0,0,true);
    }

    /**
     * 支付宝支付
     */
    public void doPay(final String orderInfo){
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(ALPayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 微信支付
     */
    public void doWechatPay(final String orderInfo){
        OrderWechatPayResponse wechatPayinfo =new Gson().fromJson(orderInfo,
                OrderWechatPayResponse.class);
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (isPaySupported) {
            PayReq req = new PayReq();
            req.appId = Constants.APP_ID;
            req.partnerId = Constants.PARTNER_ID;
            req.nonceStr = wechatPayinfo.noncestr;
            req.prepayId = wechatPayinfo.prepayid;
            req.timeStamp = String.valueOf(wechatPayinfo.timestamp);
            req.sign = wechatPayinfo.sign;
            req.packageValue = wechatPayinfo.packageValue;
            api.sendReq(req);

        }else{
            ToastUtil.showToast("版本过低");
        }
    }

    public void getData() {
        OkGo.<CommonReturnData<OrderPay>>get("http://218.90.187.218:8888/yuyue_m/mobilepay/doMobilePay.do")
                .params("token", token)
                .params("payType", "1")
                .params("orderSn", order)
                .params("type", "0")
                .execute(
                        new DialogCallback<CommonReturnData<OrderPay>>(this) {
//                            @Override
//                            public void onSuccess(com.lzy.okgo.model.Response<CommonReturnData<OrderPay>> response) {
//                                doPay(response.body().getData().payInfo);
//                            }

                            @Override
                            public void onSuccess(CommonReturnData<OrderPay> returnData) {

                            }
                        });
    }

    public void getData01() {
        OkGo.<CommonReturnData<OrderPay>>get("http://218.90.187.218:8888/yuyue_m/mobilepay/doMobilePay.do")
                .params("token", token)
                .params("payType", "2")
                .params("orderSn", order)
                .params("type", "0")
                .execute(

                        new DialogCallback<CommonReturnData<OrderPay>>(this) {

                            @Override
                            public void onSuccess(com.lzy.okgo.model.Response<CommonReturnData<OrderPay>> response) {
                                //         if (isWeixinAvilible(PayAllActivity.this)) {  //判断有误安装微信
                                doWechatPay(response.body().getData().payInfo);
//                                } else {
//                                    showMessage("未安装微信，尝试其他支付");
//                                }
                            }

                            @Override
                            public void onSuccess(CommonReturnData<OrderPay> returnData) {

                            }
                        }


/*                        new DialogCallback<CommonReturnData<OrderPay>>(this) {
                            @Override
                            public void onSuccess(CommonReturnData<OrderPay> orderPay, Call call, Response response) {
                       //         if (isWeixinAvilible(PayAllActivity.this)) {  //判断有误安装微信
                                doWechatPay(orderPay.getData().payInfo);
//                                } else {
//                                    showMessage("未安装微信，尝试其他支付");
//                                }
                            }
                        }*/


                );
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(ALPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ALPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

}
