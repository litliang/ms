package com.yzb.card.king.ui.transport.presenter;

import java.util.Map;

/**
 * 功能：航线
 *
 * @author:gengqiyun
 * @date: 2016/9/8
 */
public interface QueryLinePresenter
{
    /**
     * 加载数据；
     */
    void loadData(boolean event_tag, Map<String, Object> paramMap);
}
