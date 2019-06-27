package com.base.flyjiang.baseapp.ui.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.flyjiang.baseapp.R;
import com.base.flyjiang.baseapp.Widget.StickScrollView;
import com.base.flyjiang.baseapp.bean.CommonReturnData;
import com.base.flyjiang.baseapp.bean.SignalReturnData;
import com.base.flyjiang.baseapp.callback.JsonCallback;
import com.base.flyjiang.baseapp.utils.GlideImageLoader;
import com.carking.quotationlibrary.utils.PhoneUtil;
import com.carking.quotationlibrary.utils.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.Request;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;
import cn.sharesdk.sina.weibo.SinaWeibo;


public class TestShareActivity extends AppCompatActivity implements StickScrollView.OnScrollListener {
    private TextView btn_share;
    private TextView btn_photo;
    private ArrayList<ImageItem> imageItems;
    private SimpleDraweeView im;
    private Context mContext;
    protected static final int CHANGE_UI = 1;
    protected static final int ERROR =2;
    private TextView tv_stay01,tv_stay02,tv_float01,tv_float02;
    private StickScrollView mStickScrollView;


    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==CHANGE_UI){
                Bitmap bitmap=(Bitmap) msg.obj;
                if (1 !=0) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) im.getLayoutParams();
                    int sideLong = PhoneUtil.getScreenWidth(mContext);
                    layoutParams.width = sideLong;
                    layoutParams.height = sideLong*bitmap.getHeight()/bitmap.getWidth();
                    Log.e("jiang", bitmap.getHeight() + "");
                    Log.e("jiang",bitmap.getWidth()+"");
                    im.setLayoutParams(layoutParams);
                }
           //     im.setImageURI(Uri.parse("http://218.90.187.218:8888/upload/img/invite/logo/1481766425793460.jpg"));
            }else if(msg.what==ERROR){
            //    ToastUtil.showShort(mContext, "获取图片失败");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_share);
        mContext = this;
        btn_share = (TextView) findViewById(R.id.btn_share);
        tv_stay01 = (TextView) findViewById(R.id.tv_stay01);
        tv_stay02 = (TextView) findViewById(R.id.tv_stay02);
        tv_float01 = (TextView) findViewById(R.id.tv_float01);
        tv_float02 = (TextView) findViewById(R.id.tv_float02);
        mStickScrollView = (StickScrollView) findViewById(R.id.ssl_view);


        mStickScrollView.setOnScrollListener(this);
        findViewById(R.id.parent_layout).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //这一步很重要，使得上面的布局和下面的布局重合
                onScroll(mStickScrollView.getScrollY());
            }
        });


        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    showShare("测试","测试","www.baidu.com","http://img.blog.csdn.net/20160706134029848");
                showShare(TestShareActivity.this, null, true);
            }
        });

        btn_photo = (TextView) findViewById(R.id.btn_photo);
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker imagePicker = ImagePicker.getInstance();
                imagePicker.setImageLoader(new GlideImageLoader());
                imagePicker.setMultiMode(true);   //多选
                imagePicker.setShowCamera(true);  //显示拍照按钮
                imagePicker.setSelectLimit(9);    //最多选择9张
                imagePicker.setCrop(true);       //不进行裁减
                Intent intent = new Intent(TestShareActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, 100);
              //  imagePicker.takePicture(TestShareActivity.this,100);这句直接掉用拍照。

            }
        });

        im = (SimpleDraweeView) findViewById(R.id.welcome_image);

        new Thread(){
            @Override
            public void run() {
               Bitmap bitmap = getHttpBitmap("http://218.90.187.218:8888/upload/img/invite/logo/1481766425793460.jpg");
                Message msg=new Message();
                msg.what=CHANGE_UI;
                msg.obj=bitmap;
                handler.sendMessage(msg);
            }
        }.start();
       // im.setImageURI(Uri.parse("http://218.90.187.218:8888/upload/img/invite/logo/1481766425793460.jpg"));
    }

    @Override
    public void onScroll(int scrollY) {
        int mLayout2ParentTop = Math.max(scrollY, tv_stay01.getTop());
        tv_float01.layout(0, mLayout2ParentTop, tv_float01.getWidth(), mLayout2ParentTop + tv_float01.getHeight());

        int mLayout2ParentTop01 = Math.max(scrollY, tv_stay02.getTop());
        tv_float02.layout(0, mLayout2ParentTop01, tv_float02.getWidth(), mLayout2ParentTop01 + tv_float02.getHeight());
        if(scrollY>tv_stay02.getTop()-tv_float01.getHeight()){
            tv_float01.layout(0, tv_stay02.getTop()-tv_float01.getHeight(), tv_float01.getWidth(),tv_stay02.getTop()-tv_float01.getHeight() + tv_float01.getHeight());
            //  tv_float01.layout(0, mLayout2ParentTop-(scrollY+ll_float.getHeight()-tv_01.getTop()), ll_float.getWidth(), mLayout2ParentTop-(scrollY+ll_float.getHeight()-tv_01.getTop()) + ll_float.getHeight());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (imageItems != null && imageItems.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < imageItems.size(); i++) {
                        if (i == imageItems.size() - 1) sb.append("图片").append(i + 1).append(" ： ").append(imageItems.get(i).path);
                        else sb.append("图片").append(i + 1).append(" ： ").append(imageItems.get(i).path).append("\n");
                    }
                    Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
                    formUpload(); //执行上传
                   //tvImages.setText(sb.toString());
                } else {
                  //  tvImages.setText("--");
                }
            } else {
                Toast.makeText(this, "没有选择图片", Toast.LENGTH_SHORT).show();
           //     tvImages.setText("--");
            }
        }
    }

    public void formUpload() {
        ArrayList<File> files = new ArrayList<>();
        if (imageItems != null && imageItems.size() > 0) {
            for (int i = 0; i < imageItems.size(); i++) {
                files.add(new File(imageItems.get(i).path));
            }
        }
        //拼接参数
        OkGo.<CommonReturnData<SignalReturnData>>post("https://shopapi.kdding.com/shop_m/viewspot/uploadPic.do")//
                .tag(this)//
//                .headers("header1", "headerValue1")//
//                .headers("header2", "headerValue2")//
//                .params("param1", "paramValue1")//
//                .params("param2", "paramValue2")//
//                .params("file1",new File("文件路径"))   //这种方式为一个key，对应一个文件
//                .params("file2",new File("文件路径"))
//                .params("file3",new File("文件路径"))
                .addFileParams("file", files)           // 这种方式为同一个key，上传多个文件
                .execute(new JsonCallback<CommonReturnData<SignalReturnData>>() {

                    @Override
                    public void onStart(Request<CommonReturnData<SignalReturnData>, ? extends Request> request) {
                        super.onStart(request);
                        btn_photo.setText("正在上传中...");
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<CommonReturnData<SignalReturnData>> response) {
                        ToastUtil.showToast("上传完成");
                    }

                    @Override
                    public void onSuccess(CommonReturnData<SignalReturnData> returnData) {

                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<CommonReturnData<SignalReturnData>> response) {
                        super.onError(response);
                        ToastUtil.showToast("上传出错");
                    }

                 /*   @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        btn_photo.setText("正在上传中...");
                    }

                    @Override
                    public void onSuccess(CommonReturnData<SignalReturnData> responseData, Call call, Response response) {
                       // handleResponse(responseData.data, call, response);
                      //  btnFormUpload.setText("上传完成");
                        ToastUtil.showToast("上传完成");
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                     //   handleError(call, response);
                        ToastUtil.showToast("上传出错");
                    }*/

                });
    }

    public static Bitmap getHttpBitmap(String url)  {
        Bitmap bitmap = null;   try  {
            URL pictureUrl = new URL(url);
            InputStream in = pictureUrl.openStream();
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (MalformedURLException e)
        {    e.printStackTrace();   }
        catch (IOException e)
        {    e.printStackTrace();   }
        return bitmap;  }

    /**
     * 演示调用ShareSDK执行分享
     *
     * @param context
     * @param platformToShare 指定直接分享平台名称（一旦设置了平台名称，则九宫格将不会显示）
     * @param showContentEdit 是否显示编辑页
     */
    public static void showShare(Context context, String platformToShare, boolean showContentEdit) {
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(!showContentEdit);
        if (platformToShare != null) {
            oks.setPlatform(platformToShare);
        }
        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        // 令编辑页面显示为Dialog模式
        oks.setDialogMode();
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        //oks.setAddress("12345678901"); //分享短信的号码和邮件的地址
        oks.setTitle("ShareSDK--Title");
        oks.setTitleUrl("http://mob.com");
        oks.setText("ShareSDK--文本");
        //oks.setImagePath("/sdcard/test-pic.jpg");  //分享sdcard目录下的图片
        oks.setImageUrl(randomPic()[0]);
        oks.setUrl("http://www.mob.com"); //微信不绕过审核分享链接
        //oks.setFilePath("/sdcard/test-pic.jpg");  //filePath是待分享应用程序的本地路劲，仅在微信（易信）好友和Dropbox中使用，否则可以不提供
        oks.setComment("分享"); //我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
        oks.setSite("ShareSDK");  //QZone分享完之后返回应用时提示框上显示的名称
        oks.setSiteUrl("http://mob.com");//QZone分享参数
        oks.setVenueName("ShareSDK");
        oks.setVenueDescription("This is a beautiful place!");

        // 将快捷分享的操作结果将通过OneKeyShareCallback回调
        //oks.setCallback(new OneKeyShareCallback());
        // 去自定义不同平台的字段内容
        //oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
        // 在九宫格设置自定义的图标

  /*    Bitmap logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        String label = "ShareSDK";
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {

            }
        };
        oks.setCustomerLogo(logo, label, listener);*/

        // 为EditPage设置一个背景的View
        //oks.setEditPageBackground(getPage());
        // 隐藏九宫格中的新浪微博
        oks.addHiddenPlatform(SinaWeibo.NAME);

        // String[] AVATARS = {
        // 		"http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
        // 		"http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg" };
        // oks.setImageArray(AVATARS);              //腾讯微博和twitter用此方法分享多张图片，其他平台不可以

        // 启动分享
        oks.show(context);
    }

    public static String[] randomPic() {
        String url = "http://git.oschina.net/alexyu.yxj/MyTmpFiles/raw/master/kmk_pic_fld/";
        String urlSmall = "http://git.oschina.net/alexyu.yxj/MyTmpFiles/raw/master/kmk_pic_fld/small/";
        String[] pics = new String[]{
                "120.JPG",
                "127.JPG",
                "130.JPG",
                "18.JPG",
                "184.JPG",
                "22.JPG",
                "236.JPG",
                "237.JPG",
                "254.JPG",
                "255.JPG",
                "263.JPG",
                "265.JPG",
                "273.JPG",
                "37.JPG",
                "39.JPG",
                "IMG_2219.JPG",
                "IMG_2270.JPG",
                "IMG_2271.JPG",
                "IMG_2275.JPG",
                "107.JPG"
        };
        int index = (int) (System.currentTimeMillis() % pics.length);
        return new String[]{
                url + pics[index],
                urlSmall + pics[index]
        };
    }

    /**
     * 单个平台的分享
     */
  /*  public void shared_sina(){
        ShareSDK.initSDK(this);
        Platform.ShareParams shareParams = new SinaWeibo.ShareParams();
        shareParams.setText("新浪微博分享测试https://github.com/caiyoufei/MeiMeiAndroid");
        shareParams.setImageUrl("http://img.blog.csdn.net/20160706134029848");
        share(shareParams, SinaWeibo.NAME);
    }
    *//**
     * 执行分享
     *
     * @param shareParams  分享的参数
     * @param platformName 分享的平台
     *//*
    private void share(Platform.ShareParams shareParams, String platformName) {
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        Platform platform = ShareSDK.getPlatform(platformName);
        platform.setPlatformActionListener(new PlatformActionListener() {// 设置分享事件回调
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast("分享成功");
                    }
                });
            }

            @Override
            public void onError(Platform platform, int i, final Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                   *//*     if (BuildConfig.DEBUG) {
                            ToastUtil.showToast("分享失败:" + throwable.toString());
                        } else {*//*
                            ToastUtil.showToast("分享失败");
                //        }
                    }
                });
            }

            @Override
            public void onCancel(Platform platform, int i) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast("取消分享");
                    }
                });
            }
        });
        // 执行图文分享
        platform.share(shareParams);
    }*/
}
