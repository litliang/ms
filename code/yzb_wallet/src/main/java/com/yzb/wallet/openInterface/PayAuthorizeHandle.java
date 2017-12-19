package com.yzb.wallet.openInterface;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.yzb.wallet.dialog.WalletOpen;
import com.yzb.wallet.dialog.WalletToastCustom;
import com.yzb.wallet.dialog.WalletVerifyCodeDialog;
import com.yzb.wallet.sys.CardConstant;
import com.yzb.wallet.sys.ServiceDispatcher;
import com.yzb.wallet.util.DateUtil;
import com.yzb.wallet.util.HashUtil;
import com.yzb.wallet.util.StringUtil;
import com.yzb.wallet.util.WalletConstant;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 授权订单
 */
public class PayAuthorizeHandle extends BasePay {

    private Activity activity;

    private WalletVerifyCodeDialog.Builder verifyCodeDialog;

    private Map<String, String> params;

    public PayAuthorizeHandle(Activity activity) {
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
        // 授权订单(转账)
        getPayMethod();
    }

    /**
     * 检查参数
     */
    private String checkParamsMsg(Map<String, String> params) {

        // 判断是否登录
        String loginMsg = checkLoginMsg(params);
        if (!StringUtil.isEmpty(loginMsg))
            return loginMsg;

        // 金额不能为空
        if (StringUtil.isEmpty(params.get("amount"))) {
            return "金额不能为空";
        }
        // 授权支付
        if (StringUtil.isEmpty(params.get("creditId"))) {
            return "收款方不能为空";
        }
        return "";
    }

    /**
     * 获取支付方式
     */
    private void getPayMethod() {

        Map<String, String> param = new HashMap<String, String>();

        new AsyncTask<String, Void, Map<String, Object>>() {
            protected Map<String, Object> doInBackground(String... params) {

                Map<String, Object> result = new HashMap<String, Object>();

                result.put("code", WalletConstant.CODE_OK);

                // 获取用户账号(余额,红包,礼品卡...)
                Map<String, String> custAccountResult = ServiceDispatcher.call(activity, ServiceDispatcher.setParams(WalletConstant.sessionId, CardConstant.app_api_appCustomerAccountList, WalletConstant.UUID, params[0]));
                if (null == custAccountResult || custAccountResult.isEmpty()) {
                    result.put("code", WalletConstant.CODE_FAIL);
                    return result;
                }
                if (WalletConstant.CODE_OK.equals(custAccountResult.get("code"))) {
                    List<Map> list = JSON.parseArray(custAccountResult.get("data"), Map.class);
                    result.put("accountList", list.size() > 0 ? list : null);
                }

                // 获取银行卡列表
                Map<String, String> bankCardResult = ServiceDispatcher.call(activity, ServiceDispatcher.setParams(WalletConstant.sessionId, CardConstant.app_api_bankCardList, WalletConstant.UUID, params[0]));
                if (null == bankCardResult || bankCardResult.isEmpty()) {
                    result.put("code", WalletConstant.CODE_FAIL);
                    return result;
                }
                if (WalletConstant.CODE_OK.equals(bankCardResult.get("code"))) {
                    List<Map> list = JSON.parseArray(bankCardResult.get("data"), Map.class);
                    result.put("bankCardList", list.size() > 0 ? list : null);
                }

                return result;
            }

            @Override
            protected void onPostExecute(Map<String, Object> result) {
                String code = String.valueOf(result.get("code"));

                if (WalletConstant.CODE_FAIL.equals(code)) {
                    // 失败接口返回
                    wbListener.setError(WalletConstant.CODE_FAIL, WalletConstant.MSG_QUERY_FAIL);
                } else {
                    // 根据顺序依次支付
                    PayTurnHandle(result);
                }
            }
        }.execute(JSON.toJSONString(param), null, null);
    }

    /**
     * 依次支付处理
     * 判断使用账户余额还是银行卡支付
     */
    private void PayTurnHandle(Map<String, Object> payTurn) {

        List<Map> accountList = (List<Map>) payTurn.get("accountList");
        System.out.println("=1=>" + accountList);
        List<Map> bankCardList = (List<Map>) payTurn.get("bankCardList");
        System.out.println("=2=>" + bankCardList);
        if (accountList == null && bankCardList == null) {
            // 失败接口返回
            wbListener.setError(WalletConstant.CODE_FAIL, WalletConstant.MSG_WITHOUT_PAY);
        } else if (accountList != null) {
            // 用户账号支付,余额不足使用银行卡支付
            payTurnAccount(accountList, bankCardList);
        } else if (accountList == null && bankCardList != null) {
            // 银行卡支付
            payTurnBankCard(bankCardList);
        }
    }

