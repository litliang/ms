package com.yzb.card.king.ui.hotel.persenter;

import android.text.TextUtils;

import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.hotel.model.HotelHomeImpl;
import com.yzb.card.king.ui.hotel.model.IhotelHome;
import com.yzb.card.king.ui.hotel.view.HotelHomeView;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/8/19
 * 描  述：
 */
public class HotelHomePersenter implements DataCallBack {

    private HotelHomeView view;

    private IhotelHome dao;

    public HotelHomePersenter(HotelHomeView view)
    {

        this.view = view;

        dao = new HotelHomeImpl(this);

    }

    /**
     * 发送用户的酒店频道请求
     */
    public void sendUserChannelRequest()
    {

        dao.sendUserChannelRequest();

    }

    public void sendHotelThemeRequest()
    {

        String[] strArray = view.gatherHotelThemeRequestParam();

        String startSize = strArray[0];


        dao.sendHotelThemeRequest(startSize);
    }

    @Override
    public void requestSuccess(Object o, int type)
    {


        if (type == IhotelHome.USERCHANNEL_CODE)
        {
            view.callBackData(o);
        } else if (type == IhotelHome.HOTELTHEME_CODE)
        {

            view.callSuccessViewLogic(o, IhotelHome.HOTELTHEME_CODE);
        }

    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if(!TextUtils.isEmpty(o+"")){

        if (type == IhotelHome.HOTELTHEME_CODE)
        {
            view.callFailedViewLogic(o, IhotelHome.HOTELTHEME_CODE);
        }
        }
    }
}
