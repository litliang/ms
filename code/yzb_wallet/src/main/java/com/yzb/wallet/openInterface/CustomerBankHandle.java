package com.yzb.wallet.openInterface;

import android.app.Activity;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.yzb.wallet.sys.CardConstant;
import com.yzb.wallet.sys.ServiceDispatcher;
import com.yzb.wallet.util.DateUtil;
import com.yzb.wallet.util.StringUtil;
import com.yzb.wallet.util.WalletConstant;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户银行卡处理
 */
public class CustomerBankHandle extends BasePay {

    private Activity activity;

    private Map<String, String> params;

    public CustomerBankHandle(Activity activity) {
        this.activity = activity;
    }

    /**
     * 借记卡绑定
     */
    public void debitCardBind(Map<String, String> params) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        // 参数校验
        String msg = checkParamsMsgBind(params);
        if (!StringUtil.isEmpty(msg)) {
            // 失败接口返回
            wbListener.setError(WalletConstant.CODE_INVALID, msg);
            return;
        }

        this.params = params;

        // 绑定银行卡
        bindCard(WalletConstant.DEBIT_CARD);

    }

    /**
     * 信用卡绑定
     */
    public void creditCardBind(Map<String, String> params) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        // 参数校验
        String msg = checkParamsMsgBind(params);
        if (!StringUtil.isEmpty(msg)) {
            // 失败接口返回
            wbListener.setError(WalletConstant.CODE_INVALID, msg);
            return;
        }

        this.params = params;

        // 绑定银行卡
        bindCard(WalletConstant.CREDIT_CARD);

    }

    /**
     * 银行卡解绑
     */
    public void relieve(Map<String, String> params) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        // 参数校验
        String msg = checkParamsMsgRelieve(params);
        if (!StringUtil.isEmpty(msg)) {
            // 失败接口返回
            wbListener.setError(WalletConstant.CODE_INVALID, msg);
            return;
        }

        this.params = params;

        // 解绑银行卡
        relieveCard();

    }

    /**
     * 检查绑定银行卡参数
     */
    private String checkParamsMsgBind(Map<String, String> params) {

        if (StringUtil.isEmpty(params.get("customerId"))) {
            return "用户Id不能为空";
        }
        if (StringUtil.isEmpty(params.get("bankName"))) {
            return "银行名称不能为空";
        }
        if (StringUtil.isEmpty(params.get("fullNo"))) {
            return "卡号不能为空";
        }
        if (StringUtil.isEmpty(params.get("reservedMobile"))) {
            return "预留手机号不能为空";
        }
        return "";
    }

    /**
     * 检查解绑银行卡参数
     */
    private String checkParamsMsgRelieve(Map<String, String> params) {

        if (StringUtil.isEmpty(params.get("customerId"))) {
            return "用户Id不能为空";
        }
        if (StringUtil.isEmpty(params.get("fullNo"))) {
            return "卡号不能为空";
        }
        return "";
    }

    /**
     * 绑定银行卡
     */
    private void bindCard(String cardType) {

        final Map<String, String> param = new HashMap<String, String>();

        param.put("customerId", params.get("customerId"));
        param.put("cardType", cardType);
        param.put("bankName", params.get("bankName"));
        param.put("bankId", params.get("bankId"));
        param.put("fullNo", params.get("fullNo"));
        param.put("reservedMobile", params.get("reservedMobile"));
        param.put("name", params.get("name"));
        param.put("certNo", params.get("certNo"));

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_api_bind_card, params[0]));
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
     * 解绑银行卡
     */
    private void relieveCard() {

        final Map<String, String> param = new HashMap<String, String>();

        param.put("customerId", params.get("customerId"));
        param.put("fullNo", params.get("fullNo"));

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_api_relieve_card, params[0]));
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

}