    /**
     * 依次支付
     * 用户账号(余额,红包,礼品卡...)
     * 余额不足使用银行卡支付
     */
    private void payTurnAccount(final List<Map> accountList, final List<Map> bankCardList) {

        System.out.println("========>使用用户账号付款");

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... request) {

                Map<String, String> result = new HashMap<String, String>();

                for (Map<String, String> map : accountList) {
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("payPasswd", params.get("payPasswd"));
                    param.put("orderTime", DateUtil.date2String(new Date(), DateUtil.DEFAULT_DATE_FORMAT));
                    param.put("accountId", String.valueOf(map.get("accountId")));
                    param.put("creditId", params.get("creditId"));
                    param.put("amount", params.get("amount"));
                    param.put("summary", params.get("summary"));
                    param.put("batchNo", "");
                    param.put("reserve", "");

                    System.out.println("======付款账号====>" + String.valueOf(map.get("accountId")));

                    result = ServiceDispatcher.call(activity, ServiceDispatcher.setParams(WalletConstant.sessionId, CardConstant.app_api_transfer, WalletConstant.UUID, JSON.toJSONString(param)));

                    System.out.println("=账号付款返回结果=>" + result);

                    // 付款成功跳出所有循环
                    if (result != null && WalletConstant.CODE_OK.equals(result.get("code"))) {
                        break;
                    }
                }

                return result;
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result != null && WalletConstant.CODE_OK.equals(result.get("code"))) {
                    // 付款成功
                    wbListener.setSuccess(result.get("code"));
                } else if (bankCardList == null) {
                    // 付款失败且无银行卡
                    String error = result.get("error");
                    wbListener.setError(result.get("code"), StringUtil.isEmpty(error) ? "账户余额不足" : error);
                } else {
                    // 付款失败使用银行卡付款
                    payTurnBankCard(bankCardList);
                }
            }
        }.execute(null, null, null);
    }

    /**
     * 依次支付
     * 银行卡
     */
    private void payTurnBankCard(final List<Map> bankCardList) {

        System.out.println("========>使用银行卡付款");
        // 打开验证码输入dialog
        OpenWalletVerifyCodeDialog();
    }

    /**
     * 打开验证码
     */
    private void OpenWalletVerifyCodeDialog() {

        verifyCodeDialog = new WalletVerifyCodeDialog.Builder(activity);
        verifyCodeDialog.setVerifyCodeClick(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                wbListener.setError(WalletConstant.CODE_FAIL, "敬请期待");
            }
        });
        verifyCodeDialog.setDismiss(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });
        verifyCodeDialog.create().show();
    }

    /**
     * 验证码
     */
    private void verifyCode(String code) {
        TimerTask task = new TimerTask() {
            public void run() {
                verifyCodeDialog.dismiss();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1000);
    }

    /**
     * 转账
     */
    private void transfer(String pwd) {

        Map<String, String> param = new HashMap<String, String>();
        param.put("payPasswd", !StringUtil.isEmpty(pwd) ? HashUtil.getMD5(pwd) : null);
        param.put("orderTime", DateUtil.date2String(new Date(), DateUtil.DEFAULT_DATE_FORMAT));
        param.put("accountId", String.valueOf(params.get("accountId")));
        param.put("creditId", params.get("creditId"));
        param.put("amount", params.get("amount"));
        param.put("summary", params.get("summary"));
        param.put("batchNo", "");
        param.put("reserve", "");

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.call(activity, ServiceDispatcher.setParams(WalletConstant.sessionId, CardConstant.app_api_transfer, WalletConstant.UUID, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result != null && !result.isEmpty() && WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setSuccess(result.get("code"));
                } else {
                    String code = WalletConstant.CODE_FAIL;
                    String error = "支付失败";
                    if (null != result && !result.isEmpty()) {
                        code = result.get("code");
                        error = result.get("error");
                    }
                    wbListener.setError(code, error);
                }
            }
        }.execute(JSON.toJSONString(param), null, null);

    }

}
