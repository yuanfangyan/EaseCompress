package com.yuan.compresslibrary.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class CompressUtils2 {
    /**
     * 按大小缩放
     *
     * @param srcPath
     * @return
     */
    private Bitmap getImage(String srcPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设为true
        options.inJustDecodeBounds = true;
        Bitmap bitmap = null;

        options.inJustDecodeBounds = false;
        int w = options.outWidth;
        int h = options.outHeight;
        float hh = 800f;
        float ww = 480f;
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (w / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据高度固定大小缩放
            be = (int) (h / hh);
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时以及把options.inJustDecodeBounds设置为false了
        bitmap = BitmapFactory.decodeFile(srcPath, options);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩

    }

    /**
     * 质量压缩
     *
     * @param bitmap
     * @return
     */
    private static Bitmap compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {//循环判读，如果压缩后图片是否大于100KB,大于继续压缩
            baos.reset();//重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream
        Bitmap bm = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片

        return bm;
    }

    /**
     * 图片按比例大小缩放
     */
    private static Bitmap comp(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M，进行压缩避免再生成图片，
            baos.reset();//重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到daos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        int w = options.outWidth;
        int h = options.outHeight;
        float hh = 800f;
        float ww = 480f;
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (w / ww);
        } else if (w < h && h > hh) {
            be = (int) (h / hh);
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, options);
        return compressImage(bitmap);
    }
}
