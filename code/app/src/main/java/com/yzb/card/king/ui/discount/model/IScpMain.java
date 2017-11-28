package com.yzb.card.king.ui.discount.model;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/20
 * 描  述：
 */
public interface IScpMain {

    //获取商户列表
    public final static int SHANGHULIST_CODE=1;

    //获取个人频道
    public final static int GERENPD_CODE=2;

    /**
     * 获取商户列表
     * @param param
     */
    void getTjsh(Map<String, Object> param);

    /**
     * 获取个人频道
     * @param parentId
     * @param category
     */
    void getUserChannel(String parentId,String category);
}
