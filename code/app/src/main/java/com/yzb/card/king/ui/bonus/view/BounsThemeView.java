package com.yzb.card.king.ui.bonus.view;

import com.yzb.card.king.ui.bonus.bean.BounsThemeBean;

import java.util.List;

/**
 * 功能：红包主题
 *
 * @author:gengqiyun
 * @date: 2016/12/12
 */
public interface BounsThemeView
{
    void onGetBounsThemeSuc(boolean event_tag, List<BounsThemeBean> list);

    void onGetBounsThemeFail(String failMsg);
}
