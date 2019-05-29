package com.yuan.easecompress.utils;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.v4.content.FileProvider;


import java.io.File;

public class UriParseUtils {
    /**
     * 创建一个图片文件输出路径的Uri
     *
     * @param context
     * @param file
     * @return 转换后的Scheme为FileProvider的Uri
     */
    private static Uri getUriForFile(Context context, File file) {
        return FileProvider.getUriForFile(context, getFileProvider(context), file);
    }

    /**
     * 获取FileProvider路径  适配6.0
     *
     * @param context
     * @return
     */
    private static String getFileProvider(Context context) {
        StringBuffer name = new StringBuffer();
        name.append(context.getPackageName());
        name.append(".fileProvider");
        return name.toString();
    }

    /**
     * 获取拍照后照片的Uri
     *
     * @param context
     * @param cacheFile
     * @return
     */
    public static Uri getCameraOutPutUri(Context context, File cacheFile) {
        return getUriForFile(context, cacheFile);
    }

    public static String getPath(Context context, Uri uri) {
        boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);
            String[] split = documentId.split(":");
            String type = split[0];
            if ("primary".equalsIgnoreCase(type)) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            }
        } else if (isDownloadsDocument(uri)) {
            String id = DocumentsContract.getDocumentId(uri);
            Uri contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            return getDataColumn(context, contentUri, null, null);
        }
        return "";
    }

    private static String getDataColumn(Context context, Uri contentUri, Object o, Object o1) {
        return null;
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return false;
    }
}
