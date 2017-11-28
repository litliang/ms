package com.yzb.card.king.ui.discount.model;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.ui.other.task.CustomerChannelListTask;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.discount.bean.CouponBean;
import com.yzb.card.king.ui.travel.bean.DiscountRecommendBean;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.coupon.CouponDataRequest;
import com.yzb.card.king.http.recommendedoffers.RecommendRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.discount.presenter.DiscountCallBack;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.SharePrefUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类  名：实现首页商户优惠的具体方法
 * 作  者：Li Yubing
 * 日  期：2016/8/16
 * 描  述：
 */
public class DiscountDao implements DiscountImpl {

    private DiscountCallBack callBack;

    public DiscountDao(DiscountCallBack callBack)
    {

        this.callBack = callBack;

    }

    @Override
    public void sendPrivilegetInfoRequest(String cityId)
    {

        new RecommendRequest(cityId).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                List<DiscountRecommendBean> discountRecommendBeen = JSON.parseArray(String.valueOf(o), DiscountRecommendBean.class);
                //discountIndexFragmentAdapter.addData(JSON.parseArray(String.valueOf(o), DiscountRecommendBean.class));

                callBack.requestSuccess(discountRecommendBeen, 1);
            }

            @Override
            public void onFailed(Object o)
            {

                callBack.requestFailedDataCall(null, 1);
            }

            @Override
            public void onCancelled(Object o)
            {

                callBack.requestFailedDataCall(null, 1);
            }

            @Override
            public void onFinished()
            {

            }
        });
    }

    @Override
    public void sendCustomerChannelListRequest()
    {
        CustomerChannelListTask task = new CustomerChannelListTask(new CustomerChannelListTask.IChannelListCallBack() {
            @Override
            public void callBack(List<ChildTypeBean> displayChannelList, List<ChildTypeBean> hideChannelList)
            {
                if (displayChannelList != null) {
                    displayChannelList.addAll(hideChannelList);

                    String localJson = SharePrefUtil.getValueFromSp(GlobalApp.getInstance(), AppConstant.sp_childtypelist_home, "[]");
                    List<ChildTypeBean> localLists = JSON.parseArray(localJson, ChildTypeBean.class);
                    localLists = CommonUtil.filterList(localLists, displayChannelList);
                    // 持久化本地；
                    SharePrefUtil.saveToSp(GlobalApp.getInstance(), AppConstant.sp_childtypelist_home,
                            JSON.toJSONString(localLists));
                    callBack.requestSuccess(localLists, 2);
                }
            }
        });


        Map<String, Object> param = new HashMap<String, Object>();
        param.put("parentId", AppConstant.discount_type_parentid);
        param.put("category", AppConstant.discount_channel_category);
        task.setParamData(param);
        task.sendRequest(null);
    }


    @Override
    public void sendCouponRequest()
    {


        new CouponDataRequest().sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {

                List<CouponBean> couponBeans = JSON.parseArray(String.valueOf(o), CouponBean.class);


                callBack.requestSuccess(couponBeans, 5);

            }

            @Override
            public void onFailed(Object o)
            {
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
    public void sendRrivilegeRecommendRequest(String cityId, int pageStart)
    {

        new RecommendRequest(cityId, String.valueOf(pageStart += AppConstant.MAX_PAGE_NUM), String.valueOf(AppConstant.MAX_PAGE_NUM)).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {

                List<DiscountRecommendBean> datas = JSON.parseArray(String.valueOf(o), DiscountRecommendBean.class);

                callBack.requestSuccess(datas, 6);
            }

            @Override
            public void onFailed(Object o)
            {

                callBack.requestFailedDataCall(null, 6);


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
