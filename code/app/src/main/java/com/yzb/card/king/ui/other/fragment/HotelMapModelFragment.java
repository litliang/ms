package com.yzb.card.king.ui.other.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.HotelMapProductObjectBean;
import com.yzb.card.king.bean.hotel.HotelParam;
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.app.interfaces.MapStatusChangeAdapter;
import com.yzb.card.king.ui.app.manager.PoiOverlayManager;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.bean.HotelListBean;
import com.yzb.card.king.ui.discount.bean.StoreBean;
import com.yzb.card.king.ui.discount.fragment.BaseFragment;
import com.yzb.card.king.ui.hotel.HotelLogicManager;
import com.yzb.card.king.ui.hotel.activtiy.HotelProductInfoActivity;
import com.yzb.card.king.ui.hotel.persenter.HotelProductListPresenter;
import com.yzb.card.king.ui.hotel.persenter.impl.HotelMapPresenter;
import com.yzb.card.king.ui.hotel.view.HotelMapView;
import com.yzb.card.king.util.BaiduMapUtil;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 酒店地图模式块；
 *
 * @author gengqiyun;
 * @date 2016.11.5
 */
public class HotelMapModelFragment extends BaseFragment implements View.OnClickListener,BaseViewLayerInterface
{
    private View tv_zoom_in; // 放大；
    private View tv_zoom_out; // 缩小；
    private MapView mMapView;

    private BaiduMap baiduMap;
    private List<StoreBean> storeBeans; // 门店列表；

    private static final String IS_MY_LOCATION = "IS_MY_LOCATION";


    private HotelProductListPresenter hotelProductListPresenter;
    private int pageStart = 0;
    private LatLng screenCenterLocation; //屏幕中心点的坐标；

