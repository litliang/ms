package com.yzb.card.king.ui.hotel.holder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.RoomInfoBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.hotel.activtiy.HotelImageExpositionActivity;
import com.yzb.card.king.ui.hotel.activtiy.HotelProductInfoActivity;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.Utils;

import org.xutils.x;

import cn.lemon.view.adapter.BaseViewHolder;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/17
 * 描  述：
 */
public class TodayLeftRoomHolder extends BaseViewHolder<RoomInfoBean> implements View.OnClickListener{

    private LinearLayout llOne;

    private Context context;

    private ImageView ivRoomImage;

    private TextView tvImageNum,tvRoomName,tvHotelName,tvHotelCityDistance,tvSpecailMessage,tvPriceOne,tvRoomPriceOneTwo;

    private RelativeLayout rlRoomImage;

    private long todayTime;

    public TodayLeftRoomHolder(ViewGroup parent)
    {
        super(parent, R.layout.view_hotel_today_left_room_item);

        context = parent.getContext();

        todayTime = timeToLong(DateUtil.long2String(System.currentTimeMillis(),DateUtil.DATE_FORMAT_HHMM));

    }

    @Override
    public void setData(RoomInfoBean data) {
        super.setData(data);

       String photoUrls = data.getPhotoUrls();

        llOne.setTag(data.getHotelId());

        if(photoUrls != null){

            int index = photoUrls.indexOf(",");

            if(index == -1){


                Glide.with(context).load(ServiceDispatcher.getImageUrl(photoUrls)).into(ivRoomImage);

                tvImageNum.setText("");

                tvImageNum.setVisibility(View.INVISIBLE);
            }else {
                String[] photoUrlsArray = photoUrls.split(",");

                Glide.with(context).load(ServiceDispatcher.getImageUrl(photoUrlsArray[0])).into(ivRoomImage);

                tvImageNum.setText(photoUrlsArray.length+"张");

                tvImageNum.setVisibility(View.VISIBLE);
            }

            rlRoomImage.setTag(photoUrls);

        }else{

            tvImageNum.setText("");

            tvImageNum.setVisibility(View.INVISIBLE);
        }

        tvRoomName.setText(data.getRoomsName()+" "+data.getRoomsTypeDesc());


        tvHotelName.setText(data.getHotelName());

        tvHotelCityDistance.setText(data.getPositionDesc());

        String startTime = data.getStartTime();

        if(!TextUtils.isEmpty(startTime)){

            long roomLong = timeToLong(startTime);

            if(todayTime>= roomLong){
                tvSpecailMessage.setText("甩房中  剩余"+data.getResidualQuantity()+"份");
            }else {
                tvSpecailMessage.setText(startTime+"之后可预订");
            }
        }

        tvPriceOne.setText("¥"+ Utils.subZeroAndDot(data.getActualPrice()+""));

        tvPriceOne.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线

        tvRoomPriceOneTwo.setText(Utils.subZeroAndDot(data.getPolicysPrice()+""));

    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        llOne = findViewById(R.id.llOne);
        llOne.setOnClickListener(this);

        ivRoomImage = findViewById(R.id.ivRoomImage);

        tvImageNum = findViewById(R.id.tvImageNum);

        tvRoomName = findViewById(R.id.tvRoomName);

        tvHotelName = findViewById(R.id.tvHotelName);

        tvHotelCityDistance = findViewById(R.id.tvHotelCityDistance);

        tvSpecailMessage = findViewById(R.id.tvSpecailMessage);

        tvPriceOne = findViewById(R.id.tvPriceOne);

        tvRoomPriceOneTwo = findViewById(R.id.tvRoomPriceOneTwo);

        rlRoomImage = findViewById(R.id.rlRoomImage);

        rlRoomImage.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.llOne:

                if(v.getTag() != null){

                    Intent intent = new Intent(context, HotelProductInfoActivity.class);
                    intent.putExtra("hotelId",v.getTag() + "");
                    context.startActivity(intent);
                }


                break;
            case R.id.rlRoomImage:

                if( v.getTag() != null){

                    String photoUrls = (String) v.getTag();

                    Intent intent = new Intent(context,HotelImageExpositionActivity.class);

                    intent.putExtra("imageTitleName","房间图片");

                    intent.putExtra("photoUrls",photoUrls);

                    context.startActivity(intent);

                }

                break;
            default:
                break;

        }
    }

    private long timeToLong(String hhmm){


        int a = hhmm.indexOf(":");


        if(a != -1){

            String str = hhmm.replace(":", "");


            long cde = Long.parseLong(str);

            return  cde;

        }else {

            return  0;
        }

    }
}
