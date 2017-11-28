package com.yzb.card.king.bean.hotel;

import com.yzb.card.king.bean.SearchReusultBean;
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.sys.AppConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * 酒店产品请求参数
 * Created by 玉兵 on 2017/7/31.
 */

public class HotelProductListParam {
    /**
     * 位置级别(4,5)
     */
    private int addrLevel=-1;
    /**
     * 位置名称
     */
    private String addrName;
    /**
     * 位置id
     */
    private  long addrId;
    /**
     * 搜索位置类型
     */
    private int searchAddrType;
    /**
     * 行业id
     */
    private int industryId;
    /**
     * 礼品套餐类型 7卡权益；8限时抢购；
     */
    private int giftsType;
    /**
     * 搜索位置经度
     */
    private  double searchAddrLng;
    /**
     * 搜索位置纬度
     */
    private  double searchAddrLat;
    /**
     * 起始价
     */
    private String bgnPrice = "0";
    /**
     * 终止价
     */
    private String endPrice = Integer.MAX_VALUE+"";
    /**
     * 星级
     */
    private String levels="0";
    /**
     * 品牌类型
     */
    private String brandTypes="0";
    /**
     * 入住日期(yyyy-MM-dd)
     */
    private String arrDate;
    /**
     * 离店日期(yyyy-MM-dd)
     */
    private String  depDate;
    /**
     * 排序
     */
    private int sort =0 ;
    /**
     * 分页起始数
     */
    private  int pageStart;
    /**
     * 每页条目数
     */
    private  int pageSize = AppConstant.MAX_PAGE_NUM;

    /**
     * 最低评分
     */
    private String minVote;
    /**
     * 优惠银行id
     */
    private String bankIds;
    /**
     * 优惠促销项
     */
    private  String disTypes;
    /**
     * 分期状态
     */
    private String stageBankIds;

    /**
     * 过滤条件
     */
    private List<SubItemBean> filterList;
    /**
     * 酒店品牌(酒店列表头部)
     */
    private  List<SubItemBean> hotelBrandList;
    /**
     * 酒店位置区域（酒店列表底部的位置区域）
     */
    private  List<SubItemBean> hotelPositionList;
    /**
     * 酒店关键字搜索（酒店搜索页的选项）
     */
    private  List<SubItemBean> hotelKeyWordList;
    /**
     *  搜索条件
     */
    private List<SearchReusultBean>  searchList;
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 有效期限 卡权益（1三个月；2半年；3一年；4两年；5五年；）。限时抢购（直接传月份，如7，8，9，10）
     */
    private String effMonth = null;
    /**
     *  门店适用类型 1唯一门店可用；2多门店通用；
     */
    private String storeUseType = null;
    /**
     * 活动类型 （1、商家活动；2、银行活动）
     */
    private int actType;

    public int getAddrLevel()
    {
        return addrLevel;
    }

    public void setAddrLevel(int addrLevel)
    {
        this.addrLevel = addrLevel;
    }

    public long getAddrId() {
        return addrId;
    }

    public void setAddrId(long addrId) {
        this.addrId = addrId;
    }

    public String getAddrName() {
        return addrName;
    }

    public void setAddrName(String addrName) {
        this.addrName = addrName;
    }

    public int getSearchAddrType() {
        return searchAddrType;
    }

    public void setSearchAddrType(int searchAddrType) {
        this.searchAddrType = searchAddrType;
    }

    public double getSearchAddrLng() {
        return searchAddrLng;
    }

    public void setSearchAddrLng(double searchAddrLng) {
        this.searchAddrLng = searchAddrLng;
    }

    public double getSearchAddrLat() {
        return searchAddrLat;
    }

    public void setSearchAddrLat(double searchAddrLat) {
        this.searchAddrLat = searchAddrLat;
    }

    public String getBgnPrice() {
        return bgnPrice;
    }

    public void setBgnPrice(String bgnPrice) {
        this.bgnPrice = bgnPrice;
    }

    public String getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(String endPrice) {
        this.endPrice = endPrice;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    public String getBrandTypes() {
        return brandTypes;
    }

    public void setBrandTypes(String brandTypes) {
        this.brandTypes = brandTypes;
    }

    public String getArrDate() {
        return arrDate;
    }

    public void setArrDate(String arrDate) {
        this.arrDate = arrDate;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getMinVote() {
        return minVote;
    }

    public void setMinVote(String minVote) {
        this.minVote = minVote;
    }

    public String getBankIds() {
        return bankIds;
    }

    public void setBankIds(String bankIds) {
        this.bankIds = bankIds;
    }

    public String getDisTypes() {
        return disTypes;
    }

    public void setDisTypes(String disTypes) {
        this.disTypes = disTypes;
    }

    public String getStageBankIds() {
        return stageBankIds;
    }

    public void setStageBankIds(String stageBankIds) {
        this.stageBankIds = stageBankIds;
    }

    public List<SubItemBean> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<SubItemBean> filterList) {

        if(filterList == null){

            filterList = new ArrayList<>();
        }

        if(hotelBrandList  != null){

            filterList.addAll(hotelBrandList);
        }

        if(hotelPositionList != null){

            filterList.addAll(hotelPositionList);
        }

        if(hotelKeyWordList != null){

            filterList.addAll(hotelKeyWordList);

        }

        this.filterList = filterList;
    }

    public List<SearchReusultBean> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<SearchReusultBean> searchList) {
        this.searchList = searchList;
    }
    /**
     * 床型
     */
    private String roomsTypes;

    public int getActType()
    {
        return actType;
    }

    public void setActType(int actType)
    {
        this.actType = actType;
    }

    public String getRoomsTypes() {
        return roomsTypes;
    }

    public void setRoomsTypes(String roomsTypes) {
        this.roomsTypes = roomsTypes;
    }

    public int getGiftsType()
    {
        return giftsType;
    }

    public void setGiftsType(int giftsType)
    {
        this.giftsType = giftsType;
    }

    public int getIndustryId()
    {
        return industryId;
    }

    public void setIndustryId(int industryId)
    {
        this.industryId = industryId;
    }

    public String getStoreName()
    {
        return storeName;
    }

    public void setStoreName(String storeName)
    {
        this.storeName = storeName;
    }

    public String getEffMonth()
    {
        return effMonth;
    }

    public void setEffMonth(String effMonth)
    {
        this.effMonth = effMonth;
    }

    public String getStoreUseType()
    {
        return storeUseType;
    }

    public void setStoreUseType(String storeUseType)
    {
        this.storeUseType = storeUseType;
    }

    public List<SubItemBean> getHotelBrandList() {
        return hotelBrandList;
    }

    public void setHotelBrandList(List<SubItemBean> hotelBrandList) {
        this.hotelBrandList = hotelBrandList;
    }

    public List<SubItemBean> getHotelPositionList() {
        return hotelPositionList;
    }

    public void setHotelPositionList(List<SubItemBean> hotelPositionList) {
        this.hotelPositionList = hotelPositionList;
    }

    public List<SubItemBean> getHotelKeyWordList() {
        return hotelKeyWordList;
    }

    public void setHotelKeyWordList(List<SubItemBean> hotelKeyWordList) {
        this.hotelKeyWordList = hotelKeyWordList;
    }
}
