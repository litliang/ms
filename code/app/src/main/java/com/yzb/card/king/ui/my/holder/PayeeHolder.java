package com.yzb.card.king.ui.my.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.my.bean.Payee;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/27 21:25
 */
public class PayeeHolder extends Holder<Payee>
{
    private TextView tvName;
    private TextView tvAccount;
    private View ivBankLabel;
    private ImageView ivPhoto;

    @Override
    public View initView()
    {
        View view = UiUtils.inflate(R.layout.holder_payee);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvAccount = (TextView) view.findViewById(R.id.tvAccount);
        ivBankLabel = view.findViewById(R.id.ivBankLabel);
        ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);
        return view;
    }

    @Override
    public void refreshView(Payee data)
    {
        x.image().bind(ivPhoto, data.getPhotoUrl());
        tvName.setText(data.getCreditName());
        if ("2".equals(data.getTradeType()))//储蓄卡
        {
            tvAccount.setText(UiUtils.getString(R.string.card_bank_and_tail, data.getBankName(), getTail(data)));
            ivBankLabel.setVisibility(View.VISIBLE);
        } else//平台
        {
            tvAccount.setText(RegexUtil.hide4PhoneNum(data.getTradeAccount()));
            ivBankLabel.setVisibility(View.GONE);
        }
    }

    private String getTail(Payee data)
    {
        String account = data.getTradeAccount();
        if (!TextUtils.isEmpty(account) && account.length() > 4)
        {
            return account.substring(account.length() - 4);
        }
        return account;
    }
}
