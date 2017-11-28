package com.yzb.card.king.ui.other.listeners;

import java.util.List;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/27 16:30
 */
public interface QueryPlaceListener<T>
{
    void onQuerySuccess(Map<String, List<T>> places);
    void onQueryFail();
}
