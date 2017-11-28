package com.yzb.card.king.ui.my.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.util.UiUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 描述：充值成功
 * 作者：殷曙光
 * 日期：2016/12/28 15:40
 */
@ContentView(R.layout.activity_charge_success)
public class ChargeSuccessActivity extends BaseActivity
{

    @ViewInject(R.id.tvAmount)
    private TextView tvAmount;

    @ViewInject(R.id.tvCardInfo)
    private TextView tvCardInfo;

    @ViewInject(R.id.tvReduceAmount)
    private TextView tvReduceAmount;


    private String tailNo;
    private String bankName;
    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData()
    {
        setDefaultHeader("充值成功");
        getIntentData();
        refreshView();
    }

    private void getIntentData()
    {
        amount = getIntent().getStringExtra("amount");
        bankName = getIntent().getStringExtra("bankName");
        tailNo = getIntent().getStringExtra("tailNo");
    }

    private void refreshView()
    {
        tvAmount.setText(UiUtils.getString(R.string.common_xx_yuan, amount));
        tvCardInfo.setText(UiUtils.getString(R.string.charge_cardType_tail, bankName, tailNo));
        tvReduceAmount.setText("-" + amount);
    }
}
