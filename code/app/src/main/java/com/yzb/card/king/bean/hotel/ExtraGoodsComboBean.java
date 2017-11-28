package com.yzb.card.king.bean.hotel;

import com.yzb.card.king.bean.common.FavInfoBean;

import java.io.Serializable;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/5
 * 描  述：包括 餐厅、3酒吧、5SPA、6大堂吧、9健身房、10游泳池套餐
 */
public class ExtraGoodsComboBean implements Serializable {
    /**
     * 套餐id
     */
    private long policysId;
    /**
     * 酒店id
     */
    private long hotelId;
    /**
     * 商家id
     */
    private long shopId;
    /**
     * 套餐名称
     */
    private String policysName;
    /**
     * 套餐适用人数
     */
    private String policysLimits;
    /**
     * 线上价
     */
    private int onlinePrice;
    /**
     * 门店价
     */
    private int storePrice;
    /**
     * 是否可退
     */
    private String cancelStatus;
    /**
     * 有效日期描述
     */
    private String effDateDesc;
    /**
     * 使用时段描述
     */
    private String useDateDesc;
    /**
     * 使用时间描述
     */
    private String useTimeDesc;
    /**
     * 商家
     */
    private String shopName;
    /**
     * 套餐介绍
     */
    private String policysIntro;
    /**
     * 使用规则
     */
    private String policysRule;
    /**
     * 商家服务
     */
    private String policysServices;
    /**
     * 预约信息
     */
    private String orderInfo;
    /**
     * 套餐餐钟
     */
    private String mealTypes;
    /**
     * 套餐使用次数
     */
    private int policysQuantity;
    /**
     * 不适用人群说明
     */
    private String unsuitedIntro;
    /**
     * 优惠信息
     */
    private FavInfoBean disMap;

    /**
     * 酒店额外对象
     */
    private HotelExtraProductBean hotelExtraProductBean;

    /**
     * 自助餐内容项
     */
    private List<ServiceItem> buffetItems;
    /**
     * spa服务项目
     */
    private List<ServiceItem> spaItems;

    private int timeLegnth;

    public int getTimeLegnth()
    {
        return timeLegnth;
    }

    public void setTimeLegnth(int timeLegnth)
    {
        this.timeLegnth = timeLegnth;
    }

    public List<ServiceItem> getSpaItems()
    {
        return spaItems;
    }

    public void setSpaItems(List<ServiceItem> spaItems)
    {
        this.spaItems = spaItems;
    }

    public List<ServiceItem> getBuffetItems()
    {
        return buffetItems;
    }

    public void setBuffetItems(List<ServiceItem> buffetItems)
    {
        this.buffetItems = buffetItems;
    }

    public FavInfoBean getDisMap()
    {
        return disMap;
    }

    public void setDisMap(FavInfoBean disMap)
    {
        this.disMap = disMap;
    }

    public long getPolicysId()
    {
        return policysId;
    }

    public void setPolicysId(long policysId)
    {
        this.policysId = policysId;
    }

    public long getShopId()
    {
        return shopId;
    }

    public void setShopId(long shopId)
    {
        this.shopId = shopId;
    }

    public String getPolicysName()
    {
        return policysName;
    }

    public void setPolicysName(String policysName)
    {
        this.policysName = policysName;
    }

    public String getPolicysLimits()
    {
        return policysLimits;
    }

    public void setPolicysLimits(String policysLimits)
    {
        this.policysLimits = policysLimits;
    }

    public int getOnlinePrice()
    {
        return onlinePrice;
    }

    public void setOnlinePrice(int onlinePrice)
    {
        this.onlinePrice = onlinePrice;
    }

    public int getStorePrice()
    {
        return storePrice;
    }

    public void setStorePrice(int storePrice)
    {
        this.storePrice = storePrice;
    }

    public String getCancelStatus()
    {
        return cancelStatus;
    }

    public void setCancelStatus(String cancelStatus)
    {
        this.cancelStatus = cancelStatus;
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

    public String getShopName()
    {
        return shopName;
    }

    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }

    public String getPolicysIntro()
    {
        return policysIntro;
    }

    public void setPolicysIntro(String policysIntro)
    {
        this.policysIntro = policysIntro;
    }

    public String getPolicysRule()
    {
        return policysRule;
    }

    public void setPolicysRule(String policysRule)
    {
        this.policysRule = policysRule;
    }

    public String getPolicysServices()
    {
        return policysServices;
    }

    public void setPolicysServices(String policysServices)
    {
        this.policysServices = policysServices;
    }

    public String getOrderInfo()
    {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo)
    {
        this.orderInfo = orderInfo;
    }

    public String getMealTypes()
    {
        return mealTypes;
    }

    public void setMealTypes(String mealTypes)
    {
        this.mealTypes = mealTypes;
    }

    public int getPolicysQuantity()
    {
        return policysQuantity;
    }

    public void setPolicysQuantity(int policysQuantity)
    {
        this.policysQuantity = policysQuantity;
    }

    public String getUnsuitedIntro()
    {
        return unsuitedIntro;
    }

    public void setUnsuitedIntro(String unsuitedIntro)
    {
        this.unsuitedIntro = unsuitedIntro;
    }

    public HotelExtraProductBean getHotelExtraProductBean()
    {
        return hotelExtraProductBean;
    }

    public void setHotelExtraProductBean(HotelExtraProductBean hotelExtraProductBean)
    {
        this.hotelExtraProductBean = hotelExtraProductBean;
    }

    public void setHotelId(long hotelId)
    {
        this.hotelId = hotelId;
    }

    public long getHotelId()
    {
        return hotelId;
    }

    public static class ServiceItem implements  Serializable{

        private String itemName;

        public String getItemName()
        {
            return itemName;
        }

        public void setItemName(String itemName)
        {
            this.itemName = itemName;
        }
    }
}
