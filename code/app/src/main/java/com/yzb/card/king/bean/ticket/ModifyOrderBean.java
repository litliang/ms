package com.yzb.card.king.bean.ticket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类名： ModifyOrderBean
 * 作者： Lei Chao.
 * 日期： 2016-10-18
 * 描述： 确认改签条件参数
 */
public class ModifyOrderBean implements Serializable
{

    public String diffPriceAdt = "";
    public String orderNo = "";
    public String depCode = "";
    public String arrivalCode = "";
    public String depDate = "";
    public String cabinCode = "";
    public String flightNo = "";
    public String oriDepartDate = "";
    public List<Map<String, String>> coustomer = new ArrayList<>();
}