package com.yzb.card.king.ui.travel.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.activity.PanoActivity;
import com.yzb.card.king.ui.app.manager.PoiOverlayManager;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.discount.fragment.BaseFragment;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.util.BaiduMapUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;

/**
 * @author gengqiyun
 * @date 2016.8.13
 * 路线规划  baseFragment；
 */
public abstract class RoutePlanBaseFragment extends BaseFragment implements View.OnClickListener
{
    protected static final String ARG_PARAM1 = "param1";
    protected static final String ARG_PARAM2 = "param2";

    protected String mParam1;
    protected String mParam2;
    protected OnFragmentInteractionListener mListener;
    protected MapView mMapView;
    protected BaiduMap baiduMap;
    private ImageView tv_zoom_in; // 放大；
    private ImageView tv_zoom_out; // 缩小；

    protected RoutePlanSearch routePlanSearch;//路线规划
    protected String fromCity; //出发城市；
    protected String fromArea; //出发区域；
    protected String toCity; //目的城市；
    protected String toArea; //目的区域；
    protected double lat; //纬度；
    protected double lng; //经度；
    protected String cityId; //商家的cityId；用于判断商家和用户是否同城；

    public RoutePlanBaseFragment()
    {
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_route_plan, container, false);
        init(view);
        return view;
    }

    private void init(View view)
    {
        if (mListener != null)
        {
            fromCity = mListener.getStartCity();
            fromArea = mListener.getStartArea();
            toCity = mListener.getEndCity();
            toArea = mListener.getEndArea();

            lng = mListener.getStoreLng();
            lat = mListener.getStoreLat();
            cityId = mListener.getStoreCityId();
        }
        view.findViewById(R.id.ll_my_location).setOnClickListener(this);

        tv_zoom_in = (ImageView) view.findViewById(R.id.tv_zoom_in);
        tv_zoom_in.setOnClickListener(this);
        tv_zoom_out = (ImageView) view.findViewById(R.id.tv_zoom_out);
        tv_zoom_out.setOnClickListener(this);


        view.findViewById(R.id.panelNav).setOnClickListener(this);
        view.findViewById(R.id.panelStreet).setOnClickListener(this);

        mMapView = (MapView) view.findViewById(R.id.mapView);
        baidMapInit();
        routePlanSearchInit();
    }

    /**
     * 获取出发结点；
     */
    protected PlanNode getFromPlanNode()
    {
        Location city = GlobalApp.getPositionedCity();
        return PlanNode.withLocation(new LatLng(city.latitude, city.longitude));
    }

    /**
     * 获取定位城市；
     */
    protected String getPositionCityName()
    {
        Location city = GlobalApp.getPositionedCity();
        return city.cityName;
    }

    /**
     * 获取目的地结点；
     */
    protected PlanNode getToPlanNode()
    {
        return PlanNode.withLocation(new LatLng(lat, lng));
    }

    /**
     * 地图加载的监听
     */
    private BaiduMap.OnMapLoadedCallback mapLoadedCallback = new BaiduMap.OnMapLoadedCallback()
    {
        @Override
        public void onMapLoaded()
        {
            baiduMap.clear();
            showMyLocationWindow();
            LogUtil.i("fromCity=" + fromCity + ",fromArea=" + fromArea + ",toCity=" + toCity + ",toArea=" + toArea);
            mapLoadFinished();
        }
    };

    /**
     * 显示我的位置；
     */
    private void showMyLocationWindow()
    {
        PoiOverlayManager overlayManager = new PoiOverlayManager(getActivity(), baiduMap);
        overlayManager.setLocationIconResId(R.mipmap.icon_hotel_my_location);
        overlayManager.addToMap();
        overlayManager.zoomToSpan();
        overlayManager.showHotelMyLocationWindow();
    }

    /**
     * 地图初始化完毕；
     */
    protected abstract void mapLoadFinished();


    /**
     * 路线规划初始化；
     */
    private void routePlanSearchInit()
    {
        routePlanSearch = RoutePlanSearch.newInstance();
        routePlanSearch.setOnGetRoutePlanResultListener(routePlanResultListener);
    }

    private OnGetRoutePlanResultListener routePlanResultListener = new OnGetRoutePlanResultListener()
    {
        //步行；
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult)
        {
            if (walkingRouteResult == null || walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR)
            {
                ToastUtil.i(getActivity(), "没有找到结果");
                LogUtil.i("步行路线==" + walkingRouteResult.error);
                return;
            }
            if (walkingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR)
            {
                LogUtil.i("步行路线==" + walkingRouteResult.error);
                ToastUtil.i(getActivity(), "没有找到结果");
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                // walkingRouteResult.getSuggestAddrInfo();
                return;
            }
            if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR)
            {
                getWalkingRouteResult(walkingRouteResult);
            }
        }

        //公交；
        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult)
        {
            if (transitRouteResult == null || transitRouteResult.error != SearchResult.ERRORNO.NO_ERROR)
            {
                ToastUtil.i(getActivity(), "没有找到结果");
                return;
            }
            if (transitRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR)
            {
                ToastUtil.i(getActivity(), "没有找到结果");
                return;
            }
            if (transitRouteResult.error == SearchResult.ERRORNO.NO_ERROR)
            {
                getTransitRouteResult(transitRouteResult);
            }
        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult)
        {

        }

        //驾车；
        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult)
        {
            if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR)
            {
                ToastUtil.i(getActivity(), "没有找到结果");
                return;
            }
            if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR)
            {
                ToastUtil.i(getActivity(), "没有找到结果");
                return;
            }
            if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR)
            {
                getDrivingRouteResult(drivingRouteResult);
            }
        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult)
        {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult)
        {
            if (bikingRouteResult == null || bikingRouteResult.error != SearchResult.ERRORNO.NO_ERROR)
            {
                ToastUtil.i(getActivity(), "没有找到结果");
                return;
            }
            if (bikingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR)
            {
                ToastUtil.i(getActivity(), "没有找到结果");
                return;
            }
            if (bikingRouteResult.error == SearchResult.ERRORNO.NO_ERROR)
            {
                getBikingRouteResult(bikingRouteResult);
            }
        }
    };

    /**
     * 驾车检索结果传播到子类；
     *
     * @param drivingRouteResult
     */
    protected void getDrivingRouteResult(DrivingRouteResult drivingRouteResult)
    {

    }

    /**
     * 步行检索结果传播到子类；
     *
     * @param walkingRouteResult
     */
    protected void getWalkingRouteResult(WalkingRouteResult walkingRouteResult)
    {

    }

    /**
     * 公交检索结果传播到子类；
     *
     * @param transitRouteResult
     */
    protected void getTransitRouteResult(TransitRouteResult transitRouteResult)
    {

    }

    /**
     * 骑行检索结果传播到子类；
     *
     * @param bikingRouteResult
     */
    protected void getBikingRouteResult(BikingRouteResult bikingRouteResult)
    {

    }

    /**
     * 百度地图初始化；
     */
    private void baidMapInit()
    {
        mMapView.showZoomControls(false); // 隐藏默认缩放控制器；
        baiduMap = mMapView.getMap();
        //设置最大和最小缩放级别；
        baiduMap.setMaxAndMinZoomLevel(BaiduMapUtil.MAX_ZOOM_LEVEL, BaiduMapUtil.MIN_ZOOM_LEVEL);
        ////普通地图
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        baiduMap.setOnMapLoadedCallback(mapLoadedCallback);
    }


    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_zoom_in:  // 放大；
                zoomIn(-1);
                break;
            case R.id.tv_zoom_out:// 缩小；
                BaiduMapUtil.zoomOut(baiduMap);
                break;
            case R.id.ll_my_location:
                showMyLocationWindow();
                break;
            case R.id.panelNav: //导航；
                mapNavigation(new LatLng(lat, lng));
                break;
            case R.id.panelStreet://街景；
                Intent intent = new Intent(getActivity(), PanoActivity.class);
                intent.putExtra("latlng", new LatLng(lat, lng));
                startActivity(intent);
                break;
        }
    }

    /**
     * 打开地图导航；
     *
     * @param endLatLng 目的地坐标；
     */
    private void mapNavigation(LatLng endLatLng)
    {
        NaviParaOption naviParaOption = new NaviParaOption();
        naviParaOption.startPoint(GlobalApp.getCurLocation());
        naviParaOption.endPoint(endLatLng);
        try
        {
            BaiduMapNavigation.openBaiduMapNavi(naviParaOption, getActivity());
        } catch (Exception exception)
        {
            toastCustom("请安装地图应用");
        }
    }

    /**
     * 放大；
     */
    private void zoomIn(float targetZoom)
    {
        LogUtil.i("当前地图的比例尺；==" + mMapView.getMapLevel());
        if (targetZoom >= BaiduMapUtil.MIN_ZOOM_LEVEL && targetZoom <= BaiduMapUtil.MAX_ZOOM_LEVEL)
        {
            baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(targetZoom));
            return;
        }
        MapStatus mapStatus = baiduMap.getMapStatus();
        float zoom = mapStatus.zoom; // 当前缩放级别；
        LogUtil.i("当前zoom==" + zoom);
        if (zoom < BaiduMapUtil.MAX_ZOOM_LEVEL)
        {
            baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(zoom + 1));
        }
    }

    public interface OnFragmentInteractionListener
    {
        /**
         * 获取商家的lng；
         *
         * @return
         */
        double getStoreLng();

        /**
         * 获取商家的lat；
         *
         * @return
         */
        double getStoreLat();

        /**
         * 获取商家的cityId；
         *
         * @return
         */
        String getStoreCityId();

        /**
         * 获取出发城市；
         *
         * @return
         */
        String getStartCity();

        /**
         * 获取出发城市的区域；
         *
         * @return
         */
        String getStartArea();

        /**
         * 获取目的城市；
         *
         * @return
         */
        String getEndCity();

        /**
         * 获取目的城市的区域；
         *
         * @return
         */
        String getEndArea();
    }
}
