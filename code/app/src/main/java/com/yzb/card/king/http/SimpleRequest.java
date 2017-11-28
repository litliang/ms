package com.yzb.card.king.http;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：简单请求类
 * 作者：殷曙光
 * 日期：2016/8/31 17:47
 */
public class SimpleRequest<T> extends BaseRequest
{
    private Map<String, Object> param;
    private String serverName;

    public SimpleRequest()
    {
    }

    public SimpleRequest(String serverName)
    {
        this.serverName = serverName;
    }

    public SimpleRequest(String serverName, Map<String, Object> param, Listener listener)
    {
        this.serverName = serverName;
        this.param = param;
        this.listener = listener;
    }

    public SimpleRequest(String serverName, Map<String, Object> param)
    {
        this.serverName = serverName;
        this.param = param;
    }

    public void sendPostRequest()
    {
        sendRequest(null);
    }

    @Override
    public void sendRequest(HttpCallBackData callBack)
    {
        if (param == null)
        {
            param = new HashMap<>();
        }
        sendPostRequest(setParams(AppConstant.sessionId, serverName, AppConstant.UUID, param), callBack);
    }

    public void setParam(Map<String, Object> param)
    {
        this.param = param;
    }

    public void setServerName(String serverName)
    {
        this.serverName = serverName;
    }

    /**
     * 使用此方法时必须重写parseData方法
     *
     * @param callBack
     */
    public void sendRequestNew(BaseCallBack<T> callBack)
    {
        sendRequest(new CallBack(callBack));
    }

    class CallBack extends HttpCallBackImpl
    {
        private BaseCallBack<T> callBack;

        public CallBack(BaseCallBack<T> callBack)
        {
            this.callBack = callBack;
        }

        @Override
        public void onSuccess(Object o)
        {
            T data = parseData(String.valueOf(o));
            if (callBack != null)
            {
                callBack.onSuccess(data);
            }
        }

        @Override
        public void onFailed(Object o)
        {
            if (callBack != null)
            {
                Error error = JSON.parseObject(JSON.toJSONString(o), Error.class);
                if (error == null)
                {
                    error = new Error("", "");
                }
                callBack.onFail(error);
            }
        }
    }

    /**
     * 使用sendRequestNew 时必须重写此方法
     *
     * @param data
     * @return
     */
    protected T parseData(String data)
    {
        return null;
    }
}
