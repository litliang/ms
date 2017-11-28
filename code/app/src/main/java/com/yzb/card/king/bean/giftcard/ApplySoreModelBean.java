package com.yzb.card.king.bean.giftcard;

import java.io.Serializable;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/12
 * 描  述：
 */
public class ApplySoreModelBean implements Serializable{

    private  String key;

    private List<GoodsApplyStoreBean> value;

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public List<GoodsApplyStoreBean> getValue()
    {
        return value;
    }

    public void setValue(List<GoodsApplyStoreBean> value)
    {
        this.value = value;
    }
}
