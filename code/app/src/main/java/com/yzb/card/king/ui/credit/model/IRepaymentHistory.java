package com.yzb.card.king.ui.credit.model;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/12/1
 * 描  述：
 */
public interface IRepaymentHistory {

    /**
     * 信用卡还款记录
     * @param map
     * @param serviceName
     */
    void getList(Map<String, Object> map, String serviceName,int code);
}
