package com.yzb.card.king.bean.giftcard;

import java.io.Serializable;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/11
 * 描  述：
 */
public class GoodsApplyStoreBean implements Serializable {
    /**
     * 门店id
     */
    private  long storeId;
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 门店地址
     */
    private String storeAddr;
    /**
     * 联系方式
     */
    private String storeTel;

    public long getStoreId()
    {
        return storeId;
    }

    public void setStoreId(long storeId)
    {
        this.storeId = storeId;
    }

    public String getStoreName()
    {
        return storeName;
    }

    public void setStoreName(String storeName)
    {
        this.storeName = storeName;
    }

    public String getStoreAddr()
    {
        return storeAddr;
    }

    public void setStoreAddr(String storeAddr)
    {
        this.storeAddr = storeAddr;
    }

    public String getStoreTel()
    {
        return storeTel;
    }

    public void setStoreTel(String storeTel)
    {
        this.storeTel = storeTel;
    }
}
