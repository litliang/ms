package com.yzb.card.king.ui.gift.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 类 名： 礼品卡领取记录
 * 作 者： gaolei
 * 日 期：2016年12月20日
 * 描 述：
 */

public class GiftCardDetailBean implements Serializable {

    /**
     * payAmount : 20106
     * detailList : [{"tradeTime":"2016-12-27 14:12:00","tradeAmount":100,"paymentsStatus":"2"},{"tradeTime":"2016-12-27 14:12:00","tradeAmount":1,"paymentsStatus":"2"},{"tradeTime":"2016-12-27 14:12:00","tradeAmount":1,"paymentsStatus":"2"},{"tradeTime":"2016-12-27 14:12:00","tradeAmount":2,"paymentsStatus":"2"},{"tradeTime":"2016-12-27 14:12:00","tradeAmount":2,"paymentsStatus":"2"},{"tradeTime":"2016-12-27 14:12:00","tradeAmount":20000,"paymentsStatus":"2"}]
     * incomeAmount : 0
     * monthDesc : 本月
     */

    private BigDecimal payAmount;
    private BigDecimal incomeAmount;
    private String monthDesc;
    private List<DetailListBean> detailList;

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(BigDecimal incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public String getMonthDesc() {
        return monthDesc;
    }

    public void setMonthDesc(String monthDesc) {
        this.monthDesc = monthDesc;
    }

    public List<DetailListBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailListBean> detailList) {
        this.detailList = detailList;
    }

    public static class DetailListBean {
        /**
         * tradeTime : 2016-12-27 14:12:00
         * tradeAmount : 100
         * paymentsStatus : 2
         */

        private String tradeTime;
        private BigDecimal tradeAmount;
        private String paymentsStatus;
        private String tradeTypeDesc;
        private String tradeDesc;

        public String getTradeDesc() {
            return tradeDesc;
        }

        public void setTradeDesc(String tradeDesc) {
            this.tradeDesc = tradeDesc;
        }

        public String getTradeTypeDesc() {
            return tradeTypeDesc;
        }

        public void setTradeTypeDesc(String tradeTypeDesc) {
            this.tradeTypeDesc = tradeTypeDesc;
        }

        public String getTradeTime() {
            return tradeTime;
        }

        public void setTradeTime(String tradeTime) {
            this.tradeTime = tradeTime;
        }

        public BigDecimal getTradeAmount() {
            return tradeAmount;
        }

        public void setTradeAmount(BigDecimal tradeAmount) {
            this.tradeAmount = tradeAmount;
        }

        public String getPaymentsStatus() {
            return paymentsStatus;
        }

        public void setPaymentsStatus(String paymentsStatus) {
            this.paymentsStatus = paymentsStatus;
        }
    }
}
