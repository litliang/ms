package com.yzb.card.king.http;

import android.app.Activity;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.user.AccountInfo;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.wallet.logic.comm.PaypswdAddLogic;
import com.yzb.wallet.logic.comm.PaypswdValidateLogic;
import com.yzb.wallet.openInterface.AccountHandle;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：yzb_wallet_sdk查询工具类
 * 作  者：Li Yubing
 * 日  期：2016/7/14
 * 描  述：使用yzb_wallet_sdk包，查询用户的积分、余额、礼品卡等信息
 */
public class WalletRequestUtil {

    private WalletRequest callBack;


    public WalletRequestUtil(WalletRequest request){

        this.callBack = request;
    }


    /**
     *  发起查询用户钱包信息请求，获取用户的礼品卡、余额、红包、用户积分
     * @param userId 用户id
     * @param activity
     */
    public  void sendChechUserWalletRequest(String userId,Activity activity) {

        sendUserMoneyRequest(userId, activity);
        sendUserRedPackRequest(userId, activity);
        sendUserGiftRequest(userId, activity);
        sendUserIntegralRequest(userId,activity);
    }

    /**
     * 发送用户余额请求
     * @param userId
     * @param activity
     */
    public  void sendUserMoneyRequest(String userId,Activity activity){
        //查询红包
        AccountHandle payHandle1 = new AccountHandle(activity);
        payHandle1.accountBalance(userId, "2");
        payHandle1.setCallBack(new WalletBackListener() {
            @Override
            public void setSuccess(String RESULT_CODE) {

            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                System.out.println("======查询红包=======>" + RESULT_CODE);
                System.out.println("======查询红包=======>" + resultMap);
                if (HttpConstant.SUCCESSCODE.equals(RESULT_CODE)) {
                    String data = resultMap.get("data");
                    // System.out.println("======获取余额=====2==>" + data);
                    AccountInfo accountInfo = JSON.parseObject(data, AccountInfo.class);

                    UserManager.getInstance().setRedMoneyAccountInfo(accountInfo);

                    if(callBack!=null){

                        callBack.userRedPackCallBack(null);
                    }
                }
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG) {
                System.out.println(RESULT_CODE+"======setError=====1==>" + ERROR_MSG);

            }
        });
    }
    /**
     * 发送用户红包请求
     * @param userId
     * @param activity
     */
    public void sendUserRedPackRequest(String userId,Activity activity){
        //查询钱包
        AccountHandle payHandle2 = new AccountHandle(activity);
        payHandle2.accountBalance(userId, "1");
        payHandle2.setCallBack(new WalletBackListener() {
            @Override
            public void setSuccess(String RESULT_CODE) {

            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                System.out.println("======查询钱包=======>" + RESULT_CODE);
                System.out.println("======查询钱包=======>" + resultMap);
                if (HttpConstant.SUCCESSCODE.equals(RESULT_CODE)) {
                    String data = resultMap.get("data");
                    // System.out.println("======获取余额=====2==>" + data);
                    AccountInfo accountInfo = JSON.parseObject(data, AccountInfo.class);

                    UserManager.getInstance().setMoneyAccountInfo(accountInfo);

                    if(callBack!=null){

                        callBack.userMoneyCallBack(null);
                    }
                }
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG) {
                System.out.println(RESULT_CODE+"======setError=====1==>" + ERROR_MSG);

            }
        });
    }
    /**
     * 发送用户礼品数请求
     * @param userId
     * @param activity
     */
    public void sendUserGiftRequest(String userId,Activity activity){
        //查询礼品卡
        AccountHandle payHandle3 = new AccountHandle(activity);
        payHandle3.accountBalance(userId, "3");
        payHandle3.setCallBack(new WalletBackListener() {
            @Override
            public void setSuccess(String RESULT_CODE) {

            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                System.out.println("======查询礼品卡=======>" + RESULT_CODE);
                System.out.println("======查询礼品卡=======>" + resultMap);
                if (HttpConstant.SUCCESSCODE.equals(RESULT_CODE)) {
                    String data = resultMap.get("data");
                    // System.out.println("======获取余额=====2==>" + data);
                    AccountInfo accountInfo = JSON.parseObject(data, AccountInfo.class);

                    UserManager.getInstance().setGiftAccountInfo(accountInfo);

                    if(callBack!=null){

                        callBack.userGiftCallBack(null);
                    }
                }
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG) {
                System.out.println(RESULT_CODE+"======setError=====1==>" + ERROR_MSG);

            }
        });
    }
    /**
     * 发送用户积分请求
     * @param userId
     * @param activity
     */
    public void sendUserIntegralRequest(String userId,Activity activity ){
        //查询用户积分
        AccountHandle payHandle = new AccountHandle(activity);
        payHandle.accountBalance(userId, "4");
        payHandle.setCallBack(new WalletBackListener() {
            @Override
            public void setSuccess(String RESULT_CODE) {

            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                System.out.println("======查询用户积分=======>" + RESULT_CODE);
                System.out.println("======查询用户积分=======>" + resultMap);
                if (HttpConstant.SUCCESSCODE.equals(RESULT_CODE)) {
                    String data = resultMap.get("data");
                    // System.out.println("======获取余额=====2==>" + data);
                    AccountInfo accountInfo = JSON.parseObject(data, AccountInfo.class);
                    UserManager.getInstance().setIntegrationNumeber(Long.parseLong(accountInfo
                            .getBalance()));

                    UserManager.getInstance().setAccountInfo(accountInfo);


                    if(callBack!=null){

                        callBack.userIntegralCallBack(null);
                    }
                }
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG) {
                System.out.println(RESULT_CODE+"======setError=====1==>" + ERROR_MSG);

            }
        });

    }

