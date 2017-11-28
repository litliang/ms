package com.yzb.card.king.ui.app.model;

import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.base.BaseNoRefreshModel;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.Map;

/**
 * 功能：验证手机号
 *
 * @author:gengqiyun
 * @date: 2016/9/13
 */
public class ValidPhoneModel extends BaseModelImpl implements BaseNoRefreshModel
{
    public ValidPhoneModel(BaseMultiLoadListener loadListener)
    {
        super(loadListener);
    }

    @Override
    protected void afterSuccess(String data)
    {
        if (loadListener != null)
        {
            loadListener.onListenSuccess(true, data);
        }
    }

    @Override
    public void loadData(Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.card_api_provingMobile;
        super.loadData(event_tag, paramMap);
    }
}
