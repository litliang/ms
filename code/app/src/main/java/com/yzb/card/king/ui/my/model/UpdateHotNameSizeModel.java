package com.yzb.card.king.ui.my.model;

import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;

import java.util.Map;

/**
 * 功能：修改酒店关键字搜索次数；
 *
 * @author:gengqiyun
 * @date: 2017/3/3
 */
public class UpdateHotNameSizeModel extends BaseModelImpl
{
    public UpdateHotNameSizeModel()
    {
        super(null);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_updatehotnamesize;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
    }
}
