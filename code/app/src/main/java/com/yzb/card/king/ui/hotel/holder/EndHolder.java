package com.yzb.card.king.ui.hotel.holder;

import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.FilterType;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.UiUtils;

public class EndHolder extends Holder<FilterType>
{
    private TextView textView;
    private View.OnClickListener listener;

    public void setListener(View.OnClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public View initView()
    {
        View view = UiUtils.inflate(R.layout.filter_end_item);
        textView = (TextView) view.findViewById(R.id.textView);
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onClick(v);
            }
        });
        return view;
    }

    @Override
    public void refreshView(FilterType data)
    {
        textView.setText(data.getName());
    }
}