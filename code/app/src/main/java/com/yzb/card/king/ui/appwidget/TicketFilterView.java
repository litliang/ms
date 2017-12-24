package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.AbsFilter;
import com.yzb.card.king.bean.ticket.AirLineCompanyFilter;
import com.yzb.card.king.bean.ticket.DirectFlight;
import com.yzb.card.king.bean.ticket.DisBank;
import com.yzb.card.king.bean.ticket.DiscountActivityFilter;
import com.yzb.card.king.bean.ticket.Filter;
import com.yzb.card.king.bean.ticket.FilterData;
import com.yzb.card.king.bean.ticket.LowHeight;
import com.yzb.card.king.bean.ticket.MorningNight;
import com.yzb.card.king.ui.appwidget.popup.AirLineCompanyPP;
import com.yzb.card.king.ui.ticket.activity.SingleListActivity;
import com.yzb.card.king.ui.ticket.holder.ItemView;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述：机票列表底部筛选器
 * 作者：殷曙光
 * 日期：2016/9/28 15:05
 */
public class TicketFilterView extends LinearLayout
{
    public static FilterData filterData = new FilterData();
    private Context context;
    private List<AbsFilter> items = new ArrayList<>();
    private List<ItemView> itemViews;
    //最后两个Item：从早到晚，从低到高
    private FilterData.OnDataChangeListener listener;

    public List<AbsFilter> getItems()
    {
        return items;
    }


    public TicketFilterView(Context context)
    {
        super(context);
        this.context = context;
        init();
    }

    public TicketFilterView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public TicketFilterView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        initData();
        setDataChangedListener();
        addItemView();
        refreshView();
    }

    private void initData()
    {
        items.clear();
        items.add(new Filter());
        items.add(new AirLineCompanyFilter().setInvoiceCCallBack(new AirLineCompanyPP.BottomDataListCallBack(){

            @Override
            public void onClickItemDataBack(String name, int nameValue, int selectIndex) {
                ((SingleListActivity)context).refreshData();
            }
        }));//航空公司
      //  items.add(new DirectFlight());
        items.add(new DiscountActivityFilter());//优惠活动
        //
        items.add(new MorningNight());
        items.add(new LowHeight());
    }

    private void addItemView()
    {
        if (items != null)
        {
            LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            itemViews = new ArrayList<>();
            for (int i = 0; i < items.size(); i++)
            {
                ItemView itemView = new ItemView(items.get(i));
                itemView.hideDotView();
                itemView.setBgColor(UiUtils.getColor(R.color.hotel_black2));
                View view = itemView.getRoot();
                view.setLayoutParams(layoutParams);
                addView(view);
                itemViews.add(itemView);
            }
        }
    }

    private void setDataChangedListener()
    {
        listener = new FilterData.OnDataChangeListener()
        {
            @Override
            public void onDataChanged()
            {
                refreshView();
            }

            @Override
            public void onBankChanged(boolean selected)
            {
                refreshView();
            }
        };
        filterData.setListener(listener);
    }

    private void refreshView()
    {
        itemViews.get(0).setEnabled(filterData.getFilterTypes() != null &&
                filterData.getFilterTypes().size() > 0 && hasSelected());
        itemViews.get(1).setEnabled(!"0".equals(filterData.getDisBankCode()));
      //  itemViews.get(2).setEnabled(!"0".equals(filterData.getDirectFlight().getCode()));
        itemViews.get(2).setEnabled(false);
        itemViews.get(3).setEnabled(!"0".equals(filterData.getTime().getCode()));
        itemViews.get(4).setEnabled(!"0".equals(filterData.getPrice().getCode()));
        for (int i = 0; i < itemViews.size(); i++)
        {
            itemViews.get(i).refreshData();
        }
    }

    private boolean hasSelected()
    {
        boolean selected = false;
        for (int i = 0; i < filterData.getFilterTypes().size(); i++)
        {
            if (!"0".equals(filterData.getFilterTypes().get(i).getCode()))
            {
                selected = true;
                break;
            }
        }
        return selected;
    }

    public void setInternational(boolean isInternational)
    {
 //       int visibility = GONE;
//        if (isInternational)
//        {
//            visibility = VISIBLE;
//        }
//        itemViews.get(2).getRoot().setVisibility(visibility);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();

    }

    public List<Map<String, String>> getData()
    {
        return new ArrayList<>();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        filterData.removeListener(listener);
    }
}
