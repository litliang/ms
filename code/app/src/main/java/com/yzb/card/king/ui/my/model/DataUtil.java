package com.yzb.card.king.ui.my.model;

import com.yzb.card.king.bean.CatalogueTypeBean;
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.ui.gift.bean.GiftcardTypeBean;
import com.yzb.card.king.bean.travel.FilterBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的-->数据列表；
 *
 * @author gengqiyun
 * @date 2016.12.16
 */
public class DataUtil implements Serializable {
    /**
     * 礼品卡-心意卡排序列表；
     */
    public static List<GiftcardTypeBean> getSortData()
    {
        List<GiftcardTypeBean> beans = new ArrayList<>();
        String[] typeIds = {"1", "2"};
        String[] typeNames = {"热度最高", "最新"};
        GiftcardTypeBean item;
        for (int i = 0; i < typeIds.length; i++) {
            item = new GiftcardTypeBean();
            item.setTypeId(typeIds[i]);
            item.setTypeName(typeNames[i]);
            beans.add(item);
        }
        return beans;
    }

    /**
     * 获取优惠券使用情况；
     */
    public static List<SubItemBean> getCouponUseCondition()
    {
        List<SubItemBean> oneList = new ArrayList<>();

        SubItemBean oneTemp = new SubItemBean();
        oneTemp.setFilterId("1");
        oneTemp.setFilterName("代金券");
        oneList.add(oneTemp);

        SubItemBean twoTemp = new SubItemBean();
        twoTemp.setFilterId("2");
        twoTemp.setFilterName("优惠券");
        oneList.add(twoTemp);


        return oneList;
    }


    /**
     * 获取智能排序数据；
     * 1最近领取；2最近到期；3：离我最近，4：人气最高
     *
     * @return
     */
    public static List<SubItemBean> getCouponSorts()
    {
        List<SubItemBean> oneList = new ArrayList<>();

        SubItemBean oneTemp = new SubItemBean();
        oneTemp.setFilterId("1");
        oneTemp.setFilterName("离我最近");
        oneList.add(oneTemp);

        SubItemBean twoTemp = new SubItemBean();
        twoTemp.setFilterId("2");
        twoTemp.setFilterName("人气最高");
        oneList.add(twoTemp);


        SubItemBean threeTemp = new SubItemBean();
        threeTemp.setFilterId("3");
        threeTemp.setFilterName("最近领取");
        oneList.add(threeTemp);

        SubItemBean fourTemp = new SubItemBean();
        fourTemp.setFilterId("4");
        fourTemp.setFilterName("最近过期");
        oneList.add(fourTemp);

        return oneList;
    }

    /**
     * 获取智能排序数据；
     * 1最近领取；2最近到期；3：离我最近，4：人气最高
     *
     * @return
     */
    public static List<SubItemBean> getCouponSorts2()
    {
        List<SubItemBean> oneList = new ArrayList<>();

        SubItemBean oneTemp = new SubItemBean();
        oneTemp.setFilterId("1");
        oneTemp.setFilterName("离我最近");
        oneList.add(oneTemp);

        SubItemBean twoTemp = new SubItemBean();
        twoTemp.setFilterId("2");
        twoTemp.setFilterName("人气最高");
        oneList.add(twoTemp);
        return oneList;
    }
}
