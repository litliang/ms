package com.yzb.card.king.ui.gift.view;

import com.yzb.card.king.ui.gift.bean.GiftcardTypeBean;

import java.util.List;

/**
 * 功能：查询礼品卡分类列表
 *
 * @author:gengqiyun
 * @date: 2016/12/28
 */
public interface GiftcardTypeView
{
    void onGetGiftcardTypeSuc(List<GiftcardTypeBean> list);

    void onGetGiftcardTypeFail(String failMsg);
}
