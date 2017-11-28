package com.yzb.card.king.ui.discount.adapter;

import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.ui.discount.holder.HistoryHolder;

import java.util.List;

public class HistoryAdapterAbs extends AbsBaseListAdapter<String> {

    public HistoryAdapterAbs(List<String> list) {
        super(list);
    }

    @Override
    protected Holder getHolder(int position) {
        return new HistoryHolder();
    }
}