package com.yzb.card.king.ui.integral.model.impl;


import com.yzb.card.king.bean.user.UserCollectBean;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.hotel.HotelCollectionRequest;
import com.yzb.card.king.http.integral.ShopStopRequest;
import com.yzb.card.king.ui.integral.model.MyCollectDao;
import com.yzb.card.king.ui.integral.model.OnDataLoadFinish;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/8/16
 * 描  述：
 */
public class MyCollectDaoImpl implements MyCollectDao
{

    private OnDataLoadFinish onDataLoadFinish;

    @Override
    public void getMyCollectInfo(String type) {
        sendGetRequest(type);
    }

    @Override
    public void deleteMyCollect(UserCollectBean ucBean,String type) {
        sendDeleteRequest(ucBean,type);
    }


    @Override
    public void setOnDataLoadFinish(OnDataLoadFinish onDataLoadFinish) {
        this.onDataLoadFinish=onDataLoadFinish;
    }

    /**
     * 发送删除请求
     * @param rb
     */
    private void sendDeleteRequest(final UserCollectBean rb,String type) {
//        new HotelCollectionRequest(rb.getStoreId()+"","0",type,rb.getCategory()).sendRequest(new HttpCallBackData() {
        new HotelCollectionRequest(rb.getStoreId()+"","0",type, rb.getType()).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {
                onDataLoadFinish.onStart();
            }

            @Override
            public void onSuccess(Object o)
            {
                onDataLoadFinish.onDelete(rb);
            }

            @Override
            public void onFailed(Object o)
            {
                onDataLoadFinish.onFailed(o);
            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {
                onDataLoadFinish.onFinish();
            }
        });

    }

    /**
     * 发送获取请求
     * @param type
     */
    private void sendGetRequest(String type) {
        new ShopStopRequest(type).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart() {
                onDataLoadFinish.onStart();
            }

            @Override
            public void onSuccess(Object o) {
                onDataLoadFinish.onSuccess(o);
            }

            @Override
            public void onFailed(Object o) {
                onDataLoadFinish.onFailed(o);
            }

            @Override
            public void onCancelled(Object o) {
            }

            @Override
            public void onFinished() {
                onDataLoadFinish.onFinish();
            }
        });
    }


}
