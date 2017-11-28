package com.yzb.card.king.ui.discount.bean;

import java.io.Serializable;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/7/20 17:00
 * 描述：
 */
public class Insurance implements Serializable
{
    public String insuranceTypeId; //险种id
    public String shortName;//险种简称
    public String insuranceSummary;//险种说明
    public String amountType;//金额类型  1固定金额、2总额比例
    public float amountContent;//金额内容
    public String insuranceUrl;//详情url
    public boolean isSelected;

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }

    public String getInsuranceTypeId()
    {
        return insuranceTypeId;
    }

    public void setInsuranceTypeId(String insuranceTypeId)
    {
        this.insuranceTypeId = insuranceTypeId;
    }

    public String getShortName()
    {
        return shortName;
    }

    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    public String getInsuranceSummary()
    {
        return insuranceSummary;
    }

    public void setInsuranceSummary(String insuranceSummary)
    {
        this.insuranceSummary = insuranceSummary;
    }

    public String getAmountType()
    {
        return amountType;
    }

    public void setAmountType(String amountType)
    {
        this.amountType = amountType;
    }

    public float getAmountContent()
    {
        return amountContent;
    }

    /**
     * 获取实际金额；
     *
     * @param totalAmount 订单总金额（带税费，需乘以人数）
     */
    public String getActuAmount(float totalAmount)
    {
        //金额类型  1固定金额、2总额比例
        if ("1".equals(getAmountType()))
        {
            return getAmountContent() + "";
        } else
        {
            return (totalAmount * getAmountContent() / 100f) + "";
        }
    }

    public void setAmountContent(float amountContent)
    {
        this.amountContent = amountContent;
    }

    public String getInsuranceUrl()
    {
        return insuranceUrl;
    }

    public void setInsuranceUrl(String insuranceUrl)
    {
        this.insuranceUrl = insuranceUrl;
    }
}
