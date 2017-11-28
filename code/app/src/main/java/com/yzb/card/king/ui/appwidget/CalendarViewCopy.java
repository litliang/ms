package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yzb.card.king.ui.other.bean.CalendarDay;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarViewCopy extends View implements View.OnTouchListener
{
    private Date selectedStartDate;
    private Date curDate; // 当前日历显示的月
    private Date today; // 今天的日期文字显示红色
    private Date yellowDate;//日期颜色为黄色的
    private Date downDate; // 手指按下状态时临时日期
    private Date positionedDate;//定位日期
    private int downIndex; // 按下的格子索引
    private Calendar calendar;
    private Surface surface;
    private int[] date = new int[42]; // 日历显示数字
    private Map<Integer, String> holidayMap = new HashMap<>();
    private Map<Integer, Integer> priceMap = new HashMap<>();
    private Map<Integer, String> workMap = new HashMap<>();
    private int curStartIndex, curEndIndex, todayIndex, yellowDateIndex, positionedIndex; // 当前显示的日历起始的索引
    private int nextYearTodayIndex;//明年今日下标
    private String positionText;//定位日期下显示的文字
    //private boolean completed = false; // 为false表示只选择了开始日期，true表示结束日期也选择了
    //给控件设置监听事件
    private OnItemClickListener onItemClickListener;
    List<CalendarDay> dayList;

    public void setDayList(List<CalendarDay> dayList)
    {
        this.dayList = dayList;
    }

    public String getPositionText()
    {
        return positionText == null ? "" : positionText;
    }

    public CalendarViewCopy(Context context, Date date)
    {
        super(context);
        this.curDate = date;
        init();
    }

    public CalendarViewCopy(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private void init()
    {
        selectedStartDate = today = new Date();
        calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        surface = new Surface();
        surface.density = getResources().getDisplayMetrics().density;
        setBackgroundColor(surface.bgColor);
        setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        surface.width = getResources().getDisplayMetrics().widthPixels;
        surface.height = (int) (getResources().getDisplayMetrics().heightPixels * 2 / 5);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(surface.width,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(surface.height,
                MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom)
    {
        if (changed)
        {
            surface.init();
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        // 年月
        String monthText = getYearAndMonth(curDate);
        float textWidth = surface.monthPaint.measureText(monthText);
        canvas.drawText(monthText, (surface.width - textWidth) / 2f,
                surface.monthHeight * 3 / 4f, surface.monthPaint);
        // 计算日期
        calculateDate();
        //计算今日下标
        todayIndex = getDateIndex(-1, today);

        //计算明年今日下标
        calendar.add(Calendar.YEAR, 1);
        nextYearTodayIndex = getDateIndex(43, calendar.getTime());

        //计算黄色日期的index
        yellowDateIndex = getDateIndex(-1, yellowDate);

        //计算定位的日期的index
        positionedIndex = getDateIndex(-1, positionedDate);

        super.onDraw(canvas);
    }

    private int getDateIndex(int defaultIndex, Date date)
    {
        int index = defaultIndex;
        if (date != null)
        {
            String curStr = getYearAndMonth(curDate);
            String aimStr = getYearAndMonth(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if (curStr.equals(aimStr))
            {
                index = curStartIndex + calendar.get(Calendar.DAY_OF_MONTH) - 1;
            }
        }
        return index;
    }




    private void calculateDate()
    {
        calendar.setTime(curDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int dayInWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int monthStart = dayInWeek;

        monthStart -= 1;  //以日为开头-1，以星期一为开头-2
        curStartIndex = monthStart;
        date[monthStart] = 1;
        // last month
        if (monthStart > 0)
        {
            calendar.set(Calendar.DAY_OF_MONTH, 0);
            int dayInmonth = calendar.get(Calendar.DAY_OF_MONTH);
            for (int i = monthStart - 1; i >= 0; i--)
            {
                date[i] = dayInmonth;
                dayInmonth--;
            }
            calendar.set(Calendar.DAY_OF_MONTH, date[0]);
        }
        // this month
        calendar.setTime(curDate);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        int monthDay = calendar.get(Calendar.DAY_OF_MONTH);
        for (int i = 1; i < monthDay; i++)
        {
            date[monthStart + i] = i + 1;
        }
        curEndIndex = monthStart + monthDay;
        // next month
        for (int i = monthStart + monthDay; i < 42; i++)
        {
            date[i] = i - (monthStart + monthDay) + 1;
        }
        if (curEndIndex < 42)
        {
            // 显示了下一月的
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        calendar.set(Calendar.DAY_OF_MONTH, date[41]);
    }

    /**
     * @param canvas
     * @param index
     * @param text
     */
    private void drawCellText(Canvas canvas, int index,
                              String text, String holiday, String price,
                              int dateColor, int holidayColor, int priceColor, int bgColor)
    {
        int x = getXByIndex(index);
        int y = getYByIndex(index);

        surface.datePaint.setColor(dateColor);
        surface.pricePaint.setColor(priceColor);
        surface.holidayPaint.setColor(holidayColor);
        float cellY = surface.monthHeight + (y - 1) * surface.cellHeight + surface.cellHeight * 0.7f;
        float cellX = (surface.cellWidth * (x - 1))
                + (surface.cellWidth - surface.datePaint.measureText(text)) / 2f;
        float holidayX = (surface.cellWidth * (x - 1))
                + (surface.cellWidth - surface.holidayPaint.measureText(holiday)) / 2f;
        float holidayY = cellY - surface.cellHeight * 0.4f;

        float priceX = (surface.cellWidth * (x - 1))
                + (surface.cellWidth - surface.pricePaint.measureText(price)) / 2f;
        float priceY = cellY + surface.cellHeight * 0.25f;
        if (!"".equals(holiday) || positionedIndex != -1)
        {
            surface.cellBgPaint.setColor(bgColor);
            float startX = surface.cellWidth * (x - 0.8f);
            float startY = cellY - surface.cellHeight * 0.7f;
            float endX = cellX + surface.cellWidth * 0.4f;
            float endY = cellY + surface.cellWidth * 0.07f;
            if (positionedIndex != -1)
            {
                startY = cellY - surface.cellHeight * 0.4f;
                endY = cellY + surface.cellHeight * 0.35f;
            }
            RectF rectF = new RectF(startX, startY, endX, endY);
            canvas.drawRoundRect(rectF, surface.cellWidth * 0.1f, surface.cellHeight * 0.1f, surface.cellBgPaint);
        }
        canvas.drawText(text, cellX, cellY, surface.datePaint);
        canvas.drawText(holiday, holidayX, holidayY, surface.holidayPaint);
        canvas.drawText(price, priceX, priceY, surface.pricePaint);
    }


    private boolean isLastMonth(int i)
    {
        if (i < curStartIndex)
        {
            return true;
        }
        return false;
    }

    private boolean isNextMonth(int i)
    {
        if (i >= curEndIndex)
        {
            return true;
        }
        return false;
    }

    private int getXByIndex(int i)
    {
        return i % 7 + 1; // 1 2 3 4 5 6 7
    }

    private int getYByIndex(int i)
    {
        return i / 7 + 1; // 1 2 3 4 5 6
    }

    // 获得当前应该显示的年月
    public String getYearAndMonth(Date date)
    {
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        return year + "年" + month + "月";
    }


    private void setSelectedDateByCoor(float x, float y)
    {
        // cell click down
        if (y > surface.monthHeight + surface.weekHeight)
        {
            int m = (int) (Math.floor(x / surface.cellWidth) + 1);
            int n = (int) (Math.floor((y - (surface.monthHeight + surface.weekHeight))
                            / Float.valueOf(surface.cellHeight)) + 1);
            downIndex = (n - 1) * 7 + m - 1;
            if (downIndex >= todayIndex && downIndex >= curStartIndex && downIndex < curEndIndex)
            {
                calendar.setTime(curDate);
                if (isLastMonth(downIndex))
                {
                    calendar.add(Calendar.MONTH, -1);
                } else if (isNextMonth(downIndex))
                {
                    calendar.add(Calendar.MONTH, 1);
                }
                calendar.set(Calendar.DAY_OF_MONTH, date[downIndex]);
                downDate = calendar.getTime();
            }
        }
        invalidate();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                setSelectedDateByCoor(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (downDate != null)
                {
                    selectedStartDate = downDate;
                    //响应监听事件
                    float price = 0;
                    if (priceMap != null && !priceMap.isEmpty())
                    {
                        if (null != priceMap.get(date[downIndex]))
                            price = priceMap.get(date[downIndex]);
                    }
                    selectedStartDate.setHours(0);
                    selectedStartDate.setMinutes(0);
                    selectedStartDate.setSeconds(0);
                    onItemClickListener.OnItemClick(selectedStartDate, price);
                    downDate = null;
                    invalidate();
                }
                break;
        }
        return true;
    }

    //给控件设置监听事件
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public void setWorkMap(Map<Integer, String> workMap)
    {
        this.workMap = workMap;
    }

    //监听接口
    public interface OnItemClickListener
    {
        void OnItemClick(Date date, float price);
    }

    /**
     * 1. 布局尺寸 2. 文字颜色，大小 3. 当前日期的颜色，选择的日期颜色
     */
    private class Surface
    {
        public float density;
        public int width; // 整个控件的宽度
        public int height; // 整个控件的高度
        public float monthHeight; // 显示月的高度
        public float weekHeight; // 显示星期的高度
        public float cellWidth; // 日期方框宽度
        public float cellHeight; // 日期方框高度
        public int bgColor = Color.parseColor("#FFFFFF");
        private int textColor = Color.BLACK;
        private int btnColor = Color.parseColor("#666666");
        private int priceColor = Color.parseColor("#787878");
        private int borderColor = Color.parseColor("#C4C4C4");
        public int todayNumberColor = Color.parseColor("#FFAA00");
        public int cellDownColor = Color.parseColor("#D84043");
        public int cellSelectedColor = Color.parseColor("#99CCFF");
        public int blue = Color.parseColor("#4E86DC");
        public Paint monthPaint;
        public Paint datePaint;
        public Paint cellBgPaint;
        public Paint holidayPaint;
        public Paint pricePaint;

        public void init()
        {
            float temp = height / 7f;
            monthHeight = (float) ((temp + temp * 0.3f) * 0.6);
            cellHeight = (height - monthHeight - weekHeight) / 6f;
            cellWidth = width / 7f;
            monthPaint = new Paint();
            monthPaint.setColor(textColor);
            monthPaint.setAntiAlias(true);
            float textSize = cellHeight * 0.4f;
            monthPaint.setTextSize(textSize);
            datePaint = new Paint();
            datePaint.setColor(textColor);
            datePaint.setAntiAlias(true);
            float cellTextSize = cellHeight * 0.4f;
            datePaint.setTextSize(cellTextSize);
            cellBgPaint = new Paint();
            cellBgPaint.setAntiAlias(true);
            cellBgPaint.setStyle(Paint.Style.FILL);
            cellBgPaint.setColor(cellSelectedColor);

            holidayPaint = new Paint();
            holidayPaint.setColor(btnColor);
            cellBgPaint.setAntiAlias(true);
            float holidayTextSize = cellHeight * 0.2f;
            holidayPaint.setTextSize(holidayTextSize);

            pricePaint = new Paint();
            pricePaint.setColor(priceColor);
            pricePaint.setAntiAlias(true);
            float priceTextSize = cellHeight * 0.2f;
            pricePaint.setTextSize(priceTextSize);
        }
    }
}