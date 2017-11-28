package com.yzb.card.king.ui.gift.modle;

import com.yzb.card.king.http.CommonServerRequest;
import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类 名： 礼品卡商城Impl
 * 作 者： gaolei
 * 日 期：2017年01月06日
 * 描 述： Impl
 */

public class GiftCardStoreImpl implements GiftCardStoreDao{

    private DataCallBack callBack;

    public GiftCardStoreImpl(DataCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void setDetailData(Map<String, Object> map, String serviceName, int type) {
        CommonServerRequest request = new CommonServerRequest();
        request.sendReuqest(map,serviceName,type);
        request.setOnDataLoadFinish(callBack);
    }
}
