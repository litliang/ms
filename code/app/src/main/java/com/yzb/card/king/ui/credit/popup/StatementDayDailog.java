package com.yzb.card.king.ui.credit.popup;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dev.mylibrary.OnWheelChangedListener;
import com.example.dev.mylibrary.WheelView;
import com.example.dev.mylibrary.adapter.ArrayWheelAdapter;
import com.example.dev.mylibrary.adapter.WheelViewAdapter;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.PeopleWheelView;
import com.yzb.card.king.ui.base.BaseDialogPeopleChoseFragment;
import com.yzb.card.king.ui.credit.interfaces.OnSelectedListener;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.DensityUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/24
 */

public class StatementDayDailog extends BaseDialogPeopleChoseFragment
{
    private PeopleWheelView listView;
    private String[] dataList = new String[28];
    private TextView tvCancel;
    private TextView tvEnsure;
    private WheelViewAdapter adapter;

    private int selectedPosition;

    private void initData()
    {

        for (int i = 0; i < 28; i++)
        {
            dataList[i] = Integer.toString(i + 1);
        }
        adapter = new PeopleArrayAdapter(getActivity(), dataList);
        listView.setViewAdapter(adapter);
        listView.setVisibleItems(3);
        listView.addChangingListener(new OnWheelChangedListener()
        {
            @Override
            public void onChanged(WheelView wheelView, int oldValue, int currentValue)
            {
                selectedPosition = currentValue;
                LogUtil.e("wheelchange:" + oldValue + "  " + currentValue);
            }
        });
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.dialog_statement_day;
    }

    protected void initView(View view)
    {
        listView = (PeopleWheelView) view.findViewById(R.id.listView);
        tvEnsure = (TextView) view.findViewById(R.id.tvEnsure);
        tvCancel = (TextView) view.findViewById(R.id.tvCancel);

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
                    listener.onSelected("",dataList[selectedPosition]);
                }
                dismiss();
            }
        });
    }

    class PeopleArrayAdapter extends ArrayWheelAdapter<String>
    {

        public PeopleArrayAdapter(Context context, String[] items)
        {
            super(context, items);
        }


        @Override
        public View getItem(int index, View convertView, ViewGroup parent)
        {
            return super.getItem(index, convertView, parent);
        }

        @Override
        protected void configureTextView(TextView view)
        {
            view.setHeight(CommonUtil.dip2px(getContext(), 48));
            view.setGravity(Gravity.CENTER);
            view.setTextSize(19);

            view.setTextColor(UiUtils.getColor(R.color.gray6));
            view.setEllipsize(TextUtils.TruncateAt.END);
            view.setTypeface(Typeface.SANS_SERIF);
        }

    }

    private OnSelectedListener listener;

    public void setListener(OnSelectedListener listener)
    {
        this.listener = listener;
    }


}
