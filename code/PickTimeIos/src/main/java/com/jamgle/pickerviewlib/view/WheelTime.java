package com.jamgle.pickerviewlib.view;

import android.content.Context;
import android.view.View;

import com.jamgle.pickerviewlib.R;
import com.jamgle.pickerviewlib.adapter.ArrayWheelAdapter;
import com.jamgle.pickerviewlib.adapter.NumericWheelAdapter;
import com.jamgle.pickerviewlib.lib.WheelView;
import com.jamgle.pickerviewlib.listener.OnItemSelectedListener;
import com.jamgle.pickerviewlib.utils.AppUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class WheelTime {
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private View view;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_mins;
    private WheelView wv_hourMin;
    private WheelView monthDayWeek;

    private TimePickerView.Type type;
    public static final int DEFULT_START_YEAR = 1900;
    public static final int DEFULT_END_YEAR = 2100;
    private int startYear = DEFULT_START_YEAR;
    private int endYear = DEFULT_END_YEAR;

    private int initYear;

    private List<Long> dataTime;

    private   ArrayList<String> minuArrayList;

    private OnTimeWheelTimeSelectListener listener;

    public void setOnTimeWheelTimeSelectListener(OnTimeWheelTimeSelectListener listener) {

        this.listener = listener;
    }


    public WheelTime(View view) {
        super();
        this.view = view;
        type = TimePickerView.Type.ALL;
        setView(view);
    }

    public WheelTime(View view, TimePickerView.Type type) {
        super();
        this.view = view;
        this.type = type;
        setView(view);
    }

    public void setPicker(int year, int month, int day) {
//		this.setPicker(year, month, day, 0, 0);
    }

    /**
     * @Description: TODO 弹出日期时间选择器
     */
    public void setPicker(int year, int month, int day, int h, int m) {
        initYear = year;
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);

        Context context = view.getContext();
        // 年
        wv_year = (WheelView) view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(startYear, endYear));// 设置"年"的显示数据
        wv_year.setLabel(context.getString(R.string.pickerview_year));// 添加文字
        wv_year.setCurrentItem(year - startYear);// 初始化时显示的数据

        // 月
        wv_month = (WheelView) view.findViewById(R.id.month);
        wv_month.setAdapter(new NumericWheelAdapter(1, 12));
        wv_month.setLabel(context.getString(R.string.pickerview_month));
        wv_month.setCurrentItem(month);

        // 日
        wv_day = (WheelView) view.findViewById(R.id.day);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (list_big.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 31));
        } else if (list_little.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 30));
        } else {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                wv_day.setAdapter(new NumericWheelAdapter(1, 29));
            else wv_day.setAdapter(new NumericWheelAdapter(1, 28));
        }
        wv_day.setLabel(context.getString(R.string.pickerview_day));
        wv_day.setCurrentItem(day - 1);


        wv_hours = (WheelView) view.findViewById(R.id.hour);
        wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        if (type != TimePickerView.Type.MONTH_DAY_WEEK_HOUR_MIN) {


            wv_hours.setLabel(context.getString(R.string.pickerview_hours));// 添加文字
        }
        wv_hours.setCurrentItem(h);
        wv_hours.setOnItemSelectedListener(onItemSelectedListener);
        wv_mins = (WheelView) view.findViewById(R.id.min);

        if (type != TimePickerView.Type.MONTH_DAY_WEEK_HOUR_MIN) {
            wv_mins.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字

            wv_mins.setAdapter(new NumericWheelAdapter(0, 59));

            wv_mins.setCurrentItem(m);
        }else{

            minuArrayList = new ArrayList<String>();

            minuArrayList.add("00");
            minuArrayList.add("15");
            minuArrayList.add("30");
            minuArrayList.add("45");

            ArrayWheelAdapter arryWadapter = new ArrayWheelAdapter(minuArrayList);

            wv_mins.setAdapter(arryWadapter);
            wv_mins.setCurrentItem(1);//设置第二位

        }


        wv_mins.setOnItemSelectedListener(onItemSelectedListener);

        // 添加"年"监听
        OnItemSelectedListener wheelListener_year = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int year_num = index + startYear;
                // 判断大小月及是否闰年,用来确定"日"的数据
                int maxItem = 30;
                if (list_big.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                    maxItem = 31;
                } else if (list_little.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                    maxItem = 30;
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0) || year_num % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        maxItem = 29;
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        maxItem = 28;
                    }
                }
                if (wv_day.getCurrentItem() > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }

                if (listener != null) {
                    listener.selectTime();
                }
            }
        };
        // 添加"月"监听
        OnItemSelectedListener wheelListener_month = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int month_num = index + 1;
                int maxItem = 30;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                    maxItem = 31;
                } else if (list_little.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                    maxItem = 30;
                } else {

                    int wheelviewYear = wv_year.getCurrentItem() + startYear;

                    if (wv_year.getVisibility() == View.GONE) {

                        wheelviewYear = initYear;

                    }

                    if ((wheelviewYear % 4 == 0 && (wv_year.getCurrentItem() + startYear) % 100 != 0) ||
                            wheelviewYear % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        maxItem = 29;
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        maxItem = 28;
                    }
                }
                if (wv_day.getCurrentItem() > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }
                if (listener != null) {
                    listener.selectTime();
                }

            }
        };
        wv_year.setOnItemSelectedListener(wheelListener_year);
        wv_month.setOnItemSelectedListener(wheelListener_month);
        wv_day.setOnItemSelectedListener(onItemSelectedListener);

        //时分 00:00
        wv_hourMin = (WheelView) view.findViewById(R.id.hourMin);


        //小时字符串集合
        ArrayList<String> hourStrAL = new ArrayList<String>();

        if (type == TimePickerView.Type.MONTH_DAY_HOURMIN) {


            for (int a = 0; a < 24; a++) {

                String tempStr = null;

                if (a < 10) {

                    tempStr = 0 + String.valueOf(a);

                } else {

                    tempStr = String.valueOf(a);
                }

                hourStrAL.add(tempStr);

            }
            //分钟集合
            ArrayList<String> minStrAl = new ArrayList<String>();

            for (int b = 0; b < 60; b = b + 10) {

                if (b == 0) {

                    minStrAl.add("00");

                } else {

                    minStrAl.add(String.valueOf(b));

                }

            }

            //分钟+小时集合
            ArrayList<String> hourMinStrAL = new ArrayList<String>();

            for (int c = 0; c < hourStrAL.size(); c++) {

                String tempH = hourStrAL.get(c);

                for (int d = 0; d < minStrAl.size(); d++) {

                    String tempM = minStrAl.get(d);

                    StringBuffer sb = new StringBuffer();

                    sb.append(tempH).append(":").append(tempM);

                    hourMinStrAL.add(sb.toString());

                }

            }

            ArrayWheelAdapter arryWadapter = new ArrayWheelAdapter(hourMinStrAL);

            wv_hourMin.setAdapter(arryWadapter);

            wv_hourMin.setCurrentItem(1);

            wv_hourMin.setOnItemSelectedListener(onItemSelectedListener);
        }

        monthDayWeek = (WheelView) view.findViewById(R.id.monthDayWeek);

        ArrayList<String> data = new ArrayList<String>();

        dataTime = new ArrayList<Long>();
        if (type == TimePickerView.Type.MONTH_DAY_WEEK_HOUR_MIN) {

            monthDayWeek.setCurrentItem(0);
            //从开始日期计算出一个月内的月份星期
            int a = year;
            int b = month + 1;
            int c = day;

            StringBuffer startDate = new StringBuffer();
            startDate.append(a).append("-").append(b).append("-").append(c);


            int a1 = year;
            int b1 = month + 1;
            int c1 = day;

            //处理月份
            if (b1 == 12) {

                b1 = 1;
                a1 = a1 + 1;
            } else {

                b1 = b1 + 1;
            }
            //处理天数
            if (c1 > 28) {
                //判断是否是闰年，判断大小月
                if (b1 == 2) {

                    if ((a1 % 4 == 0 && a1 % 100 != 0) || a1 % 400 == 0) {

                        c1 = 29;
                    } else {
                        c1 = 28;
                    }
                } else {

                    if (c1 == 31) {

                        //判断是否大月小月
                        if (list_big.contains(String.valueOf(b1))) {

                        } else {
                            c1 = 30;

                        }
                    }

                }

            }
            StringBuffer endDate = new StringBuffer();
            endDate.append(a1).append("-").append(b1).append("-").append(c1);

            //把日期处理成时间戳
            long startTime = AppUtils.toTimestamp(startDate.toString(), 7);

            long endTime = AppUtils.toTimestamp(endDate.toString(), 7);

            int timeIndex = 1;
            for (long startIndex = startTime; startIndex <= endTime; startIndex = startIndex + 24 * 60 * 60 * 1000) {

                long temp = startIndex;

                dataTime.add(startIndex);

                String tempStr = null;
                if (timeIndex == 1) {

                    tempStr = "今天";

                } else if (timeIndex == 2) {

                    tempStr = "明天";

                } else {

                    tempStr = AppUtils.toData(temp, 14) + " " + setSelfDriveTimeFrame(temp);
                }

                data.add(tempStr);

                timeIndex++;

            }

        }
        ArrayWheelAdapter monthDayWeekdapter = new ArrayWheelAdapter(data);

        monthDayWeek.setAdapter(monthDayWeekdapter);
        monthDayWeek.setOnItemSelectedListener(onItemSelectedListener);
        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        int textSize = 6;
        switch (type) {
            case ALL:
                textSize = textSize * 3;
                wv_hourMin.setVisibility(View.GONE);
                monthDayWeek.setVisibility(View.GONE);
                break;
            case YEAR_MONTH_DAY:
                textSize = textSize * 4;
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                wv_hourMin.setVisibility(View.GONE);
                monthDayWeek.setVisibility(View.GONE);
                break;
            case HOURS_MINS:
                textSize = textSize * 4;
                wv_year.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                wv_hourMin.setVisibility(View.GONE);
                monthDayWeek.setVisibility(View.GONE);
                break;
            case MONTH_DAY_HOUR_MIN:
                textSize = textSize * 3;
                wv_year.setVisibility(View.GONE);
                wv_hourMin.setVisibility(View.GONE);
                monthDayWeek.setVisibility(View.GONE);
                break;
            case YEAR_MONTH:
                textSize = textSize * 4;
                wv_day.setVisibility(View.GONE);
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                wv_hourMin.setVisibility(View.GONE);
                monthDayWeek.setVisibility(View.GONE);
                break;

            case YEAR:
                textSize = textSize * 4;
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                wv_hourMin.setVisibility(View.GONE);
                monthDayWeek.setVisibility(View.GONE);
                break;
            case MONTH_DAY_HOURMIN:

                textSize = textSize * 3;
                wv_year.setVisibility(View.GONE);
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                wv_hourMin.setVisibility(View.VISIBLE);
                monthDayWeek.setVisibility(View.GONE);
                break;
            case MONTH_DAY_WEEK_HOUR_MIN:
                textSize = textSize * 3;
                wv_month.setVisibility(View.GONE);
                wv_year.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                monthDayWeek.setVisibility(View.VISIBLE);
                wv_hours.setVisibility(View.VISIBLE);
                wv_mins.setVisibility(View.VISIBLE);
                wv_hourMin.setVisibility(View.GONE);

                break;

        }
        wv_day.setTextSize(textSize);
        wv_month.setTextSize(textSize);
        wv_year.setTextSize(textSize);
        wv_hours.setTextSize(textSize);
        wv_mins.setTextSize(textSize);
        wv_hourMin.setTextSize(textSize);
        monthDayWeek.setTextSize(textSize);

    }

    private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(int index) {

            if (listener != null) {
                listener.selectTime();
            }
        }
    };

    private String setSelfDriveTimeFrame(long startTime) {


        String[] weekOfDays = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTimeInMillis(startTime);
        int startw = startCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (startw < 0) {
            startw = 0;
        }
        String startweekStr = weekOfDays[startw];

        return startweekStr;
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wv_year.setCyclic(cyclic);
        wv_month.setCyclic(cyclic);
        wv_day.setCyclic(cyclic);
        wv_hours.setCyclic(cyclic);
        wv_mins.setCyclic(cyclic);
        wv_hourMin.setCyclic(cyclic);

        monthDayWeek.setCyclic(cyclic);
    }

    public String getTime() {

        StringBuffer sb = new StringBuffer();

        if (type == TimePickerView.Type.MONTH_DAY_WEEK_HOUR_MIN) {

            long a = dataTime.get(monthDayWeek.getCurrentItem());

            long b = wv_hours.getCurrentItem();

            int c = wv_mins.getCurrentItem();

            String key = minuArrayList.get(c);

            if("00".equals(key)){

                c = 0;
            }else {

                c = Integer.parseInt(key);
            }

            long totalTime = a+b*60*60*1000+c*60*1000;

            String totalTimeStr = AppUtils.toData(totalTime,1);

            sb.append(totalTimeStr);

        } else {


            if (wv_year.getVisibility() == View.GONE) {

                sb.append(initYear);

            } else {
                sb.append((wv_year.getCurrentItem() + startYear));
            }

            sb.append("-").append((wv_month.getCurrentItem() + 1)).append("-").append((wv_day.getCurrentItem() + 1))
                    .append(" ");

            if (type == TimePickerView.Type.MONTH_DAY_HOURMIN) {

                sb.append(wv_hourMin.getSelectedStr());

            } else {

                sb.append(wv_hours.getCurrentItem()).append(":").append(wv_mins.getCurrentItem());

            }
        }

        return sb.toString();
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public interface OnTimeWheelTimeSelectListener {

        void selectTime();
    }


}
