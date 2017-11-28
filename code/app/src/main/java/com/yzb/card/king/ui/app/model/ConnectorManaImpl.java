package com.yzb.card.king.ui.app.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.HttpCallBackImpl;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.bean.Connector;
import com.yzb.card.king.ui.app.model.IConnectorMana;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/13 11:07
 */
public class ConnectorManaImpl implements IConnectorMana
{
    private HttpCallBackImpl callBack;

    public ConnectorManaImpl(HttpCallBackImpl callBack)
    {
        this.callBack = callBack;
    }

    @Override
    public void loadConn()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("type", "2");//1,积分联系人 2,常用联系人
        SimpleRequest request = new SimpleRequest(CardConstant.DISCOUNT_CONTACTSLIST, param);
        request.sendRequest(new HttpCallBackImpl()
        {
            @Override
            public void onSuccess(Object o)
            {
                List<Connector> connectors = JSON.parseArray((String) o, Connector.class);
                callBack.onSuccess(connectors);
            }

            @Override
            public void onFailed(Object o)
            {
                //String msg = "加载联系人失败";
                callBack.onFailed(String.valueOf(o));
            }
        });

    }

    @Override
    public void updateConn(Connector connector)
    {
        Map<String, Object> param = new HashMap<>();
        param.put("relationship", connector.relationship);
        param.put("contactsId", connector.contactsId);
        param.put("nickName", connector.nickName);
        param.put("mobile", connector.mobile);
        param.put("type", connector.type);
        param.put("status", "1");//1有效；0无效
        param.put("isDefault", connector.isDefault ? "1" : "0");
        SimpleRequest request = new SimpleRequest(CardConstant.UPDATE_CONNECTOR, param);
        request.sendRequest(new HttpCallBackImpl()
        {
            @Override
            public void onSuccess(Object o)
            {
                loadConn();
            }

            @Override
            public void onFailed(Object o)
            {
                String msg = "更新联系人失败";
                callBack.onFailed(msg);
            }
        });
    }

    @Override
    public void deleteConn(Connector connector)
    {
        Map<String, Object> param = new HashMap<>();
        param.put("contactsId", connector.contactsId);
        param.put("status", "0");
        SimpleRequest request = new SimpleRequest(CardConstant.DELETE_CONNECTOR, param);
        request.sendRequest(new HttpCallBackImpl()
        {
            @Override
            public void onSuccess(Object o)
            {
                loadConn();
            }

            @Override
            public void onFailed(Object o)
            {
                String msg = "删除联系人失败";
                callBack.onFailed(msg);
            }
        });
    }

    @Override
    public void setDefaultConn(Connector connector)
    {
        Map<String, Object> param = new HashMap<>();
        param.put("contactsId", connector.contactsId);
        SimpleRequest request = new SimpleRequest(CardConstant.SET_DEFAULT_CONNECTOR, param);
        request.sendRequest(new HttpCallBackImpl()
        {
            @Override
            public void onSuccess(Object o)
            {
                loadConn();
            }

            @Override
            public void onFailed(Object o)
            {
                String msg = "设置默认联系人失败";
                callBack.onFailed(msg);
            }
        });

    }

    @Override
    public void addConnector(Connector connector)
    {
        Map<String, Object> param = new HashMap<>();
        param.put("relationship", connector.relationship);
        param.put("nickName", connector.nickName);
        param.put("mobile", connector.mobile);
        param.put("type", connector.type);
        SimpleRequest request = new SimpleRequest(CardConstant.ADD_CONNECTOR, param);
        request.sendRequest(new HttpCallBackImpl()
        {
            @Override
            public void onSuccess(Object o)
            {
                loadConn();
            }

            @Override
            public void onFailed(Object o)
            {
                String msg = "添加联系人失败";
                callBack.onFailed(msg);
            }
        });
    }
}
