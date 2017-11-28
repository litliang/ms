package com.yzb.card.king.ui.travel.activity.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.TravelLineBean;
import com.yzb.card.king.ui.appwidget.BaseViewGroup;
import com.yzb.card.king.ui.appwidget.DividerDecoration;
import com.yzb.card.king.ui.travel.adapter.TravelLineAdapter;
import com.yzb.card.king.util.LogUtil;

import java.util.List;

/**
 * 功能：旅游详情-->线路列表view；
 *
 * @author:gengqiyun
 * @date: 2016/11/24
 */
public class TravelDetailLinesView extends BaseViewGroup
{
    private RecyclerView lineRecyclerView;
    private TravelLineAdapter lineAdapter;

    public TravelDetailLinesView(Context context)
    {
        super(context);
    }

    public TravelDetailLinesView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void init()
    {
        super.init();
        lineRecyclerView = (RecyclerView) rootView.findViewById(R.id.lineRecyclerView);
        lineRecyclerView.setFocusable(false);

        //设置水平方向；
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        lineRecyclerView.setLayoutManager(llm);
        DividerDecoration divider1 = new DividerDecoration(getContext(), DividerDecoration.HORIZONTAL_LIST,
                R.drawable.listview_divider_width_10dp);
        lineRecyclerView.addItemDecoration(divider1);
    }

    @Override
    public void fillViewTravelLines(final List<TravelLineBean> lineBeans)
    {
        if (lineBeans.size() > 0)
        {
            lineAdapter = new TravelLineAdapter(lineBeans, mContext);
            lineAdapter.setLineClick(new TravelLineAdapter.ILineClick()
            {
                @Override
                public void getClickLine(int linePos)
                {
                    if (linePos != lineAdapter.getSelectItemPos())
                    {
                        //点击需要刷新线路
                        provider.switchLines(linePos, true);
                    }
                }
            });
            lineRecyclerView.setAdapter(lineAdapter);

            //显示第一个线路的数据；
            provider.switchLines(0, needRefresh);
        }
        //1个线路不显示；
        lineRecyclerView.setVisibility(lineBeans.size() > 1 ? VISIBLE : GONE);
    }

    /**
     * 获取选中的线路；
     *
     * @return
     */
    public TravelLineBean getSelectLine()
    {
        if (lineAdapter != null)
        {
            return lineAdapter.getSelectItem();
        }
        return null;
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.travel_detail_lines;
    }


    /**
     * 是否需要刷新；
     * 主要用于解决首页下拉刷新时真假线路列表刷新2次的问题；
     */
    private boolean needRefresh = false;

    public void setNeedRefresh(boolean needRefresh)
    {
        this.needRefresh = needRefresh;
    }

    /**
     * 选中某一项；
     *
     * @param lineBean
     */
    public void selectItem(TravelLineBean lineBean)
    {
        if (lineAdapter != null)
        {
            lineAdapter.selectItem(lineBean);
        }
    }
}
