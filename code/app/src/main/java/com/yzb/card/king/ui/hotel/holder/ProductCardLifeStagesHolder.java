package com.yzb.card.king.ui.hotel.holder;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.BankActivityInfoBean;
import com.yzb.card.king.bean.GiftComboBean;
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.hotel.HotelLogicManager;
import com.yzb.card.king.ui.hotel.activtiy.HotelProductListActivity;
import com.yzb.card.king.ui.hotel.persenter.HotelBankActivityPersenter;

import cn.lemon.view.adapter.BaseViewHolder;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/17
 * 描  述：
 */
public class ProductCardLifeStagesHolder extends BaseViewHolder<BankActivityInfoBean> implements View.OnClickListener{


    private TextView tvLeftStages;

    private TextView tvLifeStagesnNumMsg;

    private ImageView tvBankLogo;

    private LinearLayout llBankLifeStageOne;

    private Context context;

    public ProductCardLifeStagesHolder(ViewGroup parent)
    {
        super(parent, R.layout.view_item_card_life_stages);

        context = parent.getContext();
    }

    @Override
    public void setData(BankActivityInfoBean data)
    {
        super.setData(data);

        tvLeftStages.setText(data.getActName());

        tvLifeStagesnNumMsg.setText("分期数："+data.getStageDesc());

        String imageUrl = data.getBankLogo();

        Glide.with(context).load(ServiceDispatcher.getImageUrl(imageUrl)).into(tvBankLogo);

        llBankLifeStageOne.setTag(data);

    }

    @Override
    public void onInitializeView()
    {
        super.onInitializeView();

        tvLeftStages = findViewById(R.id.tvLeftStages);

        tvLifeStagesnNumMsg = findViewById(R.id.tvLifeStagesnNumMsg);

        tvBankLogo = findViewById(R.id.tvBankLogo);

        llBankLifeStageOne = findViewById(R.id.llBankLifeStageOne);

        llBankLifeStageOne.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){

            case R.id.llBankLifeStageOne:

                if(v.getTag() != null){

                    BankActivityInfoBean data = (BankActivityInfoBean) v.getTag();

                    HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

                    productListParam.setBankIds(data.getBankId()+"");

                    Intent intent = new Intent(context, HotelProductListActivity.class);

                    intent.putExtra("bankData",data);

                    intent.putExtra("bankType", HotelBankActivityPersenter.BANK_PRE_ACT_LIST_CODE);

                    context.startActivity(intent);

                }

                break;
            default:
                break;

        }
    }
}
