package com.yuan.compresslibrary.config;

public class CompressConfig {
    private int unCompressMinPixel = 1000;//最小的像素不压缩
    private int unCompressNormalPixel = 2000;//标准像素不压缩
    private int maxPixel = 1200;//长或宽不超过最大像素，单位px
    private int maxSize = 200 * 1024;//压缩到的最大大小，单位B
    private boolean enablePixelCompress = true;//是否启用像素压缩
    private boolean enableQualityCompress = true;//是否启用质量压缩
    private boolean enableReserveRaw = true;//是否保留源文件
    private String cacheDir;//压缩后缓存图片目录，非文件目录
    private boolean showCompressDialog = true;//是否显示压缩进度条

    public int getUnCompressMinPixel() {
        return unCompressMinPixel;
    }

    public void setUnCompressMinPixel(int unCompressMinPixel) {
        this.unCompressMinPixel = unCompressMinPixel;
    }

    public int getUnCompressNormalPixel() {
        return unCompressNormalPixel;
    }

    public void setUnCompressNormalPixel(int unCompressNormalPixel) {
        this.unCompressNormalPixel = unCompressNormalPixel;
    }

    public int getMaxPixel() {
        return maxPixel;
    }

    public void setMaxPixel(int maxPixel) {
        this.maxPixel = maxPixel;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isEnablePixelCompress() {
        return enablePixelCompress;
    }

    public void setEnablePixelCompress(boolean enablePixelCompress) {
        this.enablePixelCompress = enablePixelCompress;
    }

    public boolean isEnableQualityCompress() {
        return enableQualityCompress;
    }

    public void setEnableQualityCompress(boolean enableQualityCompress) {
        this.enableQualityCompress = enableQualityCompress;
    }

    public boolean isEnableReserveRaw() {
        return enableReserveRaw;
    }

    public void setEnableReserveRaw(boolean enableReserveRaw) {
        this.enableReserveRaw = enableReserveRaw;
    }

    public String getCacheDir() {
        return cacheDir;
    }

    public void setCacheDir(String cacheDir) {
        this.cacheDir = cacheDir;
    }

    public boolean isShowCompressDialog() {
        return showCompressDialog;
    }

    public void setShowCompressDialog(boolean showCompressDialog) {
        this.showCompressDialog = showCompressDialog;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private CompressConfig config;

        private Builder() {
            config = new CompressConfig();
        }


        public Builder setUnCompressMinPixel(int unCompressMinPixel) {
            config.setUnCompressMinPixel(unCompressMinPixel);
            return this;
        }

        public Builder setUnCompressNormalPixel(int unCompressNormalPixel) {
            config.setUnCompressNormalPixel(unCompressNormalPixel);
            return this;
        }

        public Builder setMaxPixel(int maxPixel) {
            config.setMaxPixel(maxPixel);
            return this;
        }

        public Builder setMaxSize(int maxSize) {
            config.setMaxSize(maxSize);
            return this;
        }

        public Builder setEnablePixelCompress(boolean enablePixelCompress) {
            config.setEnablePixelCompress(enablePixelCompress);
            return this;
        }

        public Builder setEnableQualityCompress(boolean enableQualityCompress) {
            config.setEnableQualityCompress(enableQualityCompress);
            return this;
        }

        public Builder setEnableReserveRaw(boolean enableReserveRaw) {
            config.setEnableReserveRaw(enableReserveRaw);
            return this;
        }

        public Builder setCacheDir(String cacheDir) {
            config.setCacheDir(cacheDir);
            return this;
        }

        public Builder setShowCompressDialog(boolean showCompressDialog) {
            config.setShowCompressDialog(showCompressDialog);
            return this;
        }

    }
}
