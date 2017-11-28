package com.yzb.card.king.ui.app.model;

import android.app.Activity;

import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.base.ExistPayPwdListener;
import com.yzb.card.king.util.LogUtil;
import com.yzb.wallet.logic.comm.PaypswdValidateLogic;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.Map;

/**
 * 功能：支付密码验证；
 *
 * @author:gengqiyun
 * @date: 2016/9/12
 */
public class ExistPayPwdModel
{
    private Activity context;
    private ExistPayPwdListener loadListener;

    public ExistPayPwdModel(Activity context, ExistPayPwdListener loadListener)
    {
        this.context = context;
        this.loadListener = loadListener;
    }

    public void loadData(Map<String, String> paramMap)
    {
        PaypswdValidateLogic payHandle = new PaypswdValidateLogic(context);
        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {
                //查询成功  支付密码存在；
                if (loadListener != null)
                {
                    loadListener.onListenSuccess();
                }
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                //查询成功  支付密码存在；
                if (loadListener != null)
                {
                    loadListener.onListenSuccess();
                }
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                LogUtil.i("支付密码RESULT_CODE=" + RESULT_CODE + ",ERROR_MSG=" + ERROR_MSG);
                String errorMsg;
                switch (RESULT_CODE)
                {
                    case AppConstant.CODE_PAY_PWD_NO_EXIST:
//                        errorMsg = "您还没有设置支付密码";
                        errorMsg = ERROR_MSG;
                        break;
                    case AppConstant.CODE_PAY_PWD_VERIFY_FAIL:
//                        errorMsg = "支付密码验证失败";
                        errorMsg = ERROR_MSG;
                        break;
                    default:
                        errorMsg = ERROR_MSG;
                        break;
                }
                if (loadListener != null)
                {
                    loadListener.onListenError(RESULT_CODE, errorMsg);
                }
            }
        });
        payHandle.validate(paramMap);
    }
}
