package com.yzb.card.king.ui.credit.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.bean.CreditCardPaceBean;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名：CreditCardPaceActivity
 * 作者：殷曙光
 * 日期：2016/7/28 10:06
 * 描述：
 */

public class CreditCardPaceActivity extends BaseActivity
{

    private ListView listView;
    private AbsBaseListAdapter<CreditCardPaceBean> adapter;
    private List<CreditCardPaceBean> dataList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData()
    {
        //setHeader(R.mipmap.icon_back_white, "办卡进度");

        setTitleNmae("办卡进度");
        switchContentLeft(AppConstant.RES_HOME_PAGE);
        adapter = new CreditCardPaceAdapterAbs(dataList);
        listView.setAdapter(adapter);
        getDataList();
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

    private void getDataList()
    {
        SimpleRequest<List<CreditCardPaceBean>> request
                = new SimpleRequest<List<CreditCardPaceBean>>("QueryCreditApply")
        {
            @Override
            protected List<CreditCardPaceBean> parseData(String data)
            {
                return JSON.parseArray(data,CreditCardPaceBean.class);
            }
        };
        Map<String, Object> param = new HashMap<>();
        param.put("pageStart",0);
        param.put("pageSize",AppConstant.MAX_PAGE_NUM);
        request.setParam(param);

        request.sendRequestNew(new BaseCallBack<List<CreditCardPaceBean>>()
        {
            @Override
            public void onSuccess(List<CreditCardPaceBean> data)
            {
                dataList.clear();
                dataList.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Error error)
            {
                dataList.clear();
                adapter.notifyDataSetChanged();
                UiUtils.shortToast(error.getError());
            }
        });

    }

    private void initView()
    {
        setContentView(R.layout.activity_credit_card_pace);
        listView = (ListView) findViewById(R.id.listView);
    }

    class CreditCardPaceAdapterAbs extends AbsBaseListAdapter<CreditCardPaceBean>
    {

        public CreditCardPaceAdapterAbs(List<CreditCardPaceBean> list)
        {
            super(list);
        }

        @Override
        protected Holder getHolder(int position)
        {
            return new CreditCardPaceHolder();
        }
    }

    private class CreditCardPaceHolder extends Holder<CreditCardPaceBean>
    {
        private ImageView ivCard;
        private TextView tvCardName, tvSearch;

        @Override
        public View initView()
        {
            View view = UiUtils.inflate(R.layout.holder_credit_card_pace);
            ivCard = (ImageView) view.findViewById(R.id.ivCard);
            tvCardName = (TextView) view.findViewById(R.id.tvCardName);
            tvSearch = (TextView) view.findViewById(R.id.tvSearch);
            tvSearch.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", getData().getUrlSchedule());
                    UiUtils.readyGoWithBundle(WebViewClientActivity.class, bundle);
                }
            });
            return view;
        }

        @Override
        public void refreshView(CreditCardPaceBean data)
        {
            x.image().bind(ivCard, ServiceDispatcher.getImageUrl(data.getPhoto()));
            tvCardName.setText(data.getName());
        }
    }
}
