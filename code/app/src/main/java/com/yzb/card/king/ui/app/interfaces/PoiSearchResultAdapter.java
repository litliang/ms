package com.yzb.card.king.ui.app.interfaces;

import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;

/**
 * 功能：poi搜索结果监听适配器；
 *
 * @author:gengqiyun
 * @date: 2016/11/5
 */
public class PoiSearchResultAdapter implements OnGetPoiSearchResultListener
{

    @Override
    public void onGetPoiResult(PoiResult poiResult)
    {
        getPoiResult(poiResult);
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult)
    {
        getPoiDetailResult(poiDetailResult);
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult)
    {
        getPoiIndoorResult(poiIndoorResult);
    }

    public void getPoiResult(PoiResult poiResult)
    {

    }

    public void getPoiDetailResult(PoiDetailResult poiDetailResult)
    {

    }

    public void getPoiIndoorResult(PoiIndoorResult poiIndoorResult)
    {

    }
}
