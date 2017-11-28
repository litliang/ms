package com.yzb.card.king.ui.my.model;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.common.QueryHotCityRequest;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/23
 * 描  述：
 */
public class HotCityModel {

    private DataCallBack dataCallBack;

    public HotCityModel(DataCallBack dataCallBack){

        this.dataCallBack = dataCallBack;

    }

    /**
     * 查询热门城市
     * @param regionId
     * @param industryId
     * @param type
     */
    public void queryHotelCityAction(String regionId,String industryId,String type){


        QueryHotCityRequest request  = new QueryHotCityRequest(regionId,industryId,type);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {

                dataCallBack.requestSuccess(o,-1);
            }

            @Override
            public void onFailed(Object o)
            {

                dataCallBack.requestFailedDataCall(o,-1);
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

    /**
     * 统计用户点击选择的城市
     * @param addrId
     * @param industryId
     */
    public  void statisticsCityInfoAction(String addrId,String industryId){

        QueryHotCityRequest request  = new QueryHotCityRequest(addrId,industryId);

        request.sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {

                //dataCallBack.requestSuccess(o,-2);
            }

            @Override
            public void onFailed(Object o)
            {

                //dataCallBack.requestFailedDataCall(o,-2);
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
