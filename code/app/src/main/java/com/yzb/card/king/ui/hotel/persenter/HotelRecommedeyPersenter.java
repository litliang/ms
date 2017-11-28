package com.yzb.card.king.ui.hotel.persenter;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.hotel.model.HotelRecommedeyDao;
import com.yzb.card.king.ui.hotel.model.HotelRecommedeyDaoImpl;

/**
 * 类  名：酒店推荐观察者
 * 作  者：Li JianQiang
 * 日  期：2016/8/29
 * 描  述：
 */
public class HotelRecommedeyPersenter implements DataCallBack {

    private BaseViewLayerInterface hotelRecommedeyView;

    private HotelRecommedeyDao hotelRecommedeyDao;

    public HotelRecommedeyPersenter(BaseViewLayerInterface hotelRecommedeyView)
    {
        this.hotelRecommedeyView = hotelRecommedeyView;
        this.hotelRecommedeyDao = new HotelRecommedeyDaoImpl(this);
    }

    /**
     * 获取酒店推荐信息
     *
     * @param id
     * @param pageStar
     */
    public void getHotelRecommedey(String id, String pageStar)
    {
        this.hotelRecommedeyDao.getRecommedeyHotel(id, pageStar);
    }

    /**
     * 上拉加载更多酒店推荐信息
     *
     * @param id
     * @param pageStar 分页参数
     */
    public void getLoadMoreRecommedey(String id, String pageStar)
    {
        this.hotelRecommedeyDao.getLoadMoreRecommedeyHotel(id, pageStar);
    }

    @Override
    public void requestSuccess(Object o, int type)
    {
        if (type == HotelRecommedeyDao.HOTEL_RECOMMEDEY)
        {
            hotelRecommedeyView.callSuccessViewLogic(o, HotelRecommedeyDao.HOTEL_RECOMMEDEY);
        } else if (type == HotelRecommedeyDao.HOTEL_LOADMORE)
        {
            hotelRecommedeyView.callSuccessViewLogic(o, HotelRecommedeyDao.HOTEL_LOADMORE);
        }

    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if (type == HotelRecommedeyDao.HOTEL_RECOMMEDEY)
        {
            hotelRecommedeyView.callFailedViewLogic(o, HotelRecommedeyDao.HOTEL_RECOMMEDEY);
        } else if (type == HotelRecommedeyDao.HOTEL_LOADMORE)
        {
            hotelRecommedeyView.callFailedViewLogic(o, HotelRecommedeyDao.HOTEL_LOADMORE);
        }
    }
}
