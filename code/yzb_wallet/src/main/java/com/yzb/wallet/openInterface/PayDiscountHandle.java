package com.yzb.wallet.openInterface;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.wallet.dialog.WalletOpen;
import com.yzb.wallet.dialog.WalletPayInfoDialog;
import com.yzb.wallet.dialog.WalletPwdDialog;
import com.yzb.wallet.dialog.WalletToastCustom;
import com.yzb.wallet.dialog.WalletVerifyCodeDialog;
import com.yzb.wallet.sys.CardConstant;
import com.yzb.wallet.sys.ServiceDispatcher;
import com.yzb.wallet.util.DateUtil;
import com.yzb.wallet.util.HashUtil;
import com.yzb.wallet.util.StringUtil;
import com.yzb.wallet.util.WalletConstant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 特惠付款
 */
public class PayDiscountHandle extends BasePay {

    private Activity activity;

    private WalletPwdDialog.Builder pwdDialog;

    private Map<String, String> params;
    private String accountType;//支付方式

    public PayDiscountHandle(Activity activity) {
        this.activity = activity;
    }

    /**
     * 支付操作
     */
    public void pay(Map<String, String> params) {

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
     * 支付处理
     */
    private void handle() {

        if (params.isEmpty()) return;
        // 检查参数
        String msg = checkParamsMsg(params);
        if (!StringUtil.isEmpty(msg)) {
            // 失败接口返回
            wbListener.setError(WalletConstant.CODE_INVALID, msg);
            return;
        }
        accountType = params.get("accountType");
        if (WalletConstant.PAY_BANK.equals(accountType)) {
            //TODO 银行卡支付,默认成功
            wbListener.setSuccess(WalletConstant.CODE_OK);
        } else {
            // 打开支付密码验证
            OpenWalletPwdDialog();
        }
    }

    /**
     * 检查参数
     */
    private String checkParamsMsg(Map<String, String> params) {

        if (StringUtil.isEmpty(params.get("customerId"))) {
            return "用户id不能为空";
        }
        if (StringUtil.isEmpty(params.get("accountType"))) {
            return "付款方式不能为空";
        }
        if (StringUtil.isEmpty(params.get("amount"))) {
            return "金额不能为空";
        }
        if (StringUtil.isEmpty(params.get("summary"))) {
            return "摘要不能为空";
        }
        return "";
    }
    /**
     * 打开支付密码输入
     */
    private void OpenWalletPwdDialog() {

        pwdDialog = new WalletPwdDialog.Builder(activity);
        pwdDialog.setForgetPWdCallBack(forgetPwdListener);
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
                        // 获取付款账户(根据用户id)
                        payAccount();
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
                            // 转账-红包账户
                            transferBonus();
                            // 转账-礼品卡账户
                            transferGiftCard();
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
        param.put("creditId", WalletConstant.SYS_ACCOUNT_FEE);
        param.put("credit", WalletConstant.SYS_ACCOUNT_FEE);// 系统收费账户
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

    /**
     * 转账-红包账户
     */
    private void transferBonus() {

        String bonusAccount = params.get("bonusAccount");
        String bonusAmount = params.get("bonusAmount");

        System.out.println("===红包支付===>" + bonusAccount + "," + bonusAmount);

        if (StringUtil.isEmpty(bonusAccount))
            return;

        if (StringUtil.isEmpty(bonusAmount))
            return;

        if (new BigDecimal(bonusAmount).compareTo(new BigDecimal(WalletConstant.AMOUNT_ZERO)) == 0)
            return;

        Map<String, String> param = new HashMap<String, String>();

        param.put("creditId", WalletConstant.SYS_ACCOUNT_FEE);
        param.put("credit", WalletConstant.SYS_ACCOUNT_FEE);// 系统收费账户
        param.put("debitId", params.get("customerId"));
        param.put("debit", bonusAccount);
        param.put("orderTime", DateUtil.date2String(new Date(), DateUtil.DEFAULT_DATE_FORMAT));
        param.put("amount", bonusAmount);
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

    /**
     * 转账-礼品卡账户
     */
    private void transferGiftCard() {

        String giftCardAccount = params.get("giftCardAccount");
        String giftCardAmount = params.get("giftCardAmount");

        System.out.println("===礼品卡支付===>" + giftCardAccount + "," + giftCardAmount);

        if (StringUtil.isEmpty(giftCardAccount))
            return;

        if (StringUtil.isEmpty(giftCardAmount))
            return;

        if (new BigDecimal(giftCardAmount).compareTo(new BigDecimal(WalletConstant.AMOUNT_ZERO)) == 0)
            return;

        Map<String, String> param = new HashMap<String, String>();

        param.put("creditId", WalletConstant.SYS_ACCOUNT_FEE);
        param.put("credit", WalletConstant.SYS_ACCOUNT_FEE);// 系统收费账户
        param.put("debitId", params.get("customerId"));
        param.put("debit", giftCardAccount);
        param.put("orderTime", DateUtil.date2String(new Date(), DateUtil.DEFAULT_DATE_FORMAT));
        param.put("amount", giftCardAmount);
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
