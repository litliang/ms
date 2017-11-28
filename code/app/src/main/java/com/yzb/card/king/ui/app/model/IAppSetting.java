package com.yzb.card.king.ui.app.model;

import com.yzb.card.king.ui.app.bean.AppSettingItem;

import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/12 17:24
 */
public interface IAppSetting
{
    void loadNotice();

    void save(String status, List<AppSettingItem> dataList);
}
