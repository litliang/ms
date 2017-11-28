package com.yzb.card.king.bean.ticket;

import com.yzb.card.king.ui.app.bean.BaseBean;

/**
 * 功能：代理商红包；
 *
 * @author:gengqiyun
 * @date: 2016/10/13
 */
public class TicketHbBean extends BaseBean
{
    private String actId; //红包id
    private long startTime; //领取开始时间
    private long endTime; //领取结束时间
    private float amount; //红包金额
    private String recieveStatus; //优惠券领取状态（1已领取；0未领取）
    private String limitAmount; //领取金额限制

    public String getActId()
    {
        return super.isStrEmpty(actId);
    }

    public void setActId(String actId)
    {
        this.actId = actId;
    }

    public long getStartTime()
    {
        return startTime;
    }

    public void setStartTime(long startTime)
    {
        this.startTime = startTime;
    }

    public long getEndTime()
    {
        return endTime;
    }

    public void setEndTime(long endTime)
    {
        this.endTime = endTime;
    }

    public float getAmount()
    {
        return amount;
    }

    public void setAmount(float amount)
    {
        this.amount = amount;
    }

    public String getRecieveStatus()
    {
        return super.isStrEmpty(recieveStatus);
    }

    public void setRecieveStatus(String recieveStatus)
    {
        this.recieveStatus = recieveStatus;
    }

    public String getLimitAmount()
    {
        return super.isStrEmpty(limitAmount);
    }

    public void setLimitAmount(String limitAmount)
    {
        this.limitAmount = limitAmount;
    }
}
