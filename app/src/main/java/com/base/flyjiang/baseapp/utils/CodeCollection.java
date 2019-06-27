package com.base.flyjiang.baseapp.utils;

/**
 * Created by ${flyjiang} on 2016/10/25.
 * 文件说明：部分代码写法
 */

/**
 1 文件说明：一些网站：
  银联支付 http://blog.csdn.net/chentravelling/article/details/51321293#2%E5%90%8E%E5%8F%B0%E9%80%9A%E7%9F%A5%E5%A4%84%E7%90%86%E9%83%A8%E5%88%86
          http://blog.csdn.net/chentravelling/article/details/51333289
         http://blog.csdn.net/u012049463/article/details/42242243

 2 正则表达式 http://wenku.baidu.com/link?url=Cv5DGZ-hzeuGvWNLZMPY-2kF8w8d0rvI_Ak-CUBLVPrO-nw_xBmtmSX5ZKRS86udl_KXDy7DgxNf_8tRXBJZ2JuWG_71hkwTO3jzAPySYq7
          http://www.techug.com/20-good-regex

3  drawable控件制作代码生成    http://angrytools.com/android/button/

4  安卓屏幕知识               http://www.zcool.com.cn/article/ZNjI3NDQ=.html

 5 中英文混排自动换行        http://www.cnblogs.com/guojing1991/p/4845011.html

 6 上拉下拉刷新加载       https://github.com/Shuyun123/EasyPullRefresh

7  okHttp网络框架    https://github.com/jeasonlzy/okhttp-OkGo

8  网络加载过程框架   https://github.com/weavey/LoadingLayoutDemo

 9 icon 在线制作 http://icon.wuruihong.com/
              http://www.25xt.com/appdesign/1802.html
 10 SideBar  https://github.com/gjiazhe/WaveSideBar

 11 各种工具类 https://gold.xitu.io/entry/583cf7bac59e0d006b477dff

 12 一个简单，强大的广告活动弹窗控件 https://github.com/yipianfengye/android-adDialog

 13 gradlew上传     gradlew install           https://bintray.com 网址
                gradlew bintrayUpload

 14 Android流式布局，支持单选、多选等，适合用于产品标签等。
 https://github.com/hongyangAndroid/FlowLayout
 特色
 以setAdapter形式注入数据
 直接设置selector为background即可完成标签选则的切换，类似CheckBox
 支持控制选择的Tag数量，比如：单选、多选
 支持setOnTagClickListener，当点击某个Tag回调
 支持setOnSelectListener，当选择某个Tag后回调
 支持adapter.notifyDataChanged
 Activity重建（或者旋转）后，选择的状态自动保存

 15 https://github.com/hongyangAndroid/AndroidAutoLayout
    Android屏幕适配方案，直接填写设计图上的像素尺寸即可完成适配。
 http://blog.sina.com.cn/s/blog_748ad1210102wrl3.html

 16 https://github.com/crazycodeboy/TakePhoto
 TakePhoto是一款用于在Android设备上获取照片（拍照或从相册、文件中选择）、裁剪图片、压缩图片的开源工具库，目前最新版本4.0.3。 3.0以下版本及API说明，详见TakePhoto2.0+。
 V4.0
 支持通过相机拍照获取图片
 支持从相册选择图片
 支持从文件选择图片
 支持批量图片选取
 支持图片压缩以及批量图片压缩
 支持图片裁切以及批量图片裁切
 支持照片旋转角度自动纠正
 支持自动权限管理(无需关心SD卡及摄像头权限等问题)
 支持对裁剪及压缩参数个性化配置
 提供自带裁剪工具(可选)
 支持智能选取及裁剪异常处理
 支持因拍照Activity被回收后的自动恢复
 支持Android7.0
 +支持多种压缩工具
 +支持多种图片选择工具


 17 https://github.com/dengzq/LetterView
 一个可以选择字母完成单词的小控件
 */

/**https://github.com/drakeet/MaterialDialog
*18 MaterialDialog
 *  */
/**
19 https://github.com/m511386374/floatingsearchview
 */

/**
20 https://github.com/m511386374/JDCommoditySelectSpecifications
 仿京东app商品详情选择规格特效，IOS版本有此效果，Android版本好像没有
* */

/**相册图片上传
 21* https://github.com/Bilibili/boxing*/


/**http://android-doc.com/html/design/2017/0406/321.html*
22 详尽的Material Design 学习笔记
 */

/**android design library提供的TabLayout的用法
23 http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0731/3247.html
 */

/**
 *24  https://tinypng.com  //图片压缩网址
 */
/**
https://hndeveloper.github.io/2017/github-android-ui.html  GitHub上受欢迎的Android UI Library
 */
public class CodeCollection {
    /**
     * 1 通用适配器 getView方法
     */
    /*    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ticket_train, null);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder {
    }*/

