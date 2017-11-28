package com.yzb.card.king.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.os.Environment;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


import com.yzb.card.king.R;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by TungDX on 6/4/2015.
 */
public class Utils {


    /**
     * 处理数据 针对折扣信息，如 70 --》 7；78---》7.8
     *
     * @param numberF
     * @return
     */
    public static String handNumberToString(float numberF) {

        String handlerStr = "1";

        float a = numberF;

        System.out.println(a);

        String b = a + "";

        String[] array = b.split("\\.");

        String number = array[0];

        int length = number.length();

        if (length == 2) {

            StringBuffer sb = new StringBuffer();

            char a1 = number.charAt(0);

            sb.append(a1);
            System.out.println(a1);

            char a2 = number.charAt(1);

            if (a2 > '0') {
                sb.append(".").append(a2);
            }

            handlerStr = sb.toString();

        } else {

            handlerStr = number;
        }


        return handlerStr;
    }


    /**
     * @param context
     * @return
     * @throws IOException
     */
    public static File createTempFile(Context context) throws IOException {
        if (!hasExternalStorage()) {
            return createTempFile(context, context.getCacheDir());
        } else {
            return createTempFile(context,
                    context.getExternalFilesDir("caches"));
        }
    }

    /**
     * Check external exist or not.
     *
     * @return
     */
    public static boolean hasExternalStorage() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * Create temp file in folder
     *
     * @param context
     * @param folder  where place temp file
     * @return
     * @throws IOException
     */
    public static File createTempFile(Context context, File folder)
            throws IOException {
        String prefix = String.valueOf(System.currentTimeMillis());
        return File.createTempFile(prefix, null, folder);
    }

    public static String toData(long time, int type) {
        if (time <= 0L) {
            return "";
        } else {
            String format = "yyyy-MM-dd HH:mm:ss";
            switch (type) {
                case 1:
                    format = "yyyy-MM-dd HH:mm:ss";
                    break;
                case 2:
                    format = "MM-dd HH:mm";
                    break;
                case 3:
                    format = "HH:mm";
                    break;
                case 4:
                    format = "yyyy-MM-dd";
                    break;
                case 5:
                    format = "yyyy-MM-dd HH:mm";
                    break;
                case 6:
                    format = "yyyy年MM月dd日 HH:mm";
                    break;
                case 7:
                    format = "MM月dd日 HH:mm";
                    break;
                case 8:
                    format = "HH:mm:ss";
                    break;
                case 9:
                    format = "yyyy年MM月dd日";
                    break;
                case 10:
                    format = "yyyy/MM/dd HH:mm";
                    break;
                case 11:
                    format = "yyyy/MM/dd";
                    break;
                case 12:
                    format = "MM-dd";
                    break;
                case 13:
                    format = "yyyy/MM/dd HH:mm:ss";
                    break;
                case 14:
                    format = "MM月dd日";
                    break;
                case 15:
                    format = "dd";
                    break;
                case 16:
                    format = "yyyyMMddHHmmss";
                    break;
                case 17:
                    format = "yyyy-MM";
                    break;
            }
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.format(new Date(time));
        }
    }

    public static long toTimestamp(String data, int type) {
        String format = "yyyy-MM-dd HH:mm:ss";
        if (type == 1) {
            format = "yyyy-MM-dd HH:mm:ss";
        } else if (type == 2) {
            format = "MM-dd HH:mm";
        } else if (type == 3) {
            format = "HH:mm";
        } else if (type == 4) {
            format = "yyyy-MM-dd";
        } else if (type == 5) {
            format = "yyyy-MM-dd HH:mm";
        } else if (type == 6) {
            format = "yyyy年MM月dd日 HH:mm";
        } else if (type == 7) {
            format = "yyyy-M-d";
        } else if (type == 8) {
            format = "yyyy年MM月";
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format);

        try {
            Date e = formatter.parse(data);
            return e.getTime();
        } catch (ParseException var5) {
            var5.printStackTrace();
            return 0L;
        }
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day + "天" + hour + "小时" + min + "分" + sec + "秒";
    }

    /**
     * 获取日期差
     *
     * @param startStr
     * @param endStr
     * @return
     */
    public static String getHourSpace(String startStr, String endStr, int type) {

        if (TextUtils.isEmpty(startStr) || TextUtils.isEmpty(endStr)) {
            return "";
        }
        long a1 = toTimestamp(endStr, type);
        long b1 = toTimestamp(startStr, type);
        long temp;
        if (a1 < b1) {
            temp = a1;
            a1 = b1;
            b1 = temp;
        }
        long ab = a1 - b1;
        long h = ab / 1000 / 60 / 60;
        long m = ab / 1000 / 60 % 60;

        StringBuffer sb = new StringBuffer();

        if (h == 0) {
            sb.append(m).append("min");
        } else if (m == 0) {
            sb.append(h).append("h");
        } else {
            sb.append(h).append("h").append(m).append("min");
        }
        return sb.toString();

    }


    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param numStr
     * @return
     */
    public static String subZeroAndDot(String numStr) {
        if (TextUtils.isEmpty(numStr)) {
            return "0";
        }

        if (!isNumeric(numStr)) {
            return "0";
        }

        NumberFormat bf = NumberFormat.getInstance();

        bf.setGroupingUsed(false);

        double number = Double.parseDouble(numStr);

        return bf.format(number);

    }

