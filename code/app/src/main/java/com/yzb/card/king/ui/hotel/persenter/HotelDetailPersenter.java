package com.yzb.card.king.ui.hotel.persenter;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.hotel.model.HotelDeatilDaoImpl;
import com.yzb.card.king.ui.hotel.model.IHotelDetail;

/**
 * 类  名：酒店详情的观察者
 * 作  者：Li JianQiang
 * 日  期：2016/8/25
 * 描  述：
 */
public class HotelDetailPersenter implements DataCallBack {

    private IHotelDetail hotelDetailDao;

    private BaseViewLayerInterface hotelDetailView;

    public HotelDetailPersenter(BaseViewLayerInterface hotelDetailView)
    {
        this.hotelDetailView = hotelDetailView;

        this.hotelDetailDao = new HotelDeatilDaoImpl(this);
    }

    /**
     * 获得酒店详情
     *
     * @param hotelId
     */
    public void getHotelDetail(String hotelId,String arrDate)
    {
        hotelDetailDao.getHotelDetail(hotelId,arrDate);
    }

    /**
     * 发送酒店服务器请求
     * @param hotelId
     */
    public void sendSelectHotelServiceInfoRequest(String hotelId)
    {
        hotelDetailDao.sendSelectHotelServiceInfoRequestAction(hotelId);
    }

    /**
     * 收藏
     *
     * @param id
     * @param status
     */
    public void collectHotel(String id, String status, String type, String category)
    {
        hotelDetailDao.collectHotel(id, status, type, category);
    }


    @Override
    public void requestSuccess(Object o, int type)
    {

        hotelDetailView.callSuccessViewLogic(o, type);

    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {

        hotelDetailView.callFailedViewLogic(o, type);

    }
}
