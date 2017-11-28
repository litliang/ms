package com.yzb.card.king.ui.manage;

import com.yzb.card.king.ui.credit.bean.LeftPopItem;

import java.util.List;

/**
 * 类名：DiscountManager
 * 作者：殷曙光
 * 日期：2016/6/13 15:55
 * 描述：
 */

public class DiscountManager {

    private List<LeftPopItem> dataList;//左侧浮框数据

    private static DiscountManager discountManager;

    private DiscountManager() {

    }

    public static DiscountManager getInstance() {
        if (discountManager == null) {
            discountManager = new DiscountManager();
        }
        return discountManager;
    }

    public List<LeftPopItem> getDataList() {
        return dataList;
    }

    public void setDataList(List<LeftPopItem> dataList) {


        this.dataList = dataList;
    }
}
