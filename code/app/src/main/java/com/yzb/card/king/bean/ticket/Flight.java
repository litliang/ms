package com.yzb.card.king.bean.ticket;

import android.text.TextUtils;

import com.yzb.card.king.bean.my.NationalCountryBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.my.presenter.NationalCountryPresenter;
import com.yzb.card.king.ui.other.bean.City;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * 描述：航线类
 * 作者：殷曙光
 * 日期：2016/9/29 12:22
 */
public class Flight implements Serializable
{
    private Date startDate;

    private ShippingSpace shippingSpace;//仓位

    private City startCity;

    private City endCity;

    public Flight(){

    }

    public Flight(boolean flag)
    {
        startCity = new City();

        Location selectedCity = GlobalApp.getSelectedCity();

        if (TextUtils.isEmpty(selectedCity.cityName))
        {
            startCity.setCityId("1132");
            startCity.setCityName("上海");
            startCity.setType("1");
        } else
        {
            startCity.setCityId(selectedCity.cityId);
            startCity.setCityName(selectedCity.cityName);
            startCity.setType("1");
        }

        endCity = new City();

        endCity.setCityId("4439");

        endCity.setCityName("北京");

        endCity.setType("1");

        Calendar calendar = Calendar.getInstance();

        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

        startDate = calendar.getTime();
    }


    public City getStartCity()
    {
        return startCity;
    }

    public boolean setFStartCity(City startCity)
    {
        if (startCity != null && this.endCity != null &&
                startCity.getCityId().equals(this.endCity.getCityId()))
            return true;
        this.startCity = startCity;
        return false;
    }



    public City getEndCity()
    {
        return endCity;
    }

    public boolean setFEndCity(City endCity)
    {

        if (endCity != null && this.startCity != null &&
                endCity.getCityId().equals(this.startCity.getCityId())) return true;
        this.endCity = endCity;
        return false;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public ShippingSpace getShippingSpace()
    {
        if (shippingSpace == null)
        {
            shippingSpace = new ShippingSpace("Y", "标准经济舱");
        }
        return shippingSpace;
    }

    public void setShippingSpace(ShippingSpace shippingSpace)
    {
        this.shippingSpace = shippingSpace;
    }



    public void setStartCity(City startCity) {
        this.startCity = startCity;
    }


    public void setEndCity(City endCity) {
        this.endCity = endCity;
    }
}
