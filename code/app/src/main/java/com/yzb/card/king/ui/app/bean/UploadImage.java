package com.yzb.card.king.ui.app.bean;

import android.graphics.Bitmap;
import android.util.Base64;

import com.yzb.card.king.util.photoutils.BitmapUtil;

import org.xutils.common.util.MD5;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/2/22 11:40
 */
public class UploadImage
{
    String fileName;
    Bitmap image;

    public UploadImage(Bitmap image)
    {
        this.image = image;
    }

    public String getFileName()
    {
        return MD5.md5(getBase64())+".jpg";
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public Bitmap getImage()
    {
        return image;
    }

    public void setImage(Bitmap image)
    {
        this.image = image;
    }

    public String getBase64()
    {
        return Base64.encodeToString(BitmapUtil.getBitmapByte(image), Base64.DEFAULT);
    }
}
