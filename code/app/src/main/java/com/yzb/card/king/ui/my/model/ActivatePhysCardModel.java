package com.yzb.card.king.ui.my.model;

import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.util.IpUtil;

import java.util.Map;

/**
 * 功能：实体卡充值
 * @author:gengqiyun
 * @date: 2016/12/22
 */
public class ActivatePhysCardModel extends BaseModelImpl
{
    public ActivatePhysCardModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    public void loadData(Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_giftcardrecharge;
        paramMap.put("transIp", IpUtil.getNetIp());
        this.paramMap = paramMap;
        sendRequest();
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_giftcardrecharge;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, null);
        }
    }
}
