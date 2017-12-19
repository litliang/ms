package com.yzb.wallet.openInterface;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.wallet.dialog.WalletOpen;
import com.yzb.wallet.dialog.WalletPayInfoDialog;
import com.yzb.wallet.dialog.WalletPwdDialog;
import com.yzb.wallet.dialog.WalletToastCustom;
import com.yzb.wallet.dialog.WalletVerifyCodeDialog;
import com.yzb.wallet.sys.CardConstant;
import com.yzb.wallet.sys.ServiceDispatcher;
import com.yzb.wallet.util.DateUtil;
import com.yzb.wallet.util.HashUtil;
import com.yzb.wallet.util.StringUtil;
import com.yzb.wallet.util.WalletConstant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 信用还款
 */
public class PayCreditHandle extends BasePay {

    private Activity activity;

    private WalletPwdDialog.Builder pwdDialog;
    private WalletVerifyCodeDialog.Builder verifyCodeDialog;
    private WalletPayInfoDialog.Builder payInfoDialog;

    private Map<String, String> params;
    private String accountType;//支付方式

    public PayCreditHandle(Activity activity) {
        this.activity = activity;
    }

    /**
     * 支付操作
     */
    public void pay(Map<String, String> params) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        this.params = params;
        // 打开钱包loading
        WalletOpen.Builder walletOpen = new WalletOpen.Builder(activity);

        walletOpen.setDismiss(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                System.out.println("============>关闭loading");
                // 处理操作
                handle();
            }
        });

        walletOpen.loading();
    }

    /**
     * 支付处理
     */
    private void handle() {

        if (params.isEmpty()) return;
        // 检查参数
        String msg = checkParamsMsg(params);
        if (!StringUtil.isEmpty(msg)) {
            // 失败接口返回
            wbListener.setError(WalletConstant.CODE_INVALID, msg);
            return;
        }
        if (WalletConstant.PAY_BANK.equals(accountType)) {
            // TODO：支付方式为银行卡，默认支付成功(银行卡接口暂无)
            wbListener.setSuccess(WalletConstant.CODE_OK);
        }
        // 账户余额支付,查询accountId后打开支付密码
        getCustAccount();
    }

    /**
     * 检查参数
     */
    private String checkParamsMsg(Map<String, String> params) {

        // 判断是否登录
        String loginMsg = checkLoginMsg(params);
        if (!StringUtil.isEmpty(loginMsg))
            return loginMsg;

        // 金额不能为空
        if (StringUtil.isEmpty(params.get("amount"))) {
            return "金额不能为空";
        }
        if (StringUtil.isEmpty(params.get("summary"))) {
            return "摘要不能为空";
        }
        return "";
    }

    /**
     * 打开支付密码输入
     */
    private void OpenWalletPwdDialog() {

        pwdDialog = new WalletPwdDialog.Builder(activity);
        pwdDialog.setKeyBoardClick(new WalletPwdDialog.Builder.KeyBoardClickListener() {
            @Override
            public void backPassword(String pwd) {
                System.out.println("=========支付密码==>" + pwd);
                // 转账
                transferBalance(pwd);
            }
        });
        pwdDialog.create().show();
    }

    /**
     * 获取用户账户
     */
    private void getCustAccount() {

        final Map<String, String> param = new HashMap<String, String>();

        System.out.println("=======账户类型=======>" + WalletConstant.PAY_TEXT(accountType));

        param.put("accountType", accountType);

        new AsyncTask<String, Void, List<Map>>() {
            protected List<Map> doInBackground(String... params) {
                List<Map> result = new ArrayList<Map>();
                // 获取用户账号(余额,红包,礼品卡...)
                Map<String, String> custAccountResult = ServiceDispatcher.call(activity, ServiceDispatcher.setParams(WalletConstant.sessionId, CardConstant.app_api_appCustomerAccountList, WalletConstant.UUID, params[0]));
                if (null == custAccountResult || custAccountResult.isEmpty()) return null;
                if (WalletConstant.CODE_OK.equals(custAccountResult.get("code"))) {
                    result = JSON.parseArray(custAccountResult.get("data"), Map.class);
                }
                return result;
            }

            @Override
            protected void onPostExecute(List<Map> result) {
                if (result == null) {
                    // 失败接口返回
                    wbListener.setError(WalletConstant.CODE_FAIL, WalletConstant.MSG_QUERY_FAIL);
                } else if (result.size() == 0 || (result.size() > 0 && result.get(0).isEmpty())) {
                    // 失败接口返回
                    wbListener.setError(WalletConstant.CODE_FAIL, WalletConstant.MSG_WITHOUT_PAY);
                } else {
                    System.out.println("=======支付账户=======>" + String.valueOf(result.get(0).get("accountId")));
                    params.put("accountId", String.valueOf(result.get(0).get("accountId")));
                    // 查询支付账户后打开支付密码输入界面
                    OpenWalletPwdDialog();
                }
            }
        }.execute(JSON.toJSONString(param), null, null);
    }

    /**
     * 转账-余额账户
     */
    private void transferBalance(String pwd) {

        String amount = params.get("amount");

        if (StringUtil.isEmpty(amount))
            return;

        if (new BigDecimal(amount).compareTo(new BigDecimal(WalletConstant.AMOUNT_ZERO)) == 0)
            return;

        Map<String, String> param = new HashMap<String, String>();
        param.put("payPasswd", !StringUtil.isEmpty(pwd) ? HashUtil.getMD5(pwd) : null);
        param.put("orderTime", DateUtil.date2String(new Date(), DateUtil.DEFAULT_DATE_FORMAT));
        param.put("accountId", String.valueOf(params.get("accountId")));
        param.put("credit", WalletConstant.SYS_ACCOUNT_FEE);// 系统收费账户
        param.put("amount", amount);
        param.put("summary", params.get("summary"));
        param.put("batchNo", "");
        param.put("reserve", "");

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.call(activity, ServiceDispatcher.setParams(WalletConstant.sessionId, CardConstant.app_api_transfer, WalletConstant.UUID, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result != null && !result.isEmpty() && WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setSuccess(result, result.get("code"));
                } else {
                    String code = WalletConstant.CODE_FAIL;
                    String error = "支付失败";
                    if (null != result && !result.isEmpty()) {
                        code = result.get("code");
                        error = result.get("error");
                    }
                    wbListener.setError(code, error);
                }
                if (pwdDialog != null)
                    pwdDialog.dismiss();
                if (payInfoDialog != null)
                    payInfoDialog.dismiss();
            }
        }.execute(JSON.toJSONString(param), null, null);

    }

}
