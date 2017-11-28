package com.yzb.card.king.ui.ticket.model.impl;

import android.app.Activity;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.discount.bean.AccountBalanceBean;
import com.yzb.card.king.util.LogUtil;
import com.yzb.wallet.logic.card.AccountBalanceLogic;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.Map;

/**
 * 功能：查询红包账号和礼品卡余额；
 *
 * @author:gengqiyun
 * @date: 2016/10/14
 */
public class AccountBalanceModel
{
    private Activity context;
    private BaseMultiLoadListener loadListener;

    public AccountBalanceModel(Activity context, BaseMultiLoadListener listener)
    {
        this.context = context;
        loadListener = listener;
    }

    public void loadData(Activity activity, Map<String, String> paramMap)
    {
        AccountBalanceLogic payHandle = new AccountBalanceLogic(activity);
        payHandle.balance(paramMap);
        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {
                LogUtil.i("=返回结果1=>code" + RESULT_CODE);
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                LogUtil.i("=返回结果2=>code" + RESULT_CODE + "返回数据=>" + resultMap);
                if (resultMap != null)
                {
                    AccountBalanceBean accountBean = JSON.parseObject(resultMap.get("data"), AccountBalanceBean.class);
                    if (loadListener != null)
                    {
                        loadListener.onListenSuccess(true, accountBean);
                    }
                }
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                LogUtil.i("=返回结果3=>code" + RESULT_CODE + "错误提示：" + ERROR_MSG);
                if (loadListener != null)
                {
                    loadListener.onListenError(ERROR_MSG);
                }
            }
        });
    }

}
