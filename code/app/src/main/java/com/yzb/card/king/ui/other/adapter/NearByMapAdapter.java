package com.yzb.card.king.ui.other.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiResult;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.PoiInfoBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.util.BaiduMapUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 功能：周边地图适配器；
 *
 * @author:gengqiyun
 * @date: 2016/10/26
 */
public class NearByMapAdapter extends BaseAdapter {
    private final Context context;
    private LayoutInflater inflater;
    private List<PoiInfoBean> poiInfoList;
    private int curCategory;//当前分类；
    private ICallBack callBack;

    public void setCallBack(ICallBack callBack) {
        this.callBack = callBack;
    }

    public NearByMapAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    /**
     * 选中某一行
     *
     * @param selectPos
     */
    public void selectItem(int selectPos) {
        for (int i = 0; i < poiInfoList.size(); i++) {
            poiInfoList.get(i).setIsSelct(i == selectPos);
        }
        notifyDataSetChanged();
    }

    public interface ICallBack {
        /**
         * 拨号
         *
         * @param phoneNum 号码；
         */
        void dialNum(String phoneNum);

        /**
         * 导航点击；
         *
         * @param latLng 坐标；
         */
        void navigationMap(LatLng latLng);

        /**
         * 地图标注位置；
         *
         * @param location
         */
        void showPoiOnMap(LatLng location);
    }

    /**
     * 地图上点击的item在ListView中的位置；
     *
     * @return
     */
    public int getClickItemPosition(String uid) {
        if (poiInfoList == null) {
            return -1;
        }
        for (int i = 0; i < poiInfoList.size(); i++) {
            if (!TextUtils.isEmpty(uid) && uid.equals(poiInfoList.get(i).getPoiInfo().uid)) {
                return i;
            }
        }
        return -1;
    }


    public void setCurCategory(int curCategory) {
        this.curCategory = curCategory;
    }

    /**
     * 设置数据；
     *
     * @param poiResult
     */
    public void setPoiResult(PoiResult poiResult) {
        if (poiResult != null) {
            List<PoiInfo> poiInfoList = poiResult.getAllPoi();
            this.poiInfoList = new ArrayList<>();
            for (int i = 0; i < poiInfoList.size(); i++) {
                this.poiInfoList.add(new PoiInfoBean(poiInfoList.get(i), false));
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return poiInfoList == null ? 0 : poiInfoList.size();
    }

    @Override
    public PoiInfoBean getItem(int position) {
        return poiInfoList == null ? null : poiInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_listview_nearby_map, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.root.setBackgroundDrawable(getItem(position).isSelct() ?
                new ColorDrawable(Color.parseColor("#f0f6f8"))
                : new ColorDrawable(Color.parseColor("#ffffff")));

        final PoiInfo poiInfo = getItem(position).getPoiInfo();
        final String phone = poiInfo.phoneNum;
        boolean phoneEmpty = TextUtils.isEmpty(phone) ? true : false;
        String subTitle = "";
        boolean subTitleEmpty = TextUtils.isEmpty(subTitle) ? true : false;

        switch (curCategory) {
            case 0: //交通；
                viewHolder.tvsubtitle2.setVisibility(View.INVISIBLE);
                viewHolder.ll_price.setVisibility(View.GONE);
                viewHolder.tvphone.setVisibility(View.GONE);
                break;
            case 1://餐饮；
                viewHolder.ll_price.setVisibility(View.GONE);
                //电话和营业时间根据情况显示；
                viewHolder.tvphone.setVisibility(phoneEmpty ? View.GONE : View.VISIBLE);
                viewHolder.tvsubtitle2.setVisibility(subTitleEmpty ? View.GONE : View.VISIBLE);
                break;
            case 2://娱乐；
                viewHolder.ll_price.setVisibility(View.GONE);
                //电话和营业时间根据情况显示；
                viewHolder.tvphone.setVisibility(phoneEmpty ? View.GONE : View.VISIBLE);
                viewHolder.tvsubtitle2.setVisibility(subTitleEmpty ? View.GONE : View.VISIBLE);
                break;
            case 3://景点；
                viewHolder.ll_price.setVisibility(View.GONE);
                //电话和营业时间根据情况显示；
                viewHolder.tvphone.setVisibility(phoneEmpty ? View.GONE : View.VISIBLE);
                viewHolder.tvsubtitle2.setVisibility(subTitleEmpty ? View.GONE : View.VISIBLE);
                break;
            case 4://购物；
                viewHolder.ll_price.setVisibility(View.GONE);
                //电话和营业时间根据情况显示；
                viewHolder.tvphone.setVisibility(phoneEmpty ? View.GONE : View.VISIBLE);
                viewHolder.tvsubtitle2.setVisibility(subTitleEmpty ? View.GONE : View.VISIBLE);
                break;
            case 5://酒店；
                //电话和营业时间根据情况显示；
                viewHolder.tvphone.setVisibility(View.GONE);
                viewHolder.tvnavigation.setVisibility(View.GONE);
                viewHolder.tvsubtitle2.setVisibility(subTitleEmpty ? View.GONE : View.VISIBLE);

                //价格先不显示；
                viewHolder.ll_price.setVisibility(View.GONE);
                break;
        }
        viewHolder.tvtitle.setText(poiInfo.name);
        viewHolder.tvsubtitle1.setText("距离" + BaiduMapUtil.getFormatDistanceKm3Point(
                GlobalApp.getPositionedCity().getLatLng(), poiInfo.location) + "km");

        viewHolder.tvphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.dialNum(phone);
                }
            }
        });

        //导航；
        viewHolder.tvnavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.navigationMap(poiInfo.location);
                }
            }
        });

        viewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    selectItem(position);
                    callBack.showPoiOnMap(poiInfo.location);
                }
            }
        });
        return viewHolder.root;
    }

    public class ViewHolder {
        public final TextView tvtitle;
        public final TextView tvsubtitle1;
        public final TextView tvnavigation;
        public final TextView tvphone;
        public final LinearLayout lltop;
        public final TextView tvsubtitle2;
        public final TextView tv_price;
        public final View ll_price;
        public final View root;

        public ViewHolder(View root) {
            this.root = root.findViewById(R.id.root);
            tvtitle = (TextView) root.findViewById(R.id.tv_title);
            tvsubtitle1 = (TextView) root.findViewById(R.id.tv_sub_title1);
            tvnavigation = (TextView) root.findViewById(R.id.tv_navigation);
            tvphone = (TextView) root.findViewById(R.id.tv_phone);
            tv_price = (TextView) root.findViewById(R.id.tv_price);
            lltop = (LinearLayout) root.findViewById(R.id.ll_top);
            ll_price = root.findViewById(R.id.ll_price);
            tvsubtitle2 = (TextView) root.findViewById(R.id.tv_sub_title2);
        }
    }
}