    /**
     * 匹配是否包含数字
     *
     * @param str 可能为中文，也可能是-19162431.1254，不使用BigDecimal的话，变成-1.91624311254E7
     * @return
     * @author yutao
     * @date 2016年11月14日下午7:41:22
     */
    public static boolean isNumeric(String str) {
        // 该正则表达式可以匹配所有的数字 包括负数
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");

        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }

        Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 单关键字匹配并着色；
     *
     * @param content  要匹配的string；
     * @param mKeyWord 关键字；
     * @param color    颜色；格式 ：#FF3235
     * @return
     */
    public static CharSequence keyWordMatch(String content, String mKeyWord, String color) {
        CharSequence handleStr = content;

        if (!TextUtils.isEmpty(content) && content.contains(mKeyWord)) {
            int index = content.indexOf(mKeyWord);
            int len = mKeyWord.length();
            handleStr = Html.fromHtml(content.substring(0, index)
                    + "<font color=" + color + ">"
                    + content.substring(index, index + len) + "</font>"
                    + content.substring(index + len, content.length()));
        }
        return handleStr;
    }

    /**
     * 多关键字查询着色,避免后面的关键字成为特殊的HTML语言代码
     *
     * @param str    检索结果
     * @param inputs 关键字集合
     * @param color  颜色； 格式：#FF0000
     */
    public static String matchKeys(String str, List<String> inputs, String color) {
        StringBuffer resStr = new StringBuffer("");
        if (TextUtils.isEmpty(str)) {
            return resStr.toString();
        }
        int index = str.length();//用来做为标识,判断关键字的下标
        String next = "";//保存str中最先找到的关键字
        for (int i = inputs.size() - 1; i >= 0; i--) {
            String theNext = inputs.get(i);
            int theIndex = str.indexOf(theNext);
            if (theIndex == -1) {//过滤掉无效关键字
                inputs.remove(i);
            } else if (theIndex < index) {
                index = theIndex;//替换下标
                next = theNext;
            }
        }
        //如果条件成立,表示串中已经没有可以被替换的关键字,否则递归处理
        if (index == str.length()) {
            resStr.append(str);
        } else {
            resStr.append(str.substring(0, index));
            resStr.append("<font color=" + color + ">" + str.substring(index, index + next.length()) + "</font>");
            String str1 = str.substring(index + next.length(), str.length());
            matchKeys(str1, inputs, color); //剩余的字符串继续替换
        }
        return resStr.toString();
    }

    /**
     * @param input
     * @return
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return false;
    }


    /**
     * 旋转；
     *
     * @param imageView
     */
    public static void changeBackground(Context context, ImageView imageView) {
        Animation startAnimation = AnimationUtils.loadAnimation(context, R.anim.shuttle_rotate_0_180);
        imageView.startAnimation(startAnimation);
    }

    /**
     * 复位；
     *
     * @param imageView
     */
    public static void backBackground(Context context, ImageView imageView) {
        Animation startAnimation = AnimationUtils.loadAnimation(context, R.anim.shuttle_rotate_180_360);
        imageView.startAnimation(startAnimation);
    }

    /**
     * 获取绘制区域的宽度和高度
     *
     * @param activity
     * @return
     */
    public static int[] getAreaThree(Activity activity) {
        int[] result = new int[2];
        // 用户绘制区域
        Rect outRect = new Rect();
        activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);
        result[0] = outRect.width();
        result[1] = outRect.height();
        // end
        return result;
    }

    /**
     * 获取应用区域高度
     *
     * @author ysg
     * created at 2016/7/8 16:25
     */
    public static int getDisplayHeight(Context context) {
        Rect frame = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = getStatusBarHeight(context);
        //得到屏幕的整个高度
        int mFullDisplayHeight = ((Activity) context).getWindowManager().getDefaultDisplay()
                .getHeight();

//        int contentViewTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
//        int titleBarHeight = contentViewTop - statusBarHeight;

        //得到可显示屏幕高度
        int mDisplayHeight = mFullDisplayHeight - statusBarHeight;
        // Log.d("TEST", "status bar height:" + statusBarHeight);
        // Log.d("TEST", "mFullDisplayHeight====" + mFullDisplayHeight);
        return mDisplayHeight;
    }

    /**
     * 获取状态栏高度
     *
     * @author ysg
     * created at 2016/7/8 16:26
     */
    public static int getStatusBarHeight(Context context) {

        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 收集连续几个月份信息
     *
     * @param maxNumber
     * @return
     */
    public static int[] cellectionMonth(int maxNumber) {

        Calendar calendar = Calendar.getInstance();

        int month = calendar.get(Calendar.MONTH);

        int currentMonth = month + 1;//当前月份

        int max = maxNumber;//收集连续五个月份的数据

        int[] abc = new int[max];

        int sub = 12 - currentMonth;// 3


        if (sub >= max) {

            sub = max - 1;
        }

        int i = 0;

        for (; i <= sub; i++) {

            abc[i] = currentMonth + i;

        }

        if (max > sub) {

            int subA = max - sub - 1;

            for (int j = 0; j < subA; j++) {

                abc[i] = j + 1;
                i++;
            }

        }
        return abc;

    }

    /**
     * 删除目标值，并转换成string数组
     *
     * @param total
     * @param removeStr
     * @return
     */
    public static String[] changeToStringArrayRemoveTotalStr(String[] total, String removeStr) {

        List<String> list = Arrays.asList(total);

        List<String> tempList = new ArrayList<String>();

        tempList.addAll(list);

        String totalKey = removeStr;

        if (tempList.contains(totalKey)) {

            tempList.remove(totalKey);

        }
        String[] strABCD = tempList.toArray(new String[tempList.size()]);

        return strABCD;

    }
}
