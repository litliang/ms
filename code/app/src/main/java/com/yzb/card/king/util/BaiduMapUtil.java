package com.yzb.card.king.util;


import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.utils.DistanceUtil;

/**
 * 类  名：百度地图工具类
 * 作  者：Li Yubing
 * 日  期：2016/7/12
 * 描  述：
 */
public class BaiduMapUtil
{
    public static final float MIN_ZOOM_LEVEL = 3.0f;// 最小缩放等级；
    public static final float MAX_ZOOM_LEVEL = 21.0f;// 最大缩放等级；
    public static final String DRIVING = "DRIVING";//驾车；

    /**
     * 计算两个LatLng点之间的距离；
     *
     * @param latLng
     * @param latLng1
     * @return
     */
    public static String getFormatDistance(LatLng latLng, LatLng latLng1)
    {
        double distance = DistanceUtil.getDistance(latLng, latLng1);
        if (distance < 1000)
        {
            return String.format("%.1f", distance) + "m";
        } else if (distance > 1000 && distance <= 100000)
        {
            return String.format("%.1f", distance / 1000) + "km";
        } else if (distance > 100000)
        {
            return ">100km";
        }
        return "";
    }

    /**
     * 计算两个LatLng点之间的距离； 单位km；
     *
     * @param latLng
     * @param latLng1
     * @return
     */
    public static String getFormatDistanceKm(LatLng latLng, LatLng latLng1)
    {
        double distance = DistanceUtil.getDistance(latLng, latLng1);
        return String.format("%.1f", distance / 1000.0f);
    }

    /**
     * 计算两个LatLng点之间的距离； 单位km；
     *
     * @param latLng
     * @param latLng1
     * @return
     */
    public static String getFormatDistanceKm3Point(LatLng latLng, LatLng latLng1)
    {
        double distance = DistanceUtil.getDistance(latLng, latLng1);
        return String.format("%.3f", distance / 1000.0f);
    }

    /**
     * 获取屏幕右下角的LatLng值；
     *
     * @param baiduMap
     * @param context
     * @return
     */
    public static LatLng getScreenRightBottomLatLng(BaiduMap baiduMap, Context context)
    {
        if (context != null)
        {
            Point point = new Point();
            point.x = CommonUtil.getScreenWidth(context);
            point.y = CommonUtil.getScreenHeight(context);
            return baiduMap.getProjection().fromScreenLocation(point);
        }
        return null;
    }

    /**
     * 获取屏幕右上角的LatLng值；
     *
     * @param baiduMap
     * @param context
     * @return
     */
    public static LatLng getScreenRightTopLatLng(BaiduMap baiduMap, Context context)
    {
        if (context != null)
        {
            Point point = new Point();
            point.x = CommonUtil.getScreenWidth(context);
            point.y = 0;
            return baiduMap.getProjection().fromScreenLocation(point);
        }
        return null;
    }

    /**
     * 获取屏幕左上角的LatLng值；
     *
     * @param baiduMap
     * @param context
     * @return
     */
    public static LatLng getScreenLeftTopLatLng(BaiduMap baiduMap, Context context)
    {
        if (context != null)
        {
            Point point = new Point();
            point.x = 0;
            point.y = 0;
            return baiduMap.getProjection().fromScreenLocation(point);
        }
        return null;
    }

    /**
     * 判断坐标是否在屏幕范围内；
     * baiduMap.getProjection()  需要在地图加载完成后才能用
     *
     * @param targetLatLng 给定的坐标值；
     * @return
     */
    public static boolean isLatLngInScreen(BaiduMap baiduMap, Context context, LatLng targetLatLng)
    {

        if (context != null && targetLatLng != null && baiduMap.getProjection() != null)
        {

            LogUtil.i("baiduMap.getProjection()====" + baiduMap.getProjection());
            //得到屏幕坐标；
            Point targetPoint = baiduMap.getProjection().toScreenLocation(targetLatLng);
            Point targetScreen = baiduMap.getMapStatus().targetScreen; //屏幕中心点；

            //屏幕外的点判断
            if (targetPoint.x < 0 || targetPoint.x > targetScreen.x * 2 || targetPoint.y < 0 || targetPoint.y > targetScreen.y * 2)
            {
                return false;
            }
        }
        return true;
    }

