package com.yzb.card.king.ui.my.holder;

import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.my.bean.SmallAmount;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/26 19:53
 */
public class SmallAmountHolder extends Holder<SmallAmount>
{
    private TextView tvValue;
    private View tvIcon;

    @Override
    public View initView()
    {

        View view = UiUtils.inflate(R.layout.holder_small_amount);
        tvValue = (TextView) view.findViewById(R.id.tvValue);
        tvIcon = view.findViewById(R.id.tvIcon);
        return view;
    }

    @Override
    public void refreshView(SmallAmount data)
    {
        tvValue.setText(data.getValue());
        tvIcon.setEnabled(data.isChecked());
    }
}
