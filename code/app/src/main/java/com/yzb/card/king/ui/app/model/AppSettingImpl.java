package com.yzb.card.king.ui.app.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.HttpCallBackImpl;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.ui.app.bean.AppSettingItem;
import com.yzb.card.king.ui.app.bean.NoticeMenu;
import com.yzb.card.king.ui.app.model.IAppSetting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/12 17:24
 */
public class AppSettingImpl implements IAppSetting
{
    private HttpCallBackImpl callBack;

    public AppSettingImpl(HttpCallBackImpl callBack)
    {
        this.callBack = callBack;
    }

    @Override
    public void loadNotice()
    {
        Map<String, Object> param = new HashMap<>();
        SimpleRequest request = new SimpleRequest("RemindSetupList", param);
        request.sendRequest(new HttpCallBackImpl()
        {
            @Override
            public void onSuccess(Object o)
            {
                NoticeMenu menu =JSON.parseObject((String)o,NoticeMenu.class);

                callBack.onSuccess(menu);
            }

            @Override
            public void onFailed(Object o)
            {
                if (callBack != null)
                {
                    callBack.onFailed(o);
                }
            }
        });
    }

    @Override
    public void save(String status, List<AppSettingItem> dataList)
    {
        Map<String, Object> param = new HashMap<>();
        param.put("status", status);
        param.put("remindList", JSON.toJSONString(dataList));
        SimpleRequest request = new SimpleRequest("UpdateRemindSetup", param);
        request.sendRequest(new HttpCallBackImpl()
        {
            @Override
            public void onSuccess(Object o)
            {
            }

            @Override
            public void onFailed(Object o)
            {
            }
        });
    }
}
