package com.yzb.card.king.ui.app.manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.util.BaiduMapUtil;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：显示和管理多个Overlay的基类
 *
 * @author:gengqiyun
 * @date: 2016/7/21
 */
public abstract class OverlayManager implements BaiduMap.OnMarkerClickListener, BaiduMap.OnPolylineClickListener
{
    public static final java.lang.String IS_MY_LOCATION = "IS_MY_LOCATION";
    protected List<Overlay> mOverlayList;
    protected BaiduMap mBaiduMap = null;
    protected List<OverlayOptions> mOverlayOptionList = null;
    protected Context mContext;

    protected LatLng recvLocation; //接收到的坐标；
    private int locationIconResId = R.mipmap.icon_hotel_my_location;

    /**
     * 手动设置要显示的坐标位置；
     *
     * @param recvLocation
     */
    public void setLocation(LatLng recvLocation)
    {
        this.recvLocation = recvLocation;
    }

    public OverlayManager(Context context, BaiduMap baiduMap)
    {
        mBaiduMap = baiduMap;
        mContext = context;
        if (mOverlayOptionList == null)
        {
            mOverlayOptionList = new ArrayList<>();
        }
        if (mOverlayList == null)
        {
            mOverlayList = new ArrayList<>();
        }
    }

    /**
     * 覆写此方法设置要管理的Overlay列表
     *
     * @return 管理的Overlay列表
     */
    public abstract List<OverlayOptions> getOverlayOptions();

    /**
     * 将所有Overlay 添加到地图上
     */
    public final void addToMap()
    {
        if (mBaiduMap == null)
        {
            return;
        }
        removeFromMap();
        mBaiduMap.hideInfoWindow();

        List<OverlayOptions> overlayOptions = getOverlayOptions();
        if (overlayOptions != null)
        {
            mOverlayOptionList.addAll(overlayOptions);
        }

        for (OverlayOptions option : mOverlayOptionList)
        {
            mOverlayList.add(mBaiduMap.addOverlay(option));
        }

        mOverlayList.add(getMyLocationOverlay());
    }

    /**
     * 添加我的位置；
     */
    protected Overlay getMyLocationOverlay()
    {
        LatLng myLocation = recvLocation == null ? GlobalApp.getCurLocation() : recvLocation;
        LogUtil.i("我的位置坐标latitude==" + myLocation.latitude + ",longitude=" + myLocation.longitude);

        MarkerOptions markerOptions = new MarkerOptions().position(myLocation)
//                animateType(MarkerOptions.MarkerAnimateType.grow)
//                .period(100)
//                .alpha(1.0f)
                .extraInfo(BaiduMapUtil.getBundle(IS_MY_LOCATION, true))
                .icon(BitmapDescriptorFactory.fromResource(getLocationIconResId()));

        return mBaiduMap.addOverlay(markerOptions);
    }

    protected int getLocationIconResId()
    {
        return locationIconResId;
    }

    public void setLocationIconResId(int locationIconResId)
    {
        this.locationIconResId = locationIconResId;
    }

    /**
     * 将所有Overlay 从 地图上移除；
     */
    public final void removeFromMap()
    {
        if (mBaiduMap == null)
        {
            return;
        }
        for (Overlay marker : mOverlayList)
        {
            marker.remove();
        }
        mOverlayOptionList.clear();
        mOverlayList.clear();

    }

    /**
     * 缩放地图，使所有Overlay都在合适的视野内
     * 注： 该方法只对Marker类型的overlay有效
     */
    public void zoomToSpan()
    {
        if (mBaiduMap == null)
        {
            return;
        }
        LogUtil.i("overlayList大小==" + mOverlayList);

        if (mOverlayList.size() > 0)
        {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Overlay overlay : mOverlayList)
            {
                // polyline 中的点可能太多，只按marker缩放
                if (overlay instanceof Marker)
                {
                    builder.include(((Marker) overlay).getPosition());
                }
            }
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(builder.build()));
        }
    }


    /**
     * 显示酒店样式的我的window;
     */
    public void showHotelMyLocationWindow()
    {
        if (mBaiduMap == null || mContext == null) return;
        mBaiduMap.hideInfoWindow();

        View windowView = LayoutInflater.from(mContext).inflate(R.layout.map_hotel_location_window, null);
        TextView tvLocation = (TextView) windowView.findViewById(R.id.tvLocation);
        Location location = GlobalApp.getPositionedCity();
        tvLocation.setText(location.getHandleBaiduLocationDescribe());

        //创建InfoWindow , 传入 view，地理坐标y 轴偏移量
        InfoWindow mInfoWindow = new InfoWindow(windowView, location.getLatLng(), -55);
        //显示InfoWindow
        mBaiduMap.showInfoWindow(mInfoWindow);
    }
}
