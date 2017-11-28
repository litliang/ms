package com.yzb.card.king.ui.ticket.model;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/10/11
 * 描  述：
 */
public interface IPlaneDtDetail {

    //关注
    public static final int CONCERN_CODE=1;

    //查看航班动态详情
    public static final int SELECT_INFO=2;

    void conCern(Map<String,Object> map);

    void getDateInfo(Map<String,Object> map);
}
