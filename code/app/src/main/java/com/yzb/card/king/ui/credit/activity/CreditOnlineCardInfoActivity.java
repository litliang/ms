package com.yzb.card.king.ui.credit.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.adapter.OnLineCreditInfoAdapter;
import com.yzb.card.king.ui.credit.bean.CardOnline;
import com.yzb.card.king.ui.credit.holder.CardOnLineHolder;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.util.UiUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 在线办卡---信用卡详情
 */
public class CreditOnlineCardInfoActivity extends BaseActivity
{
    private GridView gridView;
    private TextView tvArrow;
    private LinearLayout llActivityDetail;
    private LinearLayout llArrow;
    private TextView tvQuestions;
    private LinearLayout llCardInfo;
    private CardOnline cardOnline;
    private TextView tvApply;
    private TextView tvCardServe;
    private TextView tvFeePolicy;
    private TextView tvCardLevel;
    private TextView tvOrigin;
    private TextView tvGracePeriod;
    private TextView tvActivityContent;
    private TextView tvActivityTitle;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    private void initView()
    {
        setContentView(R.layout.credit_online_card_info);
        tvArrow = (TextView) findViewById(R.id.tvArrow);
        tvActivityTitle = (TextView) findViewById(R.id.tvActivityTitle);
        tvActivityContent = (TextView) findViewById(R.id.tvActivityContent);
        tvGracePeriod = (TextView) findViewById(R.id.tvGracePeriod);
        tvOrigin = (TextView) findViewById(R.id.tvOrigin);
        tvCardLevel = (TextView) findViewById(R.id.tvCardLevel);
        tvFeePolicy = (TextView) findViewById(R.id.tvFeePolicy);
        tvCardServe= (TextView) findViewById(R.id.tvCardServe);
        tvQuestions = (TextView) findViewById(R.id.tvQuestions);
        tvApply = (TextView) findViewById(R.id.tvApply);


        llActivityDetail = (LinearLayout) findViewById(R.id.llActivityDetail);
        llArrow = (LinearLayout) findViewById(R.id.llArrow);
        llCardInfo  = (LinearLayout) findViewById(R.id.llCardInfo);

        gridView = (GridView) findViewById(R.id.gridView);


    }

    private void initListener()
    {
        switchContentLeft(AppConstant.RES_HOME_PAGE);

        //改变宽度来显示展开活动列表栏
        llArrow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (tvArrow.isEnabled())
                {
                    tvArrow.setEnabled(false);
                    llActivityDetail.setVisibility(View.VISIBLE);
                } else
                {
                    tvArrow.setEnabled(true);
                    llActivityDetail.setVisibility(View.GONE);
                }

            }
        });

        tvApply.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                applyCard();
            }
        });
    }

    private void applyCard()
    {
        Bundle bundle = new Bundle();
        bundle.putString("url",cardOnline.getUrl());
        readyGoWithBundle(this, WebViewClientActivity.class,bundle);
        SimpleRequest request = new SimpleRequest("AddApplicationNumber");
        Map<String, Object> param = new HashMap<>();
        param.put("archivesId",cardOnline.getId());
        request.setParam(param);
        request.sendRequestNew(null);
    }

    private void initData()
    {
        // header设置
        setHeader(R.mipmap.icon_back_white, "在线办卡");
        getIntentData();
        setCardInfo();
        setData(cardOnline);
//        loadData();

    }

    private void getIntentData()
    {
        cardOnline = (CardOnline) getIntent().getSerializableExtra("data");
    }

    private void setCardInfo()
    {
        llCardInfo.removeAllViews();
        CardOnLineHolder holder = new CardOnLineHolder();
        holder.setData(cardOnline);
        llCardInfo.addView(holder.getConvertView());
    }

    private void setData(CardOnline cardOnline)
    {
        gridView.setAdapter(new OnLineCreditInfoAdapter(CreditOnlineCardInfoActivity.this
                , cardOnline.getCreditRateStage()));
        tvActivityTitle.setText(cardOnline.getActivityTitle());
        tvActivityContent.setText(cardOnline.getActivityIntro());
        tvGracePeriod.setText(UiUtils.getString(R.string.card_day,cardOnline.getGracePeriod()));
        tvOrigin.setText(cardOnline.getOrigin());
        tvCardLevel.setText(cardOnline.getCardRank());
        tvFeePolicy.setText(cardOnline.getFeePolicy());
        tvCardServe.setText(cardOnline.getCardService());
        tvQuestions.setText(cardOnline.getCardQuestion());
    }

}
