package com.yzb.card.king.ui.app.bean;

import java.io.Serializable;

/**
 * 类名：会员特权
 * 作者：殷曙光
 * 日期：2016/6/20 9:42
 * 描述：
 */
public class LevelGridItem implements Serializable
{
    Long privilegeId;
    String privilegeLogo;
    String privilegeName;

    public Long getPrivilegeId()
    {
        return privilegeId;
    }

    public void setPrivilegeId(Long privilegeId)
    {
        this.privilegeId = privilegeId;
    }

    public String getPrivilegeLogo()
    {
        return privilegeLogo;
    }

    public void setPrivilegeLogo(String privilegeLogo)
    {
        this.privilegeLogo = privilegeLogo;
    }

    public String getPrivilegeName()
    {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName)
    {
        this.privilegeName = privilegeName;
    }
}
