package com.yzb.card.king.ui.credit.presenter;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.credit.model.AdjustLimitDaoImpl;
import com.yzb.card.king.ui.credit.model.IAdjustLimit;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/12
 * 描  述：
 */
public class AdjustLimitPresenter implements DataCallBack
{
    private IAdjustLimit iAdjustLimit;

    private BaseViewLayerInterface view;

    public AdjustLimitPresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        this.iAdjustLimit = new AdjustLimitDaoImpl(this);
    }

    /**
     * 查询信用卡列表
     *
     * @param map
     */
    public void queryCreditCard(Map<String, Object> map)
    {
        iAdjustLimit.queryCreditCard(map);
    }



    @Override
    public void requestSuccess(Object o, int type)
    {
        view.callSuccessViewLogic(o, IAdjustLimit.QUERYCREDITCARD_CODE);

    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        view.callFailedViewLogic(o, IAdjustLimit.QUERYCREDITCARD_CODE);

    }
}
