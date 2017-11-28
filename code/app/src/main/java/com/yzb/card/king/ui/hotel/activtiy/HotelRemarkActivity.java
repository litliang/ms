package com.yzb.card.king.ui.hotel.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 类  名：酒店备注
 * 作  者：Li Yubing
 * 日  期：2017/9/1
 * 描  述：
 */
@ContentView(R.layout.activity_hotel_remark)
public class HotelRemarkActivity extends BaseActivity{

    @ViewInject(R.id.llBedType)
    private LinearLayout llBedType;

    @ViewInject(R.id.llNosmoking)
    private LinearLayout llNosmoking;

    @ViewInject(R.id.etRemark)
    private EditText etRemark;

    @ViewInject(R.id.tvTxtNum)
    private TextView tvTxtNum;


    String[] bedTypeArray =  new String[]{"无床型","尽量安排大床","尽量安排双床"};

    String[] noSmokingArray =  new String[]{"无要求","尽量安排无烟房","尽量安排吸烟房"};

    private int bedTypeIndex = 0 ;

    private int noSmokingIndex = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initView();

        initData();
    }

    private void initData()
    {
        int size1 =  bedTypeArray.length;

        int size2 =  noSmokingArray.length;

        if(getIntent().hasExtra("remarkBean")){

            String remarkStr = getIntent().getStringExtra("remarkBean");

            String[] remarkArray = remarkStr.split(",");

            if(remarkArray.length >= 2){

             String     aStr = remarkArray[0];

             String   bStr = remarkArray[1];

                for( int a = 0 ; a < size1 ; a++) {

                    String aStrT = bedTypeArray[a];

                    if( aStr.equals(aStrT)){

                        bedTypeIndex = a;

                        break;
                    }

                }

                for( int a = 0 ; a < size1 ; a++) {

                    String aStrT = noSmokingArray[a];

                    if( bStr.equals(aStrT)){

                        noSmokingIndex = a;

                        break;
                    }

                }

            }

        }



        for( int a = 0 ; a < size1 ; a++){

            View beadTypeView = LayoutInflater.from(this).inflate(R.layout.view_item_hotel_remark,null);

            LinearLayout llRemark = (LinearLayout) beadTypeView.findViewById(R.id.llRemark);

            ImageView  tvRightBlue = (ImageView) beadTypeView.findViewById(R.id.tvRightBlue);

            TextView tvBedTypeName = (TextView) beadTypeView.findViewById(R.id.tvBedTypeName);

            ImageView line = (ImageView) beadTypeView.findViewById(R.id.line);


            if(a == size1-1){

                line.setVisibility(View.INVISIBLE);
            }

            if( bedTypeIndex== a){

                tvRightBlue.setVisibility(View.VISIBLE);


            }else {

                tvRightBlue.setVisibility(View.INVISIBLE);

            }

            tvBedTypeName.setText(bedTypeArray[a]);

            llBedType.addView(beadTypeView,a);

            llRemark.setTag(a);

            llRemark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    int goalIndex = (int) v.getTag();

                    if( goalIndex != bedTypeIndex){

                        View viewA = llBedType.getChildAt(bedTypeIndex);

                        ImageView  tvRightBlue = (ImageView) viewA.findViewById(R.id.tvRightBlue);

                        tvRightBlue.setVisibility(View.INVISIBLE);

                        View viewB = llBedType.getChildAt(goalIndex);

                        bedTypeIndex =  goalIndex;

                        ImageView  tvRightBlueB = (ImageView) viewB.findViewById(R.id.tvRightBlue);

                        tvRightBlueB.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        for( int a = 0 ; a < size2 ; a++){

            View beadTypeView = LayoutInflater.from(this).inflate(R.layout.view_item_hotel_remark,null);

            LinearLayout llRemark = (LinearLayout) beadTypeView.findViewById(R.id.llRemark);

            ImageView  tvRightBlue = (ImageView) beadTypeView.findViewById(R.id.tvRightBlue);

            TextView tvBedTypeName = (TextView) beadTypeView.findViewById(R.id.tvBedTypeName);

            ImageView line = (ImageView) beadTypeView.findViewById(R.id.line);

            if(a == size1-1){

                line.setVisibility(View.INVISIBLE);
            }

            if( noSmokingIndex== a){

                tvRightBlue.setVisibility(View.VISIBLE);


            }else {

                tvRightBlue.setVisibility(View.INVISIBLE);

            }

            tvBedTypeName.setText(noSmokingArray[a]);

            llNosmoking.addView(beadTypeView,a);

            llRemark.setTag(a);

            llRemark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    int goalIndex = (int) v.getTag();

                    if( goalIndex != noSmokingIndex){

                        View viewA = llNosmoking.getChildAt(noSmokingIndex);

                        ImageView  tvRightBlue = (ImageView) viewA.findViewById(R.id.tvRightBlue);

                        tvRightBlue.setVisibility(View.INVISIBLE);

                        View viewB = llNosmoking.getChildAt(goalIndex);

                        noSmokingIndex =  goalIndex;

                        ImageView  tvRightBlueB = (ImageView) viewB.findViewById(R.id.tvRightBlue);

                        tvRightBlueB.setVisibility(View.VISIBLE);
                    }
                }
            });

        }

    }

    private void initView()
    {

        setTitleNmae("住房偏好");//tvStorePrice


        etRemark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {

                int strLength = s.toString().length();

                tvTxtNum.setText(strLength+"/225");

            }
        });
    }

    @Event(R.id.tvConfirm)
    private void tvConfirmAction(View view){

        StringBuffer sb = new StringBuffer();

        sb.append(bedTypeArray[bedTypeIndex]).append(",").append(noSmokingArray[noSmokingIndex]);

        String etRemarkStr = etRemark.getText().toString();

        if(!TextUtils.isEmpty(etRemarkStr)&& etRemarkStr.length() >0){

            sb.append(",").append(etRemarkStr);
        }

        Intent intent = new Intent();

        intent.putExtra("remarkStr",sb.toString());

        setResult(1001,intent);

        finish();

    }
}
