package com.yzb.card.king.bean.hotel;

import com.baidu.mapapi.search.core.PoiInfo;

/**
 * Created by Lenovo on 2017/4/15.
 */
public class PoiInfoBean {
    private PoiInfo poiInfo;
    private boolean isSelct;

    public PoiInfoBean() {
    }

    public PoiInfoBean(PoiInfo poiInfo, boolean select) {
        this.poiInfo = poiInfo;
        this.isSelct = select;
    }

    public PoiInfo getPoiInfo() {
        return poiInfo;
    }

    public void setPoiInfo(PoiInfo poiInfo) {
        this.poiInfo = poiInfo;
    }

    public boolean isSelct() {
        return isSelct;
    }

    public void setIsSelct(boolean isSelct) {
        this.isSelct = isSelct;
    }
}
