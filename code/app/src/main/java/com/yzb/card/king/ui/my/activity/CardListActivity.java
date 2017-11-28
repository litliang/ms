package com.yzb.card.king.ui.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.bean.CardInfo;
import com.yzb.card.king.ui.my.holder.MyCardHolder;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.UiUtils;
import com.yzb.wallet.logic.bank.bankListLogic;
import com.yzb.wallet.openInterface.WalletBackListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_card_list)
public class CardListActivity extends BaseActivity
{
    public static final int REQ_ADD_CARD = 2;

    @ViewInject(R.id.listView)
    private ListView listView;

    private View llAddCard;
    private AbsBaseListAdapter adapter;
    private List<CardInfo> cardList = new ArrayList<>();

    @Override
    protected void onRestart()
    {
        super.onRestart();
        loadData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    private void initView()
    {
        setTitleNmae("银行卡");

        View footer = UiUtils.inflate(R.layout.footer_add_card_my);
        llAddCard = footer.findViewById(R.id.llAddCard);
        listView.addFooterView(footer);
    }

    private void initListener()
    {
        llAddCard.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CardListActivity.this, AddCardAllActivity.class);
                intent.putExtra(AddCardAllActivity.BUSINESS_ADD_CARD,AddCardAllActivity.ALL_BUSINESS_VALUE);
                startActivityForResult(intent
                        , REQ_ADD_CARD);
            }
        });
    }

    private void initData()
    {
        setAdapter();
        loadData();
    }

    private void loadData()
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", UserManager.getInstance().getUserBean().getAccount());
        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", WalletConstant.SIGN);
        bankListLogic payHandle = new bankListLogic(CardListActivity.this);
        payHandle.getData(params);
        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                cardList.clear();
                cardList.addAll(JSON.parseArray(resultMap.get("data"), CardInfo.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                System.out.println("=返回结果=>code" + RESULT_CODE + "错误提示：" + ERROR_MSG);
                UiUtils.shortToast(ERROR_MSG);
                cardList.clear();
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void setAdapter()
    {
        adapter = new AbsBaseListAdapter<CardInfo>(cardList)
        {
            @Override
            protected Holder getHolder(final int position)
            {
                MyCardHolder myCardHolder = new MyCardHolder();
                myCardHolder.setDeleteListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        deleteCard(cardList.get(position));
                    }
                });
                return myCardHolder;
            }
        };
        listView.setAdapter(adapter);
    }

    private void deleteCard(final CardInfo cardInfo)
    {
        SimpleRequest<String> request = new SimpleRequest<String>(CardConstant.RELIEVE_CARD)
        {
            @Override
            protected String parseData(String data)
            {
                return data;
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("mobile",UserManager.getInstance().getUserBean().getAccount());
        param.put("sortCode",cardInfo.getSortCode());
        param.put("cardType",cardInfo.getCardType());
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<String>()
        {
            @Override
            public void onSuccess(String data)
            {
                cardList.remove(cardInfo);
                listView.setAdapter(adapter);
                setResult(RESULT_OK);
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case REQ_ADD_CARD:
                    loadData();
                    setResult(RESULT_OK);
                    break;
            }
        }
    }
}
