package com.yzb.card.king.ui.other.holder;

import android.view.View;
import android.widget.AbsListView;

import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.bean.CalendarDay;
import com.yzb.card.king.ui.other.widget.CalendarDayView;
import com.yzb.card.king.util.DensityUtil;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/8 15:55
 */
public class CalendarDayHolder extends Holder<CalendarDay>
{
    private CalendarDayView view;
    private OnItemClickListener<CalendarDay> listener;

    public void setListener(OnItemClickListener<CalendarDay> listener)
    {
        this.listener = listener;
    }

    @Override
    public View initView()
    {
        view = new CalendarDayView(UiUtils.getContext());
        setView();
        return view;
    }

    private void setView()
    {
        int  screenWith = GlobalApp.getInstance().getAppBaseDataBean().getScreenWith();
        int height = (screenWith - 6) / 7;
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(height, height);
        view.setLayoutParams(params);
    }

    @Override
    public void refreshView(CalendarDay data)
    {
        view.setData(data);
        view.setListener(listener);
    }

    @Override
    protected void OnDataNull()
    {
        view.setData(null);
    }


}
