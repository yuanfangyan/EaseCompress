package com.yuan.compresslibrary.listener;

import com.yuan.compresslibrary.bean.Photo;

import java.util.ArrayList;

/**
 * 单张图片压缩时的监听
 */
public interface CompressRequestListener {
    void onCompressSuccess(String imgPath);

    void onCompressFailure(String imgPath, String err);
}
