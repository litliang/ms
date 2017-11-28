package com.yzb.card.king.ui.hotel.model;


/**
 * 类  名：酒店详情dao层
 * 作  者：Li JianQiang
 * 日  期：2016/8/25
 * 描  述：
 */
public interface IHotelDetail {


    //酒店详情标识
    public final static int HOTEL_DETAIL=0;

    //查询酒店是否被收藏
    public final static int HOTEL_ISCOLLECT=1;

    //收藏酒店
    public final static int HOTEL_COLLECT=2;

    //酒店房间
    public final static int HOTEL_ROOMPIC=3;

    public final static int HOTEL_SERVER_CODE = 7001;

    public int[] code = new int[]{HOTEL_DETAIL,HOTEL_ISCOLLECT,HOTEL_COLLECT,HOTEL_ROOMPIC};

    /**
     * 获得酒店详情
     *
     * @param hotelId
     */
    void getHotelDetail(String hotelId,String arrDate);


    void sendSelectHotelServiceInfoRequestAction(String hotelId);

    /**
     * 收藏酒店
     *
     * @param id
     * @param status
     */
    void collectHotel(String id, String status,String type,String category);




}
