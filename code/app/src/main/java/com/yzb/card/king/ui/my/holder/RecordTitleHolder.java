package com.yzb.card.king.ui.my.holder;

import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.my.bean.TransRecordTitle;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/27 15:08
 */
public class RecordTitleHolder extends Holder<TransRecordTitle>
{
    private View gap;
    private TextView tvDate;
    private TextView tvPayIn;
    private TextView tvPayOut;

    @Override
    public View initView()
    {
        View view = UiUtils.inflate(R.layout.holder_record_title);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvPayOut = (TextView) view.findViewById(R.id.tvPayOut);
        tvPayIn = (TextView) view.findViewById(R.id.tvPayIn);
        gap = view.findViewById(R.id.gap);
        return view;
    }

    @Override
    public void refreshView(TransRecordTitle data)
    {
        tvDate.setText(data.getMonthDesc());
        tvPayOut.setText(UiUtils.getString(R.string.transfer_pay_out,data.getPayAmount()));
        tvPayIn.setText(UiUtils.getString(R.string.transfer_pay_in,data.getIncomeAmount()));
    }

    public void setGapVisibility(int visibility)
    {
        gap.setVisibility(visibility);
    }
}
