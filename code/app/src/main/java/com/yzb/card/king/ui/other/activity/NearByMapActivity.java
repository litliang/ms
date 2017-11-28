package com.yzb.card.king.ui.other.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.app.fragment.PhoneDialogFragment;
import com.yzb.card.king.ui.app.interfaces.PoiSearchResultAdapter;
import com.yzb.card.king.ui.app.manager.PoiResultOverlayManager;
import com.yzb.card.king.ui.app.view.MaxMinHeightView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.other.adapter.NearByMapAdapter;
import com.yzb.card.king.util.BaiduMapUtil;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;

import java.io.Serializable;

/**
 * 周边地图模式；
 * <p/>
 * 需要传入坐标时，跳转代码如下：
 * intent.putExtra(NearByMapActivity.LOCATION, Location对象);
 * intent.putExtra(NearByMapActivity.CATEGORY, 分类的类型);
 * 交通，餐饮，娱乐，景点，购物，酒店  依次传入：0,1,2,3,4,5
 *
 * @author gengqiyun;
 * @date 2016.10.26
 */
public class NearByMapActivity extends BaseActivity implements View.OnClickListener
{
    public static final String LOCATION = "LOCATION";
    public static final String CATEGORY = "CATEGORY";

    private MapView mapView;
    private ImageView ivIndicator;
    private ListView listView;
    private MaxMinHeightView maxMinView;
    private LinearLayout panel_category;
    private NearByMapAdapter adapter;
    private PoiSearch mPoiSearch;
    private BaiduMap mBaiduMap;
    private static int curCategory = 0; //当前选择的类型；
    private int radius = 40000; //搜索半径；(单位：m)
    private View bottomView;//底部的布局；
    private boolean hasMapInit; //百度地图是否初始化完毕；
    private Location recvSingleLocation; //locationType=TYPE_SINGLE_LOCATION时，recvLocationList中的第一个；
    private int pageSize = 30; //百度地图每页的容量；

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_map);
        recvIntentArgs();
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(poiSearchResultListener);
        assignViews();
    }

    private void recvIntentArgs()
    {
        curCategory = getIntent().getIntExtra(CATEGORY, -1);
        Serializable locationObj = getIntent().getSerializableExtra(LOCATION);
        if (locationObj != null)
        {
            recvSingleLocation = (Location) locationObj;
            LogUtil.i("recvSingleLocation==" + JSON.toJSONString(recvSingleLocation));
        }
    }

    private void assignViews()
    {
        mapView = (MapView) findViewById(R.id.mapView);
        findViewById(R.id.ll_back).setOnClickListener(this);
        bottomView = findViewById(R.id.bottomView);
        maxMinView = (MaxMinHeightView) findViewById(R.id.maxMinView);
        maxMinView.setTag(0);

        ivIndicator = (ImageView) findViewById(R.id.iv_indicator);
        ivIndicator.setOnClickListener(this);
        ivIndicator.setTag(0);
        panel_category = (LinearLayout) findViewById(R.id.panel_category);
        initCategoryViews();

        listView = (ListView) findViewById(R.id.listView);
        adapter = new NearByMapAdapter(this);
        adapter.setCallBack(new NearByMapAdapter.ICallBack()
        {
            @Override
            public void dialNum(String phoneNum)
            {
                PhoneDialogFragment.getInstance().setPhoneNum(phoneNum)
                        .show(getSupportFragmentManager(), "");
            }

            @Override
            public void navigationMap(LatLng latLng)
            {
                mapNavigation(latLng);
            }

            @Override
            public void showPoiOnMap(LatLng location)
            {
                MapStatus mMapStatus = new MapStatus.Builder().target(location).zoom(15).build();
                //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                mBaiduMap.animateMapStatus(mMapStatusUpdate);
            }
        });
        listView.setAdapter(adapter);
        initBaiduMap();
    }

    /**
     * 地图加载完成的监听；
     */
    private BaiduMap.OnMapLoadedCallback mapLoadedCallback = new BaiduMap.OnMapLoadedCallback()
    {
        @Override
        public void onMapLoaded()
        {
            hasMapInit = true;
            mBaiduMap.clear();
            showSpecifLocation();
        }
    };

    /**
     * 打开地图导航；
     *
     * @param endLatLng 目的地坐标；
     */
    private void mapNavigation(LatLng endLatLng)
    {
        NaviParaOption naviParaOption = new NaviParaOption();
        naviParaOption.startPoint(recvSingleLocation == null ? GlobalApp.getCurLocation() : recvSingleLocation.getLatLng());
        naviParaOption.endPoint(endLatLng);
        try
        {
            BaiduMapNavigation.openBaiduMapNavi(naviParaOption, this);
        } catch (Exception exception)
        {
            toastCustom("您尚未安装百度地图app或app版本过低");
        }
    }

    private boolean hasInit; //页面是否初始化；

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);

        // 非点击美食等分类进入；隐藏底部的一些布局；只显示panel_category；
        if (!hasInit && curCategory < 0)
        {
            ivIndicator.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
            bottomView.getLayoutParams().height = panel_category.getHeight();
            hasInit = true;
        }

        //上个页面选择了相应的类型；
        if (curCategory >= 0)
        {
            ivIndicator.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);
            maxMinView.getLayoutParams().height = bottomView.getLayoutParams().height = MaxMinHeightView.MIN_HEIGHT;

            //延迟，等待地图初始化完毕；
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    panel_category.getChildAt(curCategory).performClick();
                }
            }, 400);
        }
    }

    /**
     * 初始化百度地图相关；
     */
    private void initBaiduMap()
    {
        mBaiduMap = mapView.getMap();
        mapView.showZoomControls(false); // 隐藏默认缩放控制器；
        //设置最大和最小缩放级别；
        mBaiduMap.setMaxAndMinZoomLevel(BaiduMapUtil.MAX_ZOOM_LEVEL, BaiduMapUtil.MIN_ZOOM_LEVEL);
        ////普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setOnMapLoadedCallback(mapLoadedCallback);
    }

    public void showInfoWindow()
    {
        mBaiduMap.hideInfoWindow();
        if (recvSingleLocation != null)
        {
            String hotelName = recvSingleLocation.msg;
            String streetNumber = recvSingleLocation.streetNumber;

            String km = BaiduMapUtil.getFormatDistanceKm(GlobalApp.getCurLocation(), recvSingleLocation.getLatLng());
            String distance = "(距离您" + km + "公里)";
            View windowView = getLayoutInflater().inflate(R.layout.map_mylocation_window, null);
            TextView tvContent = (TextView) windowView.findViewById(R.id.tvContent);
            int sp18 = CommonUtil.sp2px(this, 18);
            int sp14 = CommonUtil.sp2px(this, 14);

            String orignalStr = hotelName + "\n" + streetNumber + distance;
            Spannable wordtoSpan = new SpannableString(orignalStr);
            wordtoSpan.setSpan(new AbsoluteSizeSpan(sp18), 0, hotelName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            wordtoSpan.setSpan(new AbsoluteSizeSpan(sp14), hotelName.length() + 1, orignalStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvContent.setText(wordtoSpan);

            //创建InfoWindow, 传入view, 地理坐标y轴偏移量
            InfoWindow mInfoWindow = new InfoWindow(windowView, recvSingleLocation.getLatLng(), -52);
            //显示InfoWindow
            mBaiduMap.showInfoWindow(mInfoWindow);
        }
    }

    /**
     * 初始化分类views；
     */
    private void initCategoryViews()
    {
        final String[] categoryArray = getResources().getStringArray(R.array.nearby_map_category);
        TextView itemView;
        for (int i = 0; i < categoryArray.length; i++)
        {
            itemView = (TextView) panel_category.getChildAt(i);
            itemView.setText(categoryArray[i]);

            final int finalI = i;
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //页面初始化ListView没有显示，此时需要展开ListView;
                    if (listView.getVisibility() == View.GONE)
                    {
                        ivIndicator.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.VISIBLE);
                        maxMinView.startTranslate(MaxMinHeightView.OP_TYPE_UP_FROM_BOTTOM);
                        bottomView.getLayoutParams().height = MaxMinHeightView.MIN_HEIGHT;
                        bottomView.requestLayout();
                    }
                    curCategory = finalI;
                    selectSpecView(finalI);
                    changeData(categoryArray[finalI]);
                }
            });
        }
    }

    /**
     * 请求百度数据；
     *
     * @param searchkey; 搜索关键字；
     */
    private void changeData(String searchkey)
    {
        LatLng locatioin = recvSingleLocation == null ? GlobalApp.getCurLocation() : recvSingleLocation.getLatLng();

        LogUtil.i("搜索关键字==" + searchkey);
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption().
                keyword(searchkey).
                sortType(PoiSortType.distance_from_near_to_far).
                location(locatioin)
                .radius(radius).pageNum(1).pageCapacity(pageSize);
        mPoiSearch.searchNearby(nearbySearchOption);
    }

    /**
     * 获取POI搜索结果，包括searchInCity，searchNearby，searchInBound返回的搜索结果
     */
    private PoiSearchResultAdapter poiSearchResultListener = new PoiSearchResultAdapter()
    {
        @Override
        public void getPoiResult(PoiResult poiResult)
        {
            LogUtil.i("onGetPoiResult=" + JSON.toJSONString(poiResult));
            if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND)
            {
                toastCustom("未找到结果");
                return;
            }
            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR)
            {
                adapter.setCurCategory(curCategory);
                adapter.setPoiResult(poiResult);
                mBaiduMap.clear();
                PoiResultOverlayManager overlayManager = new PoiResultOverlayManager(NearByMapActivity.this, mBaiduMap)
                {
                    @Override
                    protected int getLocationIconResId()
                    {
                        return R.mipmap.icon_hotel_my_location;
                    }

                    @Override
                    public boolean onPoiClick(Marker marker)
                    {
//                        marker.setIcon(BitmapDescriptorFactory.fromResource()); 改变某个poi的图片；
                        scrollToCenterPosition(marker.getPosition());
                        selectListViewItem(marker);
                        return true;
                    }

                    @Override
                    public int getOverLayResId()
                    {
                        return R.mipmap.icon_map_overlay;
                    }
                };

                if (recvSingleLocation != null)
                {
                    overlayManager.setLocation(recvSingleLocation.getLatLng());
                }
                mBaiduMap.setOnMarkerClickListener(overlayManager);
                overlayManager.setData(poiResult);
                overlayManager.addToMap();
                overlayManager.zoomToSpan();

                if (recvSingleLocation != null)
                {
                    showInfoWindow();
                }
            }
        }
    };

    /**
     * 使某个坐标移动到视野中央；
     *
     * @param position
     */
    private void scrollToCenterPosition(LatLng position)
    {
        MapStatus mMapStatus = new MapStatus.Builder().target(position).zoom(15).build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
    }

    /**
     * 根据marker的uid；
     *
     * @param marker
     */
    private void selectListViewItem(Marker marker)
    {
        Bundle bundle = marker.getExtraInfo();
        if (bundle != null && bundle.containsKey("uid"))
        {
            int pos = adapter.getClickItemPosition(bundle.getString("uid"));
            LogUtil.i("点击的position=" + pos);

            if (pos >= 0 && pos < adapter.getCount())
            {
                adapter.selectItem(pos);
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO)
                {
                    listView.smoothScrollToPosition(pos);
                } else
                {
                    listView.setSelection(pos);
                }
            }
        }
    }

    private void showSpecifLocation()
    {
        BaiduMapUtil.zoomIn(mBaiduMap, 15);
        PoiResultOverlayManager overlayManager = new PoiResultOverlayManager(NearByMapActivity.this, mBaiduMap)
        {
            @Override
            protected int getLocationIconResId()
            {
                return R.mipmap.icon_hotel_my_location;
            }
        };
        if (recvSingleLocation != null)
        {
            overlayManager.setLocation(recvSingleLocation.getLatLng());
        }
        overlayManager.addToMap();
        overlayManager.zoomToSpan();
        showInfoWindow();
    }

    /**
     * 选中分类中特定的view；
     *
     * @param categoryIndex
     */
    private void selectSpecView(int categoryIndex)
    {
        int childCount = panel_category.getChildCount();
        for (int i = 0; i < childCount; i++)
        {
            panel_category.getChildAt(i).setSelected(categoryIndex == i ? true : false);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ll_back:
                finish();
                break;
            case R.id.iv_indicator: //指示器；
                int tag = Integer.parseInt(String.valueOf(maxMinView.getTag()));
                maxMinView.startTranslate(tag == 0 ? MaxMinHeightView.OP_TYPE_UP : MaxMinHeightView.OP_TYPE_DOWN);
                maxMinView.setTag(tag == 0 ? 1 : 0);
                updateIndicator();
                break;
        }
    }

    /**
     * 更新指示器背景
     */
    private void updateIndicator()
    {
        //当前状态，0:向上；1：向下；
        int tag = Integer.parseInt(String.valueOf(ivIndicator.getTag()));
        ivIndicator.setImageResource(tag == 0 ? R.mipmap.icon_nearby_map_nava_down : R.mipmap.icon_nearby_map_nava_up);
        ivIndicator.setTag(tag == 0 ? 1 : 0);
    }

    @Override
    protected int getColorResId()
    {
        return R.color.color_black;
    }

}
