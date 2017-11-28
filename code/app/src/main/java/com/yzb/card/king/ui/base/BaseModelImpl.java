package com.yzb.card.king.ui.base;

import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.util.LogUtil;

import java.util.Map;

/**
 * 功能：Model层实现类的基类；
 *
 * @author:gengqiyun
 * @date: 2016/10/8
 */
public abstract class BaseModelImpl
{
    protected BaseMultiLoadListener loadListener;

    protected String serviceName;

    protected Map<String, Object> paramMap;

    protected boolean event_tag = true;

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    protected BaseModelImpl(BaseMultiLoadListener listener)
    {
        this.loadListener = listener;
    }

    protected void loadData(final boolean event_tag, Map<String, Object> paramMap)
    {
        this.event_tag = event_tag;
        this.paramMap = paramMap;
        sendRequest();
    }

    /**
     * 发送请求；
     */
    protected void sendRequest()
    {
        new SimpleRequest(serviceName, paramMap, new BaseRequest.Listener()
        {
            @Override
            public void onSuccess(String data)
            {
                afterSuccess(data);
            }

            @Override
            public void onFail(String failMsg)
            {
                if (loadListener != null)
                {
                    loadListener.onListenError(failMsg);
                }
            }
        })
        {
            @Override
            protected int getConnectTimeOut()
            {
                if (getTimeOut() <= 0)
                {
                    return super.getConnectTimeOut();
                }
                return getTimeOut();
            }
        }.sendPostRequest();
    }

    protected int getTimeOut()
    {
        return 0;
    }

    /**
     * 成功；
     *
     * @param data
     */
    protected abstract void afterSuccess(String data);
}
