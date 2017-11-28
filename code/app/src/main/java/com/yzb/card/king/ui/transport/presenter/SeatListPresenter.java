package com.yzb.card.king.ui.transport.presenter;

import java.util.Map;

/**
 * 功能：火车票座位列表
 *
 * @author:gengqiyun
 * @date: 2016/9/6
 */
public interface SeatListPresenter
{
    /**
     * 加载数据；
     */
    void loadData(boolean event_tag, Map<String, Object> paramMap);
}
