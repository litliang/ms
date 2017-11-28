package com.yzb.card.king.ui.gift.presenter;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.gift.modle.GiftCardRecOrSendDao;
import com.yzb.card.king.ui.gift.modle.GiftCardRecOrSendImpl;

import java.util.Map;

/**
 * 类 名： Presenter
 * 作 者： gaolei
 * 日 期：2016年12月20日
 * 描 述：领取礼品卡的Presenter
 */

public class GiftCardRecOrSendPresent implements DataCallBack {
    private GiftCardRecOrSendDao dao;
    private BaseViewLayerInterface view;

    public GiftCardRecOrSendPresent(BaseViewLayerInterface view) {
        this.view = view;
        dao = new GiftCardRecOrSendImpl(this);
    }

    public void getListData(Map<String, Object> map, String service, int type){
        dao.setGiftCardRecOrSend(map,service,type);
    }

    @Override
    public void requestSuccess(Object o, int type) {
        if (type == 1)
        {//如果是第一个接口
            view.callSuccessViewLogic(o, 1);
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type) {
        if (type == 1)
        {
            view.callFailedViewLogic(o, 1);

        }
    }
}
