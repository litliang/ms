package com.yzb.card.king.ui.discount.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.ui.discount.model.ActivityOfBankModel;
import com.yzb.card.king.util.LogUtil;

import java.util.Map;

/**
 * 功能：银行优惠活动
 *
 * @author:gengqiyun
 * @date: 2016/9/20
 */
public class ActivityOfBankModelImpl extends BaseModelImpl implements ActivityOfBankModel
{
    public ActivityOfBankModelImpl(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_activitybankquery;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        LogUtil.i("银行优惠活动获取成功==" + data);
        Map<String, String> map = JSON.parseObject(data, Map.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(true, map);
        }
    }
}
