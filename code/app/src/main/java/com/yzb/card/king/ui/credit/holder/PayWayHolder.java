package com.yzb.card.king.ui.credit.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.credit.bean.PayWay;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/28
 */
public class PayWayHolder extends Holder<PayWay>
{
    private TextView tvPayMethodName;
    private TextView tvBalance;
    private ImageView ivPic;

    @Override
    public View initView()
    {
        View view = UiUtils.inflate(R.layout.holder_pay_way);
        tvPayMethodName = (TextView) view.findViewById(R.id.tvPayMethodName);
        tvBalance = (TextView) view.findViewById(R.id.tvBalance);
        ivPic = (ImageView) view.findViewById(R.id.ivPic);
        return view;
    }

    @Override
    public void refreshView(PayWay data)
    {
        x.image().bind(ivPic, ServiceDispatcher.getImageUrl(data.getLogo()));
        tvPayMethodName.setText(data.getPayName());
        tvBalance.setText(UiUtils.getString(R.string.card_balance,data.getLimitAmount()));
    }
}
