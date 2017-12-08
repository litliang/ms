package com.yzb.card.king.bean.common;

import com.yzb.card.king.bean.BankActivityInfoBean;

import java.io.Serializable;
import java.util.List;

/**
 * 类  名：支付方式
 * 作  者：Li Yubing
 * 日  期：2017/8/30
 * 描  述：储蓄卡、信用卡、钱包（包括银行优惠、银行分期）
 */
public class PaymethodAndBankPreStageBean implements Serializable{
    /**
     * 账户类型
     */
    private String accountType = "0";//支付类型 0银行卡快捷支付 ,1(钱包余额)，2（信用卡），3（储蓄卡），4（礼品卡余额），5（红包余额）支付账号类型 9999:支付宝;10000:添加新支付；9998:信用支付;9997:支付宝

    private  int accountLogo;

    private String payMsg;

    private String bankLogo;
    /**
     * 付款金额
     */
    private  double paymentMoney;
    /**
     * 用户id
     */
    private String customerId;
    /**
     * 银行id
     */
    private long bankId;
    /**
     * 预留手机号
     */
    private long reservedMobile;
    /**
     * 卡号
     */
    private String fullNo;
    /**
     * 尾号
     */
    private String sortNo;
    /**
     * 卡类型(1储蓄卡 2信用卡)
     */
    private String cardType;
    /**
     * 卡类型名称
     */
    private String typeName;
    /**
     * 创建时间，时间戳
     */
    private String createTime;
    /**
     * 状态
     */
    private String status;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 默认状态 (1是默认)
     */
    private String defaultStatus;
    private boolean selected =false;
    //信用卡的银行活动和分期活动同时存在，此字段用来标记选择项目--- 0 表示 没有选择；1表示选择银行活动；2标识选择分期活动
    private int activityType = 0;
    /**
     * 持卡人
     */
    private String name;
    /**
     * 持卡人证件类型
     */
    private String certType;
    /**
     * 持卡人证件号
     */
    private String certNo;
    /**
     * 有效期
     */
    private String  validThru;
    /**
     * 卡背面三位
     */
    private String cvv2;
    /**
     * 银行卡关联码
     */
    private String sortCode;
    /**
     * 单笔支付限制
     */
    private String limitSingle;
    /**
     * 单日支付限制
     */
    private String limitDay;
    /**
     * 每月支付限制
     */
    private String limitMonth;
    /**
     * 银行code
     */
    private String bankCode;
    /**
     * 分期列表
     */
    private List<LifeStageDetailBean.StageBean> stageList;
    /**
     * 银行活动信息
     */
    private BankActivityInfoBean bankActInfo;

    public void setAccountLogo(int accountLogo)
    {
        this.accountLogo = accountLogo;
    }

    public String getPayMsg()
    {
        return payMsg;
    }

    public void setPayMsg(String payMsg)
    {
        this.payMsg = payMsg;
    }

    public int getAccountLogo()
    {
        return accountLogo;
    }

    public String getAccountType()
    {
        return accountType;
    }

    public void setAccountType(String accountType)
    {
        this.accountType = accountType;
    }

    public double getPaymentMoney()
    {
        return paymentMoney;
    }

    public void setPaymentMoney(double paymentMoney)
    {
        this.paymentMoney = paymentMoney;
    }

    public String getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }

    public long getBankId()
    {
        return bankId;
    }

    public void setBankId(long bankId)
    {
        this.bankId = bankId;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public long getReservedMobile()
    {
        return reservedMobile;
    }

    public void setReservedMobile(long reservedMobile)
    {
        this.reservedMobile = reservedMobile;
    }

    public String getFullNo()
    {
        return fullNo;
    }

    public void setFullNo(String fullNo)
    {
        this.fullNo = fullNo;
    }

    public String getSortNo()
    {
        return sortNo;
    }

    public void setSortNo(String sortNo)
    {
        this.sortNo = sortNo;
    }

    public String getCardType()
    {
        return cardType;
    }

    public void setCardType(String cardType)
    {
        this.cardType = cardType;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getDefaultStatus()
    {
        return defaultStatus;
    }

    public void setDefaultStatus(String defaultStatus)
    {
        this.defaultStatus = defaultStatus;

//        if("1".equals(defaultStatus)){
//
//            selected = true;
//        }else {
//            selected = false;
//        }
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCertType()
    {
        return certType;
    }

    public void setCertType(String certType)
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

    public String getValidThru()
    {
        return validThru;
    }

    public void setValidThru(String validThru)
    {
        this.validThru = validThru;
    }

    public String getCvv2()
    {
        return cvv2;
    }

    public void setCvv2(String cvv2)
    {
        this.cvv2 = cvv2;
    }

    public String getSortCode()
    {
        return sortCode;
    }

    public void setSortCode(String sortCode)
    {
        this.sortCode = sortCode;
    }

    public String getLimitSingle()
    {
        return limitSingle;
    }

    public void setLimitSingle(String limitSingle)
    {
        this.limitSingle = limitSingle;
    }

    public String getLimitDay()
    {
        return limitDay;
    }

    public void setLimitDay(String limitDay)
    {
        this.limitDay = limitDay;
    }

    public String getLimitMonth()
    {
        return limitMonth;
    }

    public void setLimitMonth(String limitMonth)
    {
        this.limitMonth = limitMonth;
    }

    public String getBankCode()
    {
        return bankCode;
    }

    public void setBankCode(String bankCode)
    {
        this.bankCode = bankCode;
    }

    public List<LifeStageDetailBean.StageBean> getStageList()
    {
        return stageList;
    }

    public void setStageList(List<LifeStageDetailBean.StageBean> stageList)
    {
        this.stageList = stageList;
    }

    public String getBankLogo()
    {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo)
    {
        this.bankLogo = bankLogo;
    }

    public BankActivityInfoBean getBankActInfo()
    {
        return bankActInfo;
    }

    public void setBankActInfo(BankActivityInfoBean bankActInfo)
    {
        this.bankActInfo = bankActInfo;
    }

    /**
     * 是选择生活分期
     */
    private  LifeStageDetailBean.StageBean selectedBean = null;

    public LifeStageDetailBean.StageBean getSelectedBean()
    {
        return selectedBean;
    }

    public void setSelectedBean(LifeStageDetailBean.StageBean selectedBean)
    {
        this.selectedBean = selectedBean;
    }

    /**
     * 信用卡支付时，银行优惠和分期活动同时存在，检测是否选择了银行的分期活动
     * @return
     */
    public boolean ifSelectedBankStage(){


        return "2".equals( getAccountType()) && 2== getActivityType();
    }
}
