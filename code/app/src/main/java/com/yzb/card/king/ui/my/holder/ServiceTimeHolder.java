package com.yzb.card.king.ui.my.holder;

import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.my.bean.ServiceTime;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/1/17 17:25
 */
public class ServiceTimeHolder extends Holder<ServiceTime>
{
    private TextView tvName;
    private TextView tvTime;

    @Override
    public View initView()
    {
        View view = UiUtils.inflate(R.layout.holder_service_time);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        return view;
    }

    @Override
    public void refreshView(ServiceTime data)
    {
        tvName.setText(data.getName());
        tvTime.setText(data.getStartTime() + data.getDivider() + data.getEndTime());
    }
}
