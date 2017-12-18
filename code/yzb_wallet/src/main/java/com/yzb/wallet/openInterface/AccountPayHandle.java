package com.yzb.wallet.openInterface;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.yzb.wallet.dialog.WalletOpen;
import com.yzb.wallet.dialog.WalletPwdDialog;
import com.yzb.wallet.sys.CardConstant;
import com.yzb.wallet.sys.ServiceDispatcher;
import com.yzb.wallet.util.DateUtil;
import com.yzb.wallet.util.HashUtil;
import com.yzb.wallet.util.StringUtil;
import com.yzb.wallet.util.WalletConstant;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户账户付款操作
 */
public class AccountPayHandle extends BasePay {

    private Activity activity;

    private WalletPwdDialog.Builder pwdDialog;

    private Map<String, String> params;

    public AccountPayHandle(Activity activity) {
        this.activity = activity;
    }

    /**
     * 支付操作(输入密码)
     */
    public void payPwd(Map<String, String> params) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        this.params = params;
        // 打开钱包loading
        WalletOpen.Builder walletOpen = new WalletOpen.Builder(activity);

        walletOpen.setDismiss(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                System.out.println("============>关闭loading");
                // 处理操作
                handle();
            }
        });

        walletOpen.loading();
    }

    /**
     * 支付操作(不输入密码)
     */
    public void pay(Map<String, String> params) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        // 参数校验
        String msg = checkParamsMsg(params);
        if (!StringUtil.isEmpty(msg)) {
            // 失败接口返回
            wbListener.setError(WalletConstant.CODE_INVALID, msg);
            return;
        }

        this.params = params;

        // 付款方式不同
        checkPayment();

    }

    /**
     * 收款
     * 1、获取付款账户
     * 2、转账
     */
    public void handle() {

        // 参数校验
        String msg = checkParamsMsg(params);
        if (!StringUtil.isEmpty(msg)) {
            // 失败接口返回
            wbListener.setError(WalletConstant.CODE_INVALID, msg);
            return;
        }

        // 验证支付密码
        OpenWalletPwdDialog();

    }

    /**
     * 检查参数
     */
    private String checkParamsMsg(Map<String, String> params) {

        if (StringUtil.isEmpty(params.get("customerId"))) {
            return "用户Id不能为空";
        }
        if (StringUtil.isEmpty(params.get("accountType"))) {
            return "账户类型不能为空";
        }
        if (StringUtil.isEmpty(params.get("payType"))) {
            return "账户类型不能为空";
        }
        String amount = params.get("amount");
        if (StringUtil.isEmpty(amount)) {
            return "金额不能为空";
        }
        if (new BigDecimal(amount).compareTo(new BigDecimal(WalletConstant.AMOUNT_ZERO)) == 0) {
            return "金额不能为零";
        }
        return "";
    }

    /**
     * 打开支付密码输入
     */
    private void OpenWalletPwdDialog() {

        pwdDialog = new WalletPwdDialog.Builder(activity);
        pwdDialog.setKeyBoardClick(new WalletPwdDialog.Builder.KeyBoardClickListener() {
            @Override
            public void backPassword(String pwd) {
                System.out.println("=========支付密码==>" + pwd);
                // 校验支付密码
                PayPwdHandle payHandle = new PayPwdHandle(activity);
                payHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                        pwdDialog.dismiss();
                        // 不同付款方式
                        checkPayment();
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        pwdDialog.dismiss();
                        wbListener.setError(RESULT_CODE, ERROR_MSG);
                    }
                });
                payHandle.validate(params.get("customerId"), HashUtil.getMD5(pwd));
            }
        });
        pwdDialog.create().show();
    }

    /**
     * 付款方式不同
     */
    private void checkPayment(){
        String accountType = params.get("accountType");
        if(WalletConstant.PAY_BANK.equals(accountType))
            payBank();// 银行卡付款
        else
            payAccount();// 账户付款
    }

    /**
     * 银行卡付款
     */
    private void payBank(){
        wbListener.setSuccess(WalletConstant.CODE_OK);
    }

    /**
     * 获取付款账户(根据用户id)
     */
    private void payAccount() {

        final Map<String, String> param = new HashMap<String, String>();

        param.put("customerId", params.get("customerId"));

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_api_account_list, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result == null || result.isEmpty()) {
                    wbListener.setError(WalletConstant.CODE_FAIL, WalletConstant.MSG_ERROR);
                } else if (!WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setError(result.get("code"), result.get("error"));
                } else if (WalletConstant.CODE_OK.equals(result.get("code"))) {
                    List<Map> list = JSON.parseArray(result.get("data"), Map.class);
                    // 判断是否有账户
                    if (list.size() == 0) {
                        wbListener.setError(WalletConstant.CODE_NULL, WalletConstant.ACCOUNT_NULL);
                    } else {
                        String accountId = "";
                        // 循环获取对应账户类型的账户
                        for (Map<String, Object> map : list) {
                            String accountType = String.valueOf(map.get("accountType"));
                            if (params.get("accountType").equals(accountType)) {
                                accountId = String.valueOf(map.get("accountId"));
                                continue;
                            }
                        }
                        // 判断是否有该类型账户
                        if (StringUtil.isEmpty(accountId)) {
                            wbListener.setError(WalletConstant.CODE_NULL, WalletConstant.ACCOUNT_NULL);
                        } else {
                            // 使用该账户付款
                            transfer(accountId);
                        }
                    }
                }
            }
        }.execute(JSON.toJSONString(param), null, null);
    }

    /**
     * 转账
     */
    private void transfer(String accountId) {

        Map<String, String> param = new HashMap<String, String>();
        param.put("creditId", WalletConstant.SYS_ACCOUNT(params.get("payType")));
        param.put("credit", WalletConstant.SYS_ACCOUNT(params.get("payType")));// 系统账户
        param.put("debitId", params.get("customerId"));
        param.put("debit", accountId);
        param.put("orderTime", DateUtil.date2String(new Date(), DateUtil.DEFAULT_DATE_FORMAT));
        param.put("amount", params.get("amount"));
        param.put("summary", params.get("summary"));
        param.put("batchNo", "");
        param.put("reserve", "");

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_api_account_transfer, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result == null || result.isEmpty()) {
                    wbListener.setError(WalletConstant.CODE_FAIL, WalletConstant.MSG_ERROR);
                } else if (!WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setError(result.get("code"), result.get("error"));
                } else if (WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setSuccess(result.get("code"));
                }
            }
        }.execute(JSON.toJSONString(param), null, null);

    }

}
