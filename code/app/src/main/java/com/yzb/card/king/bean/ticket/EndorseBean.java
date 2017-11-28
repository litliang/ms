package com.yzb.card.king.bean.ticket;

import java.io.Serializable;

/**
 * 类  名：可改签的机票订单和乘机人
 * 作  者：Li Yubing
 * 日  期：2016/10/31
 * 描  述：
 */
public class EndorseBean implements Serializable{

    private  final String SPLIT = ",";

    /**
     * 平台小订单号（航空公司大订单号）(多个使用英文逗号分割)
     */
    private String itemOrderIds ;

    /**
     * 人id(多个使用英文逗号分割)
     */
    private String  guestIds;


    private String[] itemOrderArray;

    private String[] guestIdArray;


    public String getItemOrderIds()
    {
        return itemOrderIds;
    }

    public void setItemOrderIds(String itemOrderIds)
    {
        this.itemOrderIds = itemOrderIds;

        if (itemOrderIds.indexOf(SPLIT) != 1) {
            itemOrderArray = itemOrderIds.split(SPLIT);
        } else {
            itemOrderArray = new String[]{};
            itemOrderArray[0] = itemOrderIds;
        }

    }

    public String getGuestIds()
    {
        return guestIds;
    }

    public void setGuestIds(String guestIds)
    {
        this.guestIds = guestIds;

        if (guestIds.indexOf(SPLIT) != 1) {
            guestIdArray = guestIds.split(SPLIT);
        } else {
            guestIdArray = new String[]{};
            guestIdArray[0] = guestIds;
        }

    }

    public String[] getItemOrderArray()
    {
        return itemOrderArray;
    }

    public void setItemOrderArray(String[] itemOrderArray)
    {
        this.itemOrderArray = itemOrderArray;
    }

    public String[] getGuestIdArray()
    {
        return guestIdArray;
    }

    public void setGuestIdArray(String[] guestIdArray)
    {
        this.guestIdArray = guestIdArray;
    }
}
