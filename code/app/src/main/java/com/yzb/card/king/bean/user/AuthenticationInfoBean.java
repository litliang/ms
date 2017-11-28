package com.yzb.card.king.bean.user;

import java.io.Serializable;

/**
 * 类  名：认证用户信息类
 * 作  者：Li Yubing
 * 日  期：2016/12/22
 * 描  述：
 */
public class AuthenticationInfoBean implements Serializable{

    /**
     * 姓名
     */
    private String realName;

    /**
     * 证件类型 (1身份证；2护照；3台胞证；4回乡证；5军人证；6港澳通行证；)
     */
    private String certType;
    /**
     * 证件类型描述
     */
    private String certTypeDesc;
    /**
     * 证件号
     */
    private String certNo;

    public String getRealName()
    {
        return realName;
    }

    public void setRealName(String realName)
    {
        this.realName = realName;
    }

    public String getCertType()
    {
        return certType;
    }

    public void setCertType(String certType)
    {
        this.certType = certType;
    }

    public String getCertTypeDesc()
    {
        return certTypeDesc;
    }

    public void setCertTypeDesc(String certTypeDesc)
    {
        this.certTypeDesc = certTypeDesc;
    }

    public String getCertNo()
    {
        return certNo;
    }

    public void setCertNo(String certNo)
    {
        this.certNo = certNo;
    }
}
