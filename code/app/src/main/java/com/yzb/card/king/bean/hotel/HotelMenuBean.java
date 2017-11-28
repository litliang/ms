package com.yzb.card.king.bean.hotel;

import java.io.Serializable;
import java.util.List;

/**
 * 类  名：酒店菜单类
 * 作  者：Li Yubing
 * 日  期：2017/8/16
 * 描  述：
 */
public class HotelMenuBean implements Serializable{

    private String key;

    private List<HotelChildMenuBean> value;

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public List<HotelChildMenuBean> getValue()
    {
        return value;
    }

    public void setValue(List<HotelChildMenuBean> value)
    {
        this.value = value;
    }

    public static class  HotelChildMenuBean implements Serializable{

        private String goodsName;

        private  int goodsPrice;

        private String goodsUnit;

        public String getGoodsName()
        {
            return goodsName;
        }

        public void setGoodsName(String goodsName)
        {
            this.goodsName = goodsName;
        }

        public int getGoodsPrice()
        {
            return goodsPrice;
        }

        public void setGoodsPrice(int goodsPrice)
        {
            this.goodsPrice = goodsPrice;
        }

        public String getGoodsUnit()
        {
            return goodsUnit;
        }

        public void setGoodsUnit(String goodsUnit)
        {
            this.goodsUnit = goodsUnit;
        }
    }

}


