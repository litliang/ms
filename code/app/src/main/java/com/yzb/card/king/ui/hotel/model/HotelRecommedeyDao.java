package com.yzb.card.king.ui.hotel.model;

/**
 * 类  名：推荐酒店dao层
 * 作  者：Li JianQiang
 * 日  期：2016/8/29
 * 描  述：
 */
public interface HotelRecommedeyDao {

    //获取酒店推荐信息标识
     int HOTEL_RECOMMEDEY = 1;

    //加载更多
     int HOTEL_LOADMORE = 2;

    /**
     * 获取推荐酒店信息
     *
     * @param id
     * @param pageStar
     */
    void getRecommedeyHotel(String id, String pageStar);

    /**
     * 上拉加载更多
     *
     * @param id
     * @param pageStar
     */
    void getLoadMoreRecommedeyHotel(String id, String pageStar);

}
