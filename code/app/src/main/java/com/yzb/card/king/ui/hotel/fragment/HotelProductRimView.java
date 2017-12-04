package com.yzb.card.king.ui.hotel.fragment;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.Hotel;
import com.yzb.card.king.bean.hotel.HotelDetailServiceBean;
import com.yzb.card.king.bean.hotel.HotelServiceFacilityBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.hotel.HotelLogicManager;
import com.yzb.card.king.ui.hotel.activtiy.HotelFacilityDetail;
import com.yzb.card.king.ui.other.activity.NearByMapActivity;
import com.yzb.card.king.util.ToastUtil;

import java.util.List;

/**
 * Created by 玉兵 on 2017/12/3.
 */

public class HotelProductRimView implements View.OnClickListener{

    private LinearLayout llHotelFacility;

    //周边服务
    private TextView transportion, foodTv, amusement, landscape, gouwu, jiud;

    private  Context context;

    private Hotel hotel = null;

    private HotelDetailServiceBean hotelDetailServiceBean;

    private View view;

    public HotelProductRimView(View view, Context context){

        this.view = view;

        this.context = context;

        llHotelFacility = (LinearLayout) view.findViewById(R.id.llHotelFacility);

        initNearByView(view);
    }

    public View getView() {
        return view;
    }

    private void initNearByView(View view)
    {

        view.findViewById(R.id.tvHotelFacility).setOnClickListener(this);

        transportion = (TextView) view.findViewById(R.id.transportion);

        transportion.setOnClickListener(this);

        foodTv = (TextView) view.findViewById(R.id.food_tv);

        foodTv.setOnClickListener(this);

        amusement = (TextView) view.findViewById(R.id.amusement);

        amusement.setOnClickListener(this);

        landscape = (TextView) view.findViewById(R.id.landscape);

        landscape.setOnClickListener(this);

        gouwu = (TextView) view.findViewById(R.id.gouwu);

        gouwu.setOnClickListener(this);

        jiud = (TextView) view.findViewById(R.id.jiud);

        jiud.setOnClickListener(this);
    }

    public  void setHotelDetailServiceBean( HotelDetailServiceBean   hotelDetailServiceBean){

        this.hotelDetailServiceBean = hotelDetailServiceBean;


        List<HotelServiceFacilityBean> specialServiceList = hotelDetailServiceBean.getSpecialServiceList();

        initFacilityView(specialServiceList);

    }

    private void initFacilityView(List<HotelServiceFacilityBean> specialServiceList)
    {

        int size = specialServiceList.size();

        for (int a = 0; a < size; a++) {

            HotelServiceFacilityBean bean = specialServiceList.get(a);

            View childFacilityView = LayoutInflater.from(context).inflate(R.layout.view_facility_info, null);

            TextView tvFacName = (TextView) childFacilityView.findViewById(R.id.tvFacName);

            ImageView ivFacility = (ImageView) childFacilityView.findViewById(R.id.ivFacility);

            if (!TextUtils.isEmpty(bean.getItemPhoto())) {

                Glide.with(context).load(ServiceDispatcher.getImageUrl(bean.getItemPhoto())).into(ivFacility);
            }

            tvFacName.setText(bean.getItemName());

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);

            llHotelFacility.addView(childFacilityView, lp);

        }

    }

    @Override
    public void onClick(View v) {

        hotel = HotelLogicManager.getInstance().getHotel();

        switch (v.getId()){

            case R.id.transportion:// 周边交通
                if (hotel != null)
                    toAround(0);
                break;
            case R.id.food_tv:// 周边餐饮
                if (hotel != null)
                    toAround(1);
                break;
            case R.id.amusement:// 周边娱乐
                if (hotel != null)
                    toAround(2);
                break;
            case R.id.gouwu:// 周边景点
                if (hotel != null)
                    toAround(4);
                break;
            case R.id.jiud:// 周边景点
                if (hotel != null)
                    toAround(5);
                break;

            case R.id.tvHotelFacility://酒店设施详情

                if (hotel == null ) {

                    ToastUtil.i(context, "无酒店数据");

                    return;
                }

                if (hotelDetailServiceBean == null ) {

                    ToastUtil.i(context, "无酒店服务设施数据");

                    return;
                }

                Intent intent = new Intent(context, HotelFacilityDetail.class); //hotel

                hotel.setBaseServiceList(hotelDetailServiceBean.getBaseServiceList());

                hotel.setSpecialServiceList(hotelDetailServiceBean.getSpecialServiceList());

                intent.putExtra("hotelData", hotel);

                context.startActivity(intent);

                break;
                default:
                    break;
        }
    }

    private void toAround(int flag)
    {

        if (hotel.getAddrMap().getLat() == 0 || hotel.getAddrMap().getLng() == 0 || hotel.getAddrMap().getAddress() == null || hotel.getHotelName() == null) {

            ToastUtil.i(context, "无法进入地图");
            return;
        }

        Intent intent1 = new Intent(context, NearByMapActivity.class);
        intent1.putExtra(NearByMapActivity.CATEGORY, flag); //交通，餐饮，娱乐，景点，购物，酒店        //依次传入：0,1,2,3,4,5
        Location location = new Location();
        location.latitude = hotel.getAddrMap().getLat();
        location.longitude = hotel.getAddrMap().getLng();
        location.streetNumber = hotel.getAddrMap().getAddress();
        location.msg = hotel.getHotelName();

        intent1.putExtra(NearByMapActivity.LOCATION, location);

        context.startActivity(intent1);

    }

}
