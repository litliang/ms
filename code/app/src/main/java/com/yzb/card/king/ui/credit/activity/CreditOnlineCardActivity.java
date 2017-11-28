package com.yzb.card.king.ui.credit.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.SlideShow1ItemView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.adapter.OnLineCardAdapter;
import com.yzb.card.king.ui.credit.bean.Bank;
import com.yzb.card.king.ui.credit.bean.BestCard;
import com.yzb.card.king.ui.credit.holder.AdCardHolder;
import com.yzb.card.king.ui.credit.holder.BestCardHolder;
import com.yzb.card.king.ui.discount.bean.LbtBean;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.ViewUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在线办卡
 */
public class CreditOnlineCardActivity extends BaseActivity
{
    private GridView gridView;
    private ListView listView;
    private LinearLayout headerRight;
    private GridView gvAd;
    private AbsBaseListAdapter adAdapter;
    private List<LbtBean> adList = new ArrayList<>();
    private AbsBaseListAdapter hotAdapter;
    private List<BestCard> hotData = new ArrayList<>();
    private List<Bank> bankData = new ArrayList<>();
    private OnLineCardAdapter bankAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_online_card);
        initSlideShowView();

        initAd();
        //热门办卡推荐
        hotRecommend();

        //查询银行列表
        queryBank();

        //跳转到更多筛选页面
        intent();

    }

    private void initSlideShowView()
    {
        SlideShow1ItemView slideShowView = (SlideShow1ItemView) findViewById(R.id.slideShowView);
        if (slideShowView == null) return;
        float slide1_whrate = 432 / 1080.0f;
        int  screenWith = GlobalApp.getInstance().getAppBaseDataBean().getScreenWith();
        ViewUtil.set(slideShowView,
                screenWith,
                (int) (screenWith * slide1_whrate + 0.5));
        slideShowView.setParam(AppConstant.ONLINE_CARD_HOMEPAGE,cityId,"");
    }

    public void back(View view)
    {
        finish();
    }

    /**
     * 办卡进度
     *
     * @param view
     */
    public void cardPace(View view)
    {
        if (!UserManager.getInstance().isLogin())
        {
            startActivity(new Intent(CreditOnlineCardActivity.this, LoginActivity.class));
        } else
        {
            Intent intent = new Intent();
            intent.setClass(CreditOnlineCardActivity.this, CreditCardPaceActivity.class);
            startActivity(intent);
        }
    }

    private void initAd()
    {
        gvAd = (GridView) findViewById(R.id.gvAd);
        adAdapter = new AbsBaseListAdapter<LbtBean>(adList)
        {
            @Override
            protected Holder getHolder(int position)
            {
                return new AdCardHolder();
            }
        };
        gvAd.setAdapter(adAdapter);
        loadAd();
    }

    private void loadAd()
    {
        SimpleRequest<List<LbtBean>> request = new SimpleRequest<List<LbtBean>>("CarouselFigure")
        {
            @Override
            protected List<LbtBean> parseData(String data)
            {
                return JSON.parseArray(data, LbtBean.class);
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("pageCode",AppConstant.ONLINE_CARD_HOMEPAGE);
        param.put("category","2");//1.广告，2活动
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<List<LbtBean>>()
        {
            @Override
            public void onSuccess(List<LbtBean> data)
            {
                adList.clear();
                adList.addAll(data);
                adAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Error error)
            {
                adList.clear();
                adAdapter.notifyDataSetChanged();
                UiUtils.shortToast(error.getError());
            }
        });
    }

    private void intent()
    {
        //跳转到更多筛选
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent();

                if (position >= 7)
                {
                    intent.setClass(CreditOnlineCardActivity.this, BankListActivity.class);

                } else
                {
                    intent.setClass(CreditOnlineCardActivity.this, CreditMoreFilterActivity.class);
//                    long bankId = bankData.get(position).getBankId();
                    intent.putExtra("bank", bankData.get(position));
                }
                startActivity(intent);
            }
        });
    }


    private void queryBank()
    {
        gridView = (GridView) findViewById(R.id.onLineCardBank_gridView);
        bankAdapter = new OnLineCardAdapter(CreditOnlineCardActivity.this, bankData);
        gridView.setAdapter(bankAdapter);
        SimpleRequest<List<Bank>> request = new SimpleRequest<List<Bank>>("QueryHotBankService")
        {
            @Override
            protected List<Bank> parseData(String data)
            {
                return JSON.parseArray(data, Bank.class);
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("pageStart", 0);
        param.put("pageSize", 8);
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<List<Bank>>()
        {
            @Override
            public void onSuccess(List<Bank> data)
            {
                bankData.clear();
                bankData.addAll(data);
                bankAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFail(Error error)
            {
                bankData.clear();
                bankAdapter.notifyDataSetChanged();
                UiUtils.shortToast(error.getError());
            }
        });

    }

    //卡片之最
    private void hotRecommend()
    {

        listView = (ListView) findViewById(R.id.listView_hot_recommend);
        View footer = UiUtils.inflate(R.layout.footer_best_card);
        footer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(CreditOnlineCardActivity.this,CreditMoreFilterActivity.class));
            }
        });
        listView.addFooterView(footer);
        hotAdapter = new AbsBaseListAdapter<BestCard>(hotData){
            @Override
            protected Holder getHolder(int position)
            {
                return new BestCardHolder();
            }
        };
        listView.setAdapter(hotAdapter);
        SimpleRequest<List<BestCard>> request = new SimpleRequest<List<BestCard>>("QueryCreditArchivesWanted")
        {
            @Override
            protected List<BestCard> parseData(String data)
            {
                return JSON.parseArray(data, BestCard.class);
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("pageStart", 0);
        param.put("pageSize", 10);
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<List<BestCard>>()
        {
            @Override
            public void onSuccess(List<BestCard> data)
            {
                hotData.clear();
                hotData.addAll(data);
                hotAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Error error)
            {
                hotData.clear();
                hotAdapter.notifyDataSetChanged();
                UiUtils.shortToast(error.getError());
            }
        });

    }
}
