package com.yzb.card.king.ui.app.manager;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiResult;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：用于显示poi的overly
 *
 * @author:gengqiyun
 * @date: 2016/10/27
 */
public class PoiResultOverlayManager extends OverlayManager
{
    private static final int MAX_POI_SIZE = 20; // POI的最大数量；
    private PoiResult mPoiResult = null;
    private List<Location> locationList;

    public PoiResultOverlayManager(Context context, BaiduMap baiduMap)
    {
        super(context, baiduMap);
    }

    /**
     * 设置位置列表；
     *
     * @param recvLocationList
     */
    public void setLocationList(List<Location> recvLocationList)
    {
        this.locationList = recvLocationList;
    }

    /**
     * 设置POI数据
     *
     * @param poiResult
     */
    public void setData(PoiResult poiResult)
    {
        this.mPoiResult = poiResult;
    }

    @Override
    public List<OverlayOptions> getOverlayOptions()
    {
        if (mPoiResult != null && mPoiResult.getAllPoi() != null)
        {
            List<OverlayOptions> markerList = new ArrayList<>();
            int markerSize = 0;
            List<PoiInfo> poiInfos = mPoiResult.getAllPoi();
            PoiInfo poiInfo;
            for (int i = 0; i < poiInfos.size() && markerSize < MAX_POI_SIZE; i++)
            {
                poiInfo = poiInfos.get(i);
                if (poiInfo.location == null ||
                        (!TextUtils.isEmpty(poiInfo.name) && poiInfo.name.contains("银行")))
                {
                    continue;
                }
                markerSize++;
                Bundle bundle = new Bundle();
                bundle.putInt("index", i);
                bundle.putString("uid", poiInfo.uid);

                LogUtil.i("icon_mark1=" + getOverLayResId());
                int resId = getOverLayResId();
                if (resId > 0)
                {
                    markerList.add(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(resId)).extraInfo(bundle)
                            .position(poiInfo.location));
                } else
                {
                    if (markerSize > 0 && markerSize < 11)
                    {
                        String iconName = "Icon_mark" + markerSize + ".png";
                        markerList.add(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromAssetWithDpi(iconName)).extraInfo(bundle)
                                .position(poiInfo.location));
                    }
                }
            }
            return markerList;
        } else if (locationList != null && locationList.size() > 0) //用来在地图上标注自建的Location列表；
        {
            List<OverlayOptions> markerList = new ArrayList<>();
            int markerSize = 0;
            for (int i = 0; i < locationList.size()
                    && markerSize < MAX_POI_SIZE; i++)
            {
                if (locationList.get(i).getLatLng() == null)
                {
                    continue;
                }
                markerSize++;
                Bundle bundle = new Bundle();
                bundle.putInt("index", i);
                markerList.add(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark"
                                + markerSize + ".png")).extraInfo(bundle)
                        .position(locationList.get(i).getLatLng()));
            }
            return markerList;
        }
        return null;
    }

    public int getOverLayResId()
    {
        return 0;
    }

    @Override
    public boolean onMarkerClick(Marker marker)
    {
        if (!mOverlayList.contains(marker))
        {
            return false;
        }
        if (marker.getExtraInfo() != null)
        {
            return onPoiClick(marker);
        }
        return false;
    }

    /**
     * 覆写此方法以改变默认点击行为
     *
     * @param marker
     */
    public boolean onPoiClick(Marker marker)
    {
        Bundle extraInfo = marker.getExtraInfo();
        if (extraInfo != null)
        {
            boolean isMyLocation = extraInfo.getBoolean(IS_MY_LOCATION);
            //我的位置；
            if (isMyLocation)
            {
                //点击我的位置
            } else
            {
                //poi点击；
            }
        }
        return false;
    }

    @Override
    public boolean onPolylineClick(Polyline polyline)
    {
        return false;
    }


}
