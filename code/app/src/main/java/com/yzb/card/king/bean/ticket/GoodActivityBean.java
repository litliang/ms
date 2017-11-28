package com.yzb.card.king.bean.ticket;

import com.yzb.card.king.ui.app.bean.BaseBean;

/**
 * 功能：商品活动；
 *
 * @author:gengqiyun
 * @date: 2016/10/16
 */
public class GoodActivityBean extends BaseBean
{
    private String actId; //优惠券id
    private String actName; //优惠券名称；
    private String actDays; //活动日限定(多个使用英文逗号分割）；

    private int bankId; //银行id；
    private String bankName; //银行名称；
    private String bankLogoCode; //银行logo；
    private String detail; //银行打折优惠；

    private String flightId;
    private String bankLogo;

    private String recieveStatus; //领取状态（1已领取；0未领取）
    private String actStatus; //优惠券领取状态（1已领取；0未领取）  接口字段不一致问题；
    private float fullAmount; //满额
    private float cutAmount; //减额
    private int totalQuantity; //发放数量；
    private int receiveQuantity; //领取数量；

    private String startTime; //活动开始时间  yyyy-MM-dd
    private String endTime; //活动结束时间  yyyy-MM-dd
    private float amount; //红包金额
    private float amountUp; //金额上限
    private float amountDown; //金额下限
    private int rate; //折扣率  0--100之间；使用时 需要除以100；

    private String itemId; //满减项id
    private int scope; //应用范围（1满减;2每满减;3阶梯减;）
    private int category; //优惠类型 1折扣；2元
    private String content; //优惠内容
    /**
     * limitAmount==0;优惠券无条件使用，
     * limitAmount>订单金额；优惠券不能使用；
     */
    private int grantCondition; //发放条件 1不限；2满额
    private int grantAmount; //领取金额限制；
    private String platformType;//1平台商家活动；2平台活动；3商家活动；4银行活动

    private String shareStatus;//商家或平台优惠券共存情况；共用状态（1可共用；0不可共用）

    private String orderId;//红包类型
    private String bonusType;//红包类型
    private String shopLimit;//商家限制

    public String getOrderId()
    {
        return orderId;
    }

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public int getGrantCondition()
    {
        return grantCondition;
    }

    public void setGrantCondition(int grantCondition)
    {
        this.grantCondition = grantCondition;
    }

    public String getActId()
    {
        return actId;
    }

    public void setActId(String actId)
    {
        this.actId = actId;
    }

    public String getActName()
    {
        return actName;
    }

    public void setActName(String actName)
    {
        this.actName = actName;
    }

    public String getActDays()
    {
        return actDays;
    }

    public void setActDays(String actDays)
    {
        this.actDays = actDays;
    }

    public int getBankId()
    {
        return bankId;
    }

    public void setBankId(int bankId)
    {
        this.bankId = bankId;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getBankLogoCode()
    {
        return bankLogoCode;
    }

    public void setBankLogoCode(String bankLogoCode)
    {
        this.bankLogoCode = bankLogoCode;
    }

    public String getDetail()
    {
        return detail;
    }

    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    public String getFlightId()
    {
        return flightId;
    }

    public void setFlightId(String flightId)
    {
        this.flightId = flightId;
    }

    public String getBankLogo()
    {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo)
    {
        this.bankLogo = bankLogo;
    }

    public String getRecieveStatus()
    {
        return recieveStatus;
    }

    public void setRecieveStatus(String recieveStatus)
    {
        this.recieveStatus = recieveStatus;
    }

    public String getActStatus()
    {
        return actStatus;
    }

    public void setActStatus(String actStatus)
    {
        this.actStatus = actStatus;
    }

    public float getFullAmount()
    {
        return fullAmount;
    }

    public void setFullAmount(float fullAmount)
    {
        this.fullAmount = fullAmount;
    }

    public float getCutAmount()
    {
        return cutAmount;
    }

    public void setCutAmount(float cutAmount)
    {
        this.cutAmount = cutAmount;
    }

    public int getTotalQuantity()
    {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity)
    {
        this.totalQuantity = totalQuantity;
    }

    public int getReceiveQuantity()
    {
        return receiveQuantity;
    }

    public void setReceiveQuantity(int receiveQuantity)
    {
        this.receiveQuantity = receiveQuantity;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public float getAmount()
    {
        return amount;
    }

    public void setAmount(float amount)
    {
        this.amount = amount;
    }

    public float getAmountUp()
    {
        return amountUp;
    }

    public void setAmountUp(float amountUp)
    {
        this.amountUp = amountUp;
    }

    public float getAmountDown()
    {
        return amountDown;
    }

    public void setAmountDown(float amountDown)
    {
        this.amountDown = amountDown;
    }

    public int getRate()
    {
        return rate;
    }

    public void setRate(int rate)
    {
        this.rate = rate;
    }

    public String getItemId()
    {
        return itemId;
    }

    public void setItemId(String itemId)
    {
        this.itemId = itemId;
    }

    public int getScope()
    {
        return scope;
    }

    public void setScope(int scope)
    {
        this.scope = scope;
    }

    public int getCategory()
    {
        return category;
    }

    public void setCategory(int category)
    {
        this.category = category;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public int getGrantAmount()
    {
        return grantAmount;
    }

    public void setGrantAmount(int grantAmount)
    {
        this.grantAmount = grantAmount;
    }

    public String getPlatformType()
    {
        return platformType;
    }

    public void setPlatformType(String platformType)
    {
        this.platformType = platformType;
    }

    public String getShareStatus()
    {
        return shareStatus;
    }

    public void setShareStatus(String shareStatus)
    {
        this.shareStatus = shareStatus;
    }

    public String getBonusType()
    {
        return bonusType;
    }

    public void setBonusType(String bonusType)
    {
        this.bonusType = bonusType;
    }

    public String getShopLimit()
    {
        return shopLimit;
    }

    public void setShopLimit(String shopLimit)
    {
        this.shopLimit = shopLimit;
    }
}
