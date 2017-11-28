package com.yzb.card.king.ui.my.model;

import java.util.Map;

/**
 * 类 名： Jpush
 * 作 者： gaolei
 * 日 期：2017年01月17日
 * 描 述：dao
 */
public interface JpushDao {
    void setJpushData(Map<String, Object> map, String serviceName, int type);
}
