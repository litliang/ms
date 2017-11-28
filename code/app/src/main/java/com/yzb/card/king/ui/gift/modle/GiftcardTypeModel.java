package com.yzb.card.king.ui.gift.modle;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.ui.gift.bean.GiftcardTypeBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.List;
import java.util.Map;

/**
 * 功能：查询礼品卡分类列表
 *
 * @author:gengqiyun
 * @date: 2016/12/28
 */
public class GiftcardTypeModel extends BaseModelImpl
{
    public GiftcardTypeModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_querygiftcardtype;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<GiftcardTypeBean> beans = JSON.parseArray(data, GiftcardTypeBean.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, beans);
        }
    }
}
