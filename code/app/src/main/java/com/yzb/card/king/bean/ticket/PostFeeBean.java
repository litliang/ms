package com.yzb.card.king.bean.ticket;

import com.yzb.card.king.ui.app.bean.BaseBean;

/**
 * 功能：邮费bean；
 *
 * @author:gengqiyun
 * @date: 2016/10/14
 */
public class PostFeeBean extends BaseBean
{
    private String logisticsId;   //物流公司id
    private String logisticsName; //物流公司名称
    private float fee; //费用

    public String getLogisticsId()
    {
        return super.isStrEmpty(logisticsId);
    }

    public void setLogisticsId(String logisticsId)
    {
        this.logisticsId = logisticsId;
    }

    public String getLogisticsName()
    {
        return super.isStrEmpty(logisticsName);
    }

    public void setLogisticsName(String logisticsName)
    {
        this.logisticsName = logisticsName;
    }

    public float getFee()
    {
        return fee;
    }

    public void setFee(float fee)
    {
        this.fee = fee;
    }
}
