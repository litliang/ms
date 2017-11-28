package com.yzb.card.king.ui.travel.activity.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.TravelProduDetailBean;
import com.yzb.card.king.ui.appwidget.BaseViewGroup;
import com.yzb.card.king.ui.appwidget.StarBar;
import com.yzb.card.king.ui.appwidget.WholeListView;
import com.yzb.card.king.ui.other.activity.FullScreenImgActivity;
import com.yzb.card.king.ui.travel.adapter.ImageOnlyAdapter;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.Utils;
import com.yzb.card.king.util.XImageOptionUtil;

import org.xutils.common.util.DensityUtil;

import java.util.Arrays;
import java.util.List;

/**
 * 功能：旅游详情头部view；
 *
 * @author:gengqiyun
 * @date: 2016/11/11
 */
public class TravelDetailHeaderView extends BaseViewGroup implements View.OnClickListener
{
    private TravelDetailHeadAdView headAdView;
    private TextView tvTitle;
    private TextView tvSummry;
    private TextView tvScore;
    private TextView tvPrefPrice;
    private TextView tvOrignalPrice;
    private TextView tvShopName;
    private OnClickListener clickListener;
    private StarBar ratingBar;

    private ImageOnlyAdapter imageOnlyAdapter;
    private WholeListView productFeatureListView; //产品特色；

    public TravelDetailHeaderView(Context context)
    {
        super(context);
    }

    public TravelDetailHeaderView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void init()
    {
        super.init();
        headAdView = (TravelDetailHeadAdView) rootView.findViewById(R.id.headerAdView);
        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);

        tvSummry = (TextView) rootView.findViewById(R.id.tvSummry);
        ratingBar = (StarBar) rootView.findViewById(R.id.starBar);
        tvScore = (TextView) rootView.findViewById(R.id.tvScore);

        tvPrefPrice = (TextView) rootView.findViewById(R.id.tvPrefPrice);
        updatePriceAppearance("0");

        rootView.findViewById(R.id.panel_share).setOnClickListener(this);

        tvOrignalPrice = (TextView) rootView.findViewById(R.id.tvOrignalPrice);
        tvOrignalPrice.setText(getContext().getString(R.string.travel_orignal_start_price, Utils.subZeroAndDot("0")));
        tvOrignalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        tvShopName = (TextView) rootView.findViewById(R.id.tvShopName);
        rootView.findViewById(R.id.panelShopDetail).setOnClickListener(this);

        productFeatureListView = (WholeListView) rootView.findViewById(R.id.featureListView);
        productFeatureListView.setFocusable(false);
        productFeatureListView.setVisibility(GONE);
    }

    @Override
    protected void fillViewData(TravelProduDetailBean detailBean)
    {
        //轮播图最多3个；
        String imageCodes = detailBean.getProductImageUrl();
        if (!isEmpty(imageCodes))
        {
            List<String> imageArray = Arrays.asList(imageCodes.split(","));
            imageArray = imageArray.size() > 3 ? imageArray.subList(0, 3) : imageArray;
            headAdView.setData(imageArray);
        }
        tvTitle.setText(detailBean.getProductName());

        String labels = detailBean.getLabels();
        if (isEmpty(labels))
        {
            tvSummry.setText("");
        } else
        {
            List<String> arrays = Arrays.asList(labels.split(","));
            //最多显示4个；
            arrays = arrays.size() > 4 ? arrays.subList(0, 4) : arrays;
            String summary = "";
            for (int i = 0; i < arrays.size(); i++)
            {
                summary += (i != arrays.size() - 1 ? arrays.get(i) + "  " : arrays.get(i));
            }
            tvSummry.setText(summary);
        }

        ratingBar.setStarMarkAndSore(detailBean.getVote() / 2f);
        tvScore.setText(detailBean.getVote() + "分");
        updatePriceAppearance(detailBean.getMinPrice());

        tvOrignalPrice.setText(mContext.getString(R.string.travel_orignal_start_price,
                Utils.subZeroAndDot(detailBean.getMarketPrice())));
        tvShopName.setText(detailBean.getAgentName());

        //产品特色；
        String charcCodes = detailBean.getCharacteristicUrls();
        productFeatureListView.setVisibility(isEmpty(charcCodes) ? GONE : VISIBLE);
        if (!isEmpty(charcCodes))
        {
            List<String> charcImags = Arrays.asList(charcCodes.split(","));
            //最多显示10个；
            charcImags = charcImags.size() > 10 ? charcImags.subList(0, 10) : charcImags;
            imageOnlyAdapter = new ImageOnlyAdapter(getContext());
            imageOnlyAdapter.setImageOptions(XImageOptionUtil.getRoundImageOption(DensityUtil.dip2px(3), ImageView.ScaleType.FIT_XY));

            imageOnlyAdapter.setImageSize(0, DensityUtil.getScreenWidth() / 2);
            imageOnlyAdapter.setCallBack(new ImageOnlyAdapter.ICallBack()
            {
                @Override
                public void callBack(int clickPosition, String[] images)
                {
                    Intent intent = new Intent(mContext, FullScreenImgActivity.class);
                    intent.putExtra("currentPage", clickPosition);
                    intent.putExtra("imgUrls", images);
                    mContext.startActivity(intent);
                }
            });

            List<String> imageUrls = CommonUtil.convetCodesToUrls(charcImags);
            imageOnlyAdapter.appendALL(imageUrls);
            productFeatureListView.setAdapter(imageOnlyAdapter);
        }
    }


    private void updatePriceAppearance(String price)
    {
        String priceStr = mContext.getString(R.string.credit_repay_je, Utils.subZeroAndDot(price));
        SpannableString ss = new SpannableString(priceStr);
        ss.setSpan(new AbsoluteSizeSpan(12, true), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPrefPrice.setText(ss);
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.travel_detail_header;
    }

    @Override
    public void onClick(View v)
    {
        if (clickListener != null)
        {
            clickListener.onClick(v);
        }
    }

    public void setClickListener(OnClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    @Override
    protected void onDetachedFromWindow()
    {
        headAdView.stopADRotate();
        super.onDetachedFromWindow();
    }

}
