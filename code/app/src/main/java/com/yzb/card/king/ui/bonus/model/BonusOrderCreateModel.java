package com.yzb.card.king.ui.bonus.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.ticket.OrderOutBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：创建红包订单
 * ；
 *
 * @author:gengqiyun
 * @date: 2016/12/30
 */
public class BonusOrderCreateModel extends BaseModelImpl
{
    public BonusOrderCreateModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_createbonusorder;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        OrderOutBean outBean = JSON.parseObject(data, OrderOutBean.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, outBean);
        }
    }
}
