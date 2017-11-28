package com.yzb.card.king.ui.credit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankListActivity extends BaseActivity
{
    private ListView listView;
    private TextView headerTitle;
    private ImageView headerLeftImage;
    private View headerLeft;
    private AbsBaseListAdapter adpter;
    private List<Bank> bankList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    private void initListener()
    {
        headerLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    private void initView()
    {
        setContentView(R.layout.activity_bank_list);
        listView = (ListView) findViewById(R.id.listView);
        headerLeft = findViewById(R.id.headerLeft);
        headerLeftImage = (ImageView) findViewById(R.id.headerLeftImage);
        headerTitle = (TextView) findViewById(R.id.headerTitle);
    }

    private void initData()
    {
        setHeader();
        setListView();
        loadData();
    }

    private void loadData()
    {
        SimpleRequest<List<Bank>> request = new SimpleRequest<List<Bank>>("QueryCooperativeBank")
        {
            @Override
            protected List<Bank> parseData(String data)
            {
                return JSON.parseArray(data, Bank.class);
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("pageStart", 0);
        param.put("pageSize", AppConstant.MAX_PAGE_NUM);
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<List<Bank>>()
        {
            @Override
            public void onSuccess(List<Bank> data)
            {
                bankList.clear();
                bankList.addAll(data);
                adpter.notifyDataSetChanged();

            }

            @Override
            public void onFail(Error error)
            {
                bankList.clear();
                adpter.notifyDataSetChanged();
                UiUtils.shortToast(error.getError());
            }
        });
    }

    private void setListView()
    {
        adpter = new AbsBaseListAdapter<Bank>(bankList)
        {
            @Override
            protected Holder getHolder(final int position)
            {
                AllBankHolder holder = new AllBankHolder();
                holder.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(UiUtils.getContext(), CreditMoreFilterActivity.class);
                        intent.putExtra("bank",((AllBankHolder)v.getTag()).getData());
                        UiUtils.startActivity(intent);
                    }
                });
                return holder;
            }
        };
        listView.setAdapter(adpter);
    }

    private void setHeader()
    {
        headerLeftImage.setImageResource(R.mipmap.icon_back_white);
        headerLeftImage.setVisibility(View.VISIBLE);
        headerTitle.setText("全部银行");
    }
}
