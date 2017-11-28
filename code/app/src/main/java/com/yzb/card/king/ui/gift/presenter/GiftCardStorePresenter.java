package com.yzb.card.king.ui.gift.presenter;

import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.gift.modle.GiftCardStoreDao;
import com.yzb.card.king.ui.gift.modle.GiftCardStoreImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * 类 名： 礼品卡商城Presenter
 * 作 者： gaolei
 * 日 期：2017年01月06日
 * 描 述：Presenter
 */
public class GiftCardStorePresenter implements DataCallBack {

    private GiftCardStoreDao dao;

    private BaseViewLayerInterface view;

    public GiftCardStorePresenter(BaseViewLayerInterface view) {
        this.view = view;
        dao = new GiftCardStoreImpl(this);
    }


    public void getDetailTotalData(Map<String, Object> map, String serviceName, int type){
        dao.setDetailData(map,serviceName,type);
    }

    /**
     * 发送用户的我的礼品卡页面展示的礼品卡信息数据
     */
    public void sendGiftDataRequest(){
        Map<String, Object> param = new HashMap<>();
        param.put("category", "2");
       param.put("sort", "3");
       param.put("pageStart", "0");
       param.put("pageSize", "4");
        getDetailTotalData(param, CardConstant.card_querygiftcardproduct, 1);
    }


    @Override
    public void requestSuccess(Object o, int type) {
        if (type == 1) {
            view.callSuccessViewLogic(o,type);
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type) {
        if (type == 1) {
            view.callFailedViewLogic(o,type);
        }
    }
}
