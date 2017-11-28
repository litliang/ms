package com.yzb.card.king.bean.my;

import java.io.Serializable;

/**
 * 功能：口令信息；
 *
 * @author:gengqiyun
 * @date: 2017/1/19
 */
public class KeyInfoBean implements Serializable
{
    private String userName; //发送人；
    private String pic;//发送人头像；
    private String codeNo; //礼品卡订单号；
    private String typeName;//礼品卡类型；
    private int keyType; //
    private String orderId; //红包订单id；

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPic()
    {
        return pic;
    }

    public void setPic(String pic)
    {
        this.pic = pic;
    }

    public String getCodeNo()
    {
        return codeNo;
    }

    public String getOrderId()
    {
        return orderId;
    }

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public void setCodeNo(String codeNo)
    {
        this.codeNo = codeNo;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }


    /**
     * 设置key值类型；
     *
     * @param keyType
     */
    public void setKeyType(int keyType)
    {
        this.keyType = keyType;
    }

    public int getKeyType()
    {
        return keyType;
    }
}
