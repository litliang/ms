package com.yzb.card.king.ui.appwidget.popup.view;

import com.yzb.card.king.ui.discount.bean.ChildTypeBean;

import java.util.List;


/**
 * 功能：频道view；
 *
 * @author:gengqiyun
 * @date: 2016/9/6
 */
public interface ChannelView
{
    void onGetChannelSucess(List<ChildTypeBean> displayChannelList, List<ChildTypeBean> hideChannelList);

    void onGetChannelFail(String failMsg);
}
