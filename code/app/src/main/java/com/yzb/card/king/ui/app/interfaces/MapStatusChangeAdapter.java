package com.yzb.card.king.ui.app.interfaces;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.yzb.card.king.util.LogUtil;

/**
 * 功能： 手势操作地图，地图状态变化的监听；
 *
 * @author:gengqiyun
 * @date: 2016/11/5
 */
public class MapStatusChangeAdapter implements BaiduMap.OnMapStatusChangeListener
{

    /**
     * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
     *
     * @param mapStatus 地图状态改变开始时的地图状态
     */
    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus)
    {

    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus)
    {

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus)
    {
        statusChange(mapStatus);
    }

    public void statusChange(MapStatus mapStatus)
    {

    }
}
