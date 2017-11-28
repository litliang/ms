package com.yzb.card.king.ui.app.bean;

import java.io.Serializable;

/**
 * 功能：发票抬头；
 *
 * @author:gengqiyun
 * @date: 2016/6/21
 */
public class DebitRiseBean implements Serializable
{
    public String invoiceId; //发票id

    public long type; //1企业；2个人；3政府机关行政单位；

    public String content; //发票抬头

    private String identificationNo;//纳税识别号

    private String companyAddr;//公司地址

    private String companyTel;//公司电话

    private String bankName;//开户银行名称

    private String bankAccount;//开户银行账号

    private String status;//状态  1可用；2默认；

    private boolean isDefault;

    public String getIdentificationNo()
    {
        return identificationNo;
    }

    public void setIdentificationNo(String identificationNo)
    {
        this.identificationNo = identificationNo;
    }


    public String getCompanyAddr()
    {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr)
    {
        this.companyAddr = companyAddr;
    }

    public String getCompanyTel()
    {
        return companyTel;
    }

    public void setCompanyTel(String companyTel)
    {
        this.companyTel = companyTel;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getBankAccount()
    {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount)
    {
        this.bankAccount = bankAccount;
    }

    public String getInvoiceId()
    {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId)
    {
        this.invoiceId = invoiceId;
    }

    public long getType()
    {
        return type;
    }

    public void setType(long type)
    {
        this.type = type;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;

        if("2".equals(status)){
            isDefault = true;
        }else{
            isDefault = false;
        }
    }

    public boolean isDefault()
    {
        return isDefault;
    }
}
