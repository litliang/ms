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
import com.yzb.card.king.bean.GiftComboBean;
import com.yzb.card.king.bean.hotel.HotelProductObjectBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.hotel.HoteUtil;
import com.yzb.card.king.ui.hotel.activtiy.CardRightsProductDetailActivity;
import com.yzb.card.king.ui.hotel.activtiy.FlashSaleProductDetailActivity;
import com.yzb.card.king.ui.hotel.activtiy.HotelImageExpositionActivity;
import com.yzb.card.king.util.Utils;

import cn.lemon.view.adapter.BaseViewHolder;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/17
 * 描  述：限时抢购
 */
public class HotelFlashSaleHolder extends BaseViewHolder<GiftComboBean> implements View.OnClickListener {//

    private Context context;

    private TextView tvLowPrice;

    private ImageView ivImageSource;

    private TextView tvName;

    private TextView tvShopName;

    private TextView tvValidPeriod;

    private TextView tvItemPriceOne;

    private TextView tvNumber;

    private RelativeLayout rlImage;

    private LinearLayout llOne;

    public HotelFlashSaleHolder(ViewGroup parent)
    {
        super(parent, R.layout.view_item_flash_sale);

        context = parent.getContext();
    }


    @Override
    public void setData(GiftComboBean data)
    {
        super.setData(data);

        String photoUrls = data.getPhotoUrls();

        if (!TextUtils.isEmpty(photoUrls)) {

            rlImage.setTag(photoUrls);

            int a = photoUrls.indexOf(",");

            if (a != -1) {

                String[] photoUrlsArray = photoUrls.split(",");

                Glide.with(context).load(ServiceDispatcher.getImageUrl(photoUrlsArray[0])).into(ivImageSource);

                tvNumber.setVisibility(View.VISIBLE);

                tvNumber.setText(photoUrlsArray.length + "张");
            } else {

                Glide.with(context).load(ServiceDispatcher.getImageUrl(photoUrls)).into(ivImageSource);

                tvNumber.setVisibility(View.INVISIBLE);

            }
        } else {

            tvNumber.setVisibility(View.INVISIBLE);
        }

        tvName.setText(data.getActName());

        tvShopName.setText("适用门店：" + data.getStoreDesc());

        tvValidPeriod.setText("有效期限：" + data.getEffMonthDesc());

        tvLowPrice.setText("¥" + Utils.subZeroAndDot(data.getStorePrice()+""));

        tvItemPriceOne.setText(Utils.subZeroAndDot(data.getOnlinePrice() + ""));

        long activityId = data.getActId();

        llOne.setTag(activityId);
    }

    @Override
    public void onInitializeView()
    {
        super.onInitializeView();

        tvLowPrice = findViewById(R.id.tvLowPrice);

        tvLowPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线

        ivImageSource = findViewById(R.id.ivImageSource);

        tvName = findViewById(R.id.tvName);

        tvShopName = findViewById(R.id.tvShopName);

        tvValidPeriod = findViewById(R.id.tvValidPeriod);

        tvItemPriceOne = findViewById(R.id.tvActivityPrice);

        tvNumber = findViewById(R.id.tvNumber);

        rlImage = findViewById(R.id.rlImage);

        rlImage.setOnClickListener(this);

        llOne = findViewById(R.id.llOne);

        llOne.setOnClickListener(this);


    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.rlImage:

                if (v.getTag() != null) {

                    String photoUrls = (String) v.getTag();

                    Intent intent = new Intent(context, HotelImageExpositionActivity.class);

                    intent.putExtra("imageTitleName", "限时抢购图片");

                    intent.putExtra("photoUrls", photoUrls);

                    context.startActivity(intent);

                }

                break;
            case R.id.llOne:


                if( v.getTag() != null){

                    long activityId = (long) v.getTag();

                    Intent intent =   new Intent(context,FlashSaleProductDetailActivity.class);

                    intent.putExtra("activityId",activityId);

                    context.startActivity(intent);
                }

                break;

            default:
                break;
        }
    }
}

