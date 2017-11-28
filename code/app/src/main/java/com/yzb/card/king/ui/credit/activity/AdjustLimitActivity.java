package com.yzb.card.king.ui.credit.activity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.yzb.card.king.R;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.HeadFootRecyclerView;
import com.yzb.card.king.ui.appwidget.appdialog.WaitingDialog;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.credit.adapter.AdjustLimitAdapter;
import com.yzb.card.king.ui.credit.bean.AdjustCard;
import com.yzb.card.king.ui.credit.presenter.AdjustLimitPresenter;
import com.yzb.card.king.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 额度调整
 */
public class AdjustLimitActivity extends BaseActivity implements BaseViewLayerInterface
{
    private List<AdjustCard> data;
    private HeadFootRecyclerView listView;

    private AdjustLimitAdapter adapter;
    private AdjustLimitPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_limit);

        // 设置标题
        setTitleNmae(getString(R.string.tv_quota_adjustment));

        switchContentLeft(AppConstant.RES_HOME_PAGE);

        init();

    }

    /**
     * 标题左侧返回
     */
    public void switchContentLeft(final int resultCode)
    {

        LinearLayout headerLeft = (LinearLayout) findViewById(R.id.llTemp);

        // 返回首页
        headerLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                setResult(resultCode);
                finish();
            }
        });
    }

    public void init()
    {
        listView = (HeadFootRecyclerView) findViewById(R.id.listView);
        listView.setLayoutManager(new LinearLayoutManager(this));
        data = new ArrayList<>();
        adapter = new AdjustLimitAdapter(this, data);
        listView.setAdapter(adapter);
        listView.setNeedDivider(true);
        presenter = new AdjustLimitPresenter(this);
        queryCreditCard();
    }

    /**
     * 查询信用卡列表
     */
    public void queryCreditCard()
    {

        WaitingDialog.create(AdjustLimitActivity.this, getString(R.string.setting_loading));

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("pageStart", 0);
        param.put("pageSize", AppConstant.MAX_PAGE_NUM);
        presenter.queryCreditCard(param);
    }



    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (o instanceof List)
        {
            WaitingDialog.close();
            List<AdjustCard> list = (List<AdjustCard>) o;
            data.clear();
            data.addAll(list);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        WaitingDialog.close();
        if (o != null && o instanceof Map)
        {
            Map<String, String> onSuccessData = (Map<String, String>) o;

            ToastUtil.i(GlobalApp.getInstance().getContext(),
                    onSuccessData.get(HttpConstant.SERVER_ERROR));
        }
        data.clear();
        listView.notifyData();
    }
}
