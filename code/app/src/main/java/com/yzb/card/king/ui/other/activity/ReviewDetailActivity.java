package com.yzb.card.king.ui.other.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.CircleImageView;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.bean.PjBean;
import com.yzb.card.king.ui.discount.bean.PjReplyBean;
import com.yzb.card.king.ui.discount.fragment.CommentDialogFragment;
import com.yzb.card.king.ui.hotel.activtiy.HotelImageExpositionActivity;
import com.yzb.card.king.ui.luxury.activity.IDialogCallBack;
import com.yzb.card.king.ui.other.adapter.ReviewReplyAdapter;
import com.yzb.card.king.ui.other.presenter.CommonReviewPresenter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lemon.view.RefreshRecyclerView;
import cn.lemon.view.adapter.Action;

/**
 * 类  名：评价详情
 * 作  者：Li Yubing
 * 日  期：2017/9/7
 * 描  述：
 */
@ContentView(R.layout.activity_review_detail)
public class ReviewDetailActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface {

    @ViewInject(R.id.recycler_view)
    private RefreshRecyclerView mRecyclerView;

    @ViewInject(R.id.tvVotePinglun)
    private TextView tvVotePinglun;

    private LinearLayout llVoteImageInfo;

    private CircleImageView ivHeadImage;

    private ImageView  ivOneImage, ivTwoImage;

    private TextView tvName, tvProductName, tvVoteNumber, tvVoteContent, tvImageNumberStr, tvVoteTime, tvVoteRepalyNmber, tvVoteZanNumber;

    private PjBean pjBean;

    private ReviewReplyAdapter mAdapter;

