package com.yzb.card.king.ui.hotel.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.hotel.HotelBean;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.hotel.ThemeHotelListRequest;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.util.LogUtil;

import java.util.List;

/**
 * 类  名：推荐酒店dao的实现层
 * 作  者：Li JianQiang
 * 日  期：2016/8/29
 * 描  述：
 */
public class HotelRecommedeyDaoImpl implements HotelRecommedeyDao {

    private DataCallBack dataCallBack;

    public HotelRecommedeyDaoImpl(DataCallBack dataCallBack)
    {
        this.dataCallBack = dataCallBack;
    }

    /**
     * 获取酒店推荐信息
     *
     * @param id
     * @param pageStar
     */
    @Override
    public void getRecommedeyHotel(String id, String pageStar)
    {
        new ThemeHotelListRequest(id, pageStar).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                LogUtil.e("XYYY","---o="+o);
                LogUtil.e("XYYY","---String.valueOf(o)="+String.valueOf(o));
                String data = o + "";
                List<HotelBean> hotelBeanList = JSON.parseArray(data, HotelBean.class);

                LogUtil.e("XYYY","---size="+hotelBeanList.size());

                dataCallBack.requestSuccess(hotelBeanList, HotelRecommedeyDao.HOTEL_RECOMMEDEY);
            }

            @Override
            public void onFailed(Object o)
            {
                dataCallBack.requestFailedDataCall(o, HotelRecommedeyDao.HOTEL_RECOMMEDEY);
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
     * 上拉加载更多
     *
     * @param id
     * @param pageStar
     */
    @Override
    public void getLoadMoreRecommedeyHotel(String id, String pageStar)
    {
        new ThemeHotelListRequest(id, pageStar).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {
            }

            @Override
            public void onSuccess(Object o)
            {
                List<HotelBean> hotelBeanList = JSON.parseArray(String.valueOf(o), HotelBean.class);
                dataCallBack.requestSuccess(hotelBeanList, HotelRecommedeyDao.HOTEL_LOADMORE);
            }

            @Override
            public void onFailed(Object o)
            {
                LogUtil.i("hotelHre "+11);
                dataCallBack.requestSuccess(o, HotelRecommedeyDao.HOTEL_LOADMORE);
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
