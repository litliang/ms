package com.yzb.card.king.bean.hotel;

import android.view.Gravity;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.AbsFilter;
import com.yzb.card.king.ui.appwidget.popup.HotelFilterPop;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/10/25 16:33
 */
public class HotelFilter extends AbsFilter
{

    private HotelFilterPop hotelFilterPop;

    public HotelFilter()
    {
        super("筛选",R.drawable.selector_filter, UiUtils.getColorStateList(R.drawable.selector_filter_text));
    }

    @Override
    public void clickAction(View view)
    {
        if (hotelFilterPop == null)
        {
            hotelFilterPop = new HotelFilterPop();
        }
        hotelFilterPop.showAtLocation(view, Gravity.TOP, 0, 0);
    }

}
