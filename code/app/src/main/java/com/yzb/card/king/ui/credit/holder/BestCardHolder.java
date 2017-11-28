package com.yzb.card.king.ui.credit.holder;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.credit.bean.BestCard;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

public class BestCardHolder extends Holder<BestCard>
{
    ImageView ivCardImage;//活动图片
    TextView tvCardName;//信用卡
    TextView tvApplyNum;//
    TextView tvApply;//人数
    private View view;
    private TextView tvCardWanted;
    private TextView tvIntro;
    private TextView tvTitle;

    @Override
    public View initView()
    {
        view = UiUtils.inflate(R.layout.listview_hot_on_line);
        ivCardImage = (ImageView) view.findViewById(R.id.ivCardImage);
        tvCardName = (TextView) view.findViewById(R.id.tvCardName);
        tvApplyNum = (TextView) view.findViewById(R.id.tvApplyNum);
        tvApply = (TextView) view.findViewById(R.id.tvApply);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvIntro = (TextView) view.findViewById(R.id.tvIntro);
        tvCardWanted = (TextView) view.findViewById(R.id.tvCardWanted);
        initListener();
        return view;
    }

    private void initListener()
    {
        tvApply.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!TextUtils.isEmpty(getData().getUrl()))
                {
                    applyCard();
                } else
                {
                    UiUtils.shortToast("没找到路径");
                }
            }
        });
    }

    private void applyCard()
    {
        Bundle bundle = new Bundle();
        bundle.putString("url", getData().getUrl());
        UiUtils.readyGoWithBundle(WebViewClientActivity.class, bundle);
        SimpleRequest request = new SimpleRequest("AddApplicationNumber");
        Map<String, Object> param = new HashMap<>();
        param.put("archivesId", getData().getArchivesId());
        request.setParam(param);
        request.sendRequestNew(null);
    }

    @Override
    public void refreshView(BestCard data)
    {
        x.image().bind(ivCardImage, ServiceDispatcher.getImageUrl(data.getPhoto()));
        tvCardName.setText(data.getName());
        tvApplyNum.setText(getNum(data));
        tvCardWanted.setText(data.getCardWanted());
        tvTitle.setText(data.getTitle());
        tvIntro.setText(data.getIntro());
    }

    private String getNum(BestCard data)
    {
        if (data.getNum() < 10000)
        {
            return data.getNum() + "";
        } else
        {
            return data.getNum() / 10000 + "万";
        }
    }
}