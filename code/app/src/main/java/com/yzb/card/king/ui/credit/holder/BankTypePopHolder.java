package com.yzb.card.king.ui.credit.holder;

import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.credit.bean.CardType;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/22
 */
public class BankTypePopHolder extends Holder<CardType>
{
    private TextView tvName;
    private TextView tvDesc;

    @Override
    public View initView()
    {
        View view= UiUtils.inflate(R.layout.holder_bank_type);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvDesc = (TextView) view.findViewById(R.id.tvDesc);
        return view;
    }

    @Override
    public void refreshView(CardType data)
    {
        tvName.setText(data.getName());
        tvDesc.setText(data.getIntro());
//        tvDesc.setText("");
    }
}
