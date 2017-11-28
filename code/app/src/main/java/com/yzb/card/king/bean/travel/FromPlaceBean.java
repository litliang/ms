package com.yzb.card.king.bean.travel;

/**
 * 功能：旅游出发地；
 *
 * @author:gengqiyun
 * @date: 2016/11/23
 */
public class FromPlaceBean
{
    private String cityId;//	Long  城市id		N
    private String cityName;//	String	城市名称	N
    private double addFee;//	BigDecimal	附加费用	N	目前都是0

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

    public double getAddFee()
    {
        return addFee;
    }

    public void setAddFee(double addFee)
    {
        this.addFee = addFee;
    }
}
