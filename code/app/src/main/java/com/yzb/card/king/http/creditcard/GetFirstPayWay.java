package com.yzb.card.king.http.creditcard;

import android.app.Activity;

import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.app.interfaces.PayMethodsListener;
import com.yzb.card.king.ui.app.model.PayMethodsModel;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.bean.common.PayMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：获取默认支付方式
 * 作者：殷曙光
 * 日期：2017/2/17 12:06
 */
public class GetFirstPayWay implements PayMethodsListener
{
    public static final int CREDIT = 2;
    public static final int DEBIT = 1;

    private final OnPayWayGetListener listener;
    private Integer type;

    public GetFirstPayWay(Activity context, Integer type, OnPayWayGetListener listener)
    {
        this.type = type;
        this.listener = listener;
        if (type == null || listener == null) return;
        sendRequest(context);
    }

    private void sendRequest(Activity context)
    {
        PayMethodsModel model = new PayMethodsModel(context, this);
        Map<String, String> params = new HashMap<>();
        params.put("mobile", UserManager.getInstance().getUserBean().getAccount());
        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", AppConstant.sign);
        params.put("paymethodStatus", UserManager.getInstance().getUserBean().getPaymentStatus());//用来标志是有序还是无序；0:默认；1：有序；
        model.loadData(params);
    }

    @Override
    public void onListenSuccess(String tag, List<PayMethod> data)
    {
        if (data != null && data.size() > 0)
        {
            for (int i = 0; i < data.size(); i++)
            {
                PayMethod method = data.get(i);
                if (method.getCardType() != null && type.intValue() == method.getCardType().intValue())
                {
                    listener.onGet(method);
                    return;
                }
            }
        }
        listener.onFail("没有相关支付方式");
    }

    @Override
    public void onListenError(String failMsg)
    {
        listener.onFail(failMsg);
    }

    public interface OnPayWayGetListener
    {
        void onGet(PayMethod payMethod);

        void onFail(String msg);
    }
}
