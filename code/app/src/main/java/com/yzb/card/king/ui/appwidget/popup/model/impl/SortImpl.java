package com.yzb.card.king.ui.appwidget.popup.model.impl;

import com.yzb.card.king.ui.appwidget.popup.model.ISort;
import com.yzb.card.king.ui.discount.bean.MenuItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/9/7 17:50
 */
public class SortImpl implements ISort
{

    @Override
    public List<MenuItemBean> getData()
    {
        List<MenuItemBean> lastData = new ArrayList<>();
        String[] colum1 = {"智能", "距离", "价格", "人气", "评价"};
        for (int i = 0; i < colum1.length; i++) {
            MenuItemBean itemBean = new MenuItemBean();
            itemBean.name = colum1[i];
            itemBean.id = i + "";

            // 智能排序,点击直接跳转；
            if (i == 0) {
                itemBean.innerList = null;
                lastData.add(itemBean);
                continue;
            }

            List<MenuItemBean> innerList = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                MenuItemBean itemBean2 = new MenuItemBean();
                itemBean2.id = j + "";
                itemBean2.parentId = i + "";
                switch (i) {
                    case 0: // 智能；
                        itemBean2.id = "";
                        itemBean2.name = "";
                        break;
                    case 1:
                        if (j == 0) {
                            itemBean2.id = "1";
                            itemBean2.name = "由近到远";
                        } else if (j == 1) {
                            itemBean2.id = "2";
                            itemBean2.name = "由远到近";
                        }
                        break;
                    case 2:
                        if (j == 0) {
                            itemBean2.id = "3";
                            itemBean2.name = "升序";
                        } else if (j == 1) {
                            itemBean2.id = "4";
                            itemBean2.name = "降序";
                        }
                        break;
                    case 3:
                        if (j == 0) {
                            itemBean2.id = "5";
                            itemBean2.name = "由高到低";
                        } else if (j == 1) {
                            itemBean2.id = "6";
                            itemBean2.name = "由低到高";
                        }
                        break;
                    case 4:
                        if (j == 0) {
                            itemBean2.id = "7";
                            itemBean2.name = "由高到低";
                        } else if (j == 1) {
                            itemBean2.id = "8";
                            itemBean2.name = "由低到高";
                        }
                        break;
                }
                innerList.add(itemBean2);
            }
            itemBean.innerList = innerList;
            lastData.add(itemBean);

        }
        return lastData;
    }
}
