package com.yzb.card.king.bean.ticket;

import java.util.List;
import java.util.Map;

/**
 * 功能：退票查询
 *
 * @author:gengqiyun
 * @date: 2016/12/2
 */
public class BouncQueryBean
{
    private float refundAmount;//退票金额（退还给用户的金额）
    private float refundFee;//退票手续费（收取用户的手续费）
    private List<RefundInfo> refundList; //退票信息
    private String message;//退票信息

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public float getRefundAmount()
    {
        return refundAmount;
    }

    public void setRefundAmount(float refundAmount)
    {
        this.refundAmount = refundAmount;
    }

    public float getRefundFee()
    {
        return refundFee;
    }

    public void setRefundFee(float refundFee)
    {
        this.refundFee = refundFee;
    }

    public List<RefundInfo> getRefundList()
    {
        return refundList;
    }

    public void setRefundList(List<RefundInfo> refundList)
    {
        this.refundList = refundList;
    }

    public static class RefundInfo
    {
        private String orderNo;//大订单号
        private boolean isNewOrder;//true为新订单；false：为老订单；
        private String paxIdsAdt;//退款人员id（要退的成人乘机人id，多个以逗号分隔，形式为：paxId1,paxId2...）
        private String paxIdsChd;//要退的儿童乘机人id，多个以逗号分隔，形式为：paxId1,paxId2...
        private String paxIdsInf;//要退的婴儿乘机人id，多个以逗号分隔，形式为：paxId1,paxId2...
        private List<Map<String, String>> userList; //证件信息
        private List<Map<String, String>> refundInfos; //退票详情；

        public List<Map<String, String>> getUserList()
        {
            return userList;
        }

        public void setUserList(List<Map<String, String>> userList)
        {
            this.userList = userList;
        }

        public boolean isNewOrder()
        {
            return isNewOrder;
        }

        public List<Map<String, String>> getRefundInfos()
        {
            return refundInfos;
        }

        public void setRefundInfos(List<Map<String, String>> refundInfos)
        {
            this.refundInfos = refundInfos;
        }

        public void setNewOrder(boolean newOrder)
        {
            isNewOrder = newOrder;
        }

        public String getOrderNo()
        {
            return orderNo;
        }

        public void setOrderNo(String orderNo)
        {
            this.orderNo = orderNo;
        }

        public String getPaxIdsAdt()
        {
            return paxIdsAdt;
        }

        public void setPaxIdsAdt(String paxIdsAdt)
        {
            this.paxIdsAdt = paxIdsAdt;
        }

        public String getPaxIdsChd()
        {
            return paxIdsChd;
        }

        public void setPaxIdsChd(String paxIdsChd)
        {
            this.paxIdsChd = paxIdsChd;
        }

        public String getPaxIdsInf()
        {
            return paxIdsInf;
        }

        public void setPaxIdsInf(String paxIdsInf)
        {
            this.paxIdsInf = paxIdsInf;
        }
    }
}
