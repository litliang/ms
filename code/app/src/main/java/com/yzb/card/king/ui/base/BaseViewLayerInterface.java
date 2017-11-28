package com.yzb.card.king.ui.base;

/**
 * Mvp模式view层基类接口
 * Created by 玉兵 on 2017/5/16.
 */

public interface BaseViewLayerInterface {

    /**
     * 成功回调视图层业务逻辑数据
     * @param o
     * @param type
     */
    void callSuccessViewLogic(Object o,int type);

    /**
     * 失败回调视图层业务逻辑数据
     * @param o
     * @param type
     */
    void callFailedViewLogic(Object o,int type);
}
