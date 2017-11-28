package com.yzb.card.king.ui.other.holder;

import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.ui.other.bean.CalendarDay;
import com.yzb.card.king.ui.other.bean.CalendarMonth;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/8 15:45
 */
public class TitleGridHolder extends Holder<CalendarMonth>
{
    private TextView tvTitle;
    private GridView gridView;
    private AbsBaseListAdapter<CalendarDay> adapter;
    private OnItemClickListener<CalendarDay> listener;

    public void setListener(OnItemClickListener<CalendarDay> listener)
    {
        this.listener = listener;
    }

    @Override
    public View initView()
    {
        View view = UiUtils.inflate(R.layout.holder_title_grid);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        gridView = (GridView) view.findViewById(R.id.gridView);
        return view;
    }

    @Override
    public void refreshView(CalendarMonth data)
    {
        tvTitle.setText(data.getTitle());
        adapter = new AbsBaseListAdapter<CalendarDay>(data.getDayList())
        {
            @Override
            protected Holder getHolder(int position)
            {
                CalendarDayHolder holder = new CalendarDayHolder();
                holder.setListener(listener);
                return holder;
            }
        };
        gridView.setAdapter(adapter);
    }
}
