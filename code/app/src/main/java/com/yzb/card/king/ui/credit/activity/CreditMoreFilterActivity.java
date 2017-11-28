package com.yzb.card.king.ui.credit.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.appwidget.SmoothListView.SmoothListView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.bean.Bank;
import com.yzb.card.king.ui.credit.bean.CardLevel;
import com.yzb.card.king.ui.credit.bean.CardOnline;
import com.yzb.card.king.ui.credit.bean.CardType;
import com.yzb.card.king.ui.credit.bean.OnLineCardPopData;
import com.yzb.card.king.ui.credit.holder.CardOnLineHolder;
import com.yzb.card.king.ui.credit.interfaces.IOnlineCardPop;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.credit.popup.BankListPop;
import com.yzb.card.king.ui.credit.popup.CardLevelPop;
import com.yzb.card.king.ui.credit.popup.CardTypePop;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 更多筛选在线办卡
 */
public class CreditMoreFilterActivity extends BaseActivity implements SmoothListView.ISmoothListViewListener
{
    private SmoothListView listView;
    private View allBankLayout;
    private View cardTypeyLayout;
    private View cardLevelLayout;
    private AbsBaseListAdapter adapter;
    private List<CardOnline> cardList = new ArrayList<>();
    private Bank bank;
    private String publicityId;//宣传id
    private Long purposeId;//办卡用途id
    private Long cardRank;//卡等级
    private CardLevelPop cardLevelPop;
    private CardTypePop cardTypePop;
    private BankListPop bankListPop;
    private PopupWindow pop;
    private int pageStart = 0;
    private TextView tvAllBank;
    private TextView tvCardLevel;
    private TextView tvCardType;
    private View.OnClickListener popListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if (pop != null && pop.isShowing())
            {
                pop.dismiss();
            }
            switch (v.getId())
            {
                case R.id.allBank_layout:
                    pop = bankListPop;
                    break;
                case R.id.cardGrade_layout:
                    pop = cardLevelPop;
                    break;
                case R.id.cardUse_layout:
                    pop = cardTypePop;
                    break;
            }
            pop.showAsDropDown(v);
        }
    };

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
        setContentView(R.layout.credit_more_filter);
        listView = (SmoothListView) findViewById(R.id.listView_more_screening_bank);
        allBankLayout = findViewById(R.id.allBank_layout);
        cardTypeyLayout = findViewById(R.id.cardUse_layout);
        cardLevelLayout = findViewById(R.id.cardGrade_layout);
        tvAllBank = (TextView) findViewById(R.id.tvAllBank);
        tvCardType = (TextView) findViewById(R.id.tvCardType);
        tvCardLevel = (TextView) findViewById(R.id.tvCardLevel);
    }

    private void initListener()
    {
        switchContentLeft(AppConstant.RES_HOME_PAGE);

        listView.setSmoothListViewListener(this);
        //触发全部银行按钮
        allBankLayout.setOnClickListener(popListener);

        //触发办卡用处按钮
        cardTypeyLayout.setOnClickListener(popListener);

        //触发办卡等级按钮
        cardLevelLayout.setOnClickListener(popListener);

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

    private void initData()
    {
        setTitleNmae("在线办卡列表");
        getIntentData();
        initPops();
        setAdapter();
        loadData(true);
        loadPopData();
    }

    private void initPops()
    {
        if (cardLevelPop == null)
        {
            cardLevelPop = new CardLevelPop();
            cardLevelPop.setListener(new OnItemClickListener<IOnlineCardPop>()
            {
                @Override
                public void onItemClick(IOnlineCardPop data)
                {
                    cardRank = data.id();
                    tvCardLevel.setText(data.name());
                    loadData(true);
                }
            });
        }
        if (cardTypePop == null)
        {
            cardTypePop = new CardTypePop();
            cardTypePop.setListener(new OnItemClickListener<IOnlineCardPop>()
            {
                @Override
                public void onItemClick(IOnlineCardPop data)
                {
                    purposeId = data.id();
                    tvCardType.setText(data.name());
                    loadData(true);
                }
            });
        }

        if (bankListPop == null)
        {
            bankListPop = new BankListPop();
            bankListPop.setListener(new OnItemClickListener<IOnlineCardPop>()
            {
                @Override
                public void onItemClick(IOnlineCardPop data)
                {
                    bank = (Bank) data;
                    tvAllBank.setText(data.name());
                    loadData(true);
                }
            });
        }
    }

    private void loadPopData()
    {
        SimpleRequest<OnLineCardPopData> request = new SimpleRequest<OnLineCardPopData>("QueryOnlineFilter")
        {
            @Override
            protected OnLineCardPopData parseData(String data)
            {
                return JSON.parseObject(data, OnLineCardPopData.class);
            }
        };
        request.sendRequestNew(new BaseCallBack<OnLineCardPopData>()
        {
            @Override
            public void onSuccess(OnLineCardPopData data)
            {
                data.getBankList().add(0, new Bank(null, "全部银行"));
                data.getPurposeList().add(0, new CardType(null, "不限", null));
                data.getRankList().add(0, new CardLevel(null, "不限"));
                bankListPop.setData(data.getBankList());
                cardTypePop.setData(data.getPurposeList());
                cardLevelPop.setData(data.getRankList());
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }

    private void getIntentData()
    {
        bank = (Bank) getIntent().getSerializableExtra("bank");
        publicityId = getIntent().getStringExtra("publicityId");

        if (bank != null)
        {
            tvAllBank.setText(bank.name());
        }
    }

    private void setAdapter()
    {
        adapter = new AbsBaseListAdapter<CardOnline>(cardList)
        {
            @Override
            protected Holder getHolder(int position)
            {
                return new CardOnLineHolder();
            }
        };
        listView.setAdapter(adapter);
    }

    private void loadData(final boolean isRefresh)
    {
        //查询所有在线信用卡办卡列表
        SimpleRequest<List<CardOnline>> request
                = new SimpleRequest<List<CardOnline>>("QueryCreditArchives")
        {
            @Override
            protected List<CardOnline> parseData(String data)
            {
                return JSON.parseArray(data, CardOnline.class);
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("bankId", bank == null ? null : bank.id());
        param.put("purposeId", purposeId);
        param.put("cardRank", cardRank);
        param.put("ProId", publicityId);
        param.put("pageStart", pageStart);
        param.put("pageSize", AppConstant.MAX_PAGE_NUM);
        request.setParam(param);

        request.sendRequestNew(new BaseCallBack<List<CardOnline>>()
        {
            @Override
            public void onSuccess(List<CardOnline> data)
            {
                if (isRefresh)
                {
                    cardList.clear();
                    listView.stopRefresh();
                } else
                {
                    listView.stopLoadMore();
                }
                if (data == null || data.size() <= AppConstant.MAX_PAGE_NUM)
                {
//                    listView.setLoadMoreEnable(false);
                } else
                {
                    listView.setLoadMoreEnable(true);
                }
                cardList.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Error error)
            {
                cardList.clear();
                adapter.notifyDataSetChanged();
                listView.setLoadMoreEnable(false);
            }
        });

    }

    @Override
    public void onRefresh()
    {
        pageStart = 0;
        loadData(true);
    }

    @Override
    public void onLoadMore()
    {
        pageStart += AppConstant.MAX_PAGE_NUM;
        loadData(false);
    }
}
