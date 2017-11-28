package com.yzb.card.king.ui.bonus.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.ui.bonus.bean.BounsBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.List;
import java.util.Map;

/**
 * 功能：红包列表
 *
 * @author:gengqiyun
 * @date: 2016/12/30
 */
public class BounsListModel extends BaseModelImpl
{
    public BounsListModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_queryreceivebonus;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<BounsBean> beans = JSON.parseArray(data, BounsBean.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, beans);
        }
    }
}
