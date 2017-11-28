package com.yzb.card.king.ui.bonus.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.ui.bonus.bean.BounsThemeBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.List;
import java.util.Map;

/**
 * 功能：发红包首页；
 *
 * @author:gengqiyun
 * @date: 2016/12/12
 */
public class BounsThemeModel extends BaseModelImpl
{
    public BounsThemeModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_bonusthemelist;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<BounsThemeBean> beans = JSON.parseArray(data, BounsThemeBean.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, beans);
        }
    }
}
