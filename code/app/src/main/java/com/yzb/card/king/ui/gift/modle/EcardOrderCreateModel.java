package com.yzb.card.king.ui.gift.modle;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.ticket.OrderOutBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.util.IpUtil;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/10/17
 * 描  述：
 */
public class EcardOrderCreateModel extends BaseModelImpl
{
    public EcardOrderCreateModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_ecardordercreate;
        paramMap.put("transIp", IpUtil.getNetIp());
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

