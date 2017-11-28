package com.yzb.card.king.bean.hotel;

import android.view.Gravity;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.AbsFilter;
import com.yzb.card.king.ui.appwidget.popup.HotelLocationPop;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/10/25 16:37
 */
public class Region extends AbsFilter
{

    private HotelLocationPop hotelLocationPop;
    public Region()
    {
        super("位置区域",R.drawable.selector_region, UiUtils.getColorStateList(R.drawable.selector_filter_text));
    }

    @Override
    public void clickAction(View view)
    {
        if (hotelLocationPop == null)
        {
            hotelLocationPop = new HotelLocationPop();
        }
        hotelLocationPop.showAtLocation(view, Gravity.TOP, 0, 0);
    }
}
