package com.yzb.card.king.ui.gift.view;

import com.yzb.card.king.ui.gift.bean.ECardBean;

import java.util.List;

/**
 * 功能：礼品卡 心意e卡列表
 *
 * @author:gengqiyun
 * @date: 2016/12/22
 */
public interface MindECardsView
{
    void onGetMindECardsSuc(boolean event_tag, List<ECardBean> obj);

    void onGetMindECardsFail(String failMsg);
}
