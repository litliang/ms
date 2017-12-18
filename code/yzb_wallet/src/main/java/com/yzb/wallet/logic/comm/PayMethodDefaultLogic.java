package com.yzb.wallet.logic.comm;

import android.app.Activity;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.yzb.wallet.openInterface.BasePay;
import com.yzb.wallet.sys.CardConstant;
import com.yzb.wallet.sys.ServiceDispatcher;
import com.yzb.wallet.util.WalletConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询支付方式(默认)
 */
public class PayMethodDefaultLogic extends BasePay {

    private Activity activity;

    private Map<String, String> params;

    private Boolean showBalancePay = true;
    private Boolean showEnvelopPay = true;
    private Boolean showGiftPay = true;
    private Boolean showIntegralPay = false;
    private Boolean showDebitCard = true;
    private Boolean showCreditCard = true;

    public PayMethodDefaultLogic(Activity activity) {
        this.activity = activity;
    }

    /**
     * 获取支付列表
     */
    public void payMethod(Map<String, String> params) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        this.params = params;

        // 获取全部付款方式
        loadPayMethod();
    }

    /**
     * 显示现金账号
     */
    public void showBalancePay(Boolean showBalancePay){
        this.showBalancePay = showBalancePay;
    }

    /**
     * 显示红包账号
     */
    public void showEnvelopPay(Boolean showEnvelopPay){
        this.showEnvelopPay = showEnvelopPay;
    }

    /**
     * 显示礼品卡账号
     */
    public void showGiftPay(Boolean showGiftPay){
        this.showGiftPay = showGiftPay;
    }

    /**
     * 显示积分账号
     */
    public void showIntegralPay(Boolean showIntegralPay){
        this.showIntegralPay = showIntegralPay;
    }

    /**
     * 显示借记卡
     */
    public void showDebitCard(Boolean showDebitCard){
        this.showDebitCard = showDebitCard;
    }

    /**
     * 显示信用卡
     */
    public void showCreditCard(Boolean showCreditCard){
        this.showCreditCard = showCreditCard;
    }

    /**
     * 获取全部付款方式
     */
    private void loadPayMethod() {

        new AsyncTask<String, Void, Map<String, Object>>() {
            protected Map<String, Object> doInBackground(String... data) {

                Map<String, Object> result = new HashMap<String, Object>();

                result.put("code", WalletConstant.CODE_OK);

                // 获取用户账号(余额,红包,礼品卡...)
                Map<String, String> custAccountResult = ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_account_list, data[0]));
                if (null == custAccountResult || custAccountResult.isEmpty()) {
                    result.put("code", WalletConstant.CODE_FAIL);
                    return result;
                }
                if (WalletConstant.CODE_OK.equals(custAccountResult.get("code"))) {
                    List<Map> list = JSON.parseArray(custAccountResult.get("data"), Map.class);
                    result.put("accountList", list.size() > 0 ? list : null);
                }

                Map<String, String> cardParams = new HashMap<String, String>();
                cardParams.put("mobile", params.get("mobile"));
                cardParams.put("customerType", params.get("customerType"));
                cardParams.put("merchantNo", params.get("merchantNo"));
                cardParams.put("charset", params.get("charset"));
                cardParams.put("sign", params.get("sign"));
                cardParams.put("signType", params.get("signType"));

                if(showDebitCard)
                    cardParams.put("cardType", WalletConstant.DEBIT_CARD);
                else if(showCreditCard)
                    cardParams.put("cardType", WalletConstant.CREDIT_CARD);

                if(showDebitCard && showCreditCard)
                    cardParams.put("cardType", "");

                cardParams.put("limitDay", "1");

                // 获取银行卡列表
                Map<String, String> bankCardResult = ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_card_list, JSON.toJSONString(cardParams)));
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
        }.execute(JSON.toJSONString(params), null, null);
    }

    /**
     * 加载数据
     */
    public void addData(List<Map> accountList, List<Map> bankCardList) {

        // 格式化数据支付方式
        List<Map> payMethodList = new ArrayList<>();

        if (accountList != null) {
            for (Map<String, String> map : accountList) {
                String accountType = String.valueOf(map.get("accountType"));
                String typeName = WalletConstant.ACCOUNT_TEXT(accountType);
                String limitSingle = String.valueOf(map.get("limitSingle"));
                String limitDay = String.valueOf(map.get("limitDay"));
                String limitMonth = String.valueOf(map.get("limitMonth"));
                String balance = String.valueOf(map.get("balance"));
                map.clear();
                map.put("accountType", accountType);
                map.put("sortCode", "");
                map.put("bankId", "");
                map.put("bankName", "");
                map.put("bankNo", "");
                map.put("sortNo", "");
                map.put("typeName", typeName);
                map.put("cardType", "");
                map.put("name", "");
                map.put("reservedMobile", "");
                map.put("limitSingle", "");
                map.put("limitDay", balance);
                map.put("limitMonth", "");
                map.put("logo", "");
                // 判断是否显示账号
                if(showBalancePay && WalletConstant.PAY_BALANCE.equals(accountType))
                    payMethodList.add(map);
                if(showEnvelopPay && WalletConstant.PAY_RED_ENVELOP.equals(accountType))
                    payMethodList.add(map);
                if(showGiftPay && WalletConstant.PAY_GIFT_CARD.equals(accountType))
                    payMethodList.add(map);
                if(showIntegralPay && (WalletConstant.PAY_INTEGRAL_PT.equals(accountType) || WalletConstant.PAY_INTEGRAL_SJ.equals(accountType)))
                    payMethodList.add(map);
                if(WalletConstant.PAY_CREDIT.equals(accountType))
                    payMethodList.add(map);
            }
        }

        if (bankCardList != null) {
            for (Map<String, String> map : bankCardList) {
                String accountType = WalletConstant.PAY_BANK;
                String sortCode = String.valueOf(map.get("sortCode"));
                String bankId = String.valueOf(map.get("bankId"));
                String fullNo = String.valueOf(map.get("fullNo"));
                String sortNo = String.valueOf(map.get("sortNo"));
                String bankName = String.valueOf(map.get("bankName"));
                String typeName = String.valueOf(map.get("typeName"));
                String cardType = String.valueOf(map.get("cardType"));
                String name = String.valueOf(map.get("name"));
                String reservedMobile = String.valueOf(map.get("reservedMobile"));
                String limitSingle = String.valueOf(map.get("limitSingle"));
                String limitDay = String.valueOf(map.get("limitDay"));
                String limitMonth = String.valueOf(map.get("limitMonth"));
                String logo = String.valueOf(map.get("logo"));
                map.clear();
                map.put("accountType", accountType);
                map.put("sortCode", sortCode);
                map.put("bankId", bankId);
                map.put("bankName", bankName);
                map.put("bankNo", fullNo);
                map.put("sortNo", sortNo);
                map.put("typeName", typeName);
                map.put("cardType", cardType);
                map.put("name", name);
                map.put("reservedMobile", reservedMobile);
                map.put("limitSingle", limitSingle);
                map.put("limitDay", limitDay);
                map.put("limitMonth", limitMonth);
                map.put("logo", logo);
                payMethodList.add(map);
            }
        }

        Map<String, String> result = new HashMap<>();
        result.put("code", WalletConstant.CODE_OK);
        result.put("data", JSON.toJSONString(payMethodList));

        wbListener.setSuccess(result, WalletConstant.CODE_OK);

    }

}