    public static Bundle getBundle(String key, Object value)
    {
        Bundle b = new Bundle();
        if (value != null)
        {
            if (value instanceof Boolean)
            {
                b.putBoolean(key, (Boolean) value);
            } else if (value instanceof String)
            {
                b.putString(key, String.valueOf(value));
            }
        }
        return b;
    }

    /**
     * 缩小1级；
     */
    public static void zoomOut(BaiduMap baiduMap)
    {
        MapStatus mapStatus = baiduMap.getMapStatus();
        float zoom = mapStatus.zoom; // 当前缩放级别；

        if (zoom > MIN_ZOOM_LEVEL)
        {
            baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(zoom - 1));
        }
    }

    /**
     * 显示特定的mark；
     *
     * @param latLng
     */
    public static void showSpecificMark(BaiduMap baiduMap, LatLng latLng)
    {
        if (latLng != null)
        {
            //定义地图状态
            MapStatus mMapStatus = new MapStatus.Builder().target(latLng).build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            baiduMap.setMapStatus(mMapStatusUpdate);
        }
    }


    /**
     * 放大；
     */
    public static void zoomIn(BaiduMap baiduMap, float targetZoom)
    {
        if (targetZoom >= BaiduMapUtil.MIN_ZOOM_LEVEL && targetZoom <= BaiduMapUtil.MAX_ZOOM_LEVEL)
        {
            baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(targetZoom));
        }
    }


    /**
     * 获取驾车路线规划；
     *
     * @param fromCity
     * @param fromArea
     * @param toCity
     * @param toArea   ECAR_AVOID_JAM
     *                 驾车策略： 躲避拥堵
     *                 ECAR_DIS_FIRST
     *                 驾乘检索策略常量：最短距离
     *                 ECAR_FEE_FIRST
     *                 驾乘检索策略常量：较少费用
     *                 ECAR_TIME_FIRST
     *                 驾乘检索策略常量：时间优先
     * @return
     */
    public static DrivingRoutePlanOption getDrivingRoutePlanOption(String fromCity, String fromArea, String toCity, String toArea)
    {
        DrivingRoutePlanOption routePlanOption = new DrivingRoutePlanOption();
        PlanNode fromNode = PlanNode.withCityNameAndPlaceName(fromCity, fromArea);
        PlanNode toNode = PlanNode.withCityNameAndPlaceName(toCity, toArea);
        //驾车策略；
        return routePlanOption.from(fromNode).
                policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_DIS_FIRST).
                to(toNode);
    }

    /**
     * 获取公交路线规划；
     *
     * @param fromCity
     * @param fromArea
     * @param toCity
     * @param toArea   EBUS_NO_SUBWAY
     *                 公交检索策略常量：不含地铁
     *                 EBUS_TIME_FIRST
     *                 公交检索策略常量：时间优先
     *                 EBUS_TRANSFER_FIRST
     *                 公交检索策略常量：最少换乘
     *                 EBUS_WALK_FIRST
     *                 公交检索策略常量：最少步行距离
     * @param city     公交所在城市；
     * @return
     */
    public static TransitRoutePlanOption getTransitRoutePlanOption(String fromCity, String fromArea, String toCity, String toArea, String city)
    {
        TransitRoutePlanOption routePlanOption = new TransitRoutePlanOption();
        PlanNode fromNode = PlanNode.withCityNameAndPlaceName(fromCity, fromArea);
        PlanNode toNode = PlanNode.withCityNameAndPlaceName(toCity, toArea);
        //公交策略；
        return routePlanOption.from(fromNode).
                policy(TransitRoutePlanOption.TransitPolicy.EBUS_TIME_FIRST).
                city(city).
                to(toNode);
    }

}
