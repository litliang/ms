package com.yzb.card.king.ui.credit.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;

public class CardProtocolActivity extends BaseActivity
{
    private TextView headerTitle;
    private ImageView headerLeftImage;
    private View headerLeft;
    private TextView tvEnsure;
    private TextView tvCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    private void initListener()
    {
        tvCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        tvEnsure.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setResult(RESULT_OK);
                finish();
            }
        });
        headerLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    private void initView()
    {
        setContentView(R.layout.activity_card_protocal);

        headerLeft = findViewById(R.id.headerLeft);
        headerLeftImage = (ImageView) findViewById(R.id.headerLeftImage);
        headerTitle = (TextView) findViewById(R.id.headerTitle);

        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvEnsure = (TextView) findViewById(R.id.tvEnsure);

    }

    private void initData()
    {
        setHeader();
    }

    private void setHeader()
    {
        headerLeftImage.setImageResource(R.mipmap.icon_back_white);
        headerLeftImage.setVisibility(View.VISIBLE);
        headerTitle.setText("条款和条件");
    }
}
