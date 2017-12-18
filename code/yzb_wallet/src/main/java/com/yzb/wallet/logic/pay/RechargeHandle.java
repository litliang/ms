package com.yzb.wallet.logic.pay;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.yzb.wallet.activity.PayPwdForgetActivity;
import com.yzb.wallet.dialog.WalletOpen;
import com.yzb.wallet.dialog.WalletPwdDialog;
import com.yzb.wallet.dialog.WalletToastCustom;
import com.yzb.wallet.dialog.WalletVerifyCodeDialog;
import com.yzb.wallet.logic.comm.FreePayLogic;
import com.yzb.wallet.openInterface.BasePay;
import com.yzb.wallet.openInterface.WalletBackListener;
import com.yzb.wallet.sys.CardConstant;
import com.yzb.wallet.sys.ServiceDispatcher;
import com.yzb.wallet.util.HashUtil;
import com.yzb.wallet.util.StringUtil;
import com.yzb.wallet.util.WalletConstant;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 充值订单
 */
public class RechargeHandle extends BasePay {

    private Activity activity;

    private Map<String, String> params;

    private Boolean showForgetPwd;

    private String token;
    private String customerId;
    private String verifyCode;

    private WalletPwdDialog.Builder pwdDialog;

    private WalletVerifyCodeDialog.Builder verifyCodeDialog;

    public RechargeHandle(Activity activity) {
        this.activity = activity;
    }

    /**
     * 支付操作
     */
    public void pay(Map<String, String> params, Boolean showForgetPwd) {

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
                // 判断本行、他行支付
                validateCrossPay();
            }
        });

        walletOpen.loading();
    }

    /**
     * 判断本行、他行支付
     */
    private void validateCrossPay(){

        Map<String, String> data = new HashMap<String, String>();

        data.put("mobile", params.get("mobile"));
        data.put("customerType", params.get("customerType"));
        data.put("sortCode", params.get("sortCode"));

        data.put("merchantNo", params.get("merchantNo"));
        data.put("charset", params.get("charset"));
        data.put("sign", params.get("sign"));
        data.put("signType", params.get("signType"));

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_card_sort_code, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result == null || result.isEmpty()) {
                    wbListener.setError(WalletConstant.CODE_FAIL, WalletConstant.MSG_ERROR);
                } else if (!WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setError(result.get("code"), result.get("error"));
                } else if (WalletConstant.CODE_OK.equals(result.get("code"))) {
                    Map<String, String> cardInfo = JSON.parseObject(String.valueOf(result.get("data")), Map.class);
                    String bankCode = cardInfo.get("bankCode");

                    if(!WalletConstant.SPDB_CODE.equals(bankCode)){
                        // 他行，消费鉴权
                        String reservedMobile = cardInfo.get("reservedMobile");
                        OpenWalletVerifyCodeDialog(reservedMobile);
                    } else {
                        // 本行，校验支付密码
                        validateFreePay();
                    }

                }
            }
        }.execute(JSON.toJSONString(data), null, null);

    }

    /**
     * 打开验证码
     */
    private void OpenWalletVerifyCodeDialog(String reservedMobile) {

        verifyCodeDialog = new WalletVerifyCodeDialog.Builder(activity);
        verifyCodeDialog.setMobile(reservedMobile);
        // 发送验证码操作
        verifyCodeDialog.setVerifyCodeClick(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 消费鉴权
                Map<String, String> data = new HashMap<String, String>();

                data.put("mobileNo", params.get("mobile"));
                data.put("customerType", params.get("customerType"));
                data.put("sortCode", params.get("sortCode"));
                data.put("amount", params.get("amount"));
                data.put("orderNo", params.get("orderNo"));
                data.put("goodsName", params.get("goodsName"));

                data.put("merchantNo", params.get("merchantNo"));
                data.put("charset", params.get("charset"));
                data.put("sign", params.get("sign"));
                data.put("signType", params.get("signType"));

                new AsyncTask<String, Void, Map<String, String>>() {
                    protected Map<String, String> doInBackground(String... params) {
                        return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_quick_pre_consume, params[0]));
                    }

                    @Override
                    protected void onPostExecute(Map<String, String> result) {
                        if (result == null || result.isEmpty()) {
                            WalletToastCustom.sendDialog(activity, activity.getWindow().peekDecorView(), "获取验证码失败", 140);
                        } else if (!WalletConstant.CODE_OK.equals(result.get("code"))) {
                            WalletToastCustom.sendDialog(activity, activity.getWindow().peekDecorView(), "获取验证码失败", 140);
                        } else if (WalletConstant.CODE_OK.equals(result.get("code"))) {
                            token = String.valueOf(result.get("token"));
                            customerId = String.valueOf(result.get("customerId"));
                            System.out.println("====鉴权返回==>"+result);
                        }
                    }
                }.execute(JSON.toJSONString(data), null, null);
            }
        });
        // 支付操作
        verifyCodeDialog.setPayClick(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                verifyCode = verifyCodeDialog.getVerifyCode().getText().toString();

                if(StringUtil.isEmpty(verifyCode)){
                    WalletToastCustom.sendDialog(activity, activity.getWindow().peekDecorView(), "请输入验证码", 140);
                    return;
                }
                recharge();
            }
        });
        verifyCodeDialog.create().show();
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
                    recharge();
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
                    // 校验支付密码成功调用充值
                    recharge();
                }
            }
        }.execute(JSON.toJSONString(data), null, null);
    }

    /**
     * 充值
     */
    private void recharge() {

        Map<String, String> data = new HashMap<String, String>();

        data.put("mobile", params.get("mobile"));
        data.put("customerType", params.get("customerType"));
        data.put("accountType", params.get("accountType"));
        String amount = String.valueOf(params.get("amount"));
        data.put("amount", (new BigDecimal(amount)).setScale(2).toString());
        data.put("orderNo", params.get("orderNo"));
        data.put("goodsName", params.get("goodsName"));
        data.put("sortCode", params.get("sortCode"));
        data.put("transIp", params.get("transIp"));
        data.put("verifyCode", verifyCode);
        data.put("token", token);
        data.put("customerId", customerId);
        data.put("notifyUrl", params.get("notifyUrl"));

        data.put("merchantNo", params.get("merchantNo"));
        data.put("charset", params.get("charset"));
        data.put("sign", params.get("sign"));
        data.put("signType", params.get("signType"));

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_recharge, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result == null || result.isEmpty()) {
                    wbListener.setError(WalletConstant.CODE_FAIL, "充值失败");
                } else if (!WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setError(result.get("code"), result.get("error"));
                } else if (WalletConstant.CODE_OK.equals(result.get("code"))) {

                    Map<String, String> resultMap = JSON.parseObject(String.valueOf(result.get("data")), Map.class);

                    wbListener.setSuccess(WalletConstant.CODE_OK);

                    wbListener.setSuccess(resultMap, WalletConstant.CODE_OK);

                }

                if (pwdDialog != null)
                    pwdDialog.dismiss();

                if (verifyCodeDialog != null)
                    verifyCodeDialog.dismiss();

            }
        }.execute(JSON.toJSONString(data), null, null);
    }

}