    @Override
    public void callSuccessViewLogic(Object obj, int type)
    {
        if (obj != null)
        {
            //地图模式加载数据成功；
            List<HotelMapProductObjectBean> hotelListBeans = JSON.parseArray(obj+"",HotelMapProductObjectBean.class) ;
            LogUtil.i("地图模式加载数据成功,hotelListBeans=" + JSON.toJSONString(hotelListBeans));
            storeBeans = dataTypeConvert(hotelListBeans.size() > 20 ? hotelListBeans.subList(0, 20) : hotelListBeans);
            addToMap(storeBeans);
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        toastCustom(o+"");
    }

    public interface FragmentInteractionListener
    {
        /**
         * 获取参数列表；
         */
        Object getParamObj();
    }

    public HotelMapModelFragment()
    {
    }

    public static HotelMapModelFragment newInstance()
    {
        return new HotelMapModelFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_hotel_map_model, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView)
    {
        screenCenterLocation = GlobalApp.getCurLocation();
        hotelProductListPresenter = new HotelProductListPresenter(this);
        rootView.findViewById(R.id.iv_my_location).setOnClickListener(this);
        rootView.findViewById(R.id.ll_search).setOnClickListener(this);

        tv_zoom_in = rootView.findViewById(R.id.tv_zoom_in);
        tv_zoom_in.setOnClickListener(this);
        tv_zoom_out = rootView.findViewById(R.id.tv_zoom_out);
        tv_zoom_out.setOnClickListener(this);
        baidMapInit(rootView);
    }

    /**
     * Fragment刷新数据；
     *
     */
    public void refreshData()
    {
        HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

        productListParam.setAddrId(Integer.parseInt(cityId));

        productListParam.setAddrLevel(cityLevel);

        productListParam.setPageStart(pageStart);

        hotelProductListPresenter.sendHotelMapProductRequest(productListParam);
    }



    private PoiOverlayManager overlayManager;

    /**
     * 添加到地图；
     *
     * @param storeBeans
     */
    private void addToMap( List<StoreBean> storeBeans)
    {
        overlayManager = new PoiOverlayManager(getActivity(), baiduMap)
        {
            @Override
            public boolean onPoiClick(Marker marker)
            {
                markClickHandle(marker);
                return false;
            }
        };
        overlayManager.setLocationIconResId(R.mipmap.icon_hotel_my_location);
        baiduMap.clear();
        baiduMap.setOnMarkerClickListener(overlayManager);
        overlayManager.setData(storeBeans);
        overlayManager.addToMap();
        overlayManager.showHotelMyLocationWindow();

        //查询屏幕范围内的酒店 不进行缩放；
            overlayManager.zoomToSpan();
    }

    /**
     * marker点击的处理；
     *
     * @param marker
     */
    private void markClickHandle(Marker marker)
    {
        Bundle extraInfo = marker.getExtraInfo();
        // 不是我的位置；
        if (extraInfo != null && !extraInfo.getBoolean(IS_MY_LOCATION))
        {
            //商家id；
            String id = extraInfo.getString("id");
            LogUtil.i("点击的门店的id==" + id);
            if (!TextUtils.isEmpty(id))
            {
                Intent intent = new Intent(getActivity(), HotelProductInfoActivity.class);
                intent.putExtra("hotelId", id + "");
                startActivity(intent);
            }
        }
    }

    /**
     * 显示我的位置；
     */
    private void showMyLocationWindow()
    {
        baiduMap.hideInfoWindow();
        overlayManager = new PoiOverlayManager(getActivity(), baiduMap);
        overlayManager.setLocationIconResId(R.mipmap.icon_hotel_my_location);
        baiduMap.clear();
        overlayManager.addToMap();

        overlayManager.showHotelMyLocationWindow();
        overlayManager.zoomToSpan();
    }

    /**
     * HotelListBean-->StoreBean;
     *
     * @param hotelListBeans
     * @return
     */
    private List<StoreBean> dataTypeConvert(List<HotelMapProductObjectBean> hotelListBeans)
    {
        if (hotelListBeans != null && hotelListBeans.size() > 0)
        {
            List<StoreBean> storeBeans = new ArrayList<>();
            StoreBean storeItem;
            HotelMapProductObjectBean hotelItem;
            for (int i = 0; i < hotelListBeans.size(); i++)
            {
                storeItem = new StoreBean();
                hotelItem = hotelListBeans.get(i);
                storeItem.id = hotelItem.getHotelId() + "";
                storeItem.storeName = hotelItem.getHotelName();
                storeItem.vote = (float) hotelItem.getVote();
                storeItem.avgPrice = hotelItem.getMinPrice() + ""; //起价；
                storeItem.lat = hotelItem.getLat(); //纬度
                storeItem.lng = hotelItem.getLng(); //纬度
                storeBeans.add(storeItem);
            }
            return storeBeans;
        }
        return null;
    }

    /**
     * 百度地图初始化；
     *
     * @param rootView
     */
    private void baidMapInit(View rootView)
    {
        // 初始化搜索模块，注册搜索事件监听
        mMapView = (MapView) rootView.findViewById(R.id.bmapView);

        mMapView.showZoomControls(false); // 隐藏默认缩放控制器；
        baiduMap = mMapView.getMap();


        //设置最大和最小缩放级别；
        baiduMap.setMaxAndMinZoomLevel(BaiduMapUtil.MAX_ZOOM_LEVEL, BaiduMapUtil.MIN_ZOOM_LEVEL);
        ////普通地图
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //手势移动；
        baiduMap.setOnMapStatusChangeListener(new MapStatusChangeAdapter()
        {
            @Override
            public void statusChange(MapStatus mapStatus)
            {
                LogUtil.i("当前中心点坐标=" + JSON.toJSONString(mapStatus.target));
                screenCenterLocation = mapStatus.target;
            }
        });

        baiduMap.setOnMapLoadedCallback(mapLoadedCallback);
    }

    /**
     * 地图加载的监听
     */
    private BaiduMap.OnMapLoadedCallback mapLoadedCallback = new BaiduMap.OnMapLoadedCallback()
    {
        @Override
        public void onMapLoaded()
        {
            LogUtil.i("Map加载完毕了");
            baiduMap.clear();
            zoomIn(15);
            showMyLocationWindow();
            refreshData();
        }
    };



    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_my_location:
                BaiduMapUtil.showSpecificMark(baiduMap, GlobalApp.getCurLocation());
                break;
            case R.id.tv_zoom_in:  // 放大；
                zoomIn(-1);
                break;
            case R.id.tv_zoom_out:// 缩小；
                BaiduMapUtil.zoomOut(baiduMap);
                break;
            case R.id.ll_search:
                searchScreenStores();
                break;
        }
    }

    /**
     * 搜索屏幕范围内的所有门店；
     */
    private void searchScreenStores()
    {
        String distanceRightBottom = BaiduMapUtil.getFormatDistanceKm(screenCenterLocation,
                BaiduMapUtil.getScreenRightBottomLatLng(baiduMap, getActivity()));
        String distanceRightTop = BaiduMapUtil.getFormatDistanceKm(screenCenterLocation,
                BaiduMapUtil.getScreenRightTopLatLng(baiduMap, getActivity()));
        //取最大值；
        float maxDistance = Math.max(Float.parseFloat(distanceRightBottom), Float.parseFloat(distanceRightTop));
        LogUtil.i("distanceRightBottom=" + distanceRightBottom + ",distanceRightTop=" + distanceRightTop +
                "坐标间的最大距离==" + maxDistance + "km");
        if (maxDistance > 0)
        {
            refreshData();
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


    @Override
    public void onResume()
    {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mMapView.onDestroy();
    }
}
