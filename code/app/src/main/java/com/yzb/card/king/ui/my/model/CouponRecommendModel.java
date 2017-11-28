package com.yzb.card.king.ui.my.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.my.CouponInfoBean;
import com.yzb.card.king.bean.my.CouponShopBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseModelImpl;
import com.yzb.card.king.ui.base.BaseMultiLoadListener;

import java.util.List;
import java.util.Map;

/**
 * 类  名：查询优惠券推荐列表
 * 作  者：Li Yubing
 * 日  期：2017/10/20
 * 描  述：
 */
public class CouponRecommendModel extends BaseModelImpl
{
    public CouponRecommendModel(BaseMultiLoadListener listener)
    {
        super(listener);
    }

    @Override
    public void loadData(boolean event_tag, Map<String, Object> paramMap)
    {
        this.serviceName = CardConstant.QUERY_RECOMMOND_URL;
        super.loadData(event_tag, paramMap);
    }

    @Override
    protected void afterSuccess(String data)
    {
        List<CouponInfoBean> beans = JSON.parseArray(data, CouponInfoBean.class);
        if (loadListener != null)
        {
            loadListener.onListenSuccess(event_tag, beans);
        }
    }
}
