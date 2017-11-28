package com.yzb.card.king.ui.other.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.xutils.db.annotation.Column;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/27 13:04
 */
public abstract class Place implements IPlace, Parcelable, Serializable
{
    @Column(name = "id", isId = true)
    protected String id;

    @Column(name = "firstSpell")
    protected String firstSpell;

    //全拼
    @Column(name = "cityRuby")
    protected String cityRuby;

    //国内:1，国外:2？
    @Column(name = "type")
    protected String type;

    @Column(name = "lng")
    protected double lng;

    @Column(name = "lat")
    protected double lat;

    @Column(name = "cityLevel")
    protected int cityLevel;

    public int getCityLevel()
    {
        return cityLevel;
    }

    public void setCityLevel(int cityLevel)
    {
        this.cityLevel = cityLevel;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getFirstSpell()
    {
        return firstSpell;
    }

    public void setFirstSpell(String firstSpell)
    {
        this.firstSpell = firstSpell;
    }

    public String getCityRuby()
    {
        return cityRuby;
    }

    public void setCityRuby(String cityRuby)
    {
        this.cityRuby = cityRuby;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
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

    @Override
    public int describeContents()
    {
        return 0;
    }

    public Place()
    {

    }

    public Place(Parcel in)
    {
        id = in.readString();
        firstSpell = in.readString();
        cityRuby = in.readString();
        type = in.readString();
        lng = in.readDouble();
        lat = in.readDouble();
        cityLevel = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(id);
        dest.writeString(firstSpell);
        dest.writeString(cityRuby);
        dest.writeString(type);
        dest.writeDouble(lng);
        dest.writeDouble(lat);
        dest.writeInt(cityLevel);
    }


}
