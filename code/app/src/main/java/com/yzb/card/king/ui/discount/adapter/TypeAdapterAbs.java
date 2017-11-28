package com.yzb.card.king.ui.discount.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.ui.discount.bean.GoodsType;
import com.yzb.card.king.ui.discount.bean.Position;
import com.yzb.card.king.ui.discount.holder.TypeHolder;

import java.util.List;

public class TypeAdapterAbs extends AbsBaseListAdapter<GoodsType> {
    private Position selectPosition;

    public TypeAdapterAbs(List<GoodsType> list, Position selectPosition) {
        super(list);
        this.selectPosition = selectPosition;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        view.findViewById(R.id.textView).setEnabled(selectPosition.position != position);
        return view;
    }

    @Override
    protected Holder getHolder(int position) {
        return new TypeHolder();
    }
}