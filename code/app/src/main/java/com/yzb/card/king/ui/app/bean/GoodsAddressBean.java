package com.yzb.card.king.ui.app.bean;

import java.io.Serializable;

/**
 * 功能：收货地址；
 *
 * @author:gengqiyun
 * @date: 2016/6/21
 */
public class GoodsAddressBean implements Serializable
{
    public String addressId;  //地址id
    public String contacts; //联系人
    public String phone;
    public String provinceId; //省id
    public String provinceName; //省名称
    public String cityId; //城市id
    public String cityName;// 市名称
    public String districtId;//区县id
    public String districtName;//区县名称

    private String fee;//配送费

    public String address; //地址

    public boolean isDefault; //是否是默认的收货地址；0:非；1：是；

    public String status;//状态  1可用；2默认；
    /**
     *   配送状态 (0未配送、1已发货、2已收获)
     */
    private String expressStatus;

    public String getExpressStatus()
    {
        return expressStatus;
    }

    public void setExpressStatus(String expressStatus)
    {
        this.expressStatus = expressStatus;
    }

    public String getAddressId()
    {
        return addressId;
    }

    public void setAddressId(String addressId)
    {
        this.addressId = addressId;
    }

    public String getContacts()
    {
        return contacts;
    }

    public void setContacts(String contacts)
    {
        this.contacts = contacts;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getProvinceId()
    {
        return provinceId;
    }

    public void setProvinceId(String provinceId)
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


    public String getFee()
    {
        return fee;
    }

    public void setFee(String fee)
    {
        this.fee = fee;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public boolean getIsDefault()
    {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault)
    {
        this.isDefault = isDefault;
    }

    public String getDistrictId()
    {
        return districtId;
    }

    public void setDistrictId(String districtId)
    {
        this.districtId = districtId;
    }

    public String getDistrictName()
    {
        return districtName;
    }

    public void setDistrictName(String districtName)
    {
        this.districtName = districtName;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;

        if("2".equals(status)){
            isDefault = true;
        }else{
            isDefault = false;
        }

    }
}
