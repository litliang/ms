package com.yzb.card.king.ui.discount.bean;

import com.yzb.card.king.bean.ticket.BankInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/7/29
 * 描  述：
 */
public class HotelListBean implements Serializable {


    private long hotelId;
    private String defaultImgUrl;
    private String hotelName;
    private String levelDesc;
    private double vote;
    private String openingDate;
    private double centreDistance;
    private String positionNames;
    private String labels;
    private boolean bankStatus;
    private boolean shopStatus;
    private boolean platformStatus;
    private boolean cancelStatus;
    private String selfVote;
    private String lastReserve;
    private int minPrice;
    private double lng;
    private double lat;
    private List<BankInfo> bankList;
    private int level;

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

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


    public boolean isCancelStatus()
    {
        return cancelStatus;
    }

    public void setCancelStatus(boolean cancelStatus)
    {
        this.cancelStatus = cancelStatus;
    }

    public String getHotelName()
    {
        return hotelName;
    }

    public void setHotelName(String hotelName)
    {
        this.hotelName = hotelName;
    }

    public String getLevelDesc()
    {
        return levelDesc;
    }

    public void setLevelDesc(String levelDesc)
    {
        this.levelDesc = levelDesc;
    }

    public double getVote()
    {
        return vote;
    }

    public void setVote(double vote)
    {
        this.vote = vote;
    }

    public String getOpeningDate()
    {
        return openingDate;
    }

    public void setOpeningDate(String openingDate)
    {
        this.openingDate = openingDate;
    }

    public double getCentreDistance()
    {
        return centreDistance;
    }

    public void setCentreDistance(double centreDistance)
    {
        this.centreDistance = centreDistance;
    }

    public String getPositionNames()
    {
        return positionNames;
    }

    public void setPositionNames(String positionNames)
    {
        this.positionNames = positionNames;
    }

    public String getLabels()
    {
        return labels;
    }

    public void setLabels(String labels)
    {
        this.labels = labels;
    }

    public boolean isBankStatus()
    {
        return bankStatus;
    }

    public void setBankStatus(boolean bankStatus)
    {
        this.bankStatus = bankStatus;
    }

    public boolean isShopStatus()
    {
        return shopStatus;
    }

    public void setShopStatus(boolean shopStatus)
    {
        this.shopStatus = shopStatus;
    }

    public boolean isPlatformStatus()
    {
        return platformStatus;
    }

    public void setPlatformStatus(boolean platformStatus)
    {
        this.platformStatus = platformStatus;
    }

    public String getSelfVote()
    {
        return selfVote;
    }

    public void setSelfVote(String selfVote)
    {
        this.selfVote = selfVote;
    }

    public String getLastReserve()
    {
        return lastReserve;
    }

    public void setLastReserve(String lastReserve)
    {
        this.lastReserve = lastReserve;
    }

    public int getMinPrice()
    {
        return minPrice;
    }

    public void setMinPrice(int minPrice)
    {
        this.minPrice = minPrice;
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

    public List<BankInfo> getBankList()
    {
        return bankList;
    }

    public void setBankList(List<BankInfo> bankList)
    {
        this.bankList = bankList;
    }
}
