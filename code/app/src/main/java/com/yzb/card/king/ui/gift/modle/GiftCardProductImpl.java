package com.yzb.card.king.ui.gift.modle;

import com.yzb.card.king.http.CommonServerRequest;
import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类 名： 礼品开产品列表
 * 作 者： gaolei
 * 日 期：2017年02月07日
 * 描 述：Impl
 */

public class GiftCardProductImpl implements GiftCardProductDao{

    private DataCallBack dataCallBack;

    public GiftCardProductImpl(DataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }

    @Override
    public void setProductData(Map<String, Object> map, String serviceName, int type) {
        CommonServerRequest request = new CommonServerRequest();
        request.sendReuqest(map,serviceName,type);
        request.setOnDataLoadFinish(dataCallBack);
    }
}
