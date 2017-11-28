package com.yzb.card.king.ui.credit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.bean.PayWay;
import com.yzb.card.king.ui.credit.holder.PayWayHolder;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PayWayActivity extends BaseActivity
{
    public static final int TYPE_DEBIT = 1;
    public static final int TYPE_CREDIT = 2;

    private static final int REQ_ADD_DEBIT_CARD = 1;
    private ListView listView;
    private AbsBaseListAdapter adapter;
    private List<PayWay> dataList = new ArrayList();
    private View headerLeft;
    private ImageView headerLeftImage;
    private TextView headerTitle;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        getIntentData();
        initView();
        initData();
    }

    private void initData()
    {
        setHeader();
        setAdapter();
        loadData();
    }

    private void getIntentData()
    {
        type = getIntent().getIntExtra("type", 1);
    }

    private void setHeader()
    {
        headerLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        headerLeftImage.setVisibility(View.VISIBLE);
        headerLeftImage.setImageResource(R.mipmap.icon_back_white);
        headerTitle.setText("付款方式");
    }

    private void loadData()
    {
        SimpleRequest<List<PayWay>> request = new SimpleRequest<List<PayWay>>("QueryPayMethodDefault")
        {
            @Override
            protected List<PayWay> parseData(String data)
            {
                return JSON.parseArray(data, PayWay.class);
            }
        };
        Map<String, Object> param = new HashMap<>();
        param.put("mobile", UserManager.getInstance().getUserBean().getAccount());
        String balance, debit, credit;
        if (type == TYPE_CREDIT)
        {
            balance = "0";
            debit = "0";
            credit = "1";
        } else
        {
            balance = "1";
            debit = "1";
            credit = "0";
        }
        param.put("showBalancePay", balance);
        param.put("showDebitCard", debit);
        param.put("showCreditCard", credit);
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<List<PayWay>>()
        {
            @Override
            public void onSuccess(List<PayWay> data)
            {
                dataList.clear();
                dataList.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
                dataList.clear();
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void setAdapter()
    {
        adapter = new AbsBaseListAdapter<PayWay>(dataList)
        {
            @Override
            protected Holder getHolder(int position)
            {
                return new PayWayHolder();
            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent();
                intent.putExtra("payWay", dataList.get(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initView()
    {
        setContentView(R.layout.activity_pay_way);
        listView = (ListView) findViewById(R.id.listView);
        listView.addFooterView(getFooterView());

        headerLeft = findViewById(R.id.headerLeft);
        headerLeftImage = (ImageView) findViewById(R.id.headerLeftImage);
        headerTitle = (TextView) findViewById(R.id.headerTitle);

    }

    private View getFooterView()
    {
        View view = UiUtils.inflate(R.layout.footer_pay_way);
        TextView tvText = (TextView) view.findViewById(R.id.tvText);
        if (type == TYPE_CREDIT)
        {
            tvText.setText("添加信用卡");
        }
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(new Intent(PayWayActivity.this, AddBankCardActivity.class)
                        , REQ_ADD_DEBIT_CARD);
            }
        });
        return view;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            if (requestCode == REQ_ADD_DEBIT_CARD)
            {
                refresh();
            }
        }
    }

    private void refresh()
    {
        loadData();
    }
}
