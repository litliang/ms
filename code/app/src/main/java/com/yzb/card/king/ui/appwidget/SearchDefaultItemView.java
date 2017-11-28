package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.SearchDefaultBean;
import com.yzb.card.king.ui.app.adapter.SearchDefaultAdapter;
import com.yzb.card.king.ui.app.adapter.SearchListGvAdapter;
import com.yzb.card.king.util.LogUtil;

import java.util.List;

/**
 * 功能：搜索默认列表item view；
 *
 * @author:gengqiyun
 * @date: 2016/10/27
 */
public class SearchDefaultItemView extends LinearLayout
{
    private GridView gridView;
    private SearchDefaultAdapter.ICallBack callBack;
    private List<SearchDefaultBean.Child> resultBeans;

    public SearchDefaultItemView(Context context)
    {
        this(context, null);
    }

    public SearchDefaultItemView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    /**
     * 注入数据；
     *
     * @param resultBeans
     */
    public void setData(List<SearchDefaultBean.Child> resultBeans)
    {
        this.resultBeans = resultBeans;
        if (resultBeans != null)
        {
            gridView.setVisibility(VISIBLE);
            SearchListGvAdapter gvAdapter = new SearchListGvAdapter(getContext());
            gvAdapter.appendALL(resultBeans);
            gridView.setAdapter(gvAdapter);
        } else
        {
            gridView.setVisibility(GONE);
        }
    }

    private void init()
    {
        setOrientation(VERTICAL);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.item_search_default_view, this);
        gridView = (GridView) contentView.findViewById(R.id.gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (callBack != null)
                {
                    SearchDefaultBean.Child child = resultBeans.get(position);
                    callBack.callBack(child);
                }
            }
        });

    }

    public void setItemListener(SearchDefaultAdapter.ICallBack callBack)
    {
        this.callBack = callBack;
    }

}
