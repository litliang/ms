package com.yzb.card.king.ui.credit.activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.bean.Bank;
import com.yzb.card.king.ui.credit.bean.BankContactItem;
import com.yzb.card.king.ui.credit.bean.BankContacts;
import com.yzb.card.king.ui.credit.holder.BankContactHolder;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 联络银行详情
 */
public class ContactBankDetailActivity extends BaseActivity
{
    private Bank bank;
    private ListView listView;
    private List<BankContactItem> dataList = new ArrayList<>();
    private AbsBaseListAdapter adapter;

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
        setContentView(R.layout.activity_bank_contact);
        listView = (ListView) findViewById(R.id.listView);
    }

    private void initData()
    {
        Intent intent = getIntent();
        bank = (Bank) intent.getSerializableExtra("bank");

        // 设置标题
        setTitleNmae( bank.getBankName());

        // 返回
        switchContentLeft(AppConstant.RES_CONTACT_BANK);

        setAdapter();
        loadData();
    }
    
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

    private void setAdapter()
    {
        adapter = new AbsBaseListAdapter<BankContactItem>(dataList)
        {
            @Override
            protected Holder getHolder(int position)
            {
                return new BankContactHolder();
            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                BankContactItem contacts = dataList.get(position);
                call(contacts.getPhone());
            }
        });
    }

    private void loadData()
    {
        SimpleRequest<BankContacts> request
                = new SimpleRequest<BankContacts>(CardConstant.card_app_bank_detail)
        {
            @Override
            protected BankContacts parseData(String data)
            {
                return JSON.parseObject(data, BankContacts.class);
            }
        };

        Map<String, Object> param = new HashMap<>();

        param.put("bankId", bank.getBankId());

        request.setParam(param);

        request.sendRequestNew(new BaseCallBack<BankContacts>()
        {
            @Override
            public void onSuccess(BankContacts data)
            {
                dataList.clear();
                dataList.add(new BankContactItem("客服热线",data.getTelServiceLine(),data.getMobile()));
                dataList.add(new BankContactItem("人工服务",data.getTelServicePeople(),data.getMobile()));
                dataList.add(new BankContactItem("紧急挂失/人工",data.getTelServiceLost(),data.getMobile()));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Error error)
            {
                dataList.clear();
                adapter.notifyDataSetChanged();
                UiUtils.shortToast(error.getError());
            }
        });
    }



    private void call(String tel)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ContactBankDetailActivity.this.checkSelfPermission(Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED)
            {
                startCallIntent(tel);
            }
        } else
        {
            startCallIntent(tel);
        }
    }

    private void startCallIntent(String tel)
    {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + tel));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(callIntent, AppConstant.REQ_PHONE);
    }
}
