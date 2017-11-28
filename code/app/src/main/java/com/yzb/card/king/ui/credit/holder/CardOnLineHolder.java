package com.yzb.card.king.ui.credit.holder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.credit.activity.CreditOnlineCardInfoActivity;
import com.yzb.card.king.ui.credit.bean.CardOnline;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/22
 */
public class CardOnLineHolder extends Holder<CardOnline>
{
    private ImageView ivCard;
    private TextView tvCardName;
    private TextView tvNum;
    private TextView tvDesc;

    @Override
    public View initView()
    {
        View view = UiUtils.inflate(R.layout.holder_card_online);
        ivCard = (ImageView) view.findViewById(R.id.ivCard);
        tvCardName = (TextView) view.findViewById(R.id.tvCardName);
        tvDesc = (TextView) view.findViewById(R.id.tvDesc);
        tvNum = (TextView) view.findViewById(R.id.tvNum);
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setClass(UiUtils.getContext(), CreditOnlineCardInfoActivity.class);
                intent.putExtra("data", getData());
                UiUtils.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void refreshView(CardOnline data)
    {
        x.image().bind(ivCard, ServiceDispatcher.getImageUrl(data.getPhoto()));
        tvCardName.setText(data.getName());
        tvDesc.setText(data.getPointIntro());
        tvNum.setText(Integer.toString(data.getNum()));
    }
}
