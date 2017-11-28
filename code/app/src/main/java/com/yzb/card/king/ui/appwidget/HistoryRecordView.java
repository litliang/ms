package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.support.v7.widget.RecyclerView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.adapter.HistoryRecordAdapter;
import com.yzb.card.king.util.ToastUtil;

import java.util.List;

/**
 * 功能：搜索页 历史记录view；
 *
 * @author:gengqiyun
 * @date: 2016/10/27
 */
public class HistoryRecordView extends LinearLayout implements View.OnClickListener
{
    private View panelHistory;
    private RecyclerView recyclerviewHistory;
    private HistoryRecordAdapter adapter;

    public HistoryRecordView(Context context)
    {
        this(context, null);
    }

    public HistoryRecordView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public void setData(List<String> resultBeans)
    {
        if (resultBeans != null)
        {
            adapter.setList(resultBeans);
        }
    }

    private void init()
    {
        setOrientation(VERTICAL);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.activity_search_history_record, this);

        contentView.findViewById(R.id.tv_clear_history).setOnClickListener(this);
        panelHistory = contentView.findViewById(R.id.panel_history);

        recyclerviewHistory = (RecyclerView) contentView.findViewById(R.id.recyclerview_history);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerviewHistory.setLayoutManager(llm);
        recyclerviewHistory.setHasFixedSize(true);
        adapter = new HistoryRecordAdapter(getContext());
        //添加分割线；
        DividerDecoration decoration = new DividerDecoration(getContext(), DividerDecoration.HORIZONTAL_LIST);
        decoration.setDividerResId(getContext(), R.drawable.shape_search_divider);
        recyclerviewHistory.addItemDecoration(decoration);
        recyclerviewHistory.setAdapter(adapter);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_clear_history: //清空历史记录；
                adapter.clearAll();
                ToastUtil.i(getContext(), "清空成功");
                break;
        }
    }

    public void setItemListener(HistoryItemListener listener)
    {
        adapter.setItemListener(listener);
    }


    public interface HistoryItemListener
    {
        /**
         * 回调关键字；
         *
         * @param keyWord
         */
        void callBack(String keyWord);
    }
}
