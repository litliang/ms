package com.yzb.card.king.util;

import android.app.Activity;
import android.util.Log;

public class ScreenSchema {
    private static int w;
    private static int h;
    private static int densityDpi;
    private static float density;
    private boolean b;
    static ScreenSchema schema = new ScreenSchema();

    public static ScreenSchema getInstance() {
        return schema;
    }


    public static void init(int width, int densityDpi) {
        Cfg.width = width;
        Cfg.densityDpi = densityDpi;
    }

    public static class Cfg {
        /***
         * 调试正确的机型的dpi
         * 可用Log.i("dpi","======== dpi "+activity.getResources().getDisplayMetrics().densityDpi);打出
         * 调试正确的机型的宽度
         */
        public static int densityDpi = 320;
        public static int width = 720;

    }


    public void adapt(Activity ctx) {
        if (!b) {

            b = true;


            ScreenSchema.w = ctx.getWindowManager().getDefaultDisplay()
                    .getWidth();
            ScreenSchema.h = ctx.getWindowManager().getDefaultDisplay()
                    .getHeight();
            ScreenSchema.densityDpi = ctx.getResources().getDisplayMetrics().densityDpi;
            ScreenSchema.density = ctx.getResources().getDisplayMetrics().density;

            Float densityDpi = new Float(
                    Cfg.densityDpi * new Float(ScreenSchema.w) / Cfg.width);

            Float density = new Float(densityDpi / 160);
            ScreenSchema.densityDpi = densityDpi.intValue();

            ScreenSchema.density = density;

        }
        ctx.getResources().getDisplayMetrics().densityDpi = ScreenSchema.densityDpi;
        ctx.getResources().getDisplayMetrics().density = ScreenSchema.density;

    }
}
