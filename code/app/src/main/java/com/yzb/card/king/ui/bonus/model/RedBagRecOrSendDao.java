package com.yzb.card.king.ui.bonus.model;

import java.util.Map;

/**
 * 类 名： Model
 * 作 者： gaolei
 * 日 期：2016年12月22日
 * 描 述：红包领取记录
 */
public interface RedBagRecOrSendDao {
    void setBillDetail(Map<String, Object> map, String serviceName, int type);
}
