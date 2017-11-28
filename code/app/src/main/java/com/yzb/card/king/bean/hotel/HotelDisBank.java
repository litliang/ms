package com.yzb.card.king.bean.hotel;

import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.AbsFilter;
import com.yzb.card.king.ui.appwidget.HotelFilterView;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/10/28 18:54
 */
public class HotelDisBank extends AbsFilter
{
    public HotelDisBank()
    {
        super("银行优惠", R.drawable.selector_bank_disc, UiUtils.getColorStateList(R.drawable.selector_filter_text));
    }

    @Override
    public void clickAction(View view)
    {
        HotelFilterView.data.setDisBankCode("0".equals(HotelFilterView.data.getDisBankCode())?"1":"0");
    }
}
