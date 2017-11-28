package com.yzb.card.king.ui.discount.holder;

import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.discount.bean.Specification;
import com.yzb.card.king.util.UiUtils;

/**
 * 类名：SpecificationsItemHolder
 * 作者：殷曙光
 * 日期：2016/7/28 16:55
 * 描述：
 */

public class SpecificationsItemHolder extends Holder<Specification> {

    private TextView textView;
    private View.OnClickListener listener;
    private Specification specification;

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public View initView() {
        View view = UiUtils.inflate(R.layout.item_goods_specification);
        textView = (TextView) view.findViewById(R.id.textView);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(v);
            }
        });
        return view;
    }

    @Override
    public void refreshView(Specification data) {
        specification = data;
        textView.setTag(data);
        textView.setText(data.specName);
    }

    public TextView setTextViewEnable(boolean enable) {
        textView.setEnabled(enable);
        return textView;
    }
}

