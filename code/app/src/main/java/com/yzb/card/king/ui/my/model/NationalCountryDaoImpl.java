package com.yzb.card.king.ui.my.model;

import com.yzb.card.king.http.CommonServerRequest;
import com.yzb.card.king.http.DataCallBack;

import java.util.Map;


/**
 * 类 名： 国籍Model实现类
 * 作 者： gaolei
 * 日 期：2016年12月20日
 * 描 述：Model实现类
 */

public class NationalCountryDaoImpl implements NationalCountryDao{

    private DataCallBack callback;

    public NationalCountryDaoImpl(DataCallBack callback) {
        this.callback = callback;
    }

    @Override
    public void getNationalCountry(Map<String, Object> map, String service, int type) {
        CommonServerRequest csr = new CommonServerRequest();
        csr.sendReuqest(map, service, type);
        csr.setOnDataLoadFinish(callback);
    }
}
