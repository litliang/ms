package com.yzb.wallet.logic.comm;

import android.app.Activity;
import android.widget.Toast;


import java.util.Map;

/**
 * Created by lirr on 2017/12/19.
 */

public class ToastUtil {
    public static boolean toast = false;

    public static void toastcost(Activity aty,String success, String serviceName, Map paramMap, String respdata, long starttime) {
        if (!ToastUtil.toast) return;
        final long time2 = System.currentTimeMillis();
        long plus = time2 - starttime;
        long cost = plus / 1000;
        String param = JsonUtil.entityToJson(paramMap);
        String request = "request:\n【" + paramMap.get("serviceName") + "】:\n" + param;
        String result = "\n\n" + "Response: \n" + respdata;
        String stime = " cost: " + cost + " 秒";
        String toast = respdata.contains("0000")?"成功":"失败" + " " + stime + "\n\n" + request + result + stime;
        Toast.makeText(aty, toast, Toast.LENGTH_LONG).show();
    }
}
