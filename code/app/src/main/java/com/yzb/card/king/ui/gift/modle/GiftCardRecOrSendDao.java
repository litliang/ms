package com.yzb.card.king.ui.gift.modle;

import java.util.Map;

/**
 * 类 名： Model
 * 作 者： gaolei
 * 日 期：2016年12月28日
 * 描 述：礼品卡领取model
 */

public interface GiftCardRecOrSendDao {
    void setGiftCardRecOrSend(Map<String, Object> map, String serviceName, int type);
}
