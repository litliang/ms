package com.yzb.card.king.ui.credit.holder;

import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.credit.interfaces.IOnlineCardPop;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/21
 */
public class BankListPopHolder extends Holder<IOnlineCardPop>
{
    private TextView tvName;

    @Override
    public View initView()
    {
        View view = UiUtils.inflate(R.layout.pop_bank_list_item);
        tvName = (TextView) view.findViewById(R.id.tvName);
        return view;
    }

    @Override
    public void refreshView(IOnlineCardPop data)
    {
        tvName.setText(data.name());
    }
}
