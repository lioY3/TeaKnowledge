package com.example.teaknowledge.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.example.teaknowledge.R;

import java.io.File;

public class ImageLoaderUtil {

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480,800)
                //保存在缓存中图像的最大高度和最大宽度
                .diskCacheExtraOptions(480,800,null)
                //保存在sd卡中图像的最大高度和最大宽度
                .threadPoolSize(3)
                //设置线程池里线程的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                //动态设置不同线程的优先级数
                .denyCacheImageMultipleSizesInMemory()
                //确保每一张图片在缓存中只保存一次
                .memoryCache(new UsingFreqLimitedMemoryCache(1*1024*1024))
                //根据图像文件的使用频率来清理缓存中的图像文件，满足图像大小不超过1M
                .memoryCacheSize(2*1024*1024)
                //设置内存缓存空间的大小2M
                .diskCacheSize(50*1024*1024)
                //设置sd卡里的缓存空间大小
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                //设置缓存中的多个图像文件的先进先出处理顺序
                .diskCacheFileCount(100)
                //设置保存在缓存中的图像文件的数量
                .diskCache(new UnlimitedDiskCache(new File(Environment.getExternalStorageDirectory()+"")))
                //设置自定义图像缓存的路径
                .defaultDisplayImageOptions(getDisplayOptions())
                //设置图像加载时的配置信息
                .imageDownloader(new BaseImageDownloader(context,5*1000,30*1000))
                //设置图像加载过程中的连接超时时间以及下载最长时间
                .writeDebugLogs()
                //将图像加载过程中出现的异常写入日志
                .build();

        ImageLoader.getInstance().init(imageLoaderConfiguration);
    }


    BitmapProcessor preProcessor ;

    private static DisplayImageOptions getDisplayOptions() {
        DisplayImageOptions displayImageOptions;
        displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher_foreground)
                //图像加载过程中所显示的图像
                .showImageForEmptyUri(R.drawable.ic_launcher_background)
                //当读取不到图像的时候所显示的默认图像
                .showImageOnFail(R.drawable.ic_launcher_foreground)
                //图像读取失败时候所显示的图像
                .cacheInMemory(true)
                //设置下载的图像保存在缓存中
                .cacheOnDisk(false)
                //设置图像下载后不保存在sd卡的缓存中
                .considerExifParams(true)
                //设置图像具备旋转或翻转的几何变换
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
//                .imageScaleType(ImageScaleType.)
                //设置图像的缩放模式
                .bitmapConfig(Bitmap.Config.RGB_565)
                //设置图像解码类型:双字节构成的
                .delayBeforeLoading(1*1000)
                //设置图像加载前的延时
                .preProcessor(null)
                //对加载的图像进行预处理
                .resetViewBeforeLoading(true)
                //设置图像加载前进行重置
                .displayer(new RoundedBitmapDisplayer(200))
                //设置图像圆角的弧度
                .displayer(new FadeInBitmapDisplayer(200))
                //设置图像进入的渐变动画
                .build();
        return displayImageOptions;
    }

}
