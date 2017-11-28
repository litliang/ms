package com.yzb.card.king.ui.travel.fragment;

import android.os.Bundle;

import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.yzb.card.king.ui.app.manager.DrivingRouteOverlayManager;
import com.yzb.card.king.ui.appwidget.appdialog.MyTransitDialog;
import com.yzb.card.king.ui.hotel.adapter.RouteLineAdapter;

import java.util.List;

/**
 * @author gengqiyun
 * @date 2016.8.13
 * 驾车路线规划；
 */
public class DrivingRouteFragment extends RoutePlanBaseFragment {
    public DrivingRouteFragment() {
    }

    public static DrivingRouteFragment newInstance(String param1, String param2) {
        DrivingRouteFragment fragment = new DrivingRouteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void mapLoadFinished() {
        DrivingRoutePlanOption routePlanOption = getDrivingRoutePlanOption();
        //发起驾车路线规划
        routePlanSearch.drivingSearch(routePlanOption);
    }

    public DrivingRoutePlanOption getDrivingRoutePlanOption() {
        DrivingRoutePlanOption routePlanOption = new DrivingRoutePlanOption();
        //驾车策略；
        return routePlanOption
                .from(getFromPlanNode())
                .policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_DIS_FIRST).
                to(getToPlanNode());
    }


    @Override
    protected void getDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
        final List<DrivingRouteLine> routeLines = drivingRouteResult.getRouteLines();
        //只有一个线路；
        if (routeLines.size() == 1) {
            DrivingRouteOverlayManager transitRouteOverlayManager = new DrivingRouteOverlayManager(getActivity(), baiduMap);
            baiduMap.setOnMarkerClickListener(transitRouteOverlayManager);
            transitRouteOverlayManager.setData(routeLines.get(0));
            transitRouteOverlayManager.addToMap();
            transitRouteOverlayManager.zoomToSpan();
        } else if (routeLines.size() > 1) {
            MyTransitDialog myTransitDlg = new MyTransitDialog(getActivity(), routeLines,
                    RouteLineAdapter.Type.TRANSIT_ROUTE);
            myTransitDlg.setOnDialogItemClickLinster(new MyTransitDialog.OnDialogItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    DrivingRouteOverlayManager transitRouteOverlayManager = new DrivingRouteOverlayManager(getActivity(), baiduMap);
                    baiduMap.setOnMarkerClickListener(transitRouteOverlayManager);
                    transitRouteOverlayManager.setData(routeLines.get(position));
                    transitRouteOverlayManager.addToMap();
                    transitRouteOverlayManager.zoomToSpan();
                }
            });
            myTransitDlg.show();
        }
    }
}
