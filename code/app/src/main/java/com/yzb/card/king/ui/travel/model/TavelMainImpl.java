package com.yzb.card.king.ui.travel.model;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.CommonServerRequest;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.discount.bean.TextCycleItem;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.common.CustomerChannelListRequest;
import com.yzb.card.king.http.travel.TravelSaleListRequest;
import com.yzb.card.king.http.DataCallBack;

import java.util.List;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/9/1
 * 描  述：
 */
public class TavelMainImpl implements TravelMainDao {

    private DataCallBack dataCallBack;

    public TavelMainImpl(DataCallBack callBack)
    {

        this.dataCallBack = callBack;
    }


    @Override
    public void userChannelRequest(String parentId, String category)
    {


        new CustomerChannelListRequest(parentId, category).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {

                String data = o + "";

                //根据接口文档内容，编辑bean，解析data的value
                List<ChildTypeBean> beans = JSON.parseArray(data, ChildTypeBean.class);

                if (dataCallBack != null)
                {

                    dataCallBack.requestSuccess(beans, TravelMainDao.USERCHANNEL_CODE);

                }
            }

            @Override
            public void onFailed(Object o)
            {
                if (o != null && o instanceof Map)
                {

                    Map<String, String> onSuccessData = (Map<String, String>) o;

                 //   ToastUtil.i(GlobalApp.getInstance().getContext(), onSuccessData.get(HttpConstant.SERVER_ERROR));
                    if (dataCallBack != null)
                    {
                        dataCallBack.requestFailedDataCall(null, TravelMainDao.USERCHANNEL_CODE);
                    }
                }
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

    @Override
    public void specialSaleRequest()
    {

        new TravelSaleListRequest().sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                String data = o + "";

                //根据接口文档内容，编辑bean，解析data的value
                List<TextCycleItem> cycleTextList = JSON.parseArray(data, TextCycleItem.class);
                if (dataCallBack != null)
                {

                    dataCallBack.requestSuccess(cycleTextList, TravelMainDao.SPECIALSALE_CODE);

                }
            }

            @Override
            public void onFailed(Object o)
            {
                if (o != null && o instanceof Map)
                {

                    Map<String, String> onSuccessData = (Map<String, String>) o;

                 //   ToastUtil.i(GlobalApp.getInstance().getContext(), onSuccessData.get(HttpConstant.SERVER_ERROR));
                    if (dataCallBack != null)
                    {
                        dataCallBack.requestFailedDataCall(null, TravelMainDao.SPECIALSALE_CODE);
                    }
                }
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
     * 旅游风向标和上拉加载更多
     * @param map
     * @param serViceName
     */
    @Override
    public void travelFxbRequest(Map<String, Object> map, String serViceName,int code)
    {
        CommonServerRequest csr = new CommonServerRequest();

        csr.sendReuqest(map, serViceName, code);
        csr.setOnDataLoadFinish(dataCallBack);
    }

}
