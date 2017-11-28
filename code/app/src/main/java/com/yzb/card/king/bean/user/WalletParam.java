package com.yzb.card.king.bean.user;

/**
 * 功能：钱包参数；
 *
 * @author:gengqiyun
 * @date: 2017/1/4
 */
public class WalletParam
{
    public String mobile; //手机号；  Y
    public String accountType; //账户类型；  Y
    public String customerBankId;
    public String accountId;
    public int bankId; //银行卡的id；
    private String cardType;//银行卡类型 1、借记卡 2信用卡
    private String notifyUrl;//支付结果信息通知url；  Y

    private String merchantNo;//商户号  Y
    private String sign;//签名          Y
    private String orderNo;//订单号；   Y
    private String orderTime;//订单创建时的时间  格式为yyyyMMddHHmmss   Y
    private float amount;//订单金额；  必须要是0.00格式的数据；  0.01至100000000.00   Y
    private String leftTime;//超时时间  1-2两位的整数  Y
    private String batchNo;//批次号
    private String commodity;//商品描述
    private String antiTimestamp;//防钓鱼时间戳 Y
    private String clientIp;//客户端IP  Y
    private String bankNo;//银行卡号
    private String validCode;//验证码
    private String charset;//字符集，默认UTF-8
    private String signType;//签名方式，默认 SHA1withRSA


    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getAccountType()
    {
        return accountType;
    }

    public void setAccountType(String accountType)
    {
        this.accountType = accountType;
    }

    public String getCustomerBankId()
    {
        return customerBankId;
    }

    public void setCustomerBankId(String customerBankId)
    {
        this.customerBankId = customerBankId;
    }

    public String getAccountId()
    {
        return accountId;
    }

    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }

    public int getBankId()
    {
        return bankId;
    }

    public void setBankId(int bankId)
    {
        this.bankId = bankId;
    }

    public String getCardType()
    {
        return cardType;
    }

    public void setCardType(String cardType)
    {
        this.cardType = cardType;
    }

    public String getNotifyUrl()
    {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl)
    {
        this.notifyUrl = notifyUrl;
    }

    public String getMerchantNo()
    {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo)
    {
        this.merchantNo = merchantNo;
    }

    public String getSign()
    {
        return sign;
    }

    public void setSign(String sign)
    {
        this.sign = sign;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getOrderTime()
    {
        return orderTime;
    }

    public void setOrderTime(String orderTime)
    {
        this.orderTime = orderTime;
    }

    public float getAmount()
    {
        return amount;
    }

    public void setAmount(float amount)
    {
        this.amount = amount;
    }

    public String getLeftTime()
    {
        return leftTime;
    }

    public void setLeftTime(String leftTime)
    {
        this.leftTime = leftTime;
    }

    public String getBatchNo()
    {
        return batchNo;
    }

    public void setBatchNo(String batchNo)
    {
        this.batchNo = batchNo;
    }

    public String getCommodity()
    {
        return commodity;
    }

    public void setCommodity(String commodity)
    {
        this.commodity = commodity;
    }

    public String getAntiTimestamp()
    {
        return antiTimestamp;
    }

    public void setAntiTimestamp(String antiTimestamp)
    {
        this.antiTimestamp = antiTimestamp;
    }

    public String getClientIp()
    {
        return clientIp;
    }

    public void setClientIp(String clientIp)
    {
        this.clientIp = clientIp;
    }

    public String getBankNo()
    {
        return bankNo;
    }

    public void setBankNo(String bankNo)
    {
        this.bankNo = bankNo;
    }

    public String getValidCode()
    {
        return validCode;
    }

    public void setValidCode(String validCode)
    {
        this.validCode = validCode;
    }

    public String getCharset()
    {
        return charset;
    }

    public void setCharset(String charset)
    {
        this.charset = charset;
    }

    public String getSignType()
    {
        return signType;
    }

    public void setSignType(String signType)
    {
        this.signType = signType;
    }
}
