package com.yzb.card.king.ui.integral.model.impl;

import android.app.Activity;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.integral.AccountSchemaBean;
import com.yzb.card.king.bean.user.AccountInfo;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.HttpCallBackImpl;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.integral.model.IAccountSchema;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.wallet.openInterface.AccountHandle;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/12 11:14
 */
public class AccountSchemaImpl implements IAccountSchema
{
    private HttpCallBackData callBack;
    private WalletRequest walletCallBck;

    public AccountSchemaImpl(HttpCallBackData callBack, WalletRequest walletCallBck)
    {
        this.callBack = callBack;
        this.walletCallBck = walletCallBck;
    }

    @Override
    public void loadData()
    {
        Map<String, Object> map = new HashMap<>();
        SimpleRequest request = new SimpleRequest(CardConstant.INTEGRAL_ACCOUNTSUMMARY, map);
        request.sendRequest(new HttpCallBackImpl()
        {
            @Override
            public void onSuccess(Object o)
            {
                Map<String, String> resultMap = (Map<String, String>) o;
                String data = resultMap.get("data");
                String sessionId = resultMap.get("sessionId");
                AppConstant.handleSessionId(sessionId);
                AccountSchemaBean userBean = JSON.parseObject(data, AccountSchemaBean.class);
                if (callBack != null)
                {
                    callBack.onSuccess(userBean);
                }
            }

            @Override
            public void onFailed(Object o)
            {
                if (callBack != null)
                {
                    callBack.onFailed(o);
                }
            }
        });
    }

    /**
     * 发送用户余额请求
     *
     * @param userId
     * @param activity
     */
    public void sendUserMoneyRequest(String userId, Activity activity)
    {
        //查询红包
        AccountHandle payHandle1 = new AccountHandle(activity);
        payHandle1.accountBalance(userId, "2");
        payHandle1.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {

            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                System.out.println("======查询红包=======>" + RESULT_CODE);
                System.out.println("======查询红包=======>" + resultMap);
                if (HttpConstant.SUCCESSCODE.equals(RESULT_CODE))
                {
                    String data = resultMap.get("data");
                    // System.out.println("======获取余额=====2==>" + data);
                    AccountInfo accountInfo = JSON.parseObject(data, AccountInfo.class);

                    UserManager.getInstance().setRedMoneyAccountInfo(accountInfo);

                    if (walletCallBck != null)
                    {

                        walletCallBck.userRedPackCallBack(null);
                    }
                }
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                System.out.println(RESULT_CODE + "======setError=====1==>" + ERROR_MSG);

            }
        });
    }

    /**
     * 发送用户红包请求
     *
     * @param userId
     * @param activity
     */
    public void sendUserRedPackRequest(String userId, Activity activity)
    {
        //查询钱包
        AccountHandle payHandle2 = new AccountHandle(activity);
        payHandle2.accountBalance(userId, "1");
        payHandle2.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {

            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                System.out.println("======查询钱包=======>" + RESULT_CODE);
                System.out.println("======查询钱包=======>" + resultMap);
                if (HttpConstant.SUCCESSCODE.equals(RESULT_CODE))
                {
                    String data = resultMap.get("data");
                    // System.out.println("======获取余额=====2==>" + data);
                    AccountInfo accountInfo = JSON.parseObject(data, AccountInfo.class);

                    UserManager.getInstance().setMoneyAccountInfo(accountInfo);

                    if (walletCallBck != null)
                    {

                        walletCallBck.userMoneyCallBack(null);
                    }
                }
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                System.out.println(RESULT_CODE + "======setError=====1==>" + ERROR_MSG);

            }
        });
    }

    /**
     * 发送用户礼品数请求
     *
     * @param userId
     * @param activity
     */
    public void sendUserGiftRequest(String userId, Activity activity)
    {
        //查询礼品卡
        AccountHandle payHandle3 = new AccountHandle(activity);
        payHandle3.accountBalance(userId, "3");
        payHandle3.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {

            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                System.out.println("======查询礼品卡=======>" + RESULT_CODE);
                System.out.println("======查询礼品卡=======>" + resultMap);
                if (HttpConstant.SUCCESSCODE.equals(RESULT_CODE))
                {
                    String data = resultMap.get("data");
                    // System.out.println("======获取余额=====2==>" + data);
                    AccountInfo accountInfo = JSON.parseObject(data, AccountInfo.class);

                    UserManager.getInstance().setGiftAccountInfo(accountInfo);

                    if (walletCallBck != null)
                    {

                        walletCallBck.userGiftCallBack(null);
                    }
                }
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                System.out.println(RESULT_CODE + "======setError=====1==>" + ERROR_MSG);

            }
        });
    }

    /**
     * 发送用户积分请求
     *
     * @param userId
     * @param activity
     */
    public void sendUserIntegralRequest(String userId, Activity activity)
    {
        //查询用户积分
        AccountHandle payHandle = new AccountHandle(activity);
        payHandle.accountBalance(userId, "4");
        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {

            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                System.out.println("======查询用户积分=======>" + RESULT_CODE);
                System.out.println("======查询用户积分=======>" + resultMap);
                if (HttpConstant.SUCCESSCODE.equals(RESULT_CODE))
                {
                    String data = resultMap.get("data");
                    // System.out.println("======获取余额=====2==>" + data);
                    AccountInfo accountInfo = JSON.parseObject(data, AccountInfo.class);

                    UserManager.getInstance().setIntegrationNumeber(Long.parseLong(accountInfo
                            .getBalance()));

                    UserManager.getInstance().setAccountInfo(accountInfo);


                    if (walletCallBck != null)
                    {

                        walletCallBck.userIntegralCallBack(null);
                    }
                }
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                System.out.println(RESULT_CODE + "======setError=====1==>" + ERROR_MSG);

            }
        });

    }

    public interface WalletRequest
    {

        public void userIntegralCallBack(Object o);

        public void userGiftCallBack(Object o);

        public void userMoneyCallBack(Object o);

        public void userRedPackCallBack(Object o);


    }

}
