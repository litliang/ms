package com.yzb.card.king.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;

import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.util.encryption.Base64;
import com.yzb.card.king.util.encryption.HashUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dev on 2015/12/28.
 */
public class ImageUtils
{
    public static File personDir;       //个人信息头像
    public static File tempDir;           //临时文件缓存目录

    private static String cachePath = "";
    private static String cameraImgPath;


    public static String getCameraImgPath()
    {
        return cameraImgPath;
    }

    /**
     * 获取调取相机拍照的图片的保存路径；
     *
     * @param context
     * @return
     */
    public static String setCameraImgPath(Context context)
    {
        if (context == null)
        {
            return null;
        }

        String folder = getCachePath(context) + File.separator + "PostPicture" + File.separator;
        File savedir = new File(folder);
        if (!savedir.exists())
        {
            savedir.mkdirs();
        }
        // 照片命名
        String picName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
        //裁剪头像的绝对路径
        cameraImgPath = folder + picName;
        return cameraImgPath;
    }

    public static String getCachePath(Context context)
    {
        File cacheDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            cacheDir = context.getExternalCacheDir();
        } else
        {
            cacheDir = context.getCacheDir();
        }
        if (!cacheDir.exists())
        {
            cacheDir.mkdirs();
        }
        return cacheDir.getAbsolutePath();
    }

    /***
     * 获取轮播图缓存目录
     *
     * @param cityCode
     * @return
     */
    public static File getCarouselFigureDir(Context context, String cityCode)
    {
        if ("".equals(cachePath))
        {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable())
            {
                cachePath = context.getExternalCacheDir().getPath();
            } else
            {
                cachePath = context.getFilesDir().getPath();
            }
        }

        File carouselFigure = new File(cachePath + File.separator + AppConstant.APP_CF + File.separator + cityCode);
        if (!carouselFigure.exists())
            carouselFigure.mkdirs();

        return carouselFigure;
    }

    /**
     * 获取轮播图
     */
    public static void getCarouselFigure(Context context, String cityCode, String imageStr, String filename) throws Exception
    {
        if ("".equals(cachePath))
        {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable())
            {
                cachePath = context.getExternalCacheDir().getPath();
            } else
            {
                cachePath = context.getFilesDir().getPath();
            }
        }

        File cfPic = new File(cachePath + File.separator + AppConstant.APP_CF + File.separator + cityCode + File.separator + filename);

        if (!cfPic.exists())
        {
            boolean newFile = cfPic.createNewFile();
            if (!newFile)
                return;
        }

        if (cfPic.length() < 1)
        {
            FileOutputStream outStream = new FileOutputStream(cfPic);
            byte[] picByte = Base64.decode(imageStr);
            InputStream in = new ByteArrayInputStream(picByte);
            byte[] picBuffer = new byte[1024];
            int len = 0;
            while ((len = in.read(picBuffer)) != -1)
            {
                outStream.write(picBuffer, 0, len);
            }
            outStream.flush();
            outStream.close();
            in.close();
        }

        return;
    }

    /**
     * 使用MD5算法对传入的key进行加密并返回。
     */
    public static String hashKeyForDisk(String key)
    {
        String cacheKey;
        try
        {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e)
        {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++)
        {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1)
            {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static boolean checkFileExists(Context context, String firstDir, String secondDir, String cityCode, String filename)
    {
        if ("".equals(cachePath))
        {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable())
            {
                cachePath = context.getExternalCacheDir().getPath();
            } else
            {
                cachePath = context.getFilesDir().getPath();
            }
        }

        String dir = cachePath;
        if (!StringUtils.isEmpty(firstDir))
            dir = dir + File.separator + firstDir;

        if (!StringUtils.isEmpty(secondDir))
            dir = dir + File.separator + secondDir;

        if (!StringUtils.isEmpty(cityCode))
            dir = dir + File.separator + cityCode;

        File checkFile = new File(dir, filename);
        if (!checkFile.getParentFile().exists())
        {
            checkFile.getParentFile().mkdirs();
            return false;
        }
        if (!checkFile.exists())
        {
            return false;
        }

        return true;
    }

    /**
     * @param fromFile 被复制的文件
     * @param toFile   复制的目录文件
     * @param rewrite  是否重新创建文件
     *                 <p>
     *                 <p>文件的复制操作方法
     */
    public static void copyfile(File fromFile, File toFile, Boolean rewrite)
    {
        if (!fromFile.exists())
        {
            return;
        }
        if (!fromFile.isFile())
        {
            return;
        }
        if (!fromFile.canRead())
        {
            return;
        }
        if (!toFile.getParentFile().exists())
        {
            toFile.getParentFile().mkdirs();
        }
        if (toFile.exists() && rewrite)
        {
            toFile.delete();
        }

        try
        {
            FileInputStream fosfrom = new FileInputStream(fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);

            byte[] bt = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0)
            {
                fosto.write(bt, 0, c);
            }
            //关闭输入、输出流
            fosfrom.close();
            fosto.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户头像
     *
     * @return
     * @throws Exception
     */
    public static File getPersonPic() throws Exception
    {
        if (null == personDir)
        {
            personDir = new File(Environment.getExternalStorageDirectory() + "/" + AppConstant.APP_DIR);
            if (!personDir.exists())
                personDir.mkdirs();
        }
        File personPic = new File(personDir, "personPic.jpg");
        if (!personPic.exists())
        {
            personPic.createNewFile();
        }
        return personPic;
    }

    /**
     * 获取用户头像
     *
     * @param imageStr
     * @return
     * @throws Exception
     */
    public static File getPersonPic(String imageStr) throws Exception
    {
        if (null == personDir)
        {
            personDir = new File(Environment.getExternalStorageDirectory() + "/" + AppConstant.APP_DIR);
            if (!personDir.exists())
                personDir.mkdirs();
        }
        File personPic = new File(personDir, "personPic.jpg");
        if (!personPic.exists())
        {
            personPic.createNewFile();
        }
        if (personPic.length() < 1)
        {
            FileOutputStream outStream = new FileOutputStream(personPic);
            byte[] picByte = Base64.decode(imageStr);
            InputStream in = new ByteArrayInputStream(picByte);
            byte[] picBuffer = new byte[1024];
            int len = 0;
            while ((len = in.read(picBuffer)) != -1)
            {
                outStream.write(picBuffer, 0, len);
            }
            outStream.flush();
            outStream.close();
            in.close();
        }
        return personPic;
    }

    /**
     * 删除用户头像
     *
     * @return
     * @throws Exception
     */
    public static File delPersonPic() throws Exception
    {
        if (null == personDir)
        {
            personDir = new File(Environment.getExternalStorageDirectory() + "/" + AppConstant.APP_DIR);
            if (!personDir.exists())
                personDir.mkdirs();
        }
        File personPic = new File(personDir, "personPic.jpg");
        if (personPic.exists())
        {
            personPic.delete();
        }
        return personPic;
    }

    /***
     * 获取临时文件缓存目录
     *
     * @return
     */
    public static File getTempDir()
    {
        if (null == tempDir)
        {
            tempDir = new File(Environment.getExternalStorageDirectory() + "/" + AppConstant.APP_DIR + "/" + AppConstant.APP_TEMP);
            if (!tempDir.exists())
                tempDir.mkdirs();
        }
        return tempDir;
    }

    public static boolean checkFileExists(File file, String cityCode, String filename)
    {
        if (null == file)
        {
            return false;
        }
        File checkFile = new File(file, filename);
        if (!checkFile.exists())
        {
            return false;
        }
        return true;
    }

    public static void deleteFile(File file)
    {
        if (file.exists())
        { // 判断文件是否存在
            if (file.isFile())
            { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory())
            { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++)
                { // 遍历目录下所有的文件
                    deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        } else
        {
            //
        }
    }


    /**
     * 检测图像图片是否存在
     *
     * @return
     */
    public static boolean checkPic()
    {
        boolean result = false;

        if (null == personDir)
        {
            personDir = new File(Environment.getExternalStorageDirectory() + "/" + AppConstant.APP_DIR);
            if (personDir.exists())
            {
                File personPic = new File(personDir, "personPic.jpg");
                if (personPic.exists())
                {
                    if (personPic.length() > 0)
                    {
                        result = true;
                    }
                }
            }
        }

        return result;
    }

    /**
     * 将图片保存到本地时进行压缩, 即将图片从Bitmap形式变为File形式时进行压缩
     * File形式的图片确实被压缩了, 但是当你重新读取压缩后的file为 Bitmap是,它占用的内存并没有改变
     *
     * @param bmp
     * @param file
     */
    public static void compressBmpToFile(Bitmap bmp, File file)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 60;
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);

        while (baos.toByteArray().length / 1024 > 1024 && options != 10)
        {
            baos.reset();
            options -= 10;
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }

        while (baos.toByteArray().length / 1024 > 100 && options != 10)
        {
            baos.reset();
            options -= 10;
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }

        try
        {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 对Bitmap形式的图片进行压缩, 也就是通过设置采样率, 减少Bitmap的像素, 从而减少了它所占用的内存
     *
     * @param srcPath
     * @return
     */
    private Bitmap compressImageFromFile(String srcPath)
    {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;//
        float ww = 480f;//
        int be = 1;
        if (w > h && w > ww)
        {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh)
        {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率

        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//      return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        //其实是无效的,大家尽管尝试
        return bitmap;
    }

    public static Bitmap comp(Bitmap image)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 70;
        int length = baos.toByteArray().length / 1048576;
        options = 50;

        if (options != 0)
        {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }

        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();

        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        float hh = 1200f;
        float ww = 800f;

        int be = 1;//be=1表示不缩放
        if (w > h && w > ww)
        {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh)
        {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//降低图片从ARGB888到RGB565
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    private static Bitmap compressImage(Bitmap image)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        int length = baos.toByteArray().length / 102400;
        options = 50;

        if (options != 0)
        {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 20;//每次都减少20
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    private static int[] bankIds = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

    private static String[] bankLogoName = new String[]{
            "bank_logo_ccb.png", // 建设银行
            "bank_logo_cmb.png", // 招商银行
            "bank_logo_comm.png",  // 交通银行
            "bank_logo_boc.png", // 中国银行
            "bank_logo_icbc.png", // 工商银行
            "bank_logo_abc.png", // 农业银行
            "bank_logo_spdb.png", // 浦发银行
            "bank_logo_ecitic.png", // 中信银行
            "bank_logo_cmbc.png", // 民生银行
            "bank_logo_ceb.png", // 光大银行
            "bank_logo_hscb.png", // 徽商银行
            "bank_logo_psbc.png", // 邮政银行
            "bank_logo_pa.png", // 平安银行
            "bank_logo_cgb.png", // 广发银行
            "bank_logo_hxb.png", // 华夏银行
    };

    /**
     * 根据银行id获取银行logo
     *
     * @param bankId
     * @return bankName
     */
    public static String getBankName(Long bankId)
    {
        int index = binarySearch(bankIds, bankId, 0, bankIds.length - 1);
        if (index < 0)
        {
            return null;
        }
        return bankLogoName[index];
    }

    /**
     * 根据银行id获取银行logo
     *
     * @param bankId
     * @return
     */
//    public static int getBankImage(int bankId)
//    {
//        int index = binarySearch(bankIds, bankId, 0, bankIds.length - 1);
//        if (index < 0)
//        {
//            return 0;
//        }
//        return bankLogo[index];
//    }

    /**
     * 根据银行id获取银行logo+银行名称图片
     *
     * @param bankId
     * @return
     */
//    public static int getBankFont(int bankId) {
//        int index = binarySearch(bankIds, bankId, 0, bankIds.length - 1);
//        return bankFont[index];
//    }


    /**
     * 二分查找特定整数在整型数组中的位置(递归)
     *
     * @paramdataset
     * @paramdata
     * @parambeginIndex
     * @paramendIndex
     * @returnindex
     */
    public static int binarySearch(int[] dataset, Long data, int beginIndex, int endIndex)
    {
        int midIndex = (beginIndex + endIndex) / 2;
        if (data < dataset[beginIndex] || data > dataset[endIndex] || beginIndex > endIndex)
        {
            return -1;
        }
        if (data < dataset[midIndex])
        {
            return binarySearch(dataset, data, beginIndex, midIndex - 1);
        } else if (data > dataset[midIndex])
        {
            return binarySearch(dataset, data, midIndex + 1, endIndex);
        } else
        {
            return midIndex;
        }
    }



    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path)
    {
        int degree = 0;
        try
        {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
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

    /*
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap)
    {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        ;
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 图片序列化
     * @param bitmap
     * @return
     * @throws Exception
     */
    public static String picSerialize(Bitmap bitmap) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        return Base64.encode(baos.toByteArray());
    }







    /**
     * 获取上传文件code
     * @param filename
     * @return
     */
    public static String getCode(String filename){
        return HashUtil.getMD5(Utils.toData(System.currentTimeMillis(),16) + filename);
    }

}
