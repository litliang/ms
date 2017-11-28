package com.yzb.card.king.ui.app.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.model.LatLng;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.luxury.activity.MsDetailActivity;
import com.yzb.card.king.ui.discount.bean.StoreBean;
import com.yzb.card.king.util.BaiduMapUtil;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：用于显示poi的overly
 *
 * @author:gengqiyun
 * @date: 2016/8/13
 */
public class PoiOverlayManager extends OverlayManager {
    private static final int MAX_SCREEN_STORES = 20; // 屏幕显示的最大门店数量；
    private List<StoreBean> storeBeans;
    private LayoutInflater inflater;

    public PoiOverlayManager(Context context, BaiduMap baiduMap) {
        super(context, baiduMap);
        if (mContext != null) {
            inflater = LayoutInflater.from(mContext);
        }
    }

    /**
     * 设置数据
     */
    public void setData(List<StoreBean> storeBeans) {
        this.storeBeans = storeBeans;
    }

    @Override
    public List<OverlayOptions> getOverlayOptions() {
        if (storeBeans == null || storeBeans.size() == 0) {
            return null;
        }
        List<OverlayOptions> markerList = new ArrayList<>();
        int markerSize = 0;
        StoreBean bean;

        // 循环生成点；
        for (int i = 0; i < storeBeans.size() && markerSize < MAX_SCREEN_STORES; i++) {
            LogUtil.i("展示第" + i + "个store");
            markerSize++;
            bean = storeBeans.get(i);

            if (inflater == null) {
                break;
            }
            View windowView = inflater.inflate(R.layout.map_info_window, null);
            TextView tv_name = (TextView) windowView.findViewById(R.id.tv_name);
            TextView tv_score = (TextView) windowView.findViewById(R.id.tv_score);
            TextView tv_price = (TextView) windowView.findViewById(R.id.tv_price);

            tv_name.setText(bean.storeName);
            tv_score.setText(bean.vote + "分");
            tv_price.setText("¥" + (TextUtils.isEmpty(bean.avgPrice) ? "0" : bean.avgPrice));

            markerList.add(new MarkerOptions().position(new LatLng(bean.lat, bean.lng)).
                    icon(BitmapDescriptorFactory.fromView(windowView))
                    .extraInfo(BaiduMapUtil.getBundle("id", bean.id)));
        }
        return markerList;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (!mOverlayList.contains(marker)) {
            return false;
        }
        if (marker.getExtraInfo() != null) {
            return onPoiClick(marker);
        }
        return false;
    }

    /**
     * 覆写此方法以改变默认点击行为
     *
     * @param marker
     */
    public boolean onPoiClick(Marker marker) {
        Bundle extraInfo = marker.getExtraInfo();
        // 不是我的位置；
        if (extraInfo != null) {
            boolean isMyLocation = extraInfo.getBoolean(IS_MY_LOCATION);
            //我的位置；
            if (isMyLocation) {
              //点击我的位置，do nothing;
            } else {
                if (mContext == null) {
                    //商家id；
                    String id = extraInfo.getString("id");
                    LogUtil.i("点击的门店的id==" + id);
                    if (!TextUtils.isEmpty(id)) {
                        //跳转；
                        Intent intent = new Intent(mContext, MsDetailActivity.class);
                        intent.putExtra(AppConstant.INTENT_BUNDLE, BaiduMapUtil.getBundle("id", id));
                        mContext.startActivity(intent);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean onPolylineClick(Polyline polyline) {
        return false;
    }
}
