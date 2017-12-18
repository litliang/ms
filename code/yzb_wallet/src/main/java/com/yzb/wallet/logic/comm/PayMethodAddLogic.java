package com.yzb.wallet.logic.comm;

import android.app.Activity;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.yzb.wallet.openInterface.BasePay;
import com.yzb.wallet.sys.CardConstant;
import com.yzb.wallet.sys.ServiceDispatcher;
import com.yzb.wallet.util.WalletConstant;

import java.util.Map;

/**
 * 支付方式设置
 */
public class PayMethodAddLogic extends BasePay {

    private Activity activity;

    private Map<String, String> params;

    public PayMethodAddLogic(Activity activity) {
        this.activity = activity;
    }

    /**
     * 添加支付列表
     */
    public void addPayMethod(Map<String, String> params) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        this.params = params;

        // 获取已排序的付款列表
        addPayment();
    }

    /**
     * 添加付款列表
     */
    private void addPayment() {

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... param) {
                params.put("serviceName", CardConstant.app_add_payment);
                return ServiceDispatcher.callApp(activity, params);
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

}
