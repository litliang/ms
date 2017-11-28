package com.yzb.card.king.ui.gift.modle;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.ui.gift.bean.NoRecvCardBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.List;
import java.util.Map;

/**
 * 功能：礼品卡首页；
 *
 * @author:gengqiyun
 * @date: 2016/12/22
 */
public class GiftcardNoRecvModel extends BaseModelImpl
{
    public GiftcardNoRecvModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_queryreceivegiftcard;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<NoRecvCardBean> beans = JSON.parseArray(data, NoRecvCardBean.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, beans);
        }
    }
}
