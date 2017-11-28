package com.yzb.card.king.ui.bonus.model;

import java.util.Map;

/**
 * 类 名： Model
 * 作 者：gaolei
 * 日 期：2017年01月04日
 * 描 述：收到红包记录的model
 */

public interface ReceiveRedBagDao {
    void setReceiveRedBag(Map<String, Object> map, String service, int type);
}
