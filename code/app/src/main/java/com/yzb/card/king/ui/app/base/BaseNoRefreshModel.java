package com.yzb.card.king.ui.app.base;

import java.util.Map;

/**
 * 功能：model 无刷新功能的基类；
 *
 * @author:gengqiyun
 * @date: 2016/9/14
 */
public interface BaseNoRefreshModel
{
    void loadData(Map<String, Object> paramMap);
}
