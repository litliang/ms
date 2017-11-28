package com.yzb.card.king.ui.luxury.model.impl;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.appwidget.popup.BankListLoadListener;
import com.yzb.card.king.ui.discount.bean.BankBean;
import com.yzb.card.king.ui.luxury.model.BankMenuModel;

import java.util.List;
import java.util.Map;

/**
 * 功能：银行列表
 *
 * @author:gengqiyun
 * @date: 2016/9/23
 */
public class BankMenuModelImpl implements BankMenuModel
{
    private BankListLoadListener loadListener;

    public BankMenuModelImpl(BankListLoadListener loadListener)
    {
        this.loadListener = loadListener;
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        new SimpleRequest(CardConstant.card_app_banklist, paramMap, new BaseRequest.Listener()
        {
            @Override
            public void onResult(String result)
            {
                if (!TextUtils.isEmpty(result))
                {
                    Map<String, String> map = JSON.parseObject(result, Map.class);
                    if (map != null && AppConstant.CODE_OK.equals(map.get("code")))
                    {
                        List<BankBean> myBanks = JSON.parseArray(map.get("myBanks"), BankBean.class);
                        List<BankBean> allBanks = JSON.parseArray(map.get("allBanks"), BankBean.class);
                        if (loadListener != null)
                        {
                            loadListener.onListenSuccess(myBanks, allBanks);
                        }
                    }
                }
            }

            @Override
            public void onFail(String failMsg)
            {
                if (loadListener != null)
                {
                    loadListener.onListenError(failMsg);
                }
            }
        }).sendPostRequest();
    }
}
