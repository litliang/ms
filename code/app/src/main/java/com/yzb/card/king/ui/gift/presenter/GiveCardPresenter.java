package com.yzb.card.king.ui.gift.presenter;

import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.gift.modle.GiveCardModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：赠送礼品卡；
 *
 * @author:gengqiyun
 * @date: 2016/12/22
 */
public class GiveCardPresenter implements BaseMultiLoadListener
{
    private GiveCardModel model;
    private BaseViewLayerInterface view;
    private int requestIndex;

    public GiveCardPresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        model = new GiveCardModel(this);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        requestIndex = 1;
        model.loadData(true, paramMap);
    }

    /**
     * 检查账户请求
     * @param accoutStr
     */
    public void checkAccoutRequest(String accoutStr){


        requestIndex = 2;

        Map<String, Object> args = new HashMap<>();

        args.put("searchName", accoutStr);

        args.put("customerType", "P");

        args.put("customerMod", "2");

        args.put("pageStart", 0);

        args.put("pageSize", 20);

        args.put("serviceName", CardConstant.USER_QUERYPLATFORMACCOUNT);

        model.loadData(true, args);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {
        view.callSuccessViewLogic(data,requestIndex);
    }

    @Override
    public void onListenError(String msg)
    {
        view.callFailedViewLogic(msg,requestIndex);
    }
}
