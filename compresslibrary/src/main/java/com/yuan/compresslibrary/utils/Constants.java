package com.yuan.compresslibrary.utils;

import android.os.Environment;

public class Constants {
    //缓存根目录
    public static final String BASE_CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/";
    //压缩后缓存目录
    public static final String compress_CACHE = "compress_cache";

}
