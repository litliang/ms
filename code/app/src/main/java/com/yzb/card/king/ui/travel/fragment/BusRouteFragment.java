package com.yzb.card.king.ui.travel.fragment;

import android.os.Bundle;
import android.text.TextUtils;

import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.yzb.card.king.ui.appwidget.appdialog.MyTransitDialog;
import com.yzb.card.king.ui.hotel.adapter.RouteLineAdapter;
import com.yzb.card.king.ui.app.manager.TransitRouteOverlayManager;

import java.util.List;

/**
 * @author gengqiyun
 * @date 2016.8.13
 * 公交路线规划；
 */
public class BusRouteFragment extends RoutePlanBaseFragment {

    private  MyTransitDialog myTransitDlg;

    public BusRouteFragment() {
    }

    public static BusRouteFragment newInstance(String param1, String param2) {
        BusRouteFragment fragment = new BusRouteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 展示线路dialog
     */
    public void showRouteDialog(){

        if(myTransitDlg != null){
            myTransitDlg.show();
        }

    }

    @Override
    protected void mapLoadFinished() {
        //城市为空，无法规划公交；
        if (TextUtils.isEmpty(getPositionCityName())) {
            return;
        }
        TransitRoutePlanOption routePlanOption = getTransitRoutePlanOption();
        //发起Bus路线规划
        routePlanSearch.transitSearch(routePlanOption);
    }

    public TransitRoutePlanOption getTransitRoutePlanOption() {
        TransitRoutePlanOption routePlanOption = new TransitRoutePlanOption();
        return routePlanOption.
                policy(TransitRoutePlanOption.TransitPolicy.EBUS_TIME_FIRST).
                city(getPositionCityName()).
                from(getFromPlanNode()).
                to(getToPlanNode());
    }

    @Override
    protected void getTransitRouteResult(TransitRouteResult transitRouteResult) {
        final List<TransitRouteLine> routeLines = transitRouteResult.getRouteLines();
        //只有一个线路；
        if (routeLines.size() == 1) {
            TransitRouteOverlayManager transitRouteOverlayManager = new TransitRouteOverlayManager(getActivity(), baiduMap);
            baiduMap.setOnMarkerClickListener(transitRouteOverlayManager);
            transitRouteOverlayManager.setData(routeLines.get(0));
            transitRouteOverlayManager.addToMap();
            transitRouteOverlayManager.zoomToSpan();
        } else if (routeLines.size() > 1) {

             myTransitDlg = new MyTransitDialog(getActivity(), routeLines,
                    RouteLineAdapter.Type.TRANSIT_ROUTE);

            myTransitDlg.setOnDialogItemClickLinster(new MyTransitDialog.OnDialogItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    TransitRouteOverlayManager transitRouteOverlayManager = new TransitRouteOverlayManager(getActivity(), baiduMap);
                    baiduMap.setOnMarkerClickListener(transitRouteOverlayManager);
                    transitRouteOverlayManager.setData(routeLines.get(position));
                    transitRouteOverlayManager.addToMap();
                    transitRouteOverlayManager.zoomToSpan();
                }
            });
           // myTransitDlg.show();
        }
    }
}
