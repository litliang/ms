package com.yzb.card.king.ui.ticket.view;

import com.yzb.card.king.bean.ticket.LowPriceBean;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;

import java.util.List;

/**
 * 类名： FindLowView
 * 作者： Lei Chao.
 * 日期： 2016-10-11
 * 描述： 獲取低價數據
 */

public interface FindLowView extends BaseViewLayerInterface
{

    /**
     * 獲取低價數據
     *
     * @param data
     */
    void getLowPrice(List<LowPriceBean> data);


    void getRound(List<LowPriceBean> data);


}
