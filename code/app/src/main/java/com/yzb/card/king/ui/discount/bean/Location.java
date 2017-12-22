package com.yzb.card.king.ui.discount.bean;

import android.text.TextUtils;

import com.baidu.mapapi.model.LatLng;

import java.io.Serializable;

/**
 * 作者：殷曙光
 * 日期：2016/7/25 12:42
 * 描述：
 */

public class Location implements Serializable
{

    public String cityName;
    public String cityId;
    public double longitude = 0;//经度；
    public double latitude = 0; // 纬度；
    public String address = "";
    public String district; //区；
    public String province;//省份；
    public String provinceId;//省份id；
    public String street; // 街道
    public String streetNumber;//号
    public String country;
    public String countryCode;
    public String distance; //距离；
    public int cityLevel;

    public int getCityLevel() {
        return cityLevel;
    }

    public void setCityLevel(int cityLevel) {
        this.cityLevel = cityLevel;
    }

    public String msg;//定位信息
    public String baiduLocationDescribe; //百度定位描述；


    public Location()
    {

    }

    public String getProvinceId()
    {
        return provinceId;
    }

    public void setProvinceId(String provinceId)
    {
        this.provinceId = provinceId;
    }

    public Location(double latitude, double longitude, String address, String distance)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.distance = distance;
    }

    public Location(Location mCity)
    {
        this.cityName = mCity.cityName;
        this.cityId = mCity.cityId;
        this.longitude = mCity.longitude;
        this.latitude = mCity.latitude;
        this.address = mCity.address;
        this.district = mCity.district;
        this.province = mCity.province;
        this.street = mCity.street;
        this.streetNumber = mCity.streetNumber;
        this.country = mCity.country;
        this.countryCode = mCity.countryCode;
        this.distance = mCity.distance;
        this.msg = mCity.msg;
        this.cityLevel = mCity.cityLevel;
    }

    public String getBaiduLocationDescribe()
    {
        return baiduLocationDescribe;
    }

    public String getHandleBaiduLocationDescribe()
    {
        if (!TextUtils.isEmpty(baiduLocationDescribe))
        {
            return baiduLocationDescribe.replace("在", "").replace("附近", "");
        }
        return baiduLocationDescribe;
    }

    public void setBaiduLocationDescribe(String baiduLocationDescribe)
    {
        this.baiduLocationDescribe = baiduLocationDescribe;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public String getConcatAddress()
    {
        return province + cityName + district + street + streetNumber;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public String getCityId()
    {
        return cityId;
    }

    public void setCityId(String cityId)
    {
        this.cityId = cityId;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getDistrict()
    {
        return district;
    }

    public void setDistrict(String district)
    {
        this.district = district;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getStreetNumber()
    {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber)
    {
        this.streetNumber = streetNumber;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getDistance()
    {
        return distance;
    }

    public void setDistance(String distance)
    {
        this.distance = distance;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public LatLng getLatLng()
    {
        return new LatLng(latitude, longitude);
    }


    public String addressInfoStr(){

        int index =  province.indexOf(cityName);


        if(index == -1){

            return province+cityName+district+street;

        }else {

            return province+district+street;
        }


    }
}

