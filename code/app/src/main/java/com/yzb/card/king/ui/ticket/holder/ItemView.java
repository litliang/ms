package com.yzb.card.king.ui.ticket.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.AbsFilter;
import com.yzb.card.king.util.UiUtils;

public class ItemView
{
    private View root;
    private ImageView ivPic;
    private TextView tvDot;
    private TextView tvName;
    private AbsFilter item;

    public ItemView(AbsFilter item)
    {
        this.item = item;
        initView();
        initData();
    }

    private void initData()
    {
        refreshData();
    }

    public void refreshData()
    {
        ivPic.setBackgroundResource(item.getPic());
        tvName.setText(item.getName());
        tvName.setTextColor(item.getTextColor());

    }

    private void initView()
    {
        root = UiUtils.inflate(R.layout.ticket_list_bottom_item);
        ivPic = (ImageView) root.findViewById(R.id.ivPic);
        tvDot = (TextView) root.findViewById(R.id.tvDot);
        tvName = (TextView) root.findViewById(R.id.tvName);
        setOnClickListener();
    }


    public void setEnabled(boolean selected)
    {
        tvDot.setEnabled(selected);
        ivPic.setEnabled(selected);
        tvName.setEnabled(selected);
    }

    public View getRoot()
    {
        return root;
    }

    public void setOnClickListener()
    {
        root.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                item.clickAction(v);
            }
        });
    }

    public void hideDotView()
    {
        tvDot.setVisibility(View.GONE);
    }

    public void setBgColor(int color)
    {
        root.setBackgroundColor(color);
    }
}