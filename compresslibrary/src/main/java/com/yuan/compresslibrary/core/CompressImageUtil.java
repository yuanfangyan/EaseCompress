package com.yuan.compresslibrary.core;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.yuan.compresslibrary.config.CompressConfig;
import com.yuan.compresslibrary.listener.CompressRequestListener;
import com.yuan.compresslibrary.utils.Constants;
import com.yuan.compresslibrary.utils.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;


public class CompressImageUtil {
    private CompressConfig config;
    private Context context;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ProgressDialog progressDialog;

    public CompressImageUtil(Context context, CompressConfig config) {
        this.config = config;
        this.context = context;
    }

    private int quality = 100;

    public void compress(String imgPath, CompressRequestListener listener) {
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imgPath, options);

            if (config.isShowCompressDialog()) {
                progressDialog = showProgressDialog((Activity) context, "正在压缩");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.show();
                    }
                });
            }

            if (config.isEnablePixelCompress()) {
                bitmap = pixelCompress(imgPath);
            }
            if (config.isEnableQualityCompress()) {
                bitmap = qualityCompress(bitmap, quality);
            }
            String path = FileUtils.saveBitmap(bitmap, config.getCacheDir());
            if (!TextUtils.isEmpty(path)) {
                dismiss(progressDialog);
                if (!config.isEnableReserveRaw()) {
                    boolean b = FileUtils.deleteSingleFile(imgPath);
                }
                listener.onCompressSuccess(path);
            } else {
                listener.onCompressFailure(imgPath, "压缩失败");
                dismiss(progressDialog);
            }


        } catch (Exception e) {
            e.printStackTrace();
            listener.onCompressFailure(imgPath, "压缩失败" + e.getMessage());
            dismiss(progressDialog);
        }

    }

    private void dismiss(final ProgressDialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing()) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            },2000);

        }
    }

    /**
     * 像素压缩
     *
     * @param path
     * @return
     */
    private Bitmap pixelCompress(String path) {
        Bitmap bm = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = calculateInSamleSize(options, config.getMaxPixel(), config.getMaxPixel());

        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(path, options);
        if (bm == null) {
            return null;
        }

        int degree = readPictureDegree(path);
        bm = rotateBitmap(bm, degree);
        return bm;
    }

    /**
     * 质量压缩
     *
     * @param bitmap
     * @return
     */
    private Bitmap qualityCompress(Bitmap bitmap, int quality) {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


    private static int calculateInSamleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqHeight) {
            int heightRatio = Math.round((float) height / (float) reqHeight);
            int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }
        return inSampleSize;
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, mtx, true);
    }

    private static int readPictureDegree(String filePath) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return degree;
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
