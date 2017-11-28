package com.yzb.card.king.ui.my.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.my.bean.ServiceTime;
import com.yzb.card.king.ui.my.holder.ServiceTimeHolder;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：到账时间
 * 作者：殷曙光
 * 日期：2016/12/28 14:06
 */
@ContentView(R.layout.activity_time_table)
public class TimeTableActivity extends BaseActivity
{
    @ViewInject(R.id.listView)
    private ListView listView;
    private AbsBaseListAdapter adapter;
    private List<String> dataList = new ArrayList<>();
    private List<ServiceTime> timeList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData()
    {
        setDefaultHeader("到账时间");
        setAdapter();
        loadData();
    }

    private void loadData()
    {
        SimpleRequest<List<ServiceTime>> request = new SimpleRequest<List<ServiceTime>>("QueryBankServiceTime")
        {
            @Override
            protected List<ServiceTime> parseData(String data)
            {
                return JSON.parseArray(data, ServiceTime.class);
            }
        };

        request.sendRequestNew(new BaseCallBack<List<ServiceTime>>()
        {
            @Override
            public void onSuccess(List<ServiceTime> data)
            {
//                dataList.clear();
//                dataList.add("支持银行");
//                dataList.add("服务时间");
//                for (int i = 0; i < data.size(); i++)
//                {
//                    ServiceTime time = data.get(i);
//                    dataList.add(time.getName());
//                    dataList.add("  " + time.getStartTime() + "-" + time.getEndTime() + "  ");
//                }
                timeList.clear();
                timeList.add(new ServiceTime("支持银行","服务时间","",""));
                timeList.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Error error)
            {
                dataList.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setAdapter()
    {
        adapter = new AbsBaseListAdapter<ServiceTime>(timeList)
        {
            @Override
            protected Holder getHolder(int position)
            {
                return new ServiceTimeHolder();
            }
        };

        listView.setAdapter(adapter);
    }
}
