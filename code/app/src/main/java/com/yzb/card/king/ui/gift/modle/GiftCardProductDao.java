package com.yzb.card.king.ui.gift.modle;

import java.util.Map;

/**
 * 类 名： 礼品开产品列表
 * 作 者： gaolei
 * 日 期：2017年02月07日
 * 描 述：dao
 */
public interface GiftCardProductDao {
    void setProductData(Map<String, Object> map, String serviceName, int type);
}
