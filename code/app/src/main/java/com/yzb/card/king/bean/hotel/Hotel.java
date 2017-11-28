package com.yzb.card.king.bean.hotel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dev on 2016/5/19.
 */
public class Hotel implements Serializable
{
    /**
     * 酒店id
     */
    private long hotelId;
    /**
     * 酒店默认图片
     */
    private String defaultImgUrl;
    /**
     * 酒店名称
     */
    private String hotelName;
    /**
     * 开业日期
     */
    private String openingDate;
    /**
     * 装修日期
     */
    private String renovationDate;
    /**
     * 入住时间
     */
    private String arrTime;
    /**
     * 离店时间
     */
    private String depTime;
    /**
     * 酒店电话
     */
    private String hotelTel;
    /**
     * 酒店邮箱
     */
    private String hotelEmail;
    /**
     * 酒店传真
     */
    private String hotelFax;
    /**
     * 星级
     */
    private int level;
    /**
     * 星级描述
     */
    private String levelDesc;
    /**
     * 图片数量
     */
    private int photoQuantity;
    /**
     * 评分
     */
    private float vote;
    /**
     * 酒店评价描述
     */
    private String voteDesc;
    /**
     * 旅客类型
     */
    private String guestType;
    /**
     * 收藏状态
     */
    private boolean collectionStatus;
    /**
     * 酒店视频url
     */
    private  String videoUrl;
    /**
     * 酒店地址信息
     */
    private AddrMap addrMap;
    /**
     * 酒店经营商品类型
     */
    private List<GoodsType>  goodsTypeList;

    /**
     * 特殊服务设施项
     */
    private List<HotelServiceFacilityBean> specialServiceList;

    /**
     * 基础服务设施列表
     */
    private List<HotelServiceFacilityBean> baseServiceList;

    private String address;
    private double lng;
    private double lat;
    private String tel;
    private int voteCount;

    public String getOpeningDate()
    {
        return openingDate;
    }

    public void setOpeningDate(String openingDate)
    {
        this.openingDate = openingDate;
    }

    public String getRenovationDate()
    {
        return renovationDate;
    }

    public void setRenovationDate(String renovationDate)
    {
        this.renovationDate = renovationDate;
    }

    public AddrMap getAddrMap()
    {
        return addrMap;
    }

    public void setAddrMap(AddrMap addrMap)
    {
        this.addrMap = addrMap;
    }

    public List<GoodsType> getGoodsTypeList()
    {
        return goodsTypeList;
    }

    public void setGoodsTypeList(List<GoodsType> goodsTypeList)
    {
        this.goodsTypeList = goodsTypeList;
    }

    public List<HotelServiceFacilityBean> getSpecialServiceList()
    {
        return specialServiceList;
    }

    public void setSpecialServiceList(List<HotelServiceFacilityBean> specialServiceList)
    {
        this.specialServiceList = specialServiceList;
    }

    public List<HotelServiceFacilityBean> getBaseServiceList()
    {
        return baseServiceList;
    }

    public void setBaseServiceList(List<HotelServiceFacilityBean> baseServiceList)
    {
        this.baseServiceList = baseServiceList;
    }

    public String getHotelTel()
    {
        return hotelTel;
    }

    public void setHotelTel(String hotelTel)
    {
        this.hotelTel = hotelTel;
    }

    public String getHotelEmail()
    {
        return hotelEmail;
    }

    public void setHotelEmail(String hotelEmail)
    {
        this.hotelEmail = hotelEmail;
    }

    public String getHotelFax()
    {
        return hotelFax;
    }

    public void setHotelFax(String hotelFax)
    {
        this.hotelFax = hotelFax;
    }

    public String getLevelDesc()
    {
        return levelDesc;
    }

    public void setLevelDesc(String levelDesc)
    {
        this.levelDesc = levelDesc;
    }

    public String getVoteDesc()
    {
        return voteDesc;
    }

    public void setVoteDesc(String voteDesc)
    {
        this.voteDesc = voteDesc;
    }

    public String getGuestType()
    {
        return guestType;
    }

    public void setGuestType(String guestType)
    {
        this.guestType = guestType;
    }

    private String introUrl;

    public long getHotelId()
    {
        return hotelId;
    }

    public void setHotelId(long hotelId)
    {
        this.hotelId = hotelId;
    }

    public String getDefaultImgUrl()
    {
        return defaultImgUrl;
    }

    public void setDefaultImgUrl(String defaultImgUrl)
    {
        this.defaultImgUrl = defaultImgUrl;
    }

