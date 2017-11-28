package com.yzb.card.king.ui.my.holder;

import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.my.bean.TransRecordChild;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/27 15:09
 */
public class RecordChildHolder extends Holder<TransRecordChild>
{
    private View divider;
    private TextView tvDate;
    private TextView tvAmount;
    private TextView tvName;
    private TextView tvPayStatus;
    private TextView tvTime;

    @Override
    public View initView()
    {
        View view = UiUtils.inflate(R.layout.holder_record_child);
        divider = view.findViewById(R.id.divider);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvTime =  (TextView)view.findViewById(R.id.tvTime);
        tvPayStatus =  (TextView)view.findViewById(R.id.tvPayStatus);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvAmount =  (TextView)view.findViewById(R.id.tvAmount);
        return view;
    }

    @Override
    public void refreshView(TransRecordChild data)
    {
        tvDate.setText(DateUtil.date2String(data.getTradeTime(),"yyyy-MM-dd"));
        tvTime.setText(DateUtil.date2String(data.getTradeTime(),"HH:mm"));
        tvPayStatus.setText(data.getPaymentsStatus());
        tvPayStatus.setEnabled("支".equals(data.getPaymentsStatus()));
        tvName.setText(data.getTradeDesc());
        tvAmount.setText(getAmount(data));
    }

    private String getAmount(TransRecordChild data)
    {
        if("支".equals(data.getPaymentsStatus()))return "-¥"+data.getTradeAmount();

        return "¥"+data.getTradeAmount();
    }

    public void setDividerVisibility(int visibility)
    {
        divider.setVisibility(visibility);
    }
}
