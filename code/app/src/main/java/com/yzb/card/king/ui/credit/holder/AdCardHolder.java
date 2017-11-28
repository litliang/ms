package com.yzb.card.king.ui.credit.holder;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.discount.bean.LbtBean;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/21
 */

public class AdCardHolder extends Holder<LbtBean>
{
    private TextView tvTitle;
    private ImageView ivPic;

    @Override
    public View initView()
    {
        View view = UiUtils.inflate(R.layout.holder_ad_card);
        ivPic = (ImageView) view.findViewById(R.id.ivPic);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        initListener();
        return view;
    }

    private void initListener()
    {
        ivPic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Bundle bundle = new Bundle();
                bundle.putString("content",getData().h5Detail);
                UiUtils.readyGoWithBundle(WebViewClientActivity.class,bundle);
            }
        });
    }

    @Override
    public void refreshView(LbtBean data)
    {
        x.image().bind(ivPic, ServiceDispatcher.getImageUrl(data.picCode));
        tvTitle.setText(data.title);
    }
}
