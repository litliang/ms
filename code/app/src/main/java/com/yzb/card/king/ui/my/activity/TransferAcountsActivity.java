package com.yzb.card.king.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.appwidget.MyTextWatcher;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.my.bean.Payee;
import com.yzb.card.king.ui.my.holder.PayeeHolder;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：转账
 * 作者：殷曙光
 * 日期：2016/12/26 21:43
 */
@ContentView(R.layout.activity_transfer_acounts)
public class TransferAcountsActivity extends BaseActivity
{
    private static final int REQ_TRANSFER_PLATFORM = 2;
    private static final int REQ_TRANSFER_BANK = 3;
    private static final int REQ_TRANSFER = 4;
    @ViewInject(R.id.etSearch)
    private EditText etSearch;

    @ViewInject(R.id.listView)
    private ListView listView;

    @ViewInject(R.id.llTransfer)
    private View llTransfer;

    @ViewInject(R.id.ivClear)
    private ImageView ivClear;

    @ViewInject(R.id.ivArrowGray)
    private ImageView ivArrowGray;

    @ViewInject(R.id.llOtherTransferStyle)
    private LinearLayout llOtherTransferStyle;

    @ViewInject(R.id.llMsg)
    private View llMsg;

    @ViewInject(R.id.llContent)
    private View llContent;

    private AbsBaseListAdapter adapter;
    private List<Payee> payeeList = new ArrayList<>();
    private int pageStart = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initListener();
        initData();
    }

    private void initListener()
    {
        etSearch.addTextChangedListener(new MyTextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (TextUtils.isEmpty(s.toString()))
                {
                    ivClear.setVisibility(View.INVISIBLE);
                    llTransfer.setVisibility(View.VISIBLE);
                    loadPayee(null);
                } else
                {
                    llTransfer.setVisibility(View.GONE);
                    ivClear.setVisibility(View.VISIBLE);
                    loadPayee(s.toString());
                }
            }
        });
    }

    private void loadPayee(final String account)
    {


        SimpleRequest<List<Payee>> simpleRequest
                = new SimpleRequest<List<Payee>>(CardConstant.USER_QUERYPLATFORMACCOUNT)
        {
            @Override
            protected List<Payee> parseData(String data)
            {
                return JSON.parseArray(data, Payee.class);
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("searchName", account);
        param.put("customerType", "P");
        param.put("customerMod", "2");
        param.put("pageStart", 0);
        param.put("pageSize", 20);
        simpleRequest.setParam(param);
        simpleRequest.sendRequestNew(new BaseCallBack<List<Payee>>()
        {
            @Override
            public void onSuccess(List<Payee> data)
            {
                payeeList.clear();
                payeeList.addAll(data);
                adapter.notifyDataSetChanged();
                setResultView(true, account);
            }

            @Override
            public void onFail(Error error)
            {
                payeeList.clear();
                adapter.notifyDataSetChanged();
                setResultView(false, account);
            }
        });

//        SimpleRequest<List<Payee>> request = new SimpleRequest<List<Payee>>(CardConstant.card_querytradeobject)
//        {
//            @Override
//            protected List<Payee> parseData(String data)
//            {
//                return JSON.parseArray(data, Payee.class);
//            }
//        };
//        final Map<String, Object> params = new HashMap<>();
//        params.put("type", "0");
//        params.put("searchName", account);
//        params.put("pageStart", pageStart);
//        params.put("pageSize", AppConstant.MAX_PAGE_NUM);
//        request.setParam(params);
//        request.sendRequestNew(new BaseCallBack<List<Payee>>()
//        {
//            @Override
//            public void onSuccess(List<Payee> data)
//            {
//                payeeList.clear();
//                payeeList.addAll(data);
//                adapter.notifyDataSetChanged();
//                setResultView(true, account);
//            }
//
//            @Override
//            public void onFail(Error error)
//            {
//                payeeList.clear();
//                adapter.notifyDataSetChanged();
//                setResultView(false, account);
//            }
//        });
    }

    private void setResultView(boolean hasData, String searchText)
    {
        if (hasData)
        {
            llContent.setVisibility(View.VISIBLE);
            llMsg.setVisibility(View.GONE);
        } else
        {
            llContent.setVisibility(View.GONE);
            llMsg.setVisibility(View.VISIBLE);
        }

        if (searchText == null)
        {
            llContent.setVisibility(View.VISIBLE);
            llMsg.setVisibility(View.GONE);
        }
    }

    private void initData()
    {
        setTitleNmae("转账");
        ivClear.setVisibility(View.INVISIBLE);
        setAdapter();
        loadPayee(null);
    }

    private void setAdapter()
    {
        adapter = new AbsBaseListAdapter<Payee>(payeeList)
        {
            @Override
            protected Holder getHolder(int position)
            {
                return new PayeeHolder();
            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Payee payee = payeeList.get(position);

                if ("1".equals(payee.getTradeType()))
                {//平台账户
                    goNext(HiLifeTransferActivity.class, payee);
                } else if ("2".equals(payee.getTradeType()))
                {//储蓄卡账户
                    goNext(BankCardTransActivity.class, payee);
                }
            }
        });

        //需要添加点击事件；
        if (getIntent().hasExtra("sourceActivity"))
        {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Payee payee = payeeList.get(position);
                    Intent intent = new Intent();
                    intent.putExtra("payeeData", payee);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            });
        }
    }

    private void goNext(Class clazz, Payee payee)
    {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("payee", payee);
        startActivityForResult(intent, REQ_TRANSFER);
    }


    @Event(R.id.llToHiLife)
    private void toHiLife(View view)
    {
        Intent intent = new Intent(this, QueryAccountActivity.class);
        startActivityForResult(intent, REQ_TRANSFER_PLATFORM);
    }

    @Event(R.id.llToBankCard)
    private void toBankCard(View view)
    {
        Intent intent = new Intent(this, BankCardTransActivity.class);
        startActivityForResult(intent, REQ_TRANSFER_BANK);
    }

    @Event(R.id.llOtherAccountTranfer)
    private void llOtherAccountTranfer(View view){
        int vi = llOtherTransferStyle.getVisibility();

        if(vi == View.GONE){

            llOtherTransferStyle.setVisibility(View.VISIBLE);
            Utils.changeBackground(this, ivArrowGray);

        }else {

            llOtherTransferStyle.setVisibility(View.GONE);

            Utils.backBackground(this, ivArrowGray);
        }
    }

    @Event(R.id.ivClear)
    private void clear(View view)
    {
        etSearch.setText("");
    }

    @Event({R.id.llWeixinPay,R.id.llZhifubaoPay})
    private void llWeixinPay(View view){
        ToastUtil.i(this,"暂不支持");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case REQ_TRANSFER_BANK:
                case REQ_TRANSFER_PLATFORM:
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    }
}
