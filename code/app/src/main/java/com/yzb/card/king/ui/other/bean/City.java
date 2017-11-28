package com.yzb.card.king.ui.other.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/22 10:16
 */
@Table(name = "city")
public class City extends Place
{
    @Column(name = "cityId")
    private String cityId;
    @Column(name = "cityName")
    private String cityName;

    public City(){}
    public City(String cityId, String cityName)
    {
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public City(Parcel in)
    {
        super(in);
        cityId = in.readString();
        cityName = in.readString();
    }

    public static final Parcelable.Creator<Place> CREATOR = new Creator<Place>()
    {
        @Override
        public Place[] newArray(int size)
        {
            return new City[size];
        }

        @Override
        public Place createFromParcel(Parcel in)
        {
            return new City(in);
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        super.writeToParcel(dest, flags);
        dest.writeString(cityId);
        dest.writeString(cityName);
    }

    public String getCityId()
    {
        return cityId;
    }

    public void setCityId(String cityId)
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


    @Override
    public String getPlaceName()
    {
        return this.cityName;
    }

    @Override
    public String getPlaceId()
    {
        return this.cityId;
    }


}
