package com.yzb.card.king.ui.my.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.ui.my.enu.AccountType;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/27 20:11
 */
public class AmountLimitHolder extends Holder<PayMethod>
{

    private View view;
    private View divider;
    private TextView tvName;
    private TextView tvValue;

    @Override
    public View initView()
    {
        view = UiUtils.inflate(R.layout.holder_limit_amount);
        divider = view.findViewById(R.id.divider);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvValue = (TextView) view.findViewById(R.id.tvValue);
        return view;
    }

    @Override
    public void refreshView(PayMethod data)
    {

        tvName.setText(getName(data));
        tvValue.setText(UiUtils.getString(R.string.transfer_limit_value, data.getLimitSingle(), data.getLimitDay()));
    }

    @NonNull
    private String getName(PayMethod data)
    {
        if (AccountType.CASH.getValue().equals(data.getAccountType()))
        {
            return AccountType.CASH.getName();
        } else
        {
            return UiUtils.getString(R.string.transfer_limit_name, data.getBankName(), data.getSortNo());
        }
    }

    public void setDividerVisibility(int visibility)
    {
        divider.setVisibility(visibility);
    }
}
