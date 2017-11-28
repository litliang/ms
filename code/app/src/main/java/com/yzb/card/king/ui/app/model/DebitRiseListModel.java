package com.yzb.card.king.ui.app.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.base.BaseModel;
import com.yzb.card.king.ui.app.bean.DebitRiseBean;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.util.LogUtil;

import java.util.List;
import java.util.Map;

/**
 * 功能：发票抬头列表
 *
 * @author:gengqiyun
 * @date: 2016/9/13
 */
public class DebitRiseListModel extends BaseModelImpl implements BaseModel
{
    public DebitRiseListModel(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    public void loadData(final boolean event_flag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.setting_invoicelist;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        LogUtil.i("获取发票抬头列表成功=" + data);
        List<DebitRiseBean> passengerInfoBeans = JSON.parseArray(data, DebitRiseBean.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, passengerInfoBeans);
        }
    }
}
