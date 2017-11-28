package com.yzb.card.king.ui.my.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.my.bean.Payee;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/27 21:34
 */
public class HistoryPayeeHolder extends Holder<Payee>
{
    private ImageView ivLogo;
    private TextView tvCardInfo;
    private TextView tvName;
    private View view;

    @Override
    public View initView()
    {
        view = UiUtils.inflate(R.layout.holder_history_payee);
        ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvCardInfo = (TextView) view.findViewById(R.id.tvCardInfo);
        return view;
    }

    public void setListener(View.OnClickListener listener)
    {
        view.setOnClickListener(listener);
    }

    @Override
    public void refreshView(Payee data)
    {
        x.image().bind(ivLogo, data.getPhotoUrl());
        tvName.setText(data.getCreditName());
        tvCardInfo.setText(UiUtils.getString(R.string.cardNmu_cardName, hideCardNum(data.getTradeAccount()), data.getBankName()));
    }

    private String hideCardNum(String tradeAccount)
    {
        if (tradeAccount.length() > 9)
        {
            String start = tradeAccount.substring(0, 6);
            String end = tradeAccount.substring(tradeAccount.length() - 4, tradeAccount.length());
            return start + "***" + end;
        } else
        {
            return tradeAccount;
        }
    }
}
