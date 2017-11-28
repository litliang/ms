package com.yzb.card.king.ui.my.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.my.AccountInfoBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：查询账户信息；
 *
 * @author:gengqiyun
 * @date: 2016/12/28
 */
public class AccountInfoModel extends BaseModelImpl
{
    public AccountInfoModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_selectaccountinfo;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, JSON.parseObject(data, AccountInfoBean.class));
        }
    }
}
