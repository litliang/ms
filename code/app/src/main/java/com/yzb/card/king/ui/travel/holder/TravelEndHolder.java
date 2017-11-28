package com.yzb.card.king.ui.travel.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.FilterType;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.UiUtils;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/11/22
 * 描  述：
 */
public class TravelEndHolder extends Holder<FilterType> {
    private TextView textView;
    private View.OnClickListener listener;
    private ImageView imageView;

    public void setListener(View.OnClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public View initView()
    {
        View view = UiUtils.inflate(R.layout.travel_filter_end_item);
        textView = (TextView) view.findViewById(R.id.textView);
        imageView = (ImageView) view.findViewById(R.id.imgView);
        view.setOnClickListener(new View.OnClickListener() {
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
        if (data.getName().equals("更多"))
        {
            imageView.setBackgroundResource(R.mipmap.icon_arrow_down_red);
        } else
        {
            imageView.setBackgroundResource(R.mipmap.icon_arrow_up_red);
        }
    }
}
