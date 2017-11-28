package com.yzb.card.king.bean.common;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/30
 * 描  述：
 */
public class ActivityDeductionStyleParam {
    /**
     *  退款方式   1平台钱包；2原路退回
     */
    private String refundType ="1";
    /**
     * 付款方式 (1钱包余额；2信用卡；3储蓄卡；4生活分期)
     */
    private String payType;
    /**
     * 付款方式明细id(如信用卡id、储蓄卡id等)
     */
    private String payDetailId = null;
    /**
     * 银行优惠id
     */
    private long bankActId =-1;
    /**
     * 银行分期id
     */
    private long bankStageId = -1;

    public String getRefundType()
    {
        return refundType;
    }

    public void setRefundType(String refundType)
    {
        this.refundType = refundType;
    }

    public String getPayType()
    {
        return payType;
    }

    public void setPayType(String payType)
    {
        this.payType = payType;
    }

    public String getPayDetailId()
    {
        return payDetailId;
    }

    public void setPayDetailId(String payDetailId)
    {
        this.payDetailId = payDetailId;
    }

    public long getBankActId()
    {
        return bankActId;
    }

    public void setBankActId(long bankActId)
    {
        this.bankActId = bankActId;
    }

    public long getBankStageId()
    {
        return bankStageId;
    }

    public void setBankStageId(long bankStageId)
    {
        this.bankStageId = bankStageId;
    }
}
