package com.yzb.card.king.ui.my.holder;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.my.bean.Payee;
import com.yzb.card.king.ui.my.pop.AccountListPop;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/1/10 17:10
 */
public class PayeePopHolder extends Holder<Payee>
{
    private TextView tvName;
    private TextView tvMobile;
    private View view;
    AccountListPop pop;

    public PayeePopHolder(AccountListPop pop)
    {
        super();
        this.pop = pop;
    }

    @Override
    public View initView()
    {
        view = UiUtils.inflate(R.layout.holder_payee_pop);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvMobile = (TextView) view.findViewById(R.id.tvMobile);
        return view;
    }

    public void setListener(View.OnClickListener listener)
    {
        view.setOnClickListener(listener);
    }

    @Override
    public void refreshView(Payee data)
    {
        tvName.setText(data.getCreditName());

        SpannableStringBuilder style = new SpannableStringBuilder(data.getTradeAccount());
        style.setSpan(new ForegroundColorSpan(Color.rgb(204, 163, 82)), 0, pop.getSearchLength(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new ForegroundColorSpan(Color.BLACK), pop.getSearchLength(),
                data.getTradeAccount().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tvMobile.setText(style);

    }
}