    /**
     * 2 输入框保留两位小数
     */
   /* etMoney.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!StringUtil.isEmpty(charSequence.toString())) {
                try {
                    Double doublePrice = Double.parseDouble(charSequence.toString());
                    DecimalFormat df = new DecimalFormat("######0.00");
                    Double finalPrice = doublePrice * (1 - Double.parseDouble(guideCashRatio));
                    tvFinalMoney.setText("￥" + df.format(finalPrice));
                } catch (NumberFormatException e) {
                    etMoney.setText(""); //防止开始位置输入".",捕捉异常
                    tvFinalMoney.setText("￥0");
                    e.printStackTrace();
                }
            } else {
                tvFinalMoney.setText("￥0");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            //保留两位小数
            String temp = editable.toString();
            int posDot = temp.indexOf(".");
            if (posDot <= 0) {
                return;
            }
            if (temp.length() - posDot - 1 > 2)
            {
                editable.delete(posDot + 3, posDot + 4);
            }
        }
    });*/
//
//    etDisplacement.addTextChangedListener(new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int i, int i1, int i2) {
//            if (s.toString().contains(".")) {
//                if (s.length() - 1 - s.toString().indexOf(".") > DECIMAL_DIGITS) {
//                    s = s.toString().subSequence(0,
//                            s.toString().indexOf(".") + (DECIMAL_DIGITS + 1));
//                    etDisplacement.setText(s);
//                    etDisplacement.setSelection(s.length());
//                }
//            }
//            if (s.toString().trim().substring(0).equals(".")) {
//                s = "0" + s;
//                etDisplacement.setText(s);
//                etDisplacement.setSelection(1);
//            }
//
//            if (s.toString().startsWith("0")
//                    && s.toString().trim().length() > 1) {
//                if (!s.toString().substring(1, 2).equals(".")) {
//                    etDisplacement.setText(s.subSequence(0, 1));
//                    etDisplacement.setSelection(1);
//                    return;
//                }
//            }
//        }
//
//        @Override
//        public void afterTextChanged(Editable editable) {
//
//        }
//    });




    /**
     * Dialog类的弹窗
     */
/*    muDialog = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT).create();
    LayoutInflater mInflater = LayoutInflater.from(mContext);
    View view = mInflater.inflate(R.layout.minsu_choose_num, null);
    GridView gv_minsu = (GridView) view.findViewById(R.id.gv_minsu);
    minSuChooseAdapter = new MinSuChooseAdapter(mContext, stockNumList);
    gv_minsu.setAdapter(minSuChooseAdapter);
    gv_minsu.setOnItemClickListener(new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
            tv_total.setText(stockNumList.get(position) + "");
            total_price = Double.parseDouble(payPrice) * (position + 1) * day + "";
            total_integal = Integer.parseInt(TextUtils.isEmpty(payIntegal)?"0":payIntegal) * (position + 1) * day + "";
            DecimalFormat df = new DecimalFormat("#0.00");
            total_price = df.format(Double.parseDouble(total_price));
            int num = Integer.parseInt(tv_total.getText().toString().trim());
            tv_price.setText(PriceUtils.getTotalPriceWithDayAndNum(payPrice, payIntegal, day, num));
            muDialog.dismiss();
        }
    });
    muDialog.setView(view);*/

    /**
     *popWindow弹窗
     */
  /*  private int choosed_position;
    private void initPopUpwindowSelect() {
        List<String> list_time = new ArrayList<String>();
        list_time.add("全部时段");
        list_time.add("上午(00:00-12:00)");
        list_time.add("下午(12:00-18:00)");
        list_time.add("晚上(18:00-23:59)");
        View mView = LayoutInflater.from(TrainTicketsListActivity.this).inflate(
                R.layout.train_time_choose_layout, null);
        final TrainTimeChooseAdapter trainTimeChooseAdapter = new TrainTimeChooseAdapter(mContext, list_time,choosed_position);
        ListView listview = (ListView) mView.findViewById(R.id.lv_time_choose);
        listview.setAdapter(trainTimeChooseAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choosed_position = position;
                trainTimeChooseAdapter.setChoosePosition(position);
                trainTimeChooseAdapter.notifyDataSetChanged();
                //   popUpwindowSelectTime.dismiss();
            }
        });

        if (popUpwindowSelectTime != null)
            popUpwindowSelectTime = null;
        backgroundAlpha(0.5f);
        popUpwindowSelectTime = new
                PopupWindow(mView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popUpwindowSelectTime.setOutsideTouchable(true);
        popUpwindowSelectTime.setTouchable(true);
        popUpwindowSelectTime.setBackgroundDrawable(new BitmapDrawable());
        popUpwindowSelectTime.showAtLocation(findViewById(R.id.ll_main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popUpwindowSelectTime.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
    }*/

/*
    private static class MyHandler extends Handler {

        //对Activity的弱引用
        private final WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity){
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if(activity==null){
                super.handleMessage(msg);
                return;
            }
            switch (msg.what) {
                case MSG_RATE_UPDATE:
                    float rate_ing_per = 1 / ((180-4*activity.mRateHalfCircleView.getAngle()-2*activity.mRateHalfCircleView.getBb())*activity.rate_now/5);
                    activity.rate_ing += rate_ing_per ;
                    if(activity.rate_ing>=activity.rate_now-(rate_ing_per)){
                        activity.rate_ing = 0;
                    }

                    activity.mRateHalfCircleView.setRate_angle(activity.rate_ing);
                    activity.handler.sendEmptyMessageDelayed(MSG_RATE_UPDATE,200);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }

          private final MyHandler handler = new MyHandler(this);

             handler.sendEmptyMessage(MSG_RATE_UPDATE);
    }*/
}