    /**
     * 检查用户的支付密码
     * @param amountAccount
     * @param activity
     */
    public void sendCheckUsePayPassword(String amountAccount, Activity activity, final Handler handler){

        Map<String, String> params = new HashMap<String, String>();

        params.put("mobile", amountAccount);

        params.put("merchantNo", WalletConstant.MERCHANT_NO);

        params.put("sign", getSign());

        PaypswdValidateLogic payHandle = new PaypswdValidateLogic(activity);

        payHandle.validate(params);

        payHandle.setCallBack(new WalletBackListener() {
            @Override
            public void setSuccess(String RESULT_CODE) {
                System.out.println("=返回结果=>code" + RESULT_CODE);
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG) {
                System.out.println("=返回结果=>code" + RESULT_CODE + "错误提示：" + ERROR_MSG);
                if("2002".equals(RESULT_CODE)){
                    handler.sendEmptyMessage(0);
                }
            }
        });
    }

    /**
     * 发送设置支付密码请求
     * @param amountAccount
     * @param newPayPassword
     * @param activity
     * @param handler
     */
    public void sendSetPayPassword(String amountAccount,String newPayPassword,Activity activity,final Handler handler){
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile",amountAccount);
        params.put("payPasswd", newPayPassword);
        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", getSign());

        PaypswdAddLogic payHandle = new PaypswdAddLogic(activity);
        payHandle.add(params);
        payHandle.setCallBack(new WalletBackListener() {
            @Override
            public void setSuccess(String RESULT_CODE) {
                System.out.println("=返回结果=>code" + RESULT_CODE);

                handler.sendEmptyMessage(0);
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                System.out.println("=返回结果=>code" + RESULT_CODE+"返回数据=>"+resultMap);
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG) {
                System.out.println("=返回结果=>code" + RESULT_CODE + "错误提示：" + ERROR_MSG);
                handler.sendEmptyMessage(1);
            }
        });


    }

    public String getSign(){

        return  AppConstant.sign;
    }


      public interface WalletRequest{


          public void  userIntegralCallBack( Object o);

          public void userGiftCallBack( Object o);

          public void userMoneyCallBack( Object o );

          public void userRedPackCallBack(Object o);

    }


}
