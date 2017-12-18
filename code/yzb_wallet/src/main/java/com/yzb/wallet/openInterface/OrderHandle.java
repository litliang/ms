package com.yzb.wallet.openInterface;

import android.app.Activity;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.yzb.wallet.sys.CardConstant;
import com.yzb.wallet.sys.ServiceDispatcher;
import com.yzb.wallet.util.StringUtil;
import com.yzb.wallet.util.WalletConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单处理
 */
public class OrderHandle extends BasePay {

    private Activity activity;

    public OrderHandle(Activity activity) {
        this.activity = activity;
    }

    /**
     * 订单汇总
     */
    public void stat(String id) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        // 检查参数
        String msg = checkParamsMsg(id);
        if (!StringUtil.isEmpty(msg)) {
            // 失败接口返回
            wbListener.setError(WalletConstant.CODE_INVALID, msg);
            return;
        }

        // 订单汇总
        orderStat(id);
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
     * 查询订单汇总
     */
    private void orderStat(String id) {

        final Map<String, String> param = new HashMap<String, String>();
        param.put("id", id);

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_api_order_stat, params[0]));
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

}
