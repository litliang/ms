package com.yzb.card.king.ui.my.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.my.KeyInfoBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：口令信息；
 *
 * @author:gengqiyun
 * @date: 2016/12/28
 */
public class KeyInfoModel extends BaseModelImpl
{
    int keyType; //礼品卡：0x001； 红包：0x002

    public KeyInfoModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = String.valueOf(paramMap.get("serviceName"));
        if (paramMap.containsKey("type"))
        {
            keyType = Integer.parseInt(String.valueOf(paramMap.get("type")));
            paramMap.remove("type");
        }

        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            KeyInfoBean keyInfoBean = JSON.parseObject(data, KeyInfoBean.class);
            keyInfoBean.setKeyType(keyType);
            loadListener.onListenSuccess(event_tag, keyInfoBean);
        }
    }
}
