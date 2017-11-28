package com.yzb.card.king.bean.hotel;

import java.io.Serializable;

/**
 * 类  名：酒店房间类
 * 作  者：Li Yubing
 * 日  期：2016/7/27
 * 描  述：查询房型套餐（QueryRoomsPolicys）
 */
public class RoomPolicy extends BasePolicy implements Serializable
{
    private String priceId;
    private RoomBean rooms;
    private String guestType;
    private int residualQuantity;
    private String breakfastDesc;

    public String getPriceId()
    {
        return priceId;
    }

    public void setPriceId(String priceId)
    {
        this.priceId = priceId;
    }

    public RoomBean getRooms()
    {
        return rooms;
    }

    public void setRooms(RoomBean rooms)
    {
        this.rooms = rooms;
    }

    public String getGuestType()
    {
        return guestType;
    }

    public void setGuestType(String guestType)
    {
        this.guestType = guestType;
    }

    public int getResidualQuantity()
    {
        return residualQuantity;
    }

    public void setResidualQuantity(int residualQuantity)
    {
        this.residualQuantity = residualQuantity;
    }

    public String getBreakfastDesc()
    {
        return breakfastDesc;
    }

    public void setBreakfastDesc(String breakfastDesc)
    {
        this.breakfastDesc = breakfastDesc;
    }
}
