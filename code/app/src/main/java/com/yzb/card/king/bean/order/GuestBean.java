package com.yzb.card.king.bean.order;

import java.io.Serializable;

/**
 * 乘机人
 * Created by Timmy on 16/7/26.
 */
public class GuestBean implements Serializable {

    private String carriage;

    private String guestIDCard;

    private String guestName;

    private String seatLevle;

    private String seatNo;

    private String ticketCheck;
    /**
     * 航班id
     */
    private String fightId;

    /**
     * 乘机人id
     */
    private String guestId;

    /**
     * 用户选择的舱位
     */
    private String cabinCode;

    /**
     * 航班日期
     *
     */
    private  String depDate;


    public String getGuestId()
    {
        return guestId;
    }

    public void setGuestId(String guestId)
    {
        this.guestId = guestId;
    }

    public String getCabinCode()
    {
        return cabinCode;
    }

    public void setCabinCode(String cabinCode)
    {
        this.cabinCode = cabinCode;
    }

    public String getDepDate()
    {
        return depDate;
    }

    public void setDepDate(String depDate)
    {
        this.depDate = depDate;
    }

    public String getFightId()
    {
        return fightId;
    }

    public void setFightId(String fightId)
    {
        this.fightId = fightId;
    }

    public String getCarriage() {
        return carriage;
    }

    public void setCarriage(String carriage) {
        this.carriage = carriage;
    }

    public String getGuestIDCard() {
        return guestIDCard;
    }

    public void setGuestIDCard(String guestIDCard) {
        this.guestIDCard = guestIDCard;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getSeatLevle() {
        return seatLevle;
    }

    public void setSeatLevle(String seatLevle) {
        this.seatLevle = seatLevle;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getTicketCheck() {
        return ticketCheck;
    }

    public void setTicketCheck(String ticketCheck) {
        this.ticketCheck = ticketCheck;
    }
}
