package com.yzb.card.king.ui.other.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.other.bean.CalendarDay;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/23 14:51
 */
public class CalendarDayView extends RelativeLayout
{

    private View view;
    private TextView tvRight;
    private TextView tvBottom;
    private TextView tvDay;
    private OnItemClickListener<CalendarDay> listener;
    private CalendarDay data;

    public CalendarDayView(Context context)
    {
        super(context);
        initView();
    }

    public CalendarDayView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView();
    }

    public CalendarDayView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView()
    {
        view = UiUtils.inflate(R.layout.holder_calendar_day);
        addView(view);

        tvDay = (TextView) view.findViewById(R.id.tvDay);
        tvBottom = (TextView) view.findViewById(R.id.tvBottom);
        tvRight = (TextView) view.findViewById(R.id.tvRight);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (isEnabled())
        {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    onSelected(true);
                    break;

                case MotionEvent.ACTION_MOVE:
                    onSelected(false);
                    break;
                case MotionEvent.ACTION_OUTSIDE:
                    onSelected(false);
                    break;
                case MotionEvent.ACTION_UP:
                    onSelected(false);
                    if (listener != null && data != null && data.isInOneYear())
                    {
                        listener.onItemClick(data);
                    }
                    break;
            }
            return true;
        } else
        {
            return false;
        }
    }

    private void onSelected(boolean selected)
    {
        tvDay.setEnabled(!selected);
        tvBottom.setEnabled(!selected);
    }

    public void setData(CalendarDay data)
    {
        this.data = data;
        if (data != null)
        {
            setDayColor(data);
            tvDay.setText(getDayText(data));
            tvBottom.setText(data.getBottomText());
            tvRight.setText(data.getRightText());
            if ("休".equals(data.getRightText()))
            {
                tvRight.setTextColor(UiUtils.getColor(R.color.ticket_red));
                tvRight.setBackgroundResource(R.drawable.bg_red_edge_1_corner_2);
            } else if ("班".equals(data.getRightText()))
            {
                tvRight.setTextColor(UiUtils.getColor(R.color.gray6));
                tvRight.setBackgroundResource(R.drawable.bg_calendar_gray_edg);
            }
            if (data.isSelected())
            {
                tvDay.setBackgroundResource(R.drawable.calendar_bg_red);
                tvDay.setTextColor(Color.WHITE);
                tvBottom.setTextColor(Color.WHITE);
            }
        }
        setEnabled(data != null);
    }

    private void setDayColor(CalendarDay data)
    {
        if (data.isInOneYear())
        {
            tvDay.setTextColor(UiUtils.getColorStateList(R.color.selector_color_black_white));
            tvDay.setBackgroundResource(R.drawable.selector_bg_red_transparent);
            tvDay.setEnabled(true);

        } else
        {
            tvDay.setEnabled(false);
            tvDay.setTextColor(UiUtils.getColor(R.color.gray6));
            tvDay.setBackgroundResource(R.drawable.bg_transparent);
        }
    }

    @NonNull
    private String getDayText(CalendarDay data)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data.getDay());
        if (DateUtil.isTheSameDay(data.getDay(), new Date()))
        {
            tvDay.setTextColor(UiUtils.getColorStateList(R.color.selector_gray_white));
            tvDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            return "今天";
        } else if (TextUtils.isEmpty(data.getHoliday()))
        {
            tvDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            return "" + calendar.get(Calendar.DAY_OF_MONTH);
        } else
        {
            tvDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            return data.getHoliday();
        }
    }

    @Override
    public void setBackground(Drawable background)
    {
        view.setBackground(background);
    }

    public void setListener(OnItemClickListener<CalendarDay> listener)
    {
        this.listener = listener;
    }
}
