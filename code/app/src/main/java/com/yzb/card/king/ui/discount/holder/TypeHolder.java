package com.yzb.card.king.ui.discount.holder;

import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.discount.bean.GoodsType;
import com.yzb.card.king.util.UiUtils;

public class TypeHolder extends Holder<GoodsType> {
    private TextView textView;

    @Override
    public View initView() {
        View view = UiUtils.inflate(R.layout.item_shop_type);
        textView = (TextView) view.findViewById(R.id.textView);
        return view;
    }

    @Override
    public void refreshView(GoodsType data) {
        textView.setText(data.typeName);
    }
}