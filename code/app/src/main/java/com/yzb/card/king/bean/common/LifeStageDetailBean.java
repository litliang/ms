package com.yzb.card.king.bean.common;

import java.io.Serializable;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/28
 * 描  述：
 */
public class LifeStageDetailBean implements Serializable{

    /**
     * 银行id
     */
    private long bankId;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 分期列表
     */
    private List<StageBean> stageList;

    public long getBankId()
    {
        return bankId;
    }

    public void setBankId(long bankId)
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

    public List<StageBean> getStageList()
    {
        return stageList;
    }

    public void setStageList(List<StageBean> stageList)
    {
        this.stageList = stageList;
    }

    public static class  StageBean implements Serializable{
        /**
         * 分期活动id
         */
        private long actId;
        /**
         * 分期数
         */
        private String stage;
        /**
         * 利率
         */
        private float rate;
        /**
         * 服务费
         */
        private float serviceFee;

        public String getStage()
        {
            return stage;
        }

        public void setStage(String stage)
        {
            this.stage = stage;
        }

        public float getRate()
        {
            return rate;
        }

        public void setRate(float rate)
        {
            this.rate = rate;
        }

        public float getServiceFee()
        {
            return serviceFee;
        }

        public void setServiceFee(float serviceFee)
        {
            this.serviceFee = serviceFee;
        }

        public long getActId()
        {
            return actId;
        }

        public void setActId(long actId)
        {
            this.actId = actId;
        }
    }

}
