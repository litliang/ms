package com.yzb.card.king.ui.my.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.credit.bean.CreditCard;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/1/19 19:30
 */
public class SelectCreditHolder extends Holder<CreditCard>
{

    private ImageView ivBankLogo;
    private TextView tvCheck;
    private TextView tvLimitAmount;
    private TextView tvTypeTail;
    private View view;

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
    public void refreshView(CreditCard data)
    {
        x.image().bind(ivBankLogo, ServiceDispatcher.getImageUrl(data.getLogo()));
        tvTypeTail.setText(data.getBankName());
        tvLimitAmount.setText(UiUtils.getString(R.string.credit_user_tail,data.getUserName(),data.getSortNo()));
        if (data.isSelected())
        {
            tvCheck.setVisibility(View.VISIBLE);
        } else
        {
            tvCheck.setVisibility(View.GONE);
        }
    }

    public void setListener(View.OnClickListener listener)
    {
        view.setOnClickListener(listener);
    }
}
