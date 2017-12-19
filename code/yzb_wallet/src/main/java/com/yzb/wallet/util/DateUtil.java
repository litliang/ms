package com.yzb.wallet.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final String DEFAULT_DATE_FORMAT = "yyyyMMddHHmmss";

    public static final String DATE_FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT_DATE = "yyyy-MM-dd";

    public static final String DATE_FORMAT_DATE_DAY = "MM/dd";


    /**
     * convert long(such as: 1448849801000) to String
     *
     * @param dateLong
     * @return
     */
    public static final String long2String(Long dateLong, String format){
        SimpleDateFormat sdf= new SimpleDateFormat(format);
        return sdf.format(new Date(dateLong));
    }

    /**
     * convert string(such as: 2003-11-26) to Date
     * 
     * @param dateStr
     *            string format of date
     * @return
     */
    public static final Date string2Date(String dateStr) {
        return string2Date(dateStr + " 00:00:00", DATE_FORMAT_DATE_TIME);
    }

    /**
     * string to date, the format is same to SimpleDateFormat for example
     * "yyyyMMdd" "yyyy-MM-dd HH:mm:ss" etc please see
     * java.text.SimpleDateFormat
     * 
     * @param dateStr
     * @param format
     * @return
     */
    public static final Date string2Date(String dateStr, String format) {
        if (dateStr == null || dateStr.length() == 0)
            return null;
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * convert date to string according to default format
     * 
     * @param date
     * @return
     */
    public static final String date2String(Date date) {
        return date2String(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * convert date to string, and the format is same to SimpleDateFormat for
     * example "yyyyMMdd" "yyyy-MM-dd HH:mm" etc please see
     * java.text.SimpleDateFormat
     * 
     * @param date
     * @param format
     * @return
     */
    public static final String date2String(Date date, String format) {
        if (date == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static void main(String args[]) {

        Long LoginTimeL = 1448849801000L;

        String LoginTimeS = DateUtil.long2String(LoginTimeL, DateUtil.DEFAULT_DATE_FORMAT);
        System.out.println(LoginTimeS);

        Date LoginTimeD = DateUtil.string2Date(LoginTimeS, DateUtil.DEFAULT_DATE_FORMAT);
        System.out.println(LoginTimeD);

        int days = naturalDaysBetween(LoginTimeD, Calendar.getInstance().getTime());

        System.out.println(days);
    }

    /**
     * date 2006-04-10 author :zhaopeng
     * 
     */
    private static SimpleDateFormat sf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public static Date getWeek(Date date) {

        Calendar c = getCalendar(date);

//        int m = c.get(Calendar.DAY_OF_WEEK);
//        if (m - 1 == 0) {
//            c.add(Calendar.DAY_OF_WEEK, -6);
//        } else {
//            c.add(Calendar.DAY_OF_WEEK, -(m - 2));
//        }
        c.add(Calendar.DAY_OF_WEEK, -Calendar.DAY_OF_WEEK);
        sf.format(c.getTime());
        return c.getTime();

    }

    public static Date getMonth(Date date) {
        Calendar c = getCalendar(date);
        int m = c.get(Calendar.DAY_OF_MONTH);

        c.add(Calendar.DAY_OF_MONTH, -(m - 1));
        return c.getTime();
    }

    public static void getMailConsole() {

    }

    public static Date getMonth(Date date, int num) {
        Calendar c = getCalendar(date);
        c.add(Calendar.MONTH, -num);
        return c.getTime();
    }

    public static Date getYear(Date date) {
        Calendar c = getCalendar(date);
        c.add(Calendar.YEAR, -1);
        return c.getTime();
    }

    /**
     * add by cjh convert date to string, and the format is same to
     * SimpleDateFormat for example "yyyyMMdd" "yyyy-MM-dd HH:mm" etc please see
     * java.text.SimpleDateFormat
     * 
     * @param date
     * @return
     */
    public static final String date2ChineseString(Date date) {
        if (date == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat("yyyy年M月d日");
        String str = df.format(date);
        int yearBorder = str.indexOf("年");
        int monthBorder = str.indexOf("月");
        int dayBorder = str.indexOf("日");
        return getChineseDate(str.substring(0, 1))
                + getChineseDate(str.substring(1, 2))
                + getChineseDate(str.substring(2, 3))
                + getChineseDate(str.substring(3, 4)) + '年'
                + getChineseDate(str.substring(yearBorder + 1, monthBorder))
                + '月'
                + getChineseDate(str.substring(monthBorder + 1, dayBorder))
                + '日';
    }

    /**
     * add by cjh ��������
     * 
     * @param date
     * @return
     */
    public static String getChineseDate(String date) {
        StringBuffer sb = new StringBuffer();
        if (date.length() == 2) {
            String ten = date.substring(0, 1); // ʮλ
            String entries = date.substring(1, 2); // ��λ
            if (ten.substring(0).equals("1")) {
                sb.append("ʮ");
            }
            if (ten.substring(0).equals("2")) {
                sb.append("��ʮ");
            }
            if (ten.substring(0).equals("3")) {
                sb.append("��ʮ");
            }
            if (!entries.equals("0")) {
                sb.append(number2Chinese(entries));
            }
        } else {
            String entries = date.substring(0);
            sb.append(number2Chinese(entries));
        }
        return sb.toString();
    }

    /**
     * add by cjh ����ת����
     * 
     * @param number
     * @return
     */
    public static String number2Chinese(String number) {
        char[] numberChars = number.toCharArray();
        switch (numberChars[0]) {
        case '0':
            return "零";
        case '1':
            return "一";
        case '2':
            return "二";
        case '3':
            return "三";
        case '4':
            return "四";
        case '5':
            return "五";
        case '6':
            return "六";
        case '7':
            return "七";
        case '8':
            return "八";
        case '9':
            return "九";
        default:
            return "";
        }
    }

    /**
     * Returns natural days between beginDate and endDate. Positive number if
     * beginDate before c2, negative if beginDate after endDate, 0 if beginDate
     * and endDate represent the same day.
     * 
     * @param beginDate
     *            the begin date
     * @param endDate
     *            the end date
     * @return natural days between begin date and end date
     */
    public static int naturalDaysBetween(Date beginDate, Date endDate) {
        long msPerDay = 1000 * 60 * 60 * 24;
        Calendar c1 = getCalendar(beginDate);
        Calendar c2 = getCalendar(endDate);
        long msDiff = c2.getTimeInMillis() - c1.getTimeInMillis();
        int days = (int) (msDiff / msPerDay);
        int msResidue = (int) (msDiff % msPerDay);
        Calendar c3 = Calendar.getInstance();
        c3.setTimeInMillis(c2.getTimeInMillis() - msResidue);
        Calendar c4 = (Calendar) c2.clone();
        c4.add(Calendar.DAY_OF_MONTH, -1);
        if (c3.get(Calendar.DAY_OF_MONTH) == c4.get(Calendar.DAY_OF_MONTH))
            days++;
        else {
            c4.add(Calendar.DAY_OF_MONTH, 2);
            if (c3.get(Calendar.DAY_OF_MONTH) == c4.get(Calendar.DAY_OF_MONTH))
                days--;
        }
        return days;
    }

    /**
     * Returns natural days,hours and minutes between beginDate and endDate.
     * 
     * @author johnny 2007-1-31
     * @param beginDate
     *            the begin date
     * @param endDate
     *            the end date
     * @return int[]:[0]:days;[1]:hours;[2]:minutes
     */
    public static int[] naturalDHMBetween(Date beginDate, Date endDate) {
        int[] dhm = new int[3];
        if (beginDate == null || endDate == null)
            return dhm;
        long intervalSecond, leftSecond;
        intervalSecond = leftSecond = (endDate.getTime() - beginDate.getTime()) / 1000;
        dhm[0] = (int) intervalSecond / (60 * 60 * 24);
        leftSecond = leftSecond - dhm[0] * 60 * 60 * 24;
        dhm[1] = (int) leftSecond / (60 * 60);
        leftSecond = leftSecond - dhm[1] * 60 * 60;
        dhm[2] = (int) leftSecond / 60;
        return dhm;
    }

    public static Calendar getCalendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    public static Date earliestOfDate(Date date) {
        Calendar c = getCalendar(date);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date latestOfDate(Date date) {
        Calendar c = getCalendar(date);
        c.set(Calendar.HOUR, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    public static int getHourOfDate(Date date) {
        Calendar c = getCalendar(date);
        return c.get(Calendar.HOUR);
    }

    public static boolean isFirstDayOfMonth(Date date) {
        Calendar c = getCalendar(date);
        return c.get(Calendar.DATE) == 1;
    }

    /**
     * Ϊָ���������ָ����ݡ��·ݺ�������µ�����
     */
    public static Date addDate(Date date, int year, int month, int day) {
        Calendar c = getCalendar(date);
        c.add(Calendar.YEAR, year);
        c.add(Calendar.MONTH, month);
        c.add(Calendar.DATE, day);
        return c.getTime();
    }

    /**
     * Ϊָ���������ָ��Сʱ�����ӡ���ͺ��룬�����µ�����
     */
    public static Date addTime(Date date, int hour, int minute, int second,
            int millisecond) {
        Calendar c = getCalendar(date);
        c.add(Calendar.HOUR_OF_DAY, hour);
        c.add(Calendar.MINUTE, minute);
        c.add(Calendar.SECOND, second);
        c.add(Calendar.MILLISECOND, millisecond);
        return c.getTime();
    }

    public static Date addDay(Date date, int days) {
        Calendar c = getCalendar(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public static Date getPreviousDate(Date date) {
        return addDay(date, -1);
    }

    public static boolean isTheSameDay(Date date1, Date date2) {
        String value1 = date2String(date1, DEFAULT_DATE_FORMAT);
        String value2 = date2String(date2, DEFAULT_DATE_FORMAT);
        return value1 != null && value1.equals(value2);
    }
}