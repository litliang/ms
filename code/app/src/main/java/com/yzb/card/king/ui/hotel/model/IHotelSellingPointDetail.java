package com.yzb.card.king.ui.hotel.model;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/10/27
 * 描  述：
 */
public interface IHotelSellingPointDetail {

     int HOTEL_SERVIICE = 1;

    //酒店图片
     int HOTEL_PIC = 2;


    /**
     * 查询酒店详情下的标签，服务，附加条件
     *
     * @param map
     * @param service
     */
    void selectHotelService(Map<String, Object> map, String service);
}

