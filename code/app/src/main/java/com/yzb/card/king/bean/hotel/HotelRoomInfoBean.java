package com.yzb.card.king.bean.hotel;

import java.io.Serializable;
import java.util.List;

/**
 * 类  名：酒店房间信息类
 * 作  者：Li Yubing
 * 日  期：2017/8/4
 * 描  述：
 */
public class HotelRoomInfoBean implements Serializable{
    /**
     * 房间id
     */
    private long roomsId;
    /**
     * 房间名称
     */
    private String roomsName;
    /**
     * 最低价格
     */
    private  int minPrice;
    /**
     * 图片  多个使用,分隔
     */
    private String photoUrls;
    /**
     * 床型描述
     */
    private  String bedTypeDesc;
    /**
     * 宽带描述
     */
    private String wifiDesc;
    /**
     * 面积描述
     */
    private  String areaDesc;
    /**
     * 楼层描述
     */
    private String floorDesc;
    /**
     * 吸烟描述
     */
    private String smokeDesc;
    /**
     * 窗户描述
     */
    private String windowDesc;
    /**
     * 是否售罄  1是；0否；
     */
    private String selloutStatus;
    /**
     * 适用人数描述
     */
    private String peopleLimitDesc;
    /**
     * 房间加床描述 (1是；0否；)
     */
    private String addbedDesc;

    public String getAddbedDesc()
    {
        return addbedDesc;
    }

    public void setAddbedDesc(String addbedDesc)
    {
        this.addbedDesc = addbedDesc;
    }

    public long getRoomsId()
    {
        return roomsId;
    }

    public void setRoomsId(long roomsId)
    {
        this.roomsId = roomsId;
    }

    public String getRoomsName()
    {
        return roomsName;
    }

    public void setRoomsName(String roomsName)
    {
        this.roomsName = roomsName;
    }

    public int getMinPrice()
    {
        return minPrice;
    }

    public void setMinPrice(int minPrice)
    {
        this.minPrice = minPrice;
    }

    public String getPhotoUrls()
    {
        return photoUrls;
    }

    public void setPhotoUrls(String photoUrls)
    {
        this.photoUrls = photoUrls;
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

    public String getAreaDesc()
    {
        return areaDesc;
    }

    public void setAreaDesc(String areaDesc)
    {
        this.areaDesc = areaDesc;
    }

    public String getFloorDesc()
    {
        return floorDesc;
    }

    public void setFloorDesc(String floorDesc)
    {
        this.floorDesc = floorDesc;
    }

    public String getSmokeDesc()
    {
        return smokeDesc;
    }

    public void setSmokeDesc(String smokeDesc)
    {
        this.smokeDesc = smokeDesc;
    }

    public String getWindowDesc()
    {
        return windowDesc;
    }

    public void setWindowDesc(String windowDesc)
    {
        this.windowDesc = windowDesc;
    }

    public String getSelloutStatus()
    {
        return selloutStatus;
    }

    public void setSelloutStatus(String selloutStatus)
    {
        this.selloutStatus = selloutStatus;
    }

    public String getPeopleLimitDesc()
    {
        return peopleLimitDesc;
    }

    public void setPeopleLimitDesc(String peopleLimitDesc)
    {
        this.peopleLimitDesc = peopleLimitDesc;
    }

    public List<RoomService> getBaseServiceList()
    {
        return baseServiceList;
    }

    public void setBaseServiceList(List<RoomService> baseServiceList)
    {
        this.baseServiceList = baseServiceList;
    }

    private List<RoomService> baseServiceList;


    public static class RoomService implements Serializable{

        /**
         * 类别名称
         */
        private String typeName;
        /**
         * 子项名称  多个使用,分隔
         */
        private String itemNames;

        private String[] itemNamesArray;

        /**
         * 子项图片  多个使用,分隔
         */
        private String itemPhotos;

        private String[] itemPotoArray;

        public String getTypeName()
        {
            return typeName;
        }

        public void setTypeName(String typeName)
        {
            this.typeName = typeName;
        }

        public String getItemNames()
        {
            return itemNames;
        }

        public void setItemNames(String itemNames)
        {
            this.itemNames = itemNames;

            int index = itemNames.indexOf(",");

            if(index==-1){

                itemNamesArray = new String[1];

                itemNamesArray[0] =  itemNames;

            }else {

                itemNamesArray = itemNames.split(",",-1);

            }
        }

        public String getItemPhotos()
        {
            return itemPhotos;
        }

        public void setItemPhotos(String itemPhotos)
        {
            this.itemPhotos = itemPhotos;

            int index = itemPhotos.indexOf(",");

            if(index==-1){

                itemPotoArray = new String[1];

                itemPotoArray[0] =  itemPhotos;

            }else {

                itemPotoArray = itemPhotos.split(",",-1);

            }
        }

        public String[] getItemNamesArray()
        {
            return itemNamesArray;
        }

        public void setItemNamesArray(String[] itemNamesArray)
        {
            this.itemNamesArray = itemNamesArray;
        }

        public String[] getItemPotoArray()
        {
            return itemPotoArray;
        }

        public void setItemPotoArray(String[] itemPotoArray)
        {
            this.itemPotoArray = itemPotoArray;
        }
    }

}
