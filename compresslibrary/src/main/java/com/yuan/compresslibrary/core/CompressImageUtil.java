package com.yuan.compresslibrary.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.yuan.compresslibrary.config.CompressConfig;
import com.yuan.compresslibrary.listener.CompressRequestListener;
import com.yuan.compresslibrary.utils.Constants;

import java.io.File;


public class CompressImageUtil {
    private CompressConfig config;
    private Context context;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public CompressImageUtil(Context context, CompressConfig config) {
        this.config = config;
        this.context = context;
    }

    public void compress(String imgPath, CompressRequestListener listener) {
        Bitmap smallBitmap = CompressUtils1.getSmallBitmap(imgPath, 60);
        if (smallBitmap != null) {
            File file = CompressUtils1.saveImageFile(context, smallBitmap, "test1.png");
            Log.e("compress: ", file.getAbsolutePath());
            CompressUtils1.saveBitmap(smallBitmap, config.getCacheDir() + "/test1.png");
            listener.onCompressSuccess(imgPath);
        } else {
            listener.onCompressFailure(imgPath, "压缩失败");
        }

//        System.out.print( "=======:"+imgPath );
//        try {
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            listener.onCompressFailure(imgPath, "压缩失败" + e.getMessage());
//
//        }


    }
}
