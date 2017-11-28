package com.yzb.card.king.ui.hotel.activtiy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.Hotel;
import com.yzb.card.king.bean.hotel.HotelRoomInfoBean;
import com.yzb.card.king.bean.hotel.HotelServiceFacilityBean;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.hotel.holder.HotelDetalServiceFacitityHolder;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.lemon.view.adapter.MultiTypeAdapter;

/**
 * 类  名：酒店设施详情
 * 作  者：Li Yubing
 * 日  期：2017/8/2
 * 描  述：
 */
@ContentView(R.layout.activity_hotel_facility_detail)
public class HotelFacilityDetail extends BaseActivity {

    @ViewInject(R.id.wvLvData)
    private RecyclerView wvLvData;

    @ViewInject(R.id.tvHotelLinkeMsg)
    private TextView tvHotelLinkeMsg;

    @ViewInject(R.id.tvHotelPhone)
    private  TextView tvHotelPhone;

    private MultiTypeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setTitleNmae(R.string.hotel_facility_name);

        initData();

    }

    private void initData()
    {
        if(getIntent().hasExtra("hotelData")){

            final Hotel hotelBean = (Hotel) getIntent().getSerializableExtra("hotelData");

            tvHotelPhone.setText("联系电话："+hotelBean.getHotelTel());

            StringBuffer sb = new StringBuffer();

            String hotelEmail = hotelBean.getHotelEmail();

            if(!TextUtils.isEmpty(hotelEmail)){

                sb.append("邮箱：").append(hotelEmail).append("\n");
            }

            String hotelFax = hotelBean.getHotelFax();

            if(!TextUtils.isEmpty(hotelFax)){

                sb.append("传真：").append(hotelFax).append("\n");
            }

            String startDateShop = hotelBean.getOpeningDate();

            if(!TextUtils.isEmpty(startDateShop)){

                sb.append("开业时间：").append(startDateShop).append("\n");
            }

            String renovationDate = hotelBean.getRenovationDate();

            if(!TextUtils.isEmpty(renovationDate)){

                sb.append("最近装修：").append(renovationDate).append("\n");
            }
            tvHotelLinkeMsg.setText(sb.toString());

            final List<HotelRoomInfoBean.RoomService> roomServiceList = new ArrayList<HotelRoomInfoBean.RoomService>();

            List<HotelServiceFacilityBean> baseHotelServiceList =  hotelBean.getBaseServiceList();

            for(HotelServiceFacilityBean bean :baseHotelServiceList){


                HotelRoomInfoBean.RoomService roomService = new HotelRoomInfoBean.RoomService();


                roomService.setTypeName(bean.getTypeName());

                roomService.setItemNames(bean.getItemName());


                roomService.setItemPhotos(bean.getItemPhoto());


                roomServiceList.add(roomService);

                //LogUtil.e(roomService.getTypeName()+"-"+roomService.getItemNames()+"--"+roomService.getItemNamesArray().length+"---"+roomService.getItemPhotos()+"---"+roomService.getItemPotoArray().length);
            }


            mAdapter = new MultiTypeAdapter(this);

            wvLvData.setLayoutManager(new LinearLayoutManager(this));
            wvLvData.setAdapter(mAdapter);

            wvLvData.post(new Runnable() {
                @Override
                public void run()
                {
                    /**
                     * 请求该酒店的房间信息
                     */

                    mAdapter.clear();

                    mAdapter.addAll(HotelDetalServiceFacitityHolder.class, roomServiceList);
                }
            });

            findViewById(R.id.llTalkPhone).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                   String phoneNumber = hotelBean.getHotelTel();
                    //用intent启动拨打电话
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                    startActivity(intent);

                }
            });

        }

    }
}
