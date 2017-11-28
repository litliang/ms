package com.yzb.card.king.ui.other.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.GiftComboBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.CircleImageView;
import com.yzb.card.king.ui.discount.bean.PjReplyBean;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import cn.lemon.view.adapter.BaseViewHolder;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/7
 * 描  述：
 */
public class ReviewReplyViewHold extends BaseViewHolder<PjReplyBean> {


    private CircleImageView ivHeadImage;

    private TextView tvName;

    private  TextView tvTime;

    private TextView tvVoteContent;

    private Context context;

    public ReviewReplyViewHold(ViewGroup parent)
    {
        super(parent, R.layout.view_item_review_repaly);

        context = parent.getContext();
    }

    @Override
    public void setData(PjReplyBean data)
    {
        super.setData(data);

        //头像
        Glide.with(context).load(ServiceDispatcher.getImageUrl(data.getCustPicUrl())).into(ivHeadImage);

        tvName.setText(data.getNickName());

        tvTime.setText(data.getCreateTime());

        try {
            tvVoteContent.setText(URLDecoder.decode(data.getReplyContent(),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            tvVoteContent.setText("");
        }

    }

    @Override
    public void onInitializeView()
    {
        super.onInitializeView();

        ivHeadImage = findViewById(R.id.ivHeadImage);

        tvName = findViewById(R.id.tvName);

        tvTime = findViewById(R.id.tvTime);

        tvVoteContent = findViewById(R.id.tvVoteContent);
    }
}
