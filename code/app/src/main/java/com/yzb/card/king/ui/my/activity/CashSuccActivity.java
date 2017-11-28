package com.yzb.card.king.ui.my.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.UiUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Calendar;
import java.util.Date;

/**
 * 描述：提现成功
 * 作者：殷曙光
 * 日期：2016/12/28 17:48
 */
@ContentView(R.layout.activity_cash_succ)
public class CashSuccActivity extends BaseActivity
{

    @ViewInject(R.id.ivRight)
    private TextView headRightText;

    @ViewInject(R.id.tvAmount)
    private TextView tvAmount;

    @ViewInject(R.id.tvCreditIfo)
    private TextView tvCreditIfo;

    @ViewInject(R.id.tvTime)
    private TextView tvTime;

    private String amount;
    private PayMethod payMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData()
    {
        setTitleNmae(getTitleName());
        headRightText.setText("完成");
        getIntentData();
        refreshView();
    }

    @NonNull
    protected String getTitleName()
    {
        return "提现结果";
    }

    private void refreshView()
    {
        tvAmount.setText(UiUtils.getString(R.string.card_repay_amount,amount));
        tvCreditIfo.setText(UiUtils.getString(R.string.card_bank_tail_name,payMethod.getBankName(),
                payMethod.getSortNo(),payMethod.getName()));
        tvTime.setText(UiUtils.getString(R.string.cash_success,getTime()));
    }

    private String getTime()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY,2);

        return DateUtil.date2String(calendar.getTime(),"HH:mm");
    }

    private void getIntentData()
    {
        amount = getIntent().getStringExtra("amount");
        payMethod = (PayMethod) getIntent().getSerializableExtra("payMethod");
    }

    @Event(R.id.llRight)
    private void finish(View view)
    {
        setResult(RESULT_OK);
        finish();
    }
}
