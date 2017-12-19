package com.yzb.wallet.logic.pay;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzb.wallet.dialog.WalletAddCardSuccessDialog;
import com.yzb.wallet.dialog.WalletOpen;
import com.yzb.wallet.dialog.WalletPayInfoDialog;
import com.yzb.wallet.dialog.WalletPayMethodDialog;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 转账订单
 */
public class TransferHandle extends BasePay {

    private Activity activity;

    private Map<String, String> params;

    private Boolean showForgetPwd;

    private String token;
    private String customerId;
    private String verifyCode;

    private List<Map> payMethodList = new ArrayList<>();

    private Boolean showBalancePay = true;

    private Boolean showDebitCard = true;

    private Boolean showCreditCard = false;

    // 是否支付成功
    private Boolean paySuccess = false;

    private String NotCardNo;

    private WalletPwdDialog.Builder pwdDialog;

    private WalletVerifyCodeDialog.Builder verifyCodeDialog;

    private WalletPayInfoDialog.Builder payInfoDialog;

    private WalletPayMethodDialog.Builder payMethdoDialog;

    private WalletAddCardSuccessDialog.Builder addCardSuccessDialog;

    public TransferHandle(Activity activity) {
        this.activity = activity;
    }

    /**
     * 支付操作
     */
    public void pay(final Map<String, String> params, final Boolean showForgetPwd) {

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

                // 加载付款方式
                loadPayMethod();

            }
        });

        walletOpen.loading();
    }

    /**
     * 加载付款方式
     */
    private void loadPayMethod() {

        new AsyncTask<String, Void, Map<String, Object>>() {
            protected Map<String, Object> doInBackground(String... data) {

                Map<String, Object> result = new HashMap<String, Object>();

                result.put("code", WalletConstant.CODE_OK);

                // 获取账户列表
                Map<String, String> accountParams = new HashMap<>();
                accountParams.put("mobile", params.get("debitMobile"));
                accountParams.put("customerType", params.get("debitCustomerType"));
                accountParams.put("merchantNo", params.get("merchantNo"));
                accountParams.put("charset", params.get("charset"));
                accountParams.put("sign", params.get("sign"));
                accountParams.put("signType", params.get("signType"));

                // 获取用户账号(余额,红包,礼品卡...)
                Map<String, String> custAccountResult =ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_account_list, JSON.toJSONString(accountParams)));
                if (null == custAccountResult || custAccountResult.isEmpty()) {
                    result.put("code", WalletConstant.CODE_FAIL);
                    return result;
                }
                if (WalletConstant.CODE_OK.equals(custAccountResult.get("code"))) {
                    List<Map> list = JSON.parseArray(custAccountResult.get("data"), Map.class);
                    result.put("accountList", list.size() > 0 ? list : null);
                }

                // 获取银行卡列表
                Map<String, String> cardParams = new HashMap<>();
                cardParams.put("mobile", params.get("debitMobile"));
                cardParams.put("customerType", params.get("debitCustomerType"));
                cardParams.put("notCardNo", NotCardNo);
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
                    WalletToastCustom.sendDialog(activity, activity.getWindow().peekDecorView(), WalletConstant.MSG_QUERY_FAIL, 140);
                    return;
                }

                List<Map> accountList = (List<Map>) payMethod.get("accountList");
                System.out.println("=1=>" + accountList);
                List<Map> bankCardList = (List<Map>) payMethod.get("bankCardList");
                System.out.println("=2=>" + bankCardList);
                if (accountList == null && bankCardList == null) {
                    WalletToastCustom.sendDialog(activity, activity.getWindow().peekDecorView(), WalletConstant.MSG_WITHOUT_PAY, 140);
                } else {
                    // 格式化数据支付方式
                    if (accountList != null) {
                        for (Map<String, String> map : accountList) {
                            String accountType = String.valueOf(map.get("accountType"));
                            String typeName = WalletConstant.ACCOUNT_TEXT(accountType);
                            map.clear();
                            map.put("accountType", accountType);
                            map.put("typeName", typeName);
                            map.put("sortCode", "");
                            map.put("cardType", "");
                            map.put("limitDay", "");
                            map.put("limitMonth", "");
                            map.put("logo", "");
                            map.put("bankId", "");
                            // 判断是否显示账号
                            if(showBalancePay && WalletConstant.PAY_BALANCE.equals(accountType))
                                payMethodList.add(map);
                            /*if(showEnvelopPay && WalletConstant.PAY_RED_ENVELOP.equals(accountType))
                                payMethodList.add(map);
                            if(showGiftPay && WalletConstant.PAY_GIFT_CARD.equals(accountType))
                                payMethodList.add(map);
                            if(showIntegralPay && (WalletConstant.PAY_INTEGRAL_PT.equals(accountType) || WalletConstant.PAY_INTEGRAL_SJ.equals(accountType)))
                                payMethodList.add(map);
                            if(WalletConstant.PAY_CREDIT.equals(accountType))
                                payMethodList.add(map);*/
                        }
                    }

                    if (bankCardList != null) {
                        for (Map<String, String> map : bankCardList) {
                            String accountType = WalletConstant.PAY_BANK;
                            String sortCode = String.valueOf(map.get("sortCode"));
                            String cardType = String.valueOf(map.get("cardType"));
                            String sortNo = String.valueOf(map.get("sortNo"));
                            String bankName = String.valueOf(map.get("bankName"));
                            String typeName = String.valueOf(map.get("typeName"));
                            String limitDay = String.valueOf(map.get("limitDay"));
                            String limitMonth = String.valueOf(map.get("limitMonth"));
                            String logo = String.valueOf(map.get("logo"));
                            String bankId = String.valueOf(map.get("bankId"));
                            // 组织付款方式名称
                            typeName = bankName + typeName + "(尾号" + sortNo + ")";
                            map.clear();
                            map.put("accountType", accountType);
                            map.put("typeName", typeName);
                            map.put("sortCode", sortCode);
                            map.put("cardType", cardType);
                            map.put("limitDay", limitDay);
                            map.put("limitMonth", limitMonth);
                            map.put("logo", logo);
                            map.put("bankId", bankId);
                            payMethodList.add(map);
                        }
                    }

                    // 打开付款详情
                    OpenWalletPayInfoDialog();

                }
            }
        }.execute(JSON.toJSONString(params), null, null);
    }

    /**
     * 打开付款详情
     */
    private void OpenWalletPayInfoDialog() {

        String accountType = "";
        String sortCode = "";
        String methodName = "";

        if(payMethodList.size() > 0){
            Map<String, String> payMethod = payMethodList.get(0);
            accountType = payMethod.get("accountType");
            sortCode = payMethod.get("sortCode");
            methodName = payMethod.get("typeName");
        }

        // 打开付款详情
        payInfoDialog = new WalletPayInfoDialog.Builder(activity);
        payInfoDialog.setSummary(params.get("goodsName"));
        payInfoDialog.setAmount(params.get("amount"));
        payInfoDialog.setAccountType(accountType);
        payInfoDialog.setSortCode(sortCode);
        payInfoDialog.setPayMethodName(methodName);
        // 付款方式
        payInfoDialog.setPayMethodListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // 打开付款方式dialog
                OpenWalletPayMethodDialog();
            }
        });
        // 确认付款
        payInfoDialog.setPayListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String debitType = payInfoDialog.getAccountTypeView().getText().toString();
                String debitSortCode = payInfoDialog.getSortCodeView().getText().toString();

                params.put("debitType", debitType);
                params.put("debitSortCode", debitSortCode);

                // 判断付款方式
                if(WalletConstant.PAY_BANK.equals(debitType))
                    validateCrossPay();// 快捷付款：判断本行、他行支付
                else
                    validateFreePay();// 账户付款：校验支付密码
            }
        });
        // 监控关闭
        payInfoDialog.setDismiss(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

                if(!paySuccess)
                    payFailBackListener.callBack();
            }
        });
        payInfoDialog.create().show();

    }

    /**
     * 显示添加银行卡成功页面
     */
    public void showAddCardSuccess() {

        // 关闭付款方式
        if(payMethdoDialog != null)
            payMethdoDialog.dismiss();

        addCardSuccessDialog = new WalletAddCardSuccessDialog.Builder(activity);
        addCardSuccessDialog.setNextClick(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                System.out.println("=========>继续支付");
                // 关闭添加银行卡成功页面
                addCardSuccessDialog.dismiss();
                // 重新打开付款方式
                OpenWalletPayMethodDialog();
            }
        });
        addCardSuccessDialog.create().show();
    }

    /**
     * 显示/隐藏 现金账户 默认显示
     */
    public void showBalancePay(Boolean showBalancePay){
        this.showBalancePay = showBalancePay;
    }

    /**
     * 显示/隐藏 储蓄卡 默认显示
     */
    public void showDebitCard(Boolean showDebitCard){
        this.showDebitCard = showDebitCard;
    }

    /**
     * 显示/隐藏 信用卡 默认隐藏
     */
    public void showCreditCard(Boolean showCreditCard){
        this.showCreditCard = showCreditCard;
    }

    /**
     * 隐藏该银行卡 默认显示
     */
    public void hiddenCard(String NotCardNo){
        this.NotCardNo = NotCardNo;
    }

    /**
     * 打开付款方式
     */
    private void OpenWalletPayMethodDialog(){

        payMethdoDialog = new WalletPayMethodDialog.Builder(activity);
        payMethdoDialog.setPayMethodList(payMethodList);
        payMethdoDialog.setChosePayMethod(new WalletPayMethodDialog.Builder.chosePayMethod() {
            @Override
            public void getPayMethod(Map<String, String> payMethod) {

                TextView accountTypeView = payInfoDialog.getAccountTypeView();
                TextView sortCodeView = payInfoDialog.getSortCodeView();
                TextView payMethodNameView = payInfoDialog.getPayMethodNameView();

                String accountType = payMethod.get("accountType");
                String sortCode = payMethod.get("sortCode");

                accountTypeView.setText(accountType);
                sortCodeView.setText(sortCode);
                payMethodNameView.setText(payMethod.get("typeName"));

                // 选择付款方式回调数据
                if(payMethodListener != null){

                    Map<String, String> data = new HashMap<>();
                    String cardType = payMethod.get("cardType");

                    // 1、钱包余额
                    if(WalletConstant.PAY_BALANCE.equals(accountType)){
                        data.put("payType", "1");
                        data.put("payDetailId", "");
                    }

                    // 2、信用卡
                    if(WalletConstant.PAY_BANK.equals(accountType) && WalletConstant.CREDIT_CARD.equals(cardType)){
                        data.put("payType", "2");
                        data.put("payDetailId", sortCode);
                    }

                    // 3、储蓄卡
                    if(WalletConstant.PAY_BANK.equals(accountType) && WalletConstant.DEBIT_CARD.equals(cardType)){
                        data.put("payType", "3");
                        data.put("payDetailId", sortCode);
                    }

                    // 4、礼品卡余额
                    if(WalletConstant.PAY_GIFT_CARD.equals(accountType)){
                        data.put("payType", "4");
                        data.put("payDetailId", "");
                    }

                    // 5、红包余额
                    if(WalletConstant.PAY_RED_ENVELOP.equals(accountType)){
                        data.put("payType", "5");
                        data.put("payDetailId", "");
                    }

                    // 6、信用余额
                    if(WalletConstant.PAY_CREDIT.equals(accountType)){
                        data.put("payType", "7");
                        data.put("payDetailId", "");
                    }

                    // 回调
                    payMethodListener.callBack(data);

                }

                payMethdoDialog.dismiss();
            }
        });
        payMethdoDialog.addCard(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                addCardBackListener.callBack();
            }
        });
        payMethdoDialog.create().show();
    }

    /**
     * 判断本行、他行支付
     */
    private void validateCrossPay(){

        Map<String, String> data = new HashMap<String, String>();

        data.put("mobile", params.get("debitMobile"));
        data.put("customerType", params.get("debitCustomerType"));
        data.put("sortCode", params.get("debitSortCode"));

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

                    String payType = cardInfo.get("payType");

                    if(WalletConstant.STATUS_OFF.equals(payType)){
                        // 不可支付卡返回信息
                        Map<String, String> resultMap = new HashMap<>();
                        resultMap.put("sortCode", cardInfo.get("sortCode"));
                        resultMap.put("cardType", cardInfo.get("cardType"));
                        resultMap.put("name", cardInfo.get("name"));
                        resultMap.put("cardNo", cardInfo.get("cardNo"));

                        wbListener.setSuccess(resultMap, WalletConstant.PAY_TYPE_OFF);
                    }else{
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

                data.put("mobileNo", params.get("debitMobile"));
                data.put("customerType", params.get("debitCustomerType"));
                data.put("sortCode", params.get("debitSortCode"));
                data.put("amount", params.get("amount"));
                data.put("fee", params.get("fee"));
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
                transfer();
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
        validateParams.put("mobile", params.get("debitMobile"));
        validateParams.put("customerType", params.get("debitCustomerType"));
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
                    transfer();
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

        data.put("mobile", params.get("debitMobile"));
        data.put("customerType", params.get("debitCustomerType"));
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
                    // 校验支付密码成功调用转账
                    transfer();
                }
            }
        }.execute(JSON.toJSONString(data), null, null);
    }

    /**
     * 转账
     */
    private void transfer() {

        Map<String, String> data = new HashMap<String, String>();

        String amount = String.valueOf(params.get("amount"));
        data.put("amount", (new BigDecimal(amount)).setScale(2).toString());

        String fee = String.valueOf(params.get("fee"));
        if(!StringUtil.isEmpty(fee))
            data.put("fee", (new BigDecimal(fee)).setScale(2).toString());

        data.put("orderNo", params.get("orderNo"));
        data.put("goodsName", params.get("goodsName"));
        data.put("transIp", params.get("transIp"));

        data.put("debitMobile", params.get("debitMobile"));
        data.put("debitCustomerType", params.get("debitCustomerType"));
        data.put("debitType", params.get("debitType"));
        data.put("debitSortCode", params.get("debitSortCode"));

        data.put("verifyCode", verifyCode);
        data.put("token", token);
        data.put("customerId", customerId);

        data.put("creditType", params.get("creditType"));
        data.put("creditMobile", params.get("creditMobile"));
        data.put("creditCustomerType", params.get("creditCustomerType"));
        data.put("creditCardName", params.get("creditCardName"));
        data.put("creditCardNo", params.get("creditCardNo"));
        data.put("bankMark", params.get("bankMark"));
        data.put("notifyUrl", params.get("notifyUrl"));

        data.put("merchantNo", params.get("merchantNo"));
        data.put("charset", params.get("charset"));
        data.put("sign", params.get("sign"));
        data.put("signType", params.get("signType"));

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_transfer, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result == null || result.isEmpty()) {
                    wbListener.setError(WalletConstant.CODE_FAIL, "转账失败");
                } else if (!WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setError(result.get("code"), result.get("error"));
                } else if (WalletConstant.CODE_OK.equals(result.get("code"))) {

                    Map<String, String> resultMap = JSON.parseObject(String.valueOf(result.get("data")), Map.class);

                    wbListener.setSuccess(WalletConstant.CODE_OK);

                    wbListener.setSuccess(resultMap, WalletConstant.CODE_OK);

                    // 支付成功
                    paySuccess = true;

                    if (payInfoDialog != null)
                        payInfoDialog.dismiss();
                }

                if (pwdDialog != null)
                    pwdDialog.dismiss();
                if (verifyCodeDialog != null)
                    verifyCodeDialog.dismiss();

            }
        }.execute(JSON.toJSONString(data), null, null);
    }

}
