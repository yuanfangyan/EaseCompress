package com.yuan.compresslibrary.listener;

import com.yuan.compresslibrary.bean.Photo;

import java.util.ArrayList;

/**
 * 图片集合的压缩返回监听
 */
public interface CompressImage {
    //开始压缩
    void compress();

    //图片集合的压缩结果返回
    interface CompressListener {
        void onCompressSuccess(ArrayList<Photo> photos);

        void onCompressFailure(ArrayList<Photo> photos, String... err);
    }
}
