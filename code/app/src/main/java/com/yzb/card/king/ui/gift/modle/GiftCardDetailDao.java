package com.yzb.card.king.ui.gift.modle;

import java.util.Map;

/**
 * 类 名： Model
 * 作 者： gaolei
 * 日 期：2016年12月29日
 * 描 述：礼品卡收支详情model
 */

public interface GiftCardDetailDao {
    void setGiftCardDetail(Map<String, Object> map, String serviceName, int type);
}
