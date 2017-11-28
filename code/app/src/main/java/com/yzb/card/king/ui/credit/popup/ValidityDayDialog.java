package com.yzb.card.king.ui.credit.popup;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.dev.mylibrary.OnWheelChangedListener;
import com.example.dev.mylibrary.WheelView;
import com.example.dev.mylibrary.adapter.ArrayWheelAdapter;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.PeopleWheelView;
import com.yzb.card.king.ui.base.BaseDialogPeopleChoseFragment;
import com.yzb.card.king.ui.credit.interfaces.OnSelectedListener;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.DensityUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.Calendar;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/24
 */

public class ValidityDayDialog extends BaseDialogPeopleChoseFragment
{

    private OnSelectedListener listener;
    private PeopleWheelView lvYear;
    private TextView tvEnsure;
    private TextView tvCancel;
    private PeopleWheelView lvMonth;

    private String[] years = new String[99];
    private String[] months = new String[12];

    private int yearPosition = 0;
    private int monthPosition = 0;
    private PeopleArrayAdapter yearAdapter;
    private PeopleArrayAdapter monthAdapter;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.dialog_validity_day;
    }

    @Override
    protected void initView(View view)
    {
        lvYear = (PeopleWheelView) view.findViewById(R.id.lvYear);
        lvMonth = (PeopleWheelView) view.findViewById(R.id.lvMonth);
        tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        tvEnsure = (TextView) view.findViewById(R.id.tvEnsure);
        initListener();
        initData();
    }

    private void initListener()
    {
        tvCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });

        tvEnsure.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (listener != null)
                {
                    listener.onSelected(years[yearPosition], months[monthPosition]);
                }
                dismiss();
            }
        });
    }

    private void initData()
    {
        initYear();
        initMonth();
        initYearView();
        initMonthView();
    }

    private void initYear()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        for (int i = 0; i < 99; i++)
        {
            years[i] = Integer.toString(year + i);
        }
    }

    private void initMonth()
    {
        for (int i = 0; i < 12; i++)
        {
            months[i] = Integer.toString(i + 1);
        }
    }

    private void initYearView()
    {
        yearAdapter = new PeopleArrayAdapter(getActivity(), years)
        {
            @Override
            protected int getTextGravity()
            {
                return Gravity.RIGHT | Gravity.CENTER_VERTICAL;
            }
        };
        lvYear.setViewAdapter(yearAdapter);
        lvYear.setVisibleItems(3);
        lvYear.addChangingListener(new OnWheelChangedListener()
        {
            @Override
            public void onChanged(WheelView wheelView, int oldValue, int currentValue)
            {
                yearPosition = currentValue;
                LogUtil.e("wheelchange:" + oldValue + "  " + currentValue);
            }
        });
    }

    private void initMonthView()
    {
        monthAdapter = new PeopleArrayAdapter(getActivity(), months);
        lvMonth.setViewAdapter(monthAdapter);
        lvMonth.setVisibleItems(3);
        lvMonth.addChangingListener(new OnWheelChangedListener()
        {
            @Override
            public void onChanged(WheelView wheelView, int oldValue, int currentValue)
            {
                monthPosition = currentValue;
                LogUtil.e("wheelchange:" + oldValue + "  " + currentValue);
            }
        });
    }


    public void setListener(OnSelectedListener listener)
    {
        this.listener = listener;
    }

    class PeopleArrayAdapter extends ArrayWheelAdapter<String>
    {
        public PeopleArrayAdapter(Context context, String[] items)
        {
            super(context, items);
        }


        @Override
        protected void configureTextView(TextView view)
        {
            view.setHeight(CommonUtil.dip2px(getContext(), 48));
            view.setGravity(getTextGravity());
            view.setTextSize(19);

            view.setTextColor(UiUtils.getColor(R.color.gray6));
            view.setEllipsize(TextUtils.TruncateAt.END);
            view.setTypeface(Typeface.SANS_SERIF);
        }

        protected int getTextGravity()
        {
            return Gravity.LEFT | Gravity.CENTER_VERTICAL;
        }

    }

}
