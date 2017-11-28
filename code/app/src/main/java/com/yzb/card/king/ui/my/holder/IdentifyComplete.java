package com.yzb.card.king.ui.my.holder;

import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.activity.ValidFinishActivity;
import com.yzb.card.king.ui.app.manager.ValidManager;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/20 19:48
 */
public class IdentifyComplete
{
    private final ValidFinishActivity activity;
    private View mRoot;
    private View btComplete;

    public IdentifyComplete(ValidFinishActivity activity)
    {
        this.activity = activity;
        initView();
        initListener();
    }

    private void initListener()
    {
        btComplete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ValidManager.getValidManager().finishAll();
            }
        });
    }

    private void initView()
    {
        mRoot = UiUtils.inflate(R.layout.holder_identify_complete);
        btComplete = mRoot.findViewById(R.id.btComplete);
    }

    public View getView()
    {
        return mRoot;
    }
}
