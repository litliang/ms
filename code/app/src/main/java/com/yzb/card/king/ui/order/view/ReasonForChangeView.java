package com.yzb.card.king.ui.order.view;

import com.yzb.card.king.bean.ticket.ReasonForChangeBean;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;

import java.util.List;

/**
 * 类名： ReasonForChangeView
 * 作者： Lei Chao.
 * 日期： 2016-10-12
 * 描述：
 */
public interface ReasonForChangeView extends BaseViewLayerInterface
{

    /**
     * 退改签原因
     *
     * @param
     * @param
     */
    void getReasonFroChange(List<ReasonForChangeBean> reasonForChangeViews);

}