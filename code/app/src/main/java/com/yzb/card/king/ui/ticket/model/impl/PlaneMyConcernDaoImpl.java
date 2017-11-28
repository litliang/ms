package com.yzb.card.king.ui.ticket.model.impl;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.ticket.FlightDynamicsBean;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.ticket.model.IPlaneMyConcern;

import java.util.List;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/9/28
 * 描  述：
 */
public class PlaneMyConcernDaoImpl implements IPlaneMyConcern {

    private DataCallBack dataCallBack;

    public PlaneMyConcernDaoImpl(DataCallBack dataCallBack){
        this.dataCallBack=dataCallBack;
    }

    /**
     * 查询我的关注列表
     * @param map
     */
    @Override
    public void getList(Map<String, Object> map)
    {
        SimpleRequest request=new SimpleRequest(CardConstant.card_app_plane_followQyeryList,map);
        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                List<FlightDynamicsBean> myConcernBeen= JSON.parseArray(String.valueOf(o),FlightDynamicsBean.class);

                dataCallBack.requestSuccess(myConcernBeen,IPlaneMyConcern.QUERT_CODE);
            }

            @Override
            public void onFailed(Object o)
            {
                dataCallBack.requestFailedDataCall(null, IPlaneMyConcern.QUERT_CODE);
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
