package com.yuan.compresslibrary.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.Executor;

public class CompressUtils1 {
    /**
     * 压缩图片文件到bitmap
     *
     * @param filePath
     * @param quality
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int quality) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        //计算采样率，现在主流手机比较多是800*480分辨率
        options.inSampleSize = calculateInSamleSize(options, 480, 800);

        //用样本大小集解码位图
        options.inJustDecodeBounds = false;

        Bitmap bm = BitmapFactory.decodeFile(filePath, options);
        if (bm == null) {
            return null;
        }
        //读取图片角度
        int degree = readPictureDegree(filePath);
        //旋转位图
        bm = rotateBitmap(bm, degree);
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bm;
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
     * Bitmap对象保存成图片文件
     *
     * @param bitmap
     * @param filePath
     */
    public static void saveBitmap(Bitmap bitmap, String filePath) {
        File file = new File(filePath);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File saveImageFile(Context context, Bitmap bitmap, String fileName) {
        if (fileName == null) {
            System.out.print("saved fileName can not be null");
            return null;
        }
        fileName = fileName + ".png";
        String path = context.getFilesDir().getAbsolutePath();
        String laseFilePath = path + "/" + fileName;
        File file = new File(laseFilePath);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}
