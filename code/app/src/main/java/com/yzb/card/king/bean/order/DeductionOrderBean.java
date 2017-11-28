package com.yzb.card.king.bean.order;

import java.io.Serializable;

/**
 * 类  名：抵扣订单信息
 * 作  者：Li Yubing
 * 日  期：2017/8/25
 * 描  述：
 */
public class DeductionOrderBean implements Serializable{
    /**
     * 抵扣项名称
     */
    private String disName;
    /**
     * 抵扣金额
     */
    private float disAmount;

    public String getDisName()
    {
        return disName;
    }

    public void setDisName(String disName)
    {
        this.disName = disName;
    }

    public float getDisAmount()
    {
        return disAmount;
    }

    public void setDisAmount(float disAmount)
    {
        this.disAmount = disAmount;
    }
}
