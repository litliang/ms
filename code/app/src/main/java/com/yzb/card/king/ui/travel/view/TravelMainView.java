package com.yzb.card.king.ui.travel.view;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;

import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/9/1
 * 描  述：
 */
public interface TravelMainView extends BaseViewLayerInterface {

    /**
     * 传递频道显示和隐藏数据
     *
     * @param displayChannelList 显示的频道数据
     * @param hideChannelList    隐藏的频道数据
     */
    public void transmitChannelData(List<ChildTypeBean> displayChannelList, List<ChildTypeBean> hideChannelList);


}
