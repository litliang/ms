package com.yzb.wallet.openInterface;

import android.app.Activity;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.yzb.wallet.sys.CardConstant;
import com.yzb.wallet.sys.ServiceDispatcher;
import com.yzb.wallet.util.StringUtil;
import com.yzb.wallet.util.WalletConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账户处理
 */
public class AccountHandle extends BasePay {

    private String mAccountType;// 账户类型

    private Map<String, String> params;

    private Activity activity;

    public AccountHandle(Activity activity) {
        this.activity = activity;
    }

    /**
     * 获取余额(根据账户id和账户类型)
     */
    public void handleBalance(String accountId, String accountType) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        this.mAccountType = accountType;

        // 获取账户余额
        balance(accountId);
    }

    /**
     * 获取余额(根据用户id和账户类型)
     */
    public void accountBalance(String customerId, String accountType) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        this.mAccountType = accountType;

        // 根据customerId确定用户,accountType确定账户,查询余额
        accountBalance(customerId);

    }

    /**
     * 获取账户列表
     */
    public void handleAccount(String customerId) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        // 获取账户列表(根据用户id)
        accountList(customerId);
    }

    /**
     * 创建账户
     */
    public void create(Map<String, String> params) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        this.params = params;

        // 注册yzb用户,成功后注册各种账户
        createUser();
    }

    /**
     * 获取账户余额
     */
    private void balance(String accountId) {

        final Map<String, String> param = new HashMap<String, String>();

        param.put("accountId", accountId);

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_api_account_balance, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result == null || result.isEmpty()) {
                    wbListener.setError(WalletConstant.CODE_FAIL, WalletConstant.MSG_ERROR);
                } else if (!WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setError(result.get("code"), result.get("error"));
                } else if (WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setSuccess(result, result.get("code"));
                }
            }
        }.execute(JSON.toJSONString(param), null, null);
    }

    /**
     * 获取账户列表(根据用户id)
     */
    private void accountList(String customerId) {

        final Map<String, String> param = new HashMap<String, String>();

        param.put("customerId", customerId);

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
                    wbListener.setSuccess(result, result.get("code"));
                }
            }
        }.execute(JSON.toJSONString(param), null, null);
    }

    /**
     * 获取账户余额(根据用户id和账号类型)
     */
    private void accountBalance(String customerId) {

        final Map<String, String> param = new HashMap<String, String>();

        param.put("customerId", customerId);
        param.put("accountType", mAccountType);

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
                    List<Map> resultList = JSON.parseArray(result.get("data"), Map.class);
                    if (resultList.size() == 0) {
                        wbListener.setError(WalletConstant.CODE_NULL, WalletConstant.ACCOUNT_NULL);
                    } else {
                        Map<String, Object> resultMap = resultList.get(0);
                        // 成功查询余额
                        String accountId = String.valueOf(resultMap.get("accountId"));
                        if (StringUtil.isEmpty(accountId))
                            wbListener.setError(WalletConstant.CODE_NULL, WalletConstant.ACCOUNT_NULL);
                        else
                            balance(accountId);
                    }
                }
            }
        }.execute(JSON.toJSONString(param), null, null);
    }

    /**
     * 注册yzb用户
     */
    private void createUser() {

        final Map<String, String> param = new HashMap<String, String>();

        param.put("id", params.get("customerId"));
        param.put("mobile", params.get("mobile"));
        param.put("customerType", WalletConstant.USER_TYPE_PERSON);
        param.put("status", WalletConstant.USER_TYPE_PERSON);

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_api_cust_create, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result == null || result.isEmpty()) {
                    wbListener.setError(WalletConstant.CODE_FAIL, "注册失败");
                } else if (!WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setError(result.get("code"), result.get("error"));
                } else if (WalletConstant.CODE_OK.equals(result.get("code"))) {
                    // 创建余额账户
                    createAccount(WalletConstant.PAY_BALANCE);
                    // 创建红包账户
                    createAccount(WalletConstant.PAY_RED_ENVELOP);
                    // 创建礼品卡账户
                    createAccount(WalletConstant.PAY_GIFT_CARD);
                    // 创建平台积分账户
                    createAccount(WalletConstant.PAY_INTEGRAL_PT);
                    // 创建商家积分账户
                    createAccount(WalletConstant.PAY_INTEGRAL_SJ);
                }
            }
        }.execute(JSON.toJSONString(param), null, null);
    }

    /**
     * 创建账户
     */
    private void createAccount(final String accountType) {

        final Map<String, String> param = new HashMap<String, String>();

        param.put("customerId", params.get("customerId"));
        param.put("mobile", params.get("mobile"));
        param.put("accountType", accountType);

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_api_account_create, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result == null || result.isEmpty()) {
                    wbListener.setError(WalletConstant.CODE_FAIL, "创建失败");
                } else if (!WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setError(result.get("code"), WalletConstant.ACCOUNT_TEXT(accountType) + result.get("error"));
                } else if (WalletConstant.CODE_OK.equals(result.get("code"))) {
                    String data = WalletConstant.ACCOUNT_TEXT(accountType) + WalletConstant.CREATE_SUCCESS;
                    result.put("data", data);
                    wbListener.setSuccess(result, result.get("code"));
                }
            }
        }.execute(JSON.toJSONString(param), null, null);
    }

}
