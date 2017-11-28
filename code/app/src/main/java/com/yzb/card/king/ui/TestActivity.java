package com.yzb.card.king.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.AppBaseDataBean;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.app.manager.DrivingRouteOverlayManager;
import com.yzb.card.king.ui.app.manager.PoiOverlayManager;
import com.yzb.card.king.ui.appwidget.appdialog.HotelProductRoomInfoFragmentDialog;
import com.yzb.card.king.ui.appwidget.appdialog.MyTransitDialog;
import com.yzb.card.king.ui.base.BaseFragmentActivity;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.hotel.adapter.RouteLineAdapter;
import com.yzb.card.king.util.BaiduMapUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.encryption.RsaUtil;
import com.yzb.card.king.util.photoutils.BitmapUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.body.RequestBody;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/6/16
 * 描  述：
 */
public class TestActivity extends Activity {//BaseFragmentActivity


//    private ImageView iv;
//
//    private LinearLayout llMap;
//
//    private MapView mMapView;
//
//    protected BaiduMap baiduMap;
//
//    private boolean flag = true;

    private TextView tvResponse;

    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tvResponse = (TextView) findViewById(R.id.tvResponse);

        et = (EditText) findViewById(R.id.et);

        findViewById(R.id.llGetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.llButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  testAction();
            }
        });


        //setContentView(R.layout.activity_hotel_other_product_order);
//        iv = (ImageView) findViewById(R.id.iv);
//        mMapView = (MapView) findViewById(R.id.map);
//
//        llMap = (LinearLayout) findViewById(R.id.llMap);
//
//        baidMapInit();
//        routePlanSearchInit();

    }


