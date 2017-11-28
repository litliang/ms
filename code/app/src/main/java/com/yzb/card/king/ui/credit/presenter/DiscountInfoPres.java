package com.yzb.card.king.ui.credit.presenter;

import com.yzb.card.king.http.HttpCallBackImpl;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.credit.model.DiscountInfoImpl;
import com.yzb.card.king.ui.credit.model.IDiscountInfo;

import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/14 11:42
 */
public class DiscountInfoPres
{
    private BaseViewLayerInterface view;
    private IDiscountInfo model;

    public DiscountInfoPres(BaseViewLayerInterface view)
    {
        this.view = view;
        model = new DiscountInfoImpl(new CallBack());
    }

    public void loadData(Map<String, Object> param)
    {
        model.loadData(param);
    }

    private class CallBack extends HttpCallBackImpl
    {

        @Override
        public void onSuccess(Object o)
        {
         //   view.onDataLoadSucc((List<DiscountInfo>)o);
            view.callSuccessViewLogic(o,1);
        }

        @Override
        public void onFailed(Object o)
        {
            view.callFailedViewLogic(o,1);
        }
    }
}
