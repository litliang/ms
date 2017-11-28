package com.yzb.card.king.ui.integral.model;

import com.yzb.card.king.bean.integral.RemindBean;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/8/16
 * 描  述：
 */
public interface MyRemindDao {

    /**
     * 获取我的提醒数据
     * @param type
     */
    void getData(String type);

    /**
     * 获取当前信用卡信息
     * @param id
     */
    void getCardInfo(int id);

    /**
     * 删除我的提醒
     */
    void deleteRemind(RemindBean remindBean);

    void setOnDataLoadFinish(OnDataLoadFinish onDataLoadFinish);

}