//
//    private void testAction() {
//
//        String etStr = et.getText().toString();
//
//        String url = "http://17k33q0887.imwork.net:29343/ecologic/app.userLogin!appUserLogin.sh";
//
//
//        RequestParams params = new RequestParams(url);
//
//        params.setHeader("Accept", "application/json");
//        params.setHeader("Content-type", "application/json;charset=UTF-8");
//
//
////        params.addHeader("account","13865903888");
////
////        params.addHeader("password","B9C6634B2E9FBBEAABE29F8F6972E55D");
//
//        params.addBodyParameter("account","13865903888");
//
//        params.addBodyParameter("password","B9C6634B2E9FBBEAABE29F8F6972E55D");
//
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String s) {
//
//
//                ToastUtil.i(TestActivity.this, "success=" + s);
//
//
//                String aa = tvResponse.getText().toString() + "\n";
//
//
//                tvResponse.setText(aa + "------onSuccess--------------" + s);
//
//                LogUtil.e("success=" + s);
//            }
//
//            @Override
//            public void onError(Throwable throwable, boolean b) {
//
//                LogUtil.e("------onError--------------" + throwable.getMessage());
//
//                ToastUtil.i(TestActivity.this, "onError=" + throwable.getMessage());
//
//                String aa = tvResponse.getText().toString() + "\n";
//
//
//                tvResponse.setText(aa + "------onError--------------" + throwable.getMessage());
//            }
//
//            @Override
//            public void onCancelled(CancelledException e) {
//                LogUtil.e("------onCancelled--------------" + e.getMessage());
//                ToastUtil.i(TestActivity.this, "onCancelled=" + e.getMessage());
//
//                String aa = tvResponse.getText().toString() + "\n";
//
//
//                tvResponse.setText(aa + "------onCancelled--------------" + e.getMessage());
//            }
//
//            @Override
//            public void onFinished() {
//
//                LogUtil.e("------onFinished--------------");
//                ToastUtil.i(TestActivity.this, "onFinished");
//
//                String aa = tvResponse.getText().toString() + "\n";
//
//
//                tvResponse.setText(aa + "------onFinished--------------");
//            }
//        });
//
//    }

//    //此方法只是关闭软键盘
//    private void hintKbTwo() {
//        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        if(imm.isActive()&&getCurrentFocus()!=null){
//            if (getCurrentFocus().getWindowToken()!=null) {
//                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        }
//    }


//    //此方法，如果显示则隐藏，如果隐藏则显示
//    private void hintKbOne() {
//        InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
//// 得到InputMethodManager的实例
//        if (imm.isActive()) {
//            // 如果开启
//            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
//                    InputMethodManager.HIDE_NOT_ALWAYS);
//
//        }
//    }

//    private RoutePlanSearch routePlanSearch;
//
//    /**
//     * 路线规划初始化；
//     */
//    private void routePlanSearchInit()
//    {
//        routePlanSearch = RoutePlanSearch.newInstance();
//
//        routePlanSearch.setOnGetRoutePlanResultListener(routePlanResultListener);
//    }


//    public DrivingRoutePlanOption getDrivingRoutePlanOption()
//    {
//        DrivingRoutePlanOption routePlanOption = new DrivingRoutePlanOption();
//        //驾车策略；
//        return routePlanOption
//                .from(getFromPlanNode())
//                .policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_DIS_FIRST).
//                        to(getToPlanNode());
//    }
//
//    private void baidMapInit()
//    {
//
//        mMapView.showZoomControls(false); // 隐藏默认缩放控制器；
//        baiduMap = mMapView.getMap();
//
//        UiSettings settings = baiduMap.getUiSettings();
//        settings.setAllGesturesEnabled(false);   //关闭一切手势操作
//        settings.setOverlookingGesturesEnabled(false);//屏蔽双指下拉时变成3D地图
//        //  settings.setRotationGesturesEnabled(false);//屏蔽旋转
//        settings.setZoomGesturesEnabled(false);//获取是否允许缩放手势返回:是否允许缩放手势
//        //设置最大和最小缩放级别；
//        baiduMap.setMaxAndMinZoomLevel(BaiduMapUtil.MAX_ZOOM_LEVEL, BaiduMapUtil.MIN_ZOOM_LEVEL);
//        ////普通地图
//        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//        baiduMap.setOnMapLoadedCallback(mapLoadedCallback);
//
//        baiduMap.setOnMapRenderCallbadk(new BaiduMap.OnMapRenderCallback() {
//            @Override
//            public void onMapRenderFinished()
//            {
////                llMap.setDrawingCacheEnabled(true);
////
////                llMap.buildDrawingCache();
////
////                Bitmap bitmap = llMap.getDrawingCache();
////
////                iv.setImageBitmap(bitmap);
//
//
////                AppBaseDataBean b = GlobalApp.getInstance().getAppBaseDataBean();
////
////                int w = b.getScreenWith();
////
////                int h = b.getScreenHeight();
////
////                Rect rect = new Rect(0, 0, w, h/2);
////                baiduMap.snapshotScope(rect, new BaiduMap.SnapshotReadyCallback() {
////
////                    @Override
////                    public void onSnapshotReady(Bitmap snapshot) {
////
////                        LogUtil.e("ABCDEFG","-------onSnapshotReady---------------");
////
////                        iv.setImageBitmap(snapshot);
////                    }
////
////                });
//
//                //  if(flag){
//
//                flag = false;
//                baiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
//                    public void onSnapshotReady(Bitmap snapshot)
//                    {
//                        iv.setImageBitmap(snapshot);
//                        LogUtil.e("ABCDEFG", "-------onSnapshotReady---------------");
//                    }
//                });
//                //   }
//
//
//                LogUtil.e("ABCDEFG", "-------onMapRenderFinished---------------");
//
//            }
//        });
//
//    }

//    /**
//     * 地图加载的监听
//     */
//    private BaiduMap.OnMapLoadedCallback mapLoadedCallback = new BaiduMap.OnMapLoadedCallback() {
//
//
//        @Override
//        public void onMapLoaded()
//        {
//            baiduMap.clear();
//            showMyLocationWindow();
//            //发起驾车路线规划
//            routePlanSearch.drivingSearch(getDrivingRoutePlanOption());
//        }
//
//
//    };

//    /**
//     * 显示我的位置；
//     */
//    private void showMyLocationWindow()
//    {
//        PoiOverlayManager overlayManager = new PoiOverlayManager(this, baiduMap);
//        overlayManager.setLocationIconResId(R.mipmap.icon_hotel_my_location);
//        overlayManager.addToMap();
//        overlayManager.zoomToSpan();
//        overlayManager.showHotelMyLocationWindow();
//    }


    /**
     * 获取出发结点；
     */
    protected PlanNode getFromPlanNode() {
        Location city = GlobalApp.getPositionedCity();
        return PlanNode.withLocation(new LatLng(city.latitude, city.longitude));
    }

    /**
     * 获取目的地结点；
     */
    protected PlanNode getToPlanNode() {
        double lat = 31.240382;

        double lng = 121.482644;

        return PlanNode.withLocation(new LatLng(lat, lng));
    }
//
//    private OnGetRoutePlanResultListener routePlanResultListener = new OnGetRoutePlanResultListener() {
//        //步行；
//        @Override
//        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult)
//        {
//            if (walkingRouteResult == null || walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
//                ToastUtil.i(TestActivity.this, "没有找到结果");
//                LogUtil.i("步行路线==" + walkingRouteResult.error);
//                return;
//            }
//            if (walkingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
//                LogUtil.i("步行路线==" + walkingRouteResult.error);
//                ToastUtil.i(TestActivity.this, "没有找到结果");
//                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
//                // walkingRouteResult.getSuggestAddrInfo();
//                return;
//            }
//            if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
//                //   getWalkingRouteResult(walkingRouteResult);
//            }
//        }
//
//        //公交；
//        @Override
//        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult)
//        {
//            if (transitRouteResult == null || transitRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
//                ToastUtil.i(TestActivity.this, "没有找到结果");
//                return;
//            }
//            if (transitRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
//                ToastUtil.i(TestActivity.this, "没有找到结果");
//                return;
//            }
//            if (transitRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
//                // getTransitRouteResult(transitRouteResult);
//            }
//        }
//
//        @Override
//        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult)
//        {
//
//        }
//
//        //驾车；
//        @Override
//        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult)
//        {
//            if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
//                ToastUtil.i(TestActivity.this, "没有找到结果");
//                return;
//            }
//            if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
//                ToastUtil.i(TestActivity.this, "没有找到结果");
//                return;
//            }
//            if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
//                getDrivingRouteResult(drivingRouteResult);
//            }
//        }
//
//        @Override
//        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult)
//        {
//
//        }
//
//        @Override
//        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult)
//        {
//            if (bikingRouteResult == null || bikingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
//                ToastUtil.i(TestActivity.this, "没有找到结果");
//                return;
//            }
//            if (bikingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
//                ToastUtil.i(TestActivity.this, "没有找到结果");
//                return;
//            }
//            if (bikingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
//                //  getBikingRouteResult(bikingRouteResult);
//            }
//        }
//    };


//    protected void getDrivingRouteResult(DrivingRouteResult drivingRouteResult)
//    {
//        final List<DrivingRouteLine> routeLines = drivingRouteResult.getRouteLines();
//        //只有一个线路；
//        if (routeLines.size() == 1) {
//            DrivingRouteOverlayManager transitRouteOverlayManager = new DrivingRouteOverlayManager(TestActivity.this, baiduMap);
//            baiduMap.setOnMarkerClickListener(transitRouteOverlayManager);
//            transitRouteOverlayManager.setData(routeLines.get(0));
//            transitRouteOverlayManager.addToMap();
//            transitRouteOverlayManager.zoomToSpan();
//        } else if (routeLines.size() > 1) {
//            MyTransitDialog myTransitDlg = new MyTransitDialog(TestActivity.this, routeLines,
//                    RouteLineAdapter.Type.TRANSIT_ROUTE);
//            myTransitDlg.setOnDialogItemClickLinster(new MyTransitDialog.OnDialogItemClickListener() {
//                @Override
//                public void onItemClick(int position)
//                {
//                    DrivingRouteOverlayManager transitRouteOverlayManager = new DrivingRouteOverlayManager(TestActivity.this, baiduMap);
//                    baiduMap.setOnMarkerClickListener(transitRouteOverlayManager);
//                    transitRouteOverlayManager.setData(routeLines.get(position));
//                    transitRouteOverlayManager.addToMap();
//                    transitRouteOverlayManager.zoomToSpan();
//                }
//            });
//            myTransitDlg.show();
//        }
//    }
}

