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
                .setUnCompressMinPixel(100)//最小的像素不压缩
                .setUnCompressNormalPixel(100)//标准像素不压缩
                .setMaxPixel(1200)//长或宽不超过最大像素，
                .setMaxSize(200 * 1024)//压缩到的最大大小，单位B
                .setEnablePixelCompress(true)//是否启用像素压缩
                .setEnableQualityCompress(true)//是否启用质量压缩
                .setEnableReserveRaw(true)//是否保留源文件
                .setCacheDir(com.yuan.compresslibrary.utils.Constants.BASE_CACHE_PATH+com.yuan.compresslibrary.utils.Constants.compress_CACHE)//压缩后缓存图片目录
                .setShowCompressDialog(false)
                .create();
    }

    public void album(View view) {
//        CameraUtils.openAlbum(this, Constants.ALBUM_CODE);
        preCompress("/storage/emulated/0/DCIM/Camera/test.png");

    }

    public void camera(View view) {
        Uri outputUri;
        File file = CachePathUtils.getCameraCacheFile();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            outputUri = UriParseUtils.getCameraOutPutUri(this, file);
        } else {
            outputUri = Uri.fromFile(file);
        }
        cameraCachePath = file.getAbsolutePath();
        CameraUtils.hasCamera(this, CameraUtils.getCameraIntent(outputUri), Constants.CAMERA_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //拍照返回
        if (requestCode == Constants.CAMERA_CODE && resultCode == RESULT_OK) {
            preCompress(cameraCachePath);

        }
        //相册返回
        if (requestCode == Constants.ALBUM_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                String path = UriParseUtils.getPath(this, uri);
                //开始压缩
                Log.e("onActivityResult: ", path);
//                preCompress(path);
             preCompress("/storage/emulated/0/DCIM/Camera/test.png");
            }
        }
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
            }

            @Override
            public void onCompressFailure(ArrayList<Photo> photos, String... err) {
                Log.e("onCompressFailure: ", "压缩失败" + err[0]);
            }
        }).compress();
    }
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
