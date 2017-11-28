package com.yzb.card.king.bean.hotel;

import com.yzb.card.king.ui.app.bean.GoodsAddressBean;
import com.yzb.card.king.ui.app.bean.PassengerInfoBean;
import com.yzb.card.king.bean.OverbalanceGoodsBean;

import java.util.List;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/14
 * 描  述：
 */
public class HotelOrderParam {
    /**
     * 活动类型
     */
    private int actType;
    /**
     * 商家id
     */
    private long shopId;
    /**
     * 银行id
     */
    private long bankId;
    /**
     * 酒店id
     */
    private  long hotelId;
    /**
     * 房间id
     */
    private  long roomsId;
    /**
     * 套餐id
     */
    private long policysId;
    /**
     * 活动id
     */
    private long actId;
    /**
     * 套餐名称
     */
    private String policysName;

    /**
     * 付款方式
     */
    private int paymentType;
    /**
     * 订购数量
     */
    private int goodsQuantity;
    /**
     * 入住日期
     */
    private  String arrDate;
    /**
     * 离店日期
     */
    private String depDate;
    /**
     * 生效日期
     */
    private String effDate;
    /**
     * 保留时间
     */
    private String retentionTime;
    /**
     * 担保时间
     */
    private String guaranteeTime;
    /**
     * 订单金额
     */
    private double orderAmount;
    /**
     * 订单备注
     */
    private  String orderRemark;
    /**
     * 旅客信息列表
     */
    private List<PassengerInfoBean>  guestList;
    /**
     * 联系人
     */
    private Map<String, Object> contactsInfo;
    /**
     * 使用者名字
     */
    private String useName;

    /**
     * 发票信息
     */
    private  InvoiceInfoParam invoiceInfo;

    /**
     * 超值加购
     */
    private  List<GoodsPlusParam>  addList;
    /**
     * 酒店商品类型
     */
    private String goodsType;
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 领取方式(1门店自取；2邮寄)
     */
    private int receiveType;
    /**
     * 收货地址
     */
    private GoodsAddressBean deliveryAddress;




    public int getActType()
    {
        return actType;
    }

    public void setActType(int actType)
    {
        this.actType = actType;
    }

    public GoodsAddressBean getDeliveryAddress()
    {
        return deliveryAddress;
    }

    public void setDeliveryAddress(GoodsAddressBean deliveryAddress)
    {
        this.deliveryAddress = deliveryAddress;
    }

    public long getBankId()
    {
        return bankId;
    }

    public void setBankId(long bankId)
    {
        this.bankId = bankId;
    }

    public String getUseName()
    {
        return useName;
    }

    public void setUseName(String useName)
    {
        this.useName = useName;
    }

    public String getEffDate()
    {
        return effDate;
    }

    public void setEffDate(String effDate)
    {
        this.effDate = effDate;
    }

    public long getActId()
    {
        return actId;
    }

    public void setActId(long actId)
    {
        this.actId = actId;
    }

    public String getRetentionTime()
    {
        return retentionTime;
    }

    public int getReceiveType()
    {
        return receiveType;
    }

    public void setReceiveType(int receiveType)
    {
        this.receiveType = receiveType;
    }

    public void setRetentionTime(String retentionTime)
    {
        this.retentionTime = retentionTime;
    }

    public String getGuaranteeTime()
    {
        return guaranteeTime;
    }

    public void setGuaranteeTime(String guaranteeTime)
    {
        this.guaranteeTime = guaranteeTime;
    }

    public String getGoodsId()
    {
        return goodsId;
    }

    public void setGoodsId(String goodsId)
    {
        this.goodsId = goodsId;
    }

    public String getGoodsType()
    {
        return goodsType;
    }

    public String getPolicysName()
    {
        return policysName;
    }

    public void setPolicysName(String policysName)
    {
        this.policysName = policysName;
    }

    public void setGoodsType(String goodsType)
    {
        this.goodsType = goodsType;
    }

    public long getShopId()
    {
        return shopId;
    }

    public void setShopId(long shopId)
    {
        this.shopId = shopId;
    }

    public long getHotelId()
    {
        return hotelId;
    }

    public void setHotelId(long hotelId)
    {
        this.hotelId = hotelId;
    }

    public long getRoomsId()
    {
        return roomsId;
    }

    public void setRoomsId(long roomsId)
    {
        this.roomsId = roomsId;
    }

    public long getPolicysId()
    {
        return policysId;
    }

    public void setPolicysId(long policysId)
    {
        this.policysId = policysId;
    }

    public int getPaymentType()
    {
        return paymentType;
    }

    public void setPaymentType(int paymentType)
    {
        this.paymentType = paymentType;
    }

    public int getGoodsQuantity()
    {
        return goodsQuantity;
    }

    public void setGoodsQuantity(int goodsQuantity)
    {
        this.goodsQuantity = goodsQuantity;
    }

    public String getArrDate()
    {
        return arrDate;
    }

    public void setArrDate(String arrDate)
    {
        this.arrDate = arrDate;
    }

    public String getDepDate()
    {
        return depDate;
    }

    public void setDepDate(String depDate)
    {
        this.depDate = depDate;
    }

    public double getOrderAmount()
    {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount)
    {
        this.orderAmount = orderAmount;
    }

    public String getOrderRemark()
    {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark)
    {
        this.orderRemark = orderRemark;
    }

    public List<PassengerInfoBean> getGuestList()
    {
        return guestList;
    }

    public void setGuestList(List<PassengerInfoBean> guestList)
    {
        this.guestList = guestList;
    }

    public Map<String, Object> getContactsInfo()
    {
        return contactsInfo;
    }

    public InvoiceInfoParam getInvoiceInfo()
    {
        return invoiceInfo;
    }

    public void setInvoiceInfo(InvoiceInfoParam invoiceInfo)
    {
        this.invoiceInfo = invoiceInfo;
    }

    public void setContactsInfo(Map<String, Object> contactsInfo)
    {
        this.contactsInfo = contactsInfo;
    }

    public List<GoodsPlusParam> getAddList()
    {
        return addList;
    }

    public void setAddList(List<GoodsPlusParam> addList)
    {
        this.addList = addList;
    }
}
