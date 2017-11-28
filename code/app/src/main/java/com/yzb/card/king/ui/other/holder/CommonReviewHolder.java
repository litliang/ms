package com.yzb.card.king.ui.other.holder;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.CircleImageView;
import com.yzb.card.king.ui.discount.bean.PjBean;
import com.yzb.card.king.ui.hotel.activtiy.HotelImageExpositionActivity;
import com.yzb.card.king.ui.other.activity.ReviewDetailActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import cn.lemon.view.adapter.BaseViewHolder;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/7
 * 描  述：
 */
public class CommonReviewHolder extends BaseViewHolder<PjBean> implements View.OnClickListener {

    private LinearLayout llParent, llVoteImageInfo;

    private Context context;

    private CircleImageView ivHeadImage;

    private ImageView ivOneImage, ivTwoImage;

    private TextView tvName, tvProductName, tvVoteNumber, tvVoteContent, tvImageNumberStr, tvVoteTime, tvVoteRepalyNmber, tvVoteZanNumber;

    public CommonReviewHolder(ViewGroup parent)
    {
        super(parent, R.layout.view_item_common_review);

        context = parent.getContext();
    }

    @Override
    public void setData(PjBean data)
    {
        super.setData(data);

        llParent.setTag(data);

        llVoteImageInfo.setTag(data);

        //头像
        Glide.with(context).load(ServiceDispatcher.getImageUrl(data.getCustPicUrl())).into(ivHeadImage);


        //发布者昵称
        if ("1".equals(data.getPeopleStatus())) {

            tvName.setText("我");
        } else {
            tvName.setText(data.getNickName());
        }

        tvProductName.setText(data.getGoodsName());

        tvVoteNumber.setText(data.getVote() + "分");

        try {
            tvVoteContent.setText(  URLDecoder.decode(data.getComment(),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            tvVoteContent.setText("");
        }



        //图片
        String imageUrl = data.getPicUrls();

        if (TextUtils.isEmpty(imageUrl)) {

            llVoteImageInfo.setVisibility(View.GONE);

        } else {

            llVoteImageInfo.setVisibility(View.VISIBLE);

            int a = imageUrl.indexOf(",");

            if (a == -1) {

                ivOneImage.setVisibility(View.VISIBLE);

                ivTwoImage.setVisibility(View.GONE);

                tvImageNumberStr.setVisibility(View.GONE);

                Glide.with(context).load(ServiceDispatcher.getImageUrl(imageUrl)).into(ivOneImage);

            } else {

                String[] imageArray = imageUrl.split(",", 0);

                int length = imageArray.length;

                ivOneImage.setVisibility(View.VISIBLE);

                ivTwoImage.setVisibility(View.VISIBLE);

                Glide.with(context).load(ServiceDispatcher.getImageUrl(imageArray[0])).into(ivOneImage);

                Glide.with(context).load(ServiceDispatcher.getImageUrl(imageArray[1])).into(ivTwoImage);

                if (length == 2) {

                    tvImageNumberStr.setVisibility(View.GONE);
                } else {

                    tvImageNumberStr.setVisibility(View.VISIBLE);

                    tvImageNumberStr.setText("共" + length + "张");
                }

            }

        }

        //日期
        tvVoteTime.setText(data.getCreateTime());

        //数量
        tvVoteRepalyNmber.setText(data.getReplyNum() + "");

        tvVoteZanNumber.setText(data.getLikeNum() + "");

    }

    @Override
    public void onInitializeView()
    {
        super.onInitializeView();

        llParent = findViewById(R.id.llParent);

        llVoteImageInfo = findViewById(R.id.llVoteImageInfo);

        llParent.setOnClickListener(this);

        llVoteImageInfo.setOnClickListener(this);

        ivHeadImage = findViewById(R.id.ivHeadImage);

        ivOneImage = findViewById(R.id.ivOneImage);

        ivTwoImage = findViewById(R.id.ivTwoImage);

        tvName = findViewById(R.id.tvName);

        tvProductName = findViewById(R.id.tvProductName);

        tvVoteNumber = findViewById(R.id.tvVoteNumber);

        tvVoteContent = findViewById(R.id.tvVoteContent);

        tvImageNumberStr = findViewById(R.id.tvImageNumberStr);

        tvVoteTime = findViewById(R.id.tvVoteTime);

        tvVoteRepalyNmber = findViewById(R.id.tvVoteRepalyNmber);

        tvVoteZanNumber = findViewById(R.id.tvVoteZanNumber);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.llParent:

                if (v.getTag() != null) {

                    PjBean pjBean = (PjBean) v.getTag();

                    Intent intent = new Intent(context, ReviewDetailActivity.class);

                    intent.putExtra("voteData", pjBean);

                    context.startActivity(intent);

                }

                break;

            case R.id.llVoteImageInfo:

                if (v.getTag() != null) {

                    PjBean pjBean = (PjBean) v.getTag();
                    Intent intent = new Intent(context, HotelImageExpositionActivity.class);


                    intent.putExtra("imageTitleName", "游泳池图片");


                    intent.putExtra("photoUrls", pjBean.getPicUrls());

                    context.startActivity(intent);
                }
                break;

            default:
                break;

        }
    }
}
