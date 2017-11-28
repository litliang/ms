package com.yzb.card.king.bean.order;

import com.yzb.card.king.bean.common.InsuranceBean;
import com.yzb.card.king.ui.app.bean.GoodsAddressBean;

import java.io.Serializable;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/12/24
 * 描  述：
 */
public class HotelOrderDetailBean implements Serializable{

    /**
     * 订单信息
     */
    private ProductBaseOrderBean orderInfo;
    /**
     * 酒店信息
     */
    private BaseHotelOrderInfoBean hotelInfo;
    /**
     * 商品信息
     */
    private  GoodsInfoBean goodsInfo;
    /**
     * 房间信息
     */
    private HotelRoomOrderBean roomsInfo;
    /**
     * 发票信息
     */
    private OrderInvoiceInfoBean invoiceInfo;
    /**
     * 保险信息
     */
    private List<InsuranceBean> insuranceList;
    /**
     * 加购信息
     */
    private List<GoodsPlusOrderBean>   goodsplusList;
    /**
     * 收货地址
     */
    private GoodsAddressBean deliveryAddress;
    /**
     * 抵扣信息
     */
    private List<DeductionOrderBean>  disList;

    public ProductBaseOrderBean getOrderInfo()
    {
        return orderInfo;
    }

    public void setOrderInfo(ProductBaseOrderBean orderInfo)
    {
        this.orderInfo = orderInfo;
    }


    public HotelRoomOrderBean getRoomsInfo()
    {
        return roomsInfo;
    }

    public void setRoomsInfo(HotelRoomOrderBean roomsInfo)
    {
        this.roomsInfo = roomsInfo;
    }

    public BaseHotelOrderInfoBean getHotelInfo()
    {
        return hotelInfo;
    }

    public void setHotelInfo(BaseHotelOrderInfoBean hotelInfo)
    {
        this.hotelInfo = hotelInfo;
    }

    public OrderInvoiceInfoBean getInvoiceInfo()
    {
        return invoiceInfo;
    }

    public void setInvoiceInfo(OrderInvoiceInfoBean invoiceInfo)
    {
        this.invoiceInfo = invoiceInfo;
    }

    public List<InsuranceBean> getInsuranceList()
    {
        return insuranceList;
    }

    public void setInsuranceList(List<InsuranceBean> insuranceList)
    {
        this.insuranceList = insuranceList;
    }

    public List<GoodsPlusOrderBean> getGoodsplusList()
    {
        return goodsplusList;
    }

    public void setGoodsplusList(List<GoodsPlusOrderBean> goodsplusList)
    {
        this.goodsplusList = goodsplusList;
    }

    public List<DeductionOrderBean> getDisList()
    {
        return disList;
    }

    public void setDisList(List<DeductionOrderBean> disList)
    {
        this.disList = disList;
    }

    public GoodsInfoBean getGoodsInfo()
    {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfoBean goodsInfo)
    {
        this.goodsInfo = goodsInfo;
    }

    public GoodsAddressBean getDeliveryAddress()
    {
        return deliveryAddress;
    }

    public void setDeliveryAddress(GoodsAddressBean deliveryAddress)
    {
        this.deliveryAddress = deliveryAddress;
    }
}
