package com.yzb.card.king.ui.my.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzb.card.king.bean.my.AccountInfoBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：口令生成；
 *
 * @author:gengqiyun
 * @date: 2017/1/19
 */
public class CommandModel extends BaseModelImpl
{
    public CommandModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_getpassword;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        JSONObject jsonObject = JSON.parseObject(data);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, jsonObject.getString("pwd") + "#" + jsonObject.getString("keyUrl"));
        }
    }
}
