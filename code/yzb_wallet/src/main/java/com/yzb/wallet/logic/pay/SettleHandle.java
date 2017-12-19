package com.yzb.wallet.logic.pay;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.yzb.wallet.dialog.WalletOpen;
import com.yzb.wallet.dialog.WalletPwdDialog;
import com.yzb.wallet.logic.comm.FreePayLogic;
import com.yzb.wallet.openInterface.BasePay;
import com.yzb.wallet.openInterface.WalletBackListener;
import com.yzb.wallet.sys.CardConstant;
import com.yzb.wallet.sys.ServiceDispatcher;
import com.yzb.wallet.util.HashUtil;
import com.yzb.wallet.util.WalletConstant;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 提现订单
 */
public class SettleHandle extends BasePay {

    private Activity activity;

    private Map<String, String> params;

    private Boolean showForgetPwd;

    private WalletPwdDialog.Builder pwdDialog;

    public SettleHandle(Activity activity) {
        this.activity = activity;
    }

    /**
     * 支付操作
     */
    public void pay(Map<String, String> params, final Boolean showForgetPwd) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        this.params = params;
        this.showForgetPwd = showForgetPwd;
        // 打开钱包loading
        WalletOpen.Builder walletOpen = new WalletOpen.Builder(activity);

        walletOpen.setDismiss(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                System.out.println("============>关闭loading");
                // 校验免密支付
                validateFreePay();
            }
        });

        walletOpen.loading();
    }

    /**
     * 校验免密支付
     */
    private void validateFreePay(){

        FreePayLogic payHandle = new FreePayLogic(activity);

        Map<String, String> validateParams = new HashMap<>();
        validateParams.put("mobile", params.get("mobile"));
        validateParams.put("customerType", params.get("customerType"));
        payHandle.validate(validateParams);

        // 操作结果
        payHandle.setCallBack(new WalletBackListener() {
            @Override
            public void setSuccess(String RESULT_CODE) {

            }

            @Override
            public void setSuccess(Map<String, String> customer, String RESULT_CODE) {

                String freePay = customer.get("freePay");

                BigDecimal amount = new BigDecimal(String.valueOf(params.get("amount")));
                BigDecimal freeAmount = new BigDecimal(String.valueOf(customer.get("freeAmount")));

                // 免密支付
                if(WalletConstant.STATUS_ON.equals(freePay) && freeAmount.compareTo(amount) != -1)
                    settle();
                else
                    OpenWalletPwdDialog();
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG) {
                wbListener.setError(RESULT_CODE, ERROR_MSG);
            }
        });

    }

    /**
     * 打开支付密码输入
     */
    private void OpenWalletPwdDialog() {

        pwdDialog = new WalletPwdDialog.Builder(activity);
        pwdDialog.showForgetPwd(showForgetPwd);
        pwdDialog.setForgetPWdCallBack(forgetPwdListener);
        pwdDialog.setKeyBoardClick(new WalletPwdDialog.Builder.KeyBoardClickListener() {
            @Override
            public void backPassword(String pwd) {
                System.out.println("==支付密码==>" + pwd);
                System.out.println("==支付密码（加密）==>" + HashUtil.getMD5(pwd));
                // 校验支付密码
                validatePayPsw(pwd);
            }
        });
        pwdDialog.create().show();
    }

    /**
     * 校验支付密码
     */
    private void validatePayPsw(String pwd) {

        Map<String, String> data = new HashMap<String, String>();

        data.put("mobile", params.get("mobile"));
        data.put("customerType", params.get("customerType"));
        data.put("payPasswd", pwd);

        data.put("merchantNo", params.get("merchantNo"));
        data.put("charset", params.get("charset"));
        data.put("sign", params.get("sign"));
        data.put("signType", params.get("signType"));

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_validate_pay_pswd, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result == null || result.isEmpty()) {
                    wbListener.setError(WalletConstant.CODE_FAIL, WalletConstant.MSG_ERROR);
                    if (pwdDialog != null)
                        pwdDialog.dismiss();
                } else if (!WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setError(result.get("code"), result.get("error"));
                    if (pwdDialog != null)
                        pwdDialog.dismiss();
                } else if (WalletConstant.CODE_OK.equals(result.get("code"))) {
                    // 校验支付密码成功调用提现
                    settle();
                }
            }
        }.execute(JSON.toJSONString(data), null, null);
    }

    /**
     * 提现
     */
    private void settle() {

        Map<String, String> data = new HashMap<String, String>();

        data.put("mobile", params.get("mobile"));
        data.put("customerType", params.get("customerType"));
        data.put("accountType", params.get("accountType"));
        String amount = String.valueOf(params.get("amount"));
        data.put("amount", (new BigDecimal(amount)).setScale(2).toString());
        data.put("goodsName", params.get("goodsName"));
        data.put("sortCode", params.get("sortCode"));
        data.put("transIp", params.get("transIp"));
        data.put("validCode", params.get("validCode"));

        data.put("merchantNo", params.get("merchantNo"));
        data.put("charset", params.get("charset"));
        data.put("sign", params.get("sign"));
        data.put("signType", params.get("signType"));

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_settle, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result == null || result.isEmpty()) {
                    wbListener.setError(WalletConstant.CODE_FAIL, "提现失败");
                } else if (!WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setError(result.get("code"), result.get("error"));
                } else if (WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setSuccess(WalletConstant.CODE_OK);
                }

                if (pwdDialog != null)
                    pwdDialog.dismiss();

            }
        }.execute(JSON.toJSONString(data), null, null);
    }

}
