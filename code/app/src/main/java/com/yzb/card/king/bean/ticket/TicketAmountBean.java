package com.yzb.card.king.bean.ticket;

/**
 * 功能：票价bean；
 *
 * @author:gengqiyun
 * @date: 2016/11/29
 * <p/>
 * forAmount	chaAmount	infAmount
 * 成人机票价格	儿童价格
 * fueltax	fueltaxChd	fueltaxBad
 * 成人税费	儿童税费	婴儿税费
 */
public class TicketAmountBean
{
    float forAmount; //成人机票价格
    float chaAmount; //儿童机票价格
    float infAmount; //婴儿机票价格

    float fueltax; //成人税费
    float fueltaxChd; //儿童税费
    float fueltaxBad; //婴儿税费

    String flightType; //航班类型（单程：OW；往返：RT；多段：MT，平台组合RT_P）

    public String getFlightType()
    {
        return flightType;
    }

    public void setFlightType(String flightType)
    {
        this.flightType = flightType;
    }

    public float getForAmount()
    {
        return forAmount;
    }

    public void setForAmount(float forAmount)
    {
        this.forAmount = forAmount;
    }

    public float getChaAmount()
    {
        return chaAmount;
    }

    public void setChaAmount(float chaAmount)
    {
        this.chaAmount = chaAmount;
    }

    public float getInfAmount()
    {
        return infAmount;
    }

    public void setInfAmount(float infAmount)
    {
        this.infAmount = infAmount;
    }

    public float getFueltax()
    {
        return fueltax;
    }

    public void setFueltax(float fueltax)
    {
        this.fueltax = fueltax;
    }

    public float getFueltaxChd()
    {
        return fueltaxChd;
    }

    public void setFueltaxChd(float fueltaxChd)
    {
        this.fueltaxChd = fueltaxChd;
    }

    public float getFueltaxBad()
    {
        return fueltaxBad;
    }

    public void setFueltaxBad(float fueltaxBad)
    {
        this.fueltaxBad = fueltaxBad;
    }
}
