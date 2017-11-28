package com.yzb.card.king.ui.travel.fragment;

import android.os.Bundle;
import android.text.TextUtils;

import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.yzb.card.king.ui.app.manager.WalkingRouteOverlayManager;

import java.util.List;

/**
 * @author gengqiyun
 * @date 2016.8.13
 * 步行路线规划；
 */
public class WalkingRouteFragment extends RoutePlanBaseFragment
{
    public WalkingRouteFragment()
    {
    }

    public static WalkingRouteFragment newInstance(String param1, String param2)
    {
        WalkingRouteFragment fragment = new WalkingRouteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void mapLoadFinished()
    {
//        if (TextUtils.isEmpty(fromCity) || TextUtils.isEmpty(fromArea) || TextUtils.isEmpty(toCity) || TextUtils.isEmpty(toArea))
//        {
//            return;
//        }
        WalkingRoutePlanOption routePlanOption = getWalkingRoutePlanOption();
        //发起步行路线规划
        routePlanSearch.walkingSearch(routePlanOption);
    }

    private WalkingRoutePlanOption getWalkingRoutePlanOption()
    {
        WalkingRoutePlanOption routePlanOption = new WalkingRoutePlanOption();
//        PlanNode fromNode = PlanNode.withCityNameAndPlaceName(fromCity, fromArea);
//        PlanNode toNode = PlanNode.withCityNameAndPlaceName(toCity, toArea);
        //步行策略；
        return routePlanOption.from(getFromPlanNode()).to(getToPlanNode());
    }

    @Override
    protected void getWalkingRouteResult(WalkingRouteResult walkingRouteResult)
    {
        final List<WalkingRouteLine> routeLines = walkingRouteResult.getRouteLines();
        if (routeLines != null && routeLines.size() > 0)
        {
            WalkingRouteOverlayManager transitRouteOverlayManager = new WalkingRouteOverlayManager(getActivity(), baiduMap);
            baiduMap.setOnMarkerClickListener(transitRouteOverlayManager);
            transitRouteOverlayManager.setData(routeLines.get(0));
            transitRouteOverlayManager.addToMap();
            transitRouteOverlayManager.zoomToSpan();
        }
    }

}
