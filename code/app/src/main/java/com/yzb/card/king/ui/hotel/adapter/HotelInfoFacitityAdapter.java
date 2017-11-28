package com.yzb.card.king.ui.hotel.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.HotelRoomInfoBean;
import com.yzb.card.king.sys.ServiceDispatcher;

import java.util.List;

/**
 * 类  名：酒店服务设施适配器
 * 作  者：Li Yubing
 * 日  期：2017/8/9
 * 描  述：
 */
public class HotelInfoFacitityAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private List<HotelRoomInfoBean.RoomService> roomServiceList;

    private Context context;

    public HotelInfoFacitityAdapter(LayoutInflater inflater,List<HotelRoomInfoBean.RoomService> roomServiceList){

        this.inflater = inflater;

        this.roomServiceList = roomServiceList;

        this.context = inflater.getContext();
    }

    private String[] itemNamesArray;

    private String[] itemPhotoArray;

    public HotelInfoFacitityAdapter(LayoutInflater inflater, String[] itemNamesArray , String[] itemPhotoArray){

        this.inflater = inflater;

        this.itemNamesArray = itemNamesArray;

        this.itemPhotoArray = itemPhotoArray;

        this.context = inflater.getContext();
    }

    @Override
    public int getCount()
    {

        if(roomServiceList != null){

            return roomServiceList.size();
        }else {

          return   itemNamesArray.length;
        }

    }

    @Override
    public Object getItem(int position)
    {
        if(roomServiceList != null) {
            return roomServiceList.get(position);
        }else {

           return itemNamesArray[position];
        }
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHold viewHold = null;

        if(convertView == null){

            viewHold = new ViewHold();

            convertView = inflater.inflate(R.layout.view_hotel_fac,null);

            viewHold.initView(convertView);

            convertView.setTag(viewHold);
        }else {

            viewHold = (ViewHold) convertView.getTag();
        }

        if(roomServiceList != null){
            HotelRoomInfoBean.RoomService bean = (HotelRoomInfoBean.RoomService) getItem(position);

            String[] serviceName = bean.getItemNamesArray();

            viewHold.tvServiceName.setText(serviceName[0]);

            String[] serviceImage = bean.getItemPotoArray();

            if(!TextUtils.isEmpty(serviceImage[0])){
                Glide.with(context).load(ServiceDispatcher.getImageUrl(serviceImage[0])).into(viewHold.ivRoomService);
            }
        }else {

            String serviceName = (String) getItem(position);
            viewHold.tvServiceName.setText(serviceName);

            String serviceImage = itemPhotoArray[position];

            if(!TextUtils.isEmpty(serviceImage)){

                Glide.with(context).load(ServiceDispatcher.getImageUrl(serviceImage)).into(viewHold.ivRoomService);
            }
        }



        return convertView;
    }

    class  ViewHold{

        private ImageView ivRoomService;

        private TextView tvServiceName;

        public void initView(View convertView)
        {

            tvServiceName = (TextView) convertView.findViewById(R.id.tvServiceName);

            ivRoomService = (ImageView) convertView.findViewById(R.id.ivRoomService);
        }
    }

}