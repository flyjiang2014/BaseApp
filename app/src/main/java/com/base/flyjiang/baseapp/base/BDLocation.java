/**
 * @Title: BDLocation.java
 * @Package com.carking.cn.tools
 * @Description: TODO(百度定位)
 * @author sheng
 * @date 2014-4-9 下午6:15:20
 * @version V1.0
 */
package com.base.flyjiang.baseapp.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import in.srain.cube.util.CLog;

/**
 *
 * @Description 获取定位信息：
 *              请调用此类中的静态方法获取定位信息。
 *              采用EventBus实现，请在需要获取定位信息的类中注册EventBus（例：EventBus.getDefault().
 *              register(this)）， 并在该类中加入如下方法： public void
 *              onEventMainThread(LocationEvent event) { // TODO Auto-generated
 *              method stub String city = event.getCity(); double latitude =
 *              event.getLatitude(); double longitude = event.getLongitude(); }
 * @author Keier-泊
 * @date2014-4-10
 */
public class BDLocation {
    private static final String TAG = "BDLocation";
    private LocationClient mLocationClient = null;
    private Context context;

    /**
     *
     * <p>
     * Title: 百度定位構造函數
     * </p>
     * <p>
     * Description:
     * </p>
     *
     * @param context
     */
    public BDLocation(Context context) {
        this.context = context;
        mLocationClient = new LocationClient(context); // 声明LocationClient类
      //  setLocationParams();
        initLocation();
        mLocationClient.registerLocationListener(myListener); // 注册监听函数
        mLocationClient.start();
        CLog.e(TAG, "开始定位");
    }

    private BDLocationListener myListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(com.baidu.location.BDLocation location) {

           if (location != null) {
                CLog.e(TAG, "====================");
                if (location.getCity() == null) {
                    CLog.e(TAG,
                            "location.getCity()=" + location.getCity());
                    startLocation();
                } else {
                    saveLocation(context, location);
                    CLog.e(TAG, "定位成功:city=" + location.getCity()
                            + "||Latitude=" + location.getLatitude()
                            + "||Longitude=" + location.getLongitude());
                    mLocationClient.unRegisterLocationListener(myListener);
                    mLocationClient = null;
                }
            }
        }
    };

    /**
     *
     * Description： 设置定位参数
     *
     * @return void
     */
    private void setLocationParams() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setPriority(LocationClientOption.NetWorkFirst);// 设置网络优先(不设置，默认是gps优先)
        option.setAddrType("all");// 返回的定位结果包含地址信息
        option.setScanSpan(10 * 60 * 1000);// 设置发起定位请求的间隔时间为10分钟
        mLocationClient.setLocOption(option);
    }

    /**
     * 配置定位SDK参数
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 30000000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    /**
     *
     * Description： 发起定位
     *
     * @return void
     */
    public void startLocation() {
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.requestLocation();
        } else {

            CLog.e("LocSDK3", "locClient is null or not started");
        }
    }

    /**
     *
     * Description： 停止定位
     *
     * @return void
     */
    public void stopLocation() {
        if (mLocationClient != null) {
            mLocationClient.stop();
            mLocationClient.unRegisterLocationListener(myListener);
        }
    }

    /**
     * Description： 保存定位信息
     *
     * @param context
     * @param location
     * @return void
     */
    private void saveLocation(Context context,
                              com.baidu.location.BDLocation location) {
        SharedPreferences sp = context.getSharedPreferences("BDLocation",
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString("city", location.getCity());
        editor.putString("address", location.getAddrStr());
        editor.putString("latitude", location.getLatitude() + "");
        editor.putString("longitude", location.getLongitude() + "");
        editor.commit();
    }

    /**
     * Description： 获取当前定位的城市
     *
     * @param context
     * @return
     * @return String
     */
    public static String getCity(Context context) {
        SharedPreferences sp = context.getSharedPreferences("BDLocation",
                Context.MODE_PRIVATE);
        return sp.getString("city", "");
    }

    /**
     * Description： 获取当前定位的城市
     *
     * @param context
     * @return
     * @return String
     */
    public static String getAddress(Context context) {
        SharedPreferences sp = context.getSharedPreferences("BDLocation",
                Context.MODE_PRIVATE);
        return sp.getString("address", null);
    }

    /**
     *
     * Description： 获取当前定位的经度
     *
     * @param context
     * @return
     * @return double
     */
    public static double getLongitude(Context context) {
        SharedPreferences sp = context.getSharedPreferences("BDLocation",
                Context.MODE_PRIVATE);
        String latitude = sp.getString("longitude", "0");
        if (latitude.equals("")) {
            latitude = "0";
        }
        return Double.parseDouble(latitude);
    }

    /**
     *
     * Description： 获取当前定位的纬度
     *
     * @param context
     * @return
     * @return double
     */
    public static double getLatitude(Context context) {
        SharedPreferences sp = context.getSharedPreferences("BDLocation",
                Context.MODE_PRIVATE);
        String latitude = sp.getString("latitude", "0");
        if (latitude.equals("")) {
            latitude = "0";
        }
        return Double.parseDouble(latitude);
    }
}
