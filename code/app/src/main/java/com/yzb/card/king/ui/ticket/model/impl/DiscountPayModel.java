package com.yzb.card.king.ui.ticket.model.impl;

import android.app.Activity;
import android.content.Intent;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.my.activity.AddCardAllActivity;
import com.yzb.card.king.util.LogUtil;
import com.yzb.wallet.logic.pay.PayRequestLogic;
import com.yzb.wallet.openInterface.AddCardBackListener;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.Map;

/**
 * 功能：特惠付款-->付款；
 *
 * @author:gengqiyun
 * @date: 2016/10/14
 */
public class DiscountPayModel
{
    private final Activity context;

    private BaseMultiLoadListener loadListener;

    private DataCallBack dataCallBack;

    public DiscountPayModel(Activity context, BaseMultiLoadListener listener,DataCallBack dataCallBack)
    {
        this.context = context;
        loadListener = listener;
        this.dataCallBack = dataCallBack;
    }


    public DiscountPayModel(Activity context, BaseMultiLoadListener listener)
    {
        this.context = context;
        loadListener = listener;
    }

    public void loadData(Map<String, String> paramMap)
    {
        PayRequestLogic payHandle = new PayRequestLogic(context);
        //存在说明不需要显示付款详情；
        payHandle.showPayInfo(!paramMap.containsKey("showPayInfo"));
        payHandle.showGiftPay(paramMap.containsKey("showGiftcard"));
        payHandle.showEnvelopPay(paramMap.containsKey("showBouns"));

        //添加卡；
        payHandle.setAddCardCallBack(new AddCardBackListener()
        {
            @Override
            public void callBack()
            {
                if (context != null)
                {
                    Intent intent = new Intent(context, AddCardAllActivity.class);
                    intent.putExtra(AddCardAllActivity.BUSINESS_ADD_CARD,AddCardAllActivity.ALL_BUSINESS_VALUE);
                    context.startActivity(intent);
                }
            }
        });

        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {
                if (loadListener != null)
                {
                    loadListener.onListenSuccess(true, null);
                }
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                LogUtil.i("=返回结果111=>code" + RESULT_CODE + "返回数据=>" + resultMap);

                if(RESULT_CODE.equals( com.yzb.wallet.util.WalletConstant.PAY_TYPE_OFF)) {// 支付卡信息不全

                    if(dataCallBack != null){

                        dataCallBack.requestSuccess(resultMap,-1);
                    }


                }else{
                    if (loadListener != null)
                    {
                        loadListener.onListenSuccess(true, resultMap);
                    }
                }

            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                LogUtil.i("付款失败；RESULT_CODE=" + RESULT_CODE + ",ERROR_MSG=" + ERROR_MSG);
                if (loadListener != null)
                {
                    loadListener.onListenError(ERROR_MSG);
                }
            }
        });
        payHandle.pay(paramMap, false);
    }

}
