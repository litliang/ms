package com.yzb.card.king.ui.my.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/28 16:57
 */
public class BankCardHolder extends Holder<PayMethod>
{
    protected ImageView ivBankLogo;
    protected TextView tvCheck;
    protected TextView tvLimitAmount;
    protected TextView tvTypeTail;
    protected View view;

    @Override
    public View initView()
    {
        view = UiUtils.inflate(R.layout.holder_bank_card);
        ivBankLogo = (ImageView) view.findViewById(R.id.ivBankLogo);
        tvTypeTail = (TextView) view.findViewById(R.id.tvTypeTail);
        tvLimitAmount = (TextView) view.findViewById(R.id.tvLimitAmount);
        tvCheck = (TextView) view.findViewById(R.id.tvCheck);
        tvCheck.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void refreshView(PayMethod data)
    {
        x.image().bind(ivBankLogo, ServiceDispatcher.getImageUrl(data.getLogo()));
        tvTypeTail.setText(UiUtils.getString(R.string.card_bank_and_tail, data.getBankName()
                + data.getTypeName(), data.getSortNo()));
        tvLimitAmount.setText(UiUtils.getString(R.string.card_amount_can_use, data.getLimitDay()));
        if (data.isSelected())
        {
            tvCheck.setVisibility(View.VISIBLE);
        } else
        {
            tvCheck.setVisibility(View.GONE);
        }
    }

    public void setLimitVisibility(int visibility)
    {
        tvLimitAmount.setVisibility(visibility);
    }

    public void setListener(View.OnClickListener listener)
    {
        view.setOnClickListener(listener);
    }
}
