package com.carking.quotationlibrary.utils;

import android.content.Context;
import android.text.TextUtils;

import com.facebook.common.util.SecureHashUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

/**
 * 作者：flyjiang
 * 时间: 2015/6/23 17:56
 * 邮箱：caiyoufei@looip.cn
 * 说明:
 */
public class FrescoUtil {
    /**
     * 根据图片URL获取Fresco图片下载的文件名
     *
     * @param url 图片URL
     * @return 缓存成文件的文件名(不含.cnt)
     */
    private static String url2FileName(final String url) {
        try {
            return TextUtils.isEmpty(url) ? "" : SecureHashUtil.makeSHA1HashBase64(url.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return TextUtils.isEmpty(url) ? "" : url;
        }
    }

    /**
     * 参考DefaultDiskStorage类中的方法获取Fresco根目录名称
     *
     * @param version 在ConfigFresco类中DiskCacheConfig设置的Version(默认为1)
     * @return Fresco根目录名称(默认目录为v2.ols100.1)
     */
    private static String getVersionSubdirectoryName(int version) {
        return String.format((Locale) null, "%s.ols%d.%d", new Object[]{"v2", Integer.valueOf(100), Integer.valueOf(version)});
    }

    /**
     * 参考DefaultDiskStorage类中的方法获取Fresco根目录名称
     *
     * @return v2.ols100.1
     */
    private static String getVersionSubdirectoryName() {
        return getVersionSubdirectoryName(1);
    }

    /**
     * 参考DefaultDiskStorage类中的方法获取图片文件父目录名称
     *
     * @param resourceId 经过url2FileName转换后的文件名
     * @return 图片文件父目录名称
     */
    private static String getSubdirectory(String resourceId) {
        return TextUtils.isEmpty(resourceId) ? resourceId : String.valueOf(Math.abs(resourceId.hashCode() % 100));
    }

    /**
     * 获取默认图片文件(不包含小图配置)
     *
     * @param context 上下文
     * @param url     图片地址
     * @return 图片文件
     */
    public static File getDefaultImageFile(Context context, String url) {
        if (context == null || TextUtils.isEmpty(url)) {
            return null;
        }
        return new File(FilepathUtil.getCacheRootPath(context)
                + File.separator + FilepathUtil.getIMAGES()
                + File.separator + FilepathUtil.getIMAGES()
                + File.separator + getVersionSubdirectoryName()
                + File.separator + getSubdirectory(url2FileName(url))
                , url2FileName(url) + ".cnt");
    }
}
