package com.yzb.card.king.ui.hotel.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.hotel.HotelThemeBean;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.hotel.RecommendThemeListRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.other.task.CustomerChannelListTask;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/8/19
 * 描  述：
 */
public class HotelHomeImpl implements IhotelHome {

    private DataCallBack callBack;

    public HotelHomeImpl(DataCallBack callBack)
    {

        this.callBack = callBack;

    }

    @Override
    public void sendUserChannelRequest()
    {


        CustomerChannelListTask task =    new CustomerChannelListTask(new CustomerChannelListTask.IChannelListCallBack() {
            @Override
            public void callBack(List<ChildTypeBean> displayChannelList, List<ChildTypeBean> hideChannelList)
            {
                if (displayChannelList != null)
                {
                    displayChannelList.addAll(hideChannelList);

                    String localJson = SharePrefUtil.getValueFromSp(GlobalApp.getInstance(), AppConstant.sp_childtypelist_hotel, "[]");
                    List<ChildTypeBean> localLists = JSON.parseArray(localJson, ChildTypeBean.class);

                    localLists = CommonUtil.filterList(localLists, displayChannelList);

                    SharePrefUtil.saveToSp(GlobalApp.getInstance().getPublicActivity(),
                            AppConstant.sp_childtypelist_hotel, JSON.toJSONString(localLists));

                    callBack.requestSuccess(localLists, IhotelHome.USERCHANNEL_CODE);

                }
            }
        }) ;

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("parentId", AppConstant.hotel_id);
        param.put("category", AppConstant.discount_channel_category);
        task.setParamData(param);
        task.sendRequest(null);
    }

    @Override
    public void sendHotelThemeRequest( String startSize)
    {


        new RecommendThemeListRequest( startSize).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {

                String data = o + "";
                List<HotelThemeBean> list = JSON.parseArray(data, HotelThemeBean.class);
                callBack.requestSuccess(list, IhotelHome.HOTELTHEME_CODE);
            }

            @Override
            public void onFailed(Object o)
            {

                if (o != null && o instanceof Map)
                {

                    Map<String, String> onSuccessData = (Map<String, String>) o;

                    String code = onSuccessData.get(HttpConstant.SERVER_CODE);

                    if (code.equals(HttpConstant.NOINFO))
                    {

                        callBack.requestSuccess(null, IhotelHome.HOTELTHEME_CODE);

                    } else
                    {
                        ToastUtil.i(GlobalApp.getInstance().getContext(), onSuccessData.get(HttpConstant.SERVER_ERROR));

                        callBack.requestFailedDataCall(null, IhotelHome.HOTELTHEME_CODE);
                    }


                } else
                {
                    callBack.requestFailedDataCall(o, IhotelHome.HOTELTHEME_CODE);
                }


            }

            @Override
            public void onCancelled(Object o)
            {
                callBack.requestFailedDataCall(null, 2);
            }

            @Override
            public void onFinished()
            {

            }
        });


    }
}
