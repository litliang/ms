package com.yzb.card.king.ui.gift.view;

import com.yzb.card.king.ui.gift.bean.NoRecvCardBean;

import java.util.List;

/**
 * 功能：礼品卡 e卡列表
 *
 * @author:gengqiyun
 * @date: 2016/12/22
 */
public interface GiftcardNoRecvView
{
    void onGetECardListSuc(boolean event_tag, List<NoRecvCardBean> obj);

    void onGetECardListFail(String failMsg);
}
