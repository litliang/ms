package com.yzb.card.king.ui.my.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.my.bean.Payee;
import com.yzb.card.king.ui.my.bean.TransRecordTitle;
import com.yzb.card.king.ui.my.holder.RecordChildHolder;
import com.yzb.card.king.ui.my.holder.RecordTitleHolder;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.UiUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：转账记录
 * 作者：殷曙光
 * 日期：2016/12/27 11:07
 */
@ContentView(R.layout.activity_trans_record)
public class TransRecordActivity extends BaseActivity
{
    @ViewInject(R.id.listView)
    private ExpandableListView exbListView;

    private List<TransRecordTitle> titles = new ArrayList<>();
    private MyExbAdapter adapter;
    private CheckBox cbSave;
    private int duration;
    private Payee payee;

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
//        View footer = UiUtils.inflate(R.layout.footer_transfer_record);
        cbSave = (CheckBox) findViewById(R.id.cbSave);
//        exbListView.addFooterView(footer);
    }

    private void initData()
    {
        getIntentData();
        setDefaultHeader("转账记录");
        cbSave.setChecked(getRecordStatus());
        duration = cbSave.isChecked() ? 6 : 120;
        initListView();
        loadRecord();
    }

    private void getIntentData()
    {
        payee = (Payee) getIntent().getSerializableExtra("payee");
    }

    private void saveState(boolean isChecked)
    {
        SharePrefUtil.saveToSp(this, "TransRecordStatus", isChecked ? "1" : "0");
    }

    private boolean getRecordStatus()
    {
        return "1".equals(SharePrefUtil.getValueFromSp(this, "TransRecordStatus", "1"));
    }

    private void loadRecord()
    {
        SimpleRequest<List<TransRecordTitle>> request
                = new SimpleRequest<List<TransRecordTitle>>("QueryTransferOrder")
        {
            @Override
            protected List<TransRecordTitle> parseData(String data)
            {
                return JSON.parseArray(data, TransRecordTitle.class);
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("tradeObject", "1");//1平台账户；2银行卡
        param.put("creditId", payee.getCreditId());
        param.put("startDate", getStartDate());
        param.put("endDate", getEndDate());
        param.put("pageStart", 0);
        param.put("pageSize", 100);
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<List<TransRecordTitle>>()
        {
            @Override
            public void onSuccess(List<TransRecordTitle> data)
            {
                notifyData(data);
            }

            @Override
            public void onFail(Error error)
            {
                notifyData(null);
                UiUtils.shortToast(error.getError());
            }
        });
    }

    private void notifyData(List<TransRecordTitle> data)
    {
        titles.clear();
        if (data != null)
        {
            titles.addAll(data);
        }
        adapter.notifyDataSetChanged();
    }

    private Object getEndDate()
    {
        return DateUtil.date2String(DateUtil.addDay(new Date(),1), "yyyy-MM-dd");
    }

    private Object getStartDate()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -duration);
        return DateUtil.date2String(calendar.getTime(), "yyyy-MM-dd");
    }

    private void initListView()
    {

        exbListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
        {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id)
            {
                return true;//屏蔽点击收缩事件
            }
        });
        adapter = new MyExbAdapter();
        exbListView.setAdapter(adapter);
    }


    class MyExbAdapter extends BaseExpandableListAdapter
    {

        @Override
        public int getGroupCount()
        {
            return titles.size();
        }

        @Override
        public int getChildrenCount(int groupPosition)
        {
            return titles.get(groupPosition).getDetailList().size();
        }

        @Override
        public Object getGroup(int groupPosition)
        {
            return titles.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            return titles.get(groupPosition).getDetailList().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition)
        {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            return 0;
        }

        @Override
        public boolean hasStableIds()
        {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
        {
            if (!isExpanded)
            {
                exbListView.expandGroup(groupPosition);//展开组
            }
            RecordTitleHolder holder;
            if (convertView == null)
            {
                holder = new RecordTitleHolder();
            } else
            {
                holder = (RecordTitleHolder) convertView.getTag();
            }
            if (groupPosition == 0)
            {
                holder.setGapVisibility(View.GONE);
            } else
            {
                holder.setGapVisibility(View.VISIBLE);
            }
            holder.setData(titles.get(groupPosition));
            return holder.getConvertView();
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
        {
            RecordChildHolder holder;
            if (convertView == null)
            {
                holder = new RecordChildHolder();
            } else
            {
                holder = (RecordChildHolder) convertView.getTag();
            }
            if (childPosition == getChildrenCount(groupPosition) - 1)
            {
                holder.setDividerVisibility(View.GONE);
            } else
            {
                holder.setDividerVisibility(View.VISIBLE);
            }
            holder.setData(titles.get(groupPosition).getDetailList().get(childPosition));
            return holder.getConvertView();
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition)
        {
            return false;
        }
    }

    @Override
    protected void onDestroy()
    {
        saveState(cbSave.isChecked());
        super.onDestroy();
    }
}


