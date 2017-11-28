package com.yzb.card.king.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

/**
 * 二维码工具
 */
public class QrUtil {

    private static int WHITE = 0xFFFFFFFF;
    private static int BLACK = 0xFF000000;

    private static final int IMAGE_HALFWIDTH = 26;//宽度值，影响中间图片大小

    /**
     * 生成二维码图片
     */
    public static Bitmap createQrImage(String msg, int qrWidth, int qrHeight) {
        Bitmap bitmap = null;
        try {
            //判断URL合法性
            if (msg == null || "".equals(msg) || msg.length() < 1) {
                return bitmap;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(msg, BarcodeFormat.QR_CODE, qrWidth, qrHeight, hints);
            int[] pixels = new int[qrWidth * qrHeight];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < qrHeight; y++) {
                for (int x = 0; x < qrWidth; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * qrWidth + x] = BLACK;
                    } else {
                        pixels[y * qrWidth + x] = WHITE;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            bitmap = Bitmap.createBitmap(qrWidth, qrHeight, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, qrWidth, 0, 0, qrWidth, qrHeight);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 生成二维码
     * @param string 二维码中包含的文本信息
     * @param mBitmap logo图片
     * @param format 编码格式
     * @return Bitmap 位图
     * @throws WriterException
     */
    public static Bitmap createCode(String string, Bitmap mBitmap, BarcodeFormat format) throws WriterException {
        Matrix m = new Matrix();

        float sx = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getWidth();

        float sy = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getHeight();

        m.setScale(sx, sy);//设置缩放信息

        //将logo图片按martix设置的信息缩放
        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), m, false);

        Hashtable hst = new Hashtable();

        hst.put(EncodeHintType.CHARACTER_SET, "UTF-8");//设置字符编码

        BitMatrix matrix = new MultiFormatWriter().encode(string, format, 400, 400, hst);//生成二维码矩阵信息

        int width = matrix.getWidth();//矩阵高度
        int height = matrix.getHeight();//矩阵宽度

        int halfW = width / 2;
        int halfH = height / 2;

        int[] pixels = new int[width * height];//定义数组长度为矩阵高度*矩阵宽度，用于记录矩阵中像素信息

        for (int y = 0; y < height; y++) {//从行开始迭代矩阵
            for (int x = 0; x < width; x++) {//迭代列
                if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH && y > halfH - IMAGE_HALFWIDTH && y < halfH + IMAGE_HALFWIDTH) {//该位置用于存放图片信息
                    //记录图片每个像素信息
                    pixels[y * width + x] = mBitmap.getPixel(x - halfW + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
                } else {
                    if (matrix.get(x, y)) {//如果有黑块点，记录信息
                        pixels[y * width + x] = 0xff000000;//记录黑块信息
                    }
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
