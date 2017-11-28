package com.yzb.card.king.ui.my.model;

import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.util.LogUtil;

import java.util.Map;

/**
 * 功能：更新付款详情；
 *
 * @author:gengqiyun
 * @date: 2017/2/16
 */
public class UpdatePayDetailModel extends BaseModelImpl
{
    public UpdatePayDetailModel()
    {
        super(null);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.UpdatePayDetail;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        LogUtil.i("更新付款详情成功了");
    }
}
