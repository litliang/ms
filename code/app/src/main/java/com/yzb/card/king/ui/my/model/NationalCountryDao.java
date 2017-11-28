package com.yzb.card.king.ui.my.model;


import java.util.Map;

/**
 * 类 名： Model
 * 作 者： gaolei
 * 日 期：2016年12月20日
 * 描 述：国籍的model
 */

public interface NationalCountryDao {
    void getNationalCountry(Map<String, Object> map, String service, int type);
}
