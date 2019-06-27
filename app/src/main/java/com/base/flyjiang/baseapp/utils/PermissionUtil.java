package com.base.flyjiang.baseapp.utils;

import android.text.TextUtils;

import com.carking.quotationlibrary.utils.ToastUtil;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

/**
 * Created by ${flyjiang} on 2016/10/14.
 * 文件说明：权限处理工具类
 */
public class PermissionUtil {

    /**
     * 权限处理
     */
    private static PermissionListener mPermissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted(PermissionGrantedResponse response) {
            dealWithPermissionGranted(response);
        }

        @Override
        public void onPermissionDenied(PermissionDeniedResponse response) {
            dealWithPermissionDenied(response);
        }

        @Override
        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
            //用于用户自定义弹窗是否请求权限，请求权限token.continuePermissionRequest();不请求权限token.cancelPermissionRequest();
            token.continuePermissionRequest();
        }
    };
    /**
     * 全部权限
     */
    private static MultiplePermissionsListener mPermissionsListenerAll = new MultiplePermissionsListener() {
        @Override
        public void onPermissionsChecked(MultiplePermissionsReport report) {
            if (report != null) {
                List<PermissionGrantedResponse> permissionGrantedResponses = report.getGrantedPermissionResponses();
                List<PermissionDeniedResponse> permissionDeniedResponses = report.getDeniedPermissionResponses();
                //处理允许的权限
                if (permissionGrantedResponses != null && permissionGrantedResponses.size() > 0) {
                    for (PermissionGrantedResponse grantedResponse : permissionGrantedResponses) {
                        dealWithPermissionGranted(grantedResponse);
                    }
                }
                //处理拒绝的权限
                if (permissionDeniedResponses != null && permissionDeniedResponses.size() > 0) {
                    for (PermissionDeniedResponse deniedResponse : permissionDeniedResponses) {
                        dealWithPermissionDenied(deniedResponse);
                    }
                }
            }
        }

        @Override
        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
            //用于用户自定义弹窗是否请求权限，请求权限token.continuePermissionRequest();不请求权限token.cancelPermissionRequest();
            token.continuePermissionRequest();
        }
    };

    /**
     * 处理单个权限
     * @param permission
     */
    public static void checkPermission(String permission){
        if (Dexter.isRequestOngoing()) {
            return;
        }
      Dexter.checkPermission(mPermissionListener, permission);
    }

    /**
     * 处理所有权限
     * @param permissions
     */
    public static void checkAllPermission(String[] permissions){
        if (Dexter.isRequestOngoing()) {
            return;
        }
        Dexter.checkPermissions(mPermissionsListenerAll, permissions);
    }


    /**
     * 处理同意权限
     *
     * @param response 允许的权限封装
     */
    private static void  dealWithPermissionGranted(PermissionGrantedResponse response) {
        if (response == null || TextUtils.isEmpty(response.getPermissionName())) {
            return;
        }
    }

    /**
     * 处理拒绝的权限
     *
     * @param response 拒绝的权限封装
     */
    private static void dealWithPermissionDenied(PermissionDeniedResponse response) {
        if (response == null || TextUtils.isEmpty(response.getPermissionName())) {
            return;
        }
        ToastUtil.showToast("拒绝权限可能会引起程序相关功能失效,请知晓");
    }


}
