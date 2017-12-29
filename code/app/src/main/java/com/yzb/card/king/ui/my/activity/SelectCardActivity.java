package com.yzb.card.king.ui.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.credit.activity.AddBankCardActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.ui.my.holder.BankCardHolder;
import com.yzb.card.king.ui.my.holder.ChargePayWayHolder;
import com.yzb.card.king.ui.my.presenter.SelectCardPresenter;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.wallet.logic.comm.PayMethodDefaultLogic;
import com.yzb.wallet.openInterface.WalletBackListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：选择银行卡
 * 作者：殷曙光
 * 日期：2016/12/28 15:44
 */
@ContentView(R.layout.activity_select_card)
public class SelectCardActivity extends BaseActivity implements DataCallBack
{
    private static final int REQ_ADD_NEW_CARD = 2;

    @ViewInject(R.id.listView)
    protected ListView listView;

    private TextView tvFooterText;

    protected AbsBaseListAdapter adapter;

    protected List<PayMethod> cardList = new ArrayList<>();

    private boolean platformFlag = false;//false：钱包；true:平台

    private int accountType = 0;// 1:给钱包充值；2：给红包充值；3：给礼品卡充值

    private SelectCardPresenter selectCardPresenter;


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
        View footer = UiUtils.inflate(R.layout.footer_select_bank_card);
        footer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addNewCard();
            }

        });
        tvFooterText = (TextView) footer.findViewById(R.id.tvFooterText);
        tvFooterText.setText(getFooterText());
        listView.addFooterView(footer);
    }


    protected void addNewCard()
    {
        if(platformFlag){
            startActivityForResult(new Intent(this, AddCardAllActivity.class), REQ_ADD_NEW_CARD);

        }else{
            startActivityForResult(new Intent(this, AddBankCardActivity.class), REQ_ADD_NEW_CARD);
        }

    }

    protected void initData()
    {
        setTitleNmae(getTitleStr());


        setAdapter();
        if (getIntent().hasExtra("platformFlag"))
        {

            platformFlag = getIntent().getBooleanExtra("platformFlag", false);
            selectCardPresenter = new SelectCardPresenter(this);
        }

        if(getIntent().hasExtra("accountType")){

            accountType = getIntent().getIntExtra("accountType",0);
        }

        loadData();
    }

    private void loadData()
    {
        if (platformFlag)
        {//平台服务器的付款方式
            selectCardPresenter.sendReqeust(false);
        } else
        {//钱包服务器的付款方式
            Map<String, String> params = new HashMap<String, String>();
            params.put("mobile", UserManager.getInstance().getUserBean().getAccount());
            params.put("merchantNo", WalletConstant.MERCHANT_NO);
            params.put("sign", WalletConstant.SIGN);

            PayMethodDefaultLogic payHandle = new PayMethodDefaultLogic(SelectCardActivity.this);
            // 显示/隐藏 现金账户
            payHandle.showBalancePay(false);
            // 显示/隐藏 红包账户
            payHandle.showEnvelopPay(false);
            // 显示/隐藏 礼品卡账户
            payHandle.showGiftPay(false);
            // 显示/隐藏 积分账户
            payHandle.showIntegralPay(false);
            // 显示/隐藏 借记卡
            payHandle.showDebitCard(true);
            // 显示/隐藏 信用卡
            payHandle.showCreditCard(showCredit());

            payHandle.payMethod(params);
            payHandle.setAty(this);
            payHandle.setCallBack(new WalletBackListener()
            {
                @Override
                public void setSuccess(String RESULT_CODE)
                {
                }

                @Override
                public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
                {
                    LogUtil.e("PayMethodDefaultLogic=返回结果=>code" + RESULT_CODE + "返回数据=>" + resultMap);
                    cardList.clear();
                    cardList.addAll(JSON.parseArray(resultMap.get("data"), PayMethod.class));
                    if(adapter!=null){
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void setError(String RESULT_CODE, String ERROR_MSG)
                {
                    LogUtil.e("PayMethodDefaultLogic=返回结果=>code" + RESULT_CODE + "错误提示：" + ERROR_MSG);
                    cardList.clear();
                    if(adapter!=null){
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    protected Boolean showCredit()
    {
        return false;
    }


    private void setAdapter()
    {
        adapter = new AbsBaseListAdapter<PayMethod>(cardList)
        {
            @Override
            protected Holder getHolder(final int position)
            {
                BankCardHolder holder =  getAdapterHolder();
                holder.setListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {

                        Intent intent = new Intent();
                        intent.putExtra("payMethod", cardList.get(position));
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                return holder;
            }
        };
        listView.setAdapter(adapter);
    }

    protected BankCardHolder getAdapterHolder()
    {
        return new ChargePayWayHolder();
    }

    @NonNull
    protected String getTitleStr()
    {
        return "选择银行卡";
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        loadData();
    }

    @NonNull
    protected String getFooterText()
    {
        return "添加新卡";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case REQ_ADD_NEW_CARD:
                    loadData();
                    break;
            }
        }
    }


    @Override
    public void requestSuccess(Object o, int type)
    {
        String str = o + "";
        cardList.clear();
        cardList.addAll(JSON.parseArray(str, PayMethod.class));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {

    }
}
