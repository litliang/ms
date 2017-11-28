package com.yzb.card.king.ui.base;

/**
 * MVP中presenter的实现接口；用于M层和P层之间的数据回传；
 *
 * @param <T> 数据类型；
 * @author gengqiyun
 * @date 2016/8/31
 */
public interface BaseMultiLoadListener<T>
{

    /**
     * @param event_tag 下拉或上拉的标志位；
     * @param data
     */
    void onListenSuccess(boolean event_tag, T data);

    void onListenError(String msg);
}
