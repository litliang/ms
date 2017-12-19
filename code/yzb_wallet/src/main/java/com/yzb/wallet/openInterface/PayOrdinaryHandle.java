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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 普通订单
 */
public class PayOrdinaryHandle extends BasePay {

    private Activity activity;

    private WalletPwdDialog.Builder pwdDialog;
    private WalletVerifyCodeDialog.Builder verifyCodeDialog;
    private WalletPayInfoDialog.Builder payInfoDialog;

    private Map<String, String> params;
    private String accountType;//支付方式

    public PayOrdinaryHandle(Activity activity) {
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
        // 普通订单(转账)
        accountType = params.get("accountType");
        if (StringUtil.isEmpty(accountType)) {
            // 无支付方式-默认为账户余额
            accountType = WalletConstant.PAY_BALANCE;
            // 打开付款详情选择支付方式
            OpenWalletPayInfoDialog();
        } else {
            // 有支付方式跳过付款详情判断是否打开验证码
            if (WalletConstant.PAY_BANK.equals(accountType)) {
                // 银行卡支付打开验证码
                OpenWalletVerifyCodeDialog();
            } else {
                // 账户余额支付,查询accountId后打开支付密码
                getCustAccount();
            }
        }
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
        // 普通订单(转账)
        if (StringUtil.isEmpty(params.get("creditId"))) {
            return "收款方不能为空";
        }
        if (StringUtil.isEmpty(params.get("summary"))) {
            return "摘要不能为空";
        }
        return "";
    }

    /**
     * 打开付款详情
     */
    private void OpenWalletPayInfoDialog() {

        payInfoDialog = new WalletPayInfoDialog.Builder(activity);
        payInfoDialog.setSummary(params.get("summary"));
        payInfoDialog.setAmount(params.get("amount"));
        // 付款方式
        payInfoDialog.setPayMethodListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               /* final TextView bankName = payInfoDialog.getBankName();
                final TextView accountTypeView = payInfoDialog.getAccountType();
                final TextView customerBankId = payInfoDialog.getCustomerBankId();

                PayMethod payHandle = new PayMethod(activity);
                payHandle.chosePayMethod(params);
                payHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                        System.out.println("======付款方式=======>" + resultMap);
                        accountTypeView.setText(resultMap.get("accountType"));
                        customerBankId.setText(resultMap.get("customerBankId"));
                        bankName.setText(resultMap.get("bankName"));
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("======付款方式=======>" + RESULT_CODE);
                        WalletToastCustom.sendDialog(activity, activity.getWindow().peekDecorView(), ERROR_MSG, 140);
                    }
                });*/
            }
        });
        // 确认付款
        payInfoDialog.setPayListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                /*TextView accountTypeView = payInfoDialog.getAccountType();

                accountType = accountTypeView.getText().toString();

                // 判断是否打开验证码
                if (WalletConstant.PAY_BANK.equals(accountType)) {
                    // 银行卡支付打开验证码
                    OpenWalletVerifyCodeDialog();
                } else {
                    // 账户余额支付，查询accountId后打开支付密码
                    getCustAccount();
                }*/
            }
        });
        payInfoDialog.create().show();
    }

    /**
     * 打开验证码
     */
    private void OpenWalletVerifyCodeDialog() {

        verifyCodeDialog = new WalletVerifyCodeDialog.Builder(activity);
        verifyCodeDialog.setVerifyCodeClick(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 失败接口返回
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
     * 打开支付密码输入
     */
    private void OpenWalletPwdDialog() {

        pwdDialog = new WalletPwdDialog.Builder(activity);
        pwdDialog.setKeyBoardClick(new WalletPwdDialog.Builder.KeyBoardClickListener() {
            @Override
            public void backPassword(String pwd) {
                System.out.println("=========支付密码==>" + pwd);
                // 转账
                transfer(pwd);
            }
        });
        pwdDialog.create().show();
    }

    /**
     * 获取用户账户
     */
    private void getCustAccount() {

        final Map<String, String> param = new HashMap<String, String>();

        System.out.println("=======账户类型=======>" + WalletConstant.PAY_TEXT(accountType));

        param.put("accountType", accountType);

        new AsyncTask<String, Void, List<Map>>() {
            protected List<Map> doInBackground(String... params) {
                List<Map> result = new ArrayList<Map>();
                // 获取用户账号(余额,红包,礼品卡...)
                Map<String, String> custAccountResult = ServiceDispatcher.call(activity, ServiceDispatcher.setParams(WalletConstant.sessionId, CardConstant.app_api_appCustomerAccountList, WalletConstant.UUID, params[0]));
                if (null == custAccountResult || custAccountResult.isEmpty()) return null;
                if (WalletConstant.CODE_OK.equals(custAccountResult.get("code"))) {
                    result = JSON.parseArray(custAccountResult.get("data"), Map.class);
                }
                return result;
            }

            @Override
            protected void onPostExecute(List<Map> result) {
                if(result == null){
                    // 失败接口返回
                    wbListener.setError(WalletConstant.CODE_FAIL, WalletConstant.MSG_QUERY_FAIL);
                }else if (result.size() == 0 || (result.size() > 0 && result.get(0).isEmpty())) {
                    // 失败接口返回
                    wbListener.setError(WalletConstant.CODE_FAIL, WalletConstant.MSG_WITHOUT_PAY);
                } else {
                    System.out.println("=======支付账户=======>" + String.valueOf(result.get(0).get("accountId")));
                    params.put("accountId", String.valueOf(result.get(0).get("accountId")));
                    // 查询支付账户后打开支付密码输入界面
                    OpenWalletPwdDialog();
                }
            }
        }.execute(JSON.toJSONString(param), null, null);
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
                if (pwdDialog != null)
                    pwdDialog.dismiss();
                if (payInfoDialog != null)
                    payInfoDialog.dismiss();
            }
        }.execute(JSON.toJSONString(param), null, null);

    }


}
