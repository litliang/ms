package com.yzb.card.king.ui.app.model;

import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.ui.app.interfaces.PayMethodsListener;
import com.yzb.card.king.ui.credit.activity.PayMethodActivity;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.util.LogUtil;
import com.yzb.wallet.logic.comm.PayMethodDefaultLogic;
import com.yzb.wallet.logic.comm.PayMethodSeqLogic;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.Map;

/**
 * 功能：支付方式
 *
 * @author:gengqiyun
 * @date: 2016/9/12
 */
public class PayMethodsModel
{
    private Activity context;
    private PayMethodsListener loadListener;

    public PayMethodsModel(Activity context, PayMethodsListener loadListener)
    {
        this.context = context;
        this.loadListener = loadListener;
    }

    public void loadData(Map<String, String> paramMap)
    {
        //0:默认；1：有序；
        String paymethodStatus = paramMap.get(PayMethodActivity.PAYMETHOD_STATUS);
        paramMap.remove(PayMethodActivity.PAYMETHOD_STATUS);

        if (PayMethodActivity.PAYMETHOD_TYPE_USER.equals(paymethodStatus))
        {
            getSeqPaymethods(paramMap, paymethodStatus);
        } else
        {
            getDefaultPaymethods(paramMap, paymethodStatus);
        }
    }

    private void getSeqPaymethods(Map<String, String> paramMap, final String paymethodStatus)
    {
        PayMethodSeqLogic payHandle = new PayMethodSeqLogic(context);
        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {
                LogUtil.i("=返回结果=>code" + RESULT_CODE);
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                LogUtil.i("==========付款方式列表==========" + JSON.toJSONString(resultMap));
                String data = resultMap.get("data");
                if (!TextUtils.isEmpty(data) && loadListener != null)
                {
                    loadListener.onListenSuccess(paymethodStatus, JSON.parseArray(data, PayMethod.class));
                }
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                LogUtil.i("======付款方式=======>" + RESULT_CODE);
                if (loadListener != null)
                {
                    loadListener.onListenError(ERROR_MSG);
                }
            }
        });
        payHandle.payMethod(paramMap);
    }

    private void getDefaultPaymethods(Map<String, String> paramMap, final String paymethodStatus)
    {
        PayMethodDefaultLogic payHandle = new PayMethodDefaultLogic(context);
        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {
                LogUtil.i("=返回结果=>code" + RESULT_CODE);
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                LogUtil.i("======付款方式=======>" + resultMap);
                String data = resultMap.get("data");
                if (!TextUtils.isEmpty(data) && loadListener != null)
                {
                    loadListener.onListenSuccess(paymethodStatus, JSON.parseArray(data, PayMethod.class));
                }
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                LogUtil.i("=返回结果=>code" + RESULT_CODE + "错误提示：" + ERROR_MSG);
                if (loadListener != null)
                {
                    loadListener.onListenError(ERROR_MSG);
                }
            }
        });
        payHandle.setAty(context);
        payHandle.payMethod(paramMap);
    }
}
