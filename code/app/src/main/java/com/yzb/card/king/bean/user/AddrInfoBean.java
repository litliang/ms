package com.yzb.card.king.bean.user;

import java.io.Serializable;

/**
 * 类  名：地址信息
 * 作  者：Li Yubing
 * 日  期：2016/12/22
 * 描  述：
 */
public class AddrInfoBean implements Serializable{

    /**
     * 国籍id
     */
    private long countryId;
    /**
     * 国籍名称
     */
    private String countryName;
    /**
     * 省id
     */
    private long provinceId;
    /**
     * 省名称
     */
    private String provinceName;
    /**
     * 市id
     */
    private long  cityId;
    /**
     * 市名称
     */
    private String cityName;
    /**
     * 市区域id
     */
    private long regionId;
    /**
     * 市区域名称
     */
    private String regionName;
    /**
     * 地址
     */
    private String address;

    public long getCountryId()
    {
        return countryId;
    }

    public void setCountryId(long countryId)
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

    public long getProvinceId()
    {
        return provinceId;
    }

    public void setProvinceId(long provinceId)
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

    public long getCityId()
    {
        return cityId;
    }

    public void setCityId(long cityId)
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

    public long getRegionId()
    {
        return regionId;
    }

    public void setRegionId(long regionId)
    {
        this.regionId = regionId;
    }

    public String getRegionName()
    {
        return regionName;
    }

    public void setRegionName(String regionName)
    {
        this.regionName = regionName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
}
