package com.yzb.card.king.bean.hotel;

import java.io.Serializable;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/10/13
 * 描  述：
 */
public class HotelDetailServiceBean implements Serializable{

    /**
     * 特殊服务设施项
     */
    private List<HotelServiceFacilityBean> specialServiceList;

    /**
     * 基础服务设施列表
     */
    private List<HotelServiceFacilityBean> baseServiceList;

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
}
