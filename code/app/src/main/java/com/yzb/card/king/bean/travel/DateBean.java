package com.yzb.card.king.bean.travel;

import android.text.TextUtils;

import com.yzb.card.king.ui.app.bean.BaseBean;

/**
 * 功能：旅游日期bean;
 *
 * @author:gengqiyun
 * @date: 2016/11/17
 */
public class DateBean extends BaseBean
{
    private String priceId;//	Long	价格id	N
    private String depDate;//		String	出发日期	N	yyyy-MM-dd
    private String depDateDesc;//		String	出发日期描述	N	如出发日期是节日第一天，显示节日名称，其他周几
    private float price;//		BigDecimal	特惠价	N
    private int inventoryQuantity;//		int	库存量	N
    private float childBedPrece;//		BigDecimal	儿童占床价	N
    private float childNobedPrece;//		BigDecimal	儿童不占床价	N
    private float flatsharePrice;//		BigDecimal	拼房价格	N

    private boolean isselected; //是否选中；

    /**
     * 格式化depDate字段，以MM-dd格式输出；
     *
     * @return
     */
    public String getMMddFormatDepDate()
    {
        if (!TextUtils.isEmpty(depDate))
        {
            int firstIndex = depDate.indexOf('-', 0);
            if (firstIndex != -1)
            {
                return depDate.substring(firstIndex + 1);
            }
        }
        return null;
    }

    public boolean isselected()
    {
        return isselected;
    }

    public void setIsselected(boolean isselected)
    {
        this.isselected = isselected;
    }

    public String getPriceId()
    {
        return priceId;
    }

    public void setPriceId(String priceId)
    {
        this.priceId = priceId;
    }

    public String getDepDate()
    {
        return super.isStrEmpty(depDate);
    }

    public void setDepDate(String depDate)
    {
        this.depDate = depDate;
    }

    public String getDepDateDesc()
    {
        return super.isStrEmpty(depDateDesc);
    }

    public void setDepDateDesc(String depDateDesc)
    {
        this.depDateDesc = depDateDesc;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public int getInventoryQuantity()
    {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(int inventoryQuantity)
    {
        this.inventoryQuantity = inventoryQuantity;
    }

    public float getChildBedPrece()
    {
        return childBedPrece;
    }

    public void setChildBedPrece(float childBedPrece)
    {
        this.childBedPrece = childBedPrece;
    }

    public float getChildNobedPrece()
    {
        return childNobedPrece;
    }

    public void setChildNobedPrece(float childNobedPrece)
    {
        this.childNobedPrece = childNobedPrece;
    }

    public float getFlatsharePrice()
    {
        return flatsharePrice;
    }

    public void setFlatsharePrice(float flatsharePrice)
    {
        this.flatsharePrice = flatsharePrice;
    }
}
