package com.yzb.card.king.ui.gift.modle;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.ui.gift.bean.ECardBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.List;
import java.util.Map;

/**
 * 功能：心意e卡列表；
 *
 * @author:gengqiyun
 * @date: 2016/12/22
 */
public class MindECardsModel extends BaseModelImpl
{
    public MindECardsModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_querygiftcardproduct;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<ECardBean> beans = JSON.parseArray(data, ECardBean.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, beans);
        }
    }
}
