package com.yzb.card.king.ui.app.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.app.bean.PassengerInfoBean;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.util.LogUtil;

import java.util.List;
import java.util.Map;

/**
 * 功能：旅客信息列表
 *
 * @author:gengqiyun
 * @date: 2016/9/13
 */
public class PassengerListModel extends BaseModelImpl implements BaseModel
{
    public PassengerListModel(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(final boolean event_flag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.setting_customerguestlist;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        LogUtil.i("获取旅客信息成功=" + data);
        List<PassengerInfoBean> passengerInfoBeans = JSON.parseArray(data, PassengerInfoBean.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, passengerInfoBeans);
        }
    }
}
