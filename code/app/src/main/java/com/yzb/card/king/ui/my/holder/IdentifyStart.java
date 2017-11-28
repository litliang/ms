package com.yzb.card.king.ui.my.holder;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.activity.IDAuthenticationActivity;
import com.yzb.card.king.ui.app.activity.ValidIdentificationActivity;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/20 19:49
 */
public class IdentifyStart
{
    private View mRoot;
    private View btnStart;
    private Activity activity;
    public IdentifyStart(IDAuthenticationActivity activity)
    {
        this.activity = activity;
        initView();
        initListener();
    }

    private void initListener()
    {
        btnStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                activity.startActivity(new Intent(activity, ValidIdentificationActivity.class));
            }
        });
    }

    private void initView()
    {
        mRoot = UiUtils.inflate(R.layout.holder_identify_start);
        btnStart = mRoot.findViewById(R.id.btnStart);
    }

    public View getView()
    {
        return mRoot;
    }
}
