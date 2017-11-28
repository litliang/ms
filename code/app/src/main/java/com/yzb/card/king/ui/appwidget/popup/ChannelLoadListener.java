package com.yzb.card.king.ui.appwidget.popup;

import com.yzb.card.king.ui.discount.bean.ChildTypeBean;

import java.util.List;

/**
 * 频道回调；
 *
 * @author gengqiyun
 * @date 2016/9/6
 */
public interface ChannelLoadListener
{
    /**
     * @param displayChannelList 显示部分；
     * @param hideChannelList    隐藏部分；
     */
    void onListenSuccess(List<ChildTypeBean> displayChannelList, List<ChildTypeBean> hideChannelList);

    void onListenError(String msg);
}
