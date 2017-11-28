package com.yzb.card.king.ui.credit.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.bean.CreditCard;
import com.yzb.card.king.util.UiUtils;

public class RepaySucsActivity extends BaseActivity
{

    private TextView tvAmount;
    private TextView tvCreditIfo;
    private CreditCard creditCard;
    private float amouont;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView()
    {
        setContentView(R.layout.activity_repay_sucs);
        tvAmount = (TextView) findViewById(R.id.tvAmount);
        tvCreditIfo = (TextView) findViewById(R.id.tvCreditIfo);
    }

    private void initData()
    {
        creditCard = (CreditCard) getIntent().getSerializableExtra("creditCard");
        amouont = getIntent().getFloatExtra("amount", 0);

        tvAmount.setText(UiUtils.getString(R.string.card_repay_amount, amouont));
        tvCreditIfo.setText(UiUtils.getString(R.string.card_bank_tail_name, creditCard.getBankName()
                , creditCard.getSortNo(), creditCard.getUserName()));
    }

    public void finish(View view)
    {
        finish();
    }
}
