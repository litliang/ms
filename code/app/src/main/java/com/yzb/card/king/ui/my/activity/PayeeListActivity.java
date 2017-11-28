package com.yzb.card.king.ui.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.my.bean.Payee;
import com.yzb.card.king.ui.my.holder.HistoryPayeeHolder;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.UiUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：历史收款人
 * 作者：殷曙光
 * 日期：2016/12/27 21:07
 *
 */
@ContentView(R.layout.activity_history_people)
public class PayeeListActivity extends BaseActivity
{
    @ViewInject(R.id.listView)
    private ListView listView;
    private AbsBaseListAdapter adapter;
    private List<Payee> dataList =  new ArrayList<>();
    private int pageStart = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData()
    {
        setTitleNmae("选择收款人");
        setAdapter();
        loadData();
    }

    private void loadData()
    {
        SimpleRequest<List<Payee>> request = new SimpleRequest<List<Payee>>("QueryTradeObject")
        {
            @Override
            protected List<Payee> parseData(String data)
            {
                return JSON.parseArray(data, Payee.class);
            }
        };
        final Map<String, Object> params = new HashMap<>();
        params.put("type",getIntent().getStringExtra("type"));
        params.put("pageStart", pageStart);
        params.put("pageSize", AppConstant.MAX_PAGE_NUM);
        request.setParam(params);
        request.sendRequestNew(new BaseCallBack<List<Payee>>()
        {
            @Override
            public void onSuccess(List<Payee> data)
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
        adapter = new AbsBaseListAdapter<Payee>(dataList)
        {
            @Override
            protected Holder getHolder(final int position)
            {
                HistoryPayeeHolder holder = new HistoryPayeeHolder();
                holder.setListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent();
                        intent.putExtra("payee",dataList.get(position));
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
                return holder;
            }
        };
        listView.setAdapter(adapter);
    }
}
