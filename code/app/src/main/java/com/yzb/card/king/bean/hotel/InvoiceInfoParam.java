package com.yzb.card.king.bean.hotel;

import java.io.Serializable;

/**
 * 类  名：发票信息
 * 作  者：Li Yubing
 * 日  期：2017/8/20
 * 描  述：
 */
public class InvoiceInfoParam implements Serializable{
    /**
     * 开票金额
     */
    private  float invoiceAmount;
    /**
     * 申请方 1企业；2个人；3政府机关行政单位；
     */
    private String applicant;
    /**
     * 发票类型 1普通发票；2专用发票；
     */
    private String type;
    /**
     * 发票开具项目(1房费、2代订房费、3住宿费、4代订住宿费、5旅游费、6差旅费、7团费、8考察费、9服务费、10代办签证费)
     */
    private String invoiceItem;
    /**
     * 发票抬头
     */
    private String title;
    /**
     * 纳税人识别号
     */
    private String identificationNo;
    /**
     * 公司注册地址
     */
    private String companyAddr;
    /**
     * 公司注册电话
     */
    private String companyTel;
    /**
     * 开户银行名称
     */
    private String bankName;
    /**
     * 开户银行账号
     */
    private String bankAccount;
    /**
     * 发票形式(1电子发票；2纸质发票；)
     */
    private String invoiceForm;
    /**
     * 收票邮箱
     */
    private String email;
    /**
     * 配送金额
     */
    private float expressAmount;
    /**
     * 收货人名称
     */
    private String contacts;
    /**
     * 收货人电话
     */
    private String phone;
    /**
     * 收货区县id
     */
    private  int districtId;
    /**
     * 收货地址
     */
    private String address;
    /**
     * 发票备注
     */
    private String remark;

//    private  int selectedApplicantIndex = 1;
//
//    private int selectedInvoicIndex = 0;
//
//    private int selectedCInvoicIndex = 0;


    public float getInvoiceAmount()
    {
        return invoiceAmount;
    }

    public void setInvoiceAmount(float invoiceAmount)
    {
        this.invoiceAmount = invoiceAmount;
    }

    public String getApplicant()
    {
        return applicant;
    }

    public void setApplicant(String applicant)
    {
        this.applicant = applicant;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getInvoiceItem()
    {
        return invoiceItem;
    }

    public void setInvoiceItem(String invoiceItem)
    {
        this.invoiceItem = invoiceItem;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

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

    public String getInvoiceForm()
    {
        return invoiceForm;
    }

    public void setInvoiceForm(String invoiceForm)
    {
        this.invoiceForm = invoiceForm;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public float getExpressAmount()
    {
        return expressAmount;
    }

    public void setExpressAmount(float expressAmount)
    {
        this.expressAmount = expressAmount;
    }

    public String getContacts()
    {
        return contacts;
    }

    public void setContacts(String contacts)
    {
        this.contacts = contacts;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public int getDistrictId()
    {
        return districtId;
    }

    public void setDistrictId(int districtId)
    {
        this.districtId = districtId;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

//    public int getSelectedApplicantIndex()
//    {
//        return selectedApplicantIndex;
//    }
//
//    public void setSelectedApplicantIndex(int selectedApplicantIndex)
//    {
//        this.selectedApplicantIndex = selectedApplicantIndex;
//    }
//
//    public int getSelectedInvoicIndex()
//    {
//        return selectedInvoicIndex;
//    }
//
//    public void setSelectedInvoicIndex(int selectedInvoicIndex)
//    {
//        this.selectedInvoicIndex = selectedInvoicIndex;
//    }
//
//    public int getSelectedCInvoicIndex()
//    {
//        return selectedCInvoicIndex;
//    }
//
//    public void setSelectedCInvoicIndex(int selectedCInvoicIndex)
//    {
//        this.selectedCInvoicIndex = selectedCInvoicIndex;
//    }
}
