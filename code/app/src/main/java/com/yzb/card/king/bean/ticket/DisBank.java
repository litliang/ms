package com.yzb.card.king.bean.ticket;

import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.TicketFilterView;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：银行优惠筛选
 * 作者：殷曙光
 * 日期：2016/9/28 18:15
 */
public class DisBank extends AbsFilter
{
    public DisBank()
    {
        super("银行优惠", R.drawable.selector_bank_disc, UiUtils.getColorStateList(R.drawable.selector_filter_text));
    }

    @Override
    public void clickAction(View view)
    {
        String code = TicketFilterView.filterData.getDisBankCode();
        TicketFilterView.filterData.setBankDisCode("0".equals(code)?"1":"0");
    }
}
