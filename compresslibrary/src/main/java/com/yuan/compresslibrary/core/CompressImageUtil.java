package com.yuan.compresslibrary.core;

import android.content.Context;
import android.os.Handler;

import com.yuan.compresslibrary.config.CompressConfig;
import com.yuan.compresslibrary.listener.CompressRequestListener;


public class CompressImageUtil {
    private CompressConfig config;
    private Context context;
    private Handler mHandler = new Handler();

    public CompressImageUtil(Context context, CompressConfig config) {
        this.config = config;
        this.context = context;
    }

    public void compress(String imgPath, CompressRequestListener listener) {
        if (config.isEnablePixelCompress()) {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
