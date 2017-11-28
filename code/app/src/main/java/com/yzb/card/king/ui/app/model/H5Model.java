package com.yzb.card.king.ui.app.model;

import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;
import com.yzb.card.king.util.LogUtil;

import java.util.Map;

/**
 * 功能：H5页面请求；
 *
 * @author:gengqiyun
 * @date: 2016/9/7
 */
public class H5Model extends BaseModelImpl
{
    public H5Model(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    public void getH5(Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_selecth5page;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        LogUtil.i("H5结果==" + data);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(true, data);
        }
    }
}
