package com.yzb.card.king.bean.ticket;

import java.io.Serializable;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/10/18
 * 描  述：
 */
public class RefundApplyReqFlightOrderBean implements Serializable {


    private String flightId;

    private String guestId;

    public String getFlightId()
    {
        return flightId;
    }

    public void setFlightId(String flightId)
    {
        this.flightId = flightId;
    }

    public String getGuestId()
    {
        return guestId;
    }

    public void setGuestId(String guestId)
    {
        this.guestId = guestId;
    }

    public String getPasId()
    {
        return pasId;
    }

    public void setPasId(String pasId)
    {
        this.pasId = pasId;
    }

    public String getPsgType()
    {
        return psgType;
    }

    public void setPsgType(String psgType)
    {
        this.psgType = psgType;
    }

    public String getRefundAmount()
    {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount)
    {
        this.refundAmount = refundAmount;
    }

    public String getRefundFee()
    {
        return refundFee;
    }

    public void setRefundFee(String refundFee)
    {
        this.refundFee = refundFee;
    }

    public String getResultCode()
    {
        return resultCode;
    }

    public void setResultCode(String resultCode)
    {
        this.resultCode = resultCode;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    private String pasId;
    private String psgType;

    private String refundAmount;

    private String refundFee;

    private String resultCode;

    private String message;
}
