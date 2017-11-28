package com.yzb.card.king.ui.my.model;

import java.util.Map;

/**
 * 类 名： 消息
 * 作 者： gaolei
 * 日 期：2017年01月12日
 * 描 述：dao
 */
public interface MessageDao {
    void setMessageData(Map<String, Object> map, String serviceName, int type);
}
