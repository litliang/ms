package com.yzb.card.king.ui.credit.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.bean.Bank;
import com.yzb.card.king.ui.credit.holder.AllBankHolder;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 联络银行
 */
public class ContactBankActivity extends BaseActivity
{

    private ListView lvMyBank;
    private ListView lvOtherBank;
    private AbsBaseListAdapter myBankAdapter;
    private List<Bank> myBankList = new ArrayList<>();
    private List<Bank> otherList = new ArrayList<>();
    private AbsBaseListAdapter otherBankAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView()
    {
        setContentView(R.layout.activity_contact_bank);
        lvMyBank = (ListView) findViewById(R.id.lvMyBank);
        lvOtherBank = (ListView) findViewById(R.id.lvOtherBank);

    }

    private void initData()
    {
        //setHeader(R.mipmap.icon_back_white, getString(R.string.tv_link_bank));
        setTitleNmae(getString(R.string.tv_link_bank));
        switchContentLeft(AppConstant.RES_HOME_PAGE);
        setAdapter();
        loadData();
    }

    /**
     * 标题左侧返回
     */
    public void switchContentLeft(final int resultCode)
    {

        LinearLayout headerLeft = (LinearLayout) findViewById(R.id.llTemp);

        // 返回首页
        headerLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                setResult(resultCode);
                finish();
            }
        });
    }

    private void loadData()
    {
        loadMyBank();
        loadOtherBank("");
    }

    private void loadMyBank()
    {
        SimpleRequest<List<Bank>> request = getListSimpleRequest();
        request.setServerName("QueryBindCreditBank");
        request.sendRequestNew(new BaseCallBack<List<Bank>>()
        {
            @Override
            public void onSuccess(List<Bank> data)
            {
                myBankList.clear();
                myBankList.addAll(data);
                myBankAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
                myBankList.clear();
                myBankAdapter.notifyDataSetChanged();
            }
        });
    }

    @NonNull
    private SimpleRequest<List<Bank>> getListSimpleRequest()
    {
        return new SimpleRequest<List<Bank>>()
        {
            @Override
            protected List<Bank> parseData(String data)
            {
                return JSON.parseArray(data, Bank.class);
            }
        };
    }

    private void loadOtherBank(String notInIds)
    {
        SimpleRequest<List<Bank>> request = getListSimpleRequest();
        request.setServerName("QueryOtherCreditBank");
        request.sendRequestNew(new BaseCallBack<List<Bank>>()
        {
            @Override
            public void onSuccess(List<Bank> data)
            {
                otherList.clear();
                otherList.addAll(data);
                otherBankAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
                otherList.clear();
                otherBankAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setAdapter()
    {
        myBankAdapter = getAdapter(myBankList);
        otherBankAdapter = getAdapter(otherList);
        lvMyBank.setAdapter(myBankAdapter);
        lvOtherBank.setAdapter(otherBankAdapter);

//        lvMyBank.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                UiUtils.shortToast("??");
//            }
//        });
//        lvOtherBank.setOnItemClickListener(new OnItemClick(otherList));
    }

    private AbsBaseListAdapter getAdapter(List<Bank> list)
    {

        return new AbsBaseListAdapter<Bank>(list)
        {
            @Override
            protected Holder getHolder(int position)
            {
                final AllBankHolder holder = new AllBankHolder();
                holder.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(ContactBankActivity.this, ContactBankDetailActivity.class);
                        intent.putExtra("bank",holder.getData());
                        UiUtils.startActivity(intent);
                    }
                });
                return holder;
            }
        };
    }

    private void ToDetail(List<Bank> list, int position)
    {
        if (list != null && list.size() > position)
        {
            Bank bank = list.get(position);
            Intent intent = new Intent(ContactBankActivity.this, ContactBankDetailActivity.class);
            intent.putExtra("bank", bank);
            startActivity(intent);
        }
    }

    class OnItemClick implements AdapterView.OnItemClickListener
    {
        private List<Bank> list;

        public OnItemClick(List<Bank> list)
        {
            super();
            this.list = list;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            ToDetail(list, position);
        }


    }
}