    public String getHotelName()
    {
        return hotelName;
    }

    public void setHotelName(String hotelName)
    {
        this.hotelName = hotelName;
    }

    public float getVote()
    {
        return vote;
    }

    public void setVote(float vote)
    {
        this.vote = vote;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public double getLng()
    {
        return lng;
    }

    public void setLng(double lng)
    {
        this.lng = lng;
    }

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public String getTel()
    {
        return tel;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public int getVoteCount()
    {
        return voteCount;
    }

    public String getVideoUrl()
    {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl)
    {
        this.videoUrl = videoUrl;
    }

    public void setVoteCount(int voteCount)
    {
        this.voteCount = voteCount;
    }

    public boolean isCollectionStatus()
    {
        return collectionStatus;
    }

    public void setCollectionStatus(boolean collectionStatus)
    {
        this.collectionStatus = collectionStatus;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getPhotoQuantity()
    {
        return photoQuantity;
    }

    public void setPhotoQuantity(int photoQuantity)
    {
        this.photoQuantity = photoQuantity;
    }

    public String getIntroUrl()
    {
        return introUrl;
    }

    public void setIntroUrl(String introUrl)
    {
        this.introUrl = introUrl;
    }

    public String getArrTime()
    {
        return arrTime;
    }

    public void setArrTime(String arrTime)
    {
        this.arrTime = arrTime;
    }

    public String getDepTime()
    {
        return depTime;
    }

    public void setDepTime(String depTime)
    {
        this.depTime = depTime;
    }

    public static class  AddrMap implements Serializable{

        /**
         * 国家id
         */
        private int countryId;
        /**
         * 国家名称
         */
        private String countryName;
        /**
         * 省id
         */
        private int provinceId;
        /**
         * 省名称
         */
        private String provinceName;
        /**
         * 市id
         */
        private int cityId;
        /**
         * 市名称
         */
        private String cityName;
        /**
         * 区/县id
         */
        private  int districtId;
        /**
         * 区/县名称
         */
        private String districtName;
        /**
         * 地址
         */
        private String address;
        /**
         * 经度
         */
        private double lng;

        /**
         * 纬度
         */
        private double lat;

        public int getCountryId()
        {
            return countryId;
        }

        public void setCountryId(int countryId)
        {
            this.countryId = countryId;
        }

        public String getCountryName()
        {
            return countryName;
        }

        public void setCountryName(String countryName)
        {
            this.countryName = countryName;
        }

        public int getProvinceId()
        {
            return provinceId;
        }

        public void setProvinceId(int provinceId)
        {
            this.provinceId = provinceId;
        }

        public String getProvinceName()
        {
            return provinceName;
        }

        public void setProvinceName(String provinceName)
        {
            this.provinceName = provinceName;
        }

        public int getCityId()
        {
            return cityId;
        }

        public void setCityId(int cityId)
        {
            this.cityId = cityId;
        }

        public String getCityName()
        {
            return cityName;
        }

        public void setCityName(String cityName)
        {
            this.cityName = cityName;
        }

        public int getDistrictId()
        {
            return districtId;
        }

        public void setDistrictId(int districtId)
        {
            this.districtId = districtId;
        }

        public String getDistrictName()
        {
            return districtName;
        }

        public void setDistrictName(String districtName)
        {
            this.districtName = districtName;
        }

        public String getAddress()
        {
            return address;
        }

        public void setAddress(String address)
        {
            this.address = address;
        }

        public double getLng()
        {
            return lng;
        }

        public void setLng(double lng)
        {
            this.lng = lng;
        }

        public double getLat()
        {
            return lat;
        }

        public void setLat(double lat)
        {
            this.lat = lat;
        }
    }

    public static class GoodsType implements Serializable{

        /**
         * 商品类型编码  1房间、2餐厅、3酒吧、4会场、5SPA、6大堂吧、7卡权益、8限时抢购、9健身房、10游泳池
         */
        private String goodsTypeCode;

        /**
         * 商品类型名称
         */
        private String goodsTypeName;

        public String getGoodsTypeCode()
        {
            return goodsTypeCode;
        }

        public void setGoodsTypeCode(String goodsTypeCode)
        {
            this.goodsTypeCode = goodsTypeCode;
        }

        public String getGoodsTypeName()
        {
            return goodsTypeName;
        }

        public void setGoodsTypeName(String goodsTypeName)
        {
            this.goodsTypeName = goodsTypeName;
        }
    }

}
