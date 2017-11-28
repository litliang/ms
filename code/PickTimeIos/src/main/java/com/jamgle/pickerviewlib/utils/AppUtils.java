package com.jamgle.pickerviewlib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/6/3
 * 描  述：
 */
public class AppUtils {


    public static String toData(long time, int type) {
        if(time <= 0L) {
            return "";
        } else {
            String format = "yyyy-MM-dd HH:mm:ss";
            switch(type) {
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

            }

            SimpleDateFormat formatter = new SimpleDateFormat(format);
            String dateString = formatter.format(new Date(time));
            return dateString;
        }
    }

    public static long toTimestamp(String data, int type) {
        String format = "yyyy-MM-dd HH:mm:ss";
        if(type == 1) {
            format = "yyyy-MM-dd HH:mm:ss";
        } else if(type == 2) {
            format = "MM-dd HH:mm";
        } else if(type == 3) {
            format = "HH:mm";
        } else if(type == 4) {
            format = "yyyy-MM-dd";
        } else if(type == 5) {
            format = "yyyy-MM-dd HH:mm";
        } else if(type == 6) {
            format = "yyyy年MM月dd日 HH:mm";
        } else if (type == 7) {
            format = "yyyy-M-d";
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
}
