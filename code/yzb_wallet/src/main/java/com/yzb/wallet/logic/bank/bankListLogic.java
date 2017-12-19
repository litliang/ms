package com.yzb.wallet.logic.bank;

import android.app.Activity;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.yzb.wallet.openInterface.BasePay;
import com.yzb.wallet.sys.CardConstant;
import com.yzb.wallet.sys.ServiceDispatcher;
import com.yzb.wallet.util.WalletConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询可支付银行卡列表
 */
public class bankListLogic extends BasePay {

    private Activity activity;

    private Map<String, String> params;

    public bankListLogic(Activity activity) {
        this.activity = activity;
    }

    /**
     * 可支付银行卡列表
     */
    public void getData(Map<String, String> params) {

        // 判断网络是否异常
        if (!isNetworkAvailable(activity)) {
            NetWorkUnavailable(activity);
            return;
        }

        this.params = params;

        bankList();
    }

    /**
     * 可支付银行卡列表
     */
    private void bankList() {

        new AsyncTask<String, Void, Map<String, String>>() {
            protected Map<String, String> doInBackground(String... params) {
                return ServiceDispatcher.callApp(activity, ServiceDispatcher.setParams(CardConstant.app_card_list, params[0]));
            }

            @Override
            protected void onPostExecute(Map<String, String> result) {
                if (result == null || result.isEmpty()) {
                    wbListener.setError(WalletConstant.CODE_FAIL, WalletConstant.MSG_ERROR);
                } else if (!WalletConstant.CODE_OK.equals(result.get("code"))) {
                    wbListener.setError(result.get("code"), result.get("error"));
                } else if (WalletConstant.CODE_OK.equals(result.get("code"))) {

                    List<Map> bankList = new ArrayList<>();

                    List<Map> list = JSON.parseArray(result.get("data"), Map.class);

                    if (list != null) {
                        for (Map<String, String> map : list) {
                            String sortCode = String.valueOf(map.get("sortCode"));
                            String sortNo = String.valueOf(map.get("sortNo"));
                            String fullNo = String.valueOf(map.get("fullNo"));
                            String cardType = String.valueOf(map.get("cardType"));
                            String bankName = String.valueOf(map.get("bankName"));
                            String typeName = String.valueOf(map.get("typeName"));
                            String limitDay = String.valueOf(map.get("limitDay"));
                            String limitMonth = String.valueOf(map.get("limitMonth"));
                            String logo = String.valueOf(map.get("logo"));
                            String archivesPhoto = String.valueOf(map.get("archivesPhoto"));
                            map.clear();
                            map.put("bankName", bankName);
                            map.put("typeName", typeName);
                            map.put("sortNo", sortNo);
                            map.put("fullNo", fullNo);
                            map.put("cardType", cardType);
                            map.put("sortCode", sortCode);
                            map.put("limitDay", limitDay);
                            map.put("limitMonth", limitMonth);
                            map.put("logo", logo);
                            map.put("archivesPhoto", archivesPhoto);
                            bankList.add(map);
                        }
                    }

                    result.put("data", JSON.toJSONString(bankList));

                    wbListener.setSuccess(result, result.get("code"));
                }
            }
        }.execute(JSON.toJSONString(params), null, null);
    }

}
