package com.yzb.card.king.ui.other.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.BaseRequest;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.discount.bean.LbtBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/14 9:58
 * 描述：
 */
public class Init1ItemViewTask  extends BaseRequest implements HttpCallBackData
{
    /**
     * 接口名
     */
    private String serverName = CardConstant.card_app_carouselfigure;
    Map<String, Object> param = new HashMap<String, Object>();
    public Init1ItemViewTask(String typeParentId, String cityId)
    {

        param.put("pageCode", typeParentId);
        param.put("cityId", cityId);
    }

    public void execute()
    {
        sendPostRequest(setParams(AppConstant
                .sessionId, serverName, AppConstant.UUID, param), this);
    }

    private OnParseFinishListener listener;

    public void setOnParseFinishListener(OnParseFinishListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onStart()
    {

    }

    @Override
    public void onSuccess(Object o)
    {
        if (!TextUtils.isEmpty(o+""))
        {
            List<LbtBean> lbtBeans = JSON.parseArray(o+"", LbtBean.class);
            if (lbtBeans != null)
            {
                if (listener != null)
                {
                    listener.onParseFinish(lbtBeans);
                    return;
                }
            }
        }

        if (listener != null)
        {
            listener.onParseFinish(null);
        }
    }

    @Override
    public void onFailed(Object o)
    {
        if (listener != null)
        {
            listener.onParseFinish(null);
        }
    }

    @Override
    public void onCancelled(Object o)
    {
        if (listener != null)
        {
            listener.onParseFinish(null);
        }
    }

    @Override
    public void onFinished()
    {

    }

    @Override
    public void sendRequest(HttpCallBackData callBack)
    {

    }

    public interface OnParseFinishListener
    {
        void onParseFinish(List<LbtBean> lbtBeans);
    }
}
