package com.yuan.easecompress.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;

public class CameraUtils {
    /**
     * 许多定制的Android系统，并不带相机功能，如果强制调用，程序会崩溃
     *
     * @param activity    上下文
     * @param intent      相机意图
     * @param requestCode 回调标识
     */
    public static void hasCamera(Activity activity, Intent intent, int requestCode) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity为空");
        }
        PackageManager packageManager = activity.getPackageManager();
        boolean hasCamera = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                || packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
                || Camera.getNumberOfCameras() > 0;
        if (hasCamera) {
            activity.startActivityForResult(intent, requestCode);
        } else {
            Toast.makeText(activity, "当前设备没有相机", Toast.LENGTH_SHORT).show();
            throw new IllegalStateException("当前设备没有相机");
        }
    }

    /**
     * 获取拍照的Intent
     *
     * @param outputUri 拍照后图片的输出Uri
     * @return 方便
     */
    public static Intent getCameraIntent(Uri outputUri) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);//将拍取的照片保存到指定的URI
        return intent;
    }

    /**
     * 跳转到图库
     *
     * @param activity
     * @param requestCode
     */
    public static void openAlbum(Activity activity, int requestCode) {
        //调用图库，获取所有本地图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 显示圆形进度对话框
     *
     * @param activity
     * @param progressTile
     * @return
     */
    public static ProgressDialog showProgressDialog(Activity activity, String... progressTile) {
        if (activity == null || activity.isFinishing()) {
            return null;
        }
        String title = "提示";
        if (progressTile != null && progressTile.length > 0) {
            title = progressTile[0];
        }
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle(title);
        progressDialog.setCancelable(false);
        progressDialog.show();

        return progressDialog;
    }


}
