package com.yzb.card.king.ui.ticket.presenter;

import android.app.Activity;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.credit.activity.AddBankCardActivity;
import com.yzb.card.king.ui.credit.activity.AddCanPayCardActivity;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.ui.ticket.model.impl.DiscountPayModel;
import com.yzb.card.king.ui.ticket.view.DiscountPayView;

import java.util.Map;

/**
 * 功能：特惠付款-->付款；
 *
 * @author:gengqiyun
 * @date: 2016/10/14
 */
public class DiscountPayPresenter implements BaseMultiLoadListener,DataCallBack
{
    private DiscountPayModel model;

    private DiscountPayView view;

    private Activity context;

    public DiscountPayPresenter(Activity context, DiscountPayView view)
    {
        this.view = view;

        this.context = context;

        model = new DiscountPayModel(context, this,this);
    }

    public void loadData(Map<String, String> paramMap)
    {
        model.loadData(paramMap);
    }

    @Override
    public void onListenSuccess(boolean event_tag, Object data)
    {

            view.onPaySucess();
    }

    @Override
    public void onListenError(String msg)
    {
        view.onPayFail(msg);
    }

    @Override
    public void requestSuccess(Object o, int type)
    {

        String str = JSON.toJSONString(o);

        PayMethod accountInfo = JSON.parseObject(str , PayMethod.class);

        int cardType = accountInfo.getCardType();

        Class claz = null;

        if(cardType==1){// 储蓄卡

            claz = AddBankCardActivity.class;

        }else if(cardType ==2){//信用卡

            claz = AddCanPayCardActivity.class;

        }

        Intent intent = new Intent(context, claz);

        intent.putExtra("cardNo",accountInfo.getCardNo());

        intent.putExtra("name", accountInfo.getName());

        context.startActivity(intent);

    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {

    }
}
