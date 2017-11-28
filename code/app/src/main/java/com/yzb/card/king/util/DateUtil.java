package com.yzb.card.king.util;

import android.text.TextUtils;

import com.jamgle.pickerviewlib.utils.*;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {


    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };


    public static final String DEFAULT_DATE_FORMAT = "yyyyMMddHHmmss";

    public static final String DATE_FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_HOUR_MINIT = "yyyy-MM-dd HH:mm";

    public static final String DATE_FORMAT_DATE = "yyyy-MM-dd";

    public static final String DATE_FORMAT_DATE2 = "yyyy/MM/dd";
    public static final String DATE_FORMAT_DATE3 = "yyyyMMdd";

    public static final String DATE_FORMAT_DATE_MONTH = "yyyy年MM月";
    public static final String DATE_FORMAT_DATE_DAY = "MM/dd";

    public static final String DATE_FORMAT_DATE_DAY2 = "MM-dd";

    public static final String DATE_FORMAT_DATE_TIME2 = "yyyy年MM月dd日 HH:mm:ss";

    public static final String DATE_FORMAT_DATE_TIME3 = "yyyy年MM月dd日 HH:mm";

    public static final String DATE_FORMAT_MONTH_DAY = "MM月dd日";

    public static final String DATE_FORMAT_YEAR_MONTH = "yyyy-MM";

    public static final String DATE_FORMAT_HHMM = "HH:mm";
    public static final String DATE_FORMAT_HHMMSS = "HH:mm:ss";

    public static final String DATE_FORMAT_DATE_TIME4 = "yyyyMMddHHmmss";

    /**
     * convert long(such as: 1448849801000) to String
     *
     * @param dateLong
     * @return
     */
    public static final String long2String(long dateLong, String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(dateLong));
    }

    /**
     * convert string(such as: 2003-11-26) to Date
     *
     * @param dateStr string format of date
     * @return
     */
    public static final Date string2Date(String dateStr)
    {
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
    public static final Date string2Date(String dateStr, String format)
    {
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
    public static final String date2String(Date date)
    {
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
    public static final String date2String(Date date, String format)
    {
        if (date == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static void main(String args[])
    {

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
     */
    private static SimpleDateFormat sf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public static Date getWeek(Date date)
    {

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

    public static Date getMonth(Date date)
    {
        Calendar c = getCalendar(date);
        int m = c.get(Calendar.DAY_OF_MONTH);

        c.add(Calendar.DAY_OF_MONTH, -(m - 1));
        return c.getTime();
    }

    public static void getMailConsole()
    {

    }

    public static Date getMonth(Date date, int num)
    {
        Calendar c = getCalendar(date);
        c.add(Calendar.MONTH, -num);
        return c.getTime();
    }

    public static Date getYear(Date date)
    {
        Calendar c = getCalendar(date);
        c.add(Calendar.YEAR, -1);
        return c.getTime();
    }


    /**
     * Returns natural days between beginDate and endDate. Positive number if
     * beginDate before c2, negative if beginDate after endDate, 0 if beginDate
     * and endDate represent the same day.
     *
     * @param beginDate the begin date
     * @param endDate   the end date
     * @return natural days between begin date and end date
     */
    public static int naturalDaysBetween(Date beginDate, Date endDate)
    {
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
     * @param beginDate the begin date
     * @param endDate   the end date
     * @return int[]:[0]:days;[1]:hours;[2]:minutes
     * @author johnny 2007-1-31
     */
    public static int[] naturalDHMBetween(Date beginDate, Date endDate)
    {
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

    public static Calendar getCalendar(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    public static Date earliestOfDate(Date date)
    {
        Calendar c = getCalendar(date);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date latestOfDate(Date date)
    {
        Calendar c = getCalendar(date);
        c.set(Calendar.HOUR, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    public static int getHourOfDate(Date date)
    {
        Calendar c = getCalendar(date);
        return c.get(Calendar.HOUR);
    }


    /**
     * Ϊָ���������ָ����ݡ��·ݺ�������µ�����
     */
    public static Date addDate(Date date, int year, int month, int day)
    {
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
                               int millisecond)
    {
        Calendar c = getCalendar(date);
        c.add(Calendar.HOUR_OF_DAY, hour);
        c.add(Calendar.MINUTE, minute);
        c.add(Calendar.SECOND, second);
        c.add(Calendar.MILLISECOND, millisecond);
        return c.getTime();
    }

    public static Date addDay(Date date, int days)
    {
        Calendar c = getCalendar(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public static Date getPreviousDate(Date date)
    {
        return addDay(date, -1);
    }

    public static boolean isTheSameSecond(Date date1, Date date2)
    {
        String value1 = date2String(date1, DEFAULT_DATE_FORMAT);
        String value2 = date2String(date2, DEFAULT_DATE_FORMAT);
        return value1 != null && value1.equals(value2);
    }

    public static boolean isTheSameDay(Date date, Date calendarDay)
    {
        if (date != null && calendarDay != null) {
            String str1 = date2String(date, DateUtil.DATE_FORMAT_DATE);
            String str2 = date2String(calendarDay, DateUtil.DATE_FORMAT_DATE);
            return str1.equals(str2);
        }
        return false;
    }

    public static String getWeek(String pTime)
    {
        String Week = "";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            Week += "日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            Week += "六";
        }

        return Week;
    }

    //获得星期，今天，明天
    public static String getDateExplain(Date pTime)
    {
        String result = "";

        int duration = DateUtil.naturalDaysBetween(new Date(), pTime);

        if (0 == duration) {
            result = "今天";
        } else if (1 == duration) {
            result = "明天";
        } else {
            result = "周" + DateUtil.getWeek(DateUtil.date2String(pTime, DateUtil.DATE_FORMAT_DATE));
        }

        return result;
    }

    /**
     * 获得星期
     * @param pTime
     * @return
     */
    public static String getWeekInf(Date pTime)
    {

       String result = "周" + DateUtil.getWeek(DateUtil.date2String(pTime, DateUtil.DATE_FORMAT_DATE));

        return result;
    }

    public static String getDuration(String date1, String date2)
    {
        Long startDate = null;
        Long endDate = null;
        String duration = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            startDate = sdf.parse(date1).getTime();
            endDate = sdf.parse(date2).getTime();
            sdf = new SimpleDateFormat("H时mm分");
            duration = sdf.format(endDate - startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        duration = duration.replace("时", "h").replace("分", "min");
        return duration;
    }

    public static String getDate(Date date, int day)
    {
        if (null == date) {
            date = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("M月d日");
        return sdf.format(DateUtil.addDate(date, 0, 0, day));
    }

    /**
     * 时间格式化；
     *
     * @param flyingTime 格式： 00:00:01
     * @return 格式 1h20min20s
     */
    public static String formatDuration(String flyingTime)
    {
        if (!TextUtils.isEmpty(flyingTime)) {
            int firstPos = flyingTime.indexOf(":");
            int secondPos = flyingTime.indexOf(":", firstPos + 1);
            flyingTime = flyingTime.replaceFirst(":", "h");
            //不存在秒时；
            if (secondPos == -1) {
                flyingTime = flyingTime + "min";
            } else {
                flyingTime.replace(":", "min");
                flyingTime = flyingTime + "s";
            }
            return flyingTime;
        }
        return "";
    }

    /**
     * @param time   格式  yyyy-MM-dd hh:mm:ss
     * @param format
     * @return
     */
    public static String string2SpecString(String time, String format)
    {
        Date date = string2Date(time, format);
        String newDate = date2String(date, DATE_FORMAT_DATE);
        if (!TextUtils.isEmpty(newDate)) {
            return newDate.replace("-", ""); //
        }
        return "";
    }

    /**
     * 订单时间格式化
     *
     * @param orderTime 格式yyyy-MM-dd hh:mm:ss；
     * @return yyyyMMddHHmmss
     */
    public static String formatOrderTime(String orderTime)
    {
        if (!TextUtils.isEmpty(orderTime)) {
            return orderTime.replace("-", "")
                    .replace(" ", "")
                    .replace(":", "");
        }
        return null;
    }

    /**
     * 制定转化的格式
     *
     * @return
     */
    public static String getDateFromLongFormat(long sstime)
    {

        Date date = new Date(sstime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd    HH:mm");
        return simpleDateFormat.format(date);

    }


    /**
     * 将毫秒值转化为时间
     *
     * @return
     */
    public static String getDateFromLong(long sstime, int type)
    {
        Date date = new Date(sstime);
        SimpleDateFormat sdf = null;
        if (0 == type) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        return sdf.format(date);
    }


    /**
     * 方法说明:输入日期与今天的日期比较
     * <br/>String b_date 大日期
     * <br/>String s_date 小日期
     * <br/>返回日期文件名：20080611151112
     */
    public static String getDate_NowDate(String date)
    {
        String str = "yes";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //System.out.println("输入日期："+formatter.parse(date));
            if (date.length() == 10) {
                date = date + " 00:00:00";
            }
            long date_long = formatter.parse(date).getTime();
            Date now_date = new Date();
            //System.out.println("今天日期："+now_date);
            long now_date_long = now_date.getTime();
            if (date_long > now_date_long) {
                str = "yes";
            } else {
                str = "no";
            }
        } catch (ParseException e) {
            System.out.println("类:DateControl 方法:getBDate_SDate 执行:输入日期与今天的日期比较 时发生:ParseException异常");
        }
        //System.out.println(str);
        return str;
    }

    /**
     * 方法说明:生成日期文件名
     * <br/>
     * <br/>返回日期文件名：20080611151112
     */
    public static String getDateFileName()
    {
        String FileName = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssS");
        Date date = new Date();
        FileName = formatter.format(date);
        return FileName;
    }

    /**
     * 方法说明:取得当前日期格式
     * <br/>参数:i=0,结果 yyyy-MM-dd HH:mm:ss
     * <br/>参数:i=1,结果 yyyy-MM-dd
     * <br/>参数:i=2,结果 yyyy年MM月dd日 HH:mm:ss
     * <br/>参数:i=3,结果 yyyy年MM月dd日
     * <br/>参数:i=4,结果 yyyy-MM
     * <br/>返回日期：
     */
    public static String getDateTime(int i)
    {
        String nowdate = null;
        String Type = "yyyy-MM-dd HH:mm:ss";
        if (i == 0) {
            Type = "yyyy-MM-dd HH:mm:ss";
        } else if (i == 1) {
            Type = "yyyy-MM-dd";
        } else if (i == 2) {
            Type = "yyyy年MM月dd日 HH:mm:ss";
        } else if (i == 3) {
            Type = "yyyy年MM月dd日";
        } else if (i == 4) {
            Type = "yyyy-MM";
        } else if (i == 5) {
            Type = "yyyyMMdd";
        } else if (i == 6) {
            Type = "yyyyMMddHHmmss";
        }


        SimpleDateFormat formatter = new SimpleDateFormat(Type);
        Date date = new Date();
        nowdate = formatter.format(date);
        return nowdate;
    }

    /**
     * 方法说明:格式化日期
     * <br/>参数:date
     * <br/>参数:i=0,结果 yyyy-MM-dd HH:mm:ss
     * <br/>参数:i=1,结果 yyyy-MM-dd
     * <br/>参数:i=2,结果 yyyy年MM月dd日 HH:mm:ss
     * <br/>参数:i=3,结果 yyyy年MM月dd日
     * <br/>参数:i=4,结果 yyyyMMdd
     * <br/>返回日期：
     */
    public static String getFormatDate(String date, int i)
    {
        String nowdate = null;
        String Type = "yyyy-MM-dd HH:mm:ss";
        if (i == 0) {
            Type = "yyyy-MM-dd HH:mm:ss";
        } else if (i == 1) {
            Type = "yyyy-MM-dd";
        } else if (i == 2) {
            Type = "yyyy年MM月dd日 HH:mm:ss";
        } else if (i == 3) {
            Type = "yyyy年MM月dd日";
        } else if (i == 4) {
            Type = "yyyyMMdd";
        } else if (i == 5) {
            Type = "MM月dd日";
        } else if (i == 6) {
            Type = "MM月dd号";
        }
        if (date == null) return "";
        try {
            Date date_gsh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat(Type);
            nowdate = formatter.format(date_gsh);

        } catch (ParseException e) {
            System.out.println("类:DateControl 方法:getFormatDate 执行:转换日期格式 发生:ParseException异常");
        }

        return nowdate;
    }

    /**
     * 方法说明：获得本周的 周一 日期
     * <br/>参数：Date date
     * <br/>返回值：本周的 周一 日期
     */
    public static String getMonday(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 方法说明：获得本周的 周日 日期
     * <br/>参数：Date date
     * <br/>返回值：本周的 周日 日期
     */
    public static String getSunday(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY + 6);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 方法说明：把18.5小时转换成18小时30分钟
     * <br/>参数：小数时间
     * <br/>返回值：十分秒的时间
     */
    public static String Hms(float time)
    {
        String _time = "";
        float getTime = time * 60;
        try {
            DecimalFormat df = new DecimalFormat("00000");
            String itime = df.format(getTime);

            int ih = Integer.parseInt(itime) / 60;

            int is = Integer.parseInt(itime) % 60;

            _time = ih + "小时" + is + "分钟";

            if (ih < 1) {
                _time = is + "分钟";
            } else if (is < 1) {
                _time = ih + "小时";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return _time;
    }

    /**
     * 方法说明：把秒数转换成18小时30分钟
     * <br/>参数：小数时间
     * <br/>返回值：十分秒的时间
     */
    public static String Hm(float time)
    {
        String _time = "";
        //float getTime = time*60;
        try {
            DecimalFormat df = new DecimalFormat("00000");
            String itime = df.format(time);

            int ih = Integer.parseInt(itime) / 3600;

            int is = Integer.parseInt(itime) % 3600 / 60;

            _time = ih + "小时" + is + "分钟";

            if (ih < 1) {
                _time = is + "分钟";
            } else if (is < 1) {
                _time = ih + "小时";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return _time;
    }

    /**
     * 方法说明：计算两个时间差用秒数表示
     * <br/>参数：两个时间
     * <br/>返回值：十分秒的时间
     */
    public static long getMiaoShu(String s_date, String e_date)
    {
        long miaoshu = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (s_date.length() == 10) {
            s_date = s_date + " 00:00:00";
        }
        if (e_date.length() == 10) {
            e_date = e_date + " 23:59:59";
        }
        try {
            long s_date_long = formatter.parse(s_date).getTime();
            long e_date_long = formatter.parse(e_date).getTime();

            miaoshu = (e_date_long - s_date_long) / 1000;

            //如果有余数
            if (miaoshu % 2 == 1) {
                miaoshu = miaoshu + 1;
            }

        } catch (ParseException e) {
            System.out.println("类:DateControl 方法:getMiaoShu 执行:计算两个时间差用秒数表示 发生:ParseException异常");
        }
        return miaoshu;
    }

    /**
     * 得到日期的格式化字符串
     *
     * @param date      日期Date
     * @param formatStr 字符格式（yyyy）
     * @return
     */
    public static String getFormatDateStr(Date date, String formatStr)
    {

        if (date == null) {
            date = new Date();
        }
        String nowdate = null;

        SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
        nowdate = formatter.format(date);
        return nowdate;
    }


    /**
     * 得到昨日
     */
    public static String getYesterday(String day) throws Exception
    {
        // 得到昨日
        String yesterday = "";
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = myFmt.parse(day);

        long msel = myDate.getTime() - 60 * 60 * 24 * 1000;

        myDate.setTime(msel);

        yesterday = myFmt.format(myDate);

        return yesterday;
    }
    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = com.jamgle.pickerviewlib.utils.AppUtils.toData(today.getTime(),11);;
            String timeDate = com.jamgle.pickerviewlib.utils.AppUtils.toData(time.getTime(),11);//dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 将字符串转位日期类型
     *
     * @return
     */
    public static Date toDate(String sdate) {
        try {

            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }
}