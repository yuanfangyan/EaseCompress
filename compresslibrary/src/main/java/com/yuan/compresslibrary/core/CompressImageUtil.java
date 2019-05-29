package com.yuan.compresslibrary.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import com.yuan.compresslibrary.config.CompressConfig;
import com.yuan.compresslibrary.listener.CompressRequestListener;


public class CompressImageUtil {
    private CompressConfig config;
    private Context context;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public CompressImageUtil(Context context, CompressConfig config) {
        this.config = config;
        this.context = context;
    }

    public void compress(String imgPath, CompressRequestListener listener) {
//        if (config.isEnablePixelCompress()) {
//
//        }
//        if (config.isEnableQualityCompress()){
//
//        }
        try {
            Bitmap smallBitmap = CompressUtils1.getSmallBitmap(imgPath, 60);
            if (smallBitmap != null) {
                listener.onCompressSuccess(imgPath);
            } else {
                listener.onCompressFailure(imgPath, "压缩失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            listener.onCompressFailure(imgPath, "压缩失败" + e.getMessage());

        }


    }
}
