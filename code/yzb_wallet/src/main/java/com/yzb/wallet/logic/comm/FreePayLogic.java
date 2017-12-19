package com.yzb.wallet.logic.comm;

import android.app.Activity;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.yzb.wallet.openInterface.BasePay;
import com.yzb.wallet.sys.CardConstant;
import com.yzb.wallet.sys.ServiceDispatcher;
import com.yzb.wallet.util.WalletConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 免密支付
 */
public class FreePayLogic extends BasePay {

    private Activity activity;

    Map<String, String> params = new HashMap<>();

    public FreePayLogic(Activity activity) {
        this.activity = activity;
    }

    public void set(Map<String, String> params) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        this.params = params;

        // 设置免密支付
        setFreePay();
    }

    /**
     * 设置免密支付
     */
    private void setFreePay() {

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_update_customer, params[0]));
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
        }.execute(JSON.toJSONString(params), null, null);
    }

    /**
     * 校验是否免密支付
     */
    public void validate(String mobile) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        this.params.put("mobile", mobile);
        this.params.put("customerType", "P");

        // 校验免密支付
        validateFreePay();
    }

    /**
     * 校验是否免密支付
     */
    public void validate(Map<String, String> params) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        this.params = params;

        // 校验免密支付
        validateFreePay();
    }

    /**
     * 校验免密支付
     */
    private void validateFreePay() {

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_detail_customer, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result == null || result.isEmpty()) {
                    wbListener.setError(WalletConstant.CODE_FAIL, WalletConstant.MSG_ERROR);
                } else if (!WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setError(result.get("code"), result.get("error"));
                } else if (WalletConstant.CODE_OK.equals(result.get("code"))) {
                    Map<String, String> customer = JSON.parseObject(String.valueOf(result.get("data")), Map.class);
                    wbListener.setSuccess(customer, result.get("code"));
                }
            }
        }.execute(JSON.toJSONString(params), null, null);
    }

}
