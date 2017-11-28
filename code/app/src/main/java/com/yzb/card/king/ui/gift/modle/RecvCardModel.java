package com.yzb.card.king.ui.gift.modle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：领取礼品卡；
 *
 * @author:gengqiyun
 * @date: 2016/12/29
 */
public class RecvCardModel extends BaseModelImpl
{
    String orderId;

    public RecvCardModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.orderId = String.valueOf(paramMap.get("orderId"));

        this.serviceName = CardConstant.card_receivemindcard;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        JSONObject jsonObject = JSON.parseObject(data);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, orderId + "," + jsonObject.getString("receiveAmount"));
        }
    }
}
