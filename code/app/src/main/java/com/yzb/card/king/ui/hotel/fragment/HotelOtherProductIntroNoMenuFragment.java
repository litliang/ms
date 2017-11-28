package com.yzb.card.king.ui.hotel.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.Hotel;
import com.yzb.card.king.bean.hotel.HotelExtraProductBean;
import com.yzb.card.king.ui.appwidget.appdialog.HotelOtherProductIntroFragmentDialog;
import com.yzb.card.king.ui.hotel.HoteUtil;

/**
 * 类  名：酒店其它产品简介(无菜单)
 * 作  者：Li Yubing
 * 日  期：2017/8/10
 * 描  述：
 */
public class HotelOtherProductIntroNoMenuFragment extends Fragment  implements View.OnClickListener{

    private TextView tvName;

    private  TextView  tvDesc;

    private ImageView ivTaklPhone;

    private  TextView businessHours;

    private  TextView tvExplain;

    private  TextView tvBusinessTimeMsg;

    private TextView tvExplainMsg;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_hotel_other_product_intro_no_menu,null);

        initView(view);

        Bundle bundle = getArguments();

        if(bundle.containsKey("HotelExtraProductBean")){

            HotelExtraProductBean headerItem = (HotelExtraProductBean) bundle.get("HotelExtraProductBean");

            Hotel.GoodsType   goodsType = (Hotel.GoodsType) bundle.get("goodsType");


            initType(goodsType);

            initData(headerItem);
        }

        return view;
    }

    public void initType(Hotel.GoodsType goodsType)
    {
       String typeCode = goodsType.getGoodsTypeCode();

        if(HoteUtil.HOTEL_SPA_CODE.equals(typeCode)){

            tvExplainMsg.setText("SPA介绍");

        }else if(HoteUtil.HOTEL_GYM_CODE.equals(typeCode)){

            tvExplainMsg.setText("健身房介绍");

        }else if(HoteUtil.HOTEL_SWMMING_POOL_CODE.equals(typeCode)){

            tvExplainMsg.setText("游泳池介绍");
        }

    }


    public void initData(  HotelExtraProductBean headerItem){

        tvName.setText(headerItem.getGoodsName());

        StringBuffer sb = new StringBuffer();

        sb.append(headerItem.getFloorDesc());

        String balconyStatus = headerItem.getBalconyStatus();

        if("1".equals(balconyStatus)){

            sb.append("(").append(balconyStatus).append(")");
        }

        tvDesc.setText(sb.toString());

        String tel = headerItem.getTel();

        if(!TextUtils.isEmpty(tel)){

            ivTaklPhone.setVisibility(View.VISIBLE);

            ivTaklPhone.setTag(tel);

            ivTaklPhone.setOnClickListener(this);

        }else {

            ivTaklPhone.setVisibility(View.INVISIBLE);
        }

        businessHours.setText(headerItem.getBusinessStartHours()+"至"+headerItem.getBusinessEndHours());

        tvExplain.setText(headerItem.getGoodsIntro());

    }


    public void initView(View view)
    {


        tvName = (TextView) view.findViewById(R.id.tvName);

        tvDesc = (TextView) view.findViewById(R.id.tvDesc);

        ivTaklPhone = (ImageView) view.findViewById(R.id.ivTaklPhone);

        businessHours = (TextView) view.findViewById(R.id.businessHours);

        tvExplain = (TextView) view.findViewById(R.id.tvExplain);

        tvBusinessTimeMsg = (TextView) view.findViewById(R.id.tvBusinessTimeMsg);

        tvExplainMsg = (TextView) view.findViewById(R.id.tvExplainMsg);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){


            case R.id.ivTaklPhone:

                String phoneNumber = (String) v.getTag();
                //用intent启动拨打电话
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                startActivity(intent);

                break;

            default:
                break;
        }
    }
}
