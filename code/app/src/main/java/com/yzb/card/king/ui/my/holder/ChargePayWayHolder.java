package com.yzb.card.king.ui.my.holder;

import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/2/14 16:18
 */
public class ChargePayWayHolder extends BankCardHolder
{
    @Override
    public void refreshView(PayMethod data)
    {
        x.image().bind(ivBankLogo, ServiceDispatcher.getImageUrl(data.getLogo()));
        tvTypeTail.setText(UiUtils.getString(R.string.card_bank_and_tail, data.getBankName()
                + data.getTypeName(), data.getSortNo()));
        tvLimitAmount.setText(null);
        if (data.isSelected())
        {
            tvCheck.setVisibility(View.VISIBLE);
        } else
        {
            tvCheck.setVisibility(View.GONE);
        }
    }
}
