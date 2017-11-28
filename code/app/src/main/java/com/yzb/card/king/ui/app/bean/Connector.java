package com.yzb.card.king.ui.app.bean;

import java.io.Serializable;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/22 14:38
 * 描述：
 */
public class Connector implements Serializable
{
    public String contactsId; //联系人id；
    public String nickName; //联系人姓名；
    public String mobile; //电话；
    public String email; //邮箱；
    public String relationship = "";
    public String type = "";
    public boolean isDefault = false;

    public Connector(String id, String nickName, String mobile, String relationship, boolean isDefault)
    {
        this.contactsId = id;
        this.nickName = nickName;
        this.mobile = mobile;
        this.relationship = relationship;
        this.isDefault = isDefault;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getId()
    {
        return contactsId;
    }

    public void setId(String id)
    {
        this.contactsId = id;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getRelationship()
    {
        return relationship;
    }

    public void setRelationship(String relationship)
    {
        this.relationship = relationship;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public boolean isDefault()
    {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault)
    {
        this.isDefault = isDefault;
    }

    public Connector()
    {

    }

}
