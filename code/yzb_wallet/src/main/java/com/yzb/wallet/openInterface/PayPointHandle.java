package com.yzb.wallet.openInterface;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.yzb.wallet.dialog.WalletOpen;
import com.yzb.wallet.dialog.WalletPayInfoDialog;
import com.yzb.wallet.dialog.WalletPwdDialog;
import com.yzb.wallet.dialog.WalletVerifyCodeDialog;
import com.yzb.wallet.sys.CardConstant;
import com.yzb.wallet.sys.ServiceDispatcher;
import com.yzb.wallet.util.DateUtil;
import com.yzb.wallet.util.HashUtil;
import com.yzb.wallet.util.StringUtil;
import com.yzb.wallet.util.WalletConstant;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 积分付款
 */
public class PayPointHandle extends BasePay {

    private Activity activity;

    private WalletPwdDialog.Builder pwdDialog;

    private Map<String, String> params;

    public PayPointHandle(Activity activity) {
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
        // 打开支付密码输入
        OpenWalletPwdDialog();
    }

    /**
     * 检查参数
     */
    private String checkParamsMsg(Map<String, String> params) {

        // 判断是否登录
        String loginMsg = checkLoginMsg(params);
        if (!StringUtil.isEmpty(loginMsg))
            return loginMsg;

        if (StringUtil.isEmpty(params.get("customerId"))) {
            return "用户ID不能为空";
        }
        if (StringUtil.isEmpty(params.get("point"))) {
            return "积分不能为空";
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
                params.put("payPasswd", pwd);
                // 积分转账
                accountPoint();
            }
        });
        pwdDialog.create().show();
    }

    /**
     * 获取账户列表(根据用户id和账号类型)
     */
    private void accountPoint() {

        final Map<String, String> param = new HashMap<String, String>();

        param.put("customerId", params.get("customerId"));
        param.put("accountType", WalletConstant.PAY_INTEGRAL_PT);// 积分账户

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_api_account_list, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result == null || result.isEmpty()) {
                    wbListener.setError(WalletConstant.CODE_FAIL, "获取失败");
                    if (pwdDialog != null)
                        pwdDialog.dismiss();
                } else if (!WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setError(result.get("code"), result.get("error"));
                    if (pwdDialog != null)
                        pwdDialog.dismiss();
                } else if (WalletConstant.CODE_OK.equals(result.get("code"))) {
                    List<Map> resultList = JSON.parseArray(result.get("data"), Map.class);
                    if (resultList.size() == 0) {
                        wbListener.setError(WalletConstant.CODE_NULL, "账户不存在");
                        if (pwdDialog != null)
                            pwdDialog.dismiss();
                    } else {
                        Map<String, Object> resultMap = resultList.get(0);
                        // 成功转账
                        String accountId = String.valueOf(resultMap.get("accountId"));
                        if (StringUtil.isEmpty(accountId))
                            wbListener.setError(WalletConstant.CODE_NULL, "账户不存在");
                        else
                            transfer(accountId);
                    }
                }
            }
        }.execute(JSON.toJSONString(param), null, null);
    }

    /**
     * 转账-余额账户
     */
    private void transfer(String accountId) {

        BigDecimal point = new BigDecimal(String.valueOf(params.get("point"))).divide(new BigDecimal("1000")).setScale(2, BigDecimal.ROUND_HALF_UP);

        Map<String, String> param = new HashMap<String, String>();
        String payPasswd = param.get("payPasswd");
        param.put("payPasswd", !StringUtil.isEmpty(payPasswd) ? HashUtil.getMD5(payPasswd) : null);
        param.put("orderTime", DateUtil.date2String(new Date(), DateUtil.DEFAULT_DATE_FORMAT));
        param.put("accountId", accountId);
        param.put("credit", WalletConstant.SYS_POINT);// 系统积分账户
        param.put("amount", point.toString());
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
            }
        }.execute(JSON.toJSONString(param), null, null);

    }

}
