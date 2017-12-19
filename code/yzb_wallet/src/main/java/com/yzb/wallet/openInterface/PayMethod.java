package com.yzb.wallet.openInterface;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.wallet.R;
import com.yzb.wallet.adapter.PayMethodAdapter;
import com.yzb.wallet.dialog.WalletPayMethodDialog;
import com.yzb.wallet.dialog.WalletToastCustom;
import com.yzb.wallet.sys.CardConstant;
import com.yzb.wallet.sys.ServiceDispatcher;
import com.yzb.wallet.util.StringUtil;
import com.yzb.wallet.util.WalletConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付方式
 */
public class PayMethod extends BasePay {

    private Activity activity;

    private Map<String, String> params;

    private WalletPayMethodDialog.Builder payMethdoDialog;

    public PayMethod(Activity activity) {
        this.activity = activity;
    }

    /**
     * 选择支付方式
     */
    public void chosePayMethod(Map<String, String> params) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        this.params = params;
        // 判断是否登录
        String msg = checkLoginMsg(params);
        if (!StringUtil.isEmpty(msg)) {
            // 失败接口返回
            wbListener.setError(WalletConstant.CODE_INVALID, msg);
            return;
        }
        // 获取支付方式
        OpenWalletPayMethodDialog();
    }

    /**
     * 获取支付列表(默认方式)
     */
    public void payMethod(Map<String, String> params) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        this.params = params;
        // 判断是否登录
        String msg = checkLoginMsg(params);
        if (!StringUtil.isEmpty(msg)) {
            // 失败接口返回
            wbListener.setError(WalletConstant.CODE_INVALID, msg);
            return;
        }

        // 获取全部付款方式
        loadPayMethod();
    }

    /**
     * 获取支付列表(排序)
     */
    public void payMethodSeq(Map<String, String> params) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        // 检查参数
        String msg = checkParamsMsg(params.get("customerId"));
        if (!StringUtil.isEmpty(msg)) {
            // 失败接口返回
            wbListener.setError(WalletConstant.CODE_INVALID, msg);
            return;
        }

        this.params = params;

        // 获取已排序的付款列表
        paymentSeq();
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

        // 检查参数
        String msg = checkParamsMsg(params.get("customerId"));
        if (!StringUtil.isEmpty(msg)) {
            // 失败接口返回
            wbListener.setError(WalletConstant.CODE_INVALID, msg);
            return;
        }

        this.params = params;

        // 获取已排序的付款列表
        addPayment();
    }

    /**
     * 检查参数
     */
    private String checkParamsMsg(String id) {

        if (StringUtil.isEmpty(id)) {
            return "用户ID不能为空";
        }
        return "";
    }

    /**
     * 打开付款方式dialog
     */
    private void OpenWalletPayMethodDialog() {

        payMethdoDialog = new WalletPayMethodDialog.Builder(activity);
        payMethdoDialog.setChosePayMethod(new WalletPayMethodDialog.Builder.chosePayMethod() {
            @Override
            public void getPayMethod(Map<String, String> payMethod) {

                // 成功接口返回
                wbListener.setSuccess(payMethod, WalletConstant.CODE_OK);

                payMethdoDialog.dismiss();
            }
        });
        payMethdoDialog.create().show();
    }

    /**
     * 获取全部付款方式
     */
    private void loadPayMethod() {

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
            protected void onPostExecute(Map<String, Object> payMethod) {

                String code = String.valueOf(payMethod.get("code"));

                if (WalletConstant.CODE_FAIL.equals(code)) {
                    wbListener.setError(WalletConstant.CODE_FAIL, WalletConstant.MSG_QUERY_FAIL);
                    return;
                }

                List<Map> accountList = (List<Map>) payMethod.get("accountList");
                System.out.println("=1=>" + accountList);
                List<Map> bankCardList = (List<Map>) payMethod.get("bankCardList");
                System.out.println("=2=>" + bankCardList);
                if (accountList == null && bankCardList == null) {
                    wbListener.setError(WalletConstant.CODE_NULL, WalletConstant.MSG_QUERY_FAIL);
                } else {
                    // 加载数据
                    addData(accountList, bankCardList);
                }
            }
        }.execute(JSON.toJSONString(param), null, null);
    }

    /**
     * 加载数据
     */
    public void addData(List<Map> accountList, List<Map> bankCardList) {

        // 格式化数据支付方式
        List<Map> payMethodList = new ArrayList<>();

        if (bankCardList != null) {
            for (Map<String, String> map : bankCardList) {
                String accountType = WalletConstant.PAY_BANK;
                String customerBankId = String.valueOf(map.get("id"));
                String bankId = String.valueOf(map.get("bankId"));
                String sortNo = String.valueOf(map.get("sortNo"));
                String bankName = String.valueOf(map.get("bankName"));
                String cardType = String.valueOf(map.get("cardType"));
                String fullNo = String.valueOf(map.get("fullNo"));
                String reservedMobile = String.valueOf(map.get("reservedMobile"));
                bankName = bankName + WalletConstant.CARD_TYPE_TEXT(cardType) + "(尾号" + sortNo + ")";
                map.clear();
                map.put("accountType", accountType);
                map.put("customerBankId", customerBankId);
                map.put("bankId", bankId);
                map.put("bankName", bankName);
                map.put("fullNo", fullNo);
                map.put("reservedMobile", reservedMobile);
                payMethodList.add(map);
            }
        }

        if (accountList != null) {
            for (Map<String, String> map : accountList) {
                String accountType = String.valueOf(map.get("accountType"));
                String accountId = String.valueOf(map.get("accountId"));
                String bankName = WalletConstant.ACCOUNT_TEXT(accountType);
                map.clear();
                map.put("accountType", accountType);
                map.put("accountId", accountId);
                map.put("bankName", bankName);
                // 去除积分账户
                if(!WalletConstant.PAY_INTEGRAL_PT.equals(accountType))
                    payMethodList.add(map);
            }
        }

        Map<String, String> result = new HashMap<>();
        result.put("code", WalletConstant.CODE_OK);
        result.put("data", JSON.toJSONString(payMethodList));

        wbListener.setSuccess(result, WalletConstant.CODE_OK);

    }

    /**
     * 获取已排序的付款列表
     */
    private void paymentSeq() {

        final Map<String, String> param = new HashMap<String, String>();
        param.put("customerId", params.get("customerId"));

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_api_query_customer_payment, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result == null || result.isEmpty()) {
                    wbListener.setError(WalletConstant.CODE_FAIL, "获取失败");
                } else if (!WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setError(result.get("code"), result.get("error"));
                } else if (WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setSuccess(result, result.get("code"));
                }
            }
        }.execute(JSON.toJSONString(param), null, null);
    }

    /**
     * 添加付款列表
     */
    private void addPayment() {

        final Map<String, String> param = new HashMap<String, String>();
        param.put("customerId", params.get("customerId"));

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... param) {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("serviceName", CardConstant.app_api_add_customer_payment);
                parameters.put("customerId", params.get("customerId"));
                parameters.put("data", params.get("data"));

                return ServiceDispatcher.callApp(activity, parameters);
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result == null || result.isEmpty()) {
                    wbListener.setError(WalletConstant.CODE_FAIL, "添加失败");
                } else if (!WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setError(result.get("code"), result.get("error"));
                } else if (WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setSuccess(result.get("code"));
                }
            }
        }.execute(JSON.toJSONString(param), null, null);
    }

}
