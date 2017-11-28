package com.yzb.card.king.ui.appwidget.picks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jamgle.pickerviewlib.view.BasePickerView;
import com.jamgle.pickerviewlib.view.TimePickerView;
import com.jamgle.pickerviewlib.view.WheelTime;
import com.yzb.card.king.R;
import com.yzb.card.king.util.Utils;
import com.yzb.card.king.util.ToastUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间设置器
 */
public class SelfDrivePickTime extends BasePickerView implements View.OnClickListener, WheelTime
        .OnTimeWheelTimeSelectListener {

    private Context context;
    //控件
    private WheelTime wheelTime;

    private TextView tvSubmit, tvCancel;//確定、取消

    private LinearLayout llGetBusTime, llBackBusTime;

    private TextView tvGetBusTime, tvBackBusTime;

    private TextView tvTitle;//标题

    private LinearLayout llTimeLayout;//显示时间的layout

    //常量字段
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    /**
     * 设置开始日期
     */
    private final int SET_START_DATE = 1;
    /**
     * 设置结束日期
     */
    private final int SET_END_DATE = 2;

    private int currentDateFlag = 1;

    private long startCurrentTime;

    private long endCurrentTime;

    //时间滚动接口
    private OnTimeSelectListener timeSelectListener;


    public SelfDrivePickTime(Context context, long startCurrentTime, long endCurrentTime, TimePickerView.Type type) {
        super(context);
        this.context = context;

        LayoutInflater.from(context).inflate(R.layout.view_selfdrive_picktime_ios, contentContainer);

        tvCancel = (TextView) findViewById(R.id.tvCancel);

        tvCancel.setTag(TAG_CANCEL);

        tvSubmit = (TextView) findViewById(R.id.tvSubmit);

        tvSubmit.setTag(TAG_SUBMIT);

        tvCancel.setOnClickListener(this);

        tvSubmit.setOnClickListener(this);


        // ----时间转轮
        final View timepickerview = findViewById(R.id.timepicker);

        wheelTime = new WheelTime(timepickerview, type);

        wheelTime.setOnTimeWheelTimeSelectListener(this);


        llGetBusTime = (LinearLayout) findViewById(R.id.llGetBusTime);

        llBackBusTime = (LinearLayout) findViewById(R.id.llBackBusTime);

        llBackBusTime.setOnClickListener(this);

        llGetBusTime.setOnClickListener(this);

        tvGetBusTime = (TextView) findViewById(R.id.tvGetBusTime);

        tvBackBusTime = (TextView) findViewById(R.id.tvBackBusTime);
        //重置时段
        resetFramTime(startCurrentTime, endCurrentTime);

        tvTitle = (TextView) findViewById(R.id.tvTitle);

        llTimeLayout = (LinearLayout) findViewById(R.id.llTimeLayout);
    }

    /**
     * 设置单个时间选择器方法 (接口SelfDrivePickTime.OnTimeSelectListener中方法onTimeSelect回到的时间值为startTime)
     *
     * @param titleId
     */
    public void setSinglePickTime(int titleId) {

        tvTitle.setText(titleId);

        llTimeLayout.setVisibility(View.GONE);

    }

    /**
     * 重新设置时段
     *
     * @param startCurrentTime
     * @param endCurrentTime
     */
    public void resetFramTime(long startCurrentTime, long endCurrentTime) {

        this.startCurrentTime = startCurrentTime;

        this.endCurrentTime = endCurrentTime;
        //默认值,当前时间，加一天为结束日期
        String startTime = Utils.toData(startCurrentTime, 6);

        tvGetBusTime.setText(startTime);

        String endTime = Utils.toData(endCurrentTime, 6);

        tvBackBusTime.setText(endTime);

        setTime(this.startCurrentTime);

        currentDateFlag = SET_START_DATE;

        llGetBusTime.setBackgroundColor(context.getResources().getColor(R.color.selfdrive_gary_dcdcdc));

        llBackBusTime.setBackgroundColor(context.getResources().getColor(R.color.white));

    }

    /**
     * 设置选中时间
     *
     * @param date
     */
    public void setTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date == null) calendar.setTimeInMillis(System.currentTimeMillis());
        else calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelTime.setPicker(year, month, day, hours, minute);


    }

    /**
     * 设置时间
     *
     * @param time
     */
    public void setTime(long time) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelTime.setPicker(year, month, day, hours, minute);

    }

    /**
     * 设置循环滚动
     *
     * @param flag
     */
    public void setCyclic(boolean flag) {

        wheelTime.setCyclic(flag);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tvCancel:
                if (timeSelectListener != null) {
                    timeSelectListener.onCancel();
                }
                dismiss();
                break;
            case R.id.tvSubmit:

                if (timeSelectListener != null) {
                    //
                    if (currentDateFlag == SET_START_DATE) {

                        startCurrentTime = getWeekViewTime();

                    } else if (currentDateFlag == SET_END_DATE) {

                        endCurrentTime = getWeekViewTime();

                    }

                    if (llTimeLayout.getVisibility() == View.VISIBLE) {

                        if (checkTime(endCurrentTime, startCurrentTime)) {//还车时间必须大于取车时间

                            timeSelectListener.onTimeSelect(startCurrentTime, endCurrentTime);

                            dismiss();

                        }

                    } else {
                        timeSelectListener.onTimeSelect(startCurrentTime, endCurrentTime);

                        dismiss();
                    }

                } else {
                    dismiss();
                }


                break;
            case R.id.llGetBusTime:

                currentDateFlag = SET_START_DATE;

                llGetBusTime.setBackgroundColor(context.getResources().getColor(R.color.selfdrive_gary_dcdcdc));

                llBackBusTime.setBackgroundColor(context.getResources().getColor(R.color.white));

                startCurrentTime = getWeekViewTime();

                setTime(startCurrentTime);

                break;
            case R.id.llBackBusTime:

                currentDateFlag = SET_END_DATE;


                endCurrentTime = getWeekViewTime();
                //设置时间
                setTime(endCurrentTime);

                llBackBusTime.setBackgroundColor(context.getResources().getColor(R.color.selfdrive_gary_dcdcdc));

                llGetBusTime.setBackgroundColor(context.getResources().getColor(R.color.white));
                break;
            default:
                break;
        }

    }

    /**
     * 检查两个时间戳
     *
     * @param endCurrentTime
     * @param startCurrentTime
     * @return
     */
    private boolean checkTime(long endCurrentTime, long startCurrentTime) {

        boolean flag = true;

        int str = 0;

        if (startCurrentTime > endCurrentTime) {

            flag = false;

            str = R.string.toast_input_time_error;
        } else {

            long currentl = System.currentTimeMillis();

            if (endCurrentTime - currentl < 0 || startCurrentTime-currentl < 0) {

                flag = false;

                str = R.string.toast_input_time_error;
            }

        }


        if (!flag) {

            ToastUtil.i(context, context.getString(str));
        }

        return flag;
    }

    /**
     * 获取时间滚轮上的日期时间戳
     *
     * @return
     */
    private long getWeekViewTime() {

        Date date = null;
        try {
            date = WheelTime.dateFormat.parse(wheelTime.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date.getTime();
    }


    public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
        this.timeSelectListener = timeSelectListener;
    }

    @Override
    public void selectTime() {

        if (currentDateFlag == SET_START_DATE) {

            startCurrentTime = getWeekViewTime();

            tvGetBusTime.setText(Utils.toData(startCurrentTime, 6));

        } else if (currentDateFlag == SET_END_DATE) {

            endCurrentTime = getWeekViewTime();

            tvBackBusTime.setText(Utils.toData(endCurrentTime, 6));

        }

    }

    public interface OnTimeSelectListener {

        void onTimeSelect(long startTime, long endTime);

        public void onCancel();
    }

    /**
     * 显示隐藏时间显示控件
     *
     * @param visibility
     */
    public void setLlTimeLayoutVisibility(int visibility) {
        llTimeLayout.setVisibility(visibility);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        tvTitle.setText(title);
    }
}
