package com.yzb.card.king.ui.credit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.popup.CreditPayMentPop;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.bean.CreditCard;

import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * 自动还款设置
 */
public class AutomaticPaymentActivity extends BaseActivity implements View.OnClickListener {

    private TextView txNoSure, txSure;

    private LinearLayout panel_back;

    private TextView txt_xieyi;

    private CreditCard data;

    private ImageView bankLogo;

    private TextView bankName, peopleName, latNum;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatic_payment);
        reciveData();
        initView();
        setData();
    }

    /**
     * 设置信息
     */
    private void setData()
    {
        if (data != null)
        {
            if (!TextUtils.isEmpty(data.getLogo()))
            {
                x.image().bind(bankLogo, ServiceDispatcher
                        .getImageUrl(data.getLogo()));
            }
            bankName.setText(data.getBankName());
            peopleName.setText(data.getUserName());
            latNum.setText("尾号" + data.getSortNo());
        }
    }

    /**
     * 接收信用卡信息
     */
    private void reciveData()
    {
        Intent i = getIntent();
        if (i != null)
        {
            data = (CreditCard) i.getSerializableExtra("data");
        }
    }

    private void initView()
    {
        txNoSure = (TextView) findViewById(R.id.tvNotSure);
        txSure = (TextView) findViewById(R.id.tvSure);
        panel_back = (LinearLayout) findViewById(R.id.panel_back);
        txt_xieyi = (TextView) findViewById(R.id.txt_xieyi);
        bankLogo = (ImageView) findViewById(R.id.img_logo);
        bankName = (TextView) findViewById(R.id.bankName);
        peopleName = (TextView) findViewById(R.id.peopleName);
        latNum = (TextView) findViewById(R.id.lastNum);
        txt_xieyi.setOnClickListener(this);
        panel_back.setOnClickListener(this);
        txSure.setOnClickListener(this);
        txNoSure.setOnClickListener(this);
    }

    /**
     * 修改信用卡自动还款
     *
     * @param isAutomatic
     */
    private void updateCredit(final String isAutomatic)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("creditId", data.getId());
        params.put("autoStatus", isAutomatic);
        new SimpleRequest(CardConstant.CREDIT_UPDATE, params).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                Intent intent = new Intent(AutomaticPaymentActivity.this, PaymentChoseActivity.class);
                data.setAutoStatus(true);
                intent.putExtra("data", data);
                startActivity(intent);
                RepaymentActivity.setData(data);
                CardManageActivity.setData(data);
                finish();
            }

            @Override
            public void onFailed(Object o)
            {

            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {

            }
        });
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.panel_back:
                finish();
                break;
            case R.id.tvNotSure: //不同意
                finish();
                break;
            case R.id.tvSure: //同意
                updateCredit("1");
                break;
            case R.id.txt_xieyi:  //协议条款
                CreditPayMentPop pop = new CreditPayMentPop(this);
                pop.openPop();
                break;
        }
    }
}
