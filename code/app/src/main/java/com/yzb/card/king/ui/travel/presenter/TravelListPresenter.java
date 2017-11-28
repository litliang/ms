package com.yzb.card.king.ui.travel.presenter;

import android.text.TextUtils;

import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.manage.TravelDataManager;
import com.yzb.card.king.ui.travel.model.ITravelList;
import com.yzb.card.king.ui.travel.model.TravelListImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * 类  名：旅游列表观察者
 * 作  者：Li JianQiang
 * 日  期：2016/8/30
 * 描  述：
 */
public class TravelListPresenter implements DataCallBack
{

    private BaseViewLayerInterface travelListView;

    private ITravelList iTravelList;

    public TravelListPresenter(BaseViewLayerInterface travelListView)
    {
        this.travelListView = travelListView;

        iTravelList = new TravelListImpl();

        iTravelList.setDataCallBack(this);

    }

    /**
     * 获取旅游分类信息
     */
    public void getTravelScheduleData(String depCityId)
    {

        TravelDataManager manager = TravelDataManager.getInstance();

        //检查是否有分类缓存数据，如果城市信息一样则直接使用缓存数据，不一样则重新请求分类缓存数据
        if (manager.getO() == null || (!TextUtils.isEmpty(manager.getDepCityId()) && !manager.getDepCityId().equals(depCityId)))
        {
            iTravelList.sendQueryTravelBaseInfoRequest("1", null, depCityId, manager.getArrType(), manager.getArrDetailId(), manager.getStartTravelDate(), manager.getEndTravelDate());

        } else
        {

            travelListView.callSuccessViewLogic(manager.getO(), ITravelList.QUERY_TRAVEL_BASEINFO_URL);
        }

    }

    /**
     * 根据分类信息获取旅游产品信息
     *
     * @param ginsengBean
     */
    public void getTavelLineProductByClass(TravelDataManager.TravleListRequestBean ginsengBean)
    {

        Map<String, Object> param = new HashMap<String, Object>();
        //出发城市id
        param.put("depCityId", ginsengBean.getDepCityId());

        param.put("arrType", ginsengBean.getArrType());

        param.put("arrDetailId", ginsengBean.getArrDetailId());

        param.put("productType", ginsengBean.getProductType());

        param.put("sort", ginsengBean.getSort());

        param.put("pageStart", ginsengBean.getPageStart());

        param.put("pageSize", ginsengBean.getPageSize());

        param.put("bankStatus", ginsengBean.getBankStatus());

        if (!TextUtils.isEmpty(ginsengBean.getAgentIds()))
        {

            param.put("agentIds", ginsengBean.getAgentIds());
        }

        if (!TextUtils.isEmpty(ginsengBean.getLabelIds()))
        {
            param.put("labelIds", ginsengBean.getLabelIds());
        }
        if (!TextUtils.isEmpty(ginsengBean.getMinPrice()))
        {
            param.put("minPrice", ginsengBean.getMinPrice());
        }
        if (!TextUtils.isEmpty(ginsengBean.getMaxPrice()))
        {
            param.put("maxPrice", ginsengBean.getMaxPrice());
        }

        if (!TextUtils.isEmpty(ginsengBean.getMinTravelDay()))
        {
            param.put("minTravelDay", ginsengBean.getMinTravelDay());
        }
        if (!TextUtils.isEmpty(ginsengBean.getMaxTravelDay()))
        {
            param.put("maxTravelDay", ginsengBean.getMaxTravelDay());
        }


        if (!TextUtils.isEmpty(ginsengBean.getStartTravelDate()))
        {
            param.put("startTravelDates", ginsengBean.getStartTravelDate());
        }

        if (!TextUtils.isEmpty(ginsengBean.getEndTravelDate()))
        {
            param.put("endTravelDates", ginsengBean.getEndTravelDate());
        }

        iTravelList.sendQueryTravelProductRequest(param);
    }


    @Override
    public void requestSuccess(Object o, int type)
    {
        if (type == ITravelList.QUERY_TRAVEL_BASEINFO_URL)
        {//旅游分类信息

            // List<TravelCategorySBean> list = JSONArray.parseArray(o+"",TravelCategorySBean.class);
            travelListView.callSuccessViewLogic(o, type);
            //缓存数据
            TravelDataManager.getInstance().setO(o);

        } else if (type == ITravelList.QUERY_TRAVEL_PRODUCT_URL)
        {

            travelListView.callSuccessViewLogic(o, type);
        }

    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if (type == ITravelList.QUERY_TRAVEL_BASEINFO_URL)
        {//旅游分类信息

            travelListView.callFailedViewLogic(o, type);

        } else if (type == ITravelList.QUERY_TRAVEL_PRODUCT_URL)
        {

            String str = o + "";
            if (HttpConstant.chechNoInfo(str))
            {

                travelListView.callSuccessViewLogic(null, type);
            } else
            {

                travelListView.callFailedViewLogic(o, type);
            }
        }
    }
}
