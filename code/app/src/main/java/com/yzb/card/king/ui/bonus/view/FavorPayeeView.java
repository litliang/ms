package com.yzb.card.king.ui.bonus.view;

import com.yzb.card.king.ui.gift.bean.FavorPayee;

import java.util.List;

/**
 * 功能：红包主题
 *
 * @author:gengqiyun
 * @date: 2016/12/12
 */
public interface FavorPayeeView
{
    void onGetFavorPayeeSuc(boolean event_tag, List<FavorPayee> list);

    void onGetFavorPayeeFail(String failMsg);
}
