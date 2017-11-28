package com.yzb.card.king.ui.hotel.persenter;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.hotel.model.HotelPicDaoImpl;
import com.yzb.card.king.ui.hotel.model.IHotelSellingPointDetail;

import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/10/31
 * 描  述：
 */
public class HotelPicPresenter implements DataCallBack {

    private IHotelSellingPointDetail dao;

    private BaseViewLayerInterface view;

    public HotelPicPresenter(BaseViewLayerInterface view)
    {
        this.view = view;
        this.dao = new HotelPicDaoImpl(this);
    }

    /**
     * 获取酒店图片
     *
     * @param map
     * @param serverName
     */
    public void getHotelPic(Map<String, Object> map, String serverName)
    {
        dao.selectHotelService(map, serverName);
    }

    @Override
    public void requestSuccess(Object o, int type)
    {
        if (type == IHotelSellingPointDetail.HOTEL_PIC)
        {
            view.callSuccessViewLogic(o, IHotelSellingPointDetail.HOTEL_PIC);
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if (type == IHotelSellingPointDetail.HOTEL_PIC)
        {
            view.callFailedViewLogic(o, IHotelSellingPointDetail.HOTEL_PIC);
        }
    }
}
