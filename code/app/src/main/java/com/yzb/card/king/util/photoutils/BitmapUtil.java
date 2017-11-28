package com.yzb.card.king.util.photoutils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil
{


    /**
     * @param x              图像的宽度
     * @param y              图像的高度
     * @param image          源图片
     * @param outerRadiusRat 圆角的大小
     * @return 圆角图片
     */
    public static Bitmap createFramedPhoto(int x, int y, Bitmap image, float outerRadiusRat)
    {
        // 根据源文件新建一个darwable对象
        Drawable imageDrawable = new BitmapDrawable(image);

        // 新建一个新的输出图片
        Bitmap output = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        // 新建一个矩形
        RectF outerRect = new RectF(0, 0, x, y);

        // 产生一个红色的圆角矩形
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint);

        // 将源图片绘制到这个圆角矩形上
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        imageDrawable.setBounds(0, 0, x, y);
        canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
        imageDrawable.draw(canvas);
        canvas.restore();

        return output;
    }


    /**
     * 旋转照片
     * 将一个Bitmap旋转给定的角度；
     */
    public static Bitmap turnBitmap(Bitmap img, int angel)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angel);
        int width = img.getWidth();
        int height = img.getHeight();
        img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
        return img;
    }

    /**
     * 读取图片的旋转的角度，还是三星的问题，需要根据图片的旋转角度正确显示
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path)
    {
        int degree = 0;
        try
        {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation)
            {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return degree;
    }

    public static int findBestSampleSize(int actualWidth, int actualHeight, int desiredWidth, int desiredHeight)
    {
        double wr = (double) actualWidth / desiredWidth;
        double hr = (double) actualHeight / desiredHeight;
        double ratio = Math.min(wr, hr);
        float n = 1.0f;
        while ((n * 2) <= ratio)
        {
            n *= 2;
        }
        return (int) n;
    }

    public static BitmapFactory.Options getBitmapOptions(String imagePath, int image_out_width, int image_out_height)
    {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, op);
        int width = op.outWidth;
        int height = op.outHeight;
        int scaleFactor = findBestSampleSize(width, height, image_out_width, image_out_height);

        op.inSampleSize = scaleFactor;
        op.inJustDecodeBounds = false;
        op.inPurgeable = true;
        return op;
    }


    /**
     * bitmap--> byte[]
     *
     * @param bitmap
     * @return
     */
    public static byte[] getBitmapByte(Bitmap bitmap)
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try
        {
            out.flush();
            out.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    /**
     * Drawable -->Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable)
    {

        Bitmap bitmap = Bitmap.createBitmap(

                drawable.getIntrinsicWidth(),

                drawable.getIntrinsicHeight(),

                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

                        : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        //canvas.setBitmap(bitmap);

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        drawable.draw(canvas);

        return bitmap;

    }

    /**
     * 保存图片到手机图片库
     *
     * @param context
     * @param bmp
     */
    public static void saveImageToGallery(Context context, Bitmap bmp)
    {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists())
        {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try
        {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try
        {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + appDir)));
    }


    public static Drawable resizeImage(Bitmap bitmap, int w, int h)
    {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return new BitmapDrawable(resizedBitmap);
    }

    /**
     * 获取Bitmap的二进制形式；
     *
     * @param mBitmap
     * @return
     */
    public static String getBase64(Bitmap mBitmap)
    {
        if (mBitmap == null)
        {
            return null;
        }
        return Base64.encodeToString(BitmapUtil.getBitmapByte(mBitmap), Base64.DEFAULT);
    }
}