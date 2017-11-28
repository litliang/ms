package com.yzb.card.king.ui.app.holder;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.my.bean.CertType;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.UiUtils;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/20 19:42
 * 描述：
 */
public class IdentificationsListHolder extends Holder<CertType>
{
    private final int selectItemType;
    TextView textView;
    CertType data;

    public IdentificationsListHolder(int selectItemType)
    {
        this.selectItemType = selectItemType;
    }

    @Override
    public View initView()
    {
        textView = new TextView(UiUtils.getContext());
        textView.setHeight(UiUtils.dp2px(38));
        textView.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setTextColor(UiUtils.getColor(R.color.user_center_black));
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void refreshView(CertType data)
    {
        this.data = data;
        textView.setText(data.getName());

        textView.setTextColor((selectItemType + "").equals(data.getType()) ? UiUtils.getColor(R.color.color_436a8e) :
                UiUtils.getColor(R.color.user_center_black));
    }
}
