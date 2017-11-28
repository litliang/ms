package com.yzb.card.king.ui.app.model;

import android.app.Activity;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.util.LogUtil;
import com.yzb.wallet.logic.comm.PayMethodAddLogic;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.Map;

/**
 * 功能：更新支付顺序
 *
 * @author:gengqiyun
 * @date: 2016/9/12
 */
public class UpdatePayOrderModel
{
    private Activity context;
    private BaseMultiLoadListener loadListener;

    public UpdatePayOrderModel(Activity context, BaseMultiLoadListener loadListener)
    {
        this.context = context;
        this.loadListener = loadListener;
    }

    public void loadData(Map<String, String> paramMap)
    {
        PayMethodAddLogic payHandle = new PayMethodAddLogic(context);
        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {
                if (loadListener != null)
                {
                    loadListener.onListenSuccess(true, RESULT_CODE);
                }
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                if (loadListener != null)
                {
                    loadListener.onListenSuccess(true, RESULT_CODE);
                }
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                if (loadListener != null)
                {
                    loadListener.onListenError(ERROR_MSG);
                }
            }
        });
        payHandle.addPayMethod(paramMap);
        LogUtil.e("保存支付顺序:" + JSON.toJSONString(paramMap));
    }
}
