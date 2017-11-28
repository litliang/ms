package com.yzb.card.king.util;

import android.widget.ImageView;

import org.xutils.image.ImageOptions;

/**
 * 功能：xutil的图片配置；
 *
 * @author:gengqiyun
 * @date: 2016/8/15
 */
public class XImageOptionUtil
{

    /**
     * 获取圆形配置；
     *
     * @param radius    半径；
     * @param scaleType 缩放类型；
     * @return
     */
    public static ImageOptions getRoundImageOption(int radius, ImageView.ScaleType scaleType)
    {
        if (radius < 0)
        {
            return null;
        }
        ImageOptions imageOptions = new ImageOptions.Builder()
                //.setSize(imageWith, imageHeight)
                .setRadius(radius)
                .setCrop(true) //如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(scaleType) // 加载中或错误图片的ScaleType
                        //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .build();
        return imageOptions;
    }

    /**
     * 获取普通配置；
     *
     * @param scaleType 缩放类型；
     * @return
     */
    public static ImageOptions getCommonImageOption(ImageView.ScaleType scaleType)
    {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setCrop(false)
                .setUseMemCache(true)
                .setImageScaleType(scaleType)
                .build();
        return imageOptions;
    }
}
