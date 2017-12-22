package com.yzb.card.king.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/20
 * 描  述：条目--酒店品牌、城市
 */
public class SubItemBean implements Serializable{
    /**
     * 子类别筛选项id
     */
    private String filterId;
    /**
     * 子类别编码
     */
    private String childTypeCode;
    /**
     * 子类别筛选项名称
     */
    private String filterName;
    /**
     * 子类别筛选项经度
     */
    private double filterLng;
    /**
     * 子类别筛选项纬度
     */
    private double filterLat;

    /**
     * 本地数据编号：1：表示评分筛选数据；2：表示促销优惠筛选数据;3:表示有效期限；4表示适用门店
     */
    private int localDataCode =-1 ;

    /**
     * 本地数据类编号：0-供应商；1-支付方式；2-餐种；3-床型
     */
    private int localDataHotelRoomCode = -1;


    private List<ChildSubItemBean> childList;

    private boolean isDefault =false;

    public int getLocalDataCode()
    {
        return localDataCode;
    }

    public void setLocalDataCode(int localDataCode)
    {
        this.localDataCode = localDataCode;
    }

    public boolean isDefault()
    {
        return isDefault;
    }

    public void setDefault(boolean aDefault)
    {
        isDefault = aDefault;
    }

    public String getFilterId() {
        return filterId;
    }

    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    public String getChildTypeCode() {
        return childTypeCode;
    }

    public void setChildTypeCode(String childTypeCode) {
        this.childTypeCode = childTypeCode;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public double getFilterLng() {
        return filterLng;
    }

    public void setFilterLng(double filterLng) {
        this.filterLng = filterLng;
    }

    public double getFilterLat() {
        return filterLat;
    }

    public void setFilterLat(double filterLat) {
        this.filterLat = filterLat;
    }

    public int getLocalDataHotelRoomCode()
    {
        return localDataHotelRoomCode;
    }

    public void setLocalDataHotelRoomCode(int localDataHotelRoomCode)
    {
        this.localDataHotelRoomCode = localDataHotelRoomCode;
    }

    public boolean ifSame(SubItemBean bean){

        if(filterId.equals(bean.getFilterId()) && childTypeCode.equals(bean.getChildTypeCode())){

            return true;

        }else {

            return false;
        }

    }

    public List<ChildSubItemBean> getChildList() {
        return childList;
    }

    public void setChildList(List<ChildSubItemBean> childList) {
        this.childList = childList;
    }

   public  static class  ChildSubItemBean implements Serializable {

        /**
         * 子类别筛选项id
         */
        private String filterId;
        /**
         * 子类别编码
         */
        private String childTypeCode;
        /**
         * 子类别筛选项名称
         */
        private String filterName;
        /**
         * 子类别筛选项经度
         */
        private double filterLng;
        /**
         * 子类别筛选项纬度
         */
        private double filterLat;

       private boolean ifDefault = false;

       public boolean isIfDefault() {
           return ifDefault;
       }

       public void setIfDefault(boolean ifDefault) {
           this.ifDefault = ifDefault;
       }

       public String getFilterId() {
            return filterId;
        }

        public void setFilterId(String filterId) {
            this.filterId = filterId;
        }

        public String getChildTypeCode() {
            return childTypeCode;
        }

        public void setChildTypeCode(String childTypeCode) {
            this.childTypeCode = childTypeCode;
        }

        public String getFilterName() {
            return filterName;
        }

        public void setFilterName(String filterName) {
            this.filterName = filterName;
        }

        public double getFilterLng() {
            return filterLng;
        }

        public void setFilterLng(double filterLng) {
            this.filterLng = filterLng;
        }

        public double getFilterLat() {
            return filterLat;
        }

        public void setFilterLat(double filterLat) {
            this.filterLat = filterLat;
        }
    }

}
