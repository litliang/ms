package com.yzb.card.king.ui.travel.activity.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.adapter.TravelHistoryAdapter;
import com.yzb.card.king.ui.appwidget.BaseViewGroup;
import com.yzb.card.king.ui.appwidget.DividerDecoration;
import com.yzb.card.king.ui.appwidget.HistoryRecordView;

import java.util.List;

/**
 * 功能：旅游目的地搜索页 历史记录view；
 *
 * @author:gengqiyun
 * @date: 2016/11/21
 */
public class TravelHistoryRecordView extends BaseViewGroup
{
    private RecyclerView historyRecyView;
    private TravelHistoryAdapter adapter;

    public TravelHistoryRecordView(Context context)
    {
        super(context);
    }

    public TravelHistoryRecordView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setData(List<String> resultBeans)
    {
        if (resultBeans != null)
        {
            adapter.setList(resultBeans);
        }
    }

    @Override
    protected void init()
    {
        super.init();
        setOrientation(HORIZONTAL);
        historyRecyView = (RecyclerView) rootView.findViewById(R.id.historyRecyView);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);

        DividerDecoration divider1 = new DividerDecoration(getContext(), DividerDecoration.HORIZONTAL_LIST,
                R.drawable.listview_divider_width_20dp);
        historyRecyView.addItemDecoration(divider1);

        historyRecyView.setLayoutManager(llm);
        adapter = new TravelHistoryAdapter(mContext);
        historyRecyView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.activity_travel_history_record;
    }

    public void setItemListener(HistoryRecordView.HistoryItemListener listener)
    {
        adapter.setItemListener(listener);
    }
}
