package com.yzb.card.king.ui.luxury.model;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/20
 * 描  述：
 */
public interface IMsDetail {

    //商家信息标识
    public final static int GETDATA_CODE=1;

    //收藏标识
    public final static int COLLECT_CODE=2;

    //查询是否收藏标识
    public final static int ISCOLLECT_CODE=3;

    /**
     * 获取商家信息
     * @param param
     */
     void getData(Map<String,Object> param);

    /**
     * 收藏
     * @param param
     */
     void collect(Map<String,Object> param);

    /**
     * 查询是否收藏
     * @param param
     */
     void isCollect(Map<String,Object> param);

}
