package com.yzb.card.king.ui.ticket.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.ticket.PlaneStarEndBean;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.ticket.model.IPlaneListQj;

import java.util.List;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/10/14
 * 描  述：
 */
public class PlaneListQjDaoImpl implements IPlaneListQj {

    private DataCallBack dataCallBack;

    public PlaneListQjDaoImpl(DataCallBack dataCallBack)
    {
        this.dataCallBack = dataCallBack;
    }

    @Override
    public void getListInfo(Map<String, Object> map)
    {
        new SimpleRequest(CardConstant.ticket_follow_query_star_end,map).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                List<PlaneStarEndBean> planeStarEndBeens= JSON.parseArray(String.valueOf(o),PlaneStarEndBean.class);
                dataCallBack.requestSuccess(planeStarEndBeens,IPlaneListQj.GET_LIST_INFO);
            }

            @Override
            public void onFailed(Object o)
            {
                dataCallBack.requestFailedDataCall(o,IPlaneListQj.GET_LIST_INFO);
            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {

            }
        });
    }
}
