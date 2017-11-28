package com.yzb.card.king.ui.discount.bean;

import java.io.Serializable;

/**
 * Created by gengqiyun on 2016/4/26.
 * id	spotName	address	lat	lng	storeNum	recommNum	createTime	cityId	image
 主键	景点名称	景点地址	经度	纬度	餐厅数量	特色餐厅数量	创建时间	城市id	图片

 */
public class JdmsBean implements Serializable {
    public String id;
    public String spotName;
    public String address;
    public double lat;

    public double lng;
    public String storeNum;
    public String recommNum;

    public String createTime;
    public String cityId;
    public String image;
}
