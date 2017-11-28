package com.yzb.card.king.ui.ticket.model;

import com.yzb.card.king.bean.ticket.PlaneTicket;

import java.util.List;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/5 18:14
 */
public interface ISingleList
{
    void loadHeaderPrice(Map<String, Object> param);

    void saveList(List<PlaneTicket> dataList);
}
