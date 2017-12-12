package com.yzb.card.king.ui.base;

import android.app.Activity;
import android.widget.Toast;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.util.JsonUtil;
import com.yzb.card.king.util.LogUtil;

import java.util.Map;

/**
 * 功能：Model层实现类的基类；
 *
 * @author:gengqiyun
 * @date: 2016/10/8
 */
public abstract class BaseModelImpl {
    protected BaseMultiLoadListener loadListener;

    protected String serviceName;

    protected Map<String, Object> paramMap;

    protected boolean event_tag = true;

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    protected BaseModelImpl(BaseMultiLoadListener listener) {
        this.loadListener = listener;
    }

    protected void loadData(final boolean event_tag, Map<String, Object> paramMap) {
        this.event_tag = event_tag;
        this.paramMap = paramMap;
        sendRequest();
    }

    /**
     * 发送请求；
     */
    protected void sendRequest() {


        new SimpleRequest(serviceName, paramMap, new BaseRequest.Listener() {
            @Override
            public void onSuccess(String data) {
                afterSuccess(data);
            }

            @Override
            public void onFail(String failMsg) {
                if (loadListener != null) {
                    loadListener.onListenError(failMsg);
                }
            }
        }) {
            @Override
            protected int getConnectTimeOut() {
                if (getTimeOut() <= 0) {
                    return super.getConnectTimeOut();
                }
                return getTimeOut();
            }
        }.sendPostRequest();
    }

    public static boolean toast;

    public static void toastcost(String success, String serviceName, Map paramMap, String data, long time) {
        if (!toast) return;
        final long time2 = System.currentTimeMillis();
        long plus = time2 - time;
        long cost = plus / 1000;
        Activity aty = GlobalApp.getInstance().getPublicActivity();
        String param = JsonUtil.entityToJson(paramMap);
        String request = "request:\n【" + serviceName + "】:\n" + param;
        String result = "\n\n" + "Response: \n" + data;
        String stime = " cost: " + cost + " 秒";
        String toast = success + " " + stime + "\n\n" + request + result + stime;
        Toast.makeText(aty, toast, Toast.LENGTH_LONG).show();
    }

    protected int getTimeOut() {
        return 0;
    }

    /**
     * 成功；
     *
     * @param data
     */
    protected abstract void afterSuccess(String data);
}
