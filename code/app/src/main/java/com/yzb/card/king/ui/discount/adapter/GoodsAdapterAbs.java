package com.yzb.card.king.ui.discount.adapter;

import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.ui.discount.bean.Goods;
import com.yzb.card.king.ui.discount.holder.GoodsHolder;
import com.yzb.card.king.ui.discount.holder.OrderDetailHolder;

import java.util.List;

public class GoodsAdapterAbs extends AbsBaseListAdapter<Goods> {

    private OrderDetailHolder orderDetailHolder;

    public GoodsAdapterAbs(List<Goods> list, OrderDetailHolder orderDetailHolder) {
            super(list);
        this.orderDetailHolder = orderDetailHolder;
        }

        @Override
        protected Holder getHolder(int position) {

            return new GoodsHolder(orderDetailHolder);
        }
    }