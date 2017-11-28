package com.yzb.card.king.ui.credit.model;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/12
 * 描  述：
 */
public interface IAdjustLimit
{

    //查询信用卡列表标识
    public final static int QUERYCREDITCARD_CODE = 1;

    //修改信用卡标识


    /**
     * 查询信用卡列表
     *
     * @param map
     */
    void queryCreditCard(Map<String, Object> map);

}
