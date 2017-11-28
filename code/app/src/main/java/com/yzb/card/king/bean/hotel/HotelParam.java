package com.yzb.card.king.bean.hotel;

import com.yzb.card.king.bean.ticket.FilterType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 描述：酒店首页参数类
 * 作者：殷曙光
 * 日期：2016/10/25 11:54
 */
public class HotelParam implements Serializable
{
    /**
     * 来源 1，主页；2，附近酒店；3，列表页
     */
    public static final int SOURCE_HOME_PAGE = 1;
    public static final int SOURCE_NEARBY_HOTLE = 2;
    public static final int SOURCE_LIST_PAGE = 3;


    //来源
    private int source;

    //子分类id（多个使用英文逗号分割）
    private String childIndustryIds;

    private String cityId;

    //    经度
    private double lng;

    //    纬度
    private double lat;

    //距离（公里）
    private float distance;
    //    酒店星级
    private String levels;

    //    入住日期
    private Date arrDate;

    //    离店日期
    private Date depDate;

    //    起始价
    private int bgnPrice;

    //    终止价
    private int endPrice;

    //    排序
    private String sort;

    //    开始查找的数据的下标
    private int pageStart;

    //每页条数
    private int pageSize;

    //最低评分
    private float minVote;
    //早餐id（1含早餐、2单份早餐、3双份早餐)）（多个使用英文逗号分割）
    private String breakfasts;
    //床型id（1大床；2双床；3三人/家庭；)）（多个使用英文逗号分割）
    private String bedTypes;
    //筛选条件
    private List<FilterType> searchList;
    //位置条件
    private List<FilterType> positionList;
    //银行优惠状态（0：非银行优惠  1：关联个人银行银行机票，2：关联更多银行优惠机票 ）
    private String bankStruts;

    private String cancelStatus;

    private String keyword;

    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    public int getSource()
    {

        return source ;
    }

    public void setSource(int source)
    {
        this.source = source;
    }

    public String getChildIndustryIds()
    {
        return childIndustryIds;
    }

    public void setChildIndustryIds(String childIndustryIds)
    {
        this.childIndustryIds = childIndustryIds;
    }

    public float getDistance()
    {
        return distance;
    }

    public void setDistance(float distance)
    {
        this.distance = distance;
    }

    public String getCityId()
    {
        return cityId;
    }

    public void setCityId(String cityId)
    {
        this.cityId = cityId;
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

    public String getLevels()
    {
        return levels;
    }

    public void setLevels(String levels)
    {
        this.levels = levels;
    }

    public Date getArrDate()
    {
        return arrDate;
    }

    public void setArrDate(Date arrDate)
    {
        this.arrDate = arrDate;
    }

    public Date getDepDate()
    {
        return depDate;
    }

    public void setDepDate(Date depDate)
    {
        this.depDate = depDate;
    }

    public int getBgnPrice()
    {
        return bgnPrice;
    }

    public void setBgnPrice(int bgnPrice)
    {
        this.bgnPrice = bgnPrice;
    }

    public int getEndPrice()
    {
        return endPrice;
    }

    public void setEndPrice(int endPrice)
    {
        this.endPrice = endPrice;
    }

    public String getSort()
    {
        return sort;
    }

    public void setSort(String sort)
    {
        this.sort = sort;
    }

    public int getPageStart()
    {
        return pageStart;
    }

    public void setPageStart(int pageStart)
    {
        this.pageStart = pageStart;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public float getMinVote()
    {
        return minVote;
    }

    public void setMinVote(float minVote)
    {
        this.minVote = minVote;
    }

    public String getBreakfasts()
    {
        return breakfasts;
    }

    public void setBreakfasts(String breakfasts)
    {
        this.breakfasts = breakfasts;
    }

    public String getBedTypes()
    {
        return bedTypes;
    }

    public void setBedTypes(String bedTypes)
    {
        this.bedTypes = bedTypes;
    }

    public List<FilterType> getSearchList()
    {
        return searchList;
    }

    public void setSearchList(List<FilterType> searchList)
    {
        this.searchList = searchList;
    }

    public List<FilterType> getPositionList()
    {
        return positionList;
    }

    public void setPositionList(List<FilterType> positionList)
    {
        this.positionList = positionList;
    }

    public String getBankStruts()
    {
        return bankStruts;
    }

    public void setBankStruts(String bankStruts)
    {
        this.bankStruts = bankStruts;
    }

    public String getCancelStatus()
    {
        return cancelStatus;
    }

    public void setCancelStatus(String cancelStatus)
    {
        this.cancelStatus = cancelStatus;
    }
}
