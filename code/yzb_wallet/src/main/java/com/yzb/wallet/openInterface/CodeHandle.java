package com.yzb.wallet.openInterface;

import android.app.Activity;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.yzb.wallet.sys.CardConstant;
import com.yzb.wallet.sys.ServiceDispatcher;
import com.yzb.wallet.util.AESUtil;
import com.yzb.wallet.util.StringUtil;
import com.yzb.wallet.util.WalletConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付码处理
 */
public class CodeHandle extends BasePay {

    private String customerId;

    private Activity activity;

    public CodeHandle(Activity activity) {
        this.activity = activity;
    }

    /**
     * 获取付款支付码
     * 前2位类型+(用户id+时间戳)64位AES加密
     */
    public void payCode(String customerId) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        this.customerId = customerId;

        // 获取有效秘钥
        validKey();

    }

    /**
     * 添加用户有效秘钥
     */
    public void addKey(String customerId) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        this.customerId = customerId;

        // 添加有效秘钥
        addValidKey();

    }

    /**
     * 获取有效秘钥
     */
    private void validKey() {

        final Map<String, String> param = new HashMap<String, String>();

        param.put("customerId", customerId);

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_api_valid_key, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result == null || result.isEmpty()) {
                    wbListener.setError(WalletConstant.CODE_FAIL, WalletConstant.MSG_ERROR);
                } else if (!WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setError(result.get("code"), result.get("error"));
                } else if (WalletConstant.CODE_OK.equals(result.get("code"))) {
                    // 用户秘钥
                    List<Map> keyList = JSON.parseArray(result.get("data"), Map.class);
                    if (keyList.size() > 0) {
                        Map<String, Object> key = keyList.get(0);
                        // 组织付款支付码
                        handleCode(String.valueOf(key.get("customerKey")));
                    } else {
                        wbListener.setError(WalletConstant.CODE_NULL, WalletConstant.MSG_QUERY_NULL);
                    }
                }
            }
        }.execute(JSON.toJSONString(param), null, null);
    }

    /**
     * 添加有效秘钥
     */
    private void addValidKey() {

        final Map<String, String> param = new HashMap<String, String>();

        param.put("customerId", customerId);

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_api_add_valid_key, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result == null || result.isEmpty()) {
                    wbListener.setError(WalletConstant.CODE_FAIL, WalletConstant.MSG_ERROR);
                } else if (!WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setError(result.get("code"), result.get("error"));
                } else if (WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setSuccess(WalletConstant.CODE_OK);
                }
            }
        }.execute(JSON.toJSONString(param), null, null);
    }

    /**
     * 组织付款支付码
     */
    private void handleCode(String key) {

        // 类型-付款
        String payCode = WalletConstant.DEBIT;

        // 时间戳
        Long timeMillis = System.currentTimeMillis();

        String plainText = customerId + timeMillis;
        String cipherText = AESUtil.encrypt(plainText, key);

        if (StringUtil.isEmpty(cipherText)) {
            wbListener.setError(WalletConstant.CODE_FAIL, WalletConstant.MSG_ERROR);
            return;
        }

        // 类型+密文
        payCode += cipherText;

        Map<String, String> result = new HashMap<>();

        result.put("payCode", payCode);

        wbListener.setSuccess(result, WalletConstant.CODE_OK);

    }

}
