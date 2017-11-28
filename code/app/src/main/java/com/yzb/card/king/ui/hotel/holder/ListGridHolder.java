package com.yzb.card.king.ui.hotel.holder;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.FilterType;
import com.yzb.card.king.ui.hotel.interfaces.OnTypeClickListener;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.UiUtils;

public class ListGridHolder extends Holder<FilterType>
{
    private FilterType filterType;
    private TextView textView;
    private OnTypeClickListener onTypeClickListener;

    @Override
    public View initView()
    {
        View view = UiUtils.inflate(R.layout.filter_list_grid_item);
        textView = (TextView) view.findViewById(R.id.textView);
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                filterType.setSelected(!filterType.isSelected());
                if (onTypeClickListener != null)
                {
                    onTypeClickListener.onTypeClick(filterType);
                }
            }
        });
        return view;
    }

    public void setStyle(int background, ColorStateList colorList)
    {
        if (background != 0 && colorList != null)
        {
            textView.setBackgroundResource(background);
            textView.setTextColor(colorList);
        }
    }

    @Override
    public void refreshView(FilterType data)
    {
        this.filterType = data;
        textView.setText(data.getName());
        textView.setEnabled(data.isSelected());
    }

    public void setListener(OnTypeClickListener onTypeClickListener)
    {
        this.onTypeClickListener = onTypeClickListener;
    }
}
