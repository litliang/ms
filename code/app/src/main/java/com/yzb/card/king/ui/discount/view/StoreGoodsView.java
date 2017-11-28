package com.yzb.card.king.ui.discount.view;

/**
 * 功能：店铺商品列表；
 *
 * @author:gengqiyun
 * @date: 2016/9/21
 */
public interface StoreGoodsView
{
    /**
     * @param event_tag 下拉或上拉；
     * @param data
     */
    void onLoadStoreGoodsSucess(boolean event_tag, Object data);

    /**
     * 失败；
     *
     * @param failMsg 错误消息；
     */
    void onLoadStoreGoodsFail(String failMsg);
}
