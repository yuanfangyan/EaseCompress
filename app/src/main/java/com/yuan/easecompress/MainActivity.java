package com.yuan.easecompress;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yuan.compresslibrary.CompressManager;
import com.yuan.compresslibrary.bean.Photo;
import com.yuan.compresslibrary.config.CompressConfig;
import com.yuan.compresslibrary.listener.CompressImage;
import com.yuan.easecompress.utils.CachePathUtils;
import com.yuan.easecompress.utils.CameraUtils;
import com.yuan.easecompress.utils.Constants;
import com.yuan.easecompress.utils.UriParseUtils;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private String cameraCachePath;//拍照后，源文件路径
    private CompressConfig config;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        config = CompressConfig.builder()
                .setUnCompressMinSize(100)//100kb 不压缩
                .setMaxPixel(800)//长或宽不超过最大像素，
                .setEnablePixelCompress(true)//是否启用像素压缩
                .setEnableQualityCompress(true)//是否启用质量压缩
                .setEnableReserveRaw(true)//是否保留源文件
                .setShowCompressDialog(true)
                .create();


    }

    public void compress(View view) {
        preCompress("/storage/emulated/0/DCIM/Camera/test.png");

    }


    private void preCompress(String path) {
        ArrayList<Photo> photos = new ArrayList<>();
        photos.add(new Photo(path));
        if (!photos.isEmpty()) {
            compress(photos);
        }
    }

    public void compress(ArrayList<Photo> photos) {

        CompressManager.build(this, config, photos, new CompressImage.CompressListener() {
            @Override
            public void onCompressSuccess(ArrayList<Photo> photos) {
                Log.e("onCompressSuccess: ", "压缩成功");
                Log.e("onCompressSuccess>>>>>", photos.get(0).getCompressPath());
            }

            @Override
            public void onCompressFailure(ArrayList<Photo> photos, String... err) {
                Log.e("onCompressFailure: ", "压缩失败" + err[0]);
            }
        }).compress();
    }


}
