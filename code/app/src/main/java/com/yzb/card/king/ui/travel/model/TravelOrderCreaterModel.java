package com.yzb.card.king.ui.travel.model;

import java.util.Map;

/**
 * 功能：旅游提交订单；
 *
 * @author:gengqiyun
 * @date: 2016/8/31
 */
public interface TravelOrderCreaterModel
{
    void commit(boolean event_tag, Map<String, Object> paramMap);
}
