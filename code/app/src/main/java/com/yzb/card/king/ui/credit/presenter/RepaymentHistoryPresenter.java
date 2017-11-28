package com.yzb.card.king.ui.credit.presenter;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.credit.model.IRepaymentHistory;
import com.yzb.card.king.ui.credit.model.RepaymentHistoryDaoImpl;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/12/1
 * 描  述：
 */
public class RepaymentHistoryPresenter implements DataCallBack {
    private IRepaymentHistory dao;

    private BaseViewLayerInterface view;

    public RepaymentHistoryPresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        this.dao = new RepaymentHistoryDaoImpl(this);
    }

    /**
     * 获取信用卡还款记录
     *
     * @param map
     * @param serviceName
     */
    public void getListInfo(Map<String, Object> map, String serviceName,int code)
    {
        dao.getList(map, serviceName,code);
    }


    @Override
    public void requestSuccess(Object o, int type)
    {
        if (type == 0)
        {
            view.callSuccessViewLogic(o, 0);
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if (type == 0)
        {
            view.callFailedViewLogic(o, 0);
        }
    }
}
