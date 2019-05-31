package com.yuan.compresslibrary.bean;

import java.io.Serializable;

public class Photo implements Serializable {
    private String originalPath;//图片的原始路径
    private boolean compressed;//是否压缩过
    private String compressPath;//压缩后的图片

    public Photo(String path) {
        this.originalPath = path;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    public String getCompressPath() {
        return compressPath;
    }

    public void setCompressPath(String compressPath) {
        this.compressPath = compressPath;
    }

    @Override
    public String toString() {
        return originalPath;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.toString().equals(originalPath);
    }
}