    private CommonReviewPresenter commonReviewPresenter;

    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);

        commonReviewPresenter = new CommonReviewPresenter(this);

        setTitleNmae("评论详情");

        initData();

        initView();
    }

    private void initData()
    {

        pjBean = (PjBean) getIntent().getSerializableExtra("voteData");
    }

    private void initView()
    {
        tvVotePinglun.setOnClickListener(this);

        findViewById(R.id.tvVoteZan).setOnClickListener(this);

        mAdapter = new ReviewReplyAdapter(this);

        View headView = LayoutInflater.from(this).inflate(R.layout.view_review_info_head, null);

        initHeadView(headView);

        mAdapter.setHeader(headView);
        mRecyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));//new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshAction(new Action() {
            @Override
            public void onAction()
            {
                getData(true);
            }
        });

        mRecyclerView.setLoadMoreAction(new Action() {
            @Override
            public void onAction()
            {
                getData(false);
            }
        });

        mRecyclerView.post(new Runnable() {
            @Override
            public void run()
            {
                mRecyclerView.showSwipeRefresh();
                getData(true);
            }
        });

    }

    private void initHeadView(View headView)
    {

        llVoteImageInfo = (LinearLayout) headView.findViewById(R.id.llVoteImageInfo);

        ivHeadImage = (CircleImageView) headView.findViewById(R.id.ivHeadImage);

        ivOneImage = (ImageView) headView.findViewById(R.id.ivOneImage);

        ivTwoImage = (ImageView) headView.findViewById(R.id.ivTwoImage);

        tvName = (TextView) headView.findViewById(R.id.tvName);

        tvProductName = (TextView) headView.findViewById(R.id.tvProductName);

        tvVoteNumber = (TextView) headView.findViewById(R.id.tvVoteNumber);

        tvVoteContent = (TextView) headView.findViewById(R.id.tvVoteContent);

        tvImageNumberStr = (TextView) headView.findViewById(R.id.tvImageNumberStr);

        tvVoteTime = (TextView) headView.findViewById(R.id.tvVoteTime);

        tvVoteRepalyNmber = (TextView) headView.findViewById(R.id.tvVoteRepalyNmber);

        tvVoteZanNumber = (TextView) headView.findViewById(R.id.tvVoteZanNumber);

        PjBean data = pjBean;

        llVoteImageInfo.setOnClickListener(this);

        llVoteImageInfo.setTag(data);

        //头像
        Glide.with(this).load(ServiceDispatcher.getImageUrl(data.getCustPicUrl())).into(ivHeadImage);

        //发布者昵称
        if ("1".equals(data.getPeopleStatus())) {

            tvName.setText("我");
        } else {
            tvName.setText(data.getNickName());
        }

        tvProductName.setText(data.getGoodsName());

        tvVoteNumber.setText(data.getVote() + "分");


        try {
            tvVoteContent.setText(URLDecoder.decode(data.getComment(),"utf-8"));
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

                Glide.with(this).load(ServiceDispatcher.getImageUrl(imageUrl)).into(ivOneImage);

            } else {

                String[] imageArray = imageUrl.split(",", 0);

                int length = imageArray.length;

                ivOneImage.setVisibility(View.VISIBLE);

                ivTwoImage.setVisibility(View.VISIBLE);

                Glide.with(this).load(ServiceDispatcher.getImageUrl(imageArray[0])).into(ivOneImage);

                Glide.with(this).load(ServiceDispatcher.getImageUrl(imageArray[1])).into(ivTwoImage);

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

    public void getData(final boolean isRefresh)
    {

        if (isRefresh) {
            page = 0;

        } else {
            page = mAdapter.getItemCount();

        }
        commonReviewPresenter.sendVoteReplyListRequest(Long.parseLong(pjBean.getVoteId()), page);

    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.tvVotePinglun:


                if (!isLogin()) {
                    new GoLoginDailog(this).show();
                } else {
                    if (pjBean != null) {
                        Map<String, String> argMap = new HashMap<>();
                        argMap.put("voteId", pjBean.getVoteId());
                        showCommentDialog(argMap);
                    }
                    break;
                }

                break;

            case R.id.tvVoteZan://赞

                showPDialog("正在提交数据……");

                int isLike = pjBean.getIsLike();

                if (isLike == 1) {

                    isLike = 2;

                } else if (isLike == 2) {

                    isLike = 1;
                }

                commonReviewPresenter.sendCreateLikeReplyRequest(Long.parseLong(pjBean.getVoteId()), isLike);

                break;

            case R.id.llVoteImageInfo:

                if (v.getTag() != null) {

                    PjBean pjBean = (PjBean) v.getTag();
                    Intent intent = new Intent(this, HotelImageExpositionActivity.class);


                    intent.putExtra("imageTitleName", "游泳池图片");


                    intent.putExtra("photoUrls", pjBean.getPicUrls());

                    startActivity(intent);
                }

                break;

            default:
                break;

        }
    }

    /**
     * 显示评价dialog；
     *
     * @param argMap
     */
    private void showCommentDialog(Map<String, String> argMap)
    {
        CommentDialogFragment.getInstance("", "").setArgs(argMap).setResultCallBack(new IDialogCallBack() {
            @Override
            public void dialogCallBack(Object... obj)
            {

                toastCustom(R.string.txt_comment_suc);

                getData(true);

            }
        }).show(getSupportFragmentManager(), "");
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {

        if (type == CommonReviewPresenter.VOTE_REPLY_List_CODE) {

            List<PjReplyBean> list = JSONArray.parseArray(o + "", PjReplyBean.class);

            if (page == 0) {

                mAdapter.clear();

                mAdapter.addAll(list);

                mRecyclerView.dismissSwipeRefresh();

                mRecyclerView.getRecyclerView().scrollToPosition(0);

            } else {
                mAdapter.addAll(list);

            }

            int size = list.size();

            if (size < AppConstant.MAX_PAGE_NUM) {

                mRecyclerView.showNoMore();
            }

        } else if (CommonReviewPresenter.VOTE_CREATE_LIKE_REPLY_CODE == type) {

            dimissPdialog();

            int isLike = pjBean.getIsLike();

            if (isLike == 1) {

                isLike = 2;
                pjBean.setLikeNum(pjBean.getLikeNum()-1);
                tvVoteZanNumber.setText(pjBean.getLikeNum() + "");

            } else if (isLike == 2) {

                isLike = 1;

                pjBean.setLikeNum(pjBean.getLikeNum()+1);

                tvVoteZanNumber.setText(pjBean.getLikeNum() + "");
            }

            pjBean.setIsLike(isLike);

        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        if (type == CommonReviewPresenter.VOTE_REPLY_List_CODE) {

            if (page == 0) {

                mRecyclerView.dismissSwipeRefresh();
            }

        } else if (CommonReviewPresenter.VOTE_CREATE_LIKE_REPLY_CODE == type) {

            dimissPdialog();

        }
    }
}
