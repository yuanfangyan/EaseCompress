# EaseCompress
 对比  | 大小  | 像素
 ---- | ----- | ------  
 原图  | 3.67M | 4608*3456 
 压缩后  | 324KB | 768*576  
## 特点
1.支持单张，多张图片压缩
## 使用方式
step 1.
```
repositories {
       ...
        maven { url 'https://jitpack.io' }
    }
```  

step 2.
```
implementation 'com.github.yuanfangyan:EaseCompress:1.0.1'
```

## 使用方式
```
 CompressManager.build(this, CompressConfig.getDefaultConfig(), images, new CompressImage.CompressListener() {
            @Override
            public void onCompressSuccess(ArrayList<Photo> photos) {
                
            }

            @Override
            public void onCompressFailure(ArrayList<Photo> photos, String... err) {

            }
        });
 ```       
        
   自定义属性使用   
   ```
  CompressConfig  config = CompressConfig.builder()
                .setUnCompressMinSize(100)//100kb 不压缩
                .setMaxPixel(800)//长或宽不超过最大像素，
                .setEnablePixelCompress(true)//是否启用像素压缩
                .setEnableQualityCompress(true)//是否启用质量压缩
                .setEnableReserveRaw(true)//是否保留源文件
                .setShowCompressDialog(true)//是否显示dialog
                .create();
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
```
        
        
        
 
