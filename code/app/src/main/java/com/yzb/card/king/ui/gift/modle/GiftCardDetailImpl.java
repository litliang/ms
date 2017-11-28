package com.yzb.card.king.ui.gift.modle;

import com.yzb.card.king.http.CommonServerRequest;
import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类 名： 礼品卡收支明细实现类
 * 作 者： gaolei
 * 日 期：2016年12月28日
 * 描 述：Model实现类
 */

public class GiftCardDetailImpl implements GiftCardDetailDao{

    private DataCallBack callBack;

    public GiftCardDetailImpl(DataCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void setGiftCardDetail(Map<String, Object> map, String serviceName, int type) {
        CommonServerRequest request = new CommonServerRequest();
        request.sendReuqest(map,serviceName,type);
        request.setOnDataLoadFinish(callBack);
    }
}
