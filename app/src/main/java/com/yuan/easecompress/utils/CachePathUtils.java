package com.yuan.easecompress.utils;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CachePathUtils {
    /**
     * @param fileName
     * @return 缓存文件夹
     */
    private static File getCameraCacheDir(String fileName) {
        File cache = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!cache.mkdirs() && (!cache.exists() || !cache.isDirectory())) {
            return null;
        } else {
            return new File(cache, fileName);
        }
    }

    /**
     * 获取图片文件名
     *
     * @return
     */
    private static String getBaseFileName() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
    }

    /**
     * 获取拍照缓存文件
     *
     * @return
     */
    public static File getCameraCacheFile() {
        StringBuffer fileName = new StringBuffer();
        fileName.append("camera_");
        fileName.append(getBaseFileName());
        fileName.append(".jpg");
        return getCameraCacheDir(fileName.toString());
    }
}
