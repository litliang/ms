package com.yzb.card.king.ui.my.model;

import java.util.Map;

/**
 * 类 名： Model
 * 作 者： gaolei
 * 日 期：2016年12月22日
 * 描 述：个人账单明细model
 */

public interface BillDetailDao {
    void setBillDetail(Map<String, Object> map, String serviceName, int type);
}
