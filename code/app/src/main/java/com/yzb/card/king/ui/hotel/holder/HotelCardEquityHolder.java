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
import com.yzb.card.king.bean.hotel.HotelBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.hotel.activtiy.CardRightsProductDetailActivity;
import com.yzb.card.king.ui.hotel.activtiy.FlashSaleProductDetailActivity;
import com.yzb.card.king.ui.hotel.activtiy.HotelImageExpositionActivity;
import com.yzb.card.king.util.Utils;

import cn.lemon.view.adapter.BaseViewHolder;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/17
 * 描  述：卡权益
 */
public class HotelCardEquityHolder extends BaseViewHolder<GiftComboBean> implements View.OnClickListener {

    private Context context;

    private LinearLayout llOne;

    private TextView tvLowPrice;

    private ImageView ivImageSource;

    private TextView tvName;

    private TextView tvShopName;

    private TextView tvValidPeriod;

    private TextView tvItemPrice;

    private RelativeLayout rlImage;

    public HotelCardEquityHolder(ViewGroup parent)
    {
        super(parent, R.layout.view_item_card_equity);

        context = parent.getContext();
    }

    @Override
    public void setData(GiftComboBean data)
    {
        super.setData(data);

        if (data.ifHomeGiftComboFlag) {

            String photoUrls = data.getPhotoUrls();

            if (!TextUtils.isEmpty(photoUrls)) {

                rlImage.setTag(photoUrls);

                int a = photoUrls.indexOf(",");

                if (a != -1) {

                    String[] photoUrlsArray = photoUrls.split(",");

                    Glide.with(context).load(ServiceDispatcher.getImageUrl(photoUrlsArray[0])).into(ivImageSource);

                } else {

                    Glide.with(context).load(ServiceDispatcher.getImageUrl(photoUrls)).into(ivImageSource);

                }
            }

            tvName.setText(data.getActName());

            tvShopName.setText("适用门店：" + data.getStoreDesc());

            tvValidPeriod.setText("有效期限：" + data.getEffMonthDesc());
        } else {
            Glide.with(context).load(ServiceDispatcher.getImageUrl(data.getCardPhoto())).into(ivImageSource);
            tvName.setText(data.getGiftsName());
            tvShopName.setText("有效期限：" + data.getEffMonthDesc());
            tvValidPeriod.setText(data.getShopName());

        }

        tvLowPrice.setText("¥" + Utils.subZeroAndDot(data.getStorePrice()+""));

        tvItemPrice.setText(Utils.subZeroAndDot(data.getOnlinePrice() + ""));

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

        tvItemPrice = findViewById(R.id.tvItemPrice);

        llOne = findViewById(R.id.llOne);

        llOne.setOnClickListener(this);

        rlImage = findViewById(R.id.rlImage);

        rlImage.setOnClickListener(this);
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

                    Intent intent =   new Intent(context,CardRightsProductDetailActivity.class);

                    intent.putExtra("activityId",activityId);

                    context.startActivity(intent);
                }

                break;

            default:
                break;
        }
    }
}
