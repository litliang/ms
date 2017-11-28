package com.yzb.card.king.bean.hotel;


import com.yzb.card.king.ui.app.bean.BaseBean;
import com.yzb.card.king.util.Utils;

/**
 * 功能：搜索結果出参；
 *
 * @author:gengqiyun
 * @date: 2016/10/31
 */
public class SearchResultBean extends BaseBean
{
    private String industryId;//行业id
    private String industryName;//行业名称
    private String objId;//对象id
    private String objShopId;//对象id
    private String objName;//对象名称
    private String objMinPrice;//起价
    private String objVote;//评分
    private String objLng;//经度
    private String objLat;//纬度
    private String keyword;//搜索结果；

    private long storeId;

    private String storeName;

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

    public String getKeyword()
    {
        return keyword;
    }

    public String getObjShopId()
    {
        return objShopId;
    }

    public void setObjShopId(String objShopId)
    {
        this.objShopId = objShopId;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    private boolean expand = false; //是否伸展；true：展开状态；false：闭合状态；

    public boolean isExpand()
    {
        return expand;
    }

    public void setExpand(boolean expand)
    {
        this.expand = expand;
    }

    public String getIndustryId()
    {
        return industryId;
    }

    public void setIndustryId(String industryId)
    {
        this.industryId = industryId;
    }

    public String getIndustryName()
    {
        return super.isStrEmpty(industryName);
    }

    public void setIndustryName(String industryName)
    {
        this.industryName = industryName;
    }

    public String getObjId()
    {
        return objId;
    }

    public void setObjId(String objId)
    {
        this.objId = objId;
    }

    public String getObjName()
    {
        return super.isStrEmpty(objName);
    }

    public void setObjName(String objName)
    {
        this.objName = objName;
    }

    public String getObjMinPrice()
    {
        return Utils.subZeroAndDot(objMinPrice);
    }

    public void setObjMinPrice(String objMinPrice)
    {
        this.objMinPrice = objMinPrice;
    }

    public String getObjVote()
    {
        return super.isStrEmpty(objVote);
    }

    public void setObjVote(String objVote)
    {
        this.objVote = objVote;
    }

    public String getObjLng()
    {
        return objLng;
    }

    public void setObjLng(String objLng)
    {
        this.objLng = objLng;
    }

    public String getObjLat()
    {
        return objLat;
    }

    public void setObjLat(String objLat)
    {
        this.objLat = objLat;
    }
}
