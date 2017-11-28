package com.yzb.card.king.ui.travel.fragment;

import android.os.Bundle;

import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.yzb.card.king.ui.app.manager.BikingRouteOverlayManager;

import java.util.List;

/**
 * @author gengqiyun
 * @date 2016.8.15
 * 骑行路线规划；
 */
public class BikingRouteFragment extends RoutePlanBaseFragment {
    public BikingRouteFragment() {
    }

    public static BikingRouteFragment newInstance(String param1, String param2) {
        BikingRouteFragment fragment = new BikingRouteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void mapLoadFinished() {
        BikingRoutePlanOption routePlanOption = getBikingRoutePlanOption();
        //发起骑行路线规划
        routePlanSearch.bikingSearch(routePlanOption);
    }

    private BikingRoutePlanOption getBikingRoutePlanOption() {
        BikingRoutePlanOption routePlanOption = new BikingRoutePlanOption();
        //骑行策略；
        return routePlanOption
                .from(getFromPlanNode())
                .to(getToPlanNode());
    }

    @Override
    protected void getBikingRouteResult(BikingRouteResult bikingRouteResult) {
        final List<BikingRouteLine> routeLines = bikingRouteResult.getRouteLines();

        BikingRouteOverlayManager bikingRouteOverlayManager = new BikingRouteOverlayManager(getActivity(), baiduMap);
        baiduMap.setOnMarkerClickListener(bikingRouteOverlayManager);
        bikingRouteOverlayManager.setData(routeLines.get(0));
        bikingRouteOverlayManager.addToMap();
        bikingRouteOverlayManager.zoomToSpan();
    }

}
