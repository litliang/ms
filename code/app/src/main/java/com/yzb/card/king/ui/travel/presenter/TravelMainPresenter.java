package com.yzb.card.king.ui.travel.presenter;

import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.travel.model.TavelMainImpl;
import com.yzb.card.king.ui.travel.model.TravelMainDao;
import com.yzb.card.king.ui.travel.view.TravelMainView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类  名：旅游首页观察者
 * 作  者：Li Yubing
 * 日  期：2016/9/1
 * 描  述：管理旅游的首页和子频道首页业务管理
 */
public class TravelMainPresenter implements DataCallBack {

    private TravelMainDao travelMainDao;

    private TravelMainView travelMainView;

    public TravelMainPresenter(TravelMainView travelMainView)
    {

        travelMainDao = new TavelMainImpl(this);

        this.travelMainView = travelMainView;

    }

    /**
     * 发送特卖惠请求
     */
    public void sendSpecialSaleRequest()
    {

        travelMainDao.specialSaleRequest();

    }

    /**
     * 发送旅游用户主频道请求
     */
    public void sendUserChannelRequest()
    {

        String typeGrandParentId = AppConstant.travel_id;

        String category = AppConstant.discount_channel_category;

        travelMainDao.userChannelRequest(typeGrandParentId, category);
    }

    /**
     * 发送旅游子频道请求
     *
     * @param parentId
     * @param category
     */
    public void sendChildUserChannelRequest(String parentId, String category)
    {
        travelMainDao.userChannelRequest(parentId, category);
    }

    /**
     * 发送旅游风向标请求和上拉
     */
    public void sendTravelFxbRequest(Map<String, Object> map, String serviceName, int code)
    {

        travelMainDao.travelFxbRequest(map, serviceName, code);
    }


    @Override
    public void requestSuccess(Object o, int type)
    {

        if (type == TravelMainDao.USERCHANNEL_CODE)
        {

            //解析出隐藏的和显示的数据
            List<ChildTypeBean> displayBeans = new ArrayList<>();
            List<ChildTypeBean> hideBeans = new ArrayList<>();

            List<ChildTypeBean> beans = (List<ChildTypeBean>) o;
            if (beans != null && beans.size() > 0)
            {
                for (ChildTypeBean item : beans)
                {
                    item.typeImage = ServiceDispatcher.getImageUrl(item.typeImage);
                    //显示；
                    if ("1".equals(item.status))
                    {
                        displayBeans.add(item);
                    } else
                    {
                        hideBeans.add(item);
                    }
                }
            }

            if (travelMainView != null)
            {
                travelMainView.transmitChannelData(displayBeans, hideBeans);
            }
        } else if (type == TravelMainDao.SPECIALSALE_CODE)
        {

            travelMainView.callSuccessViewLogic(o, type);

        } else if (type == TravelMainDao.TRAVELFXB_CODE)
        {


            travelMainView.callSuccessViewLogic(o, TravelMainDao.TRAVELFXB_CODE);
        } else if (type == TravelMainDao.TRAVELFXB_MORE_CODE)
        {
            travelMainView.callSuccessViewLogic(o, TravelMainDao.TRAVELFXB_MORE_CODE);
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        travelMainView.callFailedViewLogic(o, -1);
    }
}
