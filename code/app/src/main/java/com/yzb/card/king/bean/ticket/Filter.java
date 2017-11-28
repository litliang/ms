package com.yzb.card.king.bean.ticket;

import android.view.Gravity;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.popup.TicketFilterPopup;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/28 18:12
 */
public class Filter extends AbsFilter
{
    private TicketFilterPopup ticketFilterPop;

    public Filter()
    {
        super("筛选",R.drawable.selector_filter, UiUtils.getColorStateList(R.drawable.selector_filter_text));
    }

    @Override
    public void clickAction(View view)
    {
        if (ticketFilterPop == null)
        {
            ticketFilterPop = new TicketFilterPopup();
        }
        ticketFilterPop.showAtLocation(view, Gravity.TOP, 0, 0);
    }
}
