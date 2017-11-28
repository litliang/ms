package com.yzb.card.king.bean.order;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/25
 * 描  述：
 */
public class OrderInvoiceInfoBean implements Serializable{
    /**
     * 发票类型(1电子发票；2纸质发票；)
     */
    private String invoiceType;
    /**
     * 发票抬头
     */
    private String invoiceTitile;
    /**
     * 发票邮寄邮箱(电子发票时有效)
     */
    private String invoiceExpressEamil;
    /**
     * 发票物流单号(纸质发票时有效)
     */
    private String invoiceExpressNo;
    /**
     * 发票邮寄地址(纸质发票时有效)
     */
    private String invoiceExpressAddr;
    /**
     * 配送金额
     */
    private float expressAmount;

    public String getInvoiceType()
    {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType)
    {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceTitile()
    {
        return invoiceTitile;
    }

    public void setInvoiceTitile(String invoiceTitile)
    {
        this.invoiceTitile = invoiceTitile;
    }

    public String getInvoiceExpressEamil()
    {
        return invoiceExpressEamil;
    }

    public void setInvoiceExpressEamil(String invoiceExpressEamil)
    {
        this.invoiceExpressEamil = invoiceExpressEamil;
    }

    public String getInvoiceExpressNo()
    {
        return invoiceExpressNo;
    }

    public void setInvoiceExpressNo(String invoiceExpressNo)
    {
        this.invoiceExpressNo = invoiceExpressNo;
    }

    public String getInvoiceExpressAddr()
    {
        return invoiceExpressAddr;
    }

    public void setInvoiceExpressAddr(String invoiceExpressAddr)
    {
        this.invoiceExpressAddr = invoiceExpressAddr;
    }

    public float getExpressAmount()
    {
        return expressAmount;
    }

    public void setExpressAmount(float expressAmount)
    {
        this.expressAmount = expressAmount;
    }
}
