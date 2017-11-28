package com.yzb.card.king.ui.travel.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.travel.TravelLineBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.util.LogUtil;

import java.util.List;
import java.util.Map;

/**
 * 功能：旅游线路列表
 *
 * @author:gengqiyun
 * @date: 2016/11/23
 */
public class TravelLineMImpl extends BaseModelImpl implements BaseModel
{
    public TravelLineMImpl(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(final boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_app_querytravelline;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
//        LogUtil.i("lineBeans1==" + data.substring(0, data.length() / 2));
//        LogUtil.i("lineBeans2==" + data.substring(data.length() / 2, data.length()));
        try
        {
            List<TravelLineBean> lineBeans = JSON.parseArray(data, TravelLineBean.class);
            if (loadListener != null)
            {
                loadListener.onListenSuccess(true, lineBeans);
            }
        } catch (Exception e)
        {
            if (loadListener != null)
            {
                loadListener.onListenError("数据解析失败");
            }
        }

    }
}
