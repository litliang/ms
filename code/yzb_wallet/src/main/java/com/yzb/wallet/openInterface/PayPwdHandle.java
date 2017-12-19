package com.yzb.wallet.openInterface;

import android.app.Activity;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.yzb.wallet.sys.CardConstant;
import com.yzb.wallet.sys.ServiceDispatcher;
import com.yzb.wallet.util.WalletConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付密码处理
 */
public class PayPwdHandle extends BasePay {

    private Activity activity;

    public PayPwdHandle(Activity activity) {
        this.activity = activity;
    }

    public void validate(String customerId, String oldPsw) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        // 校验支付密码
        validatePayPsw(customerId, oldPsw);
    }

    public void set(String customerId, String payPasswd) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        // 设置支付密码
        setPayPwd(customerId, payPasswd);
    }

    /**
     * 校验支付密码
     */
    private void validatePayPsw(String customerId, String oldPsw) {

        Map<String, String> param = new HashMap<String, String>();

        param.put("id", customerId);
        param.put("payPasswd", oldPsw);

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_api_validate_paypswd, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result == null || result.isEmpty()) {
                    wbListener.setError(WalletConstant.CODE_FAIL, "获取失败");
                } else if (!WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setError(result.get("code"), result.get("error"));
                } else if (WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setSuccess(result.get("code"));
                }
            }
        }.execute(JSON.toJSONString(param), null, null);
    }

    /**
     * 设置支付密码
     */
    public void setPayPwd(String customerId, String payPasswd) {

        Map<String, String> param = new HashMap<String, String>();

        param.put("id", customerId);
        param.put("payPasswd", payPasswd);

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_api_set_pswd, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result == null || result.isEmpty()) {
                    wbListener.setError(WalletConstant.CODE_FAIL, "设置失败");
                } else if (!WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setError(result.get("code"), result.get("error"));
                } else if (WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setSuccess(result.get("code"));
                }
            }
        }.execute(JSON.toJSONString(param), null, null);
    }

}
