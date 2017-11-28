package com.yzb.card.king.ui.transport.bean;

import com.yzb.card.king.ui.discount.bean.BusTypeGradeBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by yinsg on 2016/6/1.
 */
public class SpecialCar implements Serializable {
    public String startPlace;
    public String endPlace;
    public Date startDate;

    public String imageCode;
    public String carId;
    public String carTypeName;
    public String price;
    public String personNumber;
    public String luggageNumber;
    public String cardBrand;
    public String sameLevelCar;
    public String perMinutePrice;
    public String startingPrice;
    public String perKilometerPrice;
    public List<BusTypeGradeBean> supplierList;

    public int source;

}
