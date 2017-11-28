package com.yzb.card.king.ui.other.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.yzb.card.king.util.UiUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/30 18:03
 */
public class SelectPicController
{
    private static final int PHOTO_WITH_CAMERA = 3;

    private static final int PHOTO_WITH_GALLERY = 2;

    private static final int PHOTO_REQUEST_CUT = 4;

    private Activity activity;

    private OnGetPicListener onGetPicListener;

    private Uri imageUri;

    private File outputImage;

    public void setOnGetPicListener(OnGetPicListener onGetPicListener)
    {
        this.onGetPicListener = onGetPicListener;
    }

    public SelectPicController(Activity activity)
    {
        this.activity = activity;
    }

    public void getPicFromGallery()
    {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                !Environment.isExternalStorageRemovable())
        {
            outputImage = new File(Environment.getExternalStorageDirectory(),
                    "output_image.jpg");
            imageUri = Uri.fromFile(outputImage);

            try
            {
                if (outputImage.exists())
                {
                    outputImage.delete();
                }
                outputImage.createNewFile();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            activity.startActivityForResult(intent, PHOTO_WITH_GALLERY);
        } else
        {
            UiUtils.shortToast("sd卡不可用");
        }
    }

    public void getPicFormCamera()
    {
        outputImage = new File(Environment.getExternalStorageDirectory(),
                "output_image.jpg");
        try
        {
            if (outputImage.exists())
            {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        //将File对象转换成Uri对象
        //Uri表标识着图片的地址
        imageUri = Uri.fromFile(outputImage);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //  拍照后保存图片的绝对路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent, PHOTO_WITH_CAMERA);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case PHOTO_WITH_CAMERA:// 拍照返回；
            case PHOTO_WITH_GALLERY:// 图库图片返回
                crop(data);
                break;
            case PHOTO_REQUEST_CUT:
                // 从剪切图片返回的数据
                if (data != null)
                {
                    afterCrop();
                }
                break;
        }
    }

    private void afterCrop()
    {
        Bitmap src;
        try
        {
            src = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(imageUri));
            if (src != null)
            {
                if (src.getByteCount() > 3 * 1024 * 1024 * 8)
                {
                    UiUtils.shortToast("图片太大了！");
                    return;
                }

                if (onGetPicListener != null)
                {
                    onGetPicListener.onGetPic(src);
                }
                if (outputImage.exists())
                {
                    outputImage.delete();
                }
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    /*
   * 剪切图片
   */
    private void crop(Intent data)
    {
        Uri uri;
        if (data != null)
        {
            uri = data.getData();
        } else
        {
            if (outputImage.exists() && outputImage.length() > 0)
                uri = imageUri;
            else
                return;
        }

        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        activity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    public interface OnGetPicListener
    {
        void onGetPic(Bitmap bitmap);
    }
}
