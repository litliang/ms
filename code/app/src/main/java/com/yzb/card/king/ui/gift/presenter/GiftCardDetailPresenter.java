package com.yzb.card.king.ui.gift.presenter;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.gift.modle.GiftCardDetailDao;
import com.yzb.card.king.ui.gift.modle.GiftCardDetailImpl;

import java.util.Map;

/**
 * 类 名： Presenter
 * 作 者： gaolei
 * 日 期：2016年12月20日
 * 描 述：查询礼品卡收支的Presenter
 */

public class GiftCardDetailPresenter implements DataCallBack {
    private GiftCardDetailDao dao;
    private BaseViewLayerInterface view;

    public GiftCardDetailPresenter(BaseViewLayerInterface view) {
        this.view = view;
        dao = new GiftCardDetailImpl(this);
    }

    public void getListData(Map<String, Object> map, String service, int type){
        dao.setGiftCardDetail(map, service, type);
    }

    @Override
    public void requestSuccess(Object o, int type) {
        if (type == 1)
        {//如果是第一个接口
            view.callSuccessViewLogic(o, 1);
        }
        if (type == 2)
        {//第二个接口
            view.callSuccessViewLogic(o, 2);
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type) {
        if (type == 1)
        {
            view.callFailedViewLogic(o, 1);

        }

        if (type == 2)
        {//第二个接口
            view.callFailedViewLogic(o, 2);
        }
    }
}
