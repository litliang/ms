package com.yzb.card.king.ui.discount.holder;

import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.UiUtils;

public class HistoryHolder extends Holder<String> {

    private TextView textView;

    @Override
    public View initView() {
        View view = UiUtils.inflate(R.layout.item_search_history);
        textView = (TextView) view.findViewById(R.id.textView);
        return view;
    }

    @Override
    public void refreshView(String data) {
        textView.setText(data);
    }
}
