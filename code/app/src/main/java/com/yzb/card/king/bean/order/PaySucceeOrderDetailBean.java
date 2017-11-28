package com.yzb.card.king.bean.order;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/25
 * 描  述：
 */
public class PaySucceeOrderDetailBean implements Serializable{

    private int orderType;

    private  HotelInfoSucceeBean hotelInfo;

    private HotelOtherInfoSucceeBean hotelOtherInfo;

    private FlashSaleInfoSucceeBean flashsaleInfo;

    private RightsInfoSucceeBean rightsInfo;

    public int getOrderType()
    {
        return orderType;
    }

    public void setOrderType(int orderType)
    {
        this.orderType = orderType;
    }

    public HotelInfoSucceeBean getHotelInfo()
    {
        return hotelInfo;
    }

    public void setHotelInfo(HotelInfoSucceeBean hotelInfo)
    {
        this.hotelInfo = hotelInfo;
    }

    public HotelOtherInfoSucceeBean getHotelOtherInfo()
    {
        return hotelOtherInfo;
    }

    public void setHotelOtherInfo(HotelOtherInfoSucceeBean hotelOtherInfo)
    {
        this.hotelOtherInfo = hotelOtherInfo;
    }

    public FlashSaleInfoSucceeBean getFlashsaleInfo()
    {
        return flashsaleInfo;
    }

    public void setFlashsaleInfo(FlashSaleInfoSucceeBean flashsaleInfo)
    {
        this.flashsaleInfo = flashsaleInfo;
    }

    public RightsInfoSucceeBean getRightsInfo()
    {
        return rightsInfo;
    }

    public void setRightsInfo(RightsInfoSucceeBean rightsInfo)
    {
        this.rightsInfo = rightsInfo;
    }

    public static class RightsInfoSucceeBean implements Serializable{

        private String actName;

        private String effDateDesc;

        private String goodsList;

        public String getActName()
        {
            return actName;
        }

        public void setActName(String actName)
        {
            this.actName = actName;
        }

        public String getEffDateDesc()
        {
            return effDateDesc;
        }

        public void setEffDateDesc(String effDateDesc)
        {
            this.effDateDesc = effDateDesc;
        }

        public String getGoodsList()
        {
            return goodsList;
        }

        public void setGoodsList(String goodsList)
        {
            this.goodsList = goodsList;
        }
    }

    public static class  HotelInfoSucceeBean implements Serializable{

        private String roomsName;

        private String arrDate;

        private String depDate;

        private String bedTypeDesc;

        private String wifiDesc;

        private String mealInfo;

        private int cancelType;

        public String getRoomsName()
        {
            return roomsName;
        }

        public void setRoomsName(String roomsName)
        {
            this.roomsName = roomsName;
        }

        public String getArrDate()
        {
            return arrDate;
        }

        public void setArrDate(String arrDate)
        {
            this.arrDate = arrDate;
        }

        public String getDepDate()
        {
            return depDate;
        }

        public void setDepDate(String depDate)
        {
            this.depDate = depDate;
        }

        public String getBedTypeDesc()
        {
            return bedTypeDesc;
        }

        public void setBedTypeDesc(String bedTypeDesc)
        {
            this.bedTypeDesc = bedTypeDesc;
        }

        public String getWifiDesc()
        {
            return wifiDesc;
        }

        public void setWifiDesc(String wifiDesc)
        {
            this.wifiDesc = wifiDesc;
        }

        public String getMealInfo()
        {
            return mealInfo;
        }

        public void setMealInfo(String mealInfo)
        {
            this.mealInfo = mealInfo;
        }

        public int getCancelType()
        {
            return cancelType;
        }

        public void setCancelType(int cancelType)
        {
            this.cancelType = cancelType;
        }
    }


    public static class HotelOtherInfoSucceeBean implements Serializable{

        private String policysName;

        private String effDateDesc;

        private String useDateDesc;

        private String useTimeDesc;

        public String getPolicysName()
        {
            return policysName;
        }

        public void setPolicysName(String policysName)
        {
            this.policysName = policysName;
        }

        public String getEffDateDesc()
        {
            return effDateDesc;
        }

        public void setEffDateDesc(String effDateDesc)
        {
            this.effDateDesc = effDateDesc;
        }

        public String getUseDateDesc()
        {
            return useDateDesc;
        }

        public void setUseDateDesc(String useDateDesc)
        {
            this.useDateDesc = useDateDesc;
        }

        public String getUseTimeDesc()
        {
            return useTimeDesc;
        }

        public void setUseTimeDesc(String useTimeDesc)
        {
            this.useTimeDesc = useTimeDesc;
        }
    }

    public static class FlashSaleInfoSucceeBean implements  Serializable{

        private String actName;

        private String useDateDesc;

        private String useTimeDesc;

        private String goodsList;
        private String effDateDesc;

        public String getEffDateDesc()
        {
            return effDateDesc;
        }

        public void setEffDateDesc(String effDateDesc)
        {
            this.effDateDesc = effDateDesc;
        }

        public String getActName()
        {
            return actName;
        }

        public void setActName(String actName)
        {
            this.actName = actName;
        }

        public String getUseDateDesc()
        {
            return useDateDesc;
        }

        public void setUseDateDesc(String useDateDesc)
        {
            this.useDateDesc = useDateDesc;
        }

        public String getUseTimeDesc()
        {
            return useTimeDesc;
        }

        public void setUseTimeDesc(String useTimeDesc)
        {
            this.useTimeDesc = useTimeDesc;
        }

        public String getGoodsList()
        {
            return goodsList;
        }

        public void setGoodsList(String goodsList)
        {
            this.goodsList = goodsList;
        }
    }

    private  PlaneInfoSuccessBean planeInfo;

    public PlaneInfoSuccessBean getPlaneInfo() {
        return planeInfo;
    }

    public void setPlaneInfo(PlaneInfoSuccessBean planeInfo) {
        this.planeInfo = planeInfo;
    }

    public static class PlaneInfoSuccessBean implements Serializable{

        private String orderId;

        private String tripType;

        public String getTripType() {
            return tripType;
        }

        public void setTripType(String tripType) {
            this.tripType = tripType;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }

}
