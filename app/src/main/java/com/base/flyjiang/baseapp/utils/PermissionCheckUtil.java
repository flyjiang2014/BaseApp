package com.base.flyjiang.baseapp.utils;

import android.text.TextUtils;

import com.carking.quotationlibrary.utils.ToastUtil;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

/**
 * Created by ${flyjiang} on 2016/10/14.
 * 文件说明：权限处理工具类
 */
public class PermissionCheckUtil {

    /**
     * 处理单个权限
     * @param permission
     */
    public static void checkPermission(String permission, PermissionListener mPermissionListener){
        if (Dexter.isRequestOngoing()) {
            return;
        }
        Dexter.checkPermission(mPermissionListener, permission);
    }

    /**
     * 处理所有权限
     * @param permissions
     */
    public static void checkAllPermission(String[] permissions,MultiplePermissionsListener mPermissionsListenerAll){
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
