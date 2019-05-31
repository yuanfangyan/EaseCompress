package com.yuan.compresslibrary;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.yuan.compresslibrary.bean.Photo;
import com.yuan.compresslibrary.config.CompressConfig;
import com.yuan.compresslibrary.core.CompressImageUtil;
import com.yuan.compresslibrary.listener.CompressImage;
import com.yuan.compresslibrary.listener.CompressRequestListener;

import java.io.File;
import java.util.ArrayList;

public class CompressManager implements CompressImage {
    private CompressImageUtil compressImageUtil;
    private CompressImage.CompressListener listener;//压缩监听，告知ui
    private CompressConfig config;//压缩配置
    private ArrayList<Photo> images;

    private CompressManager(Context context, CompressConfig compressConfig, ArrayList<Photo> images, CompressImage.CompressListener
            listener) {
        this.config = compressConfig;
        this.images = images;
        this.listener = listener;
        compressImageUtil = new CompressImageUtil(context, config);
    }

    public static CompressManager build(Context context, CompressConfig compressConfig,
                                        ArrayList<Photo> images, CompressImage.CompressListener listener) {

        return new CompressManager(context, compressConfig, images, listener);
    }

    @Override
    public void compress() {
        if (images == null || images.isEmpty()) {
            listener.onCompressFailure(images, "图片集合为空");
            return;
        }
        for (Photo image : images) {
            if (image == null) {
                listener.onCompressFailure(images, "图片集合中某张图片为空");
                return;
            }
        }
        //开始递归从第一张开始
        compress(images.get(0));
    }

    //第一张图片
    private void compress(final Photo image) {
        if (TextUtils.isEmpty(image.getOriginalPath())) {
            //继续
            continueCompress(image, false);
            return;
        }
        File file = new File(image.getOriginalPath());

        if (!file.exists() || !file.isFile()) {
            continueCompress(image, false);
            return;
        }
        if (file.length() < config.getUnCompressMinSize()) {
            continueCompress(image, true);
            return;
        }
        Log.e("compress>>>>: ", image.getOriginalPath());
        compressImageUtil.compress(image.getOriginalPath(), new CompressRequestListener() {
            @Override
            public void onCompressSuccess(String imgPath) {
                image.setCompressPath(imgPath);
                //压缩成，设置改对象属性为已经压缩，并递归到下一张要压缩的图片对象
                continueCompress(image, true);
            }

            @Override
            public void onCompressFailure(String imgPath, String err) {
                continueCompress(image, false, err);

            }
        });
    }

    private void continueCompress(Photo image, boolean isCompress, String... err) {
        image.setCompressed(isCompress);
        //获取当前压缩过的这张图片对象，索引值
        int index = images.indexOf(image);
        //是否为压缩的图片集合中，最后一张图片对象
        if (index == images.size() - 1) {
            //全部压缩完成，通知ui
            handlerCallBack(err);
        } else {
            compress(images.get(index + 1));
        }

    }

    private void handlerCallBack(String... err) {
        //如果有错误信息
        if (err.length > 0) {
            listener.onCompressFailure(images, err);
        }
        for (Photo image : images) {
            //如果存在没有压缩的图片，或者压缩失败
            if (!image.isCompressed()) {
                listener.onCompressFailure(images, image.getOriginalPath() + "图片压缩失败");
                return;
            }
            listener.onCompressSuccess(images);
        }
    }

}
