package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.HotelDisBank;
import com.yzb.card.king.bean.hotel.HotelFilter;
import com.yzb.card.king.bean.hotel.HotelFilterData;
import com.yzb.card.king.bean.hotel.LevelPrice;
import com.yzb.card.king.bean.hotel.Luxury;
import com.yzb.card.king.bean.hotel.Region;
import com.yzb.card.king.bean.ticket.AbsFilter;
import com.yzb.card.king.bean.ticket.FilterType;
import com.yzb.card.king.ui.ticket.holder.ItemView;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/10/25 15:59
 */
public class HotelFilterView extends LinearLayout {
    private List<AbsFilter> items = new ArrayList<>();
    private List<ItemView> itemViews;
    public static HotelFilterData data = new HotelFilterData();

    public HotelFilterView(Context context) {
        super(context);
        init();
    }

    public HotelFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HotelFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initData();
        addItemView();
        initListener();
        refreshView();
    }

    private void initListener() {
        data.setListener(new HotelFilterData.OnDataChangeListener() {
            @Override
            public void onDataChange() {
                refreshView();
            }

            @Override
            public void onBankChanged(String code) {
                refreshView();
            }
        });
    }

    private void refreshView() {
        itemViews.get(0).setEnabled(hasFilterData());
        itemViews.get(1).setEnabled(!"0".equals(data.getDisBankCode()));
        itemViews.get(2).setEnabled(data.getLuxury() != null);
        itemViews.get(3).setEnabled(!isEmpty(data.getPositionList()));
        itemViews.get(4).setEnabled(!"0".equals(data.getPrice()));
    }

    private boolean hasFilterData() {
        return !(isEmpty(data.getSearchList()) &&
                data.getMinVote() == 0 &&
                TextUtils.isEmpty(data.getBreakfasts()) &&
                TextUtils.isEmpty(data.getBedTypes()) &&
                !"1".equals(data.getCancelStatus()));
    }

    private boolean isEmpty(List<FilterType> searchList) {
        return searchList == null || searchList.size() == 0;
    }

    private void initData() {
        items.clear();
        items.add(new HotelFilter());
        items.add(new HotelDisBank());
        items.add(new Luxury());
        items.add(new Region());
        items.add(new LevelPrice());
    }



    private void addItemView() {
        if (items != null) {
            LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            itemViews = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
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
}
