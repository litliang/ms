package com.yzb.card.king.ui.bonus.view;

import com.yzb.card.king.ui.bonus.bean.BounsBean;

import java.util.List;

/**
 * 功能：红包列表
 *
 * @author:gengqiyun
 * @date: 2016/12/30
 */
public interface BounsListView
{
    void onGetBounsListSuc(boolean event_tag, List<BounsBean> list);

    void onGetBounsListFail(String failMsg);
}
