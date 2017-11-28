package com.yzb.card.king.ui.hotel.view;

import com.yzb.card.king.bean.hotel.HotelOrderParam;
import com.yzb.card.king.ui.app.bean.PassengerInfoBean;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;

import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/14
 * 描  述：
 */
public interface   HotelRoomCreateOrderView extends BaseViewLayerInterface{

    /**
     * 获取订单页当前的passengerList变量；
     */
    List<PassengerInfoBean> getPassengerList();

    /**
     * 获取现在的房间数；
     *
     * @return
     */
    int getRoomCount();

    /**
     * 收集房间订单数据
     * @return
     */
   public  HotelOrderParam collectHotelRoomOrderParam();

}
