package com.yzb.card.king.ui.app.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.hotel.SearchResultBean;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.util.IpUtil;

import java.util.List;
import java.util.Map;

/**
 * 功能：搜索列表
 *
 * @author:gengqiyun
 * @date: 2016/10/31
 */
public class SearchInputModel extends BaseModelImpl
{
    public SearchInputModel(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = String.valueOf(paramMap.get("serviceName"));
        paramMap.remove("serviceName");
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<SearchResultBean> passengerInfoBeans = JSON.parseArray(data, SearchResultBean.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, passengerInfoBeans);
        }
    }
}
