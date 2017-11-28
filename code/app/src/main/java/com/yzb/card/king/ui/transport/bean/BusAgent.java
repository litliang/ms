package com.yzb.card.king.ui.transport.bean;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * Created by yinsg on 2016/5/27.
 */
public class BusAgent implements Serializable{
    public String shopId;
    public String shopName;
    public float price;
    public float tax = 50;

    public List<Map> discountActivity;
    public List<Map> pointstActivity;
    public List<Map> couponActivity;
    public List<Map> bounsActivity;
}
