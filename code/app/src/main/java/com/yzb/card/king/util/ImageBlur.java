package com.yzb.card.king.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.yzb.card.king.ui.app.activity.UserInfoActivity;

/**
 * Created by lirr on 2017/12/5.
 */

public class ImageBlur {

    /**     * 按正方形裁切图片
     */
    public static Bitmap ImageCrop(Bitmap bitmap) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        int wh = w > h ? h : w;// 裁切后所取的正方形区域边长

        int retX = w > h ? (w - h) / 2 : 0;//基于原图，取正方形左上角x坐标
        int retY = w > h ? 0 : (h - w) / 2;

        //下面这句是关键
        return Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
    }

    public void applyBlur(Bitmap bitmap,final ImageView imgBackgroung) {
        imgBackgroung.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                imgBackgroung.getViewTreeObserver().removeOnPreDrawListener(this);
                imgBackgroung.buildDrawingCache();
                Bitmap bmp = imgBackgroung.getDrawingCache();
                blur(bmp, imgBackgroung);
                return true;
            }
        });
    }

    /**
     * 一个高斯模糊的算法
     *
     * @param bkg
     * @param view
     */
    public void blur(Bitmap bkg, View view) {
        float radius = 20;
        Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth()),
                (int) (view.getMeasuredHeight()), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft(), -view.getTop());
        canvas.drawBitmap(bkg, 0, 0, null);

        RenderScript rs = RenderScript.create(view.getContext());

        Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
        blur.setInput(overlayAlloc);
        blur.setRadius(radius);
        blur.forEach(overlayAlloc);
        overlayAlloc.copyTo(overlay);
        view.setBackground(new BitmapDrawable(view.getContext().getResources(), overlay));
        rs.destroy();

    }


}
