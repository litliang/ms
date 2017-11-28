package com.yzb.card.king.ui.hotel;

import com.yzb.card.king.bean.hotel.Hotel;
import com.yzb.card.king.bean.hotel.HotelProductListParam;

/**
 * Created by 玉兵 on 2017/7/31.
 */

public class HotelLogicManager {

    private  static  HotelLogicManager instance;

    /**
     * 酒店产品列表请求参数对象
     */
    private HotelProductListParam hotelProductListParam;
    /**
     * 今日甩房列表请求参数对象
     */
    private HotelProductListParam todayLeftRoomHotelParam;
    /**
     *  卡权益列表请求参数对象
     */
    private HotelProductListParam cardRightHotelParam;
    /**
     * 限时抢购列表请求参数对象
     */
    private HotelProductListParam flashSaleHotelParam;

    /**
     *
     */
    private Hotel hotel;

    private  HotelLogicManager(){

        hotelProductListParam = new HotelProductListParam();

        todayLeftRoomHotelParam = new HotelProductListParam();

        cardRightHotelParam = new HotelProductListParam();

        flashSaleHotelParam = new HotelProductListParam();
    }

    public static HotelLogicManager getInstance(){

        if(instance == null){

            instance = new HotelLogicManager();

        }

        return  instance;
    }

    public HotelProductListParam getHotelProductListParam() {
        return hotelProductListParam;
    }

    public HotelProductListParam getTodayLeftRoomHotelParam() {
        return todayLeftRoomHotelParam;
    }

    public HotelProductListParam getCardRightHotelParam()
    {
        if(cardRightHotelParam == null){

            cardRightHotelParam = new HotelProductListParam();
        }

        return cardRightHotelParam;
    }

    public HotelProductListParam getFlashSaleHotelParam()
    {
        if(flashSaleHotelParam == null){

            flashSaleHotelParam = new HotelProductListParam();
        }

        return flashSaleHotelParam;
    }

    public Hotel getHotel()
    {
        return hotel;
    }

    public void setHotel(Hotel hotel)
    {
        this.hotel = hotel;
    }

    /**
     * 清理數據
     */
    public  void  clearData(){

        hotelProductListParam = null;

        todayLeftRoomHotelParam = null;

        hotel = null;

        cardRightHotelParam = null;

        flashSaleHotelParam = null;

        instance = null;

    }

    /**
     * 清理用户在酒店列表页面的筛选条件、关键字等数据
     */
    public  void clearUseHandlerData(){


        if(hotelProductListParam != null){

            hotelProductListParam.setFilterList(null);

            hotelProductListParam.setSearchList(null);

            hotelProductListParam.setHotelKeyWordList(null);

            hotelProductListParam.setHotelPositionList(null);

            hotelProductListParam.setHotelBrandList(null);

            hotelProductListParam.setBrandTypes("0");

            hotelProductListParam.setLevels("0");

            hotelProductListParam.setSort(0);

            hotelProductListParam.setBankIds(null);

            hotelProductListParam.setDisTypes(null);

            hotelProductListParam.setStageBankIds(null);

        }

    }




    public void clearGiftCombonData(){

        cardRightHotelParam = null;


        flashSaleHotelParam = null;
    }

}
