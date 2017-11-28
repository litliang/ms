package com.yzb.card.king.ui.credit.model;

import com.yzb.card.king.http.CommonServerRequest;
import com.yzb.card.king.http.DataCallBack;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/12/1
 * 描  述：
 */
public class RepaymentHistoryDaoImpl implements IRepaymentHistory {

    private DataCallBack dataCallBack;

    public RepaymentHistoryDaoImpl(DataCallBack dataCallBack)
    {
        this.dataCallBack = dataCallBack;
    }

    /**
     * 获取信用卡还款记录
     * @param map
     * @param serviceName
     */
    @Override
    public void getList(Map<String, Object> map, String serviceName,int code)
    {
        CommonServerRequest csr = new CommonServerRequest();
        csr.sendReuqest(map, serviceName, code);
        csr.setOnDataLoadFinish(dataCallBack);
    }
}
