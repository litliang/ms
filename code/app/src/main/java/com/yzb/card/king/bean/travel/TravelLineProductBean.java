package com.yzb.card.king.bean.travel;

import com.yzb.card.king.bean.hotel.HotelLabelBean;
import com.yzb.card.king.bean.ticket.BankInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 类  名：旅游线路产品
 * 作  者：Li Yubing
 * 日  期：2016/11/28
 * 描  述：
 */
public class TravelLineProductBean implements Serializable{

    private int viewType = 0;//0:表示正常视图；1：表示无数据视图;2:表示更多；3：表示无卡信息和有卡无优惠信息试图
    private String dataPrompt ;
    //产品id
    private long productId;
    //产品名称
    private  String productName;
    //产品图片
    private String imageCode;
    //产品图片
    private String productImageUrl;
    //实际出发地
    private String fromPlace;
    //特卖惠状态
    private String salesStatus;
    //旅行社id
    private String agentId;
    //旅行社名称
    private String agentName;
    //出发日期描述(多个使用英文逗号分割)
    private String depDateDesc;
    //起价
    private String bgnPrice;
    //线路id
    private String lineId;
    //产品类型
    private String productType;
    //当地特色
    private List<HotelLabelBean> labelList;
    //银行优惠状态
    private String bankStatus;
    //商家优惠状态
    private String shopStatus;
    //平台优惠状态
    private String platformStatus;
    //银行优惠列表
    private List<BankInfo> bankList;

    public long getProductId()
    {
        return productId;
    }

    public void setProductId(long productId)
    {
        this.productId = productId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getImageCode()
    {
        return imageCode;
    }

    public void setImageCode(String imageCode)
    {
        this.imageCode = imageCode;
    }

    public String getFromPlace()
    {
        return fromPlace;
    }

    public void setFromPlace(String fromPlace)
    {
        this.fromPlace = fromPlace;
    }

    public String getSalesStatus()
    {
        return salesStatus;
    }

    public void setSalesStatus(String salesStatus)
    {
        this.salesStatus = salesStatus;
    }

    public String getAgentId()
    {
        return agentId;
    }

    public void setAgentId(String agentId)
    {
        this.agentId = agentId;
    }

    public String getAgentName()
    {
        return agentName;
    }

    public void setAgentName(String agentName)
    {
        this.agentName = agentName;
    }

    public String getDepDateDesc()
    {
        return depDateDesc;
    }

    public void setDepDateDesc(String depDateDesc)
    {
        this.depDateDesc = depDateDesc;
    }

    public String getBgnPrice()
    {
        return bgnPrice;
    }

    public void setBgnPrice(String bgnPrice)
    {
        this.bgnPrice = bgnPrice;
    }

    public String getLineId()
    {
        return lineId;
    }

    public void setLineId(String lineId)
    {
        this.lineId = lineId;
    }

    public String getProductType()
    {
        return productType;
    }

    public void setProductType(String productType)
    {
        this.productType = productType;
    }

    public List<HotelLabelBean> getLabelList()
    {
        return labelList;
    }

    public void setLabelList(List<HotelLabelBean> labelList)
    {
        this.labelList = labelList;
    }

    public String getBankStatus()
    {
        return bankStatus;
    }

    public void setBankStatus(String bankStatus)
    {
        this.bankStatus = bankStatus;
    }

    public String getShopStatus()
    {
        return shopStatus;
    }

    public void setShopStatus(String shopStatus)
    {
        this.shopStatus = shopStatus;
    }

    public String getPlatformStatus()
    {
        return platformStatus;
    }

    public void setPlatformStatus(String platformStatus)
    {
        this.platformStatus = platformStatus;
    }

    public List<BankInfo> getBankList()
    {
        return bankList;
    }

    public void setBankList(List<BankInfo> bankList)
    {
        this.bankList = bankList;
    }

    public int getViewType()
    {
        return viewType;
    }

    public void setViewType(int viewType)
    {
        this.viewType = viewType;
    }

    public String getDataPrompt()
    {
        return dataPrompt;
    }

    public void setDataPrompt(String dataPrompt)
    {
        this.dataPrompt = dataPrompt;
    }

    public String getProductImageUrl()
    {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl)
    {
        this.productImageUrl = productImageUrl;
    }
}
