package com.yzb.card.king.bean.user;

import java.io.Serializable;
import java.util.List;

/**
 * 类名： BonusDetailBean
 * 作者： Lei Chao.
 * 日期： 2016-07-22
 * 描述：
 */
public class BonusDetailBean implements Serializable {


    /**
     * amount : 6100
     * receiveQuantity : 3
     * createTime : 1468553792000
     * category : 1
     * themeName : 普通红包祝福语
     * quantity : 50
     * orderTime : 1468553792000
     * issueImage : 2016071413485516070802
     * receiveList : [{"amount":1,"getTime":1469084745000,"receiptor":"Hi Life"},{"amount":30,"getTime":1467777437000,"receiptor":"Hi Life"},{"amount":20,"getTime":1467777137000,"receiptor":"Hi Life"}]
     * orderStatus : 4
     * orderId : 138
     */

    private String amount;
    private int receiveQuantity;
    private long createTime;
    private String category;
    private String themeName;
    private int quantity;
    private long orderTime;
    private String issueImage;
    private String orderStatus;
    private int orderId;
    private String receiveCount;

    public String getReceiveCount() {
        return receiveCount;
    }

    public void setReceiveCount(String receiveCount) {
        this.receiveCount = receiveCount;
    }

    /**
     * amount : 1
     * getTime : 1469084745000
     * receiptor : Hi Life
     */

    private List<ReceiveListBean> receiveList;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getReceiveQuantity() {
        return receiveQuantity;
    }

    public void setReceiveQuantity(int receiveQuantity) {
        this.receiveQuantity = receiveQuantity;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public String getIssueImage() {
        return issueImage;
    }

    public void setIssueImage(String issueImage) {
        this.issueImage = issueImage;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<ReceiveListBean> getReceiveList() {
        return receiveList;
    }

    public void setReceiveList(List<ReceiveListBean> receiveList) {
        this.receiveList = receiveList;
    }

    public static class ReceiveListBean {
        private String amount;
        private long getTime;
        private String receiptor;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public long getGetTime() {
            return getTime;
        }

        public void setGetTime(long getTime) {
            this.getTime = getTime;
        }

        public String getReceiptor() {
            return receiptor;
        }

        public void setReceiptor(String receiptor) {
            this.receiptor = receiptor;
        }
    }
}