package com.yzb.card.king.util;
 
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.yzb.card.king.util.photoutils.BitmapUtil;

//自定义MyView类继承自ImageView
public class ScaleView extends android.support.v7.widget.AppCompatImageView {
    private float x_down = 0;
    private float y_down = 0;
    //起始点的坐标
    private PointF start = new PointF();
    //中心点的坐标
    private PointF mid = new PointF();
    private float oldDist = 1f;
    private float oldRotation = 0;
    private Matrix matrix = new Matrix();
    private Matrix matrix1 = new Matrix();
    private Matrix savedMatrix = new Matrix();
 
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
 
    private boolean matrixCheck = false;
 
    //记录当前屏幕的宽度
    private int widthScreen;
    //记录当前屏幕的高度
    private int heightScreen;
 
    //在页面中显示的Bitmap图片
    private Bitmap kenan;


    public ScaleView(Activity activity, String kenan) {
        super(activity);
        //通过Bitampfactory读取drawable目录下的kenan资源
        this.kenan = BitmapFactory.decodeFile(kenan);
        //定义图片一个显示矩阵
        DisplayMetrics dm = new DisplayMetrics();
        //得到当前屏幕的显示矩阵存入dm变量
        activity.getWindowManager().
                getDefaultDisplay().getMetrics(dm);
        //通过显示矩阵得到当前屏幕的宽度和高度的像素值
        widthScreen = dm.widthPixels;
        heightScreen = dm.heightPixels;
 
        matrix = new Matrix();
        int scale = this.kenan.getWidth()/dm.widthPixels;
        int y = (heightScreen-this.kenan.getHeight())/2;
        matrix1.postScale(scale, scale, 0, y);
    }
 
    //显示view的时候回调onDraw
    protected void onDraw(Canvas canvas) {
        //首先保存当前页面已有的图像
        canvas.save();
        //按照当前的矩阵绘制kenan图片
        canvas.drawBitmap(kenan, matrix, null);
        //画图板恢复
        canvas.restore();
    }
 
    //当用户触摸此视图的时候回调次方法
    public boolean onTouchEvent(MotionEvent event) {
        //得到touch的事件类型
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                //当按下屏幕时，记录当前的状态为拖动
                mode = DRAG;
                //记录xy坐标
                x_down = event.getX();
                y_down = event.getY();
                //保存当前的矩阵
                savedMatrix.set(matrix);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //多个手指触摸的状态
                mode = ZOOM;
                //记录之前的两手指间距
                oldDist = spacing(event);
                //记录之前的角度
                oldRotation = rotation(event);
                //保存当前的图片矩阵
                savedMatrix.set(matrix);
                //得到旋转的中心点
                midPoint(mid, event);
                break;
            case MotionEvent.ACTION_MOVE:
                //当手指移动时的状态
                if (mode == ZOOM) {
                    //缩放并且平移
                    matrix1.set(savedMatrix);
                    //得到旋转的角度
                    float rotation =
                            rotation(event) - oldRotation;
                    //得到距离
                    float newDist = spacing(event);
                    //得到放大倍数
                    float scale = newDist / oldDist;
                    //缩放倍数
                    matrix1.postScale(scale, scale, mid.x, mid.y);
                    //得到旋转角度
//                    matrix1.postRotate(rotation, mid.x, mid.y);
                    //得到图片是否出边界
                    matrixCheck = matrixCheck();
                    if (matrixCheck == false) {
                        matrix.set(matrix1);
                        invalidate();
                    }
                } else if (mode == DRAG) {
                    //平行移动
                    matrix1.set(savedMatrix);
                    matrix1.postTranslate(event.getX() - x_down
                            , event.getY() - y_down);// 平移
                    matrixCheck = matrixCheck();
                    matrixCheck = matrixCheck();
                    if (matrixCheck == false) {
                        matrix.set(matrix1);
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
        }
        return true;
    }
 
    //对图片的矩阵进行检测
    private boolean matrixCheck() {
        float[] f = new float[9];
        matrix1.getValues(f);
        // 图片4个顶点的坐标
        float x1 = f[0] * 0 + f[1] * 0 + f[2];
        float y1 = f[3] * 0 + f[4] * 0 + f[5];
        float x2 = f[0] * kenan.getWidth()
                + f[1] * 0 + f[2];
        float y2 = f[3] * kenan.getWidth()
                + f[4] * 0 + f[5];
        float x3 = f[0] * 0 + f[1] *
                kenan.getHeight() + f[2];
        float y3 = f[3] * 0 + f[4] *
                kenan.getHeight() + f[5];
        float x4 = f[0] * kenan.getWidth() +
                f[1] * kenan.getHeight() + f[2];
        float y4 = f[3] * kenan.getWidth() +
                f[4] * kenan.getHeight() + f[5];
        // 图片现宽度
        double width = Math.sqrt((x1 - x2) *
                (x1 - x2) + (y1 - y2) * (y1 - y2));
        // 缩放比率判断
        if (width < widthScreen / 3 || width > widthScreen * 3) {
            return true;
        }
        // 出界判断
        if ((x1 < widthScreen / 3 && x2 < widthScreen / 3
                && x3 < widthScreen / 3
                && x4 < widthScreen / 3)
                || (x1 > widthScreen * 2 / 3
                && x2 > widthScreen * 2 / 3
                && x3 > widthScreen * 2 / 3
                && x4 > widthScreen * 2 / 3)
                || (y1 < heightScreen / 3
                && y2 < heightScreen / 3
                && y3 < heightScreen / 3
                && y4 < heightScreen / 3)
                || (y1 > heightScreen * 2 / 3
                && y2 > heightScreen * 2 / 3
                && y3 > heightScreen * 2 / 3
                && y4 > heightScreen * 2 / 3)) {
            return true;
        }
        return false;
    }
 
    // 触碰两点间距离
    private float spacing(MotionEvent event) {
        //通过三角函数得到两点间的距离
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }
 
    // 取手势中心点
    private void midPoint(PointF point, MotionEvent event) {
        //得到手势中心点的位置
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
 
    // 取旋转角度
    private float rotation(MotionEvent event) {
        //得到两个手指间的旋转角度
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }
}