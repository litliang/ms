package com.yzb.card.king.bean.common;

import com.yzb.card.king.ui.credit.bean.CreditCard;

import java.io.Serializable;

/**
 * 类  名：用戶身份认证类
 * 作  者：Li Yubing
 * 日  期：2017/10/15
 * 描  述：
 */
public class UserAuthenticationBean implements Serializable{

    private boolean paymentMoneyIf = false;
    /**
     * 国籍id
     */
    private long countryId;
    /**
     * 证件类型(1身份证；2护照；3台胞证；6港澳通行证；)
     */
    private int certType;

    private String certTypeName;
    /**
     * 证件号码
     */
    private String certNo;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 出生日期( yyyy-MM-dd 台胞证、港澳通行证必填)
     */
    private String birthday;
    /**
     * 证件失效日期 ( yyyy-MM-dd 台胞证、港澳通行证必填)
     */
    private String certInvalidDate;
    /**
     * 银行卡类型 (1借记卡；2贷记卡；)
     */
    private int cardType;

    private long bankId;

    private String bin;

    private String bankCode;

    private String bankName;

    private String cardNo;

    private String validityPeriod;

    private String cvv2;

    private String mobile;
    /**
     * 收款方卡信息
     */
    private CreditCard data;


    public CreditCard getData()
    {
        return data;
    }

    public void setData(CreditCard data)
    {
        this.data = data;
    }

    public boolean isPaymentMoneyIf()
    {
        return paymentMoneyIf;
    }

    public void setPaymentMoneyIf(boolean paymentMoneyIf)
    {
        this.paymentMoneyIf = paymentMoneyIf;
    }

    public long getCountryId()
    {
        return countryId;
    }

    public void setCountryId(long countryId)
    {
        this.countryId = countryId;
    }

    public int getCertType()
    {
        return certType;
    }

    public void setCertType(int certType)
    {
        this.certType = certType;
    }

    public String getCertNo()
    {
        return certNo;
    }

    public void setCertNo(String certNo)
    {
        this.certNo = certNo;
    }

    public String getRealName()
    {
        return realName;
    }

    public void setRealName(String realName)
    {
        this.realName = realName;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public String getCertInvalidDate()
    {
        return certInvalidDate;
    }

    public void setCertInvalidDate(String certInvalidDate)
    {
        this.certInvalidDate = certInvalidDate;
    }

    public int getCardType()
    {
        return cardType;
    }

    public void setCardType(int cardType)
    {
        this.cardType = cardType;
    }

    public long getBankId()
    {
        return bankId;
    }

    public void setBankId(long bankId)
    {
        this.bankId = bankId;
    }

    public String getCardNo()
    {
        return cardNo;
    }

    public void setCardNo(String cardNo)
    {
        this.cardNo = cardNo;
    }

    public String getValidityPeriod()
    {
        return validityPeriod;
    }

    public void setValidityPeriod(String validityPeriod)
    {
        this.validityPeriod = validityPeriod;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getCertTypeName()
    {
        return certTypeName;
    }

    public void setCertTypeName(String certTypeName)
    {
        this.certTypeName = certTypeName;
    }

    public String getBin()
    {
        return bin;
    }

    public void setBin(String bin)
    {
        this.bin = bin;
    }

    public String getBankCode()
    {
        return bankCode;
    }

    public void setBankCode(String bankCode)
    {
        this.bankCode = bankCode;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getCvv2()
    {
        return cvv2;
    }

    public void setCvv2(String cvv2)
    {
        this.cvv2 = cvv2;
    }
}
